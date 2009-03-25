package groovy.swt.examples

import groovy.swt.SwtBuilder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

/**
* 
* @author Alexander Becher
*
**/
class TextEditorDemo {

	def swt = new SwtBuilder()
	
	public static void main(String[] args) {
		def demo = new TextEditorDemo()
		demo.run()
	}


	void run(){
		def haschanged = false
		def shell
		def textarea
		def shelltitle= "Simple texteditor"
		shell = swt.shell(shelltitle, size:(new org.eclipse.swt.graphics.Point(393, 279)) ) {
			
		    def gridLayout2 = gridLayout(numColumns:3, makeColumnsEqualWidth:false)
		    
		    	textarea = textArea(layoutData:gridData(horizontalSpan:3, horizontalAlignment:GridData.FILL, verticalAlignment:GridData.FILL, grabExcessHorizontalSpace:true, grabExcessVerticalSpace:true)) {
		    	onEvent(type:"Modify", closure:{
		    		if (!haschanged){
		    			//println "changed"
		    			shell.text = "$shell.text *"   			
		    			haschanged = true;
		    		}
		    	})
		    } 
		    def button1 = button(style:"push", 'Load', layoutData:gridData(horizontalAlignment:GridData.END, verticalAlignment:GridData.CENTER, grabExcessHorizontalSpace:true)) {
		        onEvent(type:"Selection", closure:{ 
		        	def result = ""
		        	def fileshell = swt.shell(){
		        		def dialog = fileDialog( style:"OPEN")
		        		result = dialog.open()
		        	}
		        	if (result != null) {
			        	def file = new File(result)
					    if (file.exists()){
					    	textarea.text = file.text
					    	shell.text = "$shelltitle [$result]"
							haschanged = false;
					    }		        		
		        	}
		        })
		    }
		    def button2 = button(style:"push", 'Save', layoutData:gridData(horizontalAlignment:GridData.CENTER, verticalAlignment:GridData.CENTER)) {
		        onEvent(type:"Selection", closure:{ 
		        	def result = ""
		        	def fileshell = swt.shell(){
		        		def dialog = fileDialog( style:"SAVE")
		        		result = dialog.open()
		        	}
		        	if (result != null){
		        		def file = new File(result)
		        		def message_result
						if (file.exists()){
							def messageshell = swt.shell(){
				        		def dialog = messageBox("File already exists!", message:"Do you wish to overwrite?", style:"YES, NO, ICON_WARNING")
				        		message_result = dialog.open()
				        	}
						}	
						if (message_result != SWT.NO){
							try {
								file.write(textarea.text)
								shell.text = "$shelltitle [$result]"
								haschanged = false;
							} catch (FileNotFoundException e1) {
								swt.shell(){
					        		messageBox("Error", message:e1, style:"OK, ICON_ERROR").open()
					        	}
							} 
						}
		        	}
		        })
		    }
		    def button3 = button('Exit', layoutData:gridData(grabExcessHorizontalSpace:true)) {
		        onEvent(type:"Selection", closure:{ 
		        	doExit(shell)
		        })
		    }
		    
		}
		shell.doMainloop()
	}
	
	void doExit(context) {
		context.dispose()
	}
}