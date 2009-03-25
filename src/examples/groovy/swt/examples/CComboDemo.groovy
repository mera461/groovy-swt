package groovy.swt.examples

import groovy.swt.SwtBuilder
import org.eclipse.swt.layout.GridData

/**
*
* @author Alexander Becher
*/
class CComboDemo {

	def swt = new SwtBuilder()
	def shell
	def button1
	
	public static void main(String[] args) {
		def demo = new CComboDemo()
		demo.run()
	}
  
  	void run(){
  		
  		shell = swt.shell("cCombo Demo", location:[0,0], size:[390,150]) {
  			rowLayout()
  			cCombo(style:"BORDER", items:["1 Item one", "2 Item two", "3 Item three"])
  			combo(  items:["1 Item one", "2 Item two", "3 Item three"], visibleItemCount:10)
  	  		button1 = button('Open') {
  		        onEvent(type:"Selection", closure:{ 
  		        	println "button"
  		        })
  		    }
			onEvent(type:"MouseDown", closure:{event->
		  	        println "event"
			})
  		}
		shell.doMainloop()
  }

}