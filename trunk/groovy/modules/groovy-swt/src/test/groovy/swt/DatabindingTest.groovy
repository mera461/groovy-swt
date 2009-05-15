/*
 * Created on Feb 25, 2004
 *
 */
package groovy.swt

import groovy.beans.Bindable
import groovy.lang.GroovyClassLoader
import groovy.lang.GroovyObject

import groovy.jface.JFaceBuilder

import java.io.File

import junit.framework.TestCase

import org.eclipse.core.databinding.observable.list.WritableList
import org.eclipse.jface.window.ApplicationWindow
import org.eclipse.swt.widgets.Shell

@Bindable
public class TestPerson {
	String name
}

/**
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 915 $
 */
public class DatabindingTest extends TestCase {
	 
	@Bindable
	String text
	@Bindable
	int number
    @Bindable
	WritableList list 
	
	def jface = new JFaceBuilder()

	/*
	 * Test basic binding
	 */
    public void testSimpleAsAttributeDelegate() {
		//def t1, t2
        def shell = jface.shell() {
        	text(id:'t1', text: bind(model: this, modelProperty:'text'))
        	text(id:'t2', text: bind(model: this, modelProperty:'number'))
        }
        
		// change the model
        text = "something"
        jface.dataBindingContext.updateTargets()
        assert jface.t1.text == "something"

        // change the widget
        jface.t1.text = "something else"
        jface.dataBindingContext.updateModels()
        assert text == "something else"
        
        // test the number
        jface.t2.text = 8
        jface.dataBindingContext.updateModels()
        assert number == 8
        
        // NOTE: When it is on the same object, you need to do a set..() to trigger
        // the change events, otherwise it acts as a direct access.
        setNumber(7)
        jface.dataBindingContext.updateModels()
        assert jface.t2.text == '7'
        
        // dispose the shell
        shell.dispose()
    }
    
    public void testSimpleAsVerbose() {
		//def t1, t2
        def shell = jface.shell() {
        	text(id:'t1')
        	text(id:'t2')
        	bind(target:t1, targetProperty:'text', model: this, modelProperty:'text')
        	bind(target:t2, targetProperty:'text', model: this, modelProperty:'number')
        }
        
		// change the model
        text = "x1"
        jface.dataBindingContext.updateTargets()
        assert jface.t1.text == "x1"

        // change the widget
        jface.t1.text = "x2"
        jface.dataBindingContext.updateModels()
        assert text == "x2"
        
        // test the number
        jface.t2.text = 9
        jface.dataBindingContext.updateModels()
        assert number == 9
        
        
        // NOTE: When it is on the same object, you need to do a set..() to trigger
        // the change events, otherwise it acts as a direct access.
        setNumber(10)
        jface.dataBindingContext.updateModels()
        assert jface.t2.text == '10'
        
        // dispose the shell
        shell.dispose()
    }
    /*
     * Test binding to a empty list. There was a bug using ?: on a empty list :-)
     */
    public void testBindingToEmptyList() {
    	list = new WritableList(jface.realm)
        def shell = jface.shell() {
    		table(id:'t') {
				tableColumn('name', style:'LEFT', width: 100)
				tableViewer(input: bind(model:list, modelProperty:['name']))
    		}
        }
    	assert jface.t.itemCount == 0
    	list.add(new TestPerson(name: 'name'))
        jface.dataBindingContext.updateModels()
    	assert jface.t.itemCount == 1
    }
    
}
