# Package Explorer
> Allows you to get information about java classes in a specified directory.

![](terminal.png)

# Description
Package Explorer uses Java Reflection API to list all the .class files in the specified directory and let a user view the details of each class. (Class name, SuperClass, Interface, Fields, Methods, Providers, Clients).

* Providers - list of classes within the same directory that this class depends on. 
* Clients - list of classes within the same directory that depend on this class.

The program allows saving individual/all class files in XML by serializing with Java's XMLEncoder library. You can load files back in the program by choosing to load previosly saved XML file containing all classes.  

## Usage

Terminal:

Open the folder containing jar and run:

```sh
cd PackageExplorer/out/artifacts/PackageExplorer_jar
java -jar PackageExplorer.jar PackageExplorer/out/production/PackageExplorer
```
If the first argument is empty, the program will search for .class files in the current directory.
 
