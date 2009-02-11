package groovy.swt.factory;

import groovy.swt.SwtUtils;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;


/**
 * SWT Table with parameters data , columnNames and width
 * data two dimensional array
 * columnNames Array that specifies the number of columns and their title
 * width 	Array that specifies the width of each column
 * 			use as default width if width is only one value
 * 			if no width is given, each column will be packed
 * 
 * @author Alexander Becher
 *
 */
public class ArrayTableFactory extends AbstractSwtFactory {

	
	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
        //Shell parentShell = SwtUtils.getParentShell(builder.getCurrent());
		Object parent = builder.getCurrent();

        // Get the swt styles, seperated by ',' and without leading 'swt.' 
        String styleProperty = (String) attributes.remove("style");
    	int mStyle = SWT.NONE;
        if (styleProperty != null) {
			mStyle = SwtUtils.parseStyle(SWT.class, styleProperty);
        }
        Table table = new Table((Composite) parent,mStyle);
		
		return table;
	}

	
    public boolean onHandleNodeAttributes( FactoryBuilderSupport builder, Object node, Map attributes ) {
        String[] columnNames = (String[]) attributes.remove("columnNames");
        Object[][] data =  (Object[][]) attributes.remove("data");
        int[] width = (int[]) attributes.remove("width");

        Table table = (Table) node;
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        int columns = columnNames.length;
        
        // if the width have been specified for all columns
        if (width != null && width.length == columns){
        	for (int i=0; i<columns;i++) {
        		createColumn(columnNames[i], table, width[i]);
        	}
        } // if identical width for all columns
        else if (width != null && width.length == 1){
        	for (int i=0; i<columns;i++) {
        		createColumn(columnNames[i], table,  width[0]);
        	}
        } else { // if no width given
            for (int i=0; i<columns;i++) {
            	createColumn(columnNames[i], table);
            }
        }

        if (data!=null && data.length > 0){
        	for (int i=0; i< data.length; i++){
        		createRow(data[i], table, columns);
        	}
        }
        if (width != null && width.length == 0){
        	for (int loopIndex = 0; loopIndex < width.length; loopIndex++) {
                table.getColumn(loopIndex).pack();
            }
        }
        
        return true;
    }
	
	private void createColumn(String pName, Table pTable){
		TableColumn col1 = new TableColumn(pTable,SWT.LEFT);
        col1.setText(pName);
        col1.pack();
	}
	
	private void createColumn(String pName, Table pTable, int width){
		TableColumn col1 = new TableColumn(pTable,SWT.LEFT);
        col1.setText(pName);
        col1.setWidth(width);
	}
	
	private void createRow(Object[] pObj, Table pTable, int NumRows){
		TableItem item = new TableItem(pTable,0);
		
		String[] RowContent = new String[pObj.length];
		for (int i= 0; i< pObj.length; i++){
			RowContent[i] = String.valueOf(pObj[i]);
		}
		item.setText(RowContent);
	}
}
