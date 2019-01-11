package de.upb.sede.dsl;

import java.util.stream.Collectors;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Internal;

import de.upb.sede.dsl.seco.Argument;
import de.upb.sede.dsl.seco.Assignment;
import de.upb.sede.dsl.seco.EntityClassDefinition;
import de.upb.sede.dsl.seco.Entries;
import de.upb.sede.dsl.seco.Field;
import de.upb.sede.dsl.seco.FieldValue;
import de.upb.sede.dsl.seco.Operation;
import de.upb.sede.dsl.seco.SecoPackage;
import de.upb.sede.dsl.seco.util.SecoSwitch;

public class SecoObject extends MinimalEObjectImpl.Container{
	private static final SecoToStringSwitch toStringHelper = new SecoToStringSwitch();
	@Override
	public String toString() {
		try {
			return toStringHelper.doSwitch(this);
		} catch (Exception ex) { // fall back:
			return fallBackToString();
		}
	}
	
	private String fallBackToString() {
		return super.toString();
	}
	
	public void setRes(Resource res) {
		eSetDirectResource((Internal) res);
	}
	private static class SecoToStringSwitch extends SecoSwitch<String> {
		@Override
		public String caseField(Field object) {
			return object.getName() + (object.isDereference()?  "->" + doSwitch(object.getMember()) : "");
		}
		/**
		 *
		 (=> contextField = Field | entityName = EntityName) ("::")
		 	method=MethodName "(" "{"? 
				(
					(args+=Argument)		// First argument
					(',' args+=Argument)*  // Additional Arguments
				)? "}" ? ")" 
		 */
		@Override
		public String caseOperation(Operation object) {
			return 
					(object.getContextField() == null? object.getEntityName() : doSwitch(object.getContextField()))
					+ "::" + object.getMethod() + "("
						+ object.getArgs().stream().map(this::doSwitch).collect(Collectors.joining(", "))
					+ ")";
		}
		
		/*
			Argument:
				(((-> indexed ?= "i" index = INT)| parameterName = ID) '=')? value = FieldValue
			;
		*/
		@Override
		public String caseArgument(Argument object) {
			return 
					 (object.isIndexed()? "i" + object.getIndex() + "=" : "")
					+(object.getParameterName()==null? "" : object.getParameterName() + "=")
					+doSwitch(object.getValue())
					;
		}
		
		/*

			Assignment:
				(=> assignedFields += Field (multiAssignment ?= ',' assignedFields+= Field)* "=")? value = FieldValue ";"
			;
		*/
		@Override
		public String caseAssignment(Assignment object) {
			Entries entries = SecoUtil.createEntries(object);
			return entries.toString();
		}
		
		@Override
		public String caseEntityClassDefinition(EntityClassDefinition def) {
			return SecoUtil.createEntries(def).toString();
		}
		
		/*
		FieldValue:
			( -> cast ?='(' castTarget = EntityName ')'  castValue = __TerminalFieldValue) |
			number = NumberConst |
			string = StringConst | 
			bool = BoolConst | 
			null ?= NullConst | 
//			(entityReference ?= "ref"  entity = EntityName ('::' method=MethodName)?) | // TODO: do we need entity references?
			(=> operation = Operation) | 
			(field = Field)
		;*/	
		@Override
		public String caseFieldValue(FieldValue fieldValue) {
			return 
				 (fieldValue.getBool() == null ? "" : fieldValue.getBool())
				+(fieldValue.getCastTarget() == null ? "" : "(" + fieldValue.getCastTarget() + ")" + doSwitch(fieldValue.getCastValue()))
				+(fieldValue.getString() == null ? "" : "\"" + fieldValue.getString() + "\"")
				+(fieldValue.getNumber() == null ? "" : fieldValue.getNumber())
				+(fieldValue.isNull()? "Null" : "")
				+(fieldValue.getOperation()==null? "" : doSwitch(fieldValue.getOperation()))
				+(fieldValue.getField()==null? "" : doSwitch(fieldValue.getField()))
				;
		}
		
		@Override
		public String caseEntries(Entries entries) {
			return SecoUtil.serializeSilently(entries);
		}
		
		
	}
}