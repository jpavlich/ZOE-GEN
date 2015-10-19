package co.edu.javeriana.zoe.generator.persistence.templates

import co.edu.javeriana.isml.generator.common.SimpleTemplate
import co.edu.javeriana.isml.isml.Attribute
import co.edu.javeriana.isml.isml.DTO
import co.edu.javeriana.isml.isml.ParameterizedType
import co.edu.javeriana.isml.isml.Primitive
import co.edu.javeriana.isml.scoping.TypeExtension
import co.edu.javeriana.isml.validation.TypeChecker
import com.google.inject.Inject
import org.eclipse.xtext.naming.IQualifiedNameProvider

class DTOTemplate extends SimpleTemplate<DTO> {
	@Inject extension TypeChecker
	/*Inyección de las clases auxiliares con metodos utilitarios*/
	@Inject extension IQualifiedNameProvider
	@Inject extension TypeExtension
	
	/*Metodo callback llamado previo a la ejecución del template*/
	override preprocess(DTO e) {
		//TODO Make your own implementation 
		//var Package parent = e.eContainer as Package;

		//parent.eContents
		//parent.eAllContents

	/**/
	}
	
	def boolean evaluateAttributeToImport(Attribute attribute, DTO dto){
		var boolean needImport=false;
		if(!attribute.type.isCollection){
			needImport=!attribute.type.typeSpecification.eContainer.fullyQualifiedName.equals(dto.eContainer.fullyQualifiedName)
		} else {
			needImport=!(attribute.type as ParameterizedType).typeParameters.get(0).typeSpecification.eContainer.fullyQualifiedName.equals(dto.eContainer.fullyQualifiedName)
		}
		return needImport
	}
	
	def boolean isDatePresent(DTO dto){
		var boolean hasDate=false
		for(attr:dto.attributes){
			if(attr.type.typeSpecification instanceof Primitive && attr.type.typeSpecification.typeSpecificationString.equalsIgnoreCase("Date")){
				hasDate=true
			}
		}
		return hasDate
	}
	

	// TODO Implement hashCode and equals, based in the unique keys of the entity
	/*	Plantilla para generar entidades*/
	override def CharSequence template(DTO dto) '''
		package «dto.eContainer.fullyQualifiedName»;
		«IF isDatePresent(dto)»
			import java.util.Date;
		«ENDIF»
		«/*Se imprimen los imports de las entidades padre */ »		
		«FOR parent : dto.parents»
			«IF !parent.eContainer.eContainer.fullyQualifiedName.equals(dto.eContainer.fullyQualifiedName)»
				import «parent.typeSpecification.fullyQualifiedName»;
			«ENDIF»
		«ENDFOR»
		«/*se recorren e imprimen los tipos de datos necesarios */ »
		«FOR attribute : dto.attributes»
			«IF !(attribute.type.typeSpecification instanceof Primitive) || attribute.type.isCollection»
				«IF evaluateAttributeToImport(attribute,dto)»
					«IF attribute.type instanceof ParameterizedType»
					«IF !((attribute.type as ParameterizedType).typeParameters.get(0).typeSpecification instanceof Primitive)»
					import «(attribute.type as ParameterizedType).typeParameters.get(0).typeSpecification.fullyQualifiedName»;
					«ENDIF»
					«ELSE»
					import «attribute.type.typeSpecification.fullyQualifiedName»;
					«ENDIF»
				«ENDIF»
			«ENDIF»
		«ENDFOR»
		«/*Por medio de el metodo isArrayPresent se importa import java.util.List si hay listas presentes */ »
		«IF dto.attributes.arrayPresent»
			import java.util.*;
		«ENDIF»
		
		/**
		 * This class represents a DTO object named «dto.name.toFirstUpper»
		 */		
		«/*Declaracion de la entidad, se agrega herencia y se implementa serializable*/ »
		public «IF dto.abstract»abstract«ENDIF» class «dto.name.toFirstUpper»«IF !dto.superTypes.empty»extends «dto.superTypes.get(0).typeSpecification.typeSpecificationString»«ENDIF»{			
			«/*Se recorren los atributos del DTO para verificar si son de tipo email, lista o tipos primitivos  */ »
			«FOR Attribute a : dto.attributes»
				«IF a.type.typeSpecification.typeSpecificationString.equalsIgnoreCase("email")»
					/**
					 * Attribute for the type String 
					 */
					private String «a.name»;
				«ELSE»
					«IF a.type.isCollection»
					/**
					 * Attribute for the type «getCollectionString(a.type as ParameterizedType)»<«(a.type as ParameterizedType).typeParameters.get(0).typeSpecification.typeSpecificationString»>
					 */
					private «getCollectionString(a.type as ParameterizedType)»<«(a.type as ParameterizedType).typeParameters.get(0).typeSpecification.typeSpecificationString»> «a.name»;
					«ELSE»
					/**
					 * Attribute for the type «a.type.typeSpecification.typeSpecificationString.toFirstUpper»
					 */
					private «a.type.typeSpecification.typeSpecificationString.toFirstUpper» «a.name»;
					«ENDIF»
				«ENDIF»
			«ENDFOR»
			«/*Constructor del DTO */ »
			/**
			 * Default Constructor method for the DTO Object
			 */
			public «dto.name.toFirstUpper»(){
			}
			
			/**
			 * Optional constructor method for the DTO Object
			 */
			public «dto.name.toFirstUpper»(«FOR Attribute a : dto.attributes SEPARATOR ','»«IF a.type.typeSpecification.typeSpecificationString.equalsIgnoreCase("email")»String «a.name»«ELSE»«IF a.type.isCollection»«getCollectionString(a.type as ParameterizedType)»<«(a.type as ParameterizedType).typeParameters.get(0).typeSpecification.typeSpecificationString»> «a.name»«ELSE»«a.type.typeSpecification.typeSpecificationString.toFirstUpper» «a.name»«ENDIF»«ENDIF»«ENDFOR»){
				«FOR attr : dto.attributes»
				set«attr.name.toFirstUpper»(«attr.name.toFirstLower»);
				«ENDFOR»
			}
			«FOR Attribute a : dto.attributes»	
				«/*Se verifica el caso especial para los tipos email y se coloca como String en los metodos set y get*/ »
				«IF a.type.typeSpecification.typeSpecificationString.equalsIgnoreCase("email")»
					/**
					 * Returns the current value for the attribute «a.name»
					 */			
					public String get«a.name.toFirstUpper»(){
						return «a.name»;
					}
					
					/**
					 * Sets a value for the attribute «a.name»
					 */ 
					public void set«a.name.toFirstUpper»(String «a.name»){
						this.«a.name»=«a.name»;
					}				
				«ELSE»
					«/*Se agregan los metodos set y get a cada atributo de la entidad, verificando si son listas o tipos primitivos de datos */ »
					«IF a.type.isCollection»
							/**
							 * Returns the current value for the attribute «a.name»
							 */			
							public «getCollectionString(a.type as ParameterizedType)»<«(a.type as ParameterizedType).typeParameters.get(0).typeSpecification.typeSpecificationString»> get«a.name.toFirstUpper»(){
								return «a.name»;
							}
							/**
							 * Sets a value for the attribute «a.name»
							 */
							public void set«a.name.toFirstUpper»(«getCollectionString(a.type as ParameterizedType)»<«(a.type as ParameterizedType).typeParameters.get(0).typeSpecification.typeSpecificationString»> «a.name»){
								this.«a.name»=«a.name»;
							}
					«ELSE»
							/**
							 * Returns the current value for the attribute «a.name»
							 */			
							public «a.type.typeSpecification.typeSpecificationString.toFirstUpper» get«a.name.toFirstUpper»(){
								return «a.name»;
							}
							
							/**
							 * Sets a value for the attribute «a.name»
							 */
							public void set«a.name.toFirstUpper»(«a.type.typeSpecification.typeSpecificationString.toFirstUpper» «a.name»){
								this.«a.name»=«a.name»;
							}
					«ENDIF»	
					
				«ENDIF»			
			«ENDFOR»
		}
	'''
	
	
	
	

 
	
	
}
