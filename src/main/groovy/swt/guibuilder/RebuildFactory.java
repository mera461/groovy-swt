/*
 * Created on Mar 17, 2004
 *
 */
package groovy.swt.guibuilder;

import groovy.lang.Closure;
import groovy.lang.MissingPropertyException;
import groovy.swt.SwtUtils;
import groovy.swt.factory.AbstractSwtFactory;
import groovy.swt.factory.SwtFactory;
import java.util.Map;
import org.codehaus.groovy.GroovyException;
import org.eclipse.swt.widgets.Composite;

/**
 * Rebuild a swt widget by first disposing all children
 * 
 * @author <a href:ckl at dacelo.nl">Christiaan ten Klooster </a> 
 * $Id$
 */
public class RebuildFactory extends AbstractSwtFactory implements SwtFactory {

    /*
     * @see groovy.swt.factory.AbstractSwtFactory#newInstance(java.util.Map,
     *      java.lang.Object)
     */
    public Object newInstance(Map properties, Object parent) throws GroovyException {
        // get parent
        if(parent == null) {
            parent = properties.remove("parent");
        }
        Composite parentComposite = (Composite) SwtUtils.getParentWidget(parent);
        if (parentComposite == null) {
            throw new MissingPropertyException("parent", RebuildFactory.class);
        }

        // get closure
        Closure closure = (Closure) properties.remove("closure");
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
        return parent;
    }
}
