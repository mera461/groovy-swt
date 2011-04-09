/*
 * Created on Feb 15, 2004
 */
package groovy.swt.factory;

import groovy.lang.Closure;
import groovy.swt.ClosureSupport;
import groovy.swt.SwtUtils;
import groovy.swt.impl.ExpansionListenerImpl;
import groovy.swt.impl.HyperLinkListenerImpl;
import groovy.swt.impl.ListenerImpl;
import groovy.swt.impl.LocationListenerImpl;
import groovy.swt.impl.ProgressListenerImpl;
import groovy.swt.impl.StatusTextListenerImpl;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.browser.StatusTextListener;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.AbstractHyperlink;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormText;

/**
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 1141 $
 */
public class ListenerFactory extends AbstractSwtFactory {

    private Class beanClass;

    /**
     * @param class1
     */
    public ListenerFactory(Class beanClass) {
        this.beanClass = beanClass;
    }

    
    
	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		Object parent = builder.getCurrent();
        String type = (String) attributes.remove("type");
        if (type==null && value!=null) type = value.toString();
        if (parent instanceof Browser) {
            Browser browser = (Browser) parent;
            if (beanClass.equals(LocationListener.class)) {
                LocationListener locationListener = new LocationListenerImpl(
                        type);
                browser.addLocationListener(locationListener);
                return locationListener;
            } else if (beanClass.equals(ProgressListener.class)) {
                ProgressListener progressListener = new ProgressListenerImpl(
                        type);
                browser.addProgressListener(progressListener);
                return progressListener;
            } else if (beanClass.equals(StatusTextListener.class)) {
                StatusTextListener statusTextListener = new StatusTextListenerImpl();
                browser.addStatusTextListener(statusTextListener);
                return statusTextListener;
            }
        } else if (parent instanceof AbstractHyperlink || parent instanceof FormText) {
            IHyperlinkListener hyperLinkListenerImpl = new HyperLinkListenerImpl(type);
            if (parent instanceof AbstractHyperlink) {
                ((AbstractHyperlink) parent).addHyperlinkListener(hyperLinkListenerImpl);
            } else if(parent instanceof FormText) {
                ((FormText) parent).addHyperlinkListener(hyperLinkListenerImpl);
            }
            return hyperLinkListenerImpl;
        } else if (parent instanceof ExpandableComposite) {
            ExpandableComposite expandableComposite = (ExpandableComposite) parent;
            IExpansionListener expansionListener = new ExpansionListenerImpl(
                    type);
            expandableComposite.addExpansionListener(expansionListener);
            return expansionListener;

        } else if (parent instanceof Widget
        		   || parent instanceof Viewer
        		   || parent instanceof MenuManager) {
            Widget widget = null;
            if (parent instanceof Viewer) {
                widget = ((Viewer) parent).getControl();
            } if (parent instanceof MenuManager) {
                widget = ((MenuManager) parent).getMenu();
            } else {
                widget = (Widget) parent;
            }
            // try the simple event types defined in SWT
            int eventType = getEventType(type);
            if (eventType == 0) {
            	throw new InstantiationException(
                    "No event type specified, could not understand the event: " + type);
            }
           	ListenerImpl listenerImpl = new ListenerImpl();
           	widget.addListener(eventType, listenerImpl);
           	return listenerImpl;
        }
        
        throw new InstantiationException("No factory found for class: " + beanClass.getName());
    }
    
	
    public boolean isHandlesNodeChildren() {
        return true;
    }

    public boolean onNodeChildren(FactoryBuilderSupport builder, Object node, Closure childContent) {
    	ClosureSupport cs = (ClosureSupport) node;
    	cs.setClosure(childContent);
    	return false;
    }
	
	
	
	
	
	/**
     * Parses the given event type String and returns the SWT event type code
     * 
     * @param type
     *            is the String event type
     * @return the SWT integer event type
     */
    protected int getEventType(String type) {
        return SwtUtils.parseStyle(SWT.class, type, false);
    }
}