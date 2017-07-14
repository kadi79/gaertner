package com.github.kadi79.gaertner.examples.classdiagram.multi;

import com.github.kadi79.gaertner.annotations.UmlClassDiagram;
import com.github.kadi79.gaertner.examples.classdiagram.multi.util.MultiExampleUtil1;

@UmlClassDiagram(filename="doc-files/MultiPackage")
public class MultiExample2 extends SuperMultiExample {

	private MultiExampleUtil1 util;

	public MultiExampleUtil1 getUtil() {
		return util;
	}

	public void setUtil(MultiExampleUtil1 util) {
		this.util = util;
	}
}
