/**
 * 
 */
package groovy.swt.impl;

import groovy.lang.Closure;
import groovy.swt.ClosureSupport;

import java.util.Map;

import org.eclipse.swt.dnd.DragSourceEvent;

/**
 * @author Alexander Becher
 *
 */

/*
 * source.addDragListener(new DragSourceListener() {
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
public class DragSourceListenerImpl implements
		org.eclipse.swt.dnd.DragSourceListener, ClosureSupport {

	private Closure mClosure;
	private Closure mDragStart;
	private Closure mDragSetData;
	private Closure mDragFinished;
	private int mOperations;
	private Object mParent;
	
	public DragSourceListenerImpl(Map pProperties, Object pParent, int operations){
		this.mDragStart = (Closure) pProperties.get("dragStart");
		this.mDragSetData = (Closure) pProperties.get("dragSetData");
		this.mDragFinished = (Closure) pProperties.get("dragFinished");
		this.mOperations = operations;
		this.mParent = pParent;
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DragSourceListener#dragFinished(org.eclipse.swt.dnd.DragSourceEvent)
	 */
	public void dragFinished(DragSourceEvent event) {
		if (mDragFinished != null)
				mDragFinished.call(event);

	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DragSourceListener#dragSetData(org.eclipse.swt.dnd.DragSourceEvent)
	 */
	public void dragSetData(DragSourceEvent event) {
		if (mDragSetData != null)
			mDragSetData.call(event);

	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DragSourceListener#dragStart(org.eclipse.swt.dnd.DragSourceEvent)
	 */
	public void dragStart(DragSourceEvent event) {
		if (mDragStart != null)
			mDragStart.call(event);

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
