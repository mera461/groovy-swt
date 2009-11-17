package groovy.swt.examples

import groovy.swt.SwtBuilder


class TabAddDemo {
    
    def swt
    def shell
	def count = 2
    
    void run() {
        swt = new SwtBuilder()
   		
        shell = swt.shell ( 'Another Tab Demo', size:[500,400] ) {
         	gridLayout()
			button('New tab') {
         		onEvent('Selection') {
					count++
    				cTabItem( myTabs, text:"Generated tab $count", style:'close', ) {
    					fillLayout()
    					label( "This is no $count tab" ) 
    				}
         		}
         	}

			cTabFolder( id: 'myTabs' ) {
				gridData( style:'fill_both' )
				cTabItem( 'tab1', style:'none', ) {
					text( style:'border, multi, v_scroll, h_scroll', 'Content for Tab1' ) 
				}
				cTabItem( 'tab2', style:'none') {
					text( style:'border, multi', 'Content for Tab2' ) 
				}
			}
        }
        
		shell.doMainloop(false)
			
        }
    
    public static void main(String[] args) {
    	new TabAddDemo().run();
    }
    
}
