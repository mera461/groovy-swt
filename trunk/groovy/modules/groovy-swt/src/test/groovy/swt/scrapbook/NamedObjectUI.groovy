package groovy.swt.scrapbook

println "start building ..."
builder.composite( parent ) {
	rowLayout()
	label( style:"none", obj.description ) 
	text( style:"Border", obj.value ) 
}

println "end building ..."

