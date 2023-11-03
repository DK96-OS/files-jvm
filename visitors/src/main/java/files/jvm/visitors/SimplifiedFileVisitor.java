package files.jvm.visitors;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;

/** Overrides the FileVisitor and provides default implementations for methods not commonly needed.
 */
public abstract class SimplifiedFileVisitor
	implements FileVisitor<Path> {

	@Override
	public FileVisitResult visitFileFailed(
		Path path,
		IOException exception
	) {
		return FileVisitResult.TERMINATE;
	}

	@Override
	public FileVisitResult postVisitDirectory(
		Path path,
		IOException e
	) throws IOException {
		if (e != null)
			throw e;
		return FileVisitResult.CONTINUE;
	}

}