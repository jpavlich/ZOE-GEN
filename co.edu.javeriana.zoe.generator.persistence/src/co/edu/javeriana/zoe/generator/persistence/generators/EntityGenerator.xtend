package co.edu.javeriana.zoe.generator.persistence.generators

import co.edu.javeriana.zoe.generator.persistence.ZoePersistenceGenerator
import co.edu.javeriana.zoe.generator.persistence.templates.EntityTemplate
import co.edu.javeriana.isml.generator.common.SimpleGenerator
import co.edu.javeriana.isml.isml.Entity
import com.google.inject.Inject
import org.eclipse.xtext.naming.IQualifiedNameProvider

class EntityGenerator extends SimpleGenerator<Entity> {
	@Inject extension IQualifiedNameProvider

	override getFileName(Entity e) {
		return e.eContainer?.fullyQualifiedName.toString("/").toLowerCase + "/" + e.name + ".java"
	}

	override getOutputConfigurationName() {
		ZoePersistenceGenerator.ENTITIES
	}
	
	override getTemplate() {
		return new EntityTemplate

	}

}
