package groovy.swt.examples

import groovy.swt.SwtBuilder
//import groovy.jface.examples.AnotherWizardDemo
//import groovy.jface.examples.SimpleWizardDemo

/**
 *
 * @author Alexander Becher
 */
class SWTDemo {

	def mSwtBuilder = new SwtBuilder()
	
	public static void main(String[] args) {
		def demo = new SWTDemo()
		demo.run()
	}
  
  void run() {
	  println "SWT Demo Application"
/* TODO: Make maven compile this 	  
	  def shell = mSwtBuilder.shell("The SWT Builder Demo", location:[100,100], size:[300,300] ) {
		gridLayout(numColumns:2)
		label("Swt Demo", layoutData:gridData(horizontalSpan:2))
		def wizardButton = button("Start wizard") {
			onEvent('Selection') { 
				showWizard() 
			}
		}
		def simpleWizardButton = button("Start simple wizard") {
			onEvent('Selection') { 
				showSimpleWizard() 
			}
		}
		
		def textEditorDemoButton = button("Start TextEditorDemo") {
			onEvent('Selection'){ 
				showTextEditorDemo() 
			}
		}
		button("Button with border", style:"BORDER", layoutData:gridData(horizontalSpan:2))
		
	  }
		shell.open()

		while (!shell.disposed) {
		    if (!shell.display.readAndDispatch()) {
		    	shell.display.sleep()
		    }
		}
		*/

  }  
  	void showWizard(){
  		//def demo = new AnotherWizardDemo()
        demo.run()
  	}
  	
  	void showSimpleWizard(){
  		//def demo = new SimpleWizardDemo()
        demo.run()
  	}
  	
  	void showTextEditorDemo() {
  		//def demo = new TextEditorDemo()
  		demo.run()
  	}

}