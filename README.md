## Files - JVM
A repository of tools for working with Files on the JVM (Java Virtual Machine).

### Getting Started
Each Module is available as an independent downloadable package that can be quickly imported using the GitHub Maven package registry.

You can import any module into your project using Gradle or maven.

Here is how to implement a package using Gradle:

1. Add this to your dependencies block:
`implementation "io.github.dk96-os.files-jvm:visitors:$package_version"`

2. Add the GitHub packages maven repository.

### Visitors Module
The concept of a File Visitor is built into the Java Standard Library.
For more information on the Java Standard Library, see: [java.nio.file.FileVisitor](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/nio/file/FileVisitor.html)

#### Directory Visitor
The more important class in the Visitors module is the `Directory Visitor`.
This class requires a Path to be given in the constructor.
The path should be a valid directory, otherwise the `isValidDirectory` flag will be false, and all methods will return empty lists.

When the given Path is a valid directory, a `Simplified File Visitor` is created that collects the names of files and subdirectories that are the direct descendants (only one level deep).
The names are stored for the lifetime of the `Directory Visitor`, and can be accessed as a list of Files, or a list of Directories.
There are convenient methods available that can map the names into Lists of Path objects.

#### Simplified File Visitor
Within the Visitors module, you will also find a `Simplified File Visitor`.
This is an abstract class that provides reasonable default method implementations for most of the required `FileVisitor` methods.
It is intended to make other classes in the Visitor Module easier to read by applying the default method implementations for `FileVisitor`.


