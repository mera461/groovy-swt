/**
 * See http://dev.eclipse.org/viewcvs/index.cgi/org.eclipse.jface.examples.databinding/src/org/eclipse/jface/examples/databinding/snippets/Snippet009TableViewer.java?view=markup
 * for the example in java.
 *
 * An example showing how to create a {@link ILabelProvider label provider} that
 * to provide colors.
 */
package groovy.jface.examples.databinding

import groovy.beans.Bindable
import groovy.jface.JFaceBuilder

import org.eclipse.core.databinding.beans.BeansObservables
import org.eclipse.core.databinding.beans.BeanProperties
import org.eclipse.core.databinding.observable.Realm
import org.eclipse.core.databinding.observable.list.WritableList
import org.eclipse.core.databinding.observable.map.IObservableMap
import org.eclipse.core.databinding.observable.value.ComputedValue
 
import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.jface.viewers.ArrayContentProvider
import org.eclipse.jface.viewers.ITableColorProvider
import org.eclipse.jface.viewers.StructuredSelection
import org.eclipse.jface.window.Window

import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.widgets.Display

//TODO: Should be deleted:
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider
import org.eclipse.core.databinding.beans.BeansObservables
import org.eclipse.core.databinding.observable.Observables
import org.eclipse.jface.databinding.viewers.ViewerProperties
import org.eclipse.jface.databinding.viewers.ViewersObservables


// The data model class. This is normally a persistent class of some sort.
@Bindable
class Person018 {
	String name
	def friends = new HashSet()
	
	String toString() {name}
}

// The View's model--the root of our Model graph for this particular GUI.
class ViewModel018 {
	// The model to bind
	@Bindable
	def people
	
	ViewModel018() {
		def stan = new Person018(name:"Stan")
		def kyle = new Person018(name:"Kyle")
		def eric = new Person018(name:"Eric")
		def kenny = new Person018(name:"Kenny")
		def wendy = new Person018(name:"Wendy")
		def butters = new Person018(name:"Butters")
		
		stan.friends.addAll([kyle, eric, kenny, wendy])
		kyle.friends.addAll([stan, eric, kenny])
		eric.friends.addAll([eric])
		kenny.friends.addAll([stan, kyle, eric])
		wendy.friends.addAll([stan])
		butters.friends = new HashSet()
		
		people = new WritableList(SWTObservables.getRealm(Display.getCurrent()))
		people.addAll([stan, kyle, eric, kenny, wendy, butters])  
		
	}
}

class View018 {
	ViewModel018 viewModel
	
	def createShell() {
		def jface = new JFaceBuilder()
		def shell
		shell = jface.shell('Binding checked elements in CheckboxTableViewer') {
			migLayout(layoutConstraints:"wrap 2", columnConstraints: "[right][grow, fill]")
			label('People')
			button('Add', layoutData:'split 2') {
				onEvent(type:'Selection', closure:{
					def dlg = jface.inputDialog(shell, title:'Add Person',
											    message: 'Enter name:',
									 		    value:'<Name>',
									 		    validator: {(it==null || it.size()==0) ? "Name cannot be empty" : null})
					if (dlg.open() == Window.OK) {
						def person = new Person018(name: dlg.value)
						viewModel.people << person
						peopleViewer.selection = new StructuredSelection(person)	
					}
				})
			}
			button('Remove', id:'removeButton', layoutData:'wrap') {
				onEvent(type:'Selection', closure: {
					def selected = peopleViewer.selection
					if (selected != null && ! selected.isEmpty()) {
						def person = selected.firstElement
						if (jface.messageDialog(shell, title: 'Remove person', message:"Remove ${person.name}?")) {
							viewModel.people.remove(person)
						}
					}
				})
			}
			table(id:'t1', style:'FULL_SELECTION, BORDER', headerVisible: true, linesVisible: true, layoutData:'span 2,growx') {
				tableColumn('Name', width: 100)
				tableColumn('Friends', width: 100)
				tableViewer(id:'peopleViewer',
						    input: bind(model: viewModel.people, modelProperty:['name', 'friends']))
			}
			bind(target: removeButton, targetProperty: 'enabled',
				 model: peopleViewer, modelProperty:'name', 'model2target.converter': {t1.selectionCount>0})
			label('Name:')
			text(text: bind(peopleViewer, modelProperty:'name'))
			label('Friends:')
			table(style:'FULL_SELECTION, BORDER, CHECK', headerVisible: true, linesVisible: true,
				  enabled: bind(peopleViewer, modelProperty:'name', 'model2target.converter': {t1.selectionCount>0})) {
				tableColumn('Name', width: 100)
				checkboxTableViewer(id:'friendsViewer',
									input: bind(model: viewModel.people, modelProperty:'name'),
									checkedElements: bind(model: peopleViewer, modelProperty:'friends'))
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
public class Snippet018TableViewer {
	public static void main(String[] args){
		def display = Display.default ?: new Display()
		Realm.default = SWTObservables.getRealm(display)
		
		def model = new ViewModel018()
		def shell = new View018(viewModel: model).open()
		shell.doMainloop()
	}
	
}
