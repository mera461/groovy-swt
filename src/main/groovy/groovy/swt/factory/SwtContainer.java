package groovy.swt.factory;

import groovy.lang.Closure;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.codehaus.groovy.GroovyException;

/**
 * Simple Container for SWT code.
 * Access to parent by "parent" or "it"
 * 
 * @author Alexander Becher
 *
 */
public class SwtContainer extends AbstractSwtFactory {
	
	
	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		Closure container = (Closure)attributes.remove("closure");
		//container.setProperty("parent", pParent);
		container.call(builder.getCurrent());
		return this;
	}

}