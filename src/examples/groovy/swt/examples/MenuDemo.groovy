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
		shell = swt.shell( "Menu Demo", size:[300,150]) {
			fillLayout()
			label1= label("a text")
			def menuBar = menu(style:"BAR"){
				def file = menuItem("&File", style:"CASCADE")
				def menu2 = menuItem("&Menu2", style:"CASCADE")
				def help = menuItem("&Help", style:"CASCADE")
				def filemenu = menu( style:"DROP_DOWN")	{
					menuItem(style:"PUSH", "&Open file")
					menuSeparator()
					menuItem(style:"PUSH", "E&xit"){
						onEvent('Selection'){
							shell.dispose()						
						}
					}
				}	
				file.menu= filemenu
				def m2
				def menu2menu = menu(style:"DROP_DOWN"){
						m2 = menuItem(style:"CHECK", "&Change text"){
							onEvent('Selection') {
								if (!m2.getSelection())
								label1.text="a text"
								else
									label1.text="this is the changed text"
							}
						}
				}
				menu2.menu = menu2menu
				def helpmenu = menu(style:"DROP_DOWN") {
					menuItem(style:"PUSH", "&About")
				}
				help.menu = helpmenu
				
			}		
				
			}
		shell.doMainloop()
	}

}