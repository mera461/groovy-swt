package groovy.jface.factory;

import groovy.swt.factory.WidgetFactory;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.codehaus.groovy.runtime.InvokerHelper;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.swt.widgets.MenuItem;

/**
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 1581 $
 */
public class ContributionManagerFactory extends WidgetFactory {

    public ContributionManagerFactory(Class beanClass) {
        super(beanClass);
    }

    /*
     * @see groovy.swt.impl.SwtFactory#newInstance(java.util.Map,
     *      java.lang.Object)
     */
	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		Object parent = builder.getCurrent();
        Object bean = createWidget(parent);

        if (parent instanceof IContributionManager) {
            IContributionManager contributionManager = (IContributionManager) parent;

            if (bean instanceof IAction) {
                contributionManager.add((IAction) bean);
            }

            if (bean instanceof IContributionItem) {
                contributionManager.add((IContributionItem) bean);
            }
        }
        
        if (value instanceof String) {
            // this does not create property setting order issues, since the value arg preceeds all attributes in the builder element
            InvokerHelper.setProperty(bean, "text", value);
        }
        
        return bean;
    }
	
	// special handling for the accelerator of IAction 
    public boolean onHandleNodeAttributes( FactoryBuilderSupport builder, Object node, Map attributes ) {
    	if (node instanceof IAction) {
    		Object key = attributes.remove("accelerator");
    		if (key != null) {
    			int value = 0;
    			if (key instanceof Integer) {
    				value = (Integer) key;
    			} else {
    				String str = key.toString();
    				value = Action.convertAccelerator(str);
    			}
    			((IAction)node).setAccelerator(value);
    		}
    	}
    	return super.onHandleNodeAttributes(builder, node, attributes);
    }
	
	
}