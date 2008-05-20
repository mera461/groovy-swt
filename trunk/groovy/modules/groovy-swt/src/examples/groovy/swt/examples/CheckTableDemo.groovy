package groovy.swt.examples

import groovy.swt.SwtBuilder
import org.eclipse.swt.SWT

/**
*
* @author Alexander Becher
*/
class CheckTableDemo {
	def swt
	def table1, label1
	
	public static void main(String[] args) {
		def demo = new CheckTableDemo()
		demo.run()
	
	}
	
	void run(){
		
		swt = new SwtBuilder()
		def shell = swt.shell(text:"Table Demo", size:[280,280]){
			label1 = label(text:"", style:"CENTER")
			table1 = table(linesVisible:true, headerVisible:true,style:"CHECK, BORDER, H_SCROLL,V_SCROLL,FULL_SELECTION"){
				tableColumn(text:"Column1", width:80)
			  	tableColumn(text:"Column2", width:60)
				tableColumn(text:"Column3", width:60)
				tableColumn(text:"Column4", width:60)
				def row = ["1","2","3","4"]
				tableItem(text:row)
				(0..24).each{
					tableItem(text:["Item $it", "Cont", "ent", "#$it"])	
				}
				
				onEvent(type:"Selection", closure:{
					if (it.detail == SWT.CHECK) 
						label1.text="Selected item $it.item"	
				})
			}
			label1.setBounds(15,5,250,20)
			table1.setBounds(30, 30, 215, 200)
		}
		 
		shell.open()
		while(! shell.isDisposed()) { 
			if (! shell.display.readAndDispatch()) {
				shell.display.sleep();
			}
		}

  }

}