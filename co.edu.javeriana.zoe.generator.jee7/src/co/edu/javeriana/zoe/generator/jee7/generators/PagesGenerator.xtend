package co.edu.javeriana.zoe.generator.jee7.generators

import co.com.heinsohn.lion.generator.jee6.LionJEE6Generator
import co.edu.javeriana.isml.generator.common.SimpleGenerator
import com.google.inject.Inject
import org.eclipse.xtext.naming.IQualifiedNameProvider
import co.edu.javeriana.isml.isml.Page
import co.com.heinsohn.lion.generator.jee6.templates.PagesTemplate

class PagesGenerator extends SimpleGenerator<Page> {

	@Inject extension IQualifiedNameProvider

	override getOutputConfigurationName() {
		LionJEE6Generator.PAGES
	}

	override protected getFileName(Page e) {
		return e.eContainer?.fullyQualifiedName.toString("/").toLowerCase + "/" + e.name + ".xhtml"
	}

	override getTemplate() {
		return new PagesTemplate

	}

}
