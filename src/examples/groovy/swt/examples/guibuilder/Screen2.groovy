package groovy.swt.examples.guibuilder

class Screen2  {
    
    def guiBuilder
    def parent
    
    def main( args ) {
		guiBuilder.composite(parent) {
			fillLayout( type:"vertical")
			group( "This is Screen2.groovy", background:[255, 255, 255] ) {
				fillLayout( type:"vertical")
		 		label( "A big red label", background:[204, 0, 0] ) 
				label( "I can barelly read this", foreground:[0,200,0] )  
				label( "It sure looks like the dutch flag", foreground:[0,0,150], background:[0, 0, 153] )
			}
		}
    }
	
}
