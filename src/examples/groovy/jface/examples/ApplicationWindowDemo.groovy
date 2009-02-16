package groovy.jface.examples

import groovy.jface.JFaceBuilder

class ApplicationWindowDemo {
    
    def swt
    def mainapp
    
    def run() {
        swt = new JFaceBuilder()
        
	    mainapp = swt.applicationWindow() { 	
	         	menuManager( "File" ) {
	         		action ( "Very Nice", closure:{ println "Very Nice !!!" } )
	         		separator()
	         		action ( "Check me", checked:true, closure:{ println "I've been checked" } )
	         	}
	
	         	menuManager( "Edit" ) {
	         		action ( "Say Hi Statusbar", closure:{ mainapp.setStatus('Hello ...') } )
	         	}
	       
				fillLayout ( type:"vertical" )
	
				label("A big red label", background:[204, 0, 0] ) 
				label("I can barelly read this", foreground:[0,200,0] )  
				label("It sure looks like the dutch flag", foreground:[0,0,150], background:[0, 0, 153] )	
		}
  
		mainapp.open()
	}

    public static void main(String[] args) {
    	new ApplicationWindowDemo().run();
    }
    
}
