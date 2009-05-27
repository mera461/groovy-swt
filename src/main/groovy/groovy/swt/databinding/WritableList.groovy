/**
 * 
 */
package groovy.swt.databinding;

import java.util.Collection
import java.util.List
import groovy.jface.JFaceBuilder
import org.eclipse.swt.widgets.Display


import org.eclipse.core.databinding.observable.Realm;

/**
 * Writable list only allows access in the current Realm. 
 * This list allows access from all threads and do the work sync in the realm.
 * 
 * Note: Realm.syncExec does not block the current thread (like Display.syncExec)
 * while running the closure.
 * 
 * 
 * @author Frank
 *
 */
public class WritableList extends
		org.eclipse.core.databinding.observable.list.WritableList {

	 
	 
	/* (non-Javadoc)
	 * @see org.eclipse.core.databinding.observable.list.WritableList#add(int, java.lang.Object)
	 */
	/**
	 * 
	 */
	public WritableList() {
		super();
	}

	/**
	 * @param collection
	 * @param elementType
	 */
	public WritableList(Collection collection, Object elementType) {
		super(collection, elementType);
	}

	/**
	 * @param toWrap
	 * @param elementType
	 */
	public WritableList(List toWrap, Object elementType) {
		super(toWrap, elementType);
	}

	/**
	 * @param realm
	 * @param collection
	 * @param elementType
	 */
	public WritableList(Realm realm, Collection collection, Object elementType) {
		super(realm, collection, elementType);
	}

	/**
	 * @param realm
	 * @param toWrap
	 * @param elementType
	 */
	public WritableList(Realm realm, List toWrap, Object elementType) {
		super(realm, toWrap, elementType);
	}

	/**
	 * @param realm
	 */
	public WritableList(Realm realm) {
		super(realm);
	}

	@Override
	public void add(int index, Object element) {
		sync{
			super.add(index, element)
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.databinding.observable.list.WritableList#add(java.lang.Object)
	 */
	public boolean add(Object element) {
		boolean rc = false
		sync{
			rc = super.add(element)
		}
		return rc
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.databinding.observable.list.WritableList#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection c) {
		boolean rc = false
		sync{
			rc = super.addAll(c);
		}
		return rc
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.databinding.observable.list.WritableList#addAll(int, java.util.Collection)
	 */
	@Override
	public boolean addAll(int index, Collection c) {
		boolean rc = false
		sync{ 
			rc = super.addAll(index, c)
		}
		return rc
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.databinding.observable.list.WritableList#clear()
	 */
	@Override
	public void clear() {
		sync{ 
			super.clear();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.databinding.observable.list.WritableList#move(int, int)
	 */
	@Override
	public Object move(int oldIndex, int newIndex) {
		Object rc = null
		sync{ 
			rc = super.move(oldIndex, newIndex)
		}
		return rc
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.databinding.observable.list.WritableList#remove(int)
	 */
	@Override
	public Object remove(int index) {
		Object rc = null
		sync{ 
			rc = super.remove(index)
		}
		return rc
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.databinding.observable.list.WritableList#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		boolean rc = false
		sync{ 
			rc = super.remove(o)
		}
		return rc
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.databinding.observable.list.WritableList#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection c) {
		boolean rc = false
		sync{ 
			rc = super.removeAll(c)
		}
		return rc
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.databinding.observable.list.WritableList#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection c) {
		boolean rc = false
		sync{ 
			rc = super.retainAll(c)
		}
		return rc
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.databinding.observable.list.WritableList#set(int, java.lang.Object)
	 */
	@Override
	public Object set(int index, Object element) {
		Object rc = null
		sync{
			rc = super.set(index, element)
		}
		return rc
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.databinding.observable.list.WritableList#size()
	 */
	@Override
	public int size() {
		int rc = 0
		//println "---starting size"
		//realm.exec({ ({
		sync {
			rc = super.size()
			//println "+++super.size = $rc"
		}
		//println "***returning from size: $rc"
		return rc
	}

	/**
	 * Note: Realm.syncExec does not block the current thread (like Display.syncExec)
	 * while running the closure.
	 */
	void sync(Closure c) {
		JFaceBuilder.display.syncExec(c as Runnable)
	}
	
}
