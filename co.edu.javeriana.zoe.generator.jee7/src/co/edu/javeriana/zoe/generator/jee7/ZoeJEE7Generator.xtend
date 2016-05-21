package co.edu.javeriana.zoe.generator.jee7

import co.edu.javeriana.isml.generator.common.GeneratorSuite
import co.edu.javeriana.isml.generator.common.OutputConfiguration
import co.edu.javeriana.zoe.generator.jee7.generators.ZoeControllerGenerator
import co.edu.javeriana.zoe.generator.jee7.generators.ZoeDesignGenerator
import co.edu.javeriana.zoe.generator.jee7.generators.ZoeFacesConfigXMLGenerator
import co.edu.javeriana.zoe.generator.jee7.generators.ZoePagesGenerator
import co.edu.javeriana.zoe.generator.jee7.generators.ZoeServiceImplementationGenerator
import co.edu.javeriana.zoe.generator.jee7.generators.ZoeServiceInterfaceGenerator

class ZoeJEE7Generator extends GeneratorSuite{
	
    @OutputConfiguration
	public static final String PAGES = "pages";

	@OutputConfiguration
	public static final String BACKING_BEANS = "backing.beans"

	@OutputConfiguration
	public static final String SERVICE_INTERFACE = "service.interface"
	
	@OutputConfiguration
	public static final String SERVICE_IMPL = "service.impl"
		

	override getGenerators() {
		#{			
			new ZoeControllerGenerator,
			new ZoePagesGenerator,
			new ZoeFacesConfigXMLGenerator,
			new ZoeServiceInterfaceGenerator,
			new ZoeServiceImplementationGenerator,			
			new ZoeDesignGenerator
		}
	}
	
}