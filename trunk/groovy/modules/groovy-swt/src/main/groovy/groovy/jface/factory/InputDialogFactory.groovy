package groovy.jface.factory

import groovy.swt.factory.AbstractSwtFactory
import groovy.util.FactoryBuilderSupport

import java.util.Map

import org.eclipse.jface.dialogs.IInputValidator
import org.eclipse.jface.dialogs.InputDialog
import org.eclipse.swt.widgets.Shell

/**
 * @author Frank
 */
public class InputDialogFactory extends AbstractSwtFactory {

    /*
     * @see groovy.swt.impl.SwtFactory#newInstance(java.util.Map,
     *      java.lang.Object)
     */
	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		Object parent = value ?: builder.getCurrent() 
		
        String title = attributes.remove("title")?.toString()
        String msg = attributes.remove("message")?.toString()
        String initialValue = attributes.remove("value")?.toString()
        def validator = attributes.remove("validator")
        
        InputDialog dialog = new InputDialog((Shell)parent, title, msg, initialValue, validator as IInputValidator)
        
        return dialog
    }
}
