/*
 * Created on Feb 16, 2004
 *
 */
package groovy.swt.factory;

import groovy.jface.factory.ActionImpl;
import groovy.lang.GroovyRuntimeException;
import groovy.swt.SwtUtils;
import groovy.util.FactoryBuilderSupport;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.codehaus.groovy.GroovyException;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Control;


/**
 * @author <a href:ckl at dacelo.nl">Christiaan ten Klooster</a>
 * $Id$
 */
public class LayoutDataFactory extends AbstractSwtFactory {

    private Class beanClass;

    /**
     * @param class1
     */
    public LayoutDataFactory(Class beanClass) {
        this.beanClass = beanClass;
    }

	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		Object parent = builder.getCurrent();
        Object bean = createWidget(attributes, parent);

        if (parent instanceof Control) {
            Control control = (Control) parent;
            control.setLayoutData(bean);
        }
        else if (parent instanceof Viewer) {
        	Control control = ((Viewer)parent).getControl();
            control.setLayoutData(bean);
        }

        return bean;
	}
    
    private Object createWidget(Map attributes, Object parent) {
        Object bean = null;

        String styleText = (String) attributes.remove("style");
        if (styleText != null) {
            int style = SwtUtils.parseStyle(beanClass, styleText);

            // now lets try invoke a constructor
            Class[] types = { int.class};

            try {
                Constructor constructor = beanClass.getConstructor(types);
                if (constructor != null) {
                    Object[] values = { new Integer(style)};
                    bean = constructor.newInstance(values);
                }
            } catch (NoSuchMethodException e) {
                throw new GroovyRuntimeException(e);
            } catch (InstantiationException e) {
                throw new GroovyRuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new GroovyRuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new GroovyRuntimeException(e);
            }
        } else {
            try {
                bean = beanClass.newInstance();
            } catch (InstantiationException e) {
                throw new GroovyRuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new GroovyRuntimeException(e);
            }
        }

        return bean;
    }
}
