package groovy.swt.examples

import groovy.swt.SwtBuilder
import org.eclipse.swt.layout.FormAttachment
import org.eclipse.swt.graphics.Rectangle

/*
* java version: http://dev.eclipse.org/viewcvs/index.cgi/%7Echeckout%7E/platform-swt-home/snippits/snippet109.html
*/

class SashFormDemo {
    
    def shell    
    def sashForm1  
    def builder  
    
    def run() {
        builder = new SwtBuilder()
        
        shell = builder.shell ( 'The SashForm Demo' ) {
        	fillLayout()
        
       		sashForm1 = sashForm( style:"horizontal" ) {
       			fillLayout()
       			
       			composite( style:"none" ) {
	       			fillLayout()
	       			label( "Label in pane 1" )
       			}
    
       			composite( style:"none" ) {
	       			fillLayout()
	       			button( "Button in pane2", style:"push" )
       			}
    
       			composite( style:"none" ) {
       				fillLayout()
	       			label( "Label in pane3" )
       			}
       		}
       		
       		sashForm1.weights = [30,40,30]
       	}	

		shell.doMainloop()
	}

    public static void main(String[] args) {
    	new SashFormDemo().run();
    }
    
    
    
}

