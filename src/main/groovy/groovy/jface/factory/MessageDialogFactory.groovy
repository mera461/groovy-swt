package groovy.jface.factory

import groovy.swt.factory.AbstractSwtFactory
import groovy.util.FactoryBuilderSupport


import java.util.Map

import org.eclipse.jface.dialogs.MessageDialog
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.widgets.Shell

/**
 * @author Frank
 */
public class MessageDialogFactory extends AbstractSwtFactory {

    /*
     * @see groovy.swt.impl.SwtFactory#newInstance(java.util.Map,
     *      java.lang.Object)
     */
	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		Object parent = value ?: builder.getCurrent() 
		
        String title = attributes.remove("title")?.toString()
        Image titleImage = attributes.remove("titleImage")
        String message = attributes.remove("message")?.toString()
        def imageType = attributes.remove("imageType") ?: 0
        if (imageType instanceof GString) imageType = imageType.toString()
        if (imageType instanceof String) imageType = MessageDialog."${imageType.toUpperCase()}"

        def labels = attributes.remove("buttonLabels")
        if (labels) {
        	// translate from GString to String (just in case)
        	labels = labels.collect {it.toString()}
        }
        int defaultIndex = attributes.remove("defaultIndex") ?: 0
        
        MessageDialog dialog = new MessageDialog((Shell)parent, title, titleImage, message, imageType, labels as String[], defaultIndex)
        
        return dialog
    }
}
