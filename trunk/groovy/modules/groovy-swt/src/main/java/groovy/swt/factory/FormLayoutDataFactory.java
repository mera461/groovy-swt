/*
 * Created on Feb 16, 2004
 *  
 */
package groovy.swt.factory;

import groovy.lang.GroovyRuntimeException;
import groovy.util.FactoryBuilderSupport;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Control;

/**
 * @author <a href:ckl at dacelo.nl">Christiaan ten Klooster </a> $Id
 *         LayoutDataFactory.java,v 1.2 2004/03/18 08:51:47 ckl Exp $
 */
public class FormLayoutDataFactory extends AbstractSwtFactory {

	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		return new FormData();
	}
	
    public boolean onHandleNodeAttributes( FactoryBuilderSupport builder, Object node, Map attributes ) {
        // get attachment properties
        List left = (List) attributes.remove("left");
        List right = (List) attributes.remove("right");
        List top = (List) attributes.remove("top");
        List bottom = (List) attributes.remove("bottom");

        // build new formdata
        FormData formData = (FormData) node;
        if (left != null) {
            formData.left = getFormAttachment(left);
        }
        if (right != null) {
            formData.right = getFormAttachment(right);
        }
        if (top != null) {
            formData.top = getFormAttachment(top);
        }
        if (bottom != null) {
            formData.bottom = getFormAttachment(bottom);
        }

        // set layout data
        Object parent = builder.getCurrent();
        if (parent instanceof Control) {
            Control control = (Control) parent;
            control.setLayoutData(formData);
        }

        // set remaining properties
    	setBeanProperties(node, attributes);
    	return true;
    }
	
	
	

    /**
     * @param list
     * @return @throws
     *         GroovyException
     */
    private FormAttachment getFormAttachment(List list) {
        FormAttachment formAttachment = null;
        try {
            // get constructor
            Class[] types = new Class[list.size()];
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getClass() == Integer.class) {
                    types[i] = int.class;
                } else if (list.get(i) instanceof Control) {
                    types[i] = Control.class;
                } else {
                    throw new GroovyRuntimeException(
                            "list element must be of type 'int' or 'Control': "
                                    + list.get(i));
                }
            }
            Constructor constructor = FormAttachment.class
                    .getConstructor(types);

            // invoke constructor
            if (constructor != null) {
                Object[] values = new Object[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    values[i] = list.get(i);
                }
                formAttachment = (FormAttachment) constructor
                        .newInstance(values);
            }
        } catch (Exception e) {
            throw new GroovyRuntimeException(e);
        }
        return formAttachment;
    }
}