/*
 * Created on Feb 20, 2004
 *
 */
package groovy.jface.factory;

import groovy.jface.impl.WizardDialogImpl;
import groovy.jface.impl.WizardImpl;
import groovy.swt.SwtUtils;
import groovy.swt.factory.AbstractSwtFactory;
import groovy.swt.factory.SwtFactory;

import java.util.Map;

import org.codehaus.groovy.GroovyException;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 1537 $
 */
public class WizardDialogFactory extends AbstractSwtFactory implements SwtFactory {

    /*
     * @see groovy.swt.factory.SwtFactory#newInstance(java.util.Map,
     *      java.lang.Object)
     */
    public Object newInstance(Map properties, Object parent) throws GroovyException {
        Shell parentShell = SwtUtils.getParentShell(parent);

        // get performFinish and performCancel closures for the wizard.
        // both closures must return boolean
        // if false is returned by finish/cancel the wizard isn't closed
        // see org.eclipse.jface.wizard.IWizard#performFinish()
        // see org.eclipse.jface.wizard.IWizard#performCancel()
       	Wizard wizard = new WizardImpl(properties.get("performFinish"), properties.get("performCancel"));
       	// get the property text which will be used for the wizard's title
        String text = (String) properties.get("text");
       	wizard.setWindowTitle(text);
       	
        WizardDialog wizardDialog = new WizardDialogImpl(parentShell, wizard);
        return wizardDialog; 
    }
}