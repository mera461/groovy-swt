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
import org.eclipse.swt.widgets.Display

import org.eclipse.core.databinding.observable.list.WritableList


//The data model class. This is normally a persistent class of some sort.
@Bindable
class Person03 {
	String name
	String city
}

// The View's model--the root of our Model graph for this particular GUI.
class ViewModel03 {
	// The model to bind
	@Bindable
	WritableList people 
	
	ViewModel03() {
		people = new WritableList(SWTObservables.getRealm(Display.getCurrent()))
		people.add(new Person03(name:"Wile E. Coyote", city:"Tucson"))
		people.add(new Person03(name:"Road Runner", city:"Lost Horse"))
		people.add(new Person03(name:"Bugs Bunny", city:"Forrest"))
	}
}

class View03 {
	ViewModel03 viewModel
	
	def createShell() {
		def jface = new JFaceBuilder()
		def shell = jface.shell('Simple03ListViewer') {
			migLayout(layoutConstraints:"wrap 1, filly", columnConstraints: "[grow, fill]", rowConstraints: "")
			table(headerVisible: true, linesVisible: true, layoutData:'grow' ) {
				tableColumn('name', style:'LEFT', width: 100)
				tableColumn('city', style:'RIGHT', width: 100)
				tableViewer(input: bind(model:viewModel.people, modelProperty:['name', 'city']))
			}
			button("Add another one") {
				onEvent(type:'Selection', closure: { viewModel.people.add(new Person03(name: "John${(int) (Math.random()*1000) }"))	})
			}
			button("Change city") {
				onEvent(type:'Selection', closure: { viewModel.people.each {it.city = "Changed: "+ (new Date()).getTime()} })
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
public class Simple03TableViewer{
	public static void main(String[] args){
		def display = Display.default ?: new Display()
		def model = new ViewModel03()
		def shell = new View03(viewModel: model).open()
		shell.doMainloop()
	}
	
	
}
