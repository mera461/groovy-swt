package groovy.swt.examples

import groovy.swt.SwtBuilder

/**
*
* @author Alexander Becher
*/
class MenuDemo {

	def swt = new SwtBuilder()

	public static void main(String[] args) {
    	def demo = new MenuDemo()
    	demo.run()
  	}
	
	void run() {
		
		def shell, label1
		shell = swt.shell( text:"Menu Demo", size:[300,150]) {
			fillLayout()
			label1= label(text:"a text")
			def menuBar = menu(style:"BAR"){
				def file = menuItem(text:"&File", style:"CASCADE")
				def menu2 = menuItem(text:"&Menu2", style:"CASCADE")
				def help = menuItem(text:"&Help", style:"CASCADE")
				def filemenu = menu( style:"DROP_DOWN")	{
					menuItem(style:"PUSH", text:"&Open file")
					menuSeparator()
					menuItem(style:"PUSH", text:"E&xit"){
						onEvent(type:"Selection", closure:{
							shell.dispose()						
						})
					}
				}	
				file.menu= filemenu
				def m2
				def menu2menu = menu(style:"DROP_DOWN"){
						m2 = menuItem(style:"CHECK", text:"&Change text"){
							onEvent(type:"Selection", closure:{
								if (!m2.getSelection())
								label1.text="a text"
								else
									label1.text="this is the changed text"
							})
						}
				}
				menu2.menu = menu2menu
				def helpmenu = menu(style:"DROP_DOWN") {
					menuItem(style:"PUSH", text:"&About")
				}
				help.menu = helpmenu
				
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