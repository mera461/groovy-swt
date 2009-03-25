/*
 * Created on Feb 20, 2004
 *
 */
package groovy.jface.impl;

import groovy.lang.Closure;

import groovy.swt.ClosureSupport;
import org.eclipse.jface.wizard.Wizard;

/**
 * Provides a Wizard implementation
 * 
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 915 $
 */
public class WizardImpl extends Wizard implements ClosureSupport{
    private Closure closure;
    private Closure finish;
    private Closure cancel;
    
    public WizardImpl() {
        super();
        setNeedsProgressMonitor(true);
    }
    
    
    public WizardImpl(Object onfinish, Object oncancel) {
    	super();
    	this.finish = (Closure) onfinish;
    	this.cancel  = (Closure) oncancel;
        setNeedsProgressMonitor(true);
    }
    
    /* 
     * New implementation of performCancel()
     * return true if no closure was defined for performCancel
     * 
     * standard text output for testing purpose
     * 
     * @see org.eclipse.jface.wizard.IWizard#performCancel()
     */
    public boolean performCancel() {
    	if (cancel == null) {
        	return true;
        }
    	// workaround since closures return Object and we need boolean 
    	
        Boolean bool = Boolean.valueOf(true);
        if (bool.equals(cancel.call() )) {
        	return true;
        }
        return false;
    }

    /* 
     * New implementation of performFinish()
     * standard text output for testing purpose
     * 
     * Should performFinish return true if no method was implemented?
     * atm its returning false
     * 
     * @see org.eclipse.jface.wizard.IWizard#performFinish()
     */
    public boolean performFinish() {
        
        if (finish == null) {
        	return false;
        }
        // workaround since closures return Object and we need boolean
        Boolean bool = Boolean.TRUE;
        if (bool.equals(finish.call() )) {
        	return true;
        }
        return false;
    }

    /*
     * @see groovy.swt.ClosureSupport#getClosure()
     */
    public Closure getClosure() {
        return closure;
    }

    /*
     * @see groovy.swt.ClosureSupport#setClosure(groovy.lang.Closure)
     */
    public void setClosure(Closure closure) {
        this.closure = closure;
    }
    

}
