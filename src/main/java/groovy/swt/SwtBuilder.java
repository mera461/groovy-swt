/*
 * Created on Feb 15, 2004
 *  
 */
package groovy.swt;

import groovy.lang.Closure;
import groovy.swt.factory.ArrayTableFactory;
import groovy.swt.factory.AwtSwtFactory;
import groovy.swt.factory.DragSourceFactory;
import groovy.swt.factory.DropTargetFactory;
import groovy.swt.factory.Fontfactory;
import groovy.swt.factory.FormFactory;
import groovy.swt.factory.FormLayoutDataFactory;
import groovy.swt.factory.ImageFactory;
import groovy.swt.factory.LayoutDataFactory;
import groovy.swt.factory.LayoutFactory;
import groovy.swt.factory.ListenerFactory;
import groovy.swt.factory.SwtContainer;
import groovy.swt.factory.TrayFactory;
import groovy.swt.factory.WidgetFactory;
import groovy.util.FactoryBuilderSupport;

import net.miginfocom.swt.MigLayout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.browser.StatusTextListener;
import org.eclipse.swt.custom.CBanner;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.PopupList;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.TableTree;
import org.eclipse.swt.custom.TableTreeItem;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Caret;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.swt.widgets.Tracker;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.ColumnLayout;
import org.eclipse.ui.forms.widgets.ColumnLayoutData;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

/**
 * @author <a href="mailto:ckl@dacelo.nl">Christiaan ten Klooster </a>
 * @version $Revision: 1863 $
 */
public class SwtBuilder extends FactoryBuilderSupport {

    public SwtBuilder() {
    	// automatically done in groovy 1.6, but not in 1.5
    	registerWidgets();
    }

    protected void registerWidgetFactory(String name, final Class beanClass) {
        registerFactory(name, new WidgetFactory(beanClass));
    }

    protected void registerWidgetFactory(String name, final Class beanClass, final int style) {
        registerFactory(name, new WidgetFactory(beanClass, style));
    }

    protected void registerWidgets() {
        // widgets
        registerFactory("awtFrame", new AwtSwtFactory());
        registerWidgetFactory("button", Button.class, SWT.PUSH | SWT.CENTER);
        // Radiobutton
        registerWidgetFactory("radioButton", Button.class, SWT.RADIO);
        //CheckBox
        registerWidgetFactory("checkBox", Button.class, SWT.CHECK);
        registerWidgetFactory("canvas", Canvas.class);
        registerWidgetFactory("caret", Caret.class);
        registerWidgetFactory("combo", Combo.class, SWT.DROP_DOWN);
        registerWidgetFactory("composite", Composite.class);
        registerWidgetFactory("scrolledComposite", ScrolledComposite.class, SWT.H_SCROLL
                | SWT.V_SCROLL);
        registerWidgetFactory("coolBar", CoolBar.class, SWT.VERTICAL);
        registerWidgetFactory("coolItem", CoolItem.class);
        registerWidgetFactory("dateTime", DateTime.class);
        registerWidgetFactory("decorations", Decorations.class);
        registerWidgetFactory("expandBar", ExpandBar.class);
        registerFactory("font", new Fontfactory());
        registerWidgetFactory("group", Group.class);
        registerWidgetFactory("label", Label.class, SWT.HORIZONTAL | SWT.SHADOW_IN);
        // line label
        registerWidgetFactory("line", Label.class, SWT.SEPARATOR| SWT.HORIZONTAL|SWT.BOLD );
       
        registerWidgetFactory("link", Link.class);
        registerWidgetFactory("list", List.class);
        registerWidgetFactory("multi_list", List.class, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);

        registerWidgetFactory("menu", Menu.class, SWT.DEFAULT);
        //        registerMenuTag("menuBar", SWT.BAR);

        registerWidgetFactory("menuSeparator", MenuItem.class, SWT.SEPARATOR);
        registerWidgetFactory("menuItem", MenuItem.class);
        registerWidgetFactory("messageBox", MessageBox.class);
        registerWidgetFactory("progressBar", ProgressBar.class, SWT.HORIZONTAL);
        registerWidgetFactory("sash", Sash.class);
        registerWidgetFactory("scale", Scale.class);
        registerWidgetFactory("shell", Shell.class, SWT.BORDER | SWT.CLOSE | SWT.MIN | SWT.MAX
                | SWT.RESIZE | SWT.TITLE);
        registerWidgetFactory("slider", Slider.class);
        registerWidgetFactory("spinner", Spinner.class);
        registerWidgetFactory("tabFolder", TabFolder.class);
        registerWidgetFactory("tabItem", TabItem.class);
        registerWidgetFactory("table", Table.class, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
        // Table with checkbox
        registerWidgetFactory("check_table", Table.class, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION | SWT.CHECK);
        registerWidgetFactory("tableColumn", TableColumn.class);

        registerWidgetFactory("tableItem", TableItem.class);
        registerWidgetFactory("text", Text.class, SWT.BORDER);
        //textArea 
        registerWidgetFactory("textArea", Text.class, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL | SWT.BORDER);

        registerWidgetFactory("toolBar", ToolBar.class, SWT.VERTICAL);
        registerWidgetFactory("toolItem", ToolItem.class);
        registerWidgetFactory("toolTip", ToolTip.class, SWT.VERTICAL);
        registerWidgetFactory("tracker", Tracker.class);
        registerFactory("tray", new TrayFactory());
        registerWidgetFactory("trayItem", TrayItem.class);
        registerWidgetFactory("tree", Tree.class, SWT.MULTI);
        registerWidgetFactory("treeItem", TreeItem.class);

        // custom widgets
        registerWidgetFactory("cBanner", CBanner.class);
        registerWidgetFactory("cCombo", CCombo.class);
        registerWidgetFactory("cLabel", CLabel.class);
        registerWidgetFactory("cTabFolder", CTabFolder.class);
        registerWidgetFactory("cTabItem", CTabItem.class);
        registerWidgetFactory("sashForm", SashForm.class);
        registerWidgetFactory("styledText", StyledText.class);
        registerWidgetFactory("tableTree", TableTree.class);
        registerWidgetFactory("tableTreeItem", TableTreeItem.class);
        registerWidgetFactory("styleRange", StyleRange.class);
        registerWidgetFactory("popupList", PopupList.class);
        registerWidgetFactory("treeEditor", TreeEditor.class);
        // ExpandBar
        registerWidgetFactory("expandBar", ExpandBar.class);
        registerWidgetFactory("expandItem", ExpandItem.class);

        // layouts
        registerFactory("fillLayout", new LayoutFactory(FillLayout.class));
        registerFactory("gridLayout", new LayoutFactory(GridLayout.class));
        registerFactory("rowLayout", new LayoutFactory(RowLayout.class));
        registerFactory("formLayout", new LayoutFactory(FormLayout.class));
        registerFactory("migLayout", new LayoutFactory(MigLayout.class));

        // layout data objects
        registerFactory("gridData", new LayoutDataFactory(GridData.class));
        registerFactory("rowData", new LayoutDataFactory(RowData.class));
        registerFactory("formData", new FormLayoutDataFactory());

        // dialogs
        registerWidgetFactory("colorDialog", ColorDialog.class);
        registerWidgetFactory("directoryDialog", DirectoryDialog.class);
        registerWidgetFactory("fileDialog", FileDialog.class);
        registerWidgetFactory("fontDialog", FontDialog.class);

        // events
        registerFactory("onEvent", new ListenerFactory(Listener.class));

        // other tags
        registerFactory("image", new ImageFactory());

        // browser tags
        registerWidgetFactory("browser", Browser.class, SWT.NONE);
        registerFactory("locationListener", new ListenerFactory(LocationListener.class));
        registerFactory("progressListener", new ListenerFactory(ProgressListener.class));
        registerFactory("statusTextListener", new ListenerFactory(StatusTextListener.class));

        // forms api
        registerFactory("form", new FormFactory("form"));
        registerFactory("scrolledForm", new FormFactory("scrolledForm"));
        registerFactory("formButton", new FormFactory("formButton"));
        registerFactory("formColors", new FormFactory("formColors"));
        registerFactory("formComposite", new FormFactory("formComposite"));
        registerFactory("formCompositeSeparator", new FormFactory("formCompositeSeparator"));
        registerFactory("formExpandableComposite", new FormFactory("formButton"));
        registerFactory("formText", new FormFactory("formText"));
        registerFactory("formHyperlink", new FormFactory("formHyperlink"));
        registerFactory("formImageHyperlink", new FormFactory("formImageHyperlink"));
        registerFactory("formLabel", new FormFactory("formLabel"));
        registerFactory("formPageBook", new FormFactory("formPageBook"));
        registerFactory("formPageBookPage", new FormFactory("formPageBookPage"));
        registerFactory("formSection", new FormFactory("formSection"));
        registerFactory("formSeparator", new FormFactory("formSeparator"));
        registerFactory("formTable", new FormFactory("formTable"));
        registerFactory("formToolkit", new FormFactory("formToolkit"));
        registerFactory("formFormattedText", new FormFactory("formFormattedText"));
        registerFactory("formTree", new FormFactory("formTree"));

        // forms layout
        registerFactory("tableWrapLayout", new LayoutFactory(TableWrapLayout.class));
        registerFactory("tableWrapData", new LayoutDataFactory(TableWrapData.class));
        registerFactory("columnLayout", new LayoutFactory(ColumnLayout.class));
        registerFactory("columnLayoutData", new LayoutDataFactory(ColumnLayoutData.class));

        // forms listeners
        registerFactory("hyperlinkListener", new ListenerFactory(IHyperlinkListener.class));
        registerFactory("expansionListener", new ListenerFactory(IExpansionListener.class));

        // Array Table
        registerFactory("arrayTable", new ArrayTableFactory());
        
        // SWT Container
        registerFactory("swt", new SwtContainer());

        // none eclipse widgets
        // registerBeanFactory("tDateText", TDateText.class);
        // registerBeanFactory("tCalendar", TCalendar.class);
        // registerBeanFactory("textEditor", TextEditor.class);
        // Drag and drop support
        registerFactory("dragSource", new DragSourceFactory());
        registerFactory("dropTarget", new DropTargetFactory());
    }
    
    /* 
     * Shortcuts
     */
    static public void asyncExec(final Closure closure) {
    	asyncExec(null, closure);
    }

    
    static public void asyncExec(Display display, final Closure closure) {
    	if (display==null) {
    		display = Display.getDefault();
    	}
		display.asyncExec(new Runnable() {
			public void run() { 
				closure.call();
			}
    	});
    }
    
    static public void syncExec(final Closure closure) {
    	syncExec(null, closure);
    }

    static public void syncExec(Display display, final Closure closure) {
    	if (display==null) {
    		display = Display.getDefault();
    	}
    	display.syncExec(new Runnable() {
			public void run() { 
				closure.call();
			}
    	});
    }

    
}