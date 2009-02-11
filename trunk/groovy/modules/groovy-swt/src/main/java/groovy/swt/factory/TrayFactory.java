/*
 * Created on Feb 15, 2004
 *  
 */
package groovy.swt.factory;

import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.codehaus.groovy.GroovyException;
import org.eclipse.swt.widgets.Display;

/**
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 1084 $
 */
public class TrayFactory extends AbstractSwtFactory {

    /*
     * @see groovy.swt.impl.Factory#newInstance(java.util.Map, java.lang.Object)
     */
	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
        return Display.getCurrent().getSystemTray();
    }

}