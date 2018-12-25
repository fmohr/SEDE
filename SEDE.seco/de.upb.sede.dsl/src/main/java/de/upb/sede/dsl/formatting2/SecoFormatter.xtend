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

class SecoFormatter extends AbstractFormatter2 {
	
//	@Inject extension SecoGrammarAccess

	def dispatch void format(Entries entries, extension IFormattableDocument document) {
		// TODO: format HiddenRegions around keywords, attributes, cross references, etc. 
		
		for (entityClassDefinition : entries.entities) {
			format(entityClassDefinition, document)
	        entityClassDefinition.append[newLine]
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
	    obj.regionFor.keyword(':').prepend[noSpace].append[oneSpace]
	    obj.regionFor.keyword(',').prepend[noSpace].append[oneSpace]
	    obj.regionFor.keyword('extends').surround[oneSpace]
	    obj.regionFor.keyword('wraps').surround[oneSpace]
	    interior(
	        obj.regionFor.keyword('{').prepend[oneSpace].append[newLine],
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
	} 
	
	def dispatch void format(EntityMethod obj, extension IFormattableDocument doc) {
		obj.regionFor.keyword(':').prepend[noSpace].append[oneSpace]
	    format(obj.paramSignature, doc)
	}
	 
	def dispatch void format(EntityMethodParamSignature obj, extension IFormattableDocument doc) {
		
		obj.regionFor.keyword('(').prepend[oneSpace].append[noSpace]
		obj.regionFor.keyword(')').prepend[noSpace] .append[oneSpace]
	    for(parameter : obj.parameters){
	    	parameter.format
//	    	parameter.prepend[noSpace].append[oneSpace]
	    }
	}
	
	def dispatch void format(EntityMethodParam obj, extension IFormattableDocument doc) {
	    obj.regionFor.keyword('=').prepend[noSpace].append[oneSpace]
		
	} 
	
	def dispatch void format(EntityCast obj, extension IFormattableDocument doc) {
	    obj.regionFor.keyword(':').prepend[noSpace].append[oneSpace]
		
	} 
	
	def dispatch void format(Yield obj, extension IFormattableDocument doc) {
		
	} 
	
	def dispatch void format(Field obj, extension IFormattableDocument doc) {
		
	} 
	
	def dispatch void format(FieldValue obj, extension IFormattableDocument doc) {
		format(obj.operation, doc)
		format(obj.field, doc)
	} 
	 
	
	// TODO: implement for Argument, Assignment, EntityClassDefinition, EntityMethod, EntityMethodParamSignature, EntityMethodParam, Yield, Field, FieldValue
}
