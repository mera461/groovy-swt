package groovy.swt.databinding;

import java.text.Collator; // can't use ICU - Collator used in public API
import java.util.Comparator; 

/**
 * A viewer sorter is used by a {@link StructuredViewer} to reorder the elements 
 * provided by its content provider.
 * <p>
 * The default <code>compare</code> method compares elements using two steps. 
 * The first step uses the values returned from <code>category</code>. 
 * By default, all elements are in the same category. 
 * The second level is based on a case insensitive compare of the strings obtained 
 * from the content viewer's label provider via <code>ILabelProvider.getText</code>.
 * </p>
 * <p>
 * Subclasses may implement the <code>isSorterProperty</code> method;
 * they may reimplement the <code>category</code> method to provide 
 * categorization; and they may override the <code>compare</code> methods
 * to provide a totally different way of sorting elements.
 * </p>
 * <p>
 * It is recommended to use <code>ViewerComparator</code> instead.
 * </p>
 * @see IStructuredContentProvider
 * @see StructuredViewer
 */
public class CViewerSorter extends CViewerComparator {
    /**
     * The collator used to sort strings.
     * 
     * @deprecated as of 3.3 Use {@link ViewerComparator#getComparator()}
     */
    protected Collator collator;

    /**
     * Creates a new viewer sorter, which uses the default collator
     * to sort strings.
     */
    public CViewerSorter() {
        this(Collator.getInstance());
    }

    /**
     * Creates a new viewer sorter, which uses the given collator
     * to sort strings.
     *
     * @param collator the collator to use to sort strings
     */
    public CViewerSorter(Collator collator) {
    	//super(collator);
        this.collator = collator;
    }

    /**
     * Returns the collator used to sort strings.
     *
     * @return the collator used to sort strings
     * @deprecated as of 3.3 Use {@link ViewerComparator#getComparator()}
     */
    public Collator getCollator() {
        return collator;
    }

} 


class CViewerComparator {
	/**
	 * The comparator to use to sort a viewer's contents.
	 */
	private Comparator comparator;

	/**
     * Creates a new {@link ViewerComparator}, which uses the default comparator
     * to sort strings.
	 *
	 */
	public CViewerComparator(){
		this(null);
	}
	
	/**
     * Creates a new {@link ViewerComparator}, which uses the given comparator
     * to sort strings.
     * 
	 * @param comparator
	 */
	public CViewerComparator(Comparator comparator){
		this.comparator = comparator;
	}

	/**
	 * Returns the comparator used to sort strings.
	 * 
	 * @return the comparator used to sort strings
	 */
	protected Comparator getComparator() {
		return null;
	}

    /**
     * Returns the category of the given element. The category is a
     * number used to allocate elements to bins; the bins are arranged
     * in ascending numeric order. The elements within a bin are arranged
     * via a second level sort criterion.
     * <p>
     * The default implementation of this framework method returns
     * <code>0</code>. Subclasses may reimplement this method to provide
     * non-trivial categorization.
     * </p>
     *
     * @param element the element
     * @return the category
     */
    public int category(Object element) {
        return 0;
    }

    /**
     * Returns a negative, zero, or positive number depending on whether
     * the first element is less than, equal to, or greater than
     * the second element.
     * <p>
     * The default implementation of this method is based on
     * comparing the elements' categories as computed by the <code>category</code>
     * framework method. Elements within the same category are further 
     * subjected to a case insensitive compare of their label strings, either
     * as computed by the content viewer's label provider, or their 
     * <code>toString</code> values in other cases. Subclasses may override.
     * </p>
     * 
     * @param viewer the viewer
     * @param e1 the first element
     * @param e2 the second element
     * @return a negative number if the first element is less  than the 
     *  second element; the value <code>0</code> if the first element is
     *  equal to the second element; and a positive number if the first
     *  element is greater than the second element
     */
    public int compare(Float viewer, Object e1, Object e2) {
    	return 0;
    }

    /**
     * Returns whether this viewer sorter would be affected 
     * by a change to the given property of the given element.
     * <p>
     * The default implementation of this method returns <code>false</code>.
     * Subclasses may reimplement.
     * </p>
     *
     * @param element the element
     * @param property the property
     * @return <code>true</code> if the sorting would be affected,
     *    and <code>false</code> if it would be unaffected
     */
    public boolean isSorterProperty(Object element, String property) {
        return false;
    }

    /**
     * Sorts the given elements in-place, modifying the given array.
     * <p>
     * The default implementation of this method uses the 
     * java.util.Arrays#sort algorithm on the given array, 
     * calling <code>compare</code> to compare elements.
     * </p>
     * <p>
     * Subclasses may reimplement this method to provide a more optimized implementation.
     * </p>
     *
     * @param viewer the viewer
     * @param elements the elements to sort
     */
    public void sort(final Float viewer, Object[] elements) {
    }	
} 