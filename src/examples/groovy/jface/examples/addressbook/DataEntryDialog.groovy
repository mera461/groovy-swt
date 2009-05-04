/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package groovy.jface.examples.addressbook;

import org.eclipse.swt.widgets.Shellimport org.eclipse.swt.widgets.Display
/* Imports */
import groovy.jface.JFaceBuilder

import java.util.ResourceBundle;

/**
 * DataEntryDialog class uses <code>org.eclipse.swt</code> 
 * libraries to implement a dialog that accepts basic personal information that
 * is added to a <code>Table</code> widget or edits a <code>TableItem</code> entry 
 * to represent the entered data.
 */
public class DataEntryDialog {
	
	private static ResourceBundle resAddressBook = ResourceBundle.getBundle("examples_addressbook");
	public static JFaceBuilder jface = new JFaceBuilder();
	
	//Shell shell;
	Shell parentShell
	def dialog

	private String[] values;
	private String[] labels;
	
public DataEntryDialog(parentShell) {
	this.parentShell = parentShell
}

public String[] open() {
	if (labels == null) return;
	if (values == null) values = new String[labels.size()]
	dialog = jface.shell(style: "DIALOG_TRIM, PRIMARY_MODAL") {
		gridLayout()
		composite(style: "none") {
			gridData(style: "fill_horizontal")
			gridLayout(numColumns: 2)
			for (int i = 0; i < labels.length; i++) {
				label(labels[i], style: "right")
				text(style: "border",
					 text: (values[i]!=null)?values[i]:"",
					 data: new Integer(i) ) {
					gridData(widthHint: 400)
					onEvent('Modify') {
						values[it.widget.getData().intValue()] = it.widget.getText()
					}
				}
			}
		}
		composite(style: "none") {
			gridData(style: "HORIZONTAL_ALIGN_CENTER")
			gridLayout(numColumns: 2)
			button(resAddressBook.getString("OK")) {
				onEvent('Selection') {
					dialog.close()
				}
			}
			button(resAddressBook.getString("Cancel")) {
				onEvent('Selection') { values = null; dialog.close() }
			}
		}		
	}
	dialog.pack()
	dialog.open()
	Display display = dialog.getDisplay();
	while(!dialog.isDisposed()){
		if(!display.readAndDispatch())
			display.sleep();
	}
	return getValues()
}

public String[] getLabels() {
	return labels;
}
public String getTitle() {
	return shell.getText();
}

/**
 * Returns the contents of the <code>Text</code> widgets in the dialog in a 
 * <code>String</code> array.
 *
 * @return	String[]	
 *			The contents of the text widgets of the dialog.
 *			May return null if all text widgets are empty.
 */ 
public String[] getValues() {
	return values;
}

public void setLabels(def labels) {
	this.labels = labels;
}
public void setTitle(String title) {
	shell.setText(title);
}
/**
 * Sets the values of the <code>Text</code> widgets of the dialog to
 * the values supplied in the parameter array.
 *
 * @param	itemInfo	String[]
 * 						The values to which the dialog contents will be set.
 */
public void setValues(def itemInfo) {
	if (labels == null) return;
	if (itemInfo==null) {
		values = null;
		return;
	}
	if (values == null)
		values = new String[labels.size()];

	int numItems = Math.min(values.size(), itemInfo.size());
	for(int i = 0; i < numItems; i++) {
		values[i] = itemInfo[i];
	}	
}
 
 public static void main(String[] args) {
	 // testing the dialog
	 def app = jface.applicationWindow()
	 def t = new DataEntryDialog(app.getShell());
	 t.setLabels(["test1", "test2", "test3"])
	 t.open()
 }
 
}
