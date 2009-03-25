package groovy.swt.examples

import groovy.swt.SwtBuilder

class BrowserSwtDemo extends Script {
    
    def swt
    def shell
    def browser
    def location
    def status
    def progressBar
        
    def run() {
        swt = new SwtBuilder()
        
        shell = swt.shell( "The Browser Test", location:[100,100], size:[700,600] ) {
         	gridLayout(numColumns:3) 

        	toolBar( style:"none" ) {
        		gridData( horizontalSpan:3 )
        	
        		toolItem(style:"push", "Back") {
        			onEvent(type:"Selection", closure:{ browser.back() }) 
        		}
        		
        		toolItem(style:"push", "Forward") {
        			onEvent(type:"Selection", closure:{ browser.forward() } ) 
        		}
        		
        		toolItem(style:"push", "Stop") {	
        			onEvent(type:"Selection", closure:{ browser.stop() } )
        		}
        		
        		toolItem(style:"push", "Refresh") { 
        			onEvent(type:"Selection", closure:{ browser.refresh() } )
        		}
        		
        		toolItem(style:"push", "Go") {
        			onEvent(type:"Selection", closure:{ 
        				browser.setUrl( location.getText() ) 
        			})
        		}
        	}

        	label( style:"none", "Address" )
        	location = text( style:"border" ) {  
	        	gridData( style:"fill_horizontal", horizontalSpan:2, grabExcessHorizontalSpace:true )
	        	onEvent(type:"DefaultSelection", closure: {
	        		browser.setUrl( location.getText() ) 
	        		})
        	}


			browser = browser( style:"border" ) {
				gridData( horizontalSpan:3, style:"fill_both", grabExcessHorizontalSpace:true )

 				locationListener(type:"changed", closure: { event ->
					location.setText( event.location )
				})
	 	
				progressListener(type:"changed", closure: { event ->
					if (event.total != 0) {
						ratio = event.current * 100 / event.total
						progressBar.setSelection( 
							Integer.parseInt("" + Math.round(Double.parseDouble("" + ratio))) )
					}
				})
			
				progressListener(type:"completed", closure: { 
					progressBar.setSelection(0)
				})
		
				statusTextListener( closure: { event ->
					status.setText(event.text)	
				})
				
			}
			browser.setUrl( "http://feeds.codehaus.org/" )
			status = label( style:"none", "" ) {
				gridData( style:"fill_horizontal", horizontalSpan:2) 
			}
		
			progressBar = progressBar () {
				gridData( horizontalAlignment:"end" )
			}
				
        }
        
		shell.doMainloop()
	}

    public static void main(String[] args) {
    	new BrowserSwtDemo().run();
    }
    


}
