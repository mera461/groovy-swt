package groovy.swt.examples

import groovy.swt.SwtBuilder
import org.eclipse.swt.layout.GridData
    

public class ThreadingDemo{
	
    def swt = new SwtBuilder()
    def counterLabel
    def counter = 0
    
	public static void main(String[] args) {
    	def demo = new ThreadingDemo()
    	demo.run()
    }
    
    def run(){
        
        def shell = swt.shell ('ThreadingDemo', size:[100,100] ) {
        	rowLayout()
        	counterLabel = label('Starting ....')
        }
        
        Thread.start {
        	Thread.sleep(3000)
        	while (true) {
        		Thread.sleep(1000)
        		counter++
        		SwtBuilder.asyncExec {
        			counterLabel.text = counter
        		}
        	}
        }
    	
		shell.doMainloop()
    }
}