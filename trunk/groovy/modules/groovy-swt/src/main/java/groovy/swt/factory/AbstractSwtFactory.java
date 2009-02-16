/*
 * Created on Feb 15, 2004
 *  
 */
package groovy.swt.factory;

import groovy.lang.GString;
import groovy.swt.SwtUtils;
import groovy.swt.convertor.ColorConverter;
import groovy.swt.convertor.PointConverter;
import groovy.util.AbstractFactory;
import groovy.util.FactoryBuilderSupport;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandItem;

/**
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 3981 $
 */
public abstract class AbstractSwtFactory extends AbstractFactory {

	
    public boolean onHandleNodeAttributes( FactoryBuilderSupport builder, Object node, Map attributes ) {
    	setBeanProperties(node, attributes);
    	return true;
    }
	
	
    /**
     * set the properties
     * 
     * @param bean
     * @param properties
     */
    protected void setBeanProperties(Object bean, Map properties) {

        if (bean instanceof Control) {
            Control control = (Control) bean;

            // set size of widget
            Object size = properties.remove("size");
            if (size != null) {
                setSize(control, size);
            }

            // set background of widget
            Object colorValue = properties.remove("background");
            if (colorValue != null) {
                Color background = getColor(control, colorValue);
                control.setBackground(background);
            }

            // set foreground of widget
            colorValue = properties.remove("foreground");
            if (colorValue != null) {
                Color foreground = getColor(control, colorValue);
                control.setForeground(foreground);
            }
        } else if (bean instanceof ExpandItem){
        	Integer height = (Integer)properties.remove("height");
        	if (height != null){
        		((ExpandItem) bean).setHeight(height.intValue());
        	}
        }

        List<String> propertiesProcessed = new ArrayList<String>();
        for (Iterator iter=properties.entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Map.Entry) iter.next();
            String property = entry.getKey().toString();
            Object value = entry.getValue();
            Field field = null;
            try {
                field = bean.getClass().getDeclaredField(property);
                // throws an exception if no field defined with that name.
                if (value instanceof Boolean) {
                    field.setBoolean(bean, ((Boolean) value).booleanValue());
                } else if (value instanceof Integer) {
                    field.setInt(bean, ((Integer) value).intValue());
                } else if (value instanceof Double) {
                    field.setDouble(bean, ((Double) value).doubleValue());
                } else if (value instanceof Float) {
                    field.setFloat(bean, ((Float) value).floatValue());
                } else {
                	// Is it a String with a SWT constant?
                	if (value instanceof GString) value = value.toString();
                	if (value instanceof String) {
               			int constValue = SwtUtils.parseStyle(SWT.class, (String)value);
                        field.setInt(bean, constValue);
                	} else {
                		field = null;
                	}
                }
            } catch (Exception e) {
            	field = null;
            }
            
            if (field != null) {
            	// the field has been set. Remove it from the properties and let 
            	// and let FactoryBuilderSupport handle the rest
            	propertiesProcessed.add(property);
            }
        }
        // remove all processed properties
        for (String prop: propertiesProcessed) {
        	properties.remove(prop);
        }
    }

    protected Color getColor(Control control, Object colorValue) {
        Color color = null;
        if (colorValue != null) {
            RGB rgb = null;
            if (colorValue instanceof Color) {
                color = (Color) colorValue;
            } else if (colorValue instanceof List) {
                rgb = ColorConverter.getInstance().parse((List) colorValue);
                color = new Color(control.getDisplay(), rgb);
            } else {
                rgb = ColorConverter.getInstance().parse(colorValue.toString());
                color = new Color(control.getDisplay(), rgb);
            }
        }
        return color;
    }

    protected void setSize(Control control, Object size) {
        Point point = null;
        if (size != null) {
            if (size instanceof Point) {
                point = (Point) size;
            } else if (size instanceof List) {
                point = PointConverter.getInstance().parse((List) size);
            }
            control.setSize(point);
        }
    }
}