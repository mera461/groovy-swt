/**
 * 
 */
package groovy.swt.factory;

import groovy.swt.SwtUtils;
import groovy.swt.impl.DropTargetListenerImpl;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
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
public class DropTargetFactory extends AbstractSwtFactory {

	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		Object pParent = builder.getCurrent();
		Control parent;
		if (!(pParent instanceof Control || pParent instanceof Viewer))
			throw new InstantiationException("Parent node of DropTarget ("+name+") must be Widget or Viewer");
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
		int operations = SwtUtils.parseStyle(DND.class, ops, true);
		
		// try to get Transfer
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
		
		DropTarget source = new DropTarget(parent, operations);
		source.setTransfer(types);

		DropTargetListenerImpl listener = new DropTargetListenerImpl(attributes, pParent, operations);
		source.addDropListener(listener);
		
		return listener;
	}

}
