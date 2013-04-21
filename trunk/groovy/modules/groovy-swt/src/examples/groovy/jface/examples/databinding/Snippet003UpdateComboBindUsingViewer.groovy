/**
 * See http://dev.eclipse.org/viewcvs/index.cgi/org.eclipse.jface.examples.databinding/src/org/eclipse/jface/examples/databinding/snippets/SnippetUpdateComboBindUsingViewer.java?view=markup
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

public class Snippet003UpdateComboBindUsingViewer{
	public static void main(String[] args){
		def model = new ViewModel()
		def shell = new View(viewModel: model).open()
		shell.doMainloop()
	}
	



// The View's model--the root of our Model graph for this particular GUI.
@Bindable
static class ViewModel {
	def text = 'beef'
	def choices = ['pork', 'beef', 'poultry', 'vegetables']
}
	
static class View {
	ViewModel viewModel
	
	def createShell() {
		def jface = new JFaceBuilder()
		def shell = jface.shell('SnippetUpdateComboBindUsingViewer') {
			rowLayout()
			combo (style: 'READ_ONLY', selection: bind(model:viewModel, modelProperty:'text')) {
				comboViewer(input: bind(model:viewModel, modelProperty:'choices'))
			}
			button('reset collection') {
				onEvent('Selection') {
					viewModel.choices = ['Chocolate', 'Vanilla', 'Mango Parfait', 'beef', 'Cheesecake']
				}
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

}
