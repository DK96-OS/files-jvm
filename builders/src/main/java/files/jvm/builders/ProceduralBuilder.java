package files.jvm.builders;

import java.util.Stack;

import files.jvm.builders.input.InputLineReader;

/** The Builder that follows a strict logical procedure.
 */
public final class ProceduralBuilder {

	final InputLineReader reader = new InputLineReader();

	final Stack<String> pathStack = new Stack<>();

	/** Constructor.
	 */
	public ProceduralBuilder() {
		pathStack.add(".");
	}

}