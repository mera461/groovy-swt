package groovy.swt.examples

import groovy.swt.SwtBuilder


class TabDemo {
    
    def swt
    def shell
    
    void run() {
        swt = new SwtBuilder()
   		
        shell = swt.shell ( 'The Tab Demo', size:[500,400] ) {
         	gridLayout()

			cTabFolder( style:"BOTTOM" ) {
				gridData( style:"fill_both" )
				cTabItem( "Item1", style:"none", ) {
					text( style:"border, multi", "Content for Item1" ) 
				}
				cTabItem( "Item2", style:"none") {
					text( style:"border, multi", "Content for Item2" ) 
				}
				cTabItem( "Item3", style:"none" ) {
					composite(){
						fillLayout()
					    text( style:"border, multi", "Content for Item3" ) 
						button("ok")
					}
				}
			}
			
			tabFolder( style:"NONE" ) {
				gridData( style:"fill_both" )
				tabItem( "Item4", style:"none") {
					text( style:"border, multi", "Content for Item4" ) 
				}
				tabItem( "Item5", style:"none") {
					text( style:"border, multi", "Content for Item5" ) 
				}
				tabItem( "Item6", style:"none") {
					text( style:"border, multi", "Content for Item6" ) 
				}
			}
         	
        }
        
		shell.doMainloop()
			
        }
    
    public static void main(String[] args) {
    	new TabDemo().run();
    }
    
}
