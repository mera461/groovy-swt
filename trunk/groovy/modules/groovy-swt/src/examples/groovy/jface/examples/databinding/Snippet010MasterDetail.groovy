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
import org.eclipse.core.databinding.observable.list.WritableList
import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.jface.databinding.viewers.ViewerProperties
import org.eclipse.swt.widgets.Display

// The data model class. This is normally a persistent class of some sort.
@Bindable
class Person010 {
	String name
}

class View010 {
	@Bindable
	def people
	
	def createShell() {
		people = new WritableList(SWTObservables.getRealm(Display.getCurrent()))
		people << new Person010(name:"Me")
		people << new Person010(name:"Myself")
		people << new Person010(name:"I")
		
		def jface = new JFaceBuilder()
		def shell = jface.shell('Snippet010NestedSelectionWithCombo') {
			migLayout(layoutConstraints:"wrap 1", columnConstraints: "[grow, fill]")
			list() {
				listViewer(id:'v1', input: bind(people,  modelProperty:'name'))
			}
			// doing it manually:
			text(text: bind(BeansObservables.observeDetailValue(ViewerProperties.singleSelection().observe(v1), 'name', String.class)))
			// shortcut
			text(text: bind(model: v1, modelProperty:'name'))
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
public class Snippet010MasterDetail {
	public static void main(String[] args){
		def shell = new View010().open()
		shell.doMainloop()
	}
	
}
