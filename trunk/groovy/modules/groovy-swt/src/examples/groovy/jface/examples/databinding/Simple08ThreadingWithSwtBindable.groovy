/**
 * 
 */
package groovy.jface.examples.databinding

import java.beans.PropertyChangeListener

import groovy.jface.JFaceBuilder
import groovy.util.ObservableList

import org.eclipse.core.databinding.observable.Realm
import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.jface.viewers.ViewerSorter
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Display

//NOTE: A customized Bindable to fire events within the correct Realm
import groovy.swt.databinding.Bindable 
//NOTE: A customized Writeable that allows access from all threads
import groovy.swt.databinding.WritableList


/**
 * @author Frank
 *
 */
public class Simple08ThreadingWithSwtBindable {

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
			people = new WritableList(JFaceBuilder.realm)
			people.add(new Person(name:"Wile E. Coyote", city:"Tucson"))
			people.add(new Person(name:"Road Runner", city:"Lost Horse"))
			people.add(new Person(name:"Bugs Bunny", city:"Forrest"))
		}
	}
	
	static class View {
		ViewModel viewModel
		
		def createShell() {
			def jface = new JFaceBuilder()
			def shell = jface.shell('Simple08ListViewer') {
				migLayout(layoutConstraints:"wrap 1, filly", columnConstraints: "[grow, fill]", rowConstraints: "[grow, fill][]")
				table(headerVisible: true, linesVisible: true, layoutData:'hmin 50' ) {
					tableColumn('name', width: 100)
					tableColumn('city', width: 100)
					tableViewer(id:'viewer',
								input: bind(model:viewModel.people, modelProperty:['name', 'city']))
				}
				button("Add another one") {
					onEvent('Selection') { addOne(viewModel)	}
				}
				button("Remove one") {
					onEvent('Selection') { removeOne(viewModel) }
				}
			}
			return shell
		}
		
		def addOne(def viewModel) {
			Thread.start {
				(1..20).each {
					// NOTE: No need to do this anymore: JFaceBuilder.realmAsyncExec {
					viewModel.people.add(new Person(name: "John${(int) (Math.random()*1000) }"))
					Thread.sleep(1000)
				}
			}
		}
	
		def removeOne(def viewModel) {
			Thread.start {
				(1..20).each {
					// NOTE: No need to do this anymore: JFaceBuilder.realmAsyncExec {
					//JFaceBuilder.realmAsyncExec {
					if (viewModel.people.size()>0) viewModel.people.remove(0)
					Thread.sleep(1000)
				}
			}
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
