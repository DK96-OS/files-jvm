package files.jvm.visitors;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Visits Files and Directories in a single level,
 * records the files and directories that were found.
 */
public class DirectoryVisitor {

	/** Whether the Path given in the constructor is a valid directory.
	 */
	public final boolean isValidDirectory;

	@Nullable
	protected final List<String> mDirectories;

	@Nullable
	protected final List<String> mFiles;

	@Nonnull
	protected final Path mPath;

	/** Constructor.
	 * @param path The path to visit.
	 * @throws IOException Any Exception thrown while Visiting the Path.
	 */
	public DirectoryVisitor(
		@Nonnull final Path path
	) throws IOException {
		mPath = path;
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
	@Nonnull
	public List<String> getDirectories() {
		return Objects.requireNonNullElse(
			mDirectories, Collections.emptyList()
		);
	}

	/** Obtain the File names found at this Path.
	 * @return The List of File Names, or an empty List.
	 */
	@Nonnull
	public List<String> getFiles() {
		return Objects.requireNonNullElse(
			mFiles, Collections.emptyList()
		);
	}

	/** Obtain all files in this directory as a List of Paths.
	 * @return A List of Paths, or an empty list.
	 */
	@Nonnull
	public List<Path> getFilePaths() {
		if (mFiles == null)
			return Collections.emptyList();
		return mFiles
			.parallelStream()
			.map(mPath::resolve)
			.toList();
	}

	/** Obtain a Stream of all Files in the Directory.
	 * @return A Stream of File objects.
	 */
	@Nonnull
	public Stream<File> getFileStream() {
		if (mFiles == null)
			return Stream.empty();
		return mFiles
			.parallelStream()
			.map(mPath::resolve)
			.map(Path::toFile);
	}

	/** Obtain all Directories in this directory as a List of Paths.
	 * @return A List of Paths, or an empty list.
	 */
	@Nonnull
	public List<Path> getDirectoryPaths() {
		if (mDirectories == null)
			return Collections.emptyList();
		return mDirectories
			.parallelStream()
			.map(mPath::resolve)
			.toList();
	}

	/** Obtain all Directories in this directory as a Stream of Paths.
	 * @return A Stream of Paths.
	 */
	@Nonnull
	public Stream<Path> getDirectoryPathStream() {
		if (mDirectories == null)
			return Stream.empty();
		return mDirectories
			.parallelStream()
			.map(mPath::resolve);
	}

}