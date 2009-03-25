/*
 * Inspired by the groovy.swt.BindFactory
 */
package groovy.swt.factory

import groovy.swt.SwtBuilder
import groovy.swt.databinding.Binding
import java.util.Map.Entry
import org.codehaus.groovy.binding.*

import org.eclipse.core.databinding.beans.BeansObservables
import org.eclipse.core.databinding.beans.BeanProperties
import org.eclipse.core.databinding.observable.IObservable
import org.eclipse.core.databinding.observable.Observables
import org.eclipse.core.databinding.observable.map.IObservableMap


import org.eclipse.jface.databinding.swt.ISWTObservableValue
import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider
import org.eclipse.jface.databinding.viewers.ViewerSupport
import org.eclipse.jface.viewers.StructuredViewer
import org.eclipse.swt.SWT

/**
 */
public class BindFactory extends AbstractFactory {

    public static final String CONTEXT_DATA_KEY = "BindFactoryData";

    public BindFactory() {
    }

    /**
     * Accepted Properties...
     *
     * group?
     * source ((sourceProperty) | (sourceEvent sourceValue))
     * (target targetProperty)? (? use default javabeans property if targetProperty is not present?)
     *
     *
     * @param builder
     * @param name
     * @param value
     * @param attributes
     * @return the newly created instance
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Object newInstance(FactoryBuilderSupport builder, Object name, Object value, Map attributes) throws InstantiationException, IllegalAccessException {
        Map bindContext = builder.context.get(CONTEXT_DATA_KEY) ?: [:]
        if (bindContext.isEmpty()) {
            builder.context.put(CONTEXT_DATA_KEY, bindContext)
        }

        Object model = attributes.remove("model") ?: value
        Object modelProperty = attributes.remove("modelProperty")
        Binding binding = new Binding(model:model, modelProperty:modelProperty)
        
        return binding
    }

    public boolean isLeaf() {
        return true;
    }

    public bindingAttributeDelegate(FactoryBuilderSupport builder, def node, def attributes) {
        Iterator iter = attributes.entrySet().iterator()
        Map bindContext = builder.context.get(CONTEXT_DATA_KEY) ?: [:]

        while (iter.hasNext()) {
            Entry entry = (Entry) iter.next()
            String property = entry.key.toString()
            Object value = entry.value

            def bindAttrs = bindContext.get(value) ?: [:]
            def idAttr = builder.getAt(SwtBuilder.DELEGATE_PROPERTY_OBJECT_ID) ?: SwtBuilder.DEFAULT_DELEGATE_PROPERTY_OBJECT_ID
            def id = bindAttrs.remove(idAttr)
            if (bindAttrs.containsKey("value")) {
                node."$property" = bindAttrs.remove("value")
            }

            Binding binding = null
            if (! (value instanceof Binding)) {
            	continue
            }
            
            // viewer or simple widget?
            if (node instanceof StructuredViewer && property=='input') {
            	bindViewerInput(builder, node, property, (Binding) value)
            } else {
            	bindSimpleWidget(builder, node, property, (Binding) value)
            }
            
            // this is why we cannot use entrySet().each { }
            iter.remove()
        }
    }

    def bindViewerInput(FactoryBuilderSupport builder, StructuredViewer node, def property, Binding binding) {
    	
    	// NOTE: If I do a BeansObservables.observeList(binding.model, binding.modelProperty)
    	// then it will not monitor changes in the list, but only new assignments to the variable
    	
        Object listProperties = binding.modelProperty
        if (listProperties instanceof GString) listProperties = listProperties.toString()
        if (listProperties instanceof String) listProperties = [listProperties]
    	ViewerSupport.bind(
    	    node,
			binding.model,
    	    BeanProperties.values(listProperties as String[] ) );
    }    
    
    def bindSimpleWidget(FactoryBuilderSupport builder, def node, def property, PropertyBinding pb) {
        // target observable
        def targetObservable = createTargetObservable(builder, node, property)
        
        // model observable
        Object properties = binding.modelProperty
        if (properties instanceof GString) properties = listProperties.toString()
        def modelObservable = createBeanObservable(builder, pb.bean, properties)

        // create the binding
        def dbc = findDataBindingContext(builder, node)
        dbc.bindValue(targetObservable, modelObservable, null, null)
    }

    ISWTObservableValue createTargetObservable(FactoryBuilderSupport builder, def node, def attribute) {
    	if (! attribute) throw new RuntimeException("Attribute can not be empty.")
 
    	ISWTObservableValue targetObservable = null
    	switch (attribute.toLowerCase()) {
    	case 'background':
    		targetObservable = SWTObservables.observeBackground(node)
    		break;
    	case 'editable':
    		targetObservable = SWTObservables.observeEditable(node)
    		break;
    	case 'enabled':
    		targetObservable = SWTObservables.observeEnabled(node)
    		break;
    	case 'font':
    		targetObservable = SWTObservables.observeFont(node)
    		break;
    	case 'items':
    		targetObservable = SWTObservables.observeItems(node)
    		break;
    	case 'max':
    		targetObservable = SWTObservables.observeMax(node)
    		break;
    	case 'min':
    		targetObservable = SWTObservables.observeMin(node)
    		break;
    	case 'selection':
    		targetObservable = SWTObservables.observeSelection(node)
    		break;
    	case 'singleselectionindex':
    		targetObservable = SWTObservables.observeSingleSelectionIndex(node)
    		break;
    	case 'text':
    		if (node instanceof org.eclipse.swt.widgets.Text) {
    			targetObservable = SWTObservables.observeText(node, SWT.Modify)
    		} else {
    			targetObservable = SWTObservables.observeText(node)
    		}
    		break;
    	case 'tooltiptext':
    		targetObservable = SWTObservables.observeTooltipText(node)
    		break;
    	case 'visible':
    		targetObservable = SWTObservables.observeVisible(node)
    		break;
    	default:
    		throw new RuntimeException("Unknown target attribute: $attribute")
    	}
    	return targetObservable
    }
    
    IObservable createBeanObservable(FactoryBuilderSupport builder, def bean, def propertyName) {
    	
    	return BeansObservables.observeValue(bean, propertyName)
    }

    /*
     * Look upward in the node hiearachy and find a DataBindingContext
     * to use for the bindings.
     */
    def findDataBindingContext(def builder, def node) {
    	def context = builder.context
        def dbc = null
        while (dbc == null && context!=null) {
            dbc = context[SwtBuilder.DATA_BINDING_CONTEXT]
        	context = context[FactoryBuilderSupport.PARENT_CONTEXT]
        }
    	return dbc
    }
    
}
