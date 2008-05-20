- Getting started

Thanks to Alexander Becher for lots of updates.

================================================================
== Platforms
================================================================

GroovySWT is platform independent, but SWT is not. So if you are on 
another platform than windows then you need to download the appropriate
version of SWT for your platform and include the libraries. 

================================================================
== installing on windows
================================================================

copy
	eclipse/plugins/org.eclipse.swt.win32_3.0.0/os/win32/x86/swt-win32-3123.dll 
to 
	a directory in your system environment variable $PATH	

(Alternatively you can run the examples by using the -Djava.library.path vm argument)

All test cases will also fail when you do not have these libraries properly installed.
     
Alternatively you can download the RCP Runtime Binary from
the www.eclipse.org download pages. This download 
also contains most libraries but the forms.jar is missing.

================================================================
== running the examples
================================================================
The examples can be found in the src/main/groovy subdirectory. If you run them
from eclipse 3.3 the eclipse libraries are already on the path.

If you want to run them from outside eclipse you need the following
libraries on the path:
- groovy-swt-0.3.jar
- commands-3.3.0-I20070605-0010.jar
- common-3.3.0-v20070426.jar
- forms-3.3.0-v20070511.jar
- jface-3.3.0-I20070606-0010.jar
- runtime-3.3.100-v20070530.jar
- swt-3.3.0-v3346.jar
- x86-3.3.0-v3346.jar

If you use the forms (see groovy.swt.examples.FormDemo) you also need:
- org.eclipse.osgi_3.3.2.R33x_v20080105.jar 
- com.ibm.icu_3.6.1.v20070906.jar 


And the following dll on the path:
- swt-win32-3347.dll
	
You can either copy them to the groovy/lib directory or include them on
the classpath with
	groovy -cp groovy-swt-0.2.jar;forms-3.3.0-v20070511.jar;jface-3.3.0-I20070606-0010.jar;runtime-3.3.100-v20070530.jar;common-3.3.0-v20070426.jar;x86-3.3.0-v3346.jar;commands-3.3.0-I20070605-0010.jar <Your file>.groovy



================================================================
== compiling it
================================================================

With new the maven 2 version it is no longer necessary to 
maintain your local eclipse repository. There is a eclipse repository
at repo1.maven.org, but it is not very consistent. Use the fornax 
repository instead (it is slow, but consistent).

Use the maven commands:
mvn compile
mvn test-compile
mvn test
mvn package

To use eclipse you need the mavenide.


