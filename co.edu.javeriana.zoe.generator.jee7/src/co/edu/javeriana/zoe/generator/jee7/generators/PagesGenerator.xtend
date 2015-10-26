package co.edu.javeriana.zoe.generator.jee7.generators

import co.edu.javeriana.zoe.generator.jee7.ZoeJEE7Generator
import co.edu.javeriana.isml.generator.common.SimpleGenerator
import com.google.inject.Inject
import org.eclipse.xtext.naming.IQualifiedNameProvider
import co.edu.javeriana.isml.isml.Page
import co.edu.javeriana.zoe.generator.jee7.templates.PagesTemplate

class PagesGenerator extends SimpleGenerator<Page> {

	@Inject extension IQualifiedNameProvider

	override getOutputConfigurationName() {
		ZoeJEE7Generator.PAGES
	}

	override protected getFileName(Page e) {
		return e.eContainer?.fullyQualifiedName.toString("/").toLowerCase + "/" + e.name + ".xhtml"
	}

	override getTemplate() {
		return new PagesTemplate

	}

}
