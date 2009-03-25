package groovy.swt.examples

import groovy.swt.SwtBuilder
import org.eclipse.swt.layout.GridData

/**
*
* @author Alexander Becher
*/
class CBannerDemo {

	def swt = new SwtBuilder()
	
	public static void main(String[] args) {
		def demo = new CBannerDemo()
		demo.run()
	}
	
	void run() {
		def shell = swt.shell("cBanner Demo", location:[100,100], size:[390,150]) {
			 
			gridLayout(numColumns:1)
			def  b2, t1
			label("Underneath is a cBanner with a ToolBar on the left and a button on the right\nYou can resize by dragging the separator.", layoutData:gridData(horizontalAlignment:GridData.FILL , grabExcessHorizontalSpace:true))
			def cb = cBanner( layoutData:gridData(horizontalAlignment:GridData.FILL , grabExcessHorizontalSpace:true)){
				t1 = toolBar(style:"NONE"){
					toolItem("Button1")
					toolItem("Button2", style:"PUSH")
					toolItem("Checkit", style:"CHECK")
					toolItem("Radio1", style:"RADIO")
					toolItem("Radio2", style:"RADIO")
				}
				b2=button("ok2")
			}
			cb.setLeft(t1)
			cb.setRight(b2)
		}
		
		shell.doMainloop()
	}

}