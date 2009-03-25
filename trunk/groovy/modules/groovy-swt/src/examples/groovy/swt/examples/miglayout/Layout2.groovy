/**
 * 
 */
package groovy.swt.examples.miglayout

import groovy.swt.SwtBuilder
import org.eclipse.swt.layout.GridData


/**
 * For testing a migLayout
 * See http://www.devx.com/Java/Article/38017/0/page/2
 * 
 * @author Frank
 *
 */
public class Layout2 {

	def swt = new SwtBuilder()
	
    public static void main(String[] args) {
		def demo = new Layout2()
		demo.run()
	}
	
	void run(){
		
		def shell = swt.shell("migLayout example 2", size:[350,200]) {
			migLayout(layoutConstraints:"", columnConstraints: "[right]")
			label("General", layoutData:"split, span, gaptop 10")
			line(layoutData: "growx, wrap, gaptop 10")
			
			label("Company", layoutData:"gap 10")
			text(layoutData:"span, growx")
			label("Contact", layoutData:"gap 10")
			text(layoutData:"span, growx, wrap")
			
			label("Propeller", layoutData:"split, span, gaptop 10")
			line(layoutData: "growx, wrap, gaptop 10")

			
			label("PTI/kW", layoutData:"gap 10")
			text()
			label("Power/kW", layoutData:"gap 10")
			text(layoutData: "wrap")
			label("R/mm", layoutData:"gap 10")
			text()
			label("D/mm", layoutData:"gap 10")
			text()
		}
		
		shell.doMainloop()
	}

}
