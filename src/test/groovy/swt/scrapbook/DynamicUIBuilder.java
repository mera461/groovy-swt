/*
 * Created on Mar 7, 2004
 *
 */
package groovy.swt.scrapbook;

import groovy.swt.SwtBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href:ckl at dacelo.nl">Christiaan ten Klooster</a> 
 * $Id$
 */
public class DynamicUIBuilder extends SwtBuilder {
    public DynamicUIBuilder() {
        super();
        newContext();
    }

    /*
     * @see groovy.util.BuilderSupport#createNode(java.lang.Object)
     */
    public Object createNode(Object name) {
        System.out.println("createNode: " + name);
        return super.createNode(name, new HashMap(), null);
    }

    /* 
     * @see groovy.util.BuilderSupport#createNode(java.lang.Object, java.util.Map)
     */
    protected Object createNode(Object name, Map attributes) {
        System.out.println("createNode: " + name + " [attributes:]  " + attributes);
        return super.createNode(name, attributes, null);
    }
}
