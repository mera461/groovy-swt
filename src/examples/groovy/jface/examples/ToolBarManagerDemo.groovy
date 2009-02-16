package groovy.jface.examples

import groovy.jface.JFaceBuilder;

/**
 * @author Alexander Becher
 */
class ToolBarManagerDemo {

	def jface = new JFaceBuilder()
	
	public static void main(String[] args) {
		def demo = new ToolBarManagerDemo()
		demo.run()
	}
	
	void run() {
		def mainapp = jface.applicationWindow( size:[300,200], location:[100,100]){
			fillLayout()
			def tb = toolBarManager() {
				action ( "&Open file", closure:{ println "Open file pressed" } )
				action ( "&C", closure:{ println "C pressed" } )
				action ( "&A", closure:{ println "A pressed" } )
				separator()
				action ( "&Z", closure:{ println "Z pressed" } )
				action ( "&Y", closure:{ println "Y pressed" } )
				separator()
				def act1 = action ( closure:{ println "last one" } )
				/*
				{
					image(src:"icon.jpg")
				}
				*/
			}
		}
		mainapp.getShell().layout()
		mainapp.open()
	}
}