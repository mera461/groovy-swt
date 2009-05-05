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
	 
    public void testSimpel() {
		//def t1, t2
        def shell = jface.shell() {
        	text(id:'t1', text: bind(model: this, modelProperty:'text'))
        	text(id:'t2', text: bind(model: this, modelProperty:'number'))
        }
        
        text = "something"
        jface.dataBindingContext.updateTargets()
        assert jface.t1.text == "something"

        jface.t1.text = "something else"
        jface.dataBindingContext.updateModels()
        assert text == "something else"
        shell.dispose()
    }
    
    public void testSimple() {
          jface.shell('title') {
          }
      }
    
    
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
