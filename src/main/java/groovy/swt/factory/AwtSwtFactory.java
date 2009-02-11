/*
 * Created on Feb 15, 2004
 *  
 */
package groovy.swt.factory;

import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.widgets.Composite;

/**
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 1084 $
 */
public class AwtSwtFactory extends AbstractSwtFactory {

	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {

        if (builder.getCurrent() instanceof Composite) {
            return SWT_AWT.new_Frame((Composite) builder.getCurrent());
        }
        
        return null;
	}	
}