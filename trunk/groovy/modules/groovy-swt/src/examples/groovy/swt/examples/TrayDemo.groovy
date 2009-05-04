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
	         		
	         		onEvent('Selection') {
	         			println "Selection event ..."
	         		}
	         		onEvent('Hide') {
	         			println "Hide event ..."
	         		}
	         		onEvent('DefaultSelection') {
	         			println "DefaultSelection event ..."
	         		}
	         		onEvent('Show'){
	         			println "Show event ..."
	         		}
	         		onEvent('MenuDetect'){
	         			println "MenuDetect event ..."
	         			trayMenu.visible = true
	         		}
         		}
         	}
        }
        
		shell.doMainloop()
	}
    
    public static void main(String[] args) {
    	new TrayDemo().run();
    }
    
    
}
