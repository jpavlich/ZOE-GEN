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
import co.edu.javeriana.isml.scoping.IsmlModelNavigation
import co.edu.javeriana.isml.validation.TypeChecker
import com.google.inject.Inject
import java.util.HashMap
import java.util.Map
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.naming.QualifiedName

class ServiceInterfaceTemplate extends SimpleTemplate<Service> {

	@Inject extension IQualifiedNameProvider
	@Inject extension IsmlModelNavigation
	@Inject extension TypeChecker

	override preprocess(Service service) {
	}

	

	// TODO Implement hashCode and equals, based in the unique keys of the entity
	/*	@«constraint.type.typeSpecification.typeSpecificationString»(«FOR Expression ex : constraint.parameters SEPARATOR ","»«ex.toString.length»«ENDFOR»)*/
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
		import javax.ejb.Local;
		«FOR superType:service.superTypes»
		import «superType.typeSpecification.fullyQualifiedName»;
		«ENDFOR»
		
		/**
		 * This interface represents the local instance for the «service.name.toFirstUpper»Bean EJB 
		 */
		@Local
		public interface «service.name.toFirstUpper»«IF !service.genericTypeParameters.empty»<«FOR param:service.genericTypeParameters SEPARATOR ','»«param.name»«ENDFOR»>«ENDIF» «IF !service.superTypes.empty»extends «service.superTypes.get(0).typeSpecification.typeSpecificationString.toFirstUpper»«IF service.superTypes.get(0)instanceof ParameterizedType»<«FOR param: (service.superTypes.get(0)as ParameterizedType).typeParameters SEPARATOR ','»«param.writeType(true)»«ENDFOR»>«ENDIF»«ENDIF»{
			
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
					public «service.getReplacedType(feature.type).writeType(true)» «feature.name»(«FOR parameter : feature.parameters SEPARATOR ','»«parameter.type.
			writeType(true)» «parameter.name.toFirstLower»«ENDFOR»);
				«ELSE»
					«IF feature instanceof Attribute»
						/**
						 * Returns the current value for the attribute «feature.name»
						 *
						 * @return Some «feature.type.writeType(true)» object
						 */
						public «service.getReplacedType(feature.type).writeType(true)» get«feature.name.toFirstUpper»();
						
						/**
						 * Sets the value for the attribute «feature.name»
						 *
						 * @param «feature.name.toFirstLower» The value to set
						 */
						public void set«feature.name.toFirstUpper»(«service.getReplacedType(feature.type).writeType(true)» «feature.name.toFirstLower»);
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
					for(stmnt:feature.body){
						isNeededImportInBody(stmnt.eAllContents.toList,imports,service)
					}				
				}
			}
		}
		return imports
	}
}
