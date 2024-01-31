package files.jvm.builders.data;

/** The key data points for a Tree Node.
 */
public record TreeNodeRecord(
	int depth,
	boolean isDirectory,
	String name,
	String data
) {}