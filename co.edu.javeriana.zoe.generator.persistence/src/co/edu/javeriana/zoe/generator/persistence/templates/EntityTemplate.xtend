package co.edu.javeriana.zoe.generator.persistence.templates

import co.edu.javeriana.isml.generator.common.SimpleTemplate
import co.edu.javeriana.isml.isml.Attribute
import co.edu.javeriana.isml.isml.Constraint
import co.edu.javeriana.isml.isml.Entity
import co.edu.javeriana.isml.isml.Enum
import co.edu.javeriana.isml.isml.Instance
import co.edu.javeriana.isml.isml.LiteralValue
import co.edu.javeriana.isml.isml.Parameter
import co.edu.javeriana.isml.isml.ParameterizedType
import co.edu.javeriana.isml.isml.Primitive
import co.edu.javeriana.isml.scoping.TypeExtension
import co.edu.javeriana.isml.validation.TypeChecker
import com.google.inject.Inject
import org.eclipse.xtext.naming.IQualifiedNameProvider

class EntityTemplate extends SimpleTemplate<Entity> {
	@Inject extension TypeChecker
	/*Inyección de las clases auxiliares con metodos utilitarios*/
	@Inject extension IQualifiedNameProvider
	@Inject extension TypeExtension
	
	/*Metodo callback llamado previo a la ejecución del template*/
	override preprocess(Entity e) {
		//TODO Make your own implementation 
		//var Package parent = e.eContainer as Package;

		//parent.eContents
		//parent.eAllContents

	/**/
	}
	
	def boolean evaluateAttributeToImport(Attribute attribute, Entity entity){
		var boolean needImport=false;
		if(!attribute.type.isCollection){
			needImport=!attribute.type.typeSpecification.eContainer.fullyQualifiedName.equals(entity.eContainer.fullyQualifiedName)
		} else {
			needImport=!(attribute.type as ParameterizedType).typeParameters.get(0).typeSpecification.eContainer.fullyQualifiedName.equals(entity.eContainer.fullyQualifiedName)
		}
		return needImport
	}
	
	def boolean isDatePresent(Entity entity){
		var boolean hasDate=false
		for(attr:entity.attributes){
			if(attr.type.typeSpecification instanceof Primitive && attr.type.typeSpecification.typeSpecificationString.equalsIgnoreCase("Date")){
				hasDate=true
			}
		}
		return hasDate
	}

	def CharSequence getParametersTemplate(Instance constraint) '''
		«IF !constraint.parameters.empty»
		(«FOR Parameter parameter : setParametersValue(
			(constraint.type.typeSpecification as Constraint).parameters, constraint.parameters) SEPARATOR ","»
			«parameter.name»=«IF parameter.type.typeSpecification.typeSpecificationString.equalsIgnoreCase("string")»"«(parameter.
			value as LiteralValue).literal»"«ELSE»«(parameter.
			value as LiteralValue).literal»«ENDIF»«ENDFOR»)
		 «ENDIF»	
	'''

	// TODO Implement hashCode and equals, based in the unique keys of the entity
	/*	Plantilla para generar entidades*/
	override def CharSequence template(Entity entity) '''
		package «entity.eContainer.fullyQualifiedName»;
		«/*imports necesarios de libreria javax*/ »
		import javax.persistence.*;
		import javax.validation.constraints.*;
		import java.io.Serializable;
		import javax.xml.bind.annotation.XmlRootElement;
		«IF isDatePresent(entity)»
			import java.util.Date;
		«ENDIF»
		«/*Se imprimen los imports de las entidades padre */ »		
		«FOR parent : entity.parents»
			«IF !parent.eContainer.eContainer.fullyQualifiedName.equals(entity.eContainer.fullyQualifiedName)»
				import «parent.typeSpecification.fullyQualifiedName»;
			«ENDIF»
		«ENDFOR»
		«/*se recorren e imprimen los tipos de datos necesarios */ »
		«FOR attribute : entity.attributes»
			«IF !(attribute.type.typeSpecification instanceof Primitive) || attribute.type.isCollection»
				«IF evaluateAttributeToImport(attribute,entity)»
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
		«IF entity.attributes.arrayPresent»
			import java.util.*;
		«ENDIF»
		«/*Se verfica si la entidad es padre*/ »
		«IF isParent(entity)»
			import javax.persistence.Inheritance;
			import javax.persistence.InheritanceType;
		«ENDIF»
		
		/**
		 * This class represents a persistent entity object named «entity.name.toFirstUpper» 
		 */
		@Entity
		@XmlRootElement
		«/*Se verfica si la entidad es padre*/ »
		«IF isParent(entity)»
		@Inheritance(strategy=InheritanceType.JOINED)
		@DiscriminatorColumn(name="«entity.name.toUpperCase»_TYPE", discriminatorType=DiscriminatorType.STRING)
		«ENDIF»
		
		«IF isSon(entity)»
		@DiscriminatorValue(value="«entity.name.toUpperCase»")
		«ENDIF»
		«/*Declaracion de la entidad, se agrega herencia y se implementa serializable*/ »
		public «IF entity.abstract»abstract«ENDIF» class «entity.name.toFirstUpper» «IF !entity.superTypes.empty»extends «entity.superTypes.get(0).typeSpecification.typeSpecificationString» «ENDIF»implements Serializable {
			/**
			 * The serialVersionUID
			 */
			private static final long serialVersionUID = 1L;
			«/*Se verifica si la entidad no tiene padres para generar el id*/ »
			«IF entity.parents.empty»
				/**
				 * The unique id for the entity
				 */		
				private Long id = null;
			«ENDIF»
			«/*Se recorren los atributos de la entidad para verificar si son de tipo email, lista o tipos primitivos  */ »
			«FOR Attribute a : entity.attributes»
				«IF a.type.typeSpecification.typeSpecificationString.equalsIgnoreCase("email")»
					/**
					 * The «a.name» for the «entity.name.toFirstUpper»
					 */
					private String «a.name»;
				«ELSE»
					«IF a.type.isCollection»
					/**
					 * The «a.name» for the «entity.name.toFirstUpper»
					 */
					private «getCollectionString(a.type as ParameterizedType)»<«(a.type as ParameterizedType).typeParameters.get(0).typeSpecification.typeSpecificationString»> «a.name»;
					«ELSE»
					/**
					 * The «a.name» for the «entity.name.toFirstUpper»
					 */
					private «a.type.typeSpecification.typeSpecificationString.toFirstUpper» «a.name»;
					«ENDIF»
				«ENDIF»
			«ENDFOR»
			«/*Constructor de la entidad */ »
			/**
			 * Default Entity constructor method
			 */
			public «entity.name.toFirstUpper»(){
			}
			
			/**
			 * Needed constructor to transfer entity through RESTFul
			 */
			public «entity.name.toFirstUpper»(String param){
			}
				
			/**
			 * Optional Entity constructor method
			 */
			public «entity.name.toFirstUpper»(«FOR Attribute a : entity.attributes SEPARATOR ','»«IF a.type.typeSpecification.typeSpecificationString.equalsIgnoreCase("email")»String «a.name»«ELSE»«IF a.type.isCollection»«getCollectionString(a.type as ParameterizedType)»<«(a.type as ParameterizedType).typeParameters.get(0).typeSpecification.typeSpecificationString»> «a.name»«ELSE»«a.type.typeSpecification.typeSpecificationString.toFirstUpper» «a.name»«ENDIF»«ENDIF»«ENDFOR»){
				«FOR attr : entity.attributes»
				set«attr.name.toFirstUpper»(«attr.name.toFirstLower»);
				«ENDFOR»
			}
			«/*Se verifica que la entidad no tenga padres para encapsular el 
			 * id e incluir los metodos equals 
			 * y hashCode
			 */ »
			«IF entity.parents.empty»
				/**
				 * Returns the current value for the unique id
				 * @return current instance for id attribute
				 */	
				@Id
				@GeneratedValue(strategy = GenerationType.AUTO)
				public Long getId() {
					return id;
				}
				
				/**
				 * Sets the value for the unique id
				 * @param id The value to set
				 */
				public void setId(final Long id) {
					this.id = id;
				}
				
				/**
				 * Equals ovewritten method for the object
				 */
				@Override
				public boolean equals(Object obj) {
					if (this == obj) {
						return true;
					}
					if (obj == null) {
						return false;
					}
					if (!(obj instanceof «entity.name»)) {
						return false;
					}
					final «entity.name» other = («entity.name») obj;
					if (id == null) {
						if (other.getId() != null) {
							return false;
						}
					} else if (!id.equals(other.getId())) {
						return false;
					}
					return true;
				}
				
				/**
				 * Hashcode overwritten method for the object
				 */
				@Override
				public int hashCode() {
					final int prime = 31;
					int result = 1;
					result = prime * result
							+ ((id == null) ? super.hashCode() : id.hashCode());
					return result;
				}	
			«ENDIF»
			«/*Se recorren los atributos para verificar si existen relaciones 
			 * entre las entidades*/ »
			«FOR Attribute a : entity.attributes»				
				«a.associationAnnotation»
				«/*Se agregan las anotaciones segun los constaint definidos*/ »	
				«FOR constraint : a.constraints»			
						@«constraint.type.typeSpecification.typeSpecificationString.toFirstUpper»«constraint.parametersTemplate»
				«ENDFOR»
				«/*Se verifica el caso especial para los tipos email y se coloca como String en los metodos set y get*/ »
				«IF a.type.typeSpecification.typeSpecificationString.equalsIgnoreCase("email")»
					/**
					 * Returns the current value for the attibute «a.name»
					 *
					 * @return current instance for «a.name.toFirstLower» attribute
					 */			
					@Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
					public String get«a.name.toFirstUpper»(){
						return «a.name»;
					}
					
					/**
					 * Sets the value for the attribute «a.name»
					 * @param «a.name.toFirstLower» The value to set
					 */
					public void set«a.name.toFirstUpper»(String «a.name»){
						this.«a.name»=«a.name»;
					}				
				«ELSE»
					«/*Se agregan los metodos set y get a cada atributo de la entidad, verificando si son listas o tipos primitivos de datos */ »
					«IF a.type.isCollection»
							/**
							 * Returns the current value for the attibute «a.name»
							 *
							 * @return current instance for «a.name.toFirstLower» attribute
							 */
							public «getCollectionString(a.type as ParameterizedType)»<«(a.type as ParameterizedType).typeParameters.get(0).typeSpecification.typeSpecificationString»> get«a.name.toFirstUpper»(){
								return «a.name»;
							}
							
							/**
							 * Sets the value for the attribute «a.name»
							 * @param «a.name.toFirstLower» The value to set
							 */
							public void set«a.name.toFirstUpper»(«getCollectionString(a.type as ParameterizedType)»<«(a.type as ParameterizedType).typeParameters.get(0).typeSpecification.typeSpecificationString»> «a.name»){
								this.«a.name»=«a.name»;
							}
					«ELSE»  
							/**
							 * Returns the current value for the attibute «a.name»
							 *
							 * @return current instance for «a.name.toFirstLower» attribute
							 */
							«IF a.type.typeSpecification instanceof Enum»@Enumerated«ENDIF»
							public «a.type.typeSpecification.typeSpecificationString.toFirstUpper» get«a.name.toFirstUpper»(){
								return «a.name»;
							}
							
							/**
							 * Sets the value for the attribute «a.name»
							 * @param «a.name.toFirstLower» The value to set
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
