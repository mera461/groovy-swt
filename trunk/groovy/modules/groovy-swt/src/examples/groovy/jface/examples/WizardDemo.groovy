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
		
  			wizardDialog1 = wizardDialog() {
  			
				wizardPage1 = wizardPage( title:"Step 1", description:"Step 1", closure: { parent ->
					jface.composite( parent ) {
						gridLayout( numColumns:2 )
						label( text:"Some input" )

						text = text()
						label( text:"Label 1" )
						
						button( text:"Set Message") {
							onEvent( type:"Selection" , closure: { 
								wizardPage1.setMessage( text.getText() ) 								
							})
						}	
	
						label( text:"Label 2" )
						button( text:"Set Error Message" ) {
							onEvent( type:"Selection", closure: {
								wizardPage1.setErrorMessage('ErrorMessage: This step is not complete') 
							})
						}
						
						label( text:"Label 3" )
						button ( text:"Set Page Complete" ) {
							onEvent ( type:"Selection", closure: {
								wizardPage1.setPageComplete(true)
							})
						}
					} 
				})
				
				wizardPage2 = wizardPage( title:"Step 2", description:"Step 2", closure: { parent ->
					jface.composite( parent ) {
						gridLayout( numColumns:"2" )
						label( text:"Label 3" )
						button( text:"Do nothing" )
					}
				})
							
				wizardPage3 = wizardPage( title:"Step 3", description:"Step 3", closure: { parent ->
					jface.composite( parent ) {
						gridLayout( numColumns:"2" )
						label( text:"Label 4" )
						button( text:"Do nothing" )
					}
				})	
			} 
				
  			wizardDialog1.getWizard().setClosure(
  					{action-> 
  						action.equals("FINISH") ? onPerformFinish() : onPerformCancel();
  					})
  			wizardDialog1.open()
			wizardPage1.setPageComplete(false)
		}
	}
	
	void onPerformFinish() {
		println "The real onPerformFinish called ..."
	}	 

	void onPerformCancel() {
		println "The real onPerformCancel called ......"
	}

    public static void main(String[] args) {
    	new WizardDemo().run();
    }
    
}





