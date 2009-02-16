package groovy.jface.examples

import groovy.jface.JFaceBuilder 
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.widgets.Display;

/**
 * @author Alexander Becher
 */
class MenuManagerDemo {

	def mainapp
	def jface = new JFaceBuilder()
	def act1, label1
	
	public static void main(String[] args) {
		def demo = new MenuManagerDemo()
		demo.run()
	}

  	void run(){
  		
  		mainapp = jface.applicationWindow() { 
  			
         	menuManager( "&File" ) {
         		action ( "&Open file", closure:{ println "Open file pressed" } )
         		separator()
         		action ( "E&xit", closure:{ mainapp.close() } )
         	}

         	menuManager( "&Color" ) {
         		act1 = action ( "Enable blue", checked:false, closure:{if (act1.checked)label1.background=new Color(Display.getDefault( ), 0,0,255); else label1.background=new Color(Display.getDefault( ), 255,0,0)} )
         	}
       
			fillLayout ( type:"vertical" )

			label1 = label( "A label", background:[255, 0, 0] ) {
				image(src:"icon.gif")
			}
		}

  		mainapp.open()
		
  	}
  	
}