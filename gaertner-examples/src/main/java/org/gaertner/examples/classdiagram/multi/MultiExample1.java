package org.gaertner.examples.classdiagram.multi;

import org.gaertner.annotations.UmlClassDiagram;
import org.gaertner.examples.classdiagram.multi.util.MultiExampleUtil1;

@UmlClassDiagram(filename="doc-files/MultiPackage")
public class MultiExample1 extends SuperMultiExample {

	private MultiExampleUtil1 util;

	public MultiExampleUtil1 getUtil() {
		return util;
	}

	public void setUtil(MultiExampleUtil1 util) {
		this.util = util;
	}
}
