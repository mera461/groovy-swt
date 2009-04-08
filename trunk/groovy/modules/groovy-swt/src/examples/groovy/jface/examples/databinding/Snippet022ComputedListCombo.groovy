/**
 * See http://dev.eclipse.org/viewcvs/index.cgi/org.eclipse.jface.examples.databinding/src/org/eclipse/jface/examples/databinding/snippets/Snippet022ComputedListCombo.java?view=markup
 * for the example in java.
 *
 * Snippet 022: Binding to the checked elements in a CheckboxTableViewer.
 */
package groovy.jface.examples.databinding

import groovy.beans.Bindable
import groovy.jface.JFaceBuilder

import org.eclipse.core.databinding.observable.Realm
import org.eclipse.core.databinding.observable.list.WritableList
import org.eclipse.core.databinding.observable.list.ComputedList

import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.jface.databinding.viewers.ObservableListContentProvider
import org.eclipse.jface.viewers.StructuredSelection
import org.eclipse.jface.window.Window

import org.eclipse.swt.widgets.Display

// The data model class. This is normally a persistent class of some sort.
@Bindable
class Thing022 {
	String name
	boolean female
	boolean male
	String toString() {name}
}

// The View's model--the root of our Model graph for this particular GUI.
class ViewModel022 {
	// The model to bind
	@Bindable
	def things
	
	ViewModel022() {
		things = new WritableList(SWTObservables.getRealm(Display.getCurrent()))

		things<<new Thing022(name:"Alice", female:true, male:false)
		things<<new Thing022(name:"Beth", female:true, male:false)
		things<<new Thing022(name:"Cathy", female:true, male:false)
		things<<new Thing022(name:"Arthur", female:false, male:true)
		things<<new Thing022(name:"Bob", female:false, male:true)
		things<<new Thing022(name:"Curtis", female:false, male:true)
		things<<new Thing022(name:"Snail", female:true, male:true)
		things<<new Thing022(name:"Nail", female:false, male:false)		
	}
}

class View022 {
	ViewModel022 viewModel
	
	def createShell() {
		def jface = new JFaceBuilder()
		def shell
		shell = jface.shell('Snippet022ComputedListCombo') {
			migLayout(layoutConstraints:"wrap 1", columnConstraints: "[grow, fill]")
			group('Filter') {
				migLayout(layoutConstraints:"", columnConstraints: "")
				button(id:'male', 'Male', style:'CHECK')
				button(id:'female', 'Female', style:'CHECK')
			}
			def femaleObservable = SWTObservables.observeSelection(female)
			def maleObservable = SWTObservables.observeSelection(male)
			combo(style:'DROP_DOWN, READ_ONLY') {
				comboViewer(contentProvider: new ObservableListContentProvider(),
						    input: [calculate: {  viewModel.things.findAll {
				    					(!femaleObservable.value || it.female) &&
				    					(!maleObservable.value || it.male)
				    		                                }
						                       }] as ComputedList)
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
public class Snippet022ComputedListCombo {
	public static void main(String[] args){
		Realm.default = SWTObservables.getRealm(Display.default ?: new Display())
		
		def model = new ViewModel022()
		def shell = new View022(viewModel: model).open()
		shell.doMainloop()
	}
	
}
