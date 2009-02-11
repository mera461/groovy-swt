/*
 * Created on Feb 28, 2004
 *
 */
package groovy.jface.factory;

import groovy.swt.InvalidParentException;
import groovy.swt.convertor.PointConverter;
import groovy.swt.factory.WidgetFactory;
import groovy.util.FactoryBuilderSupport;

import java.util.List;
import java.util.Map;

import org.codehaus.groovy.GroovyException;
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
}
