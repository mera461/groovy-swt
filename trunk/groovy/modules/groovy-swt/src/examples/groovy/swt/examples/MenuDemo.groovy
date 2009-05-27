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
			menu(style:"BAR"){
				menuItem("&File" , style:"CASCADE") {
					menu( style:"DROP_DOWN")	{
						menuItem(style:"PUSH", "&Open file")
						menuSeparator()
						menuItem(style:"PUSH", "E&xit"){
							onEvent('Selection'){
								shell.dispose()						
							}
						}
					}	
				}
				menuItem("&Menu2", style:"CASCADE") {
					menu(style:"DROP_DOWN"){
						menuItem(style:"CHECK", "&Change text", id:'m2'){
							onEvent('Selection') {
								if (!m2.getSelection())
									label1.text="a text"
								else
									label1.text="this is the changed text"
							}
						}
					}					
				}
				menuItem("&Help", style:"CASCADE") {
					menu(style:"DROP_DOWN") {
						menuItem(style:"PUSH", "&About")
					}
				}
			}
		}
		shell.doMainloop()
	}

}