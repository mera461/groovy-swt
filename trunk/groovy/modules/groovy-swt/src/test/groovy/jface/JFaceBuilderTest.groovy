package groovy.jface

import groovy.jface.JFaceBuilder
import junit.framework.TestCase;

class JFaceBuilderTest extends TestCase {
    def jface
    
    void testAllWidgets() {
        jface = new JFaceBuilder()
               
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
        	tabletree {
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
}


