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
import org.eclipse.jface.viewers.ViewerSorter
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Display

import org.eclipse.core.databinding.observable.list.WritableList


//The data model class. This is normally a persistent class of some sort.
@Bindable
class Person06 {
	String name
	String city
}

// The View's model--the root of our Model graph for this particular GUI.
class ViewModel06 {
	// The model to bind
	@Bindable
	WritableList people 
	
	ViewModel06() {
		people = new WritableList(SWTObservables.getRealm(Display.getCurrent()))
		people.add(new Person06(name:"Wile E. Coyote", city:"Tucson"))
		people.add(new Person06(name:"Road Runner", city:"Lost Horse"))
		people.add(new Person06(name:"Bugs Bunny", city:"Forrest"))
	}
}

class View06 {
	ViewModel06 viewModel
	
	def createShell() {
		def jface = new JFaceBuilder()
		def shell = jface.shell('Simple06ListViewer') {
			migLayout(layoutConstraints:"wrap 1, filly", columnConstraints: "[grow, fill]", rowConstraints: "")
			label('DEFAULT sortable:')
			table(headerVisible: true, linesVisible: true, layoutData:'grow' ) {
				tableColumn('name', width: 100)
				tableColumn('city', width: 100)
				tableViewer(id:'viewer',
						    input: bind(model:viewModel.people, modelProperty:['name', 'city']))
			}
			label('NOT sortable:')
			table(headerVisible: true, linesVisible: true, layoutData:'grow' ) {
				tableColumn('name', width: 100)
				tableColumn('city', width: 100)
				tableViewer(id:'viewer',
						    input: bind(model:viewModel.people, modelProperty:['name', 'city']),
						    sortable: false)
			}
			label('Sortable on one column:')
			table(headerVisible: true, linesVisible: true, layoutData:'grow' ) {
				tableColumn('name', width: 100)
				tableColumn('city', width: 100)
				tableViewer(id:'viewer',
						    input: bind(model:viewModel.people, modelProperty:['name', 'city']),
						    sortable: [0])
			}
			button("Add another one") {
				onEvent('Selection') { viewModel.people.add(new Person06(name: "John${(int) (Math.random()*1000) }"))	}
			}
			button("Change city") {
				onEvent('Selection') { viewModel.people.each {it.city = "Changed: "+ (new Date()).getTime()} }
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
public class Simple06TableViewer{
	public static void main(String[] args){
		def display = Display.default ?: new Display()
		def model = new ViewModel06()
		def shell = new View06(viewModel: model).open()
		shell.doMainloop()
	}
	
	
}
