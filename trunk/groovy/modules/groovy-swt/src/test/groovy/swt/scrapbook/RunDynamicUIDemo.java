package groovy.swt.scrapbook;

import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import junit.framework.TestCase;

import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Shell;

/**
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision$
 */
public class RunDynamicUIDemo extends TestCase {
    public void testBasic() throws Exception {
        NamedObject namedObject = new NamedObject();
        
        DynamicUIBuilder builder  = new DynamicUIBuilder();
        Shell shell = (Shell) builder.createNode("shell");
        shell.setLayout(new RowLayout());
        
        Binding binding = new Binding();
        binding.setVariable("obj", namedObject);
        binding.setVariable("builder", builder);
        binding.setVariable("parent", shell);
        
        GroovyScriptEngine scriptEngine = new GroovyScriptEngine("src/test/groovy/swt/scrapbook/");
        Object object = scriptEngine.run("NamedObjectUI.groovy", binding);

        shell.pack();
        shell.open();
        while (!shell.isDisposed()) {
            if (!shell.getDisplay().readAndDispatch()) {
                shell.getDisplay().sleep();
            }
        }
    }
}
