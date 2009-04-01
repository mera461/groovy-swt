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
class Person05 {
	String name
	String city
}

// The View's model--the root of our Model graph for this particular GUI.
@Bindable
class ViewModel05 {
	// The model to bind
	def people
	
	def cities = ['NY', 'LA', 'NB']
	
	ViewModel05() {
		people = new WritableList(Realm.default)
		people.add(new Person05(name:"Wile E. Coyote", city:"Tucson"))
		people.add(new Person05(name:"Road Runner", city:"Lost Horse"))
		people.add(new Person05(name:"Bugs Bunny", city:"Forrest"))
	}
}

class View05 {
	ViewModel05 viewModel
	
	def createShell() {
		def jface = new JFaceBuilder()
		def shell = jface.shell('Simple05ListViewer') {
			migLayout(layoutConstraints:"wrap 2", columnConstraints: "[right][grow, fill]", rowConstraints: "")
			label('Name:')
			// Combo with model objects
			combo(style:'READ_ONLY') {
				comboViewer(input: bind(model:viewModel.people, modelProperty:'name'))
			}
			label('Born in:')
			// combo with "static" data
			combo(style:'READ_ONLY') {
				comboViewer(input: bind(model:viewModel, modelProperty:'cities'))
			}
			// Buttons panel
			composite(layoutData:"grow, span 2") {
				migLayout(layoutConstraints:"wrap 2",columnConstraints:"")
				button("Add person") {
					onEvent(type:'Selection', closure: { 
						// Here you MUST add to the list and reassigning the variable will not work
						viewModel.people.add(new Person04(name: "John${(int) (Math.random()*1000) }"))
					})
				}
				button("Empty person list") {
					onEvent(type:'Selection', closure: { 
						viewModel.people.clear()
					})
				}
				button("Add city") {
					onEvent(type:'Selection', closure: {
						def newList = []
						newList.addAll(viewModel.cities)
						newList << "A4" 
						// Here you MUST reassign the variable, adding to the list is not enough
						// as the list is not monitored for changes
						viewModel.cities = newList
					})
				}
				button("Empty city list") {
					onEvent(type:'Selection', closure: {
						viewModel.cities = []
					})
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
public class Simple05ListViewer{
	public static void main(String[] args){
		def display = Display.default ?: new Display()
		Realm.default = SWTObservables.getRealm(display)
		def model = new ViewModel05()
		
		def shell = new View05(viewModel: model).open()
		shell.doMainloop()
	}
	
	
}
