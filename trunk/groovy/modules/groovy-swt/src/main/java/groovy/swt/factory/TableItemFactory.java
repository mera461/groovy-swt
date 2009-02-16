package groovy.swt.factory;

import groovy.lang.GString;
import groovy.swt.SwtUtils;
import groovy.util.FactoryBuilderSupport;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class TableItemFactory extends AbstractSwtFactory {
	
	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {

		Object parent = builder.getCurrent();

        // Get the swt styles, seperated by ',' and without leading 'swt.' 
        String styleProperty = (String) attributes.remove("style");
    	int mStyle = SWT.NONE;
        if (styleProperty != null) {
			mStyle = SwtUtils.parseStyle(SWT.class, styleProperty);
        }
        TableItem tableItem = new TableItem((Table) parent,mStyle);
        
        // handle default value
        if (value != null) {
        	handleTextAttribute(tableItem, value);
        }
		
		return tableItem;
	}
	
    public boolean onHandleNodeAttributes( FactoryBuilderSupport builder, Object node, Map attributes ) {
        Object text = attributes.remove("text");
        if (text != null) {
        	handleTextAttribute((TableItem)node, text);
        }
        return true;
    }

	protected void handleTextAttribute(TableItem node, Object value) {
		if (value instanceof GString) value = value.toString();
		if (value instanceof String) {
			node.setText((String) value);
		} else if (value instanceof String[]) {
			node.setText((String[]) value);
		} else if (value instanceof List) {
			List list = (List) value;
			String[] arrayValues = new String[list.size()];
			int i = 0;
			for (Object o : list) {
				arrayValues[i++] = o.toString(); 
			}
			node.setText(arrayValues);
		} else {
			throw new RuntimeException("Can not handle text attribute of type "+value.getClass().getName()+" and value="+value.toString());
		}
	}
	
}
