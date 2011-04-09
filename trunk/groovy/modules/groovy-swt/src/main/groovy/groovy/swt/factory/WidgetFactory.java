/*
 * Created on Feb 15, 2004
 *  
 */
package groovy.swt.factory;

import groovy.lang.GString;
import groovy.lang.GroovyRuntimeException;
import groovy.swt.SwtUtils;
import groovy.util.FactoryBuilderSupport;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.codehaus.groovy.runtime.InvokerHelper;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabItem;

/**
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 1556 $
 */
public class WidgetFactory extends AbstractSwtFactory {

    protected Class beanClass;

    protected int defaultStyle = SWT.NONE;

    public WidgetFactory(Class beanClass) {
        this.beanClass = beanClass;
    }

    /**
     * @param beanClass2
     * @param style
     */
    public WidgetFactory(Class beanClass, int style) {
        this.beanClass = beanClass;
        this.defaultStyle = style;
    }

	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		Object parent = builder.getCurrent();
		
		// if no parent given, check if value can be the parent
		if (value != null
			&& ! (value instanceof String)
			&& ! (value instanceof GString)) {
			parent = value;
		}
		
        String styleProperty = (String) attributes.remove("style");
        int style = defaultStyle;
        if (styleProperty != null) {
        	style = SwtUtils.parseStyle(SWT.class, styleProperty);
        }

        Object parentWidget = SwtUtils.getParentWidget(parent, attributes);

        Object bean = createWidget(parentWidget, style);

        // TODO: Do we need this?
        setParent(builder, parent, bean);

        // 
        if (value instanceof GString) value = value.toString();
        if (value instanceof String) {
            // this does not create property setting order issues, since the value arg preceeds all attributes in the builder element
            InvokerHelper.setProperty(bean, "text", value);
        }
        
        return bean;
    }

    protected Object createWidget(Object parent) throws InstantiationException {
    	return createWidget(parent, 0);
    }	
	
    /**
     * @param parent
     * @param bean
     * @return @throws
     *         GroovyException
     * @throws InstantiationException 
     */
    protected Object createWidget(Object parent, int style) throws InstantiationException {
        if (beanClass == null) {
            throw new InstantiationException("No Class available to create the new widget of beanClass=null");
        }
        try {
            if (parent == null) {
                // lets try call a constructor with a single style
                Class[] types = { int.class };
                Constructor constructor = beanClass.getConstructor(types);
                if (constructor != null) {
                    Object[] arguments = { new Integer(style) };
                    return constructor.newInstance(arguments);
                }
            } else {
                Constructor[] constructors = beanClass.getConstructors();
                if (constructors != null) {
                    // lets try to find the constructor with 2 arguments with the
                    // 2nd argument being an int
                    for (Constructor constructor: constructors) {
                        Class[] types = constructor.getParameterTypes();
                        if (types.length == 2 && types[1].isAssignableFrom(int.class)) {
                            if (types[0].isAssignableFrom(parent.getClass())) {
                                Object[] arguments = { parent, new Integer(style) };
                                return constructor.newInstance(arguments);
                            }
                        } 
                    }
                    // if not found then try to find one with just one argument
                    for (Constructor constructor: constructors) {
                        Class[] types = constructor.getParameterTypes();
                        if (types.length == 1
                                && types[0].isAssignableFrom(parent.getClass())) {
                            Object[] arguments = { parent };
                            return constructor.newInstance(arguments);
                        }
                    }
                }
            }
            return beanClass.newInstance();
        } catch (NoSuchMethodException e) {
            throw new GroovyRuntimeException(e);
        } catch (InstantiationException e) {
            throw new GroovyRuntimeException("Could not instantiate class "+beanClass.getName()+" (may be abstract or could not find a suitable constructor): "+ e.getMessage(), e);
        } catch (IllegalAccessException e) {
            throw new GroovyRuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new GroovyRuntimeException(e.getTargetException().getLocalizedMessage(), e);
        }
    }

    public void setParent(FactoryBuilderSupport builder, Object parent, Object child) {
    	parent = builder.getCurrent();
        if (parent instanceof CTabItem) {
            if (!(child instanceof Control)) {
                throw new GroovyRuntimeException("The first child of cTabItem should be "+ Control.class.getName());
            }
            CTabItem tabItem = (CTabItem) parent;
            tabItem.setControl((Control) child);
        } else if (parent instanceof TabItem) {
            if (!(child instanceof Control)) {
                throw new GroovyRuntimeException("The first child of tabItem should be "+ Control.class.getName());
            }
            TabItem tabItem = (TabItem) parent;
            tabItem.setControl((Control) child);
        } else if (parent instanceof ScrolledComposite) {
            if (!(child instanceof Control)) {
                throw new GroovyRuntimeException("The first child of scrolledComposite should be "+ Control.class.getName());
            }
            ScrolledComposite scrolledComposite = (ScrolledComposite) parent;
            scrolledComposite.setContent((Control) child);
        } else if (child instanceof Menu && parent instanceof Shell) {
            Menu menu = (Menu) child;
            Shell shell = (Shell) parent;
            if (0 != (menu.getStyle() & SWT.BAR)) {
                shell.setMenuBar(menu);
            } else {
                shell.setMenu(menu);
            }
        } else if (child instanceof Menu && parent instanceof Viewer){
        	Menu menu = (Menu) child;
        	Control control = ((Viewer) parent).getControl();
        	control.setMenu(menu);
        }
        // Try to add menu to control
        // other possibilities to add a menu should be checked before
        else if (child instanceof Menu && parent instanceof Control) {
        	Menu menu = (Menu) child;
        	Control control = (Control) parent;
        	control.setMenu(menu);
        }
    }

}
