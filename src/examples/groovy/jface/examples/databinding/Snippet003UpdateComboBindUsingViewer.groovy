/**
 * See http://dev.eclipse.org/viewcvs/index.cgi/org.eclipse.jface.examples.databinding/src/org/eclipse/jface/examples/databinding/snippets/Snippet003UpdateComboBindUsingViewer.java?view=markup
 * for the example in java.
 *
 * Shows how to bind a Combo so that when update its items, the selection is
 * retained if at all possible.
 */
package groovy.jface.examples.databinding

import groovy.beans.Bindable
import groovy.jface.JFaceBuilder

import org.eclipse.core.databinding.observable.Realm
import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.jface.viewers.ArrayContentProvider
import org.eclipse.swt.widgets.Display

// The View's model--the root of our Model graph for this particular GUI.
@Bindable
class ViewModel003 {
	def text = 'beef'
	def choices = ['pork', 'beef', 'poultry', 'vegetables']
}

class View003 {
	ViewModel003 viewModel
	
	def createShell() {
		def jface = new JFaceBuilder()
		def shell = jface.shell('Snippet003UpdateComboBindUsingViewer') {
			rowLayout()
			combo (style: 'READ_ONLY', selection: bind(model:viewModel, modelProperty:'text')) {
				comboViewer(input: bind(model:viewModel, modelProperty:'choices'))
			}
			button('reset collection') {
				onEvent(type:'Selection', closure: {
					viewModel.choices = ['Chocolate', 'Vanilla', 'Mango Parfait', 'beef', 'Cheesecake']
				})
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
public class Snippet003UpdateComboBindUsingViewer{
	public static void main(String[] args){
		def model = new ViewModel003()
		def shell = new View003(viewModel: model).open()
		shell.doMainloop()
	}
	
}
