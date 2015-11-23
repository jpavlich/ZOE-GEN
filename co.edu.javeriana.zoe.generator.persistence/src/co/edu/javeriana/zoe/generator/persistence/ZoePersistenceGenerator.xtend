package co.edu.javeriana.zoe.generator.persistence

import co.edu.javeriana.zoe.generator.persistence.generators.DTOGenerator
import co.edu.javeriana.zoe.generator.persistence.generators.EntityGenerator
import co.edu.javeriana.zoe.generator.persistence.generators.EnumGenerator
import co.edu.javeriana.zoe.generator.persistence.generators.ResourceBundleGenerator
import co.edu.javeriana.isml.generator.common.GeneratorSuite
import co.edu.javeriana.isml.generator.common.OutputConfiguration
import co.edu.javeriana.zoe.generator.persistence.generators.ServiceGeneralGenerator

/**
 * Generates code from your model files on save.
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#TutorialCodeGeneration
 */
class ZoePersistenceGenerator extends GeneratorSuite {
	@OutputConfiguration
	public static final String ENTITIES = "entities";
	
	@OutputConfiguration	
	public static final String RESOURCE_BUNDLE="resource.bundle"
	
	@OutputConfiguration
	public static final String ENUM="enum"	
	
	@OutputConfiguration
	public static final String DTOS="dto"
	
	@OutputConfiguration
	public static final String SERVICE_GENERAL = "service.general"
	

	override getGenerators() {
		#{
			new EntityGenerator,			
			new ResourceBundleGenerator,
			new EnumGenerator,			
			new DTOGenerator,
			new ServiceGeneralGenerator
		}
	}

}
