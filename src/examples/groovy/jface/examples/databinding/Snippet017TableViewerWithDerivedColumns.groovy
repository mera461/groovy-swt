/**
 * See http://dev.eclipse.org/viewcvs/index.cgi/org.eclipse.jface.examples.databinding/src/org/eclipse/jface/examples/databinding/snippets/Snippet017TableViewerWithDerivedColumns.java?view=markup
 * for the example in java.
 *
 * Hello, databinding. Bind changes in a GUI to a Model object but don't worry
 * about propogating changes from the Model to the GUI.
 * <p>
 * Illustrates the basic Model-ViewModel-Binding-View architecture typically
 * used in data binding applications.
 */
package groovy.jface.examples.databinding

import groovy.beans.Bindable
import groovy.jface.JFaceBuilder

import org.eclipse.core.databinding.beans.BeansObservables
import org.eclipse.core.databinding.beans.BeanProperties
import org.eclipse.core.databinding.observable.Realm
import org.eclipse.core.databinding.observable.list.WritableList
import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.jface.databinding.viewers.ViewerProperties
import org.eclipse.swt.widgets.Display

// The data model class. This is normally a persistent class of some sort.
@Bindable
class Person017 {
	String name = 'Donald Duck'
	Person017 father
	Person017 mother
}
 
class View017 {
	@Bindable
	def people

	private static Person017 UNKNOWN = new Person017(name: "unknown", father:null, mother:null); 
	
	def createShell() {
		people = new WritableList(SWTObservables.getRealm(Display.getCurrent()))
		def fergus = new Person017(name:"Fergus McDuck", mother:UNKNOWN, father:UNKNOWN)
		def downy = new Person017(name:"Downy O'Drake", mother:UNKNOWN, father:UNKNOWN)
		def scrooge = new Person017(name:"Scrooge McDuck", mother:downy, father:fergus)
		def hortense = new Person017(name:"Hortense McDuck", mother:downy, father:fergus)
		def quackmore = new Person017(name:"Quackmore Duck", mother:UNKNOWN, father:UNKNOWN)
		def della = new Person017(name:"Della Duck", mother:hortense, father:quackmore)
		def donald = new Person017(name:"Donald Duck", mother:hortense, father:quackmore)
		
		people << UNKNOWN << downy << fergus << scrooge << quackmore <<
			      hortense << della << donald
		
		def jface = new JFaceBuilder()
		def shell = jface.shell('Snippet017TableViewerWithDerivedColumns') {
			migLayout(layoutConstraints:"wrap 2", columnConstraints: "[right][grow, fill]")
			table(style:'FULL_SELECTION, BORDER', headerVisible: true, linesVisible: true, layoutData:'span 2') {
				tableColumn('Name', width: 100)
				tableColumn('Mother', width: 100)
				tableColumn('Father', width: 100)
				tableColumn('Grandmother', width: 100)
				tableViewer(id:'v1', input: bind(people,  modelProperty:['name', 'mother.name', 'father.name', 'mother.mother.name']))
			}
			label('Name:')
			text(text: bind(model: v1, modelProperty:'name'))
			label('Mother:')
			combo(selection: bind(v1, modelProperty:'mother.name')) {
				comboViewer(input: bind(people, modelProperty:'name'))
			}
			label('Father:')
			combo(selection: bind(v1, modelProperty:'father.name')) {
				comboViewer(input: bind(people, modelProperty:'name'))
			}
		}
		return shell
	}

	def open() {
		def shell
		def display = new Display()
		Realm.runWithDefault(SWTObservables.getRealm(display), {
			shell = createShell()
		})
		return shell
	}
}

/**
 * @author Frank
 *
 */
public class Snippet017TableViewerWithDerivedColumns {
	public static void main(String[] args){
		def shell = new View017().open()
		shell.doMainloop()
	}
	
}
