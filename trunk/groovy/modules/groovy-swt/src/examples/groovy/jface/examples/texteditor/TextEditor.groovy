/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     
 * Converted from the SWT examples to Groovy: 03-03-2008
 *     
 *******************************************************************************/
package groovy.jface.examples.texteditor;

import groovy.jface.JFaceBuilder
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Shell
import org.eclipse.swt.layout.GridData
import org.eclipse.swt.custom.StyleRangeimport org.eclipse.swt.custom.ExtendedModifyEventimport org.eclipse.swt.widgets.Widgetimport org.eclipse.swt.graphics.Pointimport org.eclipse.swt.custom.StyledTextimport org.eclipse.swt.widgets.Eventimport org.eclipse.swt.widgets.Displayimport org.eclipse.swt.graphics.Colorimport org.eclipse.swt.graphics.RGBimport org.eclipse.swt.widgets.FontDialogimport org.eclipse.swt.graphics.FontDataimport java.awt.Fontimport org.eclipse.swt.custom.ExtendedModifyListener
public class TextEditor implements ExtendedModifyListener {
	
	// the builders
	JFaceBuilder jface = new JFaceBuilder()
	
	def mainapp;
	StyledText text;

	def boldButton
	def italicButton
	def underlineButton
	def strikeoutButton
	
	Color RED = null;
	Color BLUE = null;
	Color GREEN = null;

	void createWindow () {
	    mainapp = jface.applicationWindow( title:"Groovy Text Editor", location:[100,100], size:[500, 300] ) {
	     	gridLayout(numColumns:1)
	     	menuManager( "File" ) {
	     		action ( "Exit", closure:{ mainapp.close() } )
	     	}

	     	menuManager( "Edit" ) {
	     		action ( "Cut", accelerator: SWT.MOD1 + (int) 'X', closure:{ handleCutCopy(); text.cut() } )
	     		action ( "Copy", accelerator: SWT.MOD1 + (int) 'C',closure:{ handleCutCopy(); text.copy() } )
	     		action ( "Paste", accelerator: SWT.MOD1 + (int) 'P',closure:{ text.paste() } )
	     		separator()
	     		action ( "Set Font", closure:{ setFont() } )
	     	}
	     	toolBar( style:"none" ) {
        		boldButton = toolItem(style:"check", toolTipText:"Bold") {
        			image( src:"bold.png" ) 
        			onEvent('Selection') {setStyle(it.widget)} 
        		}
        		italicButton = toolItem(style:"check", toolTipText:"Italic") {
        			image( src:"italic.png" ) 
        			onEvent('Selection') {setStyle(it.widget)} 
        		}
        		underlineButton = toolItem(style:"check", toolTipText:"Underline") {
        			image( src:"underline.png" ) 
        			onEvent('Selection'){setStyle(it.widget)} 
        		}
        		strikeoutButton = toolItem(style:"check", toolTipText:"Strikeout") {
        			image( src:"strikeout.png" ) 
        			onEvent('Selection') {setStyle(it.widget)} 
        		}
        		toolItem(style:"separator")
        		toolItem(style:"push", toolTipText:"Red text") {
        			image( src:"red.png" ) 
        			onEvent('Selection') {fgColor(RED)} 
        		}
        		toolItem(style:"push", toolTipText:"Blue text") {
        			image( src:"blue.png" ) 
        			onEvent('Selection'){fgColor(BLUE)} 
        		}
        		toolItem(style:"push", toolTipText:"Green text") {
        			image( src:"green.png" ) 
        			onEvent('Selection'){fgColor(GREEN)} 
        		}
        		toolItem(style:"separator")
        		toolItem(style:"push", toolTipText:"Clear formatting") {
        			image( src:"erase.png" ) 
        			onEvent('Selection') {clear()} 
        		}
	     	}        		
	     	
	     	text = styledText ( style: "Border, Multi, V_Scroll, H_Scroll") {
	     		gridData(horizontalAlignment: GridData.FILL, 
	     				 verticalAlignment: GridData.FILL, 
	     				 grabExcessHorizontalSpace: true,
	     				 grabExcessVerticalSpace: true
	     				 )
	     	}
	     	
	     	text.addExtendedModifyListener(this)
		}
		mainapp.open()
	}


	void modifyText(ExtendedModifyEvent event) {
		handleExtendedModify(event)
	}
	
	
/*
 * Set a style
 */
void setStyle(Widget widget) {
	Point sel = text.getSelectionRange();
	if ((sel == null) || (sel.y == 0)) return;
	StyleRange style;
	for (int i = sel.x; i<sel.x+sel.y; i++) {
		StyleRange range = text.getStyleRangeAtOffset(i);
		if (range != null) {
			style = (StyleRange)range.clone();
			style.start = i;
			style.length = 1;
		} else {
			style = new StyleRange(i, 1, null, null, SWT.NORMAL);
		}
		if (widget == boldButton) {
			style.fontStyle ^= SWT.BOLD;
		} else if (widget == italicButton) {
			style.fontStyle ^= SWT.ITALIC;						
		} else if (widget == underlineButton) {
			style.underline = !style.underline;
		} else if (widget == strikeoutButton) {
			style.strikeout = !style.strikeout;
		}
		text.setStyleRange(style);
	}
	text.setSelectionRange(sel.x + sel.y, 0);			
}

/*
 * Clear all style data for the selected text.
 */
void clear() {
	Point sel = text.getSelectionRange();
	if (sel.y != 0) {
		StyleRange style;
		style = new StyleRange(sel.x, sel.y, null, null, SWT.NORMAL);
		text.setStyleRange(style);
	}
	text.setSelectionRange(sel.x + sel.y, 0);
}

/*
 * Set the foreground color for the selected text.
 */
void fgColor(Color fg) {
	Point sel = text.getSelectionRange();
	if ((sel == null) || (sel.y == 0)) return;
	StyleRange style, range;
	for (int i = sel.x; i<sel.x+sel.y; i++) {
		range = text.getStyleRangeAtOffset(i);
		if (range != null) {
			style = (StyleRange)range.clone();
			style.start = i;
			style.length = 1;
			style.foreground = fg;
		} else {
			style = new StyleRange (i, 1, fg, null, SWT.NORMAL);
		}
		text.setStyleRange(style);
	}
	text.setSelectionRange(sel.x + sel.y, 0);
}

/*
 * Cache the style information for text that has been cut or copied.
 */
void handleCutCopy() {
	// Save the cut/copied style info so that during paste we will maintain
	// the style information.  Cut/copied text is put in the clipboard in
	// RTF format, but is not pasted in RTF format.  The other way to 
	// handle the pasting of styles would be to access the Clipboard directly and 
	// parse the RTF text.
	cachedStyles = new Vector();
	Point sel = text.getSelectionRange();
	int startX = sel.x;
	for (int i=sel.x; i<=sel.x+sel.y-1; i++) {
		StyleRange style = text.getStyleRangeAtOffset(i);
		if (style != null) {
			style.start = style.start - startX;
			if (!cachedStyles.isEmpty()) {
				StyleRange lastStyle = (StyleRange)cachedStyles.lastElement();
				if (lastStyle.similarTo(style) && lastStyle.start + lastStyle.length == style.start) {
					lastStyle.length++;
				} else {
					cachedStyles.addElement(style);
				}
			} else {
				cachedStyles.addElement(style);
			}
		}
	}
}

void handleExtendedModify(ExtendedModifyEvent event) {
	if (event.length == 0) return;
	StyleRange style;
	if (event.length == 1 || text.getTextRange(event.start, event.length).equals(text.getLineDelimiter())) {
		// Have the new text take on the style of the text to its right (during
		// typing) if no style information is active.
		int caretOffset = text.getCaretOffset();
		style = null;
		if (caretOffset < text.getCharCount()) style = text.getStyleRangeAtOffset(caretOffset);
		if (style != null) {
			style = (StyleRange) style.clone ();
			style.start = event.start;
			style.length = event.length;
		} else {
			style = new StyleRange(event.start, event.length, null, null, SWT.NORMAL);
		}		
		if (boldButton.getSelection()) style.fontStyle |= SWT.BOLD;
		if (italicButton.getSelection()) style.fontStyle |= SWT.ITALIC;
		style.underline = underlineButton.getSelection();
		style.strikeout = strikeoutButton.getSelection();
		if (!style.isUnstyled()) text.setStyleRange(style);
	} else {
		// paste occurring, have text take on the styles it had when it was
		// cut/copied
		for (int i=0; i<cachedStyles.size(); i++) {
			style = (StyleRange)cachedStyles.elementAt(i);
			StyleRange newStyle = (StyleRange)style.clone();
			newStyle.start = style.start + event.start;
			text.setStyleRange(newStyle);
		}
	}
}


public static void main (String [] args) {
	TextEditor example = new TextEditor ();
	example.open ();
}

public void open () {
	initializeColors()
	createWindow ();
}

void setFont() {
	FontDialog fontDialog = new FontDialog(mainapp.getShell());
	fontDialog.setFontList((text.getFont()).getFontData());
	FontData fontData = fontDialog.open();
	if (fontData != null) {
		Font newFont = new Font(shell.getDisplay(), fontData); 
		text.setFont(newFont);
		if (font != null) font.dispose();
		font = newFont;
	}
}

void initializeColors() {
	Display display = Display.getDefault();
	RED = new Color (display, new RGB(255,0,0));
	BLUE = new Color (display, new RGB(0,0,255));
	GREEN = new Color (display, new RGB(0,255,0));
}
}
