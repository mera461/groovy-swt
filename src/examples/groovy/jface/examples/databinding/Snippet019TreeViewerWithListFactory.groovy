/**
 * See http://dev.eclipse.org/viewcvs/index.cgi/org.eclipse.jface.examples.databinding/src/org/eclipse/jface/examples/databinding/snippets/Snippet019TreeViewerWithListFactory.java?view=markup
 * for the example in java.
 *
 */
package groovy.jface.examples.databinding

import groovy.beans.Bindable
import groovy.jface.JFaceBuilder

import org.eclipse.core.databinding.observable.Realm
import org.eclipse.core.databinding.observable.list.WritableList
import org.eclipse.core.databinding.observable.value.WritableValue

import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.jface.viewers.StructuredSelection
import org.eclipse.jface.window.Window

import org.eclipse.swt.widgets.Display

// The data model class. This is normally a persistent class of some sort.
@Bindable
class Bean019 {
	String text
	List list = []
	
	String toString() {name}
}

// The View's model--the root of our Model graph for this particular GUI.
class ViewModel019 {
	// The model to bind
	@Bindable
	def input = new Bean019(text:'input')
	
}

class View019 {
	ViewModel019 viewModel
	@Bindable
	def clipboard = new WritableValue()
	
	def createShell() {
		def jface = new JFaceBuilder()
		def shell
		shell = jface.shell('SWT Application', size:[535, 397]) {
			migLayout(layoutConstraints:"wrap 1", columnConstraints: "[grow, fill]", rowConstraints: "[][grow, fill][]")
			composite() {
				rowLayout()
				button('Add Root') {
					onEvent('Selection') {
						def list = new ArrayList(viewModel.input.list)
						def root = new Bean019(text:'root') 
						list << root
						viewModel.input.list = list
						beanViewer.selection = new StructuredSelection(root)
						beanText.selectAll()
						beanText.setFocus()
					}
				}
				button('Add Child', id:'addchildButton') {
					onEvent('Selection') {
						def parent = beanViewer.selection?.firstElement
						def list = new ArrayList(parent.list)
						def child = new Bean019(text:'child')
						list << child
						parent.list = list
						
						beanViewer.selection = new StructuredSelection(child)
						beanText.selectAll()
						beanText.setFocus()
					}
				}
				button('Remove', id:'removeButton') {
					onEvent('Selection'){
						def selectedItem = beanViewer.tree.selection[0]
						def parentItem = selectedItem.parentItem
						def parent, index
						if (parentItem == null) {
							parent = viewModel.input
							index = beanViewer.tree.indexOf(selectedItem)
						} else {
							parent = parentItem.data
							index = parentItem.indexOf(selectedItem)
						}
						def list = new ArrayList(parent.list)
						list.remove(index)
						parent.list = list
					}
				}
				button('Copy', id:'copyButton') {
					onEvent('Selection'){
						clipboard.value = beanViewer.selection?.firstElement
					}
				}
				button('Paste', id:'pasteButton') {
					onEvent('Selection') {
						def copy = clipboard.value
						if (copy == null) return
						def parent = beanViewer.selection?.firstElement
						if (parent==null) parent=input
						def list = new ArrayList(parent.list)
						list << copy
						parent.list = list
						
						beanViewer.selection = new StructuredSelection(copy)
						beanText.selectAll()
						beanText.setFocus()
					}
				}
				button('Refresh') {
					onEvent('Selection'){
						beanViewer.refresh()
					}
				}
			}
			tree(id:'beanTree') {
				treeViewer(id:'beanViewer', style:'FULL_SELECTION, BORDER', useHashlookup: true,
						   input: bind(model:viewModel.input, modelProperty:'text', childrenProperty: 'list'))
			}
			label('Item Name', layoutData:'split 2')
			text(id: 'beanText',
				 text: bind(model: beanViewer, modelProperty:'text'))
			bind(target:addchildButton, targetProperty: 'enabled') {beanViewer.selection.size()>0}
// Another way of doing it:			
//			bind(target:addchildButton, targetProperty: 'enabled',
//				 model: beanViewer, modelProperty: 'text', 'model2target.converter': {beanTree.selectionCount>0})
			bind(target:removeButton, targetProperty: 'enabled') {beanViewer.selection.size()>0}
			bind(target:copyButton, targetProperty: 'enabled') {beanViewer.selection.size()>0}
			bind(target:pasteButton, targetProperty: 'enabled') {clipboard.value != null}
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
public class Snippet019TreeViewerWithListFactory {
	public static void main(String[] args){
		Realm.default = SWTObservables.getRealm(Display.default ?: new Display())
		
		def model = new ViewModel019()
		def shell = new View019(viewModel: model).open()
		shell.doMainloop()
	}
	
}
