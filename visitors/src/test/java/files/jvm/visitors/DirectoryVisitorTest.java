package files.jvm.visitors;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/** Testing SingleLevelFileVisitor class.
 */
public final class DirectoryVisitorTest {

	private final Path testProjectDirectory = Path.of("test_directory");

	private final int numberOfSubDirectories = 4;

	private final int numberOfFiles = 5;

	private DirectoryVisitor mInstance;

	@Before
	public void testSetup() throws IOException {
		mInstance = new DirectoryVisitor(testProjectDirectory);
	}

	@Test
	public void testInitialCondition() {
		assertTrue(
			mInstance.isValidDirectory
		);
		assertEquals(
			numberOfSubDirectories,
			mInstance.getDirectories().size()
		);
		assertEquals(
			numberOfFiles,
			mInstance.getFiles().size()
		);
	}

	@Test
	public void testConstructor_FilePath_NotValidDirectory() throws IOException {
		final Path filePath = Path.of("test_project/build.gradle");
		mInstance = new DirectoryVisitor(filePath);
		assertFalse(
			mInstance.isValidDirectory
		);
		assertEquals(
			0,
			mInstance.getDirectories().size()
		);
		assertEquals(
			0,
			mInstance.getFiles().size()
		);
	}

	@Test
	public void testConstructor_InvalidPath_Fails() throws IOException {
		final Path invalidPath = Path.of("invalid_path");
		mInstance = new DirectoryVisitor(invalidPath);
		assertFalse(
			mInstance.isValidDirectory
		);
		assertEquals(
			0,
			mInstance.getDirectories().size()
		);
		assertEquals(
			0,
			mInstance.getFiles().size()
		);
	}

	@Test
	public void testConstructor_SampleDirectory_OnlyContainsSingleFile() throws IOException {
		final Path testSampleDirectory = testProjectDirectory.resolve("samples");
		mInstance = new DirectoryVisitor(testSampleDirectory);
		assertTrue(
			mInstance.isValidDirectory
		);
		assertEquals(
			0,
			mInstance.getDirectories().size()
		);
		assertEquals(
			1,
			mInstance.getFiles().size()
		);
	}

	@Test
	public void testGetFilePaths_CorrectSize() {
		final List<Path> pathList = mInstance.getFilePaths();
		assertEquals(
			numberOfFiles,
			pathList.size()
		);
	}

	@Test
	public void testGetFilePaths_InvalidDirectory_ReturnsEmptyList() throws IOException {
		final Path invalidPath = Path.of("invalid_path");
		mInstance = new DirectoryVisitor(invalidPath);
		assertFalse(
			mInstance.isValidDirectory
		);
		assertEquals(
			0, mInstance.getFilePaths().size()
		);
	}

	@Test
	public void testGetFileStream_() {
		final var pathList = mInstance.getFileStream();
		assertEquals(
			numberOfFiles,
			pathList.toList().size()
		);
	}

	@Test
	public void testGetDirectoryPaths_CorrectSize() {
		final List<Path> pathList = mInstance.getDirectoryPaths();
		assertEquals(
			numberOfSubDirectories,
			pathList.size()
		);
	}

	@Test
	public void testGetDirectoryPaths_InvalidPath_ReturnsEmptyList() throws IOException {
		final Path invalidPath = Path.of("invalid_path");
		mInstance = new DirectoryVisitor(invalidPath);
		assertFalse(
			mInstance.isValidDirectory
		);
		assertEquals(
			0, mInstance.getDirectoryPaths().size()
		);
	}

	@Test
	public void testGetDirectoryPathStream_() {
		final var pathList = mInstance.getDirectoryPathStream();
		assertEquals(
			numberOfSubDirectories,
			pathList.toList().size()
		);
	}

}