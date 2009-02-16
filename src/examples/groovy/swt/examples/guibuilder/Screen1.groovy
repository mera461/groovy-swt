package groovy.swt.examples.guibuilder

class Screen1 {
    
    def guiBuilder
    
    def main( args ) {
		guiBuilder.composite {
			fillLayout()
			
			group( "This is Screen1.groovy" ) {
				gridLayout()
				button( "the hardest", background:[0, 255, 255] )
				button( "button", background:[0, 255, 255] )
				button( "to button", background:[0, 255, 255] )
			}
		}
	}
	
}
