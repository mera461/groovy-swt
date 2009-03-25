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
public class WizardWindowImpl extends Wizard implements ClosureSupport{
    private Closure closure;    
    public WizardWindowImpl() {
        super();
        setNeedsProgressMonitor(true);
    }

    /* 
     * TODO implements this
     * 
     * @see org.eclipse.jface.wizard.IWizard#performCancel()
     */
    public boolean performCancel() {
        System.out.println("performCancel ...");
        return true;
    }

    /* 
     * TODO implements this
     * 
     * @see org.eclipse.jface.wizard.IWizard#performFinish()
     */
    public boolean performFinish() {
        System.out.println("performFinish ...");
        return true;
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
