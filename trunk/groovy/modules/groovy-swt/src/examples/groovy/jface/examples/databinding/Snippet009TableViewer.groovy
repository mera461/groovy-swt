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
import org.eclipse.core.databinding.observable.map.IObservableMap;

import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.jface.viewers.ArrayContentProvider
import org.eclipse.jface.viewers.ITableColorProvider

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
class Person009 {
	String name
}

// The View's model--the root of our Model graph for this particular GUI.
class ViewModel009 {
	// The model to bind
	@Bindable
	def people
	
	ViewModel009() {
		people = new WritableList(SWTObservables.getRealm(Display.getCurrent()))
		people << new Person009(name:"Steve Northover")
		people << new Person009(name:"Grant Gayed")
		people << new Person009(name:"Veronika Irvine")
		people << new Person009(name:"Mike Wilson")
		people << new Person009(name:"Christophe Cornu")
		people << new Person009(name:"Lynne Kues")
		people << new Person009(name:"Silenio Quarti")
	}
}

class View009 {
	ViewModel009 viewModel
	
	def createShell() {
		def jface = new JFaceBuilder()
		def shell = jface.shell('Gender Bender') {
			fillLayout()
			table(style:'FULL_SELECTION, BORDER', linesVisible: true) {
				tableViewer(input: bind(model: viewModel.people, modelProperty:'name'))
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
public class Snippet009TableViewer {
	public static void main(String[] args){
		def display = Display.default ?: new Display()
		Realm.default = SWTObservables.getRealm(display)
		
		def model = new ViewModel009()
		def shell = new View009(viewModel: model).open()
		shell.doMainloop()
	}
	
}
