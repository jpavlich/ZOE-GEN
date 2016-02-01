package co.edu.javeriana.zoe.generator.jee7

import co.edu.javeriana.isml.generator.common.GeneratorSuite
import co.edu.javeriana.isml.generator.common.OutputConfiguration
import co.edu.javeriana.zoe.generator.jee7.generators.BackingBeanGenerator
import co.edu.javeriana.zoe.generator.jee7.generators.FacesConfigXMLGenerator
import co.edu.javeriana.zoe.generator.jee7.generators.PagesGenerator
import co.edu.javeriana.zoe.generator.jee7.generators.ServiceImplementationGenerator
import co.edu.javeriana.zoe.generator.jee7.generators.ServiceInterfaceGenerator
import co.edu.javeriana.zoe.generator.jee7.generators.ZoeDesignGenerator

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
			new BackingBeanGenerator,
			new PagesGenerator,
			new FacesConfigXMLGenerator,
			new ServiceInterfaceGenerator,
			new ServiceImplementationGenerator,			
			new ZoeDesignGenerator
		}
	}
	
}