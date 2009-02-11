package groovy.jface.factory;

import groovy.jface.impl.PreferenceDialogImpl;
import groovy.swt.InvalidParentException;
import groovy.swt.SwtUtils;
import groovy.swt.factory.AbstractSwtFactory;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.swt.widgets.Shell;

/**
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 1237 $
 */
public class PreferencesDialogFactory extends AbstractSwtFactory {

	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		Object parent = builder.getCurrent();

		Shell parentShell = SwtUtils.getParentShell(parent);
        if (parent != null) {
            PreferenceManager pm = new PreferenceManager();
            return new PreferenceDialogImpl(parentShell, pm);
        } else {
            throw new InstantiationException("The parent of a PreferencesDialog must be an ApplicationWindow or Shell");
        }
    }
}