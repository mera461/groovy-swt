package groovy.jface.examples

import groovy.jface.JFaceBuilder


/**
 * @author Alexander Becher
 */
class SimpleWizardDemo {

	def jface = new JFaceBuilder()
	
	public static void main(String[] args) {
		def demo = new SimpleWizardDemo()
		demo.run()
	}
	  
	void run() {
		
		def mainapp = jface.window() {
			// and now the wizard dialog
	  		def wizardDialog = wizardDialog("Wizard Demo") 
	  		{ image(src:"groovy.gif")
				// first page for the wizard
		  		def wizardPage1 = wizardPage( title:"Step 1", description:"This is the first page", closure: 
		  		{ 
	  				jface.composite(it) {
	  					gridLayout( numColumns:"2" )
					    label("label on first page")
					    button("do nothing")
					}
	  			})
	  			
	  			// second page for the wizard
	  			def wizardPage2 = wizardPage( title:"Step 2", description:"This is the second page", closure: { 
					jface.composite( it ) {
						gridLayout( numColumns:"2" )
					    label("label on second page")
					    button("do nothing")							}
				})
				
	  		}
			wizardDialog.open()
		}		
	}
}