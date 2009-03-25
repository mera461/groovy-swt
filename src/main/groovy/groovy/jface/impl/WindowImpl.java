package groovy.jface.impl;

import org.eclipse.jface.window.IShellProvider;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * This is the default implementation for a Window
 * 
 * @author Alexander Becher
 */
public class WindowImpl extends org.eclipse.jface.window.Window
 {

	/**
	 * @param shell
	 */
	public WindowImpl(Shell parentShell) {
		super(parentShell);

		// default at all
		/*
		addMenuBar();
		addStatusLine();
		addToolBar(SWT.FLAT | SWT.WRAP);
		setBlockOnOpen(true);
*/
		// create window
		create();
	}
	
	/**
	 * Creates a new window which will create its shell as a 
	 * child of whatever the given shellProvider returns.
	 * 
	 * @see org.eclipse.jface.window.Window
	 * @param shellProvider
	 */
	public WindowImpl(IShellProvider shellProvider) {
		super (shellProvider);
		
		create();
	}


	/*
	 * override to make public
	 * 
	 * @see org.eclipse.jface.window.Window#getContents()
	 */
	public Control getContents() {
		return super.getContents();
	}

	/*
	 * override to make public
	 * 
	 * @see org.eclipse.jface.window.ApplicationWindow#getStatusLineManager()
	 */
	/*
	public StatusLineManager getStatusLineManager() {
		return super.getStatusLineManager();
	}
	*/
}
