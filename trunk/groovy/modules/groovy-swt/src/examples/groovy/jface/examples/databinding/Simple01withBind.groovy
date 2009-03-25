/**
 * See http://www.vogella.de/articles/EclipseDataBinding/article.html
 * for the example in java 
 */
package groovy.jface.examples.databinding

import groovy.beans.Bindable
import groovy.swt.SwtBuilder

import org.eclipse.core.databinding.observable.Realm
import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.swt.widgets.Display


@Bindable
private class Person01 {
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
public class Simple01withBind{
	
	private Person01 person
	
	def createPartControl() {
		person = new Person01(firstName:'John', lastName:'Doo', gender:'Male', age:12, married:true)
		def swt = new SwtBuilder()
		def shell = swt.shell('Simple01', size:[400,200]) {
			migLayout(layoutConstraints:"wrap 2", columnConstraints: "[left]10[left, grow]", rowConstraints: "")
			label('Firstname:')
			text(text:bind(model:person, modelProperty:'firstName'), layoutData:'growx')
			label('Age:')
			text(text:bind(model:person, modelProperty:'age'), layoutData:'growx')
			label('Married:')
			button(style: 'CHECK', selection:bind(model:person, modelProperty:'married') )
			label('Gender:')
			def genderCombo = combo(selection:bind(model:person, modelProperty:'gender'))
			genderCombo.add("Male");
			genderCombo.add("Female");
			button('Write model') {
				onEvent(type:'Selection', closure:{
					println person.firstName
					println person.age
					println person.married
					println person.gender
				})
			}
			button('Change model') {
				onEvent(type:'Selection', closure:{
					person.with {
						firstName = 'Lars'
						age++
						married = ! married
						gender = (gender == 'Male') ? 'Female' : 'Male'
					}
				})
			}
		}
		return shell
	}

	def open() {
		def shell
		def display = new Display()
		Realm.runWithDefault(SWTObservables.getRealm(display), {
			shell = createPartControl()
		})
		
		// display it
		shell.doMainloop()
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args){
		new Simple01withBind().open()
	}
	
}
