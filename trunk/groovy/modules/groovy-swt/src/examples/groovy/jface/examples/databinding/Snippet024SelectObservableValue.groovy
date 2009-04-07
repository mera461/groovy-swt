/**
 * See http://dev.eclipse.org/viewcvs/index.cgi/org.eclipse.jface.examples.databinding/src/org/eclipse/jface/examples/databinding/snippets/Snippet024CheckboxTableViewerCheckedSelection.java?view=markup
 * for the example in java.
 *
 * Demonstrate usage of SelectObservableValue
 */
package groovy.jface.examples.databinding

import groovy.beans.Bindable
import groovy.jface.JFaceBuilder

import org.eclipse.core.databinding.observable.Realm
import org.eclipse.core.databinding.observable.list.WritableList
import org.eclipse.core.databinding.observable.value.SelectObservableValue
import org.eclipse.jface.databinding.viewers.ViewerProperties
 
import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.jface.viewers.ArrayContentProvider
import org.eclipse.swt.widgets.Display

@Bindable
public static class Color024 {
	public static final Color024 RED = new Color024(name:"Red")
	public static final Color024 ORANGE = new Color024(name:"Orange")
	public static final Color024 YELLOW = new Color024(name:"Yellow")
	public static final Color024 GREEN = new Color024(name:"Green")
	public static final Color024 BLUE = new Color024(name:"Blue")
	public static final Color024 INDIGO = new Color024(name:"Indigo")
	public static final Color024 VIOLET = new Color024(name:"Violet")
	public static def values() { [ RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET] }

	String name

	public String toString() {name}
}

class View024 {
	def createShell() {
		def jface = new JFaceBuilder()
		def shell
		shell = jface.shell('Snippet024SelectObservableValue', size:[400,300]) {
			migLayout(layoutConstraints:"wrap 2", columnConstraints: "[right][grow, fill]")
			def colors = Color024.values()
			list() {
				listViewer(id:'colorList', input: bind(model:colors, modelProperty:'name'))
				//listViewer(id:'colorList', contentProvider: new ArrayContentProvider(), input: colors)
			}
			group('Radio Group') {
				migLayout(layoutConstraints:"wrap 1", columnConstraints: "[grow, fill]")
				SelectObservableValue radioGroup = new SelectObservableValue()
				colors.each {
					def b = button(text:it, style:'RADIO')
					radioGroup.addOption(it, SWTObservables.observeSelection(b))
				}
				bind(target:radioGroup, model: ViewerProperties.singleSelection().observe(colorList))
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
public class Snippet024SelectObservableValue {
	public static void main(String[] args){
		Realm.default = SWTObservables.getRealm(Display.default ?: new Display())
		def shell = new View024().open()
		shell.doMainloop()
	}
	
}
