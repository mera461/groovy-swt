package groovy.swt.impl;

import groovy.lang.Closure;
import groovy.swt.ClosureSupport;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.OpenWindowListener;
import org.eclipse.swt.browser.WindowEvent;
import org.eclipse.swt.graphics.Point;

/**
 * This implementation adds a OpenWindowListener to a browser widget
 * 
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster</a>
 */
public class OpenWindowListenerImpl implements OpenWindowListener, ClosureSupport {

    private Closure closure;

    public Closure getClosure() {
        return closure;
    }

    public void setClosure(Closure closure) {
        this.closure = closure;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.swt.browser.OpenWindowListener#open(org.eclipse.swt.browser
     * .WindowEvent)
     */
    public void open(WindowEvent event) {
        if (closure == null) {
            throw new NullPointerException("No closure has been configured for this Listener");
        }

        closure.setProperty("event", new CustomWindowEvent(event));
        closure.call(event);
    }

    public class CustomWindowEvent {
        private WindowEvent event;

        public CustomWindowEvent(WindowEvent event) {
            this.event = event;
        }

        public boolean getAddressBar() {
            return event.addressBar;
        }

        public Browser getBrowser() {
            return event.browser;
        }

        public Point getLocation() {
            return event.location;
        }

        public Point getSize() {
            return event.size;
        }

        public boolean getMenuBar() {
            return event.menuBar;
        }

        public boolean getStatusBar() {
            return event.statusBar;
        }

        public boolean getToolBar() {
            return event.toolBar;
        }

        public boolean getRequired() {
            return event.required;
        }

    }

}
