/**
 * Snippet that validates values across multiple bindings on change of each
 * observable. If the values of the target observables are not equal the model
 * is not updated. When the values are equal they will be written to sysout.
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

import java.beans.PropertyChangeListener



//The View's model--the root of our Model graph for this particular GUI.
@Bindable
class ViewModel011 {
	String value1
	String value2
}

class View011 {
	ViewModel011 viewModel
	def value
	
	def createShell() {
		def jface = new JFaceBuilder()
		def shell = jface.shell('Data Binding Snippet 011') {
			migLayout(layoutConstraints:"wrap 2", columnConstraints: "[left][grow, fill]", rowConstraints: "")
			text(text: bind(model: viewModel,
						    modelProperty: 'value1',
					        'target2model.afterConvertValidator': {
								it != viewModel.value2 ? ValidationStatus.ok() : ValidationStatus.error("values cannot be the same")
							})
			)
			text(text:  bind(model: viewModel,
				    	modelProperty: 'value2',
				    	'target2model.afterConvertValidator': {
							it != viewModel.value1 ? ValidationStatus.ok() : ValidationStatus.error("values cannot be the same")
						})
			)
		}
		
		// DEBUG 
		viewModel.addPropertyChangeListener({println "value1=${viewModel.value1}";println "value2=${viewModel.value2}"} as PropertyChangeListener)
		
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
public class Snippet011DatabindingContextErrorLabel{
	public static void main(String[] args){
		def shell = new View011(viewModel: new ViewModel011()).open()
		shell.doMainloop()
	}
	
}
