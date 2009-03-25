/**
 * 
 */
package groovy.swt.examples.snippets

import groovy.swt.SwtBuilder
import org.eclipse.swt.layout.GridData


/**
 * For testing a 3.4 new feature: Tristate buttons
 * 
 * @author Frank
 *
 */
public class Snippet293{

	def swt = new SwtBuilder()
	
    public static void main(String[] args) {
		def demo = new Snippet293()
		demo.run()
	}
	
	void run(){
		
		def shell = swt.shell("snippet293") {
			gridLayout()
			button("State 1", style:'check', selection: true)
			button("State 2", style:'check', selection: false)
			button("State 3", style:'check', selection: true, grayed: true)
			button("State 4", style:'check', selection: true, enabled: false)
		}
		
		shell.doMainloop()
	}

}
