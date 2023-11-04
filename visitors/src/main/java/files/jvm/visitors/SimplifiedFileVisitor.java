package files.jvm.visitors;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/** Overrides the FileVisitor and provides default implementations for methods not commonly needed.
 */
public abstract class SimplifiedFileVisitor
	implements FileVisitor<Path> {

	@ExcludeFromJacocoGeneratedReport
	@Override
	public FileVisitResult preVisitDirectory(
		Path path,
		BasicFileAttributes basicFileAttributes
	) {
		return path != null ? FileVisitResult.CONTINUE
			: FileVisitResult.SKIP_SUBTREE;
	}

	@ExcludeFromJacocoGeneratedReport
	@Override
	public FileVisitResult visitFileFailed(
		Path path,
		IOException exception
	) throws IOException {
		if (exception != null)
			throw exception;
		return FileVisitResult.TERMINATE;
	}

	@ExcludeFromJacocoGeneratedReport
	@Override
	public FileVisitResult postVisitDirectory(
		Path path,
		IOException exception
	) throws IOException {
		if (exception != null)
			throw exception;
		return FileVisitResult.CONTINUE;
	}

}