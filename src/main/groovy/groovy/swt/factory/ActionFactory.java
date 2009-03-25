package groovy.swt.factory;

import groovy.jface.factory.ActionImpl;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.codehaus.groovy.GroovyException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IContributionManager;

/**
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 915 $
 */
public class ActionFactory extends AbstractSwtFactory {


	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		return new ActionImpl();
	}
	
    public void setParent( FactoryBuilderSupport builder, Object parent, Object child ) {
        if (parent instanceof IContributionManager){
            IContributionManager contributionManager = (IContributionManager) parent;
            contributionManager.add((Action)child);
        }
    }
	
}
