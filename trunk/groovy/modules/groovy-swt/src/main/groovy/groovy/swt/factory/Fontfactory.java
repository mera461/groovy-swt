/*
 * Created on Jun 15, 2004
 *  
 */
package groovy.swt.factory;

import groovy.swt.InvalidParentException;
import groovy.swt.SwtUtils;
import groovy.swt.UnKnownStyleException;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.codehaus.groovy.GroovyException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Control;

/**
 * @author <a href:ckl at dacelo.nl">Christiaan ten Klooster </a> 
 * $Id$
 */
public class Fontfactory extends AbstractSwtFactory {

	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
        Object parentWidget = SwtUtils.getParentWidget(builder.getCurrent(), attributes);
        if (!(parentWidget instanceof Control)) {
            throw new InstantiationException("Parent of Font must be a Control widget");
        }

        return parentWidget;
	}	

    public boolean onHandleNodeAttributes( FactoryBuilderSupport builder, Object node, Map attributes ) {
        Control parentControl = (Control) node;

        String styleProperty = (String) attributes.remove("style");
        if (styleProperty != null) {
            int style = SwtUtils.parseStyle(SWT.class, styleProperty);

            Font initialFont = parentControl.getFont();
            FontData[] fontData = initialFont.getFontData();
            for (int i = 0; i < fontData.length; i++) {
                fontData[i].setStyle(style);
            }

            Font newFont = new Font(parentControl.getDisplay(), fontData);
            parentControl.setFont(newFont);
        }

        return true;
    }	
}