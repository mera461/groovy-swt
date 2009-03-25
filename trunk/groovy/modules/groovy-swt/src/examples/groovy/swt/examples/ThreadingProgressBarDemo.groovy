package groovy.swt.examples

import groovy.swt.SwtBuilder
import org.eclipse.swt.layout.GridData
    

public class ThreadingProgressBarDemo{
	
    def swt = new SwtBuilder()
    def counterLabel
    def counter = 0
    
	public static void main(String[] args) {
    	def demo = new ThreadingProgressBarDemo()
    	demo.run()
    }
    
    def run(){

    	def bar = null
        def shell = swt.shell ('ThreadingDemo', size:[150,100] ) {
        	migLayout(columnConstraints:"[right][grow]")
        	label("Updating:")
        	bar = progressBar(style:"smooth", bounds:[10, 10, 200, 32])
        }
    	
    	final int maximum = bar.maximum
        
        Thread.start {
			for (final int[] i = new int[1]; i[0] <= maximum; i[0]++) {
				Thread.sleep(100)
				swt.asyncExec {
					if (! bar.isDisposed ()) bar.setSelection(i[0])
				}
			}
        }
    	
		shell.doMainloop()
    }
}