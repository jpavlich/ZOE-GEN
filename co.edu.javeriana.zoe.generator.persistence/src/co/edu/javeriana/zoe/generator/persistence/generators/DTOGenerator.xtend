package co.edu.javeriana.zoe.generator.persistence.generators

import co.edu.javeriana.zoe.generator.persistence.ZoePersistenceGenerator
import co.edu.javeriana.zoe.generator.persistence.templates.DTOTemplate
import co.edu.javeriana.isml.generator.common.SimpleGenerator
import co.edu.javeriana.isml.isml.DTO
import com.google.inject.Inject
import org.eclipse.xtext.naming.IQualifiedNameProvider

class DTOGenerator extends SimpleGenerator<DTO> {
	@Inject extension IQualifiedNameProvider

	override getFileName(DTO e) {
		return e.eContainer?.fullyQualifiedName.toString("/").toLowerCase + "/" + e.name + ".java"
	}

	override getOutputConfigurationName() {
		ZoePersistenceGenerator.DTOS
	}
	
	override getTemplate() {
		return new DTOTemplate

	}

}