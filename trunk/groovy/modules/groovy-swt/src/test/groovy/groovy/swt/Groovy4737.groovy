package groovy.swt;

import groovy.util.GroovyTestCase;
import groovy.swing.SwingBuilder

class Groovy4737 extends GroovyTestCase {


	public void testUnnested() {
		def swing = new SwingBuilder()
		swing.frame(title:'Frame') {
			label("text", id:'textlabel')
			textlabel.text = "new label"
		}
		assert true
	}

	public void testNested() {
		try {
			new View().create()
		} catch (e) {
			// Because of GROOVY-4737, it will throw a MissingFieldException on accessing id attributes
			// if neested into another class.
			assert e instanceof MissingFieldException
		}
	}

	public static class View {
		def create() {
			def swing = new SwingBuilder()
			swing.frame(title:'Frame') {
				label("text", id:'textlabel')
				textlabel.text = "new label"
			}
		}
	}
}
