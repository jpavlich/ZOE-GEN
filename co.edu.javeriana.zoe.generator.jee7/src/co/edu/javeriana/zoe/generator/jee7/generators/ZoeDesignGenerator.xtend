package co.edu.javeriana.zoe.generator.jee7.generators

import co.edu.javeriana.zoe.generator.jee7.ZoeJEE7Generator

import co.edu.javeriana.isml.generator.common.SimpleGenerator
import co.edu.javeriana.isml.isml.Page
import java.util.List
import co.edu.javeriana.zoe.generator.jee7.templates.ZoeDesignTemplate

class ZoeDesignGenerator  extends SimpleGenerator<List<Page>>{

	override protected getFileName(List<Page> is) {		
		return "template.xhtml";
	}
	
	override protected getOutputConfigurationName() {
		ZoeJEE7Generator.PAGES
	}
	
	override getTemplate() {
		return new ZoeDesignTemplate
	}
		
}