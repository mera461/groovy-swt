package groovy.jface.factory;

import groovy.jface.impl.PreferencePageFieldEditorImpl;
import groovy.lang.MissingPropertyException;
import groovy.swt.InvalidParentException;
import groovy.swt.factory.AbstractSwtFactory;
import groovy.swt.factory.SwtFactory;

import java.util.ArrayList;
import java.util.Map;

import org.codehaus.groovy.GroovyException;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;

/**
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 915 $
 */
public class PreferencesFieldEditorFactory extends AbstractSwtFactory implements
        SwtFactory {

    private Class beanClass;

    public PreferencesFieldEditorFactory(Class beanClass) {
        this.beanClass = beanClass;
    }
    
    private Object getValueOrThrow(Map properties, String name) {
        Object value = properties.get(name);
        if (name == null) {
        	throw new MissingPropertyException(name, FieldEditor.class);
        }
    	return value;
    }

    public Object newInstance(Map properties, Object parent)
            throws GroovyException {

        if (beanClass == null) { throw new GroovyException(
                "No Class available to create the FieldEditor"); }

        // check location
        if (!(parent instanceof PreferencePage)) { throw new InvalidParentException(
                "preferencePage"); }

        String name = (String) getValueOrThrow(properties, "propertyName");
        String labelText = (String) getValueOrThrow(properties, "title");

        PreferencePageFieldEditorImpl preferencePageImpl = (PreferencePageFieldEditorImpl) parent;
        if (beanClass.equals(RadioGroupFieldEditor.class)) {
            int numColumns = ((Integer) getValueOrThrow(properties, "numColumns")).intValue();
            Boolean bUseGroup = (Boolean) properties.get("useGroup");
            boolean useGroup = false;
            if (bUseGroup!=null) {
            	useGroup = bUseGroup.booleanValue();
            }
            ArrayList values = (ArrayList) getValueOrThrow(properties, "labelAndValues");
            
            String[][] labelAndValues = new String[values.size()][2];
            for (int no=0; no<values.size(); no++) {
            	ArrayList labelValuePair = (ArrayList) values.get(no); 
            	if (labelValuePair.size()!=2) {
                	throw new MissingPropertyException("Only label and value in labelAndValues field", FieldEditor.class);
            	}
            	labelAndValues[no][0] = (String) labelValuePair.get(0);
            	labelAndValues[no][1] = (String) labelValuePair.get(1);
            }
//            String[][] labelAndValues = new String[][] {{"label1", "value1"}, {"label2", "value2"}};
        	preferencePageImpl.addRadioGroupFieldCreator(name, labelText, numColumns, labelAndValues, useGroup);
        } else {
        	preferencePageImpl.addFieldCreator(beanClass, name, labelText);
        }

        return preferencePageImpl;
    }

}
