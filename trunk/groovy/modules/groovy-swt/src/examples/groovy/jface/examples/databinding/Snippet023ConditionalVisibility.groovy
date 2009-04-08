/**
 * See http://dev.eclipse.org/viewcvs/index.cgi/org.eclipse.jface.examples.databinding/src/org/eclipse/jface/examples/databinding/snippets/Snippet023ConditionalVisibility.java?view=markup
 * for the example in java.
 *
 */
package groovy.jface.examples.databinding

import groovy.beans.Bindable
import groovy.jface.JFaceBuilder

import org.eclipse.core.databinding.observable.Realm
import org.eclipse.core.databinding.observable.list.WritableList
 
import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.jface.viewers.StructuredSelection
import org.eclipse.jface.window.Window

import org.eclipse.swt.widgets.Display


class View023 {
	
	def createShell() {
		def jface = new JFaceBuilder()
		def shell
		shell = jface.shell('Binding checked elements in CheckboxTableViewer') {
			migLayout(layoutConstraints:"wrap 1", columnConstraints: "[grow, fill]")
			composite {
				fillLayout()
				group('Type') {
					fillLayout()
					button('Text', id:'textButton', style:'RADIO')
					button('Range', id:'rangeButton', style:'RADIO')
				}
			}
			composite {
				stackLayout()
				group('Range', visible: bind (model: rangeButton, modelProperty:'selection')) {
					migLayout(layoutConstraints:"wrap 2", columnConstraints: "[right][grow, fill]")
					label('From:')
					text(style:'SINGLE, LEAD, BORDER')
					label('To:')
					text(style:'SINGLE, LEAD, BORDER')
				}
				group('Text', visible: bind (model: textButton, modelProperty:'selection')) {
					migLayout(layoutConstraints:"wrap 2", columnConstraints: "[right][grow, fill]")
					label('Text:')
					text(style:'SINGLE, LEAD, BORDER')
				}
			}
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
public class Snippet023ConditionalVisibility {
	public static void main(String[] args){
		Realm.default = SWTObservables.getRealm(Display.default ?: new Display())
		
		def shell = new View023().open()
		shell.doMainloop()
	}
	
}
