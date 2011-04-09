/*
 * Created on Feb 27, 2004
 *  
 */
package groovy.swt.factory;

import groovy.lang.GroovyRuntimeException;
import groovy.swt.SwtUtils;
import groovy.util.FactoryBuilderSupport;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.FormColors;
import org.eclipse.ui.forms.HyperlinkSettings;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormText;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.ScrolledPageBook;
import org.eclipse.ui.forms.widgets.Section;

/**
 * @author <a href:ckl at dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 1862 $
 */
public class FormFactory extends AbstractSwtFactory {

    /** static is evil, too many toolkits is evil */
    protected static FormToolkit toolkit;

    /**
     * @return Returns the toolkit.
     */
    public static FormToolkit getToolkit() {
        if (toolkit == null) {
            FormColors formColors = new FormColors(Display.getCurrent() == null ? new Display()
                    : Display.getCurrent());
            toolkit = new FormToolkit(formColors);
        }
        return toolkit;
    }

    /** type of */
    private String type;

    /**
     * @param string
     */
    public FormFactory(String type) {
        this.type = type;
    }

	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		Object parent = builder.getCurrent(); 
        Composite parentComposite = (Composite) SwtUtils.getParentWidget(parent, attributes);

        String styleProperty = (String) attributes.remove("style");
        int style = SWT.NULL;
        if (styleProperty != null) {
            if (type.equals("formSection")) {
                style = SwtUtils.parseStyle(Section.class, styleProperty);
            } else {
                style = SwtUtils.parseStyle(SWT.class, styleProperty);
            }
        }

        String text = (String) attributes.remove("text");
        if (text==null && value instanceof String) {
        	text = (String) value;
        }
        
        if (parentComposite != null) {
            Object formWidget = getFormWidget(parentComposite, attributes, style, text);
            setBeanProperties(formWidget, attributes);

            // if (shouldLayout && parentComposite != null && parentComposite instanceof Composite) {
            //    ((Composite) parentComposite).layout();
            //}

            return formWidget;

        } else {
            throw new InstantiationException("The parent of the Form must be a Composite instance");
        }

	}
    
    
    /**
     * @param parentComposite
     * @param style
     * @param text
     * @return
     */
    private Object getFormWidget(final Composite parentComposite, Map properties, int style,
            String text) throws InstantiationException {
        if ("form".equals(type)) {
            Form form = getToolkit().createForm(parentComposite);
            form.setText(text);
            return form;
        }
        if ("scrolledForm".equals(type)) {
            ScrolledForm scrolledForm = getToolkit().createScrolledForm(parentComposite);
            scrolledForm.setText(text);
            return scrolledForm;
        }
        if ("formButton".equals(type)) {
            return getToolkit().createButton(parentComposite, text, style);
        }
        if ("formColors".equals(type)) {
            return getToolkit().getColors();
        }
        if ("formComposite".equals(type)) {
            return getToolkit().createComposite(parentComposite, style);
        }
        if ("formCompositeSeparator".equals(type)) {
            return getToolkit().createCompositeSeparator(parentComposite);
        }
        if ("formExpandableComposite".equals(type)) {
            return getToolkit().createExpandableComposite(parentComposite, style);
        }
        if ("formText".equals(type)) {
            Text text2 = getToolkit().createText(parentComposite, text, style);
            getToolkit().paintBordersFor(parentComposite);
            return text2;
        }
        if ("formHyperlink".equals(type)) {
            return getToolkit().createHyperlink(parentComposite, text, style);
        }
        if ("formImageHyperlink".equals(type)) {
            return getToolkit().createImageHyperlink(parentComposite, style);
        }
        if ("formLabel".equals(type)) {
            return getToolkit().createLabel(parentComposite, text, style);
        }
        if ("formPageBook".equals(type)) {
            return getToolkit().createPageBook(parentComposite, style);
        }
        if ("formPageBookPage".equals(type)) {
            if (parentComposite instanceof ScrolledPageBook) {
                ScrolledPageBook pageBook = (ScrolledPageBook) parentComposite;
                String key = (String) properties.remove("key");
                if (key != null) {
                    Composite page = pageBook.createPage(key);
                    pageBook.registerPage(key, page);
                    return page;
                } else {
					 throw new GroovyRuntimeException("formPageBookPage must have an attribute \"key\" (is null)");
                }
            } else {
                throw new InstantiationException("The parent of a formPageBookPage must be a formPageBook");
            }
        }
        if ("formSection".equals(type)) {
            Section section = getToolkit().createSection(parentComposite, style);
            if (text != null) {
                section.setText(text);
            }
            section.setSeparatorControl(getToolkit().createCompositeSeparator(section));
            String description = (String) properties.remove("description");
            if (description != null) {
                section.setDescription(description);
            }
            Composite client = getToolkit().createComposite(section);
            client.setLayout(new GridLayout());
            section.setClient(client);
            return section;
        }
        if ("formSeparator".equals(type)) {
            return getToolkit().createSeparator(parentComposite, style);
        }
        if ("formTable".equals(type)) {
            return getToolkit().createTable(parentComposite, style);
        }
        if ("formToolkit".equals(type)) {
            return getToolkit();
        }
        if ("formFormattedText".equals(type)) {
            boolean parseTags = false;
            boolean expandURLs = false;
            boolean trackFocus = false;
            if (properties.get("parseTags") != null) {
                parseTags = ((Boolean) properties.remove("parseTags")).booleanValue();
            }
            if (properties.get("expandURLs") != null) {
                expandURLs = ((Boolean) properties.remove("expandURLs")).booleanValue();
            }
            if (properties.get("trackFocus") != null) {
                trackFocus = ((Boolean) properties.remove("trackFocus")).booleanValue();
            }

            FormText formText = getToolkit().createFormText(parentComposite, trackFocus);
            HyperlinkSettings hyperlinkSettings = new HyperlinkSettings(Display.getCurrent());
            hyperlinkSettings.setBackground(getToolkit().getColors().getBackground());
            hyperlinkSettings.setActiveBackground(getToolkit().getColors().getBackground());
            hyperlinkSettings.setForeground(new Color(Display.getCurrent(), 0,0,255));
            hyperlinkSettings.setActiveForeground(getToolkit().getColors().getForeground());
            formText.setHyperlinkSettings(hyperlinkSettings);

            formText.setText(text, parseTags, expandURLs);

            return formText;
        }
        if ("formTree".equals(type)) {
            return getToolkit().createTree(parentComposite, style);
        }
        return null;
    }
}