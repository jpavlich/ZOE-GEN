package co.edu.javeriana.zoe.generator.persistence

import co.edu.javeriana.isml.generator.common.GeneratorSuite
import co.edu.javeriana.isml.generator.common.OutputConfiguration
import co.edu.javeriana.zoe.generator.persistence.generators.ZoeEntityGenerator
import co.edu.javeriana.zoe.generator.persistence.generators.ZoePersistenceXmlGenerator
import co.edu.javeriana.zoe.generator.persistence.generators.ZoeResourceBundleGenerator
import co.edu.javeriana.zoe.generator.persistence.generators.ZoeServiceGeneralGenerator

/**
 * Generates code from your model files on save.
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#TutorialCodeGeneration
 */
class ZoePersistenceGenerator extends GeneratorSuite {
	
	
	@OutputConfiguration	
	public static final String RESOURCE_BUNDLE="resource.bundle"
	
	@OutputConfiguration
	public static final String ENTITIES = "entities";

	@OutputConfiguration
	public static final String SERVICE_GENERAL = "service.general"

	@OutputConfiguration
	public static final String PERSISTENCE_XML = "persistencexml"
		

	override getGenerators() {
		#{
			new ZoeEntityGenerator,			
			new ZoeResourceBundleGenerator,
			new ZoeServiceGeneralGenerator,
			new ZoePersistenceXmlGenerator
		}
	}

}
