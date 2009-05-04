package groovy.jface.examples

import groovy.jface.JFaceBuilder 
import org.eclipse.swt.layout.GridData

/**
 * @author Alexander Becher
 */
class AnotherWizardDemo {

	def jface = new JFaceBuilder()
	def wizardPage2
	
  public static void main(String[] args) {
    def demo = new AnotherWizardDemo()
    demo.run()
  }
  
	void run() {
		def dates =[ "1", "2", "3", "4", "5", "6", "7", "8", "9",
		     		"10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
		     		"21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"]
		         
		def  months= ["January", "February", "March", "April", "May",
		     		"June", "July", "August", "September", "October", "November", "December" ]
		// lets create a window
		def mainapp = jface.window() {
			// and now the wizard dialog
	  		def wizardDialog = wizardDialog("Groovy Wizard Demo", 
	  				performFinish:{ println "Finished was pressed"; return true}, 
	  				performCancel:{ println "Cancel method"; return true} ) 
	  		{
				// lets add a picture to the wizard's title
				// TODO: Make the image work.
				image(src:"groovy.gif")
				// first page for the wizard
		  		def wizardPage1 = wizardPage( title:"Step 1", description:"This is the first page", 
		  				nextPressed:{ println "Button Next on first Page" }, closure: 
		  		{ 
					
	  				jface.composite(it) {
						gridLayout( numColumns:4 )
					     
					    // Date of travel
					  	def Calendar cal = Calendar.getInstance()
					  	cal.setTime(new Date())
				        def day = cal.get(Calendar.DAY_OF_MONTH)
						label ("Travel on:", layoutData:gridData(horizontalAlignment:GridData.BEGINNING))
					    def travelDate = combo( style:"READ_ONLY", items:dates, visibleItemCount:10, layoutData:gridData(horizontalAlignment:GridData.FILL, widthHint:25))
					    travelDate.text=day
						def travelMonth = combo(  style:"READ_ONLY", items: months, layoutData:gridData(horizontalAlignment:GridData.FILL))
						travelMonth.text = travelMonth.items[cal.get(Calendar.MONTH)]
						// Calculate years 	
						def startyear = cal.get(Calendar.YEAR);
						def years = []
						years.add(startyear)
						(1..<3).each {
							years.add(startyear + it)
						}
						
						def travelYear = combo( style:"READ_ONLY", items: years, layoutData:gridData(horizontalAlignment:GridData.FILL))
					   	travelYear.text = startyear
						
						// Date of return		
						label (style:"NONE", "Return on:", layoutData:gridData(horizontalAlignment:GridData.FILL))

						combo(style:"READ_ONLY", items: dates, layoutData:gridData(horizontalAlignment:GridData.FILL, widthHint:25))
						combo( style:"READ_ONLY", items:months, layoutData:gridData(horizontalAlignment:GridData.FILL))
						combo(style:"READ_ONLY", items:years, layoutData:gridData(horizontalAlignment:GridData.FILL))
						
						// Departure				
						label (style:"NONE", "From:", layoutData:gridData(horizontalAlignment:GridData.FILL))			
						text( style:"BORDER", layoutData:gridData(horizontalAlignment:GridData.FILL, horizontalSpan:3));

						// Destination
						label (style:"NONE", "To:", layoutData:gridData(horizontalAlignment:GridData.FILL))				
						text( style:"BORDER", layoutData:gridData(horizontalAlignment:GridData.FILL, horizontalSpan:3))
						
						radioButton("Take a plane", layoutData:gridData(horizontalAlignment:GridData.FILL, horizontalSpan:3))
						radioButton("Take the car", layoutData:gridData(horizontalAlignment:GridData.FILL, horizontalSpan:3))
						line (layoutData:gridData(horizontalAlignment:GridData.FILL, horizontalSpan:3))
					}
	  			})
	  			// second page for the wizard
	  			wizardPage2 = wizardPage( title:"Step 2", description:"This is the second page", 
	  					backPressed:{println "back on 2nd page"},
	  					closure: { 
						jface.composite( it ) {
							gridLayout( numColumns:"2" )
							label( "Set PageComplete to true/false: " )
							    def button2 = button("Change it") {
        							onEvent('Selection'){ 
							        	wizardPage2.setPageComplete(!wizardPage2.isPageComplete() )
							        }
							    }
						}
				})
				def wizardPage3 = wizardPage( title:"Step 3", description:"This is the third page", closure: { 
						jface.composite( it ) {
							gridLayout( numColumns:"2" )
							label("Label 3" )
							button("Do nothing" )
						}
				})
					
	  		}
	  		wizardPage2.setPageComplete(false)
	  		wizardDialog.open()
		}		
	}

}