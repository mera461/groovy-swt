package groovy.jface.factory;

import groovy.lang.MissingPropertyException;
import groovy.swt.factory.AbstractSwtFactory;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.codehaus.groovy.runtime.InvokerHelper;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.widgets.Control;

/**
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 915 $
 */
public class MenuManagerFactory extends AbstractSwtFactory {

    /*
     * @see groovy.swt.impl.SwtFactory#newInstance(java.util.Map,
     *      java.lang.Object)
     */
	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		Object parent = builder.getCurrent();

		
        String text = (String) attributes.remove("text");
        if (text == null) {
            if (value instanceof String) {
            	text = (String) value;
            } else {
            	throw new MissingPropertyException("text", String.class);
            }
        }
        
        MenuManager menuManager = new MenuManager(text);
        return menuManager;
    }
	
    public void setParent(FactoryBuilderSupport builder, Object parent, Object menuManager) {
        if (parent instanceof ApplicationWindow) {
            ApplicationWindow window = (ApplicationWindow) parent;
            if (window != null) {
                window.getMenuBarManager().add((MenuManager) menuManager);
            }
        } else if (parent instanceof MenuManager) {
        	MenuManager outerLevel = (MenuManager) parent;
        	outerLevel.add((MenuManager) menuManager);
        } else if (parent instanceof Control) {
        	// if it is a control the use the menu as a context menu.
        	Control control = (Control) parent;
        	control.setMenu(((MenuManager) menuManager).createContextMenu(control));
        }
    }	
	
}
