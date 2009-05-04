/**
 * 
 */
package groovy.swt.examples.snippets

import groovy.swt.SwtBuilder
import org.eclipse.swt.graphics.Region

/**
 * For testing a 3.4 new feature: Buttons with regions.
 * 
 * @author Frank
 *
 */
public class Snippet294{

	def swt = new SwtBuilder()
	def shell
	
    public static void main(String[] args) {
		def demo = new Snippet294()
		demo.run()
	}
	
	static int[] circle(int r, int offsetX, int offsetY) {
		int[] polygon = new int[8 * r + 4];
		// x^2 + y^2 = r^2
		for (int i = 0; i < 2 * r + 1; i++) {
			int x = i - r;
			int y = (int)Math.sqrt(r*r - x*x);
			polygon[2*i] = offsetX + x;
			polygon[2*i+1] = offsetY + y;
			polygon[8*r - 2*i - 2] = offsetX + x;
			polygon[8*r - 2*i - 1] = offsetY - y;
		}
		return polygon;
	}
	
	void run(){
		
		// define a region that looks like a circle with two holes in ot
		Region region = new Region();
		region.add(circle(67, 87, 77));
		region.subtract(circle(20, 87, 47));
		region.subtract(circle(20, 87, 113));
		
		
		shell = swt.shell("Regions on a Control", size:[200,200], background: "#ff0000") {
			fillLayout()
			def b2 = button("Button with Regions", location:[100,100], region: region) {
				onEvent('Selection'){shell.close()}
			}
		}
		
		shell.open()
		while(! shell.isDisposed()) { 
			if (! shell.display.readAndDispatch()) {
				shell.display.sleep();
			}
		}
		
	}

}
