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


/**
 * @author Frank
 *
 */
public class Snippet009TableViewer {
	public static void main(String[] args){
		def display = Display.default ?: new Display()
		Realm.default = SWTObservables.getRealm(display)

		def model = new ViewModel()
		def shell = new View(viewModel: model).open()
		shell.doMainloop()
	}


	// The data model class. This is normally a persistent class of some sort.
	@Bindable
	static class Person {
		String name
	}

	// The View's model--the root of our Model graph for this particular GUI.
	static class ViewModel {
		// The model to bind
		@Bindable
		def people

		ViewModel() {
			people = new WritableList(SWTObservables.getRealm(Display.getCurrent()))
			people << new Person(name:"Steve Northover")
			people << new Person(name:"Grant Gayed")
			people << new Person(name:"Veronika Irvine")
			people << new Person(name:"Mike Wilson")
			people << new Person(name:"Christophe Cornu")
			people << new Person(name:"Lynne Kues")
			people << new Person(name:"Silenio Quarti")
		}
	}

	static class View {
		ViewModel viewModel

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
			Realm.runWithDefault(Realm.default, { shell = createShell() })
			return shell
		}
	}

}
