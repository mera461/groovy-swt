/**
 * See http://dev.eclipse.org/viewcvs/index.cgi/org.eclipse.jface.examples.databinding/src/org/eclipse/jface/examples/databinding/snippets/SnippetColorLabelProvider.java?view=markup
 * for the example in java.
 *
 * An example showing how to create a {@link ILabelProvider label provider} that
 * to provide colors.
 */
package groovy.jface.examples.databinding

import groovy.beans.Bindable
import groovy.jface.JFaceBuilder

import org.eclipse.core.databinding.beans.BeansObservables
import org.eclipse.core.databinding.beans.BeanProperties
import org.eclipse.core.databinding.observable.Realm
import org.eclipse.core.databinding.observable.list.WritableList
import org.eclipse.core.databinding.observable.map.IObservableMap;

import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.jface.viewers.ArrayContentProvider
import org.eclipse.jface.viewers.ITableColorProvider

import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.widgets.Display

//TODO: Should be deleted:
import org.eclipse.jface.databinding.viewers.ObservableMapLabelProvider
import org.eclipse.core.databinding.beans.BeansObservables
import org.eclipse.core.databinding.observable.Observables
import org.eclipse.jface.databinding.viewers.ViewerProperties
import org.eclipse.jface.databinding.viewers.ViewersObservables


/**
* @author Frank
*
*/
public class Snippet007ColorLabelProvider {
   public static void main(String[] args){
	   def display = Display.default ?: new Display()
	   Realm.default = SWTObservables.getRealm(display)
	   
	   def model = new ViewModel()
	   def shell = new View(viewModel: model).open()
	   shell.doMainloop()
   }

   // The data model class. This is normally a persistent class of some sort.
   @Bindable
   static class Person {
	   static final int MALE = 0
	   static final int FEMALE = 1
	   
	   String name
	   int gender
	   String city
	   
	   def toggleGender() {
		   println "Toggle gender on $name"
		   // NOTE: Remember to use the set method otherwise it will not trigger a PropertyChange
		   setGender ((gender==MALE) ? FEMALE : MALE)
	   }
   }
   
   static class ViewModel {
	   // The model to bind
	   @Bindable
	   def people
	   
	   ViewModel() {
		   people = new WritableList(SWTObservables.getRealm(Display.getCurrent()))
		   people << new Person(name:"Fiona Apple", gender:Person007.FEMALE, city:'a')
		   people << new Person(name:"Elliot Smith", gender:Person007.MALE, city:'b')
		   people << new Person(name:"Diana Krall", gender:Person007.FEMALE, city:'c')
		   people << new Person(name:"David Gilmour", gender:Person007.MALE, city:'d')
	   }
   }
   
   static class ColorLabelProvider extends ObservableMapLabelProvider implements ITableColorProvider {
	   Color male = Display.getCurrent().getSystemColor(SWT.COLOR_BLUE)
	   Color female = new Color(Display.getCurrent(), 255, 192, 203)
   
	   // get the viewModel to find the index of the person in the people list.
	   ViewModel viewModel
   
	   ColorLabelProvider(IObservableMap[] attributes) {
		   super(attributes);
	   }
   
	   // to drive home the point that attributes does not have to
	   // match the columns
	   // in the table, we change the column text as follows:
	   public String getColumnText(Object element, int index) {
		   if (index == 0) {
			   "${viewModel.people.indexOf(element) + 1}"
		   } else if (index == 1) {
			   element.name
		   } else {
			   element.city
		   }
	   }
   
	   public Color getBackground(Object element, int index) {
		   return null;
	   }
   
	   public Color getForeground(Object element, int index) {
		   if (index == 0)
			   return null;
		   return (element.gender == Person.MALE) ? male : female;
	   }
   
	   public void dispose() {
		   super.dispose();
		   female.dispose();
	   }
   }
   
   static class View {
	   ViewModel viewModel
	   
	   def createShell() {
		   def jface = new JFaceBuilder()
		   def shell = jface.shell('Gender Bender') {
			   migLayout(layoutConstraints:"wrap 1", columnConstraints: "[grow, fill]")
			   table(headerVisible: true, linesVisible: true, layoutData:'grow') {
				   tableColumn('No', width: 30)
				   tableColumn('Name', width: 100)
				   tableColumn('City', width: 100)
				   tableViewer(id:'viewer',
							   input: bind(model: viewModel.people, modelProperty:['gender', 'name', 'city']))
			   }
			   
			   // this does not have to correspond to the columns in the table,
			   // we just list all attributes that affect the table content.
			   IObservableMap[] attributes = BeansObservables.observeMaps(
					   viewer.contentProvider.getKnownElements(), Person.class,
					   ['gender', 'name', 'city'] as String[])
			   def labelProvider = new ColorLabelProvider(attributes)
			   labelProvider.viewModel = viewModel
			   viewer.setLabelProvider(labelProvider)
   
			   
			   button('Toggle Gender') {
				   onEvent('Selection'){
					   def selection = viewer.selection
					   if (selection !=null && !selection.isEmpty()) {
						   selection.firstElement.toggleGender()
					   }
				   }
			   }
			   button('Change city') {
				   onEvent('Selection') {
					   viewModel.people.each {
						   it.city = "City ${(int) (Math.random()*1000) }"
						   it.gender = 0
					   }
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
      
}


