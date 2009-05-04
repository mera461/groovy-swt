/*
 * Created on Feb 25, 2004
 *
 */
package groovy.swt

import groovy.lang.GroovyClassLoader
import groovy.lang.GroovyObject

import java.io.File

import junit.framework.TestCase

import org.eclipse.jface.window.ApplicationWindow
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Event
import org.eclipse.swt.widgets.Shell

/**
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 915 $
 */
public class SwtTest extends TestCase {
    public void testSwt() {
        Shell shell = new Shell()
        shell.dispose()
    }
    
    public void testWithIds() {
          def swt = new SwtBuilder()
          def localVar = null
          swt.shell('title') {
              label('byAttr', id:'byAttr')
              byExpr = label('byExpr')
              localVar = label('localVar')
          }
          swt[SwtBuilder.DELEGATE_PROPERTY_OBJECT_ID] = 'key'
          swt.shell {
              label('byKey', key:'byKey')
          }

          assert localVar != null
          assert swt.getVariables().containsKey('byAttr')
          assert swt.getVariables().containsKey('byExpr')
          assert !swt.getVariables().containsKey('localVar')
          assert swt.getVariables().containsKey('byKey')
      }
    
    public void testJFace() {
        Shell shell = new Shell()
        ApplicationWindow window = new ApplicationWindow(shell)
        shell.dispose()
    }
    
    public void testOnEvent() {
        def swt = new SwtBuilder()
        def x = 1
        def localVar = null
        swt.shell('title') {
        	rowLayout()
        	button('text', id:'b1') {
        		onEvent('Selection') {
        			x = 2
        		}
        	}
        	button('text', id:'b2') {
        		onEvent(type:'Selection') {
        			x = 3
        		}
        	}
        	button('text', id:'b3') {
        		onEvent(type:'Selection', closure: {
        			x = 4
        		})
        	}
        }
    	
        assert x == 1
        pushButton(swt.b1)
        assert x == 2
        pushButton(swt.b2)
        assert x == 3
        pushButton(swt.b3)
        assert x == 4
    }
    
    void pushButton(def button) {
    	button.notifyListeners(SWT.Selection, null)
    }
    
}
