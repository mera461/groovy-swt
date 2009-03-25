/*
 * Created on Feb 25, 2004
 *  
 */
package groovy.jface

import groovy.jface.factory.ContributionManagerFactory
import groovy.jface.factory.DoubleClickListenerFactory
import groovy.jface.factory.JFaceImageFactory
import groovy.jface.factory.MenuManagerFactory
import groovy.jface.factory.PreferencesDialogFactory
import groovy.jface.factory.PreferencesFieldEditorFactory
import groovy.jface.factory.PreferencesPageFactory
import groovy.jface.factory.SelectionChangedListenerFactory
import groovy.jface.factory.ToolBarManagerFactory
import groovy.jface.factory.ViewerFactory
import groovy.jface.factory.WindowFactory
import groovy.jface.factory.WizardDialogFactory
import groovy.jface.factory.WizardPageFactory
import groovy.jface.impl.ActionImpl
import groovy.jface.impl.ApplicationWindowImpl
import groovy.jface.impl.WindowImpl
import groovy.swt.SwtBuilder

import org.eclipse.jface.action.Separator
import org.eclipse.jface.preference.BooleanFieldEditor
import org.eclipse.jface.preference.ColorFieldEditor
import org.eclipse.jface.preference.DirectoryFieldEditor
import org.eclipse.jface.preference.FileFieldEditor
import org.eclipse.jface.preference.FontFieldEditor
import org.eclipse.jface.preference.IntegerFieldEditor
import org.eclipse.jface.preference.RadioGroupFieldEditor
import org.eclipse.jface.preference.StringFieldEditor
import org.eclipse.jface.viewers.CheckboxTableViewer
import org.eclipse.jface.viewers.CheckboxTreeViewer
import org.eclipse.jface.viewers.ComboViewer
import org.eclipse.jface.viewers.ListViewer
import org.eclipse.jface.viewers.TableTreeViewer
import org.eclipse.jface.viewers.TableViewer
import org.eclipse.jface.viewers.TreeViewer

/**
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 1560 $
 */
public class JFaceBuilder extends SwtBuilder {

	public JFaceBuilder(boolean init=true) {
		super(init)
	}

	public void registerPreferenceWidgets() {
        registerFactory("preferenceDialog", new PreferencesDialogFactory())
        registerFactory("preferencePage", new PreferencesPageFactory())
        registerFactory("booleanFieldEditor", new PreferencesFieldEditorFactory(
                BooleanFieldEditor.class))
        registerFactory("colorFieldEditor", new PreferencesFieldEditorFactory(
                ColorFieldEditor.class))
        registerFactory("directoryFieldEditor", new PreferencesFieldEditorFactory(
                DirectoryFieldEditor.class))
        registerFactory("fileFieldEditor", new PreferencesFieldEditorFactory(FileFieldEditor.class))
        registerFactory("fontFieldEditor", new PreferencesFieldEditorFactory(FontFieldEditor.class))
        registerFactory("integerFieldEditor", new PreferencesFieldEditorFactory(
                IntegerFieldEditor.class))
        registerFactory("radioGroupFieldEditor", new PreferencesFieldEditorFactory(RadioGroupFieldEditor.class))
        registerFactory("stringFieldEditor", new PreferencesFieldEditorFactory(
                StringFieldEditor.class))
    }
    
	public void registerWindows() {
    	super.registerWindows()
        registerFactory("applicationWindow", new WindowFactory(ApplicationWindowImpl.class))
        registerFactory("window", new WindowFactory(WindowImpl.class))
        registerFactory("wizardDialog", new WizardDialogFactory())
        registerFactory("wizardPage", new WizardPageFactory())
    }

	public void registerMenuWidgets() {
    	super.registerMenuWidgets()
        // ContributionManager
        registerFactory("menuManager", new MenuManagerFactory())
        registerFactory("toolBarManager", new ToolBarManagerFactory())
    }
    
	public void registerSupportWidgets() {
    	super.registerSupportWidgets()
        registerFactory("doubleClickListener", new DoubleClickListenerFactory())
        registerFactory("selectionChangedListener", new SelectionChangedListenerFactory())
        registerFactory("action", new ContributionManagerFactory(ActionImpl.class))
        registerFactory("separator", new ContributionManagerFactory(Separator.class))
        registerFactory("image", new JFaceImageFactory())
    }
    
	public void registerViewers() {
        registerFactory("tableViewer", new ViewerFactory(TableViewer.class))
        registerFactory("tableTreeViewer", new ViewerFactory(TableTreeViewer.class))
        registerFactory("checkboxTableViewer", new ViewerFactory(CheckboxTableViewer.class))
        registerFactory("treeViewer", new ViewerFactory(TreeViewer.class))
        registerFactory("checkboxTreeViewer", new ViewerFactory(CheckboxTreeViewer.class))
        registerFactory("comboViewer", new ViewerFactory(ComboViewer.class))
        registerFactory("listViewer", new ViewerFactory(ListViewer.class))
    }
}