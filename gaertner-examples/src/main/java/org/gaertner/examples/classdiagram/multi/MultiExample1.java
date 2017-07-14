package org.gaertner.examples.classdiagram.multi;

import java.util.List;

import org.gaertner.annotations.ReferenceType;
import org.gaertner.annotations.UmlClassDiagram;
import org.gaertner.examples.classdiagram.multi.util.MultiExampleUtil1;

@UmlClassDiagram(filename="doc-files/MultiPackage")
public class MultiExample1 extends SuperMultiExample {

	private MultiExampleUtil1 util;
	@ReferenceType("org.gaertner.examples.classdiagram.multi.MultiExample2")
	private List<MultiExample2> example2List;

	public MultiExampleUtil1 getUtil() {
		return util;
	}

	public void setUtil(MultiExampleUtil1 util) {
		this.util = util;
	}
}
