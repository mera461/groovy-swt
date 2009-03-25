/**
 * 
 */
package groovy.swt.factory;

import groovy.swt.InvalidParentException;
import groovy.swt.SwtUtils;
import groovy.swt.impl.DragSourceListenerImpl;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.codehaus.groovy.GroovyException;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.HTMLTransfer;
import org.eclipse.swt.dnd.RTFTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Control;

/**
 * @author Alexander Becher
 *
 */

/* Example:
 * 
 * Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
    int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;

    final DragSource source = new DragSource(tree, operations);
    source.setTransfer(types);
    final TreeItem[] dragSourceItem = new TreeItem[1];
    source.addDragListener(new DragSourceListener() {
      public void dragStart(DragSourceEvent event) {
        TreeItem[] selection = tree.getSelection();
        if (selection.length > 0 && selection[0].getItemCount() == 0) {
          event.doit = true;
          dragSourceItem[0] = selection[0];
        } else {
          event.doit = false;
        }
      };

      public void dragSetData(DragSourceEvent event) {
        event.data = dragSourceItem[0].getText();
      }

      public void dragFinished(DragSourceEvent event) {
        if (event.detail == DND.DROP_MOVE)
          dragSourceItem[0].dispose();
        dragSourceItem[0] = null;
      }
    });
    
    */
 
public class DragSourceFactory extends AbstractSwtFactory {

	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		Object pParent = builder.getCurrent();
		Control parent;
		if (!(pParent instanceof Control || pParent instanceof Viewer))
			throw new InstantiationException("Parent node of DragSource ("+name+") must be Widget or Viewer");
		if (pParent instanceof Control){
			parent = (Control) pParent;
		}
		else {
			parent = ((Viewer) pParent).getControl();
		}
		
		// get operations
		// e.g. "DROP_MOVE, DROP_COPY, DROP_LINK"
		String ops = (String)attributes.remove("operations");
		if (ops == null)
			ops = (String)attributes.remove("style");
		if (ops == null)
			ops = (String)attributes.remove("type");
		if (ops == null)
			ops = "copy, move, link";
			int operations = SwtUtils.parseStyle(DND.class, ops , true);

//		 try to get Transfer
		Transfer[] types = null;
		Object transfer = attributes.remove("transfer");
		if (transfer instanceof String){
			
			if (((String)transfer).equalsIgnoreCase("text")){
				types = new Transfer[] { TextTransfer.getInstance() };
			}
			else if (((String)transfer).equalsIgnoreCase("html")){
				types = new Transfer[] { HTMLTransfer.getInstance()};
			}
			else if (((String)transfer).equalsIgnoreCase("file")){
				types = new Transfer[] { FileTransfer.getInstance()};
			}
			else if (((String)transfer).equalsIgnoreCase("rtf")){
				types = new Transfer[] { RTFTransfer.getInstance()};
			}
			// ecore stuff
//			else if (((String)transfer).equalsIgnoreCase("local")){
//				types = new Transfer[] {LocalTransfer.getInstance() };
//			}
		}
		else if (transfer instanceof Transfer[]){
			types = (Transfer[]) transfer;
		}
		
		DragSource source = new DragSource(parent, operations);
		source.setTransfer(types);
		DragSourceListenerImpl listener = new DragSourceListenerImpl(attributes, pParent, operations);
		
		source.addDragListener(listener);
		
		return listener;
	}

}
