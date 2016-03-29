package co.edu.javeriana.zoe.generator.jee7.generators

import co.edu.javeriana.zoe.generator.jee7.ZoeJEE7Generator
import co.edu.javeriana.zoe.generator.jee7.templates.ServiceInterfaceTemplate
import co.edu.javeriana.isml.generator.common.SimpleGenerator
import co.edu.javeriana.isml.isml.Service
import com.google.inject.Inject
import org.eclipse.xtext.naming.IQualifiedNameProvider

class ServiceInterfaceGenerator extends SimpleGenerator<Service> {
	@Inject extension IQualifiedNameProvider

	override getFileName(Service e) {
		return e.eContainer?.fullyQualifiedName.toString("/").toLowerCase + "/"+ "interfaces" +"/" +e.name.toFirstUpper + ".java"
	}

	override getOutputConfigurationName() {
		ZoeJEE7Generator.SERVICE_INTERFACE
	}
	
	override getTemplate() {
		return new ServiceInterfaceTemplate
	}

}
