package files.jvm.visitors;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/** Visits Files and Directories in a single level,
 * records the files and directories that were found in a list.
 */
public class SingleLevelFileVisitor {

	/** Whether the Path given in the constructor is a valid directory.
	 */
	public final boolean isValidDirectory;

	private final List<String> mDirectories;

	private final List<String> mFiles;

	/** Constructor.
	 * @param path The path to visit.
	 * @throws IOException Any Exception thrown while Visiting the Path.
	 */
	public SingleLevelFileVisitor(
		final Path path
	) throws IOException {
		if (!Files.isDirectory(path)) {
			isValidDirectory = false;
			mDirectories = null;
			mFiles = null;
			return;
		}
		isValidDirectory = true;
		// Create Mutable Collections to store verified files and directories.
		final var directories = new ArrayList<String>();
		final var files = new ArrayList<String>();
		// Create A Temporary Visitor
		final var visitor = new SimplifiedFileVisitor() {
			@Override
			public FileVisitResult visitFile(
				Path path, BasicFileAttributes basicFileAttributes
			) {
				if (path == null) {
					return FileVisitResult.TERMINATE;
				}
				final String name = path.getFileName().toString();
				if (Files.isDirectory(path)) {
					directories.add(name);
				} else {
					files.add(name);
				}
				return FileVisitResult.CONTINUE;
			}
		};
		// Search the Path using the temporary file visitor
		Files.walkFileTree(
			path,
			Set.of(),
			1,
			visitor
		);
		// Check for empty Collections of data
		mDirectories = directories.isEmpty() ? null
			: directories;
		mFiles = files.isEmpty() ? null
			: files;
	}

	/** Obtain the directory names found at this Path.
	 * @return The List of directories, or an empty List.
	 */
	public List<String> getDirectories() {
		if (mDirectories == null)
			return Collections.emptyList();
		return mDirectories;
	}

	/** Obtain the File names found at this Path.
	 * @return The List of File Names, or an empty List.
	 */
	public List<String> getFiles() {
		if (mFiles == null)
			return Collections.emptyList();
		return mFiles;
	}

}