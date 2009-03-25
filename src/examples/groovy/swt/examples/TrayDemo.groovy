package groovy.swt.examples

import groovy.swt.SwtBuilder

class TrayDemo {
    
    def swt
    def shell
        
    def run() {
        swt = new SwtBuilder()
        
        shell = swt.shell ( 'The tray Demo' ) {
         	
         	def trayMenu = menu {
         		menuItem( "menuItem1" )
         		menuItem( "menuItem2" )
         	}
         	
         	tray() {
	         	
         		trayItem( text:"trayItem1" ) {
	         		image( src:"src/test/groovy/swt/groovy-logo.png" ) 
	         		
	         		onEvent( type:"Selection", closure:{
	         			println "Selection event ..."
	         		})
	         		onEvent( type:"Hide", closure:{
	         			println "Hide event ..."
	         		})
	         		onEvent( type:"DefaultSelection", closure:{
	         			println "DefaultSelection event ..."
	         		})
	         		onEvent( type:"Show", closure:{
	         			println "Show event ..."
	         		})
	         		onEvent( type:"MenuDetect", closure:{
	         			println "MenuDetect event ..."
	         			trayMenu.visible = true
	         		})
         		}
         	}
        }
        
		shell.doMainloop()
	}
    
    public static void main(String[] args) {
    	new TrayDemo().run();
    }
    
    
}
