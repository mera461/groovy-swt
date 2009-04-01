/**
 * See http://dev.eclipse.org/viewcvs/index.cgi/org.eclipse.jface.examples.databinding/src/org/eclipse/jface/examples/databinding/snippets/Snippet001NestedSelectionWithCombo.java?view=markup
 * for the example in java.
 *
 * Hello, databinding. Bind changes in a GUI to a Model object but don't worry
 * about propogating changes from the Model to the GUI.
 * <p>
 * Illustrates the basic Model-ViewModel-Binding-View architecture typically
 * used in data binding applications.
 */
package groovy.jface.examples.databinding

import groovy.beans.Bindable
import groovy.jface.JFaceBuilder

import org.eclipse.core.databinding.beans.BeansObservables
import org.eclipse.core.databinding.beans.BeanProperties
import org.eclipse.core.databinding.observable.Realm
import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.jface.viewers.ArrayContentProvider
import org.eclipse.swt.widgets.Display

//TODO: Should be deleted:
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider
import org.eclipse.core.databinding.beans.BeansObservables
import org.eclipse.core.databinding.observable.Observables
import org.eclipse.jface.databinding.viewers.ViewerProperties
import org.eclipse.jface.databinding.viewers.ViewersObservables


// The data model class. This is normally a persistent class of some sort.
@Bindable
class Person001 {
	String name
	String city
}

// The View's model--the root of our Model graph for this particular GUI.
@Bindable
class ViewModel001 {
	// The model to bind
	def people = [new Person001(name:"Wile E. Coyote", city:"Tuscon"),
	              new Person001(name:"Road Runner", city:"Lost Horse"),
	              new Person001(name:"Bugs Bunny", city:"Forrest")]
	
	def cities = ["Tuscon", "AcmeTown", "Lost Horse", "Forrest", "Lost Mine"]
}

class View001 {
	ViewModel001 viewModel
	
	def createShell() {
		def jface = new JFaceBuilder()
		def shell = jface.shell('Snippet001NestedSelectionWithCombo') {
			migLayout(layoutConstraints:"wrap 1", columnConstraints: "[grow, fill]")
			list() {
				listViewer(id:'v1', input: bind(BeansObservables.observeList(viewModel, 'people'),  modelProperty:'name'))
			}
			text(text: bind(BeansObservables.observeDetailValue(ViewerProperties.singleSelection().observe(v1), 'name', String.class)))
			text(text: bind(BeansObservables.observeDetailValue(ViewersObservables.observeSingleSelection(v1), 'name', String.class)))
			combo(style:'READ_ONLY', selection : bind(BeansObservables.observeDetailValue(ViewersObservables.observeSingleSelection(v1), 'city', String.class))) {
				comboViewer(input: bind(viewModel, modelProperty:'cities'))
			}
		}
		return shell
	}

	def open() {
		def shell
		def display = new Display()
		Realm.runWithDefault(SWTObservables.getRealm(display), {
			shell = createShell()
		})
		return shell
	}
}

/**
 * @author Frank
 *
 */
public class Snippet001NestedSelectionWithCombo{
	public static void main(String[] args){
		def model = new ViewModel001()
		def shell = new View001(viewModel: model).open()
		shell.doMainloop()
	}
	
}
