/*
 * Created on Feb 25, 2004
 *
 */
package groovy.swt

import groovy.jface.JFaceBuilder
import junit.framework.TestCase

import org.eclipse.core.databinding.observable.Realm
import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.jface.window.ApplicationWindow
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell

/**
 */
public class SwtTest extends TestCase {
	public void testSwt() {
		Shell shell = new Shell()
		shell.dispose()
	}

	
	/**
	 * NOTE::
	 * Because of GROOVY-4737, it will throw a MissingFieldException on accessing id attributes
	 * if the class is neested into another class.
	 *
	 */
	public void testWithIds() {
		def swt = new SwtBuilder()
		def localVar = null
		swt.shell('title') {
			label('byAttr', id:'byAttr')
			byExpr = label('byExpr')
			localVar = label('localVar')
		}
		assert localVar != null
		assert swt.getVariables().containsKey('byAttr')
		assert swt.getVariables().containsKey('byExpr')
		assert !swt.getVariables().containsKey('localVar')

		swt.shell('test') {
			composite {
				label('label1', id:'label1')
			}
			assert 'label1' == swt.label1.text
			assert 'label1' == label1.text
		}
		def jface = new JFaceBuilder()
		def shell
		Realm.runWithDefault(Realm.getDefault(), {
			shell = jface.shell('test1') {
				list() {
					listViewer(id:'v1', input: bind([],  modelProperty:'name'))
				}
				println jface.v1
				println v1
			}
		})

	}

	public void testChangeIDAttribute() {
		def swt = new SwtBuilder()
		swt[SwtBuilder.DELEGATE_PROPERTY_OBJECT_ID] = 'key'
		swt.shell {
			label('byKey', key:'byKey')
		}

		assert swt.getVariables().containsKey('byKey')
	}

	public void testJFace() {
		Shell shell = new Shell()
		ApplicationWindow window = new ApplicationWindow(shell)
		shell.dispose()
	}

	public void testOnEvent() {
		def swt = new SwtBuilder()
		def x = 1
		def localVar = null
		swt.shell('title') {
			rowLayout()
			button('text', id:'b1') {
				onEvent('Selection') { x = 2 }
			}
			button('text', id:'b2') {
				onEvent(type:'Selection') { x = 3 }
			}
			button('text', id:'b3') {
				onEvent(type:'Selection', closure: { x = 4 })
			}
		}

		assert x == 1
		pushButton(swt.b1)
		assert x == 2
		pushButton(swt.b2)
		assert x == 3
		pushButton(swt.b3)
		assert x == 4
	}

	void pushButton(def button) {
		button.notifyListeners(SWT.Selection, null)
	}

	public void testMenuItem() {
		def swt = new SwtBuilder()
		swt.shell('title') {
			rowLayout()
			menu() {
				menuItem('text', accelerator: 'Ctrl+S', id:'m1')
				menuItem('text', accelerator: SWT.CTRL | (int)'S', id:'m2')
			}
		}

		assertEquals swt.m1.accelerator, swt.m2.accelerator
	}

	public void testTableMenu() {
		def swt = new SwtBuilder()
		swt.shell('title') {
			rowLayout()
			table(id:'t1') {
				menu() {
					menuItem('text', accelerator: 'Ctrl+S', id:'m1')
				}
			}
		}
		assertNotNull swt.t1.menu
	}

	public void testMenu() {
		def swt = new SwtBuilder()
		def x = 1
		def localVar = null
		swt.shell('title') {
			submenu = swt.menu( style:"BAR") {
				menuItem('File', id:'file', style:'CASCADE') {
					menu(id:'m2') {
						menuItem('Save')
						menuItem('Save as', id:'saveas', style:'CASCADE') {
							menu(id:'m3') {
								menuItem('word')
								menuItem('excel')
								menuItem('powerpoint')
							}
						}
						menuItem('E&xit')
					}
				}
			}
		}
		assert swt.file.menu == swt.m2
		assert swt.saveas.menu == swt.m3
	}

	public void testWidgetWithStyle() {
		def swt = new SwtBuilder()
		def dialog = null
		swt.shell('title') {
			dialog = fileDialog( style:"SAVE")
		}
		assert dialog.style & SWT.SAVE;
	}

	public void testCast() {
		def t = new org.eclipse.swt.events.SelectionAdapter() {
					def widgetSelected(e) {
						int dir = node.table.sortDirection
						if (node.table.sortColumn == column) {
							dir = (dir == SWT.UP) ? SWT.DOWN : SWT.UP
						} else {
							dir = SWT.DOWN
						}
						node.table.sortDirection = dir
						node.table.sortColumn = column
						node.refresh()
					}
					def widgetDefaultSelected() {println 2}
				}
		def t1 = {e -> println 3} as org.eclipse.swt.events.SelectionAdapter
	}


}
