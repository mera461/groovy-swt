package groovy.swt.examples

import groovy.swt.SwtBuilder
import groovy.swt.CustomSwingBuilder

class AwtSwtDemo {
    
    def swt
    def swing
    def shell
    
    def run() {
        swt = new SwtBuilder()
   		swing = new CustomSwingBuilder()        
   		
        shell = swt.shell ( text:'The AwtSwt Demo' ) {
         	fillLayout()
         	
         	label( text:"this is a swt label" )
         	
         	composite ( style:"border, embedded" ) {
         		fillLayout()
         	
	         	//swing.awtFrame()
				swing.tree()
			}
        }
        
		shell.open()
	
		while(! shell.isDisposed()) { 
			if (! shell.display.readAndDispatch()) {
				shell.display.sleep();
			}
		}
	}
    
    public static void main(String[] args) {
    	new AwtSwtDemo().run();
    }
    
    
}
