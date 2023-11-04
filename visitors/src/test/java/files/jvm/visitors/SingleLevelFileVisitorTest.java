package files.jvm.visitors;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;

/** Testing SingleLevelFileVisitor class.
 */
public final class SingleLevelFileVisitorTest {

	private final Path testProjectDirectory = Path.of("test_directory");

	private SingleLevelFileVisitor mInstance;

	@Before
	public void testSetup() throws IOException {
		mInstance = new SingleLevelFileVisitor(testProjectDirectory);
	}

	@Test
	public void testInitialCondition() {
		assertTrue(
			mInstance.isValidDirectory
		);
		assertEquals(
			4,
			mInstance.getDirectories().size()
		);
		assertEquals(
			5,
			mInstance.getFiles().size()
		);
	}

	@Test
	public void testConstructor_FilePath_NotValidDirectory() throws IOException {
		final Path filePath = Path.of("test_project/build.gradle");
		mInstance = new SingleLevelFileVisitor(filePath);
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

}