/**
 * 
 */
package groovy.swt.examples.miglayout

import groovy.swt.SwtBuilder
import org.eclipse.swt.layout.GridData


/**
 * For testing a migLayout
 * See http://www.medicalgenomics.org/miglayout_sample
 * 
 * @author Frank
 *
 */
public class Layout1 {

	def swt = new SwtBuilder()
	
    public static void main(String[] args) {
		def demo = new Layout1()
		demo.run()
	}
	
	void run(){
		
		def shell = swt.shell("migLayout example", size:[450,250]) {
			migLayout(layoutConstraints:"", columnConstraints: "[right]10[left, grow]", rowConstraints: "30")
			label("DB type")
			label("Derby/JavaDB", layoutData:"wrap")
			label("DB name")
			combo(layoutData:"growx, wrap")
			label("Host")
			text(layoutData: "growx, wrap")
			label("Port")
			text(layoutData:"width 100!, split")
			label("(Default port 1527)", layoutData:"gapleft 10, wrap")
			label("Username")
			text(layoutData: "width 200!, wrap")
			label("Password")
			text(layoutData: "width 200!, split")
			button("Remember password", style: "Check", layoutData: "gapleft 10, wrap")
			label("Table prefix")
			text(layoutData:"width 200!, wrap")
		}
		
		shell.doMainloop()
	}

}
