package co.edu.javeriana.zoe.generator.persistence.templates

import co.edu.javeriana.isml.generator.common.SimpleTemplate
import co.edu.javeriana.isml.isml.Enum
import co.edu.javeriana.isml.isml.EnumItem
import co.edu.javeriana.isml.scoping.TypeExtension
import com.google.inject.Inject
import org.eclipse.xtext.naming.IQualifiedNameProvider

class EnumTemplate extends SimpleTemplate<Enum> {

	@Inject extension IQualifiedNameProvider
	@Inject extension TypeExtension
		

	override preprocess(Enum service) {
	}

	

	// TODO Implement hashCode and equals, based in the unique keys of the entity
	/*	@«constraint.type.classifier.name»(«FOR Expression ex : constraint.parameters SEPARATOR ","»«ex.toString.length»«ENDFOR»)*/
	override def CharSequence template(Enum enumeration) '''
		package «enumeration.eContainer?.fullyQualifiedName.toLowerCase»;			
			
		/**
		 * This represents an enumeration Object named «enumeration.name.toFirstUpper»
		 */
		public enum «enumeration.name.toFirstUpper» {
			
			«FOR attr : enumeration.features SEPARATOR ','»
				«IF attr instanceof EnumItem»
					/**
					 * This is the «attr.name.toUpperCase» value for the «enumeration.name.toFirstUpper» enumeration
					 */
					«attr.name.toUpperCase»("«attr.name.toFirstUpper»")
				«ENDIF»
		    «ENDFOR»
			;
			/**
			 * This is the label for each enumeration's value
			 */
			private String enumItemLabel;
			
			/**
			 * Default enumeration Constructor method
			 */
			private «enumeration.name.toFirstUpper»(String enumItemLabel){
				this.setEnumItemLabel(enumItemLabel);
			}
			
			/**
			 * Returns the current value of the enumItemLabel attribute
			 */	
			String getEnumItemLabel(){
				return enumItemLabel;
			}
			
			/**
			 * Sets the value of the enumItemLabel attribute
			 */	
			void setEnumItemLabel(String enumItemLabel){
				this.enumItemLabel=enumItemLabel;
			}
		}
	'''
}
