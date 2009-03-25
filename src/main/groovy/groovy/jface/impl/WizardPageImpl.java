/*
 * Created on Feb 21, 2004
 *
 */
package groovy.jface.impl;

import groovy.lang.Closure;
import groovy.swt.ClosureSupport;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

/**
 * Implementation of a WizardPage method createControl is called on
 * Dialog.open()
 * 
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 915 $
 */
public class WizardPageImpl extends WizardPage implements ClosureSupport {
    private Closure closure;
    private Closure nextPressed;
    private Closure backPressed; 
    
    public WizardPageImpl(String title) {
        super(title);
    }

    public void createControl(Composite parent) {
        if (closure == null) {
            throw new NullPointerException(
            "No closure has been configured for this WizardPage");
        }
        Composite composite = (Composite) closure.call(parent);
        setControl(composite);
    }

    public Closure getClosure() {
        return closure;
    }

    public void setClosure(Closure closure) {
        this.closure = closure;
    }
    
    public void nextPressed() {
    	if (nextPressed != null)
    		nextPressed.call();
    }
    
    public void backPressed() {
    	if (backPressed != null)
    		backPressed.call();
    }

	/**
	 * @return the nextPressed
	 */
	public Closure getNextPressed() {
		return nextPressed;
	}

	/**
	 * @param nextPressed the nextPressed to set
	 */
	public void setNextPressed(Closure nextPressed) {
		this.nextPressed = nextPressed;
	}

	/**
	 * @return the backPressed
	 */
	public Closure getBackPressed() {
		return backPressed;
	}

	/**
	 * @param backPressed the backPressed to set
	 */
	public void setBackPressed(Closure backPressed) {
		this.backPressed = backPressed;
	}     
    
}
