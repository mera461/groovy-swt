package groovy.swt.examples

import groovy.swt.SwtBuilder
import org.eclipse.swt.layout.GridData

/**
*
* @author Alexander Becher
*/
class CLabelDemo {
	def swt = new SwtBuilder()
	
	public static void main(String[] args) {
		def demo = new CLabelDemo()
		demo.run()
	}
	
	void run(){
		
		def shell = swt.shell("cLabel Demo", location:[100,100], size:[300,150]) {
			gridLayout(numColumns:1)
			cLabel(background: "#fff777", "the quick brown fox jumps over the lazy dog the quick brown fox jumps over the lazy dog ", layoutData:gridData(horizontalAlignment:GridData.FILL , grabExcessHorizontalSpace:true))
		}
		
		shell.doMainloop()
	}

}