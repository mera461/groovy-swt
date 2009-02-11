/*
 * Created on Feb 15, 2004
 *
 */
package groovy.swt.factory;

import groovy.lang.GroovyRuntimeException;
import groovy.lang.MissingPropertyException;
import groovy.util.FactoryBuilderSupport;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.ScrolledForm;

/**
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 1306 $
 */
public class ImageFactory extends AbstractSwtFactory {

	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		Object parent = builder.getCurrent();
        String src = (String) attributes.remove("src");
        if (src == null) {
            throw new MissingPropertyException("src", Image.class);
        }

        if (parent == null) {
            throw new InstantiationException("The parent of a Image must be a Widget or a Window");
        }

        Image image = null;
        File imageFile = new File(src);
        if (imageFile.exists()) {
        	image = new Image(Display.getCurrent(), src);
        } else {
        	InputStream resourceAsStream = ImageFactory.class.getClassLoader().getResourceAsStream(src);
        	if (resourceAsStream == null) {
                throw new GroovyRuntimeException("Can not open the given image with src="+src);
        	}
       		image = new Image(Display.getCurrent(), resourceAsStream);
        }

        setImage(parent, image);

        return image;
	}

    /**
     * Add image to a widget or window
     * 
     * @param parent
     * @param image
     * @throws JellyTagException
     */
    protected void setImage(Object parent, Image image) {
        if (parent instanceof Label) {
            Label label = (Label) parent;
            label.setImage(image);

        } else if (parent instanceof Button) {
            Button button = (Button) parent;
            button.setImage(image);

        } else if (parent instanceof Item) {
            Item item = (Item) parent;
            item.setImage(image);

        } else if (parent instanceof Decorations) {
            Decorations item = (Decorations) parent;
            item.setImage(image);

        } else if (parent instanceof Form) {
            Form form = (Form) parent;
            form.setBackgroundImage(image);

        } else if (parent instanceof ScrolledForm) {
            ScrolledForm form = (ScrolledForm) parent;
            form.setBackgroundImage(image);

        } else if (parent instanceof Window) {
            Window window = (Window) parent;
            window.getShell().setImage(image);

        } else {
            throw new GroovyRuntimeException(
                    "This tag must be nested inside a Label, Button, Item, Decorations, Form, or a Window");
        }
    }

}