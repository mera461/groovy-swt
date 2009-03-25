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
		def shell = swt.shell("Table Demo", size:[280,280]){
			label1 = label("label", style:"CENTER")
			table1 = table(linesVisible:true, headerVisible:true,style:"CHECK, BORDER, H_SCROLL,V_SCROLL,FULL_SELECTION"){
				tableColumn("Column1", width:80)
			  	tableColumn("Column2", width:60)
				tableColumn("Column3", width:60)
				tableColumn("Column4", width:60)
				// TODO: has this ever been working?
				def row = ["1","2","3","4"]
				tableItem(text:row)
				tableItem(["row 2", "3", "4", "5"])
				(0..24).each{
					tableItem(["Item $it", "Cont", "ent", "#$it"])	
				}
				
				onEvent(type:"Selection", closure:{
					if (it.detail == SWT.CHECK) 
						label1.text="Selected item $it.item"	
				})
			}
			label1.setBounds(15,5,250,20)
			table1.setBounds(30, 30, 215, 200)
		}
		 
		shell.doMainloop()
  }

}