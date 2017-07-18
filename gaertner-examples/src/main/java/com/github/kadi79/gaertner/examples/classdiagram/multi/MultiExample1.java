package com.github.kadi79.gaertner.examples.classdiagram.multi;

import java.util.List;

import com.github.kadi79.gaertner.annotations.ReferenceType;
import com.github.kadi79.gaertner.annotations.UmlClassDiagram;
import com.github.kadi79.gaertner.examples.classdiagram.multi.util.MultiExampleUtil1;

/**
 * <p>MultiExample1 class.</p>
 *
 * @since 0.0.1
 */
@UmlClassDiagram(filename="doc-files/MultiPackage")
public class MultiExample1 extends SuperMultiExample {

	private MultiExampleUtil1 util;
	@ReferenceType("com.github.kadi79.gaertner.examples.classdiagram.multi.MultiExample2")
	private List<MultiExample2> example2List;

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
