package groovy.swt.examples

import groovy.swt.SwtBuilder
import groovy.swt.CustomSwingBuilder


class AwtSwtDemo {
    property swt
        
    void run() {
        swt = new SwtBuilder()
   		swing = new CustomSwingBuilder()        
   		
        shell = swt.shell ( text:'The AwtSwt Demo' ) {
         	fillLayout()
         	
         	label( text:"this is a swt label" )
         	
         	composite ( style:"border, embedded" ) {
         		fillLayout()
         	
	         	swing.current = awtFrame()
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
}
