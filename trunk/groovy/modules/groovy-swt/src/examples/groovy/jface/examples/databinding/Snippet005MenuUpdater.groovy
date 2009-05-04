/**
 * Snippet that displays how to bind the validation error of the
 * DataBindingContext to a label.
 */
package groovy.jface.examples.databinding

import groovy.beans.Bindable
import groovy.jface.JFaceBuilder

import org.eclipse.core.databinding.observable.Realm
import org.eclipse.core.databinding.observable.list.WritableList
import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.jface.internal.databinding.provisional.swt.MenuUpdater

import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.MenuItem
import org.eclipse.swt.SWT


class View005 {
	
	def createShell() {
		def jface = new JFaceBuilder()

		WritableList menuItemStrings = new WritableList()
		menuItemStrings.addAll(['open', 'exit', 'sep'])
		def addMenuItem
		addMenuItem = {
				println "Adding item."
				menuItemStrings << new Date().toString()
				jface.timerExec(5000, addMenuItem)
		}
		jface.asyncExec(addMenuItem)
		
		def shell
		def submenu
		shell = jface.shell('Data Binding Snippet 005') {
			menu(style:'BAR') {
				def testmenu = menuItem("&Test Menu", style:"CASCADE")
				submenu = jface.menu( style:"DROP_DOWN")	{
					menuItem(style:"PUSH", "&Open file")
					menuItem(style:"PUSH", "E&xit"){
						onEvent('Selection'){ shell.dispose() }
					}
					menuSeparator()
				}
				testmenu.menu = submenu
			}
		}
		
		// No inner classes in groovy, so doing it the manual way works:
		//    new LocalMenuUpdater(submenu, menuItemStrings)
		// But this is a shorter way of doing it.
		def updater = [
		updateMenu: {
			println('updating menu')
			// add new items from the list
			def items = submenu.items
			for (int i=items.size(); i<menuItemStrings.size(); i++) {
				def item = new MenuItem(submenu, SWT.NONE)
				item.text = menuItemStrings[i]
			}
		}]

		ProxyGenerator.instantiateAggregateFromBaseClass( updater, MenuUpdater.class, submenu) 
		
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

class LocalMenuUpdater extends MenuUpdater {
	def submenu
	def menuItemStrings
	LocalMenuUpdater(def submenu, def menuItemStrings) {
		super(submenu)
		this.submenu=submenu
		this.menuItemStrings = menuItemStrings
	}
	
	void updateMenu() {
		println('updating menu')
		// add new items from the list
		def items = submenu.items
		for (int i=items.size(); i<menuItemStrings.size(); i++) {
			def item = new MenuItem(submenu, SWT.NONE)
			item.text = menuItemStrings[i]
		}
	}
	
}

/**
 * @author Frank
 *
 */
public class Snippet005MenuUpdater {
	public static void main(String[] args){
		def shell = new View005().open()
		shell.doMainloop()
	}
	
}
