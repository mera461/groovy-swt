package groovy.jface.examples

import groovy.jface.JFaceBuilder 
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.widgets.Display;

/**
 * @author Frank
 */
class TableWithPopup {

	def mainapp
	def jface = new JFaceBuilder()
	def act1, label1
	
	public static void main(String[] args) {
		def demo = new TableWithPopup()
		demo.run()
	}

  	void run(){
  		
  		mainapp = jface.applicationWindow() { 
			fillLayout ( type:"vertical" )
			def mm =null
			table(linesVisible:true, headerVisible:true,
					       style:"BORDER, H_SCROLL,V_SCROLL,FULL_SELECTION"){
				tableColumn("Column1", width:80)
				(0..24).each{
					tableItem(["Item $it"])	
				}
				menuManager('popup') {
	         		action ( "&Open file",
	         				accelerator: 'Ctrl+O',
	         				closure:{ println "Open file pressed" } )
	         		separator()
	         		action ( "E&xit", closure:{ mainapp.close() } )
	         	}
			}
		}

  		mainapp.shell.pack()
  		mainapp.open()
		
  	}
  	
}