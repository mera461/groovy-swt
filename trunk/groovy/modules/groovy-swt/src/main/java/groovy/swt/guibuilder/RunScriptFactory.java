/*
 * Created on Mar 17, 2004
 *  
 */
package groovy.swt.guibuilder;

import groovy.lang.Binding;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.MissingPropertyException;
import groovy.swt.SwtUtils;
import groovy.swt.factory.AbstractSwtFactory;
import groovy.util.FactoryBuilderSupport;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.util.Map;

import org.codehaus.groovy.GroovyException;
import org.eclipse.swt.widgets.Composite;

/**
 * Run another script
 * 
 * @author <a href:ckl at dacelo.nl">Christiaan ten Klooster </a> $Id:
 *         RunScriptFactory.java 1862 2005-02-22 15:08:42Z ckl $
 */
public class RunScriptFactory extends AbstractSwtFactory {

    /** the builder */
    private ApplicationGuiBuilder guiBuilder;

    /**
     * @param scriptEngine
     */
    public RunScriptFactory(ApplicationGuiBuilder guiBuilder) {
        this.guiBuilder = guiBuilder;
    }

	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		Object parent = builder.getCurrent();

        // get src
        String src = (String) attributes.remove("src");
        if (src == null) {
            throw new MissingPropertyException("src", RunScriptFactory.class);
        }

        // get binding
        Binding binding = (Binding) attributes.remove("binding");
        if (binding == null) {
            binding = new Binding();
        }

        // get parent composite
        Composite parentComposite = null;
        Object obj = attributes.remove("parent");
        if (obj != null) {
            parentComposite = (Composite) SwtUtils.getParentWidget(obj,
            		attributes);
            if (parentComposite == null && parent instanceof Composite) {
                parentComposite = (Composite) parent;
            }
        } else {
            parentComposite = (Composite) SwtUtils.getParentWidget(guiBuilder
                    .getCurrent(), attributes);
        }
        //TODO: ??? guiBuilder.setCurrent(parentComposite);

        // dispose children
        Boolean rebuild = (Boolean) attributes.remove("rebuild");
        if (parentComposite != null && rebuild != null
                && rebuild.booleanValue()) {
            SwtUtils.disposeChildren(parentComposite);
        }

        // run script
        Object result;
        try {
            result = runScript(src, parentComposite, binding);
        } catch (Exception e) {
            throw new GroovyRuntimeException(e);
        }

        return result;
    }

    /**
     * @param widget
     * @param script
     * @param parent
     * @return
     * @throws ScriptException
     * @throws ResourceException
     */
    private Object runScript(String script, Composite parent, Binding binding)
            throws GroovyException, ResourceException, ScriptException {

        // script binding
        binding.setVariable("guiBuilder", guiBuilder);
        if (parent != null) {
            binding.setVariable("parent", parent);
        }

        Object obj = guiBuilder.getScriptEngine().run(script, binding);

        // layout widget
        if (parent != null) {
            parent.layout();
        } else if (obj != null && (obj instanceof Composite)) {
            ((Composite) obj).layout();
        }

        return obj;
    }
}