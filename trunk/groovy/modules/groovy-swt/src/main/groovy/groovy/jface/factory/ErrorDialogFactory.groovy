package groovy.jface.factory

import groovy.swt.factory.AbstractSwtFactory
import groovy.util.FactoryBuilderSupport

import java.util.Map

import org.eclipse.core.runtime.IStatus
import org.eclipse.core.runtime.Status
import org.eclipse.jface.dialogs.ErrorDialog
import org.eclipse.swt.widgets.Shell
/**
 * @author Frank
 */
public class ErrorDialogFactory extends AbstractSwtFactory {

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
        IStatus status = attributes.remove("status") ?: new Status(Status.ERROR, 'pluginid', msg)
        int displayMask = attributes.remove("displayMask") ?: 0
        
        ErrorDialog dialog = new ErrorDialog((Shell)parent, title, msg, status, displayMask)
        
        return dialog
    }
}
