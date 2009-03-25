/**
 * 
 */
package groovy.swt.impl;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Frank
 * 
 */
public class ShellImpl extends Shell {

	public ShellImpl() {super();}
	public ShellImpl(Display display) {super(display);}
	public ShellImpl(Display display, int style)  {super(display, style);}
	public ShellImpl(int style)  {super(style);}
	public ShellImpl(Shell parent)  {super(parent);}
	public ShellImpl(Shell parent, int style) 	  {super(parent, style);}

	public void doMainloop() {
		pack();
		open();
		while (!isDisposed()) {
			if (!getDisplay().readAndDispatch()) {
				getDisplay().sleep();
			}
		}
	}
	
	protected void checkSubclass() {}
}
