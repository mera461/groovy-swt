/**
 * 
 */
package groovy.swt.databinding

import org.eclipse.core.databinding.UpdateValueStrategy


/**
 * @author Frank
 *
 */
public class Binding{
	// Value, Set, List
	// Set is currently only used for checkElements of a checkTableViewer
	String bindType = 'Value'
	
	Object target
	
	Object targetProperty
	
	Object model
	
	/* modelProperty: a single name given the property of the model
	 * or a list of names.
	 */
	Object modelProperty
	
	
	/* childrenProperty: the property to use as the children of an element
	 */
	Object childrenProperty
	
	// used for viewers when the modelProperty is a list of objects
	// select which properties of the object to display
	//def listProperties
	
	/* closure: for closure binding. 
	 */
	Closure closure
	
	UpdateValueStrategy model2target = null
	
	UpdateValueStrategy target2model = null
}
