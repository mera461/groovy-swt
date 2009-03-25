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

// The data model class. This is normally a persistent class of some sort.
private class Person000 {
	// A property...
	@Bindable
	String name = "HelloWorld"
}

// The View's model--the root of our Model graph for this particular GUI.
private class ViewModel000 {
	// The model to bind
	Person000 person = new Person000()
}

private class View000 {
	private ViewModel000 viewModel
	
	View000(def model) {
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

/**
 * @author Frank
 *
 */
public class Snippet000HelloWorld{
	public static void main(String[] args){
		def model = new ViewModel000()
		def shell = new View000(model).open()
		shell.doMainloop()
	}
	
}
