/**
 * See http://dev.eclipse.org/viewcvs/index.cgi/org.eclipse.jface.examples.databinding/src/org/eclipse/jface/examples/databinding/snippets/Snippet032TableViewerColumnEditing.java?view=markup
 * for the example in java.
 *
 * Demonstrates binding a TableViewer with multiple columns to a collection.
 */
package groovy.jface.examples.databinding

import groovy.beans.Bindable
import groovy.jface.JFaceBuilder

import org.eclipse.core.databinding.beans.BeansObservables
import org.eclipse.core.databinding.beans.BeanProperties
import org.eclipse.core.databinding.beans.IBeanValueProperty
import org.eclipse.core.databinding.observable.Realm
import org.eclipse.core.databinding.observable.list.WritableList
import org.eclipse.core.databinding.observable.map.IObservableMap;

import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.jface.databinding.swt.WidgetProperties
import org.eclipse.jface.databinding.viewers.CellEditorProperties
import org.eclipse.jface.databinding.viewers.ViewerProperties
import org.eclipse.jface.databinding.viewers.ObservableValueEditingSupport
import org.eclipse.jface.databinding.viewers.ObservableMapCellLabelProvider
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider
import org.eclipse.jface.viewers.ArrayContentProvider
import org.eclipse.jface.viewers.ITableColorProvider
import org.eclipse.jface.viewers.TextCellEditor

import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.widgets.Display

//TODO: Should be deleted:
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider
import org.eclipse.core.databinding.beans.BeansObservables
import org.eclipse.core.databinding.observable.Observables
import org.eclipse.jface.databinding.viewers.ViewerProperties
import org.eclipse.jface.databinding.viewers.ViewersObservables


// The data model class. This is normally a persistent class of some sort.
@Bindable
class Person032 {
	String name
	String firstName
}

// The View's model--the root of our Model graph for this particular GUI.
class ViewModel032 {
	// The model to bind
	@Bindable
	def people
	
	ViewModel032() {
		people = new WritableList(SWTObservables.getRealm(Display.getCurrent()))
		people << new Person032(firstName: "Dave", name:"Orme")
		people << new Person032(firstName: "Gili", name:"Mendel")
		people << new Person032(firstName: "Joe", name:"Winchester")
		people << new Person032(firstName: "Boris", name:"Bokowski")
		people << new Person032(firstName: "Brad", name:"Reynolds")
		people << new Person032(firstName: "Matthew", name:"Hall")
	}
}

class View032 {
	ViewModel032 viewModel
	
	def createShell() {
		def jface = new JFaceBuilder()
		
		// basic properties
		def propName = BeanProperties.value("name")
		def propFirstName = BeanProperties.value("firstName")
		def cellEditorControlText = CellEditorProperties.control().value(WidgetProperties.text());
		
		// providers
		
		// the shell
		def shell = jface.shell('Snippet032TableViewerColumnEditing') {
			migLayout(layoutConstraints:"wrap 2", columnConstraints: "[right][grow, fill]", rowConstraints: "")
			table(id:'committers', style:'FULL_SELECTION, BORDER', linesVisible: true, headerVisible: true, layoutData:"span 2") {
				tableColumn(id:'tcName', 'Name', width:100)
				tableColumn(id:'tcFirstName', 'FirstName', width:100)
				tableViewer(id:'peopleViewer',
							input: bind(model: viewModel.people, modelProperty:['name', 'firstName'])) {
					peopleViewer.setContentProvider(new ObservableListContentProvider())
					IObservableMap[] result = Properties.observeEach(peopleViewer.contentProvider.getKnownElements(),
				 			 [propName, propFirstName] as IBeanValueProperty[]);
					tableViewerColumn(tcName,
									  editingSupport: ObservableValueEditingSupport
									  				.create(peopleViewer,
									  						jface.dataBindingContext,
									  						new TextCellEditor(peopleTable),
									  						cellEditorControlText,
									  						propName),
									  labelProvider: new ObservableMapCellLabelProvider(result[0]))
					tableViewerColumn(tcFirstName,
									  editingSupport: ObservableValueEditingSupport
									  				.create(peopleViewer,
									  						jface.dataBindingContext,
									  						new TextCellEditor(peopleTable),
									  						cellEditorControlText,
									  						propFirstName),
									  labelProvider: new ObservableMapCellLabelProvider(result[0]))
				}
			}
			def selection = ViewerProperties.singleSelection().observe(peopleViewer)
			label(text: bind(BeansObservables.observeDetailValue(selection, "name", String.class)))
			label(text: bind(BeansObservables.observeDetailValue(selection, "firstName", String.class)))
			
		}
		return shell
	}

	def open() {
		def shell
		Realm.runWithDefault(Realm.default, {
			shell = createShell()
		})
		return shell
	}
}

/**
 * @author Frank
 *
 */
public class Snippet032TableViewerColumnEditing {
	public static void main(String[] args){
		def display = Display.default ?: new Display()
		Realm.default = SWTObservables.getRealm(display)
		
		def model = new ViewModel032()
		def shell = new View032(viewModel: model).open()
		shell.doMainloop()
	}
	
}
