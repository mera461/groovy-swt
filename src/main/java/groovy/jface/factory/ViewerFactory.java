/*
 * Created on Feb 28, 2004
 *
 */
package groovy.jface.factory;

import groovy.swt.SwtUtils;
import groovy.swt.factory.WidgetFactory;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.codehaus.groovy.GroovyException;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.TableTreeViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableTree;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;

/**
 * @author <a href:ckl at dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 1557 $
 */
public class ViewerFactory extends WidgetFactory {

    /**
     * @param beanClass
     * @param style
     */
    public ViewerFactory(Class beanClass, int style) {
        super(beanClass, style);
    }

    /**
     * @param class1
     */
    public ViewerFactory(Class beanClass) {
        super(beanClass);
    }

	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		Object parent = builder.getCurrent();
        Object bean;

        String styleProperty = (String) attributes.remove("style");
        if (styleProperty != null) {
            defaultStyle = SwtUtils.parseStyle(SWT.class, styleProperty);
        }

        if (beanClass.equals(TableViewer.class) && (parent instanceof Table)) {
            bean = new TableViewer((Table) parent, defaultStyle);

        } else if (beanClass.equals(TableTreeViewer.class) && (parent instanceof TableTree)) {
            bean = new TableTreeViewer((TableTree) parent, defaultStyle);

        } else if (beanClass.equals(TreeViewer.class) && (parent instanceof Tree)) {
            bean = new TreeViewer((Tree) parent, defaultStyle);

        } else if (beanClass.equals(CheckboxTreeViewer.class) && (parent instanceof Tree)) {
            bean = new CheckboxTreeViewer((Tree) parent, defaultStyle);

        } else {
            Object parentWidget = SwtUtils.getParentWidget(parent, attributes);
            bean = createWidget(parentWidget);
        }

        setParent(builder, parent, bean);

        return bean;
    }
}