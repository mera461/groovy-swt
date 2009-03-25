/*
 * Created on Feb 28, 2004
 *
 */
package groovy.jface.factory;

import groovy.jface.impl.ApplicationWindowImpl;
import groovy.swt.convertor.PointConverter;
import groovy.swt.factory.WidgetFactory;
import groovy.util.FactoryBuilderSupport;

import java.util.List;
import java.util.Map;

import org.eclipse.jface.action.CoolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;

/**
 * @author <a href:ckl at dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 915 $
 */
public class WindowFactory extends WidgetFactory {

    /**
     * @param beanClass
     */
    public WindowFactory(Class beanClass) {
        super(beanClass);
    }

	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		Object parent = builder.getCurrent();

		if (parent == null){
            parent = new Shell();
        }
        if (!(parent instanceof Shell)){
            throw new InstantiationException("The parent of a Window must be a Shell");
        }
        
        Window window = (Window) createWidget(parent);
        if (window != null){
            Shell shell = (Shell) parent;

            // set title of Window
            String title = (String) attributes.remove("title");
            if (title != null){
                window.getShell().setText((String) title);
            }

            // set size of Window
            List size = (List) attributes.remove("size");
            if (size != null){
                Point point = PointConverter.getInstance().parse(size);
                window.getShell().setSize(point);
            }

            // set location of Window
            List location = (List) attributes.remove("location");
            if (location != null){
                Point point = PointConverter.getInstance().parse(location);
                window.getShell().setLocation(point);
            }
        }
        setBeanProperties(window, attributes);
        return window;
    }
	
	@Override
	public void onNodeCompleted(FactoryBuilderSupport builder, Object parent,
			Object node) {
		super.onNodeCompleted(builder, parent, node);
		
		// Because of a bug in eclipse 3.4
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=243758
		// you need to update all menuManagers
		
		if (node instanceof ApplicationWindowImpl) {
			ApplicationWindowImpl window = (ApplicationWindowImpl) node;
			
			MenuManager menuManager = window.getMenuBarManager();
			if (menuManager != null) {
				menuManager.updateAll(true);
			}
			CoolBarManager coolBarManager = window.getCoolBarManager();
			if (coolBarManager != null) {
				coolBarManager.update(true);
			}
			ToolBarManager toolBarManager = window.getToolBarManager();
			if (toolBarManager != null) {
				toolBarManager.update(true);
			}
		}
	}
	
}
