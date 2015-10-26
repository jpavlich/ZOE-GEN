package co.edu.javeriana.zoe.generator.jee7.templates

import co.edu.javeriana.isml.generator.common.SimpleTemplate
import co.edu.javeriana.isml.isml.ActionCall
import co.edu.javeriana.isml.isml.Block
import co.edu.javeriana.isml.isml.Controller
import co.edu.javeriana.isml.isml.Entity
import co.edu.javeriana.isml.isml.For
import co.edu.javeriana.isml.isml.ForView
import co.edu.javeriana.isml.isml.If
import co.edu.javeriana.isml.isml.MethodStatement
import co.edu.javeriana.isml.isml.Parameter
import co.edu.javeriana.isml.isml.ParameterizedType
import co.edu.javeriana.isml.isml.Primitive
import co.edu.javeriana.isml.isml.Return
import co.edu.javeriana.isml.isml.Service
import co.edu.javeriana.isml.isml.Show
import co.edu.javeriana.isml.isml.Type
import co.edu.javeriana.isml.isml.While
import co.edu.javeriana.isml.scoping.TypeExtension
import co.edu.javeriana.isml.validation.TypeChecker
import com.google.inject.Inject
import java.util.ArrayList
import java.util.HashMap
import java.util.Map
import java.util.Map.Entry
import javax.swing.text.html.FormView
import org.eclipse.emf.common.util.EList
import org.eclipse.xtext.naming.IQualifiedNameProvider

class BackingBeanTemplate extends SimpleTemplate<Controller> {

	@Inject extension IQualifiedNameProvider
	@Inject extension TypeExtension
	@Inject extension TypeChecker
	@Inject extension ExpressionTemplate
	@Inject extension StatementTemplate
	@Inject extension ServicesValidator
	var EList<Entity> controllerEntities
	var Entity entityToList
	var Map<String, Type> neededAttributes = new HashMap

	override preprocess(Controller controller) {
		var Map<String, Object> neededData = controller.getNeededAttributes
		controllerEntities = neededData.get("controllerEntities") as EList<Entity>
		entityToList = neededData.get("entityToList") as Entity
		neededAttributes = neededData.get("neededAttributes") as Map<String,Type>
//		if (neededData.containsKey("selectedRegisters")) {
//			neededAttributes.put("selectedRegisters", neededData.get("selectedRegisters")as Type)
//		}
//		p.collection.lastReference.referencedElement.type.writeType()

		val controlledPages = controller.controlledPages
		val forViews = new ArrayList<ForView>()
		for (p : controlledPages) {
			forViews.addAll(p.eAllContents.filter(ForView).toIterable)
			
		}
		
		for (p : forViews) {
			
			val key = p.variable.name 
			val value = p.variable.type
			println(key + "->" + value)
			neededAttributes.put(key , value )
			
		}
		
	}

	// TODO Implement hashCode and equals, based in the unique keys of the entity
	/*	@«constraint.type.typeSpecification.typeSpecificationString»(«FOR Expression ex : constraint.parameters SEPARATOR ","»«ex.toString.length»«ENDFOR»)*/
	override def CharSequence template(
		Controller controller
	) '''
		package «controller.eContainer.fullyQualifiedName»;
				
		import java.io.Serializable;
		import javax.inject.*;		
		import javax.enterprise.context.*;		
		import java.util.*;
		import javax.faces.application.FacesMessage;
		import javax.faces.context.FacesContext;
		
		«FOR attr : neededAttributes.entrySet»
			«IF !(attr.value instanceof ParameterizedType)»
				«IF !attr.value.typeSpecification.eContainer.fullyQualifiedName.equals(controller.eContainer.fullyQualifiedName) && !(attr.value.typeSpecification instanceof Primitive)»			
					import «attr.value.typeSpecification.fullyQualifiedName»;
				«ENDIF» 
			«ELSE»
				«IF !(attr.value as ParameterizedType).typeParameters.get(0).typeSpecification.eContainer.fullyQualifiedName.equals(controller.eContainer.fullyQualifiedName) && !(attr.value.typeSpecification instanceof Primitive)»			
					import «(attr.value as ParameterizedType).typeParameters.get(0).typeSpecification.fullyQualifiedName»;
				«ENDIF»
			«ENDIF»
		«ENDFOR»		
		«FOR entity : getNeededImportsInActions(controller).entrySet»
			import «entity.value.fullyQualifiedName»;
		«ENDFOR»
		«FOR invokedController : getActionCallControllers(controller)»
			«IF !invokedController.eContainer.fullyQualifiedName.equals(controller.eContainer.fullyQualifiedName)»			
				import «invokedController.fullyQualifiedName»;
			«ENDIF» 
		«ENDFOR»
		«FOR service : controller.services»
			import «service.type.typeSpecification.fullyQualifiedName»;
			«IF validateService(service.type.typeSpecification as Service)»import «service.type.typeSpecification.eContainer.fullyQualifiedName».qualifier.«service.type.typeSpecification.typeSpecificationString»Qualifier;«ENDIF»
		«ENDFOR»	
		
		/**
		 * This class represents a controller with name «controller.name.toFirstUpper»
		 */
		@Named
		@ConversationScoped
		public class «controller.name.toFirstUpper» implements Serializable {
			
			/**
			 * The serialVersionUID
			 */
			private static final long serialVersionUID = 1L;
			
			/**
			 *This represents the object to mantain the conversational state
			 */
			@Inject
			private Conversation conversation;
			
			/**
			 * This is an instance from FacesContext object
			 */
			 @Inject
			private FacesContext facesContext;
			
			/**
			 * This is the flag to avoid multiple initializations for the component
			 */
			private boolean flag = false;
			
			«FOR service : controller.services»
				/**
				 * Injection for the component named «service.type.typeSpecification.typeSpecificationString.toFirstUpper» 
				 */
				@Inject
				«IF validateService(service.type.typeSpecification as Service)»@«service.type.typeSpecification.typeSpecificationString.toFirstUpper»Qualifier«ENDIF»
			private «service.type.typeSpecification.typeSpecificationString.toFirstUpper»«IF service.type instanceof ParameterizedType»<«FOR param: (service.type as ParameterizedType).typeParameters SEPARATOR ','»«param.writeType(true)»«ENDFOR»>«ENDIF» «IF service.name != null»«service.name.toFirstLower»«ELSE»«service.
			name.toFirstLower»«ENDIF»; 
			«ENDFOR»
			
			«FOR attr : neededAttributes.entrySet»
			«IF attr.value.isCollection»
				/**
				 * Attribute for the type «getCollectionString(attr.value as ParameterizedType)»<«(attr.value as ParameterizedType).typeParameters.get(0).typeSpecification.typeSpecificationString»>»  
				 */
				private «getCollectionString(attr.value as ParameterizedType)»
				<«(attr.value as ParameterizedType).typeParameters.get(0).typeSpecification.typeSpecificationString»> 
				«attr.key»;
			«ELSE»
				/**
				 * Attribute for the type «attr.value.typeSpecification.typeSpecificationString.toFirstUpper»
				 */
				private «attr.value.typeSpecification.typeSpecificationString.toFirstUpper» «attr.key»«IF !(attr.value.typeSpecification instanceof Primitive)»=new «attr.value.typeSpecification.typeSpecificationString.toFirstUpper»()«ENDIF»;
			«ENDIF»				
			«ENDFOR»
			
			
			
			«FOR invokedController : getActionCallControllers(controller)»
			/**
			 * Instance of the controller named «invokedController.name.toFirstUpper»
			 */
			@Inject
			private «invokedController.name.toFirstUpper» «invokedController.name.toFirstLower»; 
			«ENDFOR»			
			
			/**
		 * Initialization method  for the controller
		 */			
			public void init(){
				if(flag==Boolean.FALSE){
					if(conversation.isTransient()){
						conversation.begin();
					}
					«FOR action : controller.actions»
						«IF action.isDefault»
							«FOR st:action.body.statements»
								«IF !(st instanceof Show)»
									«writeStatement(st as MethodStatement)»
								«ENDIF»
							«ENDFOR»
						«ENDIF»
					«ENDFOR»
					flag=Boolean.TRUE;	
				}			
			}
			«FOR method : controller.actions»
				/**
				 * Action method named «method.name»
				 «FOR param: method.parameters»
				 	* @param «param.name.toFirstLower» Parameter from type «param.type.typeSpecification.name.toFirstUpper»
				 «ENDFOR»
				 *
				 * @return String value with some navigation outcome
				 */
				public String «method.name»(«FOR param : method.parameters SEPARATOR ','»«param.type.typeSpecification.typeSpecificationString.toFirstUpper» «param.name.toFirstLower»«ENDFOR»){
					try{
						«FOR param : method.parameters»
							«IF neededAttributes.containsKey(param.name)»
								if(«param.name»!=null){
									this.set«param.name.toFirstUpper»(«param.name»);
								}
							«ELSE»
								«IF param.obtainAttribute!=null»
									if(«param.name»!=null){
										this.set«param.obtainAttribute.key.toFirstUpper»(«param.name»);
									}
								«ENDIF»
							«ENDIF»
						«ENDFOR»					
						«writeStatements(method.body)»
						«IF method.body.actionRequiresReturnSentence»
							return "";
						«ENDIF»
					}catch (Exception e)	{
						facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"",e.getMessage()));
					} 
					return "";
					
				}
			«ENDFOR»
			
			«FOR attr : neededAttributes.entrySet»
				«IF attr.value.isCollection»
					/**
					 * Returns the current value for the attribute «attr.key»
					 *
					 * @return current instance for «attr.key.toFirstLower» attribute
					 */
					public «getCollectionString(attr.value as ParameterizedType)»<«(attr.value as ParameterizedType).typeParameters.get(0).typeSpecification.typeSpecificationString»> get«attr.key.toFirstUpper»(){						
						return «attr.key.toFirstLower»;
					}
					
					/**
					 * Sets the value for the attribute «attr.key»
					 * @param «attr.key.toFirstLower» The value to set
					 */
					public void set«attr.key.toFirstUpper»(«getCollectionString(attr.value as ParameterizedType)»<«(attr.value as ParameterizedType).typeParameters.get(0).typeSpecification.typeSpecificationString»> «attr.key.toFirstLower»){
						this.«attr.key.toFirstLower»=«attr.key.toFirstLower»;
					}
				«ELSE»
					/**
					 * Returns the current value for the attribute «attr.key»
					 * @return current instance for «attr.key.toFirstLower» attribute
					 */
					public «attr.value.typeSpecification.typeSpecificationString.toFirstUpper» get«attr.key.toFirstUpper»(){
						return «attr.key.toFirstLower»;
					}
					
					/**
					 * Sets the value for the attribute «attr.key»
					 * @param «attr.key.toFirstLower» The value to set
					 */
					public void set«attr.key.toFirstUpper»(«attr.value.typeSpecification.typeSpecificationString.toFirstUpper» «attr.key.toFirstLower»){
						this.«attr.key.toFirstLower»=«attr.key.toFirstLower»;
					}
				«ENDIF»				
			«ENDFOR»			
			
			«FOR service : controller.services»
				/**
				 * Returns the instance for the «IF service.name != null»«service.name»«ELSE»«service.type.typeSpecification.typeSpecificationString»«ENDIF» EJB
				 *
				 * @return current instance for «service.name.toFirstLower» attribute
				 */
			public «service.type.typeSpecification.typeSpecificationString.toFirstUpper»«IF service.type instanceof ParameterizedType»<«FOR param: (service.type as ParameterizedType).typeParameters SEPARATOR ','»«param.writeType(true)»«ENDFOR»>«ENDIF» «IF service.name != null»get«service.name.toFirstUpper»«ELSE»get«service.type.typeSpecification.
			name.toFirstUpper»«ENDIF»(){
				return «IF service.name != null»«service.name.toFirstLower»«ELSE»«service.
			name.toFirstLower»«ENDIF»;
				}
				/**
				 * Sets the value for the «IF service.name != null»«service.name»«ELSE»«service.type.typeSpecification.typeSpecificationString»«ENDIF» EJB
				 * @param «service.name.toFirstLower» The value to set
				 */
			public void «IF service.name != null»set«service.name.toFirstUpper»«ELSE»set«service.
			name.toFirstUpper»«ENDIF»(«service.type.typeSpecification.typeSpecificationString.toFirstUpper»«IF service.type instanceof ParameterizedType»<«FOR param: (service.type as ParameterizedType).typeParameters SEPARATOR ','»«param.writeType(true)»«ENDFOR»>«ENDIF» «IF service.name != null»«service.name.toFirstLower»«ELSE»set«service.
			name.toFirstLower»«ENDIF»){
				this.«IF service.name != null»«service.name.toFirstLower»«ELSE»«service.
			name.toFirstLower»«ENDIF»=«IF service.name != null»«service.name.toFirstLower»«ELSE»«service.
			name.toFirstLower»«ENDIF»;
				} 
			«ENDFOR»
		}
	'''

	def Entry<String, Type> obtainAttribute(Parameter parameter) {
		for (entry : neededAttributes.entrySet) {
			if (entry.value.typeSpecification.typeSpecificationString.equalsIgnoreCase(
				parameter.type.typeSpecification.typeSpecificationString)) {
				return entry
			}
		}
		return null
	}

	def boolean actionRequiresReturnSentence(Block body) {
		var boolean requires = true
		if (body?.statements != null) {
			for (stmnt : body.statements) {
				if (stmnt instanceof Show || stmnt instanceof ActionCall || stmnt instanceof Return) {
					requires = false
				} else if (stmnt instanceof If) {
					requires = actionRequiresReturnSentence(stmnt.body)
					if (stmnt.elseBody != null) {
						requires = actionRequiresReturnSentence(stmnt.elseBody)
					}
				} else if (stmnt instanceof While) {
					requires = actionRequiresReturnSentence(stmnt.body)
				} else if (stmnt instanceof For) {
					requires = actionRequiresReturnSentence(stmnt.body)
				}
			}
		}

		return requires;
	}

}