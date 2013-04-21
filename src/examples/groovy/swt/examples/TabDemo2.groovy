package groovy.swt.examples

import groovy.swt.SwtBuilder


class TabDemo2 {
    
    def swt
    def shell
    
    void run() {
        swt = new SwtBuilder()
   		
        shell = swt.shell ( 'Another Tab Demo', size:[500,400] ) {
         	fillLayout()

			cTabFolder( style:"none" ) {
				fillLayout()
				cTabItem( "Item1", style:"none", ) {
					text( style:"border, multi, v_scroll, h_scroll", "Content for Item1" ) 
				}
				cTabItem( "Item2", style:"none") {
					text( style:"border, multi", "Content for Item2" ) 
				}
				cTabItem( "Item3", style:"none" ) {
					composite(){
						fillLayout()
					    text( style:"border, multi, v_scroll", "Content for Item3" ) 
						button("ok")
					}
				}
			}
        }
        
		shell.doMainloop()
			
        }
    
    public static void main(String[] args) {
    	new TabDemo().run();
    }
    
}
