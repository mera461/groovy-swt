package groovy.swt.examples

import groovy.swt.SwtBuilder

class BrowserSwtDemo extends Script {
    property swt
    property shell
    property browser
    property location
    property status
    property progressBar
        
    void run() {
        swt = new SwtBuilder()
        
        shell = swt.shell( text:"The Browser Test", location:[100,100], size:[700,600] ) {
         	gridLayout(numColumns:3) 

        	toolBar( style:"none" ) {
        		gridData( horizontalSpan:3 )
        	
        		toolItem(style:"push", text:"Back") {
        			onEvent(type:"Selection", closure:{ browser.back() }) 
        		}
        		
        		toolItem(style:"push", text:"Forward") {
        			onEvent(type:"Selection", closure:{ browser.forward() } ) 
        		}
        		
        		toolItem(style:"push", text:"Stop") {	
        			onEvent(type:"Selection", closure:{ browser.stop() } )
        		}
        		
        		toolItem(style:"push", text:"Refresh") { 
        			onEvent(type:"Selection", closure:{ browser.refresh() } )
        		}
        		
        		toolItem(style:"push", text:"Go") {
        			onEvent(type:"Selection", closure:{ 
        				browser.setUrl( location.getText() ) 
        			})
        		}
        	}

        	label( style:"none", text:"Address" )
        	location = text( style:"Border" ) {  
	        	gridData( horizontalAlignment:"fill", horizontalSpan:2, grabExcessHorizontalSpace:true )
        	}


			browser = browser( style:"border" ) {
				gridData( horizontalAlignment:"fill", verticalAlignment:"fill", horizontalSpan:3, grabExcessHorizontalSpace:true, grabExcessVerticalSpace:true)

 				locationListener(type:"changed", closure: { event |
					location.setText( event.location )
				})
	 	
				progressListener(type:"changed", closure: { event |
					if (event.total != 0) {
						ratio = event.current * 100 / event.total
						progressBar.setSelection( Integer.parseInt("" + Math.round(Double.parseDouble("" + ratio))) )
					}
				})
			
				progressListener(type:"completed", closure: { 
					progressBar.setSelection(0)
				})
		
				statusTextListener( closure: { event |
					status.setText(event.text)	
				})
				
			}
		
			status = label( style:"none", text:"" ) {
				gridData( style:"fill_horizontal", horizontalSpan:2) 
			}
		
			progressBar = progressBar () {
				gridData( horizontalAlignment:"end" )
			}
				
        }
        
		browser.setUrl( "http://feeds.codehaus.org/" )
		shell.open()
	
		while(! shell.isDisposed()) { 
			if (! shell.display.readAndDispatch()) {
				shell.display.sleep();
			}
		}
			
		shell.display.dispose()	
	}
}
