package co.edu.javeriana.zoe.generator.jee7.generators

import co.com.heinsohn.lion.generator.jee6.LionJEE6Generator
import co.com.heinsohn.lion.generator.jee6.templates.BackingBeanTemplate
import co.edu.javeriana.isml.generator.common.SimpleGenerator
import co.edu.javeriana.isml.isml.Controller
import com.google.inject.Inject
import org.eclipse.xtext.naming.IQualifiedNameProvider

class BackingBeanGenerator extends SimpleGenerator<Controller> {
	@Inject extension IQualifiedNameProvider

	override getFileName(Controller e) {
		return e.eContainer?.fullyQualifiedName.toString("/").toLowerCase + "/" + e.name + ".java"
	}

	override getOutputConfigurationName() {
		LionJEE6Generator.BACKING_BEANS
	}
	
	override getTemplate() {
		return new BackingBeanTemplate
	}

}
