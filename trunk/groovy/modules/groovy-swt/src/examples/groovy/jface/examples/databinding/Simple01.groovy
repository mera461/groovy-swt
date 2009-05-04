/**
 * See here http://www.vogella.de/articles/EclipseDataBinding/article.html
 * for the example in java 
 */
package groovy.jface.examples.databinding

import groovy.beans.Bindable
import groovy.swt.SwtBuilder

import org.eclipse.core.databinding.DataBindingContext
import org.eclipse.core.databinding.observable.Realm
import org.eclipse.core.databinding.beans.BeansObservables
import org.eclipse.core.databinding.observable.value.IObservableValue
import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Display


@Bindable
private class Person01a {
	String firstName;
	String lastName;
	boolean married;
	String gender;
	Integer age;
}


/**
 * @author Frank
 *
 */
public class Simple01{

	 
	 
	private Person01a person
	private def firstName
	private def ageText
	private def marriedButton
	private def genderCombo
	
	def createPartControl() {
		person = new Person01a(firstName:'John', lastName:'Doo', gender:'Male', age:12, married:true)
		def swt = new SwtBuilder()
		def shell = swt.shell('Simple01', size:[400,200]) {
			migLayout(layoutConstraints:"wrap 2", columnConstraints: "[left]10[left, grow]", rowConstraints: "")
			label('Firstname:')
			firstName = text(layoutData:'growx')
			label('Age:')
			ageText = text(layoutData:'growx')
			label('Married:')
			marriedButton = button(style: 'CHECK')
			label('Gender:')
			genderCombo = combo()
			genderCombo.add("Male");
			genderCombo.add("Female");
			button('Write model') {
				onEvent('Selection') {
					println person.firstName
					println person.age
					println person.married
					println person.gender
				}
			}
			button('Change model') {
				onEvent('Selection') {
					person.with {
						firstName = 'Lars'
						age++
						married = ! married
						gender = (gender == 'Male') ? 'Female' : 'Male'
					}
				}
			}
		}
		return shell
	}
	
	def manuallyBindValues() {
		// The DataBindingContext object will manage the databindings
		DataBindingContext bindingContext = new DataBindingContext()
		IObservableValue uiElement
		IObservableValue modelElement
		// Lets bind it
		uiElement = SWTObservables.observeText(firstName, SWT.Modify)
		modelElement = BeansObservables.observeValue(person, "firstName")
		// The bindValue method call binds the text element with the model
		bindingContext.bindValue(uiElement, modelElement, null, null)
		
		uiElement = SWTObservables.observeText(ageText, SWT.Modify)
		modelElement = BeansObservables.observeValue(person, "age")
		// Remember the binding so that we can listen to validator problems
		// See below for usage
		bindingContext.bindValue(uiElement, modelElement, null, null)
		
		uiElement = SWTObservables.observeSelection(marriedButton)
		modelElement = BeansObservables.observeValue(person, "married")
		
		bindingContext.bindValue(uiElement, modelElement, null, null)
		
		uiElement = SWTObservables.observeSelection(genderCombo)
		modelElement = BeansObservables.observeValue(person, "gender")
		
		bindingContext.bindValue(uiElement, modelElement, null, null)
	}
	
	def open() {
		def shell
		def display = new Display()
		Realm.runWithDefault(SWTObservables.getRealm(display), {
			shell = createPartControl()
			manuallyBindValues()
		})
		
		// display it
		shell.doMainloop()
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args){
		new Simple01().open()
	}
	
}
