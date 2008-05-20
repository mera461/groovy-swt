package groovy.swt.examples

import groovy.swt.SwtBuilder


class TabDemo {
    
    def swt
    def shell
    
    void run() {
        swt = new SwtBuilder()
   		
        shell = swt.shell ( text:'The Tab Demo', size:[500,400] ) {
         	gridLayout()

			cTabFolder( style:"BOTTOM" ) {
				gridData( style:"fill_both" )
				cTabItem( style:"none", text:"Item1" ) {
					text( style:"border, multi", text:"Content for Item1" ) 
				}
				cTabItem( style:"none", text:"Item2" ) {
					text( style:"border, multi", text:"Content for Item2" ) 
				}
				cTabItem( style:"none", text:"Item3" ) {
					composite(){
						fillLayout()
					    text( style:"border, multi", text:"Content for Item3" ) 
						button(text:"ok")
					}
				}
			}
			
			tabFolder( style:"NONE" ) {
				gridData( style:"fill_both" )
				tabItem( style:"none", text:"Item4" ) {
					text( style:"border, multi", text:"Content for Item4" ) 
				}
				tabItem( style:"none", text:"Item5" ) {
					text( style:"border, multi", text:"Content for Item5" ) 
				}
				tabItem( style:"none", text:"Item6" ) {
					text( style:"border, multi", text:"Content for Item6" ) 
				}
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
    	new TabDemo().run();
    }
    
}
