/**
 * 
 */
package groovy.jface.examples.databinding

import groovy.beans.Bindable
import groovy.jface.JFaceBuilder

import org.eclipse.core.databinding.observable.Realm
import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.swt.widgets.Display


/**
 * @author Frank
 *
 */
public class Simple09ListViewer{

	public static void main(String[] args){
		def display = Display.default ?: new Display()
		Realm.default = SWTObservables.getRealm(display)
		def model = new ViewModel()
		
		def shell = new View(viewModel: model).open()
		shell.doMainloop()
	}
	
	// The View's model--the root of our Model graph for this particular GUI.
	static class ViewModel {
		// The model to bind
		@Bindable
		def text = ''
	}
	
	static class View {
		ViewModel viewModel = new ViewModel()
		
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
	
}
