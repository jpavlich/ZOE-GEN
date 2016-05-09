package co.edu.javeriana.zoe.generator.jee7.generators

import co.edu.javeriana.zoe.generator.jee7.ZoeJEE7Generator
import co.edu.javeriana.zoe.generator.jee7.templates.ZoeControllerTemplate
import co.edu.javeriana.isml.generator.common.SimpleGenerator
import co.edu.javeriana.isml.isml.Controller
import com.google.inject.Inject
import org.eclipse.xtext.naming.IQualifiedNameProvider

class ZoeControllerGenerator extends SimpleGenerator<Controller> {
	@Inject extension IQualifiedNameProvider

	override getFileName(Controller e) {
		return e.eContainer?.fullyQualifiedName.toString("/").toLowerCase + "/" + e.name + ".java"
	}

	override getOutputConfigurationName() {
		ZoeJEE7Generator.BACKING_BEANS
	}
	
	override getTemplate() {
		return new ZoeControllerTemplate
	}

}
