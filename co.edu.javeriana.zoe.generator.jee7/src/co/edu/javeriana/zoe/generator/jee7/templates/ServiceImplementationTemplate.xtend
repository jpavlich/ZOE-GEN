 package co.edu.javeriana.zoe.generator.jee7.templates

import co.edu.javeriana.isml.generator.common.SimpleTemplate
import co.edu.javeriana.isml.isml.Attribute
import co.edu.javeriana.isml.isml.Entity
import co.edu.javeriana.isml.isml.GenericTypeSpecification
import co.edu.javeriana.isml.isml.Method
import co.edu.javeriana.isml.isml.ParameterizedType
import co.edu.javeriana.isml.isml.Primitive
import co.edu.javeriana.isml.isml.Service
import co.edu.javeriana.isml.isml.TypeSpecification
import co.edu.javeriana.isml.scoping.TypeExtension
import co.edu.javeriana.isml.validation.TypeChecker
import com.google.inject.Inject
import java.util.HashMap
import java.util.Map
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.naming.QualifiedName

class ServiceImplementationTemplate extends SimpleTemplate<Service> {

	@Inject extension IQualifiedNameProvider
	@Inject extension TypeExtension
	@Inject extension TypeChecker

	override preprocess(Service service) {
	}
	

	// TODO Implement hashCode and equals, based in the unique keys of the entity
	/*	@«constraint.type.classifier.name»(«FOR Expression ex : constraint.parameters SEPARATOR ","»«ex.toString.length»«ENDFOR»)*/
	override def CharSequence template(Service service) '''
		package «service.eContainer?.fullyQualifiedName.toLowerCase»;				
		
		«FOR entity : getNeededImportsInMethods(service).entrySet»
			import «entity.value.fullyQualifiedName»;
		«ENDFOR»
		«FOR parent : service.superTypes»
			«FOR entity : getNeededImportsInMethods(parent.typeSpecification).entrySet»
				import «entity.value.fullyQualifiedName»;
			«ENDFOR»
		«ENDFOR»
			
		import java.util.*;
		import java.io.Serializable;
		import javax.ejb.Stateless;
		«FOR superType:service.superTypes»
		import «superType.typeSpecification.fullyQualifiedName»Bean;
		«ENDFOR»
		import «service.eContainer.fullyQualifiedName».qualifier.«service.name.toFirstUpper»Qualifier;	
		
		/**
		 * This class represents an EJB named «service.name.toFirstUpper»Bean
		 */
		@Stateless
		@«service.name.toFirstUpper»Qualifier
		public class «service.name.toFirstUpper»Bean«IF !service.genericTypeParameters.isEmpty»<«FOR param:service.genericTypeParameters SEPARATOR ','»«param.name»«ENDFOR»>«ENDIF» «IF !service.superTypes.empty»extends «service.superTypes.get(0).typeSpecification.name.toFirstUpper»«IF service.superTypes.get(0).typeSpecification instanceof Service»Bean«IF service.superTypes.get(0)instanceof ParameterizedType»<«FOR param: (service.superTypes.get(0)as ParameterizedType).typeParameters SEPARATOR ','»«param.writeType(true)»«ENDFOR»>«ENDIF»«ENDIF»«ENDIF» implements «service.name.toFirstUpper»«IF !service.genericTypeParameters.isEmpty»<«FOR param:service.genericTypeParameters SEPARATOR ','»«param.name»«ENDFOR»>«ENDIF»,Serializable{
		
			«FOR gen:service.genericTypeParameters»
				/**
				 * Class Object represented by "«gen.name»" class parameter
				 */
				private Class<«gen.name»> clazz«gen.name»Object;
			«ENDFOR»
			«FOR type : service.superTypes»	
				«FOR attr : type.typeSpecification.features»
					«IF attr instanceof Attribute»
						/**
						 * The attribute «attr.name.toFirstLower»
						 */
						private «attr.type.writeType(true)» «attr.name.toFirstLower»;
					«ENDIF»
				«ENDFOR»
			«ENDFOR»
			
			«FOR attr : service.features»
				«IF attr instanceof Attribute»
					/**
					 * The attribute «attr.name.toFirstLower»
					 */
					private «attr.type.writeType(true)» «attr.name.toFirstLower»;
				«ENDIF»
			«ENDFOR»
			
			«IF !service.genericTypeParameters.isEmpty»
			/**
			 * Service default constructor for Generic types
			 *
			 «FOR gen:service.genericTypeParameters»
			 * @param clazz«gen.name»Object It's the generic clazz for '«gen.name»' generic parameter 
			«ENDFOR»
			 */
			public «service.name.toFirstUpper»Bean(«FOR gen:service.genericTypeParameters SEPARATOR ','»Class<«gen.name»> clazz«gen.name»Object«ENDFOR»){
				«FOR gen:service.genericTypeParameters»				
				this.clazz«gen.name»Object=clazz«gen.name»Object;
				«ENDFOR»
			}
			«ENDIF»
			
			/**
			 * Service default constructor
			 */
			public «service.name.toFirstUpper»Bean(){
				«FOR superType:service.superTypes»
				«IF superType instanceof ParameterizedType»
				super(«FOR param:superType.typeParameters SEPARATOR ','»«param.writeType(true)».class«ENDFOR»);
				«ENDIF»
				«ENDFOR»
			}
			
			«FOR feature : service.features»
				«IF feature instanceof Method»
					/**
					 * This method executes the proper actions for «feature.name»
					 «FOR param:feature.parameters»
					 * @param «param.name.toFirstLower» Parameter from type «param.type.typeSpecification.name.toFirstUpper»
					 «ENDFOR»
					 «IF feature.type!=null && !feature.type.typeSpecification.name.equalsIgnoreCase("Void")»
					 * @return Some «feature.type.writeType(true)» object
					 «ENDIF»
					 */
					@Override
					public «service.getReplacedType(feature.type).writeType(true)» «feature.name»(«FOR parameter : feature.parameters SEPARATOR ','»«parameter.type.
			writeType(true)» «parameter.name.toFirstLower»«ENDFOR»){
						«IF !feature.type.typeSpecification.name.equalsIgnoreCase("Void")»
							return null;
						«ENDIF»
					}
				«ELSE»
					«IF feature instanceof Attribute»
						/**
						 * Returns the current value for the attribute «feature.name»
						 *
						 * @return Some «feature.type.writeType(true)» object
						 */
						@Override						
						public «service.getReplacedType(feature.type).writeType(true)» get«feature.name.toFirstUpper»(){
							return «feature.name.toFirstLower»;
						}
						
						/**
						 * Sets the value for the attribute «feature.name»
						 *
						 * @param «feature.name.toFirstLower» The value to set
						 */
						@Override
						public void set«feature.name.toFirstUpper»(«service.getReplacedType(feature.type).writeType(true)» «feature.name.toFirstLower»){
							this.«feature.name.toFirstLower»=«feature.name.toFirstLower»;
						}
					«ENDIF»
				«ENDIF»
			«ENDFOR»		
		}	
		
			
	'''

	def Map<QualifiedName,TypeSpecification> getNeededImportsInMethods(TypeSpecification service) {
		var Map<QualifiedName,TypeSpecification> imports = new HashMap
		for (feature : service.features) {
			if (!feature.type.isCollection) {
				if (feature.type != null && !feature.type.typeSpecification.eContainer.fullyQualifiedName.equals(
					service.eContainer.fullyQualifiedName)) {
					if (!(feature.type.typeSpecification instanceof Primitive)) {
						if (!(feature.type.typeSpecification instanceof GenericTypeSpecification)){
							if(!imports.containsKey(feature.type.typeSpecification.fullyQualifiedName)){
								imports.put(feature.type.typeSpecification.fullyQualifiedName,feature.type.typeSpecification)					
							}						
						}
					}
				}			
			}else {
				if (feature.type instanceof ParameterizedType) {
					if (feature.type != null && !(feature.type as ParameterizedType).typeParameters.get(0).
						typeSpecification.eContainer.fullyQualifiedName.equals(service.eContainer.fullyQualifiedName)) {
						if (!((feature.type as ParameterizedType).typeParameters.get(0).typeSpecification instanceof Primitive)) {
							if (!((feature.type as ParameterizedType).typeParameters.get(0).typeSpecification instanceof GenericTypeSpecification)){
								if(!imports.containsKey((feature.type as ParameterizedType).typeParameters.get(0).typeSpecification.fullyQualifiedName)){
									imports.put((feature.type as ParameterizedType).typeParameters.get(0).typeSpecification.fullyQualifiedName,
										(feature.type as ParameterizedType).typeParameters.get(0).typeSpecification)
									
								}							
							}
						}
					}
				}
			}
			if (feature instanceof Method) {				
				for (param : feature.parameters) {					
					if (!param.type.isCollection) {
						if (!param.type.typeSpecification.eContainer.fullyQualifiedName.equals(
							service.eContainer.fullyQualifiedName)) {
							if (!(param.type.typeSpecification instanceof Primitive)) {
								if (!(param.type.typeSpecification instanceof GenericTypeSpecification)) {
									if(!imports.containsKey(param.type.typeSpecification.fullyQualifiedName)){
										imports.put(param.type.typeSpecification.fullyQualifiedName,param.type.typeSpecification)							
									}								
								}
							}
						}						
					} else {
						if (param.type instanceof ParameterizedType) {
							if (!(param.type as ParameterizedType).typeParameters.get(0).typeSpecification.eContainer.
								fullyQualifiedName.equals(service.eContainer.fullyQualifiedName)) {
								if (!((param.type as ParameterizedType).typeParameters.get(0).typeSpecification instanceof Primitive)) {
									if (!((param.type as ParameterizedType).typeParameters.get(0).typeSpecification instanceof GenericTypeSpecification)) {
										if(!imports.containsKey((param.type as ParameterizedType).typeParameters.get(0).typeSpecification.fullyQualifiedName)){
											imports.put((param.type as ParameterizedType).typeParameters.get(0).typeSpecification.fullyQualifiedName,
												(param.type as ParameterizedType).typeParameters.get(0).typeSpecification as Entity)											
										}									
									}
								}
							}
						}
					}									
				}
				if(feature.body!=null){
					for(stmnt:feature.body.statements){
						isNeededImportInBody(stmnt.eAllContents.toList,imports,service)
					}				
				}
			}
		}
		return imports
	}
	
	
	
	
}
