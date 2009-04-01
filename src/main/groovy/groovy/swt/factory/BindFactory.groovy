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
import org.eclipse.core.databinding.conversion.IConverter
import org.eclipse.core.databinding.observable.IObservable
import org.eclipse.core.databinding.observable.Observables
import org.eclipse.core.databinding.observable.list.WritableList
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.Realm
import org.eclipse.core.databinding.property.Properties
import org.eclipse.core.databinding.UpdateValueStrategy
import org.eclipse.core.databinding.validation.IValidator

import org.eclipse.jface.databinding.swt.ISWTObservableValue
import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider
import org.eclipse.jface.databinding.viewers.ViewerSupport
import org.eclipse.jface.databinding.viewers.ViewersObservables
import org.eclipse.jface.viewers.StructuredViewer
import org.eclipse.jface.viewers.TableViewer
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.TableColumn


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
        
        extractUpdateValueStrategy(attributes, binding)
        
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
            if (node instanceof StructuredViewer && property.toLowerCase()=='input') {
            	bindViewerInput(builder, node, property, (Binding) value)
            } else {
            	bindSimpleWidget(builder, node, property, (Binding) value)
            }
            
            // this is why we cannot use entrySet().each { }
            iter.remove()
        }
    }

    def bindViewerInput(FactoryBuilderSupport builder, StructuredViewer node, def property, Binding binding) {
    	
        Object columnProperties = binding.modelProperty
        if (columnProperties instanceof GString) columnProperties = columnProperties.toString()
        
        // if the model is a writable list of objects, then the columnProperty is a property
        // of that object.
        if (binding.model instanceof WritableList) {
            if (columnProperties instanceof String) columnProperties = [columnProperties]
        	// NOTE: If I do a BeansObservables.observeList(binding.model, binding.modelProperty)
        	// then it will not monitor changes in the list, but only new assignments to the variable
        	ViewerSupport.bind(
            	    node,
            	    binding.model,
            	    BeanProperties.values(columnProperties as String[]) );
        } else if (binding.model instanceof IObservableList
        	|| binding.model instanceof IObservableSet) {
            if (columnProperties instanceof String) columnProperties = [columnProperties]
        	ViewerSupport.bind(
            	    node,
            	    binding.model,
            	    BeanProperties.values(columnProperties as String[]) );
        } else if (binding.model instanceof ArrayList) {
        	println "WARNING: Wrapping an ArrayList in a WritableList. Updating is not possible"
        	def l = new WritableList(Realm.default)
        	l.addAll(binding.model)
        	binding.model = l
        	println l[1]
        	ViewerSupport.bind(
            	    node,
            	    binding.model,
            	    BeanProperties.values(columnProperties as String[]) );
        } else {
			ViewerSupport.bind(node,
							   BeansObservables.observeList(binding.model, columnProperties, String.class),
							   Properties.selfValue(String.class));
        }
    	
    	
    	// It it is a TableViewer and no tablecolumns defined then provide some
    	// reasonably defaults with the given properties
    	if (node instanceof TableViewer
    		&& node.table.columnCount == 0) {
    		// create some table columns
    		// TODO: TableViewerColumn
    		columnProperties.each {
    			def col = new TableColumn(node.table, SWT.LEFT)
    			col.text = it
    			col.width = 100
    			col.pack()
    		}
    		node.table.headerVisible = true
    		node.table.linesVisible = true 
    	}
    }    
    
    def bindSimpleWidget(FactoryBuilderSupport builder, def node, def property, Binding binding) {
        // target observable
        def targetObservable = createTargetObservable(builder, node, property)
        
        // model observable
        Object properties = binding.modelProperty
        if (properties instanceof GString) properties = properties.toString()
        
        // wrap an array list in a WritableList
        if (binding.model instanceof ArrayList) {
        	println "WARNING: Wrapping an ArrayList in a WritableList. Updating is not possible"
        	def l = new WritableList(Realm.default)
        	l.addAll(binding.model)
        	model.bidnding = l
        }

        def modelObservable = createBeanObservable(builder, binding.model, properties)

        // create the binding
        def dbc = builder.dataBindingContext
        dbc.bindValue(targetObservable, modelObservable, binding.target2model, binding.model2target)
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
    	IObservable observable
    	if (bean instanceof IObservable) {
    		observable = bean
    	} else if (bean instanceof StructuredViewer) {
    		def selectedItem = ViewersObservables.observeSingleSelection(bean)
    		observable = BeansObservables.observeDetailValue(selectedItem, propertyName)
    	} else {
    		observable = BeansObservables.observeValue(bean, propertyName) 
    	}
    	return observable
    }

    /*
     * Set all UpdateValueStrategy attributes on the binding.
     */
    def extractUpdateValueStrategy(Map attributes, Binding binding) {
    	binding.model2target =  extractUpdateValueStrategy(attributes, binding, 'model2target')
    	binding.target2model =  extractUpdateValueStrategy(attributes, binding, 'target2model')
    }

    UpdateValueStrategy extractUpdateValueStrategy(Map attributes, Binding binding, String prefix) {
    	UpdateValueStrategy strategy = null
    	// handle the model2target:[] part
    	def strategyMap = attributes.remove(prefix)
    	if (strategyMap!=null) {
    		strategy = updateStategyAttributes(strategy, strategyMap, '')
    	}

    	// handle the model2target.converter:{} part
		strategy = updateStategyAttributes(strategy, attributes, prefix+'.')
    	return strategy
    }
    
    UpdateValueStrategy updateStategyAttributes(UpdateValueStrategy strategy, Map attributes, String prefix) {
    	def policy = attributes.remove(prefix+'policy')
    	if (strategy==null && policy!=null) strategy=new UpdateValueStrategy(true, convertPolicy(policy))
    	['afterConvertValidator', 'afterGetValidator', 'beforeSetValidator', 'converter'].each {
        	def value = attributes.remove(prefix+it)
        	if (value != null) {
        		if (strategy==null) strategy=new UpdateValueStrategy()
        		if (it=='converter') {
        			strategy.converter = value as IConverter
        		} else {
        			strategy."$it" = value as IValidator
        		}
        		
        	}
    	}
    	return strategy
    }
    
    int convertPolicy(String policy) {
    	switch(policy.toLowerCase()) {
    	case 'convert':
    		return UpdateValueStrategy.POLICY_CONVERT
    	case 'never':
    		return UpdateValueStrategy.POLICY_NEVER
    	case 'onRequest':
    		return UpdateValueStrategy.POLICY_ON_REQUEST
    	case 'update':
    		return UpdateValueStrategy.POLICY_UPDATE
    	default:
    		throw new RuntimeException("Invalid UpdateStrategy policy: $policy. It should be [never, convert, onRequest, update].")
    	}
    }
    
}
