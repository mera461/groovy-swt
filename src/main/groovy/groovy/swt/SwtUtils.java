package groovy.swt;

import groovy.jface.impl.ApplicationWindowImpl;
import groovy.lang.GroovyRuntimeException;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

/** 
 * A helper class for working with SWT.
 *
 * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 *  
 * @version 1.1
 */
public class SwtUtils {

    /** The Log to which logging calls will be made. */
    private static final Log log = LogFactory.getLog(SwtUtils.class);

    /**
     * Parses the comma delimited String of style codes which are or'd
     * together. The given class describes the integer static constants
     *
     * @param constantClass is the type to look for static fields
     * @param text is a comma delimited text value such as "border, resize"
     * @return the int code
     */
    public static int parseStyle(Class constantClass, String text) {
        return parseStyle(constantClass, text, true);
    }

    /**
     * Parses the comma delimited String of style codes which are or'd
     * together. The given class describes the integer static constants
     *
     * @param constantClass is the type to look for static fields
     * @param text is a comma delimited text value such as "border, resize"
     * @param toUpperCase is whether the text should be converted to upper case
     * before its compared against the reflection fields
     * 
     * @return the int code
     */
    public static int parseStyle(Class constantClass, String text, boolean toUpperCase) {
        int answer = 0;
        if (text != null) {
            if (toUpperCase) {
                text = text.toUpperCase();
            }
            StringTokenizer enumeration = new StringTokenizer(text, ",");
            while (enumeration.hasMoreTokens()) {
                String token = enumeration.nextToken().trim();
                answer |= getStyleCode(constantClass, token);
            }
        }
        return answer;
    }

    /**
     * @return the code for the given word or zero if the word doesn't match a
     * valid style
     */
    public static int getStyleCode(Class constantClass, String text) {
        try {
            Field field = constantClass.getField(text);
            if (field == null) {
                log.warn("Unknown style code: " + text + " will be ignored");
                return 0;
            }
            return field.getInt(null);
        } catch (NoSuchFieldException e) {
            throw new GroovyRuntimeException("The value: " + text + " is not understood ");
        } catch (IllegalAccessException e) {
            throw new GroovyRuntimeException("The value: " + text + " is not understood");
        }
    }

    /**
     * dispose all children
     * 
     * @param parent
     */
    public static void disposeChildren(Composite parent) {
        Control[] children = parent.getChildren();
        for (int i = 0; i < children.length; i++) {
            Control control = children[i];
            control.dispose();
        }
    }

    /**
     * return the parent shell
     * 
     * @param parent
     * @return
     */
    public static Shell getParentShell(Object parent) {
        if (parent instanceof ApplicationWindow) {
            return ((ApplicationWindowImpl) parent).getShell();
        } else if (parent instanceof Shell) {
            return (Shell) parent;
        } else {
            return null;
        }
    }

    /**
     * return the parent widget
     * 
     * @param parent
     * @return
     */
    public static Object getParentWidget(Object parent, Map properties) {
        if (parent == null && properties.containsKey("parent")) {
            Object attribute = properties.remove("parent");
            if (attribute instanceof Composite) {
                Composite parentWidget = (Composite) attribute;
                SwtUtils.disposeChildren((Composite) parentWidget);
                return parentWidget;
            }
        }

        if (parent instanceof ApplicationWindow) {
            return (Composite) ((ApplicationWindowImpl) parent).getContents();
        } else if (parent instanceof Form) {
            return ((Form) parent).getBody();
        } else if (parent instanceof ScrolledForm) {
            return ((ScrolledForm) parent).getBody();
        } else if (parent instanceof Section) {
            return ((Section) parent).getClient();
        } else if (parent instanceof CTabItem) {
            return ((CTabItem) parent).getParent();
        } else if (parent instanceof TabItem) {
            return ((TabItem) parent).getParent();
        } else if (parent instanceof ExpandItem) {
        	return ((ExpandItem) parent).getParent();
        } else if (parent instanceof Widget) {
            return (Widget) parent;
        } else if (parent instanceof TableViewer) {
            return ((TableViewer) parent).getTable();
        } else if (parent instanceof TreeViewer){
        	return ((TreeViewer) parent).getControl();
        } else {
            return parent;
        }
    }

    
    /**
	 * Parse the given String and returns the static String defined in constantClass
	 * @param constantClass
	 * @param text
	 * @return String
	 */
	public static String parseString(Class pConstantClass, String pText) {
		return parseString(pConstantClass, pText, true);
	}
	
	
	/**
	 * Parse the given String and returns the static String defined in constantClass
	 * @param constantClass
	 * @param text
	 * @param toUpperCase
	 * @return String
	 */
	public static String parseString(Class pConstantClass, String pText, boolean pToUpperCase) {
		String ret = "";
		if (pText != null) {
            if (pToUpperCase) {
                pText = pText.toUpperCase();
            }
            
            try {
                Field field = pConstantClass.getField(pText);
                if (field == null) {
                    log.warn("Unknown code: " + pText + " will be ignored");
                    return "";
                }
                return field.get(pText).toString();
            } catch (NoSuchFieldException e) {
                throw new GroovyRuntimeException("The value: " + pText + " is not understood ");
            } catch (IllegalAccessException e) {
                throw new GroovyRuntimeException("The value: " + pText + " is not understood");
            }
        }
		return ret;
	}
	
	/**
	 * Parse the given String and returns the static String defined in constantClass
	 * String can be comma-delimited. Each value will be parsed.
	 * @param constantClass
	 * @param text
	 * @return String[]
	 */
	public static String[] parseStringArray(Class pConstantClass, String pText) {
		return parseStringArray(pConstantClass, pText, true);
	}
	
	/**
	 * Parse the given String and returns the static String defined in constantClass
	 * String can be comma-delimited. Each value will be parsed.
	 * @param constantClass
	 * @param text
	 * @param toUpperCase
	 * @return String[]
	 */
	public static String[] parseStringArray(Class pConstantClass, String pText, boolean pToUpperCase) {
		String[] ret = null;
		int i = 0;
		if (pText != null) {
            if (pToUpperCase) {
                pText = pText.toUpperCase();
            }
            StringTokenizer enumeration = new StringTokenizer(pText, ",");
            ret = new String[enumeration.countTokens()];
            while (enumeration.hasMoreTokens()) {
            	String token = enumeration.nextToken().trim();
            	ret[i] = new String() ;
            	ret[i] = parseString(pConstantClass, token, pToUpperCase);
            	i++;
        	}
        }
		return ret;
	}
	
	
	/**
	 * Parses the given String and returns the corresponding Object.
	 * @param pConstantClass
	 * @param pText
	 * @param pToUpperCase
	 * @return Object
	 */
	public static Object parseObject(Class pConstantClass, String pText, boolean pToUpperCase) {
		if (pText != null) {
            if (pToUpperCase) {
                pText = pText.toUpperCase();
            }
            try {
                Field field = pConstantClass.getField(pText);
                if (field == null) {
                    log.warn("Unknown code: " + pText + " will be ignored");
                    return null;
                }
                return field.get(pText);
            } catch (NoSuchFieldException e) {
                throw new GroovyRuntimeException("The value: " + pText + " is not understood ");
            } catch (IllegalAccessException e) {
                throw new GroovyRuntimeException("The value: " + pText + " is not understood");
            }
        }
		return null;
	}

}