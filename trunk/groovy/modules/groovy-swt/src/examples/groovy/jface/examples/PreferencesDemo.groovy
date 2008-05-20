package groovy.jface.examples

import groovy.jface.JFaceBuilder

class PreferencesDemo {
    
    def jface
    def mainapp
    def pd
    def p1
    
    def run() {
        jface = new JFaceBuilder()
		
        mainapp = jface.applicationWindow() { 	        
			pd = preferenceDialog() {
				p1 = preferencePage( title:"General settings", filename:"settings.props" ) { 
					booleanFieldEditor (propertyName:"var1", title:"It's boolean" )
					colorFieldEditor( propertyName:"var2", title:"MainColor" )
					directoryFieldEditor(propertyName:"var3", title:"Directory"	)
					fileFieldEditor( propertyName:"var4", title:"File" )
					fontFieldEditor( propertyName:"var5", title:"Font" )
					integerFieldEditor( propertyName:"var6", title:"Integer" )
					stringFieldEditor( propertyName:"var7", title:"String" )
					radioGroupFieldEditor( propertyName:"Burger with group", title:"Burger size:", numColumns: 3, useGroup: true,
    						labelAndValues: [["Small", "0"], ["Big", "1"], ["HUGE", "2"]])
    				radioGroupFieldEditor( propertyName:"Cola without group", title:"Cola size:", numColumns: 3, useGroup: false,
    	    				labelAndValues: [["Small", "0"], ["Big", "1"], ["HUGE", "2"]])
    	    		radioGroupFieldEditor( propertyName:"shake without group", title:"Shake size:", numColumns: 2, useGroup: true,
    	    	    		labelAndValues: [["Small", "0"], ["Big", "1"], ["XL", "2"], ["HUGE", "3"]])
				} 								
				p1.noDefaultAndApplyButton()

				preferencePage( title:"Personal settings", filename:"settings.props" ) { 
					booleanFieldEditor( propertyName:"var8", title:"It's boolean" )
					colorFieldEditor( propertyName:"var2", title:"MainColor" )
					directoryFieldEditor( propertyName:"var9", title:"Directory" )
					fileFieldEditor( propertyName:"var10", title:"File" )
					fontFieldEditor( propertyName:"var11", title:"Font" )
					integerFieldEditor( propertyName:"var12", title:"Integer" )
					stringFieldEditor( propertyName:"var13", title:"String" )
				} 
			}
		}
		
	  	pd.open()
	}
  
    public static void main(String[] args) {
    	new PreferencesDemo().run();
    }
    
    
    
}
