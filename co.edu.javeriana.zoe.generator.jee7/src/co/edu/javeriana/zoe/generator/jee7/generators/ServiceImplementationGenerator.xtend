package co.edu.javeriana.zoe.generator.jee7.generators

import co.com.heinsohn.lion.generator.jee6.LionJEE6Generator
import co.com.heinsohn.lion.generator.jee6.templates.ServiceImplementationTemplate
import co.edu.javeriana.isml.generator.common.SimpleGenerator
import co.edu.javeriana.isml.isml.Service
import com.google.inject.Inject
import org.eclipse.xtext.naming.IQualifiedNameProvider

class ServiceImplementationGenerator extends SimpleGenerator<Service> {
	@Inject extension IQualifiedNameProvider

	override getFileName(Service e) {
		return e.eContainer?.fullyQualifiedName.toString("/").toLowerCase + "/" +e.name.toFirstUpper +"Bean"+ ".java"
	}

	override getOutputConfigurationName() {
		LionJEE6Generator.SERVICE_IMPL
	}
	
	override getTemplate() {
		return new ServiceImplementationTemplate
	}

}
