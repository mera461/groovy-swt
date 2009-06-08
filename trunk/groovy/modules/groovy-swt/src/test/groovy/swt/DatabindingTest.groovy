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
import java.beans.PropertyChangeListener

import junit.framework.TestCase

import org.eclipse.core.databinding.observable.list.WritableList
import org.eclipse.jface.window.ApplicationWindow
import org.eclipse.swt.widgets.Shell

import org.codehaus.groovy.binding.ClosureTriggerBinding

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
	@Bindable
	boolean bool
	
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
     
    public void testClosureTriggerBinding() {
    	def model = new DatabindingTest()
    	def closure = {model.number*2}
    	def ctb = new ClosureTriggerBinding(new HashMap())
    	ctb.closure = closure
		def fullbinding = ctb.createBinding(ctb, null)
		def bp = fullbinding.bindPaths[0]
		assert 'model' == bp.propertyName
		assert 'number' == bp.children[0].propertyName
		assert model == bp.extractNewValue(closure)
    }
    
    public void testWithClosureBinding1() {
		def model = this
        def shell = jface.shell() {
        	text(id:'t1', text: bind {model.text})
        	text(id:'t2', text: bind {model.number.toString()})
        	text(id:'t3', text: bind {model.text?.toLowerCase()})
        	text(id:'t4', text: bind {model.bool ? 'Enabled' : 'Disabled'}, enabled: bind {model.bool})
        }
		
		// change the model
        model.text = "SomeThing"
        jface.dataBindingContext.updateTargets()
        assert jface.t1.text == "SomeThing"
        assert jface.t3.text == "something"

		// change a number
        model.number = 7
        jface.dataBindingContext.updateTargets()
        assert jface.t2.text == '7'

		// change a boolean
        model.bool = true
        jface.dataBindingContext.updateTargets()
        assert jface.t4.text == 'Enabled'
		assert true == jface.t4.enabled
        model.bool = false
        jface.dataBindingContext.updateTargets()
        assert jface.t4.text == 'Disabled'
		assert false == jface.t4.enabled

		
        // dispose the shell
        shell.dispose()    	
    }
}
