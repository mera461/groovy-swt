/**
 * 
 */
package groovy.jface.examples.databinding

import java.beans.PropertyChangeListener

import groovy.beans.Bindable
import groovy.jface.JFaceBuilder

import org.eclipse.core.databinding.observable.Realm
import org.eclipse.core.databinding.observable.list.WritableList
import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.swt.widgets.Display



/**
 * @author Frank
 *
 */
public class Simple04TableViewer{
	
	public static void main(String[] args){
		def display = Display.default ?: new Display()
		def model = new ViewModel()
		def shell = new View(viewModel: model).open()
		shell.doMainloop()
	}
	
	
	//The data model class. This is normally a persistent class of some sort.
	@Bindable
	static class Person {
		String name
		String city
	}
	
	// The View's model--the root of our Model graph for this particular GUI.
	static class ViewModel {
		// The model to bind
		@Bindable
		WritableList people
		
		ViewModel() {
			people = new WritableList(SWTObservables.getRealm(Display.getCurrent()))
			people.add(new Person(name:"Wile E. Coyote", city:"Tucson"))
			people.add(new Person(name:"Road Runner", city:"Lost Horse"))
			people.add(new Person(name:"Bugs Bunny", city:"Forrest"))
		}
	}
	
	static class View {
		ViewModel viewModel
		
		def createShell() {
			def jface = new JFaceBuilder()
			def shell = jface.shell('Simple04ListViewer') {
				migLayout(layoutConstraints:"wrap 1, filly", columnConstraints: "[grow, fill]", rowConstraints: "")
				table(layoutData:'hmin 50' ) {
					tableViewer(input: bind(model:viewModel.people, modelProperty:['name', 'city']))
				}
				button("Add another one") {
					onEvent('Selection'){ viewModel.people.add(new Person(name: "John${(int) (Math.random()*1000) }"))	}
				}
				button("Change city") {
					onEvent('Selection'){ viewModel.people.each {it.city = "Changed: "+ (new Date()).getTime()} }
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
}
