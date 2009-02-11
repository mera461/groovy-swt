package groovy.jface.factory;

import groovy.jface.impl.PreferencePageFieldEditorImpl;
import groovy.lang.MissingPropertyException;
import groovy.swt.InvalidParentException;
import groovy.swt.factory.AbstractSwtFactory;
import groovy.util.FactoryBuilderSupport;

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
public class PreferencesFieldEditorFactory extends AbstractSwtFactory {

    private Class beanClass;

    public PreferencesFieldEditorFactory(Class beanClass) {
        this.beanClass = beanClass;
    }
    
    private Object getValueOrThrow(Map properties, String name) {
        Object value = properties.remove(name);
        if (name == null) {
        	throw new MissingPropertyException(name, FieldEditor.class);
        }
    	return value;
    }

	public Object newInstance(FactoryBuilderSupport builder, Object name,
			Object value, Map attributes) throws InstantiationException,
			IllegalAccessException {
		Object parent = builder.getCurrent();

        if (beanClass == null) {
        	throw new InstantiationException("No Class available to create the FieldEditor");
        }

        // check location
        if (!(parent instanceof PreferencePage)) {
        	throw new InstantiationException("the parent of a PreferencesFieldEditor must be a PreferencePage");
        }

        String propertyName = (String) getValueOrThrow(attributes, "propertyName");
        String labelText = (String) getValueOrThrow(attributes, "title");

        PreferencePageFieldEditorImpl preferencePageImpl = (PreferencePageFieldEditorImpl) parent;
        if (beanClass.equals(RadioGroupFieldEditor.class)) {
            int numColumns = ((Integer) getValueOrThrow(attributes, "numColumns")).intValue();
            Boolean bUseGroup = (Boolean) attributes.remove("useGroup");
            boolean useGroup = false;
            if (bUseGroup!=null) {
            	useGroup = bUseGroup.booleanValue();
            }
            ArrayList values = (ArrayList) getValueOrThrow(attributes, "labelAndValues");
            
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
        	preferencePageImpl.addRadioGroupFieldCreator(propertyName, labelText, numColumns, labelAndValues, useGroup);
        } else {
        	preferencePageImpl.addFieldCreator(beanClass, propertyName, labelText);
        }

        return preferencePageImpl;
    }

}
