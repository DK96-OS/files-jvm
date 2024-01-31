package files.jvm.builders.input;

import java.util.Arrays;

/** Manages the Key Characters found in valid Inputs.
 */
public class InputCharacters {

	final int[] SpaceChars = new int[]{' ', ' ', ' ', ' '};

	final int[] DirectoryChars = new int[]{'/', '\\'};

	/** Determines if this string contains any directory characters.
	 * @param name The Name of the Node. May contain directory characters.
	 * @return Whether the name contained any directory characters.
	 */
	public boolean isDirectory(
		final String name
	) {
		final boolean containsCharacters = name.chars()
			.anyMatch(i ->
				Arrays.stream(DirectoryChars).anyMatch(it -> it == i)
			);
		if (!containsCharacters)
			return false;
		// todo: Check that the character is at the start or end of the name
		return true;
	}

	/** Count the number of space characters at the start of the
	 * @param line The input line to count spaces.
	 * @return The number of consecutive space characters at the start of the input.
	 */
	public long countStartingSpaces(
		final String line
	) {
		return line.chars()
			.takeWhile(i -> Arrays.binarySearch(SpaceChars, i) >= 0)
			.count();
	}

	/** Obtain a Space Character value from the internal collection of accepted values.
	 * @param index The index of the character in the collection.
	 * @return The Char value, representing a space.
	 */
	public char getSpaceCharAt(
		final int index
	) {
		char space = (char) SpaceChars[0];
		if (0 < index && index < SpaceChars.length) {
			space = (char) SpaceChars[index];
		}
		return space;
	}

}