/**
 * 
 */
package groovy.jface.examples.databinding

import java.beans.PropertyChangeListener

import groovy.beans.Bindable
import groovy.jface.JFaceBuilder
import groovy.util.ObservableList

import org.eclipse.core.databinding.observable.Realm
import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.jface.viewers.ArrayContentProvider
import org.eclipse.swt.widgets.Display

import org.eclipse.core.databinding.observable.list.WritableList
import org.eclipse.core.databinding.observable.list.IListChangeListener
import org.eclipse.core.databinding.observable.list.IObservableList
import org.eclipse.core.databinding.property.Properties

//The data model class. This is normally a persistent class of some sort.
@Bindable
class Person02 {
	String name
	String city
}

// The View's model--the root of our Model graph for this particular GUI.
class ViewModel02 {
	// The model to bind
	@Bindable
	def people
	
	ViewModel02() {
		people = new WritableList(Realm.default)
		people.add(new Person02(name:"Wile E. Coyote", city:"Tucson"))
		people.add(new Person02(name:"Road Runner", city:"Lost Horse"))
		people.add(new Person02(name:"Bugs Bunny", city:"Forrest"))
	}
}

class View02 {
	ViewModel02 viewModel
	
	def createShell() {
		def jface = new JFaceBuilder()
		def shell = jface.shell('Simple02ListViewer') {
			migLayout(layoutConstraints:"wrap 1, filly", columnConstraints: "[grow, fill]", rowConstraints: "[grow, fill][]")
			list(layoutData:"hmin 50") {
				listViewer(input: bind(model:viewModel.people, modelProperty:'name'))
			}
			button("Add another one") {
				onEvent('Selection') { viewModel.people.add(new Person02(name: "John${(int) (Math.random()*1000) }"))	}
			}
			button("New List") {
				onEvent('Selection'){
					// You should NEVER re-assign the property. The binding tracks the list,
					// not the property.
					// See http://fire-change-event.blogspot.com/2009/02/bind-viewer-in-one-statement-with.html
					// DO NOT : viewModel.people = new WritableList()
					// Do this instead:
					viewModel.people.clear()
				}
			}
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
public class Simple02ListViewer{
	public static void main(String[] args){
		def display = Display.default ?: new Display()
		Realm.default = SWTObservables.getRealm(display)
		def model = new ViewModel02()
		
		def shell = new View02(viewModel: model).open()
		shell.doMainloop()
	}
	
	
}