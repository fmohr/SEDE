package de.upb.sede.composition.graphs;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import de.upb.sede.composition.graphs.nodes.BaseNode;

import javax.annotation.Nullable;

public class DummyNode implements BaseNode {

	String name = "noname";

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
    public Optional<Long> getIndex() {
        return Optional.empty();
    }
}
