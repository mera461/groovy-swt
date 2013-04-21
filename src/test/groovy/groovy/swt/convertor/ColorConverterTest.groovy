/*
 * Created on Feb 25, 2004
 *
 */
package groovy.swt.convertor

import junit.framework.TestCase

import groovy.jface.JFaceBuilder

import org.eclipse.swt.widgets.Display
import org.eclipse.swt.SWT

public class ColorConverterTest extends TestCase {
	 
	def jface = new JFaceBuilder()
	
	/*
	 * Test basic binding
	 */
    public void testSystemColor() {
		ColorConverter converter = ColorConverter.instance
		assert jface.display.getSystemColor(SWT.COLOR_BLUE).getRGB() == converter.parse('blue')
    }
    
}
