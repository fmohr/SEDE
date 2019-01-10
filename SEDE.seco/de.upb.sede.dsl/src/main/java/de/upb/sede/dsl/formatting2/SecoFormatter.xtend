/*2.15.0
 */
package de.upb.sede.dsl.formatting2

import de.upb.sede.dsl.seco.Entries
import de.upb.sede.dsl.seco.Operation
import org.eclipse.xtext.formatting2.AbstractFormatter2
import org.eclipse.xtext.formatting2.IFormattableDocument
import de.upb.sede.dsl.seco.EntityClassDefinition
import static de.upb.sede.dsl.seco.SecoPackage.*
import de.upb.sede.dsl.seco.Argument
import de.upb.sede.dsl.seco.Assignment
import de.upb.sede.dsl.seco.FieldValue
import de.upb.sede.dsl.seco.Field
import de.upb.sede.dsl.seco.Yield
import de.upb.sede.dsl.seco.EntityMethodParam
import de.upb.sede.dsl.seco.EntityMethodParamSignature
import de.upb.sede.dsl.seco.EntityMethod
import de.upb.sede.dsl.seco.EntityCast
import de.upb.sede.dsl.seco.EntityDeploymentDefinition
import de.upb.sede.dsl.seco.DeploymentProcedure
import de.upb.sede.dsl.seco.DeploymentDependency
import de.upb.sede.dsl.seco.EntityMethodInstructions

class SecoFormatter extends AbstractFormatter2 {
	
//	@Inject extension SecoGrammarAccess

	def dispatch void format(Entries entries, extension IFormattableDocument document) {
		// TODO: format HiddenRegions around keywords, attributes, cross references, etc. 
		
		for (entityClassDefinition : entries.entities) {
			format(entityClassDefinition, document)
	        entityClassDefinition.append[newLine]
		}
		for (entityDeploymentDefinition : entries.deployments) {
			format(entityDeploymentDefinition, document)
			entityDeploymentDefinition.append[newLine]
		}
		for (eObject : entries.instructions) {
			format(eObject, document)
			eObject.append[newLine]
		}
	}

	def dispatch void format(Operation operation, extension IFormattableDocument document) {
		// TODO: format HiddenRegions around keywords, attributes, cross references, etc. 
		operation.contextField.format
		for (argument : operation.args) {
			argument.format
		}
	}
	
	def dispatch void format(EntityClassDefinition obj, extension IFormattableDocument doc) {
	    obj.regionFor.feature(eINSTANCE.entityClassDefinition_QualifiedName).surround[oneSpace]
	    obj.regionFor.keyword(':').prepend[noSpace]
	    obj.regionFor.keyword(',').prepend[noSpace]
	    obj.regionFor.keyword('extends')
	    obj.regionFor.keyword('wraps')
	    interior(
	        obj.regionFor.keyword('{').append[newLine],
	        obj.regionFor.keyword('}'),
	        [indent]
	    )
	    for (method : obj.methods) {
			format(method, doc)
	        method.append[setNewLines(1, 1, 2)]
	    }
	    for (cast : obj.casts) {
	        format(cast, doc)
	        cast.append[setNewLines(1, 1, 2)]
	    }
	}
	
	def dispatch void format(Argument obj, extension IFormattableDocument doc) {
		
	} 
	
	def dispatch void format(Assignment obj, extension IFormattableDocument doc) {
		format(obj.value, doc)
	    obj.regionFor.keyword(';').prepend[noSpace]
	} 
	
	def dispatch void format(EntityMethod obj, extension IFormattableDocument doc) {
		obj.regionFor.keyword(':').prepend[noSpace]
	    obj.regionFor.keyword(';').prepend[noSpace]
	    for (instructions : obj.methodInstructions) {
			format(instructions, doc)
	        instructions.prepend[newLine]
	    }
	    
	    format(obj.paramSignature, doc)
	    obj.regionFor.keyword('runtime').append[noSpace].prepend[newLine]
	}
	
	
	def dispatch void format(EntityMethodInstructions obj, extension IFormattableDocument doc) {
		obj.regionFor.keyword(':').prepend[noSpace]
	    interior(
			obj.regionFor.keyword('{'),
			obj.regionFor.keyword('}')
		)[indent]
	    
	    for (instructions : obj.instructions) {
			format(instructions, doc)
	        instructions.prepend[newLine].append[newLine]
	    }
	}
	 
	def dispatch void format(EntityMethodParamSignature obj, extension IFormattableDocument doc) {
		var first = true
		obj.regionFor.keyword('(').append[noSpace]
		obj.regionFor.keyword(')').prepend[noSpace]
	    for(parameter : obj.parameters){
	    	parameter.format
	    	if(!first) {
	    		parameter.prepend[oneSpace].append[noSpace]
    		} else {
	    		parameter.prepend[noSpace].append[noSpace]
    			first = false
    		}
	    }
	    first = true
	    for(parameter : obj.outputs){
	    	parameter.format
	    	if(!first) {
	    		parameter.prepend[oneSpace] .append[noSpace]
    		} else {
	    		parameter.prepend[noSpace]
    			first = false
    		}
	    }
	}
	
	def dispatch void format(EntityMethodParam obj, extension IFormattableDocument doc) {
	    obj.regionFor.keyword('=').prepend[noSpace].append[oneSpace]
		
	} 
	
	def dispatch void format(EntityCast obj, extension IFormattableDocument doc) {
	    obj.regionFor.keyword(':').prepend[noSpace].append[oneSpace]
	    obj.regionFor.keyword(';').prepend[noSpace].append[oneSpace]
		
	} 
	
	def dispatch void format(Yield obj, extension IFormattableDocument doc) {
		
	} 
	
	def dispatch void format(Field obj, extension IFormattableDocument doc) {
		
	} 
	
	def dispatch void format(FieldValue obj, extension IFormattableDocument doc) {
		format(obj.operation, doc)
		format(obj.field, doc)
	} 
	 
	def dispatch void format(EntityDeploymentDefinition obj, extension IFormattableDocument doc) {
	    obj.regionFor.feature(eINSTANCE.entityDeploymentDefinition_QualifiedName).surround[oneSpace]
	    obj.regionFor.keyword(':').prepend[noSpace].append[oneSpace]
	    interior(
	        obj.regionFor.keyword('{').prepend[oneSpace].append[newLine],
	        obj.regionFor.keyword('}'),
	        [indent]
	    )
	    for (procedure : obj.procedures) {
			format(procedure, doc)
	        procedure.append[setNewLines(1, 1, 2)]
	    }
	    for (dependency : obj.dependencies) {
	        format(dependency, doc)
	        dependency.append[setNewLines(1, 1, 2)]
	    }
	}
	
	def dispatch void format(DeploymentProcedure obj, extension IFormattableDocument doc) {
		/*
		"procedure" ":" 
			(
				("name=" name=ID) &
				("act=" act=ID) &
				("fetch=" fetch=ID) &
				("source=" source=STRING)
			)
		";"
		 */
		obj.regionFor.keyword(':').prepend[noSpace].append[oneSpace]
		obj.regionFor.keyword('name=').append[noSpace]
		obj.regionFor.feature(eINSTANCE.deploymentProcedure_Name).prepend[noSpace]
		obj.regionFor.keyword('act=').append[noSpace]
		obj.regionFor.feature(eINSTANCE.deploymentProcedure_Act).prepend[noSpace]
		obj.regionFor.keyword('fetch=').append[noSpace]
		obj.regionFor.feature(eINSTANCE.deploymentProcedure_Fetch).prepend[noSpace]
		obj.regionFor.keyword('source=').append[noSpace]
		obj.regionFor.feature(eINSTANCE.deploymentProcedure_Source).prepend[noSpace]
	    obj.regionFor.keyword(';').prepend[noSpace].append[oneSpace]
	}
	
	def dispatch void format(DeploymentDependency obj, extension IFormattableDocument doc) {
		obj.regionFor.keyword(':').prepend[noSpace].append[oneSpace]
	    obj.regionFor.keyword('(').prepend[noSpace].append[noSpace]
	    obj.regionFor.keyword(')').prepend[noSpace].append[noSpace]
	    obj.regionFor.keyword(';').prepend[noSpace].append[oneSpace]
	}
	
	// TODO: implement for Argument, Assignment, EntityClassDefinition, EntityMethod, EntityMethodParamSignature, EntityMethodParam, Yield, Field, FieldValue
}
