package co.edu.javeriana.zoe.generator.jee7.generators

import co.edu.javeriana.zoe.generator.jee7.ZoeJEE7Generator
import co.edu.javeriana.zoe.generator.jee7.templates.FacesConfigXMLTemplate
import co.edu.javeriana.isml.generator.common.SimpleGenerator
import co.edu.javeriana.isml.isml.Page
import java.util.List

class FacesConfigXMLGenerator  extends SimpleGenerator<List<Page>>{

	override protected getFileName(List<Page> is) {		
		return "WEB-INF/faces-config.xml";
	}
	
	override protected getOutputConfigurationName() {
		ZoeJEE7Generator.PAGES
	}
	
	override getTemplate() {
		return new FacesConfigXMLTemplate
	}
		
}