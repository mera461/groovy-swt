package groovy.jface

import groovy.jface.JFaceBuilder
import junit.framework.TestCase
import org.eclipse.core.runtime.IStatus 
import org.eclipse.core.runtime.Status

class JFaceBuilderTest extends TestCase {
    def jface = new JFaceBuilder()
    
    void testAllWidgets() {
               
        jface.applicationWindow() {
        	toolBarManager()
        
        	// Viewers
        	tableViewer() {
        		doubleClickListener()
       			selectionChangedListener()
        	}
        	
        	table {
				tableViewer()
        	}
        	
        	tableTreeViewer()
        	tableTree {
        		tableTreeViewer()
        	}
        	
        	treeViewer()
        	tree {
        		treeViewer()
        	}	
        	
        	checkboxTreeViewer()
        	tree {
	        	checkboxTreeViewer()	
        	}

        	// ContributionManager 
        	menuManager( text:"menuManager" )
        	
        	// Action tags
        	action()

        	// ContributionItem 
        	separator()

        	// Wizard 
        	wizardDialog(){
        		wizardPage( title:"title" )
        	}

        	// Preferences
        	preferenceDialog(  ) {
        		preferencePage( filename:"src/test/groovy/jface/test.properties", title:"myprefs" ) {
	        		booleanFieldEditor( propertyName:"prop", title:"none" )
    	    		colorFieldEditor( propertyName:"prop", title:"none" )
   					directoryFieldEditor( propertyName:"prop", title:"none" )
	        		fileFieldEditor( propertyName:"prop", title:"none" )
	        		fontFieldEditor( propertyName:"prop", title:"none" )
    	    		integerFieldEditor( propertyName:"prop", title:"none" )
    	    		stringFieldEditor( propertyName:"prop", title:"none" )
    	    		radioGroupFieldEditor( propertyName:"prop", title:"none", numColumns:2, labelAndValues: [["label1", "value1"], ["label2", "value2"]] )
    	    	}
    	    }

        	image( src:"src/test/groovy/swt/groovy-logo.png" )
        }
	}
    
    void testDialogs() {
    	def shell = jface.shell()
    	jface.inputDialog(shell, title:'test')
    	jface.inputDialog(shell, title:'test', message:'Enter at least 5 chars', value: 'XXXXX',
    				      validator: {(it.size()>5) ? null : "At least 5 chars."})
    	def i1=jface.inputDialog(shell, title:"test${2+2}")
    	// FAILS: jface.errorDialog(shell, title:'test', message:'no message')
    	jface.errorDialog(shell, title:'test', message:'no message', status: new Status(IStatus.ERROR, 'org.eclipse', 'Something went wrong'))
    	jface.messageDialog(shell, title:'test', message:'some message')
    	jface.messageDialog(shell, title:'test', message:'some message', imageType: 1)
    	jface.messageDialog(shell, title:'test', message:'some message', imageType: 'ERROR')
    	jface.messageDialog(shell, title:'test', message:'some message', imageType: 'information')
    	jface.messageDialog(shell, title:'test', message:'some message', imageType: 'information',
    						buttonLabels: [])
    	jface.messageDialog(shell, title:'test', message:'some message', imageType: 'information',
    	    				buttonLabels: ['OK', 'CANCEL', "test${2+2}"], defaultIndex: 1)
    	jface.messageDialogWithToggle(shell, title:'test', message:'some message', imageType: 'information',
    	    	    				  buttonLabels: ['OK', 'CANCEL', "test${2+2}"], defaultIndex: 1,
    	    	    				  toggleMessage: 'message', toggleState: true)
    	jface.messageDialogWithToggle(shell, title:'test', message:'some message', imageType: 'information',
    	      	    	    				  buttonLabels: ['OK', 'CANCEL', "test${2+2}"], defaultIndex: 1,
    	      	    	    				  toggleMessage: 'message')
    }
}


