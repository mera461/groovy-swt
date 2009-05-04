package groovy.swt.examples.guibuilder

import groovy.swt.guibuilder.ApplicationGuiBuilder

class ApplicationGuiDemo {
    
    def comp
    def builder
    def mainapp
    def guiBuilder
    def comp1
    
    def main( args ) {

		mainapp = guiBuilder.applicationWindow( title:"The ApplicationGuiDemo", size:[700,400] ) { 
			fillLayout( ) 
			
			menuManager( "Screens" ) {
				action( "Screen1", closure:{ 
					guiBuilder.runScript( src:"Screen1.groovy", parent:mainapp, rebuild:true )
				})
				action( "Screen2", closure:{ 
					guiBuilder.runScript( src:"Screen2.groovy", parent:mainapp, rebuild:true )
				})
			}
				
			toolBar( style:"horizontal" ){
				toolItem( "Blue" ) {
					onEvent('Selection') {
						guiBuilder.rebuild( parent:comp1, closure:{ 
							guiBuilder.composite( it ) {
								fillLayout()
								label( "This is fresh new blue label ...", background:[0, 0, 255] )
							}
						})
					}
				}				
				
				toolItem( "Red" ) {
					onEvent('Selection') {
						guiBuilder.rebuild( parent:comp1, closure:{ 
							guiBuilder.composite( it ) {
								fillLayout()
								label( "This is fresh new red label ...", background:[255, 0, 0] )
							}
						})
					}
				}				
						
			}			
		
			composite() {
				
				fillLayout() 
				
				comp1 = composite() {
					fillLayout()
					label( "This is green label", background:[0, 255, 0]  )
				}
			}
			
		}
		
		mainapp.getMenuBarManager().updateAll(true)
		mainapp.open()
	}
}
