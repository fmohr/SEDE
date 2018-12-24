package de.upb.sede.entity;

import de.upb.sede.dsl.seco.EntityMethodParam;

import java.util.List;

public interface MethodView {

	boolean isPure();

	boolean isStatic();

	List<EntityMethodParam> outputParams();

	List<EntityMethodParam> inputParams();
}
