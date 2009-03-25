package groovy.swt.examples

import groovy.swt.SwtBuilder
import groovy.swing.SwingBuilder
import java.awt.BorderLayout

/*
 * See SWT snippet 154 for flicker free integration.
 * http://dev.eclipse.org/viewcvs/index.cgi/org.eclipse.swt.snippets/src/org/eclipse/swt/snippets/Snippet154.java?view=co
 *
 * 
 */


class AwtSwtDemo {
    
    def swt
    def swing
    def shell
    def count = 0
    def textlabel
    
    def run() {
        swt = new SwtBuilder()
   		swing = new SwingBuilder()        
   		
        shell = swt.shell ( 'The AwtSwt Demo', size:[200,100] ) {
         	fillLayout()
         	
         	label("this is a swt label" )
         	
         	composite ( style:"border, embedded" ) {
         		fillLayout()
         		//label('Another swt label')
	         	awtFrame() {
        			swing.label('Swing label')
         		}
			}
        }
        
		shell.doMainloop()
	}
    
    public static void main(String[] args) {
    	new AwtSwtDemo().run();
    }
    
    
}
