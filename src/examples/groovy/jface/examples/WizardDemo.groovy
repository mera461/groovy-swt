package groovy.jface.examples 

import groovy.jface.JFaceBuilder

class WizardDemo {
    
    def jface
    def mainapp
    def text    
    def wizardDialog1
    def wizardPage1
    def wizardPage2
    def wizardPage3
    
    def run() {
		jface = new JFaceBuilder()

		mainapp = jface.applicationWindow() { 	
		
  			wizardDialog1 = wizardDialog(performFinish: {return onPerformFinish()},
  										 performCancel: {return onPerformCancel()}) {
  			
				wizardPage1 = wizardPage( title:"Step 1", description:"Step 1", closure: { parent ->
					jface.composite( parent ) {
						gridLayout( numColumns:2 )
						label( "Some input" )

						text = text()
						label( "Label 1" )
						
						button( "Set Message") {
							onEvent('Selection') { 
								wizardPage1.setMessage( text.getText() ) 								
							}
						}	
	
						label( "Label 2" )
						button( "Set Error Message" ) {
							onEvent('Selection') {
								wizardPage1.setErrorMessage('ErrorMessage: This step is not complete') 
							}
						}
						
						label( "Label 3" )
						button ( "Set Page Complete" ) {
							onEvent ('Selection') {
								wizardPage1.setPageComplete(true)
							}
						}
					} 
				})
				
				wizardPage2 = wizardPage( title:"Step 2", description:"Step 2", closure: { parent ->
					jface.composite( parent ) {
						gridLayout( numColumns:"2" )
						label( "Label 3" )
						button( "Do nothing" )
					}
				})
							
				wizardPage3 = wizardPage( title:"Step 3", description:"Step 3", closure: { parent ->
					jface.composite( parent ) {
						gridLayout( numColumns:"2" )
						label( "Label 4" )
						button( "Do nothing" )
					}
				})	
			} 
				
//  			wizardDialog1.getWizard().setClosure(
//  					{action-> 
//  						action.equals("FINISH") ? onPerformFinish() : onPerformCancel();
//  					})
  			wizardDialog1.open()
			wizardPage1.setPageComplete(false)
		}
	}
	
	boolean onPerformFinish() {
		println "The real onPerformFinish called ..."
		return true;
	}	 

	boolean onPerformCancel() {
		println "The real onPerformCancel called. Try again....."
		return false;
	}

    public static void main(String[] args) {
    	new WizardDemo().run();
    }
    
}





