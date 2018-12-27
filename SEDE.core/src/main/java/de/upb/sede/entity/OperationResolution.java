package de.upb.sede.entity;

import de.upb.sede.dsl.seco.Operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OperationResolution {
	private final Operation operation;
	private final MethodView methodView;
	private final List<List<ClassCastPath>> argsCastPaths = new ArrayList<>();

	OperationResolution(Operation operation, MethodView methodView) {
		this.operation = operation;
		this.methodView = methodView;
	}

	public Operation getOperation() {
		return operation;
	}

	public MethodView getMethodView() {
		return methodView;
	}

	public List<List<ClassCastPath>> getArgsCastPaths() {
		return Collections.unmodifiableList(argsCastPaths);
	}

	void addArgCast(List<ClassCastPath> castPath) {
		argsCastPaths.add(castPath);
	}
}
