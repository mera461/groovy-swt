package groovy.swt.examples.guibuilder

class Screen1 {
    
    def guiBuilder
    def parent
    
    def main( args ) {
		guiBuilder.composite(parent) {
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
