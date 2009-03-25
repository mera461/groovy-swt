/**
 * 
 */
package groovy.swt.examples.miglayout

import groovy.swt.SwtBuilder
import org.eclipse.swt.layout.GridData


/**
 * For testing a migLayout
 * See http://www.devx.com/Java/Article/38017/0/page/2
 * 
 * See the complex example at the buttom and included in the
 * attached source code.
 * 
 * @author Frank
 *
 */
public class ComplexSample {

	def swt = new SwtBuilder()
	
    public static void main(String[] args) {
		def demo = new ComplexSample()
		demo.run()
	}
	
	void run(){
		
		def shell = swt.shell("migLayout example 2", size:[950,700]) {
			migLayout(layoutConstraints:"wrap 3", columnConstraints: "[] [grow] []", rowConstraints: "[] [grow] [grow] [grow] []")
			
			// Client panel
			group("Client:", layoutData:"grow") {
				migLayout(layoutConstraints:"wrap 2",columnConstraints:"[] 16 []")
				label("Client ID:", layoutData:"right")
				label("#43354")

				label("Name:", layoutData:"right")
				label("John Smith")

				label("Phone:", layoutData:"right")
				label("(514) 999-9999")

				label("Fax:", layoutData:"right")
				label("(514) 888-8888")

				label("Registration #:", layoutData:"right")
				label("#4534-AAC-324")

				label("Website:", layoutData:"right")
				label("devx.com")

				button("Details...:")
			}
			
			// Information panel
			group("Information", layoutData:"grow") {
				migLayout(layoutConstraints:"wrap 3",columnConstraints:"[] 16 [grow] []")
				label("Reserve days:", layoutData: "right")
				text("0 of 0", editable: false, layoutData: "grow")
				button("Add Days...")
				
				label("Buyer:", layoutData:"right")
				text("Wal-Mart", layoutData:"span, growx")
				
				label("Seller:", layoutData:"right")
				text("China, Inc.", layoutData:"span, growx")
				
				label("Address:", layoutData:"right")
				text("123 Mao Street, Beijing", layoutData:"span, growx")
				
				label("Credit rating", layoutData:"right")
				text("AAA", layoutData:"growx")
				button("S&P Update...")
				
				button("Approved", style:"check")
				text("Tony Soprano gave us an offer we couldn't refuse", layoutData:"span, growx")
			}
			
			// Additional Information panel
			group("Additional Information", layoutData:"grow") {
				migLayout(layoutConstraints:"wrap 4",columnConstraints:"[] 16 [grow] [] []")
				label("Estimated close:", layoutData: "right")
				text("Real soon now...", layoutData: "span 2")
				button("Edit", layoutData:"top") {
					image(src:"edit-redo.png")
				}

				label("Creation data:", layoutData:"right")
				label("01-01-1970", layoutData:"wrap")

				label("Created by:", layoutData:"right")
				label("Big Tony", layoutData:"wrap")

				label("Last edit date:", layoutData:"right")
				label("05-13-1989", layoutData:"wrap")

				label("Last edited by:", layoutData:"right")
				label("Big Tony", layoutData:"wrap")

				label("Closed date:", layoutData:"right")
				label("", layoutData:"wrap")

				label("Closed by:", layoutData:"right")
				label("", layoutData:"wrap")
			}
			// Product List Panel
			group("Production List", layoutData:"grow, span 3") {
				migLayout(layoutConstraints:"wrap 4",columnConstraints:"[grow] 16 [] 32 [] []", rowConstraints:"[grow,:100:] [] [] []")
				String[] columns = ["Renovation","Description","Part #","Quantity","List Price","Discount","Price",
		        				"Wholesaler Discount","Wholesaler Price"]
				def int[] width = [90]
				String[][] data = [["test1", "test2"]]
				arrayTable(columnNames:columns, width: width, data:data, style:"MULTI , BORDER , FULL_SELECTION, H_SCROLL, V_SCROLL", layoutData: "grow, span 3")

				button("Add", layoutData: "sg 1, split 3, flowy, top") {
					image(src:"list-add.png")
				}
				button("Edit", layoutData: "sg 1") {
					image(src:"edit-redo.png")
				}
				button("Delete", layoutData: "sg 1") {
					image(src:"list-remove.png")
				}
				
				line(layoutData:"growx")
				label("Subtotal list price:")
				label('$0.00', layoutData:"right, wrap")
				label("Total retailer price:", layoutData:"skip 1")
				label('$0.00', layoutData:"right, wrap")
				label('Total wholesaler price:', layoutData:"skip 1")
				label('$0.00', layoutData:"right")
			
			}
			// Tasks Panel
			group("Tasks", layoutData:"grow, span 3") {
				migLayout(layoutConstraints:"wrap 2",columnConstraints:"[grow] []", rowConstraints:"[grow,:100:]")
				String[] columns = ["State","Task","Assigner","Executer","Creation Date","Estimated Date","Executed Date"]
				def int[] width = [110]
				String[][] data = [["test1", "test2"]]
				arrayTable(columnNames:columns, data:data, width: width, style:"MULTI , BORDER , FULL_SELECTION, H_SCROLL, V_SCROLL", layoutData: "grow")
				button("Add", layoutData: "sg 1, split 3, flowy, top") {
					image(src:"list-add.png")
				}
				button("Edit", layoutData: "sg 1")  {
					image(src:"edit-redo.png")
				}
				button("Delete", layoutData: "sg 1") {
					image(src:"list-remove.png")
				}
			}
			// Comments Panel
			group("Comments", layoutData:"grow, span 3") {
				migLayout(layoutConstraints:"wrap 2",columnConstraints:"[grow] []", rowConstraints:"[grow,:100:]")
				String[] columns = ["Date","User","Comment"]
				def int[] width = [250]
				arrayTable(columnNames:columns, width: width, style:"MULTI , BORDER , FULL_SELECTION, H_SCROLL, V_SCROLL", layoutData: "grow")
				button("Add", layoutData: "sg 1, split 3, flowy, top") {
					image(src:"list-add.png")
				}
				button("Edit", layoutData: "sg 1") {
					image(src:"edit-redo.png")
				}
				button("Delete", layoutData: "sg 1") {
					image(src:"list-remove.png")
				}
			}
			// Buttons panel
			composite(layoutData:"grow, span 3") {
				migLayout(layoutConstraints:"",columnConstraints:"[] [grow, right]")
				button("Export", layoutData:"sg 1")
				button("Save", layoutData:"split 2, sg 1") {
					image(src:"document-save.png")
				}
				button("Cancel", layoutData:"sg 1") {
					image(src:"system-log-out.png")
				}
			}
		}
		
		shell.doMainloop()
	}

}
