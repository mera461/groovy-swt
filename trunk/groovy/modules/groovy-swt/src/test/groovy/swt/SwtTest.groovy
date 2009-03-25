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
}
