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
        def awtframe = null
        def swingframe = null
   		
        shell = swt.shell ( 'The AwtSwt Demo', size:[200,100] ) {
         	fillLayout()
         	
         	label("this is a swt label" )
         	
         	composite ( style:"border, embedded" ) {
         		fillLayout()
	         	awtframe = awtFrame()
			}
         	composite ( style:"border, embedded" ) {
         		fillLayout()
	         	swingframe = awtFrame() {
        			swing.label("This doesn't work")
         		}
			}
        }
        
        // add a AWT label
        awtframe.add(new java.awt.Label('AWT Label'))
        
        // add a SWING label
        swingframe.add(swing.label('SWING Label'))
        
		shell.doMainloop()
	}
    
    public static void main(String[] args) {
    	new AwtSwtDemo().run();
    }
    
    
}
