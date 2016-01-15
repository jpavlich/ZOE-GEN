package co.edu.javeriana.zoe.generator.persistence.generators

import co.edu.javeriana.zoe.generator.persistence.ZoePersistenceGenerator
import co.edu.javeriana.zoe.generator.persistence.templates.ResourceBundleTemplate
import co.edu.javeriana.isml.generator.common.SimpleGenerator
import co.edu.javeriana.isml.isml.ResourceBundle
import com.google.inject.Inject
import org.eclipse.xtext.naming.IQualifiedNameProvider

class ResourceBundleGenerator extends SimpleGenerator<ResourceBundle> {
	@Inject extension IQualifiedNameProvider

	override getFileName(ResourceBundle rs) {
				return rs.eContainer?.fullyQualifiedName.toString("/").toLowerCase + "/" +rs.name + ".properties"
				
					}

	override getOutputConfigurationName() {
		ZoePersistenceGenerator.RESOURCE_BUNDLE
	}
	
	override getTemplate() {
		return new ResourceBundleTemplate
	}

}
