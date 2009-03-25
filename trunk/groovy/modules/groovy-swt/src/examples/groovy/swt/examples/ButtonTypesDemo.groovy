package groovy.swt.examples

import groovy.swt.SwtBuilder
import org.eclipse.swt.layout.GridData
    

public class ButtonTypesDemo{
	
    def swt = new SwtBuilder()
    
	public static void main(String[] args) {
    	def demo = new ButtonTypesDemo()
    	demo.run()
    }
    
    def run(){
        
        def shell = swt.shell ('The Button types Demo', size:[700,700] ) {
        	rowLayout()
        	button("Check 1", style:"check")
        	button("Push 1")
        	button("Radio 1", style:"radio")
        	button("Push 2")
		}
    	
		shell.doMainloop()
    }
}