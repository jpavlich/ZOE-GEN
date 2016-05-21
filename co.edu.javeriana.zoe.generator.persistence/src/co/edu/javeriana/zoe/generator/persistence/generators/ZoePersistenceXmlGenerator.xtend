package co.edu.javeriana.zoe.generator.persistence.generators

import co.edu.javeriana.isml.generator.common.SimpleGenerator
import co.edu.javeriana.isml.isml.Entity
import co.edu.javeriana.zoe.generator.persistence.ZoePersistenceGenerator
import co.edu.javeriana.zoe.generator.persistence.templates.ZoePersistenceXmlTemplate
import java.util.List

class ZoePersistenceXmlGenerator  extends SimpleGenerator<List<Entity>>{

	override protected getFileName(List<Entity> is) {		
		return "persistence.xml";
	}
	
	override protected getOutputConfigurationName() {
		ZoePersistenceGenerator.PERSISTENCE_XML
	}
	
	override getTemplate() {
		return new ZoePersistenceXmlTemplate
	}
		
}