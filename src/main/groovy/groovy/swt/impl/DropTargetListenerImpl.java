/**
 * 
 */
package groovy.swt.impl;

import groovy.lang.Closure;
import groovy.swt.ClosureSupport;

import java.util.Map;

import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;

/**
 * @author Alexander Becher
 *
 */
public class DropTargetListenerImpl implements DropTargetListener, ClosureSupport {

	private Closure mClosure;
	private Closure mDragEnter;
	private Closure mDragLeave;
	private Closure mDragOperationChanged;
	private Closure mDragOver;
	private Closure mDrop;
	private Closure mDropAccept;
	private Object mParent;
	private int mOperations;
	
	public DropTargetListenerImpl(Map pProperties, Object pParent, int operations){
		this.mDragEnter = (Closure)pProperties.get("dragEnter");
		this.mDragLeave = (Closure)pProperties.get("dragLeave");
		this.mDragOperationChanged = (Closure)pProperties.get("dragOperationChanged");
		this.mDragOver = (Closure)pProperties.get("dragOver");
		this.mDrop = (Closure)pProperties.get("drop");
		this.mDropAccept = (Closure)pProperties.get("dropAccept");
		this.mParent = pParent;
		this.mOperations = operations;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#dragEnter(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dragEnter(DropTargetEvent event) {

		if (mDragEnter != null)
			mDragEnter.call(event);

	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#dragLeave(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dragLeave(DropTargetEvent event) {
		if (mDragLeave != null)
			mDragLeave.call(event);

	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#dragOperationChanged(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dragOperationChanged(DropTargetEvent event) {
		if (mDragOperationChanged != null)
			mDragOperationChanged.call(event);

	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#dragOver(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dragOver(DropTargetEvent event) {
		if (mDragOver != null)
			mDragOver.call(event);

	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#drop(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void drop(DropTargetEvent event) {
		if (mDrop != null)
			mDrop.call(event);

	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#dropAccept(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dropAccept(DropTargetEvent event) {
		// TODO Auto-generated method stub

	}
	
	/* (non-Javadoc)
	 * @see org.codehaus.groovy.ui.swt.ClosureSupport#getClosure()
	 */
	public Closure getClosure() {
		return this.mClosure;
	}

	/* (non-Javadoc)
	 * @see org.codehaus.groovy.ui.swt.ClosureSupport#setClosure(groovy.lang.Closure)
	 */
	public void setClosure(Closure pClosure) {
		this.mClosure = pClosure;

	}
	public Object getParent() {
		return mParent;
	}
	public void setParent(Object parent) {
		mParent = parent;
	}

}
