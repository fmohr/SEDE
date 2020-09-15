package ai.services.composition.graphs;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import ai.services.composition.graphs.nodes.BaseNode;

import javax.annotation.Nullable;

public class DummyNode implements BaseNode {

    static long GLOBAL_INDEX = 0;
	String name = "noname";

    long index = GLOBAL_INDEX++;
	private Collection<String> producingFields;
	private Collection<String> consumingFields;

	DummyNode() {
		super();
	}

	public DummyNode(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}

    @Override
    public Map<String, Object> getRuntimeAuxiliaries() {
        return Collections.EMPTY_MAP;
    }

    @Nullable
    @Override
    public String getHostExecutor() {
        return "";
    }

    @Override
    public Long getIndex() {
        return index;
    }
}
