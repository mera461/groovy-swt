/*
 * Created on Feb 15, 2004
 *
 */
package groovy.swt.factory;

import groovy.swt.SwtUtils;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Widget;

/**
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 1556 $
 */
public class LayoutFactory extends AbstractSwtFactory {

    private Class beanClass;

    /**
     * @param beanClass
     */
    public LayoutFactory(Class beanClass) {
        this.beanClass = beanClass;
    }

	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		Object parent = builder.getCurrent();
        Layout layout = (Layout) beanClass.newInstance();

        Widget parentComposite = (Widget) SwtUtils.getParentWidget(parent, attributes);
        if (parentComposite != null) {
            ((Composite) parentComposite).setLayout(layout);
        }

        return layout;
	}
}
