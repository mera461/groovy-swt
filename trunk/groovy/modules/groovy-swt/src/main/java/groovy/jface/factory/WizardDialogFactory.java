/*
 * Created on Feb 20, 2004
 *
 */
package groovy.jface.factory;

import groovy.jface.impl.WizardDialogImpl;
import groovy.jface.impl.WizardImpl;
import groovy.lang.GString;
import groovy.swt.SwtUtils;
import groovy.swt.factory.AbstractSwtFactory;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.codehaus.groovy.runtime.InvokerHelper;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 1537 $
 */
public class WizardDialogFactory extends AbstractSwtFactory {

	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		Object parent = builder.getCurrent();
        Shell parentShell = SwtUtils.getParentShell(parent);

        // get performFinish and performCancel closures for the wizard.
        // both closures must return boolean
        // if false is returned by finish/cancel the wizard isn't closed
        // see org.eclipse.jface.wizard.IWizard#performFinish()
        // see org.eclipse.jface.wizard.IWizard#performCancel()
       	Wizard wizard = new WizardImpl(attributes.remove("performFinish"), attributes.remove("performCancel"));
       	
        if (value instanceof GString) value = value.toString();
        if (value instanceof String) {
        	wizard.setWindowTitle((String)value);
        }
        // get the property text which will be used for the wizard's title
        String text = (String) attributes.remove("text");
        if (text != null) {
        	wizard.setWindowTitle(text);
        }

       	
        WizardDialog wizardDialog = new WizardDialogImpl(parentShell, wizard);
        return wizardDialog; 
    }
	
}