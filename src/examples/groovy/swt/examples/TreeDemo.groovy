package groovy.swt.examples

import groovy.swt.SwtBuilder

class TreeDemo {
    
    def swt
    def shell
        
    def run() {
        swt = new SwtBuilder()
        
        shell = swt.shell ( 'The Swt Demo #1', location:[100,100], size:[700,600] ) {
         	gridLayout(numColumns:3) 
 
			tree( toolTipText:"This is a tree!", style:"multi" ) {
			
				gridData( style:"fill_both" )
			
				treeItem( "A" ) {
					treeItem( "A/A" )
					treeItem( "A/B" )
					treeItem( "A/C" )
				}
				
				treeItem( "B" ) {
					treeItem( "B/A" )
					treeItem( "B/B" )
					treeItem( "B/C" )
				}
						
				menu( style:"pop_up" ) {
					menuItem( "do something!" )
					menuItem( "do something else" )
				}
			}		
        }
        
		shell.doMainloop()
	}
    
    public static void main(String[] args) {
    	new TreeDemo().run()
    }
    
}
