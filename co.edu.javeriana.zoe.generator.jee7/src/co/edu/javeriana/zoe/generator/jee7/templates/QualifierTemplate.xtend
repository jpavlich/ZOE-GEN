 package co.edu.javeriana.zoe.generator.jee7.templates

import co.edu.javeriana.isml.generator.common.SimpleTemplate
import co.edu.javeriana.isml.isml.Service
import com.google.inject.Inject
import org.eclipse.xtext.naming.IQualifiedNameProvider

class QualifierTemplate extends SimpleTemplate<Service> {

	@Inject extension IQualifiedNameProvider

	override preprocess(Service service) {
	}
	

	// TODO Implement hashCode and equals, based in the unique keys of the entity
	/*	@«constraint.type.classifier.name»(«FOR Expression ex : constraint.parameters SEPARATOR ","»«ex.toString.length»«ENDFOR»)*/
	override def CharSequence template(Service service) '''
		package «service.eContainer?.fullyQualifiedName.toLowerCase».qualifier;
		
		import static java.lang.annotation.ElementType.FIELD;
		import static java.lang.annotation.ElementType.METHOD;
		import static java.lang.annotation.ElementType.PARAMETER;
		import static java.lang.annotation.ElementType.TYPE;
		import static java.lang.annotation.RetentionPolicy.RUNTIME;
		
		import java.lang.annotation.Retention;
		import java.lang.annotation.Target;
		
		import javax.inject.Qualifier;			
		
		/**
		 * This anotation represents a Qualifier to obtain the correct instance for the «service.name.toFirstUpper»Bean component
		 */ 
		@Qualifier
		@Retention(RUNTIME)
		@Target({TYPE, METHOD, FIELD, PARAMETER})		
		public @interface «service.name.toFirstUpper»Qualifier {			
			
		}
	'''
}
