/*
 * Created on Feb 25, 2004
 *
 */
package groovy.jface.factory;

import groovy.lang.Closure;
import groovy.swt.ClosureSupport;
import groovy.swt.factory.AbstractSwtFactory;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.StructuredViewer;

/**
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 1556 $
 */
public class DoubleClickListenerFactory extends AbstractSwtFactory implements IDoubleClickListener, ClosureSupport {
    
    private Closure closure;

	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		Object parent = builder.getCurrent();
        if (parent instanceof StructuredViewer) {
            StructuredViewer viewer = (StructuredViewer) parent;
            viewer.addDoubleClickListener(this);
        } else {
            throw new InstantiationException("The parent of a DoubleClickListener must be a StructuredViewer");
        }
        return this;
    }

    /*
     * @see groovy.swt.ClosureSupport#getClosure()
     */
    public Closure getClosure() {
        return closure;
    }

    /*
     * @see groovy.swt.ClosureSupport#setClosure(groovy.lang.Closure)
     */
    public void setClosure(Closure closure) {
        this.closure = closure;
    }

    /*
     * @see org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.eclipse.jface.viewers.DoubleClickEvent)
     */
    public void doubleClick(DoubleClickEvent event) {
        closure.call(event);
    }
}