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
	Object model
	/* modelProperty: a single name given the property of the model
	 * or a list of names.
	 */
	Object modelProperty
	// used for viewers when the modelProperty is a list of objects
	// select which properties of the object to display
	//def listProperties
	
	UpdateValueStrategy model2target = null
	
	UpdateValueStrategy target2model = null
}
