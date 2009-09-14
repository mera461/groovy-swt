package groovy.swt.examples.guibuilder;

import groovy.lang.Binding;
import groovy.swt.guibuilder.ApplicationGuiBuilder;
import groovy.util.GroovyScriptEngine;

/**
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 1027 $
 */
public class RunGuiBuilderDemo {

    public static void main(String[] args) throws Exception {
        String basePath = "src/examples/groovy/swt/examples/guibuilder";
        GroovyScriptEngine scriptEngine = new GroovyScriptEngine(basePath);

        Binding binding = new Binding();
        ApplicationGuiBuilder guiBuilder = new ApplicationGuiBuilder(basePath);
        binding.setVariable("guiBuilder", guiBuilder);

        scriptEngine.run("GuiBuilderDemo.groovy", binding);
    }

}