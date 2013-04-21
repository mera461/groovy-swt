package groovy.swt.databinding

import org.eclipse.jface.viewers.ViewerSorter

class ATest {
	
	static class A {
		def calc(a, b) {a+b}
	}
	
	
	
	public static void main (def args) {
		println "s1"
		def a = [calc: {x,y -> x*y}] as A
		println a.calc(3,4)
		println "s2"
		def b = [calc: {x,y -> x*y}] as BTest
		println b.calc(3,4)
		println "x1"
		def x1 = [compare: {} ] as CViewerSorter

		println "x2"
		def x = new ViewerSorter() { def compare(viewer, e1, e2, e3, e4) {} }
		println "x3"
		def y = [compare: {} ] as ViewerSorter
		println "x4"
	}
}
