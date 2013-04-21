package groovy.swt.examples

import groovy.swt.SwtBuilder
import groovy.jface.JFaceBuilder
import org.eclipse.swt.layout.GridData
    
/*
 * Inherit from this, just it doesn't work very well in eclipse.
 * It seems that the main class has to be in the file for eclipse
 * to launch it.
 */
public class DemoLauncher {
	
    def swt = new SwtBuilder()
    def jface = new JFaceBuilder()
    def shell = null
    
	public static void main(String[] args) {
    	def demo = new ArrayTableDemo()
    	demo.build()
    	demo.run()
    }
    
    def run(){
		shell.open()
		
		while(! shell.isDisposed()) { 
			if (! shell.display.readAndDispatch()) {
				shell.display.sleep();
			}
		}
    }        
}