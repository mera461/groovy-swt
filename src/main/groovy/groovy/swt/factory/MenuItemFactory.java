package groovy.swt.factory;

import groovy.lang.MissingPropertyException;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.codehaus.groovy.runtime.InvokerHelper;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

/**
 * @author FT
 */
public class MenuItemFactory extends WidgetFactory {

    public MenuItemFactory() {
    	super(MenuItem.class);
    }

    public MenuItemFactory(int style) {
    	super(MenuItem.class, style);
    }

    public boolean onHandleNodeAttributes( FactoryBuilderSupport builder, Object node, Map attributes ) {
    	Object key = attributes.remove("accelerator");
    	if (key != null) {
    		int value = 0;
    		if (key instanceof Integer) {
    			value = (Integer) key;
    		} else {
        		String str = key.toString();
        		value = Action.convertAccelerator(str);
    		}
    		((MenuItem) node).setAccelerator(value);
    	}
    	return super.onHandleNodeAttributes(builder, node, attributes);
    }

    public void setChild( FactoryBuilderSupport builder, Object parent, Object child ) {
    	if (child instanceof Menu) {
    		((MenuItem)parent).setMenu((Menu)child);
    	}
    }
    
}
