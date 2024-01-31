package files.jvm.builders.input;

import java.util.List;
import java.util.Objects;

import files.jvm.builders.data.TreeNodeRecord;

/** The Default Input Line Reader.
 */
public final class InputLineReader {

	final InputCharacters mCharacters = new InputCharacters();

	/** Calculates the depth of a line in the tree structure.
	 * @param line A line from the tree command output.
	 * @return The depth of the line in the tree structure.
	 */
	short calculateDepth(
		final String line
	) {
		long spaceCount = mCharacters.countStartingSpaces(line);
		final short depth = (short) (spaceCount >>> 1);
		if (depth << 1 == spaceCount)
			return depth;
		throw new IllegalArgumentException("Invalid Spacing in Line");
	}

	/** Creates a string of space chars equivalent to the given depth.
	 * Default is the first space in the list of accepted spaces.
	 * @param depth The amount of depth in the Tree Node Structure.
	 * @return The depth of the line in the tree structure.
	 */
	String createDepth(
		int depth
	) {
		return createDepth(depth, 0);
	}

	/** Creates a string of space chars equivalent to the given depth.
	 * @param depth The amount of depth in the Tree Node Structure.
	 * @param spaceChar The specific whitespace character to use.
	 * @return The depth of the line in the tree structure.
	 */
	String createDepth(
		int depth,
		int spaceChar
	) {
		final char space = mCharacters.getSpaceCharAt(spaceChar);
		if (depth < 2) {
			if (depth == 1) return String.valueOf(new char[]{space, space});
			return "";
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < depth; ++i) {
			builder.append(space);
			builder.append(space);
		}
		return builder.toString();
	}

	/** Divide the Input into multiple Arguments.
	 * @param input The String to check for Arguments.
	 * @return A List containing one or more Arguments.
	 */
	List<String> splitArguments(
		final String input
	) {
		List<String> args = null;
		// Search for multiple arguments
		for (int ch : mCharacters.SpaceChars) {
			int charIndex = input.indexOf((char) ch);
			if (charIndex < 0)
				continue;
			// Split in two at this index
			args = List.of(
				input.substring(0, charIndex),
				input.substring(charIndex + 1).stripLeading()
			);
			break;
		}
		return Objects.requireNonNullElse(args, List.of(input));
	}

	/** Process A Line from the Input Data.
	 * @param line The Line to process.
	 * @return A TreeNodeRecord of the Line Data, or null.
	 */
	public TreeNodeRecord processLine(
		final String line
	) {
		final short depth;
		boolean isDirectory;
		String name;
		String data;
		// Search for multiple arguments
		final List<String> args = splitArguments(line.trim());
		// Count The Arguments
		if (args.size() == 0)
			return null;
		// First arg is the Node Name
		name = args.get(0);
		isDirectory = mCharacters.isDirectory(name);
		// Check if name needs to be fixed
		if (isDirectory) {
			// Remove all Directory chars
			for (int ch : mCharacters.DirectoryChars) {
				name = name.replace(
					String.valueOf((char) ch), ""
				);
			}
		}
		// Calculate depth after arg valid, because it throws on odd space counts
		depth = calculateDepth(line);
		// Check Arguments for Data
		data = args.size() == 1 ? "" : args.get(1);
		// Create Data Object
		return new TreeNodeRecord(
			depth,
			isDirectory,
			name,
			data
		);
	}

}