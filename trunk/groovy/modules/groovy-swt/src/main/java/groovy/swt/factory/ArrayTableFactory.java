package groovy.swt.factory;

import groovy.swt.SwtUtils;

import java.util.Map;
import org.codehaus.groovy.GroovyException;
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
public class ArrayTableFactory extends AbstractSwtFactory implements SwtFactory{
	protected int mStyle = SWT.NONE;

public Object newInstance(Map pProperties, Object pParent) throws GroovyException {
    	
        Shell parentShell = SwtUtils.getParentShell(pParent);
        
        String[] columnNames = (String[]) pProperties.get("columnNames");
        Object[][] data =  (Object[][]) pProperties.get("data");
        int[] width = (int[]) pProperties.get("width");
        /*
         *	Get the swt styles, seperated by ',' and without leading 'swt.' 
         */
        String styleProperty = (String) pProperties.remove("style");
        if (styleProperty != null) {
            mStyle = SwtUtils.parseStyle(SWT.class, styleProperty);
        }
        Table table = new Table((Composite) parentShell,mStyle);
        
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        int columns = columnNames.length;
        
        if (width != null && width.length == columns){
        	for (int i=0; i<columns;i++) {
        		createColumn(columnNames[i], table, width[i]);
        	}
        }
        else if (width != null && width.length == 1){
        	for (int i=0; i<columns;i++) {
        		createColumn(columnNames[i], table,  width[0]);
        	}
        }
        else {
            for (int i=0; i<columns;i++) {
            	createColumn(columnNames[i], table);
            }
        }

        if (data.length > 0){
        	for (int i=0; i< data.length; i++){
        		createRow(data[i], table, columns);
        	}
        }
        if (width != null && width.length == 0){
        	for (int loopIndex = 0; loopIndex < data.length; loopIndex++) {
                table.getColumn(loopIndex).pack();
            }
        }
        return table;
    }

	private void createColumn(String pName, Table pTable){
		TableColumn col1 = new TableColumn(pTable,SWT.LEFT);
        col1.setText(pName);
        //col1.pack();
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
