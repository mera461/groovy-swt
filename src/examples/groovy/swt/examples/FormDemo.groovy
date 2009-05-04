package groovy.swt.examples

import groovy.jface.JFaceBuilder

class FormDemo {
    
    def mainapp
    def jface
    def htmlText
    
    def run() {
        jface = new JFaceBuilder()

        mainapp = jface.applicationWindow( title:"The forms demo", size:[700,800], location:[0,0] ) { 	
         	gridLayout ( numColumns:2 )
			form( "Hello, Eclipse Forms" ) {
				gridData( style:"fill_both" )
				tableWrapLayout()

				formSection( "section1", description:"description of section1", style:"description, twistie" ) {
					tableWrapData( style:"fill" )
					
					expansionListener( type:"expansionStateChanging", closure: { println "expansionStateChanging ... " + it } )
					expansionListener( type:"expansionStateChanged", closure: { println "expansionStateChanged ... " + it } )
					
					htmlText = "<form>"
					htmlText += "<li>list item</li>"
					htmlText += "<p>this html code with an url: http://groovy.codehaus.org</p>"
					htmlText += "<li style=\"text\" value=\"1.\">list item 2</li>"
			        htmlText += "<li style=\"text\" value=\"2.\">list item 3</li>"
			        htmlText += "</form>"
					
					formFormattedText( htmlText, parseTags:true, expandURLs:true )					
					
					formButton ( "This is radiobutton1", style:"radio" ) 
					formButton ( "This is radiobutton2", style:"radio" ) 
					
					formButton ( "This is a ARROW button", style:"arrow" ) {
						onEvent('Selection'){ println "stop selecting me !!!" } 
					}	
					formButton ( "This is a PUSH button", style:"push" ) {
	        			onEvent('Selection'){ println "stop pushing me !!!" } 
	        		}
					formButton ( "This is a TOGGLE button", style:"TOGGLE" ) {
						onEvent('Selection'){ println "TOGGLE" } 
					}
					
				}
				
				formSection( "section2", description:"description of section2", style:"description, twistie" ) {
					tableWrapData( style:"fill" )
					formLabel( "This is a label in section 2" )
					formExpandableComposite( "formExpandableComposite" )
				}

				formSection( "section3", description:"description of section3", style:"description, twistie" ) {
					tableWrapData( style:"fill" )
					formLabel( "Below me is a tree" )
	       			formTree()
				}
				
				formSeparator( style:"separator, horizontal" ) {
					tableWrapData( style:"fill" )
				}
				
				formButton( "This is a formButton" )
				
       			formCompositeSeparator()
								
				formHyperlink( "this is a hyperlink" ) {
					hyperlinkListener( type:"hyperlinkUpdate", closure: { println "hyperlinkUpdate ... " + it } )
					hyperlinkListener( type:"linkEntered", closure: { println "linkEntered ... " + it } )
					hyperlinkListener( type:"linkExited", closure: { println "linkExited ... " + it } )
					hyperlinkListener( type:"linkActivated", closure: { println "linkActivated ... " + it } )
				}
       			
       			formLabel( "This is a formLabel, folowed by a formTable" )
       			formTable() {
	       			tableWrapData( style:"fill" )
       			}
     
     			
       			
       			// NOT FULLY IMPLEMENTED YET:
       			// formImageHyperlink( "formImageHyperlink" )
       			// formPageBook( "formPageBook" )
       			
			}
			
			form( "hello formScrolledForm" ) {
				gridData( style:"fill_both" )
				formLabel( "my parent is a scrolledForm" )
				formButton( "formButton" )
			}
		}
		
		mainapp.getShell().pack()
		mainapp.open()
	}

    public static void main(String[] args) {
    	new FormDemo().run();
    }
    

}
