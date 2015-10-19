package co.edu.javeriana.zoe.generator.persistence.templates

import co.edu.javeriana.isml.generator.common.SimpleTemplate
import co.edu.javeriana.isml.isml.Assignment
import co.edu.javeriana.isml.isml.ResourceBundle
import co.edu.javeriana.isml.isml.ResourceReference
import co.edu.javeriana.isml.isml.StringValue

class ResourceBundleTemplate extends SimpleTemplate<ResourceBundle> {

	

	override preprocess(ResourceBundle resource) {	

	}

	

	// TODO Implement hashCode and equals, based in the unique keys of the entity
	/*	@«constraint.type.typeSpecification.typeSpecificationString»(«FOR Expression ex : constraint.parameters SEPARATOR ","»«ex.toString.length»«ENDFOR»)*/
	override def CharSequence template(ResourceBundle resource) '''
		«FOR stmnt:resource.body.statements»
			«IF stmnt instanceof Assignment»
				«(stmnt.left as ResourceReference).referencedElement.name.substring(1)»=«(stmnt.right as StringValue).literal»
			«ENDIF»
		«ENDFOR»
	'''

}
