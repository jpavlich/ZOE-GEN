package co.edu.javeriana.zoe.generator.persistence.generators

import co.edu.javeriana.isml.generator.common.SimpleGenerator
import co.edu.javeriana.isml.isml.Entity
import co.edu.javeriana.zoe.generator.persistence.ZoePersistenceGenerator
import co.edu.javeriana.zoe.generator.persistence.templates.ZoeServiceGeneralTemplate
import com.google.inject.Inject
import org.eclipse.xtext.naming.IQualifiedNameProvider

class ZoeServiceGeneralGenerator extends SimpleGenerator<Entity> {
	
	@Inject extension IQualifiedNameProvider

	override getOutputConfigurationName() {
		ZoePersistenceGenerator.SERVICE_GENERAL
	}

	override protected getFileName(Entity e) {
		return e.eContainer?.fullyQualifiedName.toString("/").toLowerCase + "/"+ "services" +"/" + e.name +"__General__"+ ".java"
	}

	override getTemplate() {
		return new ZoeServiceGeneralTemplate

	}
	
}