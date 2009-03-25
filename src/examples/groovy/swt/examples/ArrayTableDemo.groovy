package groovy.swt.examples

import groovy.swt.SwtBuilder
import groovy.swt.examples.DemoLauncher
import org.eclipse.swt.layout.GridData
    

public class ArrayTableDemo extends DemoLauncher {
	
    def swt = new SwtBuilder()
    
	public static void main(String[] args) {
    	def demo = new ArrayTableDemo()
    	demo.run()
    }
    
    def run(){
        
        def shell = swt.shell ('The ArrayTable Demo', location:[100,100], size:[700,700] ) {
    	rowLayout()
		def String[] cn = ["one","two","three","four"]
    	
    	def Object[][] testdata = new Object[20][]
		(0..<20).each{row->
			def Object[] rowcontent = new Object[4]
			(0..<4).each{col->
				rowcontent[col] = row+col
			}
			testdata[row] =rowcontent
    	}
    	
		def String[][]  data = [["fsgsdf","dfdf","dfdfddfdfdfdfdfdfdfdfdfdf","sfdfd"],[ "sdsd","sdsd","sdsdsdsd","rt"],["wew"],["sdsd","sdsd"]]
		def int[] widthdata = [100,200,100,100]		
		def int[] width = [150]
		label("ArrayTable with width=150")
		def table= arrayTable(data:testdata, columnNames:cn, style:"MULTI , BORDER , FULL_SELECTION, H_SCROLL, V_SCROLL", width:width)
		label("ArrayTable with different widths for each column")
		def table2 = arrayTable(data:data, columnNames:cn, style:"MULTI , BORDER , FULL_SELECTION", width:widthdata)

           
		}
		shell.doMainloop()
    }
}