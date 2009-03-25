
package groovy.jface.factory;

import groovy.jface.impl.WizardDialogImpl;
import groovy.jface.impl.WizardImpl;
import groovy.swt.factory.ImageFactory;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.graphics.Image;

/**
 * @author Alexander Becher
 */
public class JFaceImageFactory extends ImageFactory {

    /*
     * @see groovy.swt.impl.Factory#newInstance(java.util.Map, java.lang.Object)
     */
	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
        return super.newInstance(builder, name, value, attributes);
    }

    /**
     * Add image to a widget or window
     * Now supports actions and wizardpages.
     * 
     */
    protected void setImage(Object parent, Image image) {
        
    	if (parent instanceof Action) {
    		Action action = (Action) parent;
            action.setImageDescriptor(ImageDescriptor.createFromImage(image));
        } 

    	else if (parent instanceof WizardPage ){
    		WizardPage wizardpage = (WizardPage) parent;
    		wizardpage.setImageDescriptor(ImageDescriptor.createFromImage(image));
    	}
    	else if (parent instanceof WizardDialog){
    		WizardImpl wizard = (WizardImpl)((WizardDialogImpl)parent).getWizard();
    		wizard.setDefaultPageImageDescriptor(ImageDescriptor.createFromImage(image));
    	}
    	else {
        	super.setImage(parent, image);
        }
    }

}