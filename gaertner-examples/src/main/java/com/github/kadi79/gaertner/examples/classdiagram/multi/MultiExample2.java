package com.github.kadi79.gaertner.examples.classdiagram.multi;

import com.github.kadi79.gaertner.annotations.UmlClassDiagram;
import com.github.kadi79.gaertner.examples.classdiagram.multi.util.MultiExampleUtil1;

/**
 * <p>MultiExample2 class.</p>
 *
 * @since 0.0.1
 */
@UmlClassDiagram(filename="doc-files/MultiPackage")
public class MultiExample2 extends SuperMultiExample {

	private MultiExampleUtil1 util;

	/**
	 * <p>Getter for the field <code>util</code>.</p>
	 *
	 * @return a {@link com.github.kadi79.gaertner.examples.classdiagram.multi.util.MultiExampleUtil1} object.
	 */
	public MultiExampleUtil1 getUtil() {
		return util;
	}

	/**
	 * <p>Setter for the field <code>util</code>.</p>
	 *
	 * @param util a {@link com.github.kadi79.gaertner.examples.classdiagram.multi.util.MultiExampleUtil1} object.
	 */
	public void setUtil(MultiExampleUtil1 util) {
		this.util = util;
	}
}
