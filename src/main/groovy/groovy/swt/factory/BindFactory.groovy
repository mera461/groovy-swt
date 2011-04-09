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
import org.eclipse.core.databinding.observable.Realm
import org.eclipse.core.databinding.observable.list.WritableList
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.map.IObservableMap
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.databinding.observable.value.ComputedValue
import org.eclipse.core.databinding.property.Properties
import org.eclipse.core.databinding.UpdateValueStrategy
import org.eclipse.core.databinding.validation.IValidator
import org.eclipse.jface.databinding.swt.ISWTObservableValue
import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider
import org.eclipse.jface.databinding.viewers.ViewerSupport
import org.eclipse.jface.databinding.viewers.ViewerProperties
import org.eclipse.jface.databinding.viewers.ViewersObservables
import org.eclipse.jface.viewers.AbstractTreeViewer
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

        Binding binding = new Binding()
        binding.model = attributes.remove("model")
        if (binding.model==null && value!=null) binding.model = value
        binding.modelProperty = attributes.remove("modelProperty")
        binding.target = attributes.remove("target")
        binding.targetProperty = attributes.remove("targetProperty")
        binding.childrenProperty = attributes.remove("childrenProperty")
        
        extractUpdateValueStrategy(attributes, binding)
        
        return binding
    }

    public boolean isLeaf() {
        return false; // closures
    }
    
    public boolean isHandlesNodeChildren() {
    	return true;
    }
    
    public boolean onNodeChildren(FactoryBuilderSupport builder, Object node, Closure childContent) {
    	node.closure = childContent
		return false
    }

	public void onNodeCompleted(FactoryBuilderSupport builder, Object parent, Object binding) {
		super.onNodeCompleted(builder, parent, binding)
		if (binding.target!=null) {
			doBind(builder, binding)
		}
	}    
    
    public bindingAttributeDelegate(FactoryBuilderSupport builder, def node, def attributes) {
        Iterator iter = attributes.entrySet().iterator()
        Map bindContext = builder.context.get(CONTEXT_DATA_KEY) ?: [:]

        while (iter.hasNext()) {
            Entry entry = (Entry) iter.next()
            String property = entry.key.toString()
            Object value = entry.value

            def bindAttrs = /*bindContext.get(value) ?: */ [:]
            def idAttr = builder.getAt(SwtBuilder.DELEGATE_PROPERTY_OBJECT_ID) ?: SwtBuilder.DEFAULT_DELEGATE_PROPERTY_OBJECT_ID
            def id = bindAttrs.remove(idAttr)
            if (bindAttrs.containsKey("value")) {
                node."$property" = bindAttrs.remove("value")
            }

            Binding binding = null
            if (! (value instanceof Binding)) {
            	continue
            }
            
            // update the binding with the target
            value.target = node
            value.targetProperty = property
            
            doBind(builder, (Binding) value)
            
            // this is why we cannot use entrySet().each { }
            iter.remove()
        }
    }
    
    def doBind(FactoryBuilderSupport builder, Binding binding) {
		if ((binding.target instanceof IObservableValue && binding.targetProperty==null)
			|| (binding.target!=null && binding.targetProperty!=null)) {
		} else {
			throw new RuntimeException("Can not creating a binding where target or targetProperty is null.")
		}
		if (binding.closure!=null
			|| (binding.model!=null && binding.modelProperty!=null)
			|| (binding.model instanceof IObservableValue && binding.modelProperty==null)) {
		} else {
			throw new RuntimeException("Can not creating a binding where model (${binding.model}) or modelProperty (${binding.modelProperty}) is null or no closure given.")
		}

        // binding the input of a viewer
        if (binding.target instanceof StructuredViewer && binding.targetProperty.toLowerCase()=='input') {
        	bindViewerInput(builder, binding)
        } else {
        	bindSimpleWidget(builder, binding)
        }
    }

    def bindViewerInput(FactoryBuilderSupport builder, Binding binding) {
    	
        Object columnProperties = binding.modelProperty
        if (columnProperties instanceof GString) columnProperties = columnProperties.toString()
        
        if (binding.model instanceof ArrayList) {
        	println "WARNING: Wrapping an ArrayList in a WritableList. Updating is not possible"
        	def l = new WritableList(Realm.default)
    		l.addAll(binding.model)
    		binding.model = l
        }

        def observableModel
        def labelProperties
        if (binding.model instanceof WritableList
        	|| binding.model instanceof IObservableList
        	|| binding.model instanceof IObservableSet) {
        	observableModel = binding.model
        
        	if (columnProperties instanceof String) columnProperties = [columnProperties]
        	labelProperties = BeanProperties.values(columnProperties as String[])
        } else {
        	observableModel = BeansObservables.observeList(binding.model, columnProperties, String.class)
        	labelProperties = Properties.selfValue(String.class)
        }
        
        if (binding.target instanceof AbstractTreeViewer) {
        	if (! binding.childrenProperty) throw new RuntimeException("Binding input on a tree you need to specify the childrenProperty")
        	if (columnProperties instanceof String) columnProperties = [columnProperties]
        	ViewerSupport.bind(
        			binding.target,
        			binding.model,
        			BeanProperties.list(binding.childrenProperty),
        			BeanProperties.values(columnProperties as String[]))
        } else {
        	ViewerSupport.bind(
        			binding.target,
        			observableModel,
        			labelProperties)
        }
        

/*        
        // if the model is a writable list of objects, then the columnProperty is a property
        // of that object.
        if (binding.model instanceof WritableList) {
        	// NOTE: If I do a BeansObservables.observeList(binding.model, binding.modelProperty)
        	// then it will not monitor changes in the list, but only new assignments to the variable
        	ViewerSupport.bind(
            	    binding.target,
            	    binding.model,
            	    BeanProperties.values(columnProperties as String[]) );
        } else if (binding.model instanceof IObservableList
        	|| binding.model instanceof IObservableSet) {
            if (columnProperties instanceof String) columnProperties = [columnProperties]
        	ViewerSupport.bind(
            	    binding.target,
            	    binding.model,
            	    BeanProperties.values(columnProperties as String[]) );
        } else {
			ViewerSupport.bind(binding.target,
							   BeansObservables.observeList(binding.model, columnProperties, String.class),
							   Properties.selfValue(String.class));
        }
*/    	
    	
    	// It it is a TableViewer and no tablecolumns defined then provide some
    	// reasonably defaults with the given properties
    	if (binding.target instanceof TableViewer
    		&& binding.target.table.columnCount == 0) {
    		// create some table columns
    		// TODO: TableViewerColumn
    		columnProperties.each {
    			def col = new TableColumn(binding.target.table, SWT.LEFT)
    			col.text = it
    			col.width = 100
    			col.pack()
    		}
    		binding.target.table.headerVisible = true
    		binding.target.table.linesVisible = true 
    	}
    }    
    
    def bindSimpleWidget(FactoryBuilderSupport builder, Binding binding) {
        // target observable
        def targetObservable = createTargetObservable(builder, binding)
		
        // model observable
		if (binding.model != null && binding.closure !=null) {
			throw new RuntimeException("Binding: You can not specify both a model and a closure")
		}

        def modelObservable = null
        // if a closure binding 
		if (binding.closure != null) {
			modelObservable = createClosureObservable(builder, binding)
		} else {
	        Object properties = binding.modelProperty
	        if (properties instanceof GString) properties = properties.toString()
	        
	        // wrap an array list in a WritableList
	        if (binding.model instanceof ArrayList) {
	        	println "WARNING: Wrapping an ArrayList in a WritableList. Updating is not possible"
	        	def l = new WritableList(Realm.default)
	        	l.addAll(binding.model)
	        	binding.model = l
	        }

	        modelObservable = createBeanObservable(builder, binding)
		}
		
        // create the binding
        def dbc = builder.dataBindingContext
        dbc."bind${binding.bindType}"(targetObservable, modelObservable, binding.target2model, binding.model2target)
    }
    
    def createClosureObservable(FactoryBuilderSupport builder, Binding binding) {
    	def ctb = new ClosureTriggerBinding(new HashMap())
    	ctb.closure = binding.closure
		def fullbinding = ctb.createBinding(ctb, null)
		if (fullbinding.bindPaths.size() == 0) {
			throw new RuntimeException("A closure binding was used, but found no model properties")
		}
    	def modelObservables = []
		def modelBinding = new Binding() 
		for (bp in fullbinding.bindPaths) {
			modelBinding.model = bp.extractNewValue(binding.closure)
			modelBinding.modelProperty = bp.children[0].propertyName // TODO: Recursive through all children
	        modelObservables << createBeanObservable(builder, modelBinding)
		}
    	
    	return [calculate: {
    		 		// TODO: Do we need get the values?
    			    def values = []
    			    for (model in modelObservables) {
    			    	values << model.getValue()
    			    }
    				def value = binding.closure.call()
					return value
    		    }] as ComputedValue
    }

    Object extractNewValue(Object newObject, def propertyName) {
        Object newValue;
        try {
            newValue = InvokerHelper.getProperty(newObject, propertyName)
        } catch (MissingPropertyException mpe) {
            //todo we should flag this whent he path is created that this is a field not a prop...
            // try direct method...
            try {
                newValue = InvokerHelper.getAttribute(newObject, propertyName)
                if (newValue instanceof Reference) {
                    newValue = ((Reference) newValue).get()
                }
            } catch (Exception e) {
                //LOGME?
                newValue = null;
            }
        }
        return newValue;
    }    
    

    def createTargetObservable(FactoryBuilderSupport builder, Binding binding) {
    	// if the target is an observable then just return it
    	if (binding.target instanceof IObservable) return binding.target 
 
    	if (binding.targetProperty.equalsIgnoreCase('checkedelements')) {
    		binding.bindType = 'Set'
    	}
    	
    	return createWidgetObservable(builder, binding.target, binding.targetProperty)
    }
    
    def createWidgetObservable(FactoryBuilderSupport builder, def node, String property) {
    	def targetObservable = null
    	switch (property.toLowerCase()) {
    	case 'background':
    		targetObservable = SWTObservables.observeBackground(node)
    		break;
    	case 'checkedelements':
    		targetObservable = ViewerProperties.checkedElements(null).observe(node)
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
    		throw new RuntimeException("Unknown target attribute: ${property}")
    	}
    	return targetObservable
    }
    
    IObservable createBeanObservable(FactoryBuilderSupport builder, Binding binding) {
    	IObservable observable
    	def bindtype = binding.bindType.toLowerCase()
    	if (binding.model instanceof IObservable) {
    		observable = binding.model
    	} else if (binding.model instanceof StructuredViewer) {
    		def selectedItem = ViewerProperties.singleSelection().observe(binding.model)
    		observable = BeanProperties."$bindtype"(binding.modelProperty).observeDetail(selectedItem)
    	} else if (binding.model instanceof org.eclipse.swt.widgets.Widget) {
    		observable = createWidgetObservable(builder, binding.model, binding.modelProperty)
    	} else {
    		observable = BeanProperties."$bindtype"(binding.modelProperty).observe(binding.model)
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
