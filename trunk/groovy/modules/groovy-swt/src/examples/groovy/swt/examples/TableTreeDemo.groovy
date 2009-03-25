package groovy.swt.examples

import groovy.swt.SwtBuilder

class TableTreeDemo {
    
    def swt
    def shell
        
    void run() {
        swt = new SwtBuilder()
        
        shell = swt.shell ( 'The TableTree Test1', location:[100,100], size:[700,600] ) {
         	gridLayout(numColumns:3) 
         	
         	tableTree( toolTipText:"This is a table tree!", style:"multi, full_selection" ) {  
			
				gridData( style:"fill_both" ) 
				
				tableTreeItem ( "root1" )  {
						tableTreeItem ( "child 1-1" )  
						tableTreeItem ( "child 1-2" )  								
						tableTreeItem ( "child 1-3" )  								
				}

				tableTreeItem ( "root2" )  {
						tableTreeItem ( "child 2-1" )  
						tableTreeItem ( "child 2-2" )  								
						tableTreeItem ( "child 2-3" )  								
				}
				
			}
		}
        
		shell.doMainloop()
	}

    public static void main(String[] args) {
    	new TableTreeDemo().run();
    }
    
}
