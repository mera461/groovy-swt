/**
 * Snippet that displays how to bind the validation error of the
 * DataBindingContext to a label.
 */
package groovy.jface.examples.databinding

import groovy.beans.Bindable
import groovy.jface.JFaceBuilder

import org.eclipse.core.databinding.observable.Realm
import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.jface.viewers.ArrayContentProvider
import org.eclipse.swt.widgets.Display

import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.databinding.AggregateValidationStatus;



//The View's model--the root of our Model graph for this particular GUI.
@Bindable
class ViewModel004 {
	String value
}

class View004 {
	ViewModel004 viewModel
	def value
	
	def createShell() {
		def jface = new JFaceBuilder()
		def shell = jface.shell('Data Binding Snippet 004') {
			migLayout(layoutConstraints:"wrap 2", columnConstraints: "[right][grow, fill]", rowConstraints: "")
			label("Enter '5' to be valid:")
			text(text: bind(model: viewModel,
						    modelProperty: 'value',
					        'target2model.afterConvertValidator': {
								it == "5" ? Status.OK_STATUS : ValidationStatus.error("the value was '$it', not '5'")
							})
			)
			label('Error: ')
			label(foreground: '#FF0000',
				  text: bind(new AggregateValidationStatus(jface.dataBindingContext.getBindings(),
							                          AggregateValidationStatus.MAX_SEVERITY)))
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
public class Snippet004DatabindingContextErrorLabel{
	public static void main(String[] args){
		def shell = new View004(viewModel: new ViewModel004()).open()
		shell.doMainloop()
	}
	
}
