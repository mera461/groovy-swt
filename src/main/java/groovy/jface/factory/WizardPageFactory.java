/*
 * Created on Feb 20, 2004
 *
 */
package groovy.jface.factory;

import groovy.jface.impl.WizardDialogImpl;
import groovy.jface.impl.WizardPageImpl;
import groovy.lang.MissingPropertyException;
import groovy.swt.factory.AbstractSwtFactory;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;

/**
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 915 $
 */
public class WizardPageFactory extends AbstractSwtFactory {

	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		Object parent = builder.getCurrent();

        // check location
        if (!(parent instanceof WizardDialog)) {
            throw new InstantiationException("The parent of a WizardPage must be a WizardDialog");
        }
        WizardDialogImpl wizardDialog = (WizardDialogImpl) parent;

        // check for missing attributes
        String title = (String) attributes.get("title");
        if (title == null) {
            throw new MissingPropertyException("title", 
            WizardPage.class);
        }

        // get WizardPageImpl
        WizardPageImpl page = new WizardPageImpl(title);
        setBeanProperties(page, attributes);

        // get Wizard
        Wizard wizard = (Wizard) wizardDialog.getWizard();

        // add WizardPage to the Wizard
        wizard.addPage(page);
        return page;
    }
}
