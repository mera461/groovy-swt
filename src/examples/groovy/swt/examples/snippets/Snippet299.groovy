/**
 * 
 */
package groovy.swt.examples.snippets

import groovy.swt.SwtBuilder
import org.eclipse.swt.layout.GridData


/**
 * For testing a 3.4 new feature
 * 
 * @author Frank
 *
 */
public class Snippet299{

	def swt = new SwtBuilder()
	
    public static void main(String[] args) {
		def demo = new Snippet299()
		demo.run()
	}
	
	void run(){
		
		def shell = swt.shell("snippet299", size:[250,100]) {
			rowLayout(center:true)
			button("Button 0")
			button("Button 1") {
				rowData (height: 50)
			}
			button("Button 2") {
				rowData (height: 70)
			}
			button("Button 3")
		}
		
		shell.doMainloop()
	}

}
