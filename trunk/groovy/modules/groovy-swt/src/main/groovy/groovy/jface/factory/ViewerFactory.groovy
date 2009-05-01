/*
 * Created on Feb 28, 2004
 *
 */
package groovy.jface.factory

import groovy.swt.SwtUtils
import groovy.swt.factory.WidgetFactory
import groovy.util.FactoryBuilderSupport

import java.util.Map

import org.codehaus.groovy.runtime.InvokerHelper

import org.eclipse.jface.viewers.AbstractTreeViewer
import org.eclipse.jface.viewers.CheckboxTableViewer
import org.eclipse.jface.viewers.CheckboxTreeViewer
import org.eclipse.jface.viewers.ComboViewer
import org.eclipse.jface.viewers.ListViewer
import org.eclipse.jface.viewers.TableTreeViewer
import org.eclipse.jface.viewers.TableViewer
import org.eclipse.jface.viewers.TableViewerColumn
import org.eclipse.jface.viewers.TreeViewer
import org.eclipse.jface.viewers.ViewerSorter

import org.eclipse.swt.SWT
import org.eclipse.swt.custom.TableTree
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.widgets.Combo
import org.eclipse.swt.widgets.List
import org.eclipse.swt.widgets.Table
import org.eclipse.swt.widgets.TableColumn
import org.eclipse.swt.widgets.TableItem
import org.eclipse.swt.widgets.Tree


/**
 * @author <a href:ckl at dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 1557 $
 */
public class ViewerFactory extends WidgetFactory {

	def sortable = null
	 
    /**
     * @param beanClass
     * @param style
     */
    public ViewerFactory(Class beanClass, int style) {
        super(beanClass, style)
    }

    /**
     * @param class1
     */
    public ViewerFactory(Class beanClass) {
        super(beanClass)
    }

	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		Object parent = builder.getCurrent()
        Object bean
        sortable = null
        
        String styleProperty = (String) attributes.remove("style")
        int style = 0
        if (styleProperty != null) {
        	style = SwtUtils.parseStyle(SWT.class, styleProperty)
        }

        if (beanClass.equals(TableViewer.class) && (parent instanceof Table)) {
            bean = new TableViewer((Table) parent)
            
        } else if (beanClass.equals(CheckboxTableViewer.class) && (parent instanceof Table)) {
                bean = new CheckboxTableViewer((Table) parent)

        } else if (beanClass.equals(TableViewerColumn.class) && (parent instanceof TableViewer)) {
            String index = (String) attributes.remove("index")
            if (value != null && value instanceof TableColumn) {
            	bean = new TableViewerColumn((TableViewer) parent, (TableColumn) value)
            } else if (index != null) {
                bean = new TableViewerColumn((TableViewer) parent, style, Integer.parseInt(index))
            } else {
                bean = new TableViewerColumn((TableViewer) parent, style)
            }

        } else if (beanClass.equals(TableTreeViewer.class) && (parent instanceof TableTree)) {
            bean = new TableTreeViewer((TableTree) parent)

        } else if (beanClass.equals(TreeViewer.class) && (parent instanceof Tree)) {
            bean = new TreeViewer((Tree) parent)
            // useHaslookup needs to be set before the input
            def useHashlookup = attributes.remove('useHashlookup')
            if (useHashlookup) InvokerHelper.setProperty(bean, 'useHashlookup', useHashlookup)

        } else if (beanClass.equals(CheckboxTreeViewer.class) && (parent instanceof Tree)) {
            bean = new CheckboxTreeViewer((Tree) parent)

        } else if (beanClass.equals(ComboViewer.class) && (parent instanceof Combo)) {
            bean = new ComboViewer((Combo) parent)

        } else if (beanClass.equals(ListViewer.class) && (parent instanceof List)) {
            bean = new ListViewer((List)parent)

        } else {
            Object parentWidget = SwtUtils.getParentWidget(parent, attributes)
            bean = createWidget(parentWidget)
        }

        setParent(builder, parent, bean)

        return bean
    }
	
    public boolean onHandleNodeAttributes( FactoryBuilderSupport builder, Object node, Map attributes ) {
    	if (node instanceof TableViewer) {
    		sortable = attributes.remove('sortable')
    	}
    	return true
    }

    public void onNodeCompleted( FactoryBuilderSupport builder, Object parent, Object node ) {
    	// handle the sortable attribute at the completion in case the columns are added
    	// after the viewer.
    	if (! (node instanceof TableViewer)) return
    	
    	// as default always add the sortable columns, so only skip if "sortable: false"
    	if (sortable!=null && ! sortable) return
    	
    	// no columns?
    	if (node.table.columnCount == 0) return		
    	
    	// default use all columns
    	if (sortable==null || sortable instanceof Boolean) sortable = [*0..node.table.columnCount-1]
    	
    	// set the columns to be sortable
    	sortable.each {
    		def column = node.table.getColumn(it)
    		column.addSelectionListener(
    				{
    					int dir = node.table.sortDirection
    					if (node.table.sortColumn == column) {
    						dir = (dir == SWT.UP) ? SWT.DOWN : SWT.UP
    					} else {
    						dir = SWT.DOWN
    					}
    					node.table.sortDirection = dir
    					node.table.sortColumn = column
    					node.refresh()
    				} as SelectionAdapter)
    	}

        // if no sorter have been set then use a default.
        if (! node.sorter) {
        	node.sorter = [compare: { viewer, item0, item1 ->
        		if (! viewer.table.sortColumn) return 0
        		def lp = viewer.labelProvider
        		def columnIndex = viewer.table.columns.findIndexOf { it == viewer.table.sortColumn}
        		String s1 = lp.getColumnText(item0, columnIndex)
        		String s2 = lp.getColumnText(item1, columnIndex)
        		def result = s1.compareTo(s2)
        		if (viewer.table.sortDirection == SWT.UP) result = -result
        		return result
        	}] as ViewerSorter
        		
        }
    	
    }
    
    
	
}