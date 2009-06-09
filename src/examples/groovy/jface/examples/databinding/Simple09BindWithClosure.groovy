/**
 * 
 */
package groovy.jface.examples.databinding

import groovy.beans.Bindable
import groovy.jface.JFaceBuilder

import org.eclipse.core.databinding.observable.Realm
import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.swt.widgets.Display

// The View's model--the root of our Model graph for this particular GUI.
class ViewModel09 {
	// The model to bind
	@Bindable
	def text = ''
}

class View09 {
	ViewModel09 viewModel = new ViewModel09()
	
	def createShell() {
		def jface = new JFaceBuilder()
		def shell = jface.shell('Simple09ListViewer') {
			migLayout(layoutConstraints:"wrap 1", columnConstraints: "[grow, fill]", rowConstraints: "[grow, fill][]")
			text(text:bind(model:viewModel, modelProperty:'text'))
			// NOTE: Bind can not find the properties to bind to if they are hidden inside a GString.
			label(text:bind {"You have written "+viewModel.text?.size()+ " characters"})
			label('Characters left: ', layoutData:'split 2')
			// NOTE: For now you need explicitly make it a string if you are binding to a text.
			label(text:bind{(50-viewModel.text?.size()).toString()})
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
public class Simple09ListViewer{
	public static void main(String[] args){
		def display = Display.default ?: new Display()
		Realm.default = SWTObservables.getRealm(display)
		def model = new ViewModel09()
		
		def shell = new View09(viewModel: model).open()
		shell.doMainloop()
	}
}
