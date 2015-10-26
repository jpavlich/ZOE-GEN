package co.edu.javeriana.zoe.generator.jee7.generators

import co.com.heinsohn.lion.generator.jee6.LionJEE6Generator
import co.com.heinsohn.lion.generator.jee6.templates.FacesConfigXMLTemplate
import co.edu.javeriana.isml.generator.common.SimpleGenerator
import co.edu.javeriana.isml.isml.Page
import java.util.List

class FacesConfigXMLGenerator  extends SimpleGenerator<List<Page>>{

	override protected getFileName(List<Page> is) {		
		return "WEB-INF/faces-config.xml";
	}
	
	override protected getOutputConfigurationName() {
		LionJEE6Generator.PAGES
	}
	
	override getTemplate() {
		return new FacesConfigXMLTemplate
	}
		
}