package co.edu.javeriana.zoe.generator.persistence.generators

import co.edu.javeriana.zoe.generator.persistence.ZoePersistenceGenerator
import co.edu.javeriana.zoe.generator.persistence.templates.EnumTemplate
import co.edu.javeriana.isml.generator.common.SimpleGenerator
import co.edu.javeriana.isml.isml.Enum
import com.google.inject.Inject
import org.eclipse.xtext.naming.IQualifiedNameProvider

class EnumGenerator extends SimpleGenerator<Enum> {
	@Inject extension IQualifiedNameProvider

	override getFileName(Enum e) {
		return e.eContainer?.fullyQualifiedName.toString("/").toLowerCase + "/" +e.name.toFirstUpper + ".java"
	}

	override getOutputConfigurationName() {
		ZoePersistenceGenerator.ENUM
	}
	
	override getTemplate() {
		return new EnumTemplate
	}

}
