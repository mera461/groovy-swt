/* Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     
 * Converted from SWT example to groovy: 03-03-2008
 *     
 *******************************************************************************/
package groovy.jface.examples.addressbook;


/* Imports */
import groovy.jface.JFaceBuilder
import groovy.jface.examples.addressbook.DataEntryDialog

import java.io.*;
import java.util.*;

import org.eclipse.swt.SWT
import org.eclipse.swt.events.*
import org.eclipse.swt.graphics.*
import org.eclipse.swt.layout.*
import org.eclipse.swt.widgets.*

/**
 * AddressBookExample is an example that uses <code>org.eclipse.swt</code> 
 * libraries to implement a simple address book.  This application has 
 * save, load, sorting, and searching functions common
 * to basic address books.
 */
public class AddressBook {

	 
	static ResourceBundle resAddressBook = ResourceBundle.getBundle("examples_addressbook");
	JFaceBuilder jface = new JFaceBuilder()
	def mainapp
	def popupMenu

	
	Table table
	def searchDialog
	
	File file;
	boolean isModified;
	
	String[] copyBuffer;

	int lastSortColumn= -1;	
	static final String DELIMITER = "\t";
	static final String[] columnNames = [resAddressBook.getString("Last_name"),
												 resAddressBook.getString("First_name"),
												 resAddressBook.getString("Business_phone"),
												 resAddressBook.getString("Home_phone"),
												 resAddressBook.getString("Email"),
												 resAddressBook.getString("Fax")];
	
public static void main(String[] args) {
	AddressBook application = new AddressBook();
	application.open();
}

public void open() {
    popupMenu = jface.menuManager("popup") {
    	/* TODO: 
     	onEvent (type: "Show", closure: { e ->
 			Menu menu = (Menu)e.widget;
 			MenuItem[] items = menu.getItems();
 			int count = table.getSelectionCount();
 			items[2].setEnabled(count != 0); // edit
 			items[3].setEnabled(count != 0); // copy
 			items[4].setEnabled(copyBuffer != null); // paste
 			items[5].setEnabled(count != 0); // delete
 			items[7].setEnabled(table.getItemCount() != 0); // find
     	}) */
        action (  resAddressBook.getString("Pop_up_new"),
        		 closure:{ newEntry() })
        separator()
     	action (  resAddressBook.getString("Pop_up_edit"),
     			 accelerator: SWT.MOD1 + (int) 'E',
     	   		 closure:{ def items = table.getSelection()
     					   if (items.size() == 0) return
     					   editEntry(items[0])
     			 } )
     	action (  resAddressBook.getString("Pop_up_copy"),
     			 accelerator: SWT.MOD1 + (int) 'C',
     	   		 closure:{ TableItem[] items = table.getSelection()
     	   		 		   if (items.size() == 0) return
   	    		 		   copyBuffer = new String[table.getColumnCount()]
     	   		 		   for (int i = 0; i < copyBuffer.length; i++) {
     	   		 			   copyBuffer[i] = items[0].getText(i)
     	   		 		   }
     			 } )
     	action (  resAddressBook.getString("Pop_up_paste"),
     			 accelerator: SWT.MOD1 + (int) 'V',
     	   		 closure:{ if (copyBuffer == null) return
     	   		   	       TableItem item = new TableItem(table, SWT.NONE)
     	   		   	       item.setText(copyBuffer)
     	   		   	       isModified = true
     			 } )
    	action (  resAddressBook.getString("Delete"),
     	   		 closure:{ TableItem[] items = table.getSelection()
     	   		 		   if (items.length == 0) return
     	   		 		   items[0].dispose()
     	   		   	       isModified = true
     			 } )
        separator()
    	action (  resAddressBook.getString("Pop_up_find"),
     	   		 closure:{ searchDialog.open() } )
    } // End of popup menu
	
    mainapp = jface.applicationWindow( title:"Address Book" ) {
     	fillLayout()
     	menuManager(  resAddressBook.getString("File_menu_title") ) {
     		/* TODO:
     		onEvent (type: "Show", closure: { e -> 
    			Menu menu = (Menu)e.widget;
    			MenuItem[] items = menu.getItems();
    			items[1].setEnabled(table.getSelectionCount() != 0); // edit contact
    			items[5].setEnabled((file != null) && isModified); // save
    			items[6].setEnabled(table.getItemCount() != 0); // save as
     		}) */
     		action (  resAddressBook.getString("New_contact"),
     				 accelerator: SWT.MOD1 + (int) 'N',
     				 closure:{ newEntry() } )
     	    action (  resAddressBook.getString("Edit_contact"),
     	    		 accelerator: SWT.MOD1 + (int) 'E',
     	    		 closure:{ def items = table.getSelection()
     	    		 	       if (items.size() == 0) return
     	    		 	       editEntry(items[0])
     	    		 } )
     	    separator()
     		action (  resAddressBook.getString("New_address_book"),
    				 accelerator: SWT.MOD1 + (int) 'B',
    				 closure:{ if (closeAddressBook()) newAddressBook() } )
     		action (  resAddressBook.getString("Open_address_book"),
     				 accelerator: SWT.MOD1 + (int) 'O',
    		    	 closure:{ if (closeAddressBook()) openAddressBook() } )
    		action (  resAddressBook.getString("Save_address_book"),
    				 accelerator: SWT.MOD1 + (int) 'S',
    				 closure:{ save() } )
    		action (  resAddressBook.getString("Save_book_as"),
    				 accelerator: SWT.MOD1 + (int) 'A',
    				 closure:{ saveAs() } )
    		separator()
    		action (  resAddressBook.getString("Exit"),
    				 closure:{ mainapp.close() } )
     	}
     	menuManager(  resAddressBook.getString("Edit_menu_title") ) {
     		/* TODO:
     		onEvent (type: "Show", closure: { e -> 
    			Menu menu = (Menu)e.widget;
    			MenuItem[] items = menu.getItems();
    			int count = table.getSelectionCount();
    			items[0].setEnabled(count != 0); // edit
    			items[1].setEnabled(count != 0); // copy
    			items[2].setEnabled(copyBuffer != null); // paste
    			items[3].setEnabled(count != 0); // delete
    			items[5].setEnabled(table.getItemCount() != 0); // sort
			}) */
     		action (  resAddressBook.getString("Edit"),
     				 accelerator: SWT.MOD1 + (int) 'E',
     	    		 closure:{ def items = table.getSelection()
     						   if (items.size() == 0) return
     						   editEntry(items[0])
     				 } )
     		action (  resAddressBook.getString("Copy"),
     				 accelerator: SWT.MOD1 + (int) 'C',
     	    		 closure:{ TableItem[] items = table.getSelection()
     	    		 		   if (items.size() == 0) return
     	    		 		   copyBuffer = new String[table.getColumnCount()]
     	    		 		   for (int i = 0; i < copyBuffer.length; i++) {
     	    		 			   copyBuffer[i] = items[0].getText(i)
     	    		 		   }
     				 } )
     		action (  resAddressBook.getString("Paste"),
     				 accelerator: SWT.MOD1 + (int) 'V',
     	    		 closure:{ if (copyBuffer == null) return
     	    		   	       TableItem item = new TableItem(table, SWT.NONE)
     	    		   	       item.setText(copyBuffer)
     	    		   	       isModified = true
     				 } )
    		action (  resAddressBook.getString("Delete"),
    				 accelerator: SWT.DEL,
     	    		 closure:{ TableItem[] items = table.getSelection()
     	    		 		   if (items.length == 0) return
     	    		 		   items[0].dispose()
     	    		   	       isModified = true
     				 } )
     		separator()
     		menuManager(  resAddressBook.getString("Sort") ) {
     			for(int i = 0; i < columnNames.length; i++) {
     				final int column = i;
     				action ( columnNames [i], closure: {sort(column)} )
     			}
     		}
     	} // end of Edit menu.
     	menuManager(  resAddressBook.getString("Search_menu_title") ) {
     		action (  resAddressBook.getString("Find"),
     				 accelerator: SWT.MOD1 + (int) 'F',
     	    		 closure:{ 
     			        searchDialog.open();
     					searchDialog.setMatchCase(false);
     					searchDialog.setMatchWord(false);
     					searchDialog.setSearchDown(true);
     					searchDialog.setSearchString("");
     					searchDialog.setSelectedSearchArea(0);
     				 } )
     		action (  resAddressBook.getString("Find_next"),
     				 accelerator: SWT.F3,
     				 closure:{searchDialog.open()} )
     	}
     	
     	menuManager(  resAddressBook.getString("Help_menu_title") ) {
     		action (  resAddressBook.getString("About"),
     	    		 closure:{ def box = jface.messageBox( resAddressBook.getString("About_1") + mainapp.getShell().getText(),
     													  style: "ICON_WARNING, YES, NO, CANCEL",
     													  message: mainapp.getShell().getText() + resAddressBook.getString("About_2"))
     						   box.open()
     				 } )
     	}
		
    	table = table(style: "SINGLE, BORDER, FULL_SELECTION", headerVisible: true /*TODO:, menu: popupMenu.getMenu() */) {
        	for(int i = 0; i < columnNames.length; i++) {
        		final int columnIndex = i;
        		def column = tableColumn(style: "none",  columnNames[i], width: 150 ) {
        			onEvent ("Selection"){sort(columnIndex)}
        		}
        	}
    	}
    	/*
    	table.addSelectionListener(new SelectionAdapter() {
    		public void widgetDefaultSelected(SelectionEvent e) {
    			TableItem[] items = table.getSelection();
    			if (items.length > 0) editEntry(items[0]);
    		}
    	});
    	*/
    } // End of Application Window

	searchDialog = new SearchDialog(mainapp.getShell());
	searchDialog.setSearchAreaNames(columnNames);
	searchDialog.setSearchAreaLabel(resAddressBook.getString("Column"));
	searchDialog.addFindListener({findEntry()})
    
    
// TODO:	mainapp.getShell().addShellListener(this)
    //mainapp.menuBarManager.updateAll( true )
    //mainapp.getShell().pack()
	// TODO: mainapp.setSize(table.computeSize(SWT.DEFAULT, SWT.DEFAULT).x, 300);
	newAddressBook();

 	mainapp.open()
}

	public void shellClosed(ShellEvent e) {
		e.doit = closeAddressBook()
	}
	

     	// -------------------------------------------------------------------------------

private boolean closeAddressBook() {
	if(isModified) {
		//ask user if they want to save current address book
		MessageBox box = new MessageBox(mainapp.getShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO | SWT.CANCEL);
		box.setText(mainapp.getShell().getText());
		box.setMessage(resAddressBook.getString("Close_save"));
	
		int choice = box.open();
		if(choice == SWT.CANCEL) {
			return false;
		} else if(choice == SWT.YES) {
			if (!save()) return false;
		}
	}
		
	TableItem[] items = table.getItems();
	for (int i = 0; i < items.length; i ++) {
		items[i].dispose();
	}
	
	return true;
}

/**
 * Converts an encoded <code>String</code> to a String array representing a table entry.
 */
private String[] decodeLine(String line) {
	if(line == null) return null;
	
	String[] parsedLine = new String[table.getColumnCount()];
	for(int i = 0; i < parsedLine.length - 1; i++) {
		int index = line.indexOf(DELIMITER);
		if (index > -1) {
			parsedLine[i] = line.substring(0, index);
			line = line.substring(index + DELIMITER.length(), line.length());
		} else {
			return null;
		}
	}
	
	if (line.indexOf(DELIMITER) != -1) return null;
	
	parsedLine[parsedLine.length - 1] = line;

	return parsedLine;
}
private void displayError(String msg) {
	MessageBox box = new MessageBox(mainapp.getShell(), SWT.ICON_ERROR);
	box.setMessage(msg);
	box.open();
}
private void editEntry(TableItem item) {
	DataEntryDialog dialog = new DataEntryDialog(mainapp.getShell());
	dialog.setLabels(columnNames);
	String[] values = new String[table.getColumnCount()];
	for (int i = 0; i < values.length; i++) {
		values[i] = item.getText(i);
	}
	dialog.setValues(values);
	values = dialog.open();
	if (values != null) {
		item.setText(values);
		isModified = true;
	}
}
private String encodeLine(String[] tableItems) {
	String line = "";
	for (int i = 0; i < tableItems.length - 1; i++) {
		line += tableItems[i] + DELIMITER;
	}
	line += tableItems[tableItems.length - 1] + "\n";
	
	return line;
}
private boolean findEntry() {
	Cursor waitCursor = new Cursor(mainapp.getShell().getDisplay(), SWT.CURSOR_WAIT);
	mainapp.getShell().setCursor(waitCursor);
	
	boolean matchCase = searchDialog.getMatchCase();
	boolean matchWord = searchDialog.getMatchWord();
	String searchString = searchDialog.getSearchString();
	int column = searchDialog.getSelectedSearchArea();
	
	searchString = matchCase ? searchString : searchString.toLowerCase();
	
	boolean found = false;
	if (searchDialog.getSearchDown()) {
		for(int i = table.getSelectionIndex() + 1; i < table.getItemCount(); i++) {
			found = findMatch(searchString, table.getItem(i), column, matchWord, matchCase);
			if (found) {
				table.setSelection(i);
				break;
			}
		}
	} else {
		for(int i = table.getSelectionIndex() - 1; i > -1; i--) {
			found = findMatch(searchString, table.getItem(i), column, matchWord, matchCase);
			if (found) {
				table.setSelection(i);
				break;
			}
		}
	}
	
	mainapp.getShell().setCursor(null);
	//TODO: waitCursor.dispose();
		
	return found;
}
private boolean findMatch(String searchString, TableItem item, int column, boolean matchWord, boolean matchCase) {
	
	String tableText = matchCase ? item.getText(column) : item.getText(column).toLowerCase();
	if (matchWord) {
		if (tableText != null && tableText.equals(searchString)) {
			return true;
		}
		
	} else {
		if(tableText!= null && tableText.indexOf(searchString) != -1) {
			return true;
		}
	}
	return false;
}
private void newAddressBook() {	
	mainapp.getShell().setText(resAddressBook.getString("Title_bar") + resAddressBook.getString("New_title"));
	file = null;
	isModified = false;
}
private void newEntry() {
	DataEntryDialog dialog = new DataEntryDialog(/*mainapp.getShell()*/);
	dialog.setLabels(columnNames);
	String[] data = dialog.open();
	if (data != null) {
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(data);
		isModified = true;
	}
}

private void openAddressBook() {	
	FileDialog fileDialog = new FileDialog(mainapp.getShell(), SWT.OPEN);

	fileDialog.setFilterExtensions(["*.adr;", "*.*"] as String[]);
	fileDialog.setFilterNames([resAddressBook.getString("Book_filter_name") + " (*.adr)", 
											resAddressBook.getString("All_filter_name") + " (*.*)"] as String[]);
	String name = fileDialog.open();

	if(name == null) return;
	File file = new File(name);
	if (!file.exists()) {
		displayError(resAddressBook.getString("File")+file.getName()+" "+resAddressBook.getString("Does_not_exist")); 
		return;
	}
	
	Cursor waitCursor = new Cursor(mainapp.getShell().getDisplay(), SWT.CURSOR_WAIT);
	mainapp.getShell().setCursor(waitCursor);
	
	FileReader fileReader = null;
	BufferedReader bufferedReader = null;
	String[] data = new String[0];
	try {
		fileReader = new FileReader(file.getAbsolutePath());
		bufferedReader = new BufferedReader(fileReader);
		String nextLine = bufferedReader.readLine();
		while (nextLine != null){
			String[] newData = new String[data.length + 1];
			System.arraycopy(data, 0, newData, 0, data.length);
			newData[data.length] = nextLine;
			data = newData;
			nextLine = bufferedReader.readLine();
		}
	} catch(FileNotFoundException e) {
		displayError(resAddressBook.getString("File_not_found") + "\n" + file.getName());
		return;
	} catch (IOException e ) {
		displayError(resAddressBook.getString("IO_error_read") + "\n" + file.getName());
		return;
	} finally {	
		
		mainapp.getShell().setCursor(null);
		waitCursor.dispose();
	
		if(fileReader != null) {
			try {
				fileReader.close();
			} catch(IOException e) {
				displayError(resAddressBook.getString("IO_error_close") + "\n" + file.getName());
				return;
			}
		}
	}
	
	String[][] tableInfo = new String[data.length][table.getColumnCount()];
	int writeIndex = 0;
	for (int i = 0; i < data.length; i++) {
		String[] line = decodeLine(data[i]);
		if (line != null) tableInfo[writeIndex++] = line;
	}
	if (writeIndex != data.length) {
		String[][] result = new String[writeIndex][table.getColumnCount()];
		System.arraycopy(tableInfo, 0, result, 0, writeIndex);
		tableInfo = result;
	}
	Arrays.sort(tableInfo, new RowComparator(0));

	for (int i = 0; i < tableInfo.length; i++) {
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(tableInfo[i]);
	}
	mainapp.getShell().setText(resAddressBook.getString("Title_bar")+fileDialog.getFileName());
	isModified = false;
	this.file = file;
}
private boolean save() {
	if(file == null) return saveAs();
	
	Cursor waitCursor = new Cursor(mainapp.getShell().getDisplay(), SWT.CURSOR_WAIT);
	mainapp.getShell().setCursor(waitCursor);
	
	TableItem[] items = table.getItems();
	String[] lines = new String[items.length];
	for(int i = 0; i < items.length; i++) {
		String[] itemText = new String[table.getColumnCount()];
		for (int j = 0; j < itemText.length; j++) {
			itemText[j] = items[i].getText(j);
		}
		lines[i] = encodeLine(itemText);
	}
		
	FileWriter fileWriter = null;
	try { 
		fileWriter = new FileWriter(file.getAbsolutePath(), false);
		for (int i = 0; i < lines.length; i++) {
			fileWriter.write(lines[i]);
		}
	} catch(FileNotFoundException e) {
		displayError(resAddressBook.getString("File_not_found") + "\n" + file.getName());
		return false;
	} catch(IOException e ) {
		displayError(resAddressBook.getString("IO_error_write") + "\n" + file.getName());
		return false;
	} finally {
		mainapp.getShell().setCursor(null);
		waitCursor.dispose();
		
		if(fileWriter != null) {
			try {
				fileWriter.close();
			} catch(IOException e) {
				displayError(resAddressBook.getString("IO_error_close") + "\n" + file.getName());
				return false;
			}
		}
	}

	mainapp.getShell().setText(resAddressBook.getString("Title_bar")+file.getName());
	isModified = false;
	return true;
}
private boolean saveAs() {
		
	FileDialog saveDialog = new FileDialog(mainapp.getShell(), SWT.SAVE);
	saveDialog.setFilterExtensions(["*.adr;",  "*.*"] as String[]);
	saveDialog.setFilterNames(["Address Books (*.adr)", "All Files "] as String[] );
	
	saveDialog.open();
	String name = saveDialog.getFileName();
		
	if(name.equals("")) return false;

	if(name.indexOf(".adr") != name.length() - 4) {
		name += ".adr";
	}

	File file = new File(saveDialog.getFilterPath(), name);
	if(file.exists()) {
		MessageBox box = new MessageBox(mainapp.getShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
		box.setText(resAddressBook.getString("Save_as_title"));
		box.setMessage(resAddressBook.getString("File") + file.getName()+" "+resAddressBook.getString("Query_overwrite"));
		if(box.open() != SWT.YES) {
			return false;
		}
	}
	this.file = file;
	return save();	
}
private void sort(int column) {
	if(table.getItemCount() <= 1) return;

	TableItem[] items = table.getItems();
	String[][] data = new String[items.length][table.getColumnCount()];
	for(int i = 0; i < items.length; i++) {
		for(int j = 0; j < table.getColumnCount(); j++) {
			data[i][j] = items[i].getText(j);
		}
	}
	
	Arrays.sort(data, new RowComparator(column));
	
	if (lastSortColumn != column) {
		table.setSortColumn(table.getColumn(column));
		table.setSortDirection(SWT.DOWN);
		for (int i = 0; i < data.length; i++) {
			items[i].setText(data[i]);
		}
		lastSortColumn = column;
	} else {
		// reverse order if the current column is selected again
		table.setSortDirection(SWT.UP);
		int j = data.length -1;
		for (int i = 0; i < data.length; i++) {
			items[i].setText(data[j--]);
		}
		lastSortColumn = -1;
	}
	
}
 }

/**
 * To compare entries (rows) by the given column
 */
class RowComparator implements Comparator {
	private int column;
	
	/**
	 * Constructs a RowComparator given the column index
	 * @param col The index (starting at zero) of the column
	 */
	public RowComparator(int col) {
		column = col;
	}
	
	/**
	 * Compares two rows (type String[]) using the specified
	 * column entry.
	 * @param obj1 First row to compare
	 * @param obj2 Second row to compare
	 * @return negative if obj1 less than obj2, positive if
	 * 			obj1 greater than obj2, and zero if equal.
	 */
	public int compare(Object obj1, Object obj2) {
		String[] row1 = (String[])obj1;
		String[] row2 = (String[])obj2;
		
		return row1[column].compareTo(row2[column]);
	}
}

 