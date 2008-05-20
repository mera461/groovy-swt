package groovy.swt.factory;

import groovy.lang.Closure;

import java.util.Map;

import org.codehaus.groovy.GroovyException;

/**
 * Simple Container for SWT code.
 * Access to parent by "parent" or "it"
 * 
 * @author Alexander Becher
 *
 */
public class SwtContainer extends AbstractSwtFactory implements SwtFactory {
	
	
	public Object newInstance(Map pProperties, Object pParent)
			throws GroovyException {
		Closure container = (Closure)pProperties.remove("closure");
		//container.setProperty("parent", pParent);
		container.call(pParent);
		return this;
	}

}