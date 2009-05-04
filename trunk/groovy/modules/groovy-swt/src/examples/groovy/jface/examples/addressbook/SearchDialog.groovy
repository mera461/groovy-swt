/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package groovy.jface.examples.addressbook;


/* Imports */
import groovy.jface.JFaceBuilder

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.events.*;
import java.util.ResourceBundle;

/**
 * SearchDialog is a simple class that uses <code>org.eclipse.swt</code> 
 * libraries to implement a basic search dialog.
 */
public class SearchDialog {

	private static ResourceBundle resAddressBook = ResourceBundle.getBundle("examples_addressbook");
	JFaceBuilder jface = new JFaceBuilder();

	Shell shell;
	Text searchText;
	Combo searchArea;
	Label searchAreaLabelWidget;
	Button matchCaseWidget;
	Button matchWordWidget;
	Button findButton;
	Button down;
	def findHandler;

/**
 * Class constructor that sets the parent shell and the table widget that
 * the dialog will search.
 *
 * @param parent	Shell 
 *			The shell that is the parent of the dialog.
 */
public SearchDialog(Shell parentShell) {
	shell = jface.shell(/*parent: parentShell, */style:"CLOSE, BORDER, TITLE",
						text: resAddressBook.getString("Search_dialog_title")) {
		onEvent('Close') { e -> 
			// don't dispose of the shell, just hide it for later use
			e.doit = false
			shell.setVisible(false);
		}
		gridLayout(numColumns: 2)
		label (resAddressBook.getString("Dialog_find_what"), style: "left")
		searchText = text(style: "BORDER") {
			gridData(widthHint: 200, style: "FILL_HORIZONTAL")
			onEvent('Modify') {
				boolean enableFind = (searchText.getCharCount() != 0);
				findButton.setEnabled(enableFind);
			}
		}
		searchAreaLabelWidget = label (style: "left")
		searchArea = combo(style: "DROP_DOWN, READ_ONLY") {
			gridData(widthHint:200, style: "FILL_HORIZONTAL")
		}
		matchCaseWidget = button(style: "Check", resAddressBook.getString("Dialog_match_case")) {
			gridData(horizontalSpan: 2)
		}
		matchWordWidget = button(style: "Check", resAddressBook.getString("Dialog_match_word")) {
			gridData(horizontalSpan: 2)
		}
		
		group (style: "none", resAddressBook.getString("Dialog_direction")) {
			gridData(horizontalSpan: 2)
			fillLayout()
			button (style: "Radio", resAddressBook.getString("Dialog_dir_up"), selection: false)
			down = button (style: "Radio", resAddressBook.getString("Dialog_dir_down"), selection: true)
		}
		
		composite (style: "none") {
			gridData(horizontalSpan: 2, style:"HORIZONTAL_ALIGN_FILL")
			gridLayout(numColumns: 2, makeColumnsEqualWidth: true)
			findButton = button(style:"push", resAddressBook.getString("Dialog_find"), enabled: false) {
				gridData(style: "HORIZONTAL_ALIGN_END")
				onEvent('Selection') {
					if (!findHandler.call()){
						MessageBox box = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK | SWT.PRIMARY_MODAL);
						box.setText(shell.getText());
						box.setMessage(resAddressBook.getString("Cannot_find") + "\"" + searchText.getText() + "\"");
						box.open();	
					}		
				}
			}
			button(resAddressBook.getString("Cancel")) {
				gridData(style: "HORIZONTAL_ALIGN_BEGINNING")
				onEvent('Selection'){ shell.setVisible(false) }
			}
		}
		
	}

	shell.layout()
	shell.pack()
//	shell.setVisible(false)
	//shell.open()
	assert searchAreaLabelWidget != null
}
public String getSearchAreaLabel(String label) {
	return searchAreaLabelWidget.getText();
}

public String[] getsearchAreaNames() {
	return searchArea.getItems();
}
public boolean getMatchCase() {
	return matchCaseWidget.getSelection();
}
public boolean getMatchWord() {
	return matchWordWidget.getSelection();
}
public String getSearchString() {
	return searchText.getText();
}
public boolean getSearchDown(){
	return down.getSelection();
}
public int getSelectedSearchArea() {
	return searchArea.getSelectionIndex();
}
public void open() {
	if (shell.isVisible()) {
		shell.setFocus();
	} else {
		shell.open();
	}
	searchText.setFocus();
}
public void setSearchAreaNames(String[] names) {
	for (int i = 0; i < names.length; i++) {
		searchArea.add(names[i]);
	}
	searchArea.select(0);
}
public void setSearchAreaLabel(String label) {
	searchAreaLabelWidget.setText(label);
}
public void setMatchCase(boolean match) {
	matchCaseWidget.setSelection(match);
}
public void setMatchWord(boolean match) {
	matchWordWidget.setSelection(match);
}
public void setSearchDown(boolean searchDown){
	down.setSelection(searchDown);
}
public void setSearchString(String searchString) {
	searchText.setText(searchString);
}

public void setSelectedSearchArea(int index) {
	searchArea.select(index);
}
public void addFindListener(def listener) {
	this.findHandler = listener;	
}
public void removeFindListener() {
	this.findHandler = null;
}
}
