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
import org.eclipse.jface.viewers.ViewerFilter
import org.eclipse.swt.widgets.Display

// The data model class. This is normally a persistent class of some sort.
@Bindable
class Person025 {
	String name = 'Donald Duck'
	Person025 father
	Person025 mother
}
 
class View025 {
	@Bindable
	def people

	private static Person025 UNKNOWN = new Person025(name: "unknown", father:null, mother:null); 
	
	def createShell() {
		people = new WritableList(SWTObservables.getRealm(Display.getCurrent()))
		def fergus = new Person025(name:"Fergus McDuck", mother:UNKNOWN, father:UNKNOWN)
		def downy = new Person025(name:"Downy O'Drake", mother:UNKNOWN, father:UNKNOWN)
		def scrooge = new Person025(name:"Scrooge McDuck", mother:downy, father:fergus)
		def hortense = new Person025(name:"Hortense McDuck", mother:downy, father:fergus)
		def quackmore = new Person025(name:"Quackmore Duck", mother:UNKNOWN, father:UNKNOWN)
		def della = new Person025(name:"Della Duck", mother:hortense, father:quackmore)
		def donald = new Person025(name:"Donald Duck", mother:hortense, father:quackmore)
		
		people << UNKNOWN << downy << fergus << scrooge << quackmore <<
			      hortense << della << donald
		
		def jface = new JFaceBuilder()
		def shell = jface.shell('Snippet025TableViewerWithDerivedColumns') {
			migLayout(layoutConstraints:"wrap 2", columnConstraints: "[right][grow, fill]")
			table(style:'FULL_SELECTION, BORDER', headerVisible: true, linesVisible: true, layoutData:'span 2') {
				tableColumn('Name', width: 100)
				tableColumn('Mother', width: 100)
				tableColumn('Father', width: 100)
				tableColumn('Maternal grandmother', width: 120)
				tableColumn('Maternal grandfather', width: 120)
				tableColumn('Paternal grandmother', width: 120)
				tableColumn('Paternal grandfather', width: 120)
				tableViewer(id:'v1', input: bind(model:people,  modelProperty:['name', 'mother.name', 'father.name',
				                                                         'mother.mother.name', 'mother.father.name',
				                                                         'father.mother.name', 'father.father.name']))
			    v1.addFilter([select: {viewer, parentElement, element -> return element != UNKNOWN}] as ViewerFilter)
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
public class Snippet025TableViewerWithPropertyDerivedColumns {
	public static void main(String[] args){
		def shell = new View025().open()
		shell.doMainloop()
	}
}
