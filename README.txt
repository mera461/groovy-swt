- Getting started

Thanks to Alexander Becher for lots of updates.

================================================================
== Platforms
================================================================

GroovySWT is platform independent, but SWT is not. So if you are on 
another platform than windows then you need to download the appropriate
version of SWT for your platform and include the libraries. 

================================================================
== running the examples
================================================================
The examples can be found in the src/examples/groovy subdirectory. If you run them
from eclipse 3.3 the eclipse libraries are already on the path.

================================================================
== compiling it
================================================================

Building has been moved to gradle with the rest of groovy, so just do a 
	gradle
	
Please also note, that the source includes a maven repository for the needed
eclipse libraries in src/repository, so you don't need to go hunting for eclipse
repositories.

