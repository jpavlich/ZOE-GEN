package co.edu.javeriana.zoe.generator.persistence.generators

import co.edu.javeriana.isml.generator.common.SimpleGenerator
import co.edu.javeriana.isml.isml.Service
import co.edu.javeriana.zoe.generator.persistence.ZoePersistenceGenerator
import com.google.inject.Inject
import org.eclipse.xtext.naming.IQualifiedNameProvider
import co.edu.javeriana.isml.isml.Entity
import co.edu.javeriana.zoe.generator.persistence.templates.ServiceGeneralTemplate

class ServiceGeneralGenerator extends SimpleGenerator<Entity> {
	
	@Inject extension IQualifiedNameProvider

	override getOutputConfigurationName() {
		ZoePersistenceGenerator.SERVICE_GENERAL
	}

	override protected getFileName(Entity e) {
		return e.eContainer?.fullyQualifiedName.toString("/").toLowerCase + "/" + e.name +"General"+ ".java"
	}

	override getTemplate() {
		return new ServiceGeneralTemplate

	}
	
}