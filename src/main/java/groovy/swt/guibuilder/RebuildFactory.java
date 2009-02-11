/*
 * Created on Mar 17, 2004
 *
 */
package groovy.swt.guibuilder;

import groovy.lang.Closure;
import groovy.lang.MissingPropertyException;
import groovy.swt.InvalidParentException;
import groovy.swt.SwtUtils;
import groovy.swt.factory.AbstractSwtFactory;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.codehaus.groovy.GroovyException;
import org.eclipse.swt.widgets.Composite;

/**
 * Rebuild a swt widget by first disposing all children
 * 
 * @author <a href:ckl at dacelo.nl">Christiaan ten Klooster </a> 
 * $Id$
 */
public class RebuildFactory extends AbstractSwtFactory {

    private ApplicationGuiBuilder guiBuilder;

    /**
     * @param builder
     */
    public RebuildFactory(ApplicationGuiBuilder guiBuilder) {
        this.guiBuilder = guiBuilder;
    }

	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		Object parent = builder.getCurrent();

		// get parent
        if( attributes.containsKey("parent") ) {
            parent = attributes.remove("parent");
        }
        if (parent == null) {
            throw new InstantiationException("Parent widget can not be null");
        }
        
        Composite parentComposite = (Composite) SwtUtils.getParentWidget(parent, attributes);
        if (parentComposite == null) {
            throw new MissingPropertyException("parent", RebuildFactory.class);
        }
        // TODO: ????? builder.setCurrent(parentComposite);

        // get closure
        Closure closure = (Closure) attributes.remove("closure");
        if (closure == null) {
            throw new MissingPropertyException("closure", RebuildFactory.class);
        }

        // rebuild & pack widgets
        SwtUtils.disposeChildren(parentComposite);
        
        Object obj = closure.call(parentComposite);
        if (obj instanceof Composite) {
            ((Composite) obj).layout();
        }
        parentComposite.layout();
        
        return obj;
    }
}
