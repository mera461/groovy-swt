/**
 * See http://dev.eclipse.org/viewcvs/index.cgi/org.eclipse.jface.examples.databinding/src/org/eclipse/jface/examples/databinding/snippets/Snippet000HelloWorld.java?view=markup
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
import groovy.swt.SwtBuilder

import org.eclipse.core.databinding.observable.Realm
import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.swt.widgets.Display

/**
 * @author Frank
 *
 */
public class Snippet000HelloWorld{

	// The data model class. This is normally a persistent class of some sort.
	static class Person {
		// A property...
		@Bindable
		String name = "HelloWorld"
	}
	
	// The View's model--the root of our Model graph for this particular GUI.
	static class ViewModel {
		// The model to bind
		Person person = new Person()
	}
	
	static class View {
		private ViewModel viewModel
		
		View(def model) {
			viewModel = model
		}
		
		def createShell() {
			def swt = new SwtBuilder()
			def shell = swt.shell('Snippet000HelloWorld') {
				rowLayout()
				text(text:bind(model:viewModel.person, modelProperty:'name'))
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
	
	public static void main(String[] args){
		def model = new ViewModel()
		def shell = new View(model).open()
		shell.doMainloop()
	}
	
}
