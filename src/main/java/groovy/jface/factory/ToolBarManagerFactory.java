package groovy.jface.factory;

import groovy.swt.InvalidParentException;
import groovy.swt.factory.AbstractSwtFactory;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.codehaus.groovy.GroovyException;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.ui.forms.widgets.Form;

/**
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 1560 $
 */
public class ToolBarManagerFactory extends AbstractSwtFactory {

	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		Object parent = builder.getCurrent();

        ToolBarManager toolBarManager = null;

        if (parent instanceof Form) {
            Form form = (Form) parent;
            if (form != null) {
                toolBarManager = (ToolBarManager) form.getToolBarManager();
            }
        }

        if (parent instanceof ApplicationWindow) {
            ApplicationWindow applicationWindow = (ApplicationWindow) parent;
            if (applicationWindow != null) {
                toolBarManager = (ToolBarManager) applicationWindow.getToolBarManager();
            }
        }

        if (toolBarManager == null) {
            throw new InstantiationException("The parent of a ToolBarManager must be a Form or ApplicationWindow");
        }

        return toolBarManager;
    }
}