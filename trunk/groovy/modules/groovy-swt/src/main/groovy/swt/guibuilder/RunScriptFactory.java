/*
 * Created on Mar 17, 2004
 *  
 */
package groovy.swt.guibuilder;

import groovy.lang.Binding;
import groovy.lang.MissingPropertyException;
import groovy.swt.SwtUtils;
import groovy.swt.factory.AbstractSwtFactory;
import groovy.swt.factory.SwtFactory;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.groovy.GroovyException;
import org.eclipse.swt.widgets.Composite;

/**
 * Run another script
 * 
 * @author <a href:ckl at dacelo.nl">Christiaan ten Klooster </a> $Id:
 *         RunScriptFactory.java,v 1.1 2004/03/18 08:51:48 ckl Exp $
 */
public class RunScriptFactory extends AbstractSwtFactory implements SwtFactory {

    /** the logger */
    private Logger log = Logger.getLogger(getClass().getName());

    /** the builder */
    private ApplicationGuiBuilder guiBuilder;

    /**
     * @param scriptEngine
     */
    public RunScriptFactory(ApplicationGuiBuilder guiBuilder) {
        this.guiBuilder = guiBuilder;
    }

    /*
     * @see groovy.swt.factory.AbstractSwtFactory#newInstance(java.util.Map,
     *      java.lang.Object)
     */
    public Object newInstance(Map properties, Object parent)
            throws GroovyException {
        // get src
        String src = (String) properties.remove("src");
        if (src == null) { throw new MissingPropertyException("src",
                RunScriptFactory.class); }

        // get binding
        Binding binding = (Binding) properties.remove("binding");
        if (binding == null) {
            binding = new Binding();
        }

        // get parent composite
        Object obj = properties.remove("parent");
        Composite parentComposite = (Composite) SwtUtils.getParentWidget(obj);
        if (parentComposite == null && parent instanceof Composite) {
            parentComposite = (Composite) parent;
        }

        // run script
        return runScript(src, parentComposite, binding);
    }

    /**
     * @param widget
     * @param script
     * @param parent
     * @return
     */
    private Object runScript(String script, Composite parent, Binding binding)
            throws GroovyException {
        // TODO dispose children as an option
        // SwtUtils.disposeChildren(parent);

        // script binding
        binding.setVariable("guiBuilder", guiBuilder);
        if (parent != null) {
            binding.setVariable("parent", parent);
        }
        Object obj = null;
        try {
            obj = guiBuilder.getScriptEngine().run(script, binding);
        } catch (Exception e) {
            log.log(Level.WARNING, e.getMessage());
        }
        return obj;
    }
}