package co.edu.javeriana.zoe.generator.jee7.templates

import co.edu.javeriana.isml.generator.common.SimpleTemplate
import co.edu.javeriana.isml.isml.ActionCall
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
import co.edu.javeriana.isml.scoping.IsmlModelNavigation
import co.edu.javeriana.isml.validation.TypeChecker
import com.google.inject.Inject
import java.util.ArrayList
import java.util.HashMap
import java.util.Map
import java.util.Map.Entry
import org.eclipse.emf.common.util.EList
import org.eclipse.xtext.naming.IQualifiedNameProvider
import co.edu.javeriana.isml.isml.Statement

class ZoeControllerTemplate extends SimpleTemplate<Controller> {

	@Inject extension IQualifiedNameProvider
	@Inject extension IsmlModelNavigation
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
		neededAttributes = neededData.get("neededAttributes") as Map<String, Type>
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
			neededAttributes.put(key, value)

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
		import «controller.eContainer.fullyQualifiedName».util.JsfUtil;
		import javax.enterprise.context.*;		
		import java.util.*;
		import javax.faces.application.FacesMessage;
		import javax.faces.context.FacesContext;
		import javax.faces.bean.ManagedBean;
		import javax.annotation.PostConstruct;
		import javax.ejb.EJB;
		import org.primefaces.model.map.MapModel;
		import org.primefaces.model.map.DefaultMapModel;
		import org.primefaces.model.map.LatLng;
		import org.primefaces.model.map.Marker;
		import co.edu.javeriana.sesion.Query;
		import co.edu.javeriana.sesion.Convert;
		

		
		
		
		«/* Se importan los controladores necesarios */»
	    «FOR invokedController : getActionCallControllers(controller)»
			«IF !invokedController.eContainer.fullyQualifiedName.equals(controller.eContainer.fullyQualifiedName)»			
				import «invokedController.fullyQualifiedName»;
			«ENDIF» 
		«ENDFOR»
		«/* Se importan los package de los atributos necesarios */»
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
		
			«/* Se importan los package las entidades */»
		«FOR entity : getNeededImportsInActions(controller).entrySet»
			
			
			«IF  entity.value.typeSpecificationString != "Query" && entity.value.typeSpecificationString != "Convert"»
			import «entity.value.eContainer.fullyQualifiedName».services.«entity.value.name»__General__;
			import «entity.value.eContainer.fullyQualifiedName».*;
				«ENDIF»
		«ENDFOR»
		«/* Se importan los package de los servicios necesarios */»
			«FOR service : controller.services»
			    «IF  service.type.typeSpecification.typeSpecificationString != "Persistence"»
			    	«IF  service.type.typeSpecification.typeSpecificationString != "Query"  && service.type.typeSpecification.typeSpecificationString  != "Convert"»
						import «controller.eContainer.fullyQualifiedName».interfaces.«service.type.typeSpecification.typeSpecificationString.toFirstUpper»;
					«ENDIF»
				«ENDIF»
		«ENDFOR»	
		
	
		
		/**
		 * This class represents a controller with name «controller.name.toFirstUpper»
		 */
		 «/* Se declara el controlador con scope y anotaciones */»
		@ManagedBean(name = "«controller.name.toFirstLower»")
		@RequestScoped
		«««»@ConversationScoped
		public class «controller.name.toFirstUpper» implements Serializable {
			
			/**
			 * The serialVersionUID
			 */
			private static final long serialVersionUID = 1L;
			
			«««»/**
			««««» *This represents the object to mantain the conversational state
			««««»» */
			««««»»@Inject
			«««««private Conversati	n conversation;
			
		««««»	/**
		««««»»	 * This is an instance from FacesContext object
		«««»»	 */
		««««»	 @Inject
		«««»»	private Face	Contex	 facesContext;
			
		
			
			«/* Se inyectan los servicios en el controlador */»
			«FOR service : controller.services»
				/**
				 * Injection for the component named «service.type.typeSpecification.typeSpecificationString.toFirstUpper» 
				 */
				@EJB
			«««»«IF validateService(service.type.typeSpecification as Service)»@«service.type.typeSpecification.typeSpecificationString.toFirstUpper»Qualifier«ENDIF»
			private «IF service.type.typeSpecification.typeSpecificationString == "Persistence"» «FOR param: (service.type as ParameterizedType).typeParameters SEPARATOR ','»«param.writeType(true)»«ENDFOR»«'__General__'»«ELSE» «service.type.typeSpecification.typeSpecificationString.toFirstUpper»«ENDIF»«IF service.type instanceof ParameterizedType»«ENDIF» «IF service.name != null»«service.name.toFirstLower»«ELSE»«service.name.toFirstLower»«ENDIF»;
			
			«««private «FOR param: (service.type as ParameterizedType).typeParameters SEPARATOR ','»«param.writeType(true)»«ENDFOR»General «IF service.name != null»«service.name.toFirstLower»«ELSE»«service.
			«««name.toFirstLower»«ENDIF»; 
			«ENDFOR»
		    «/* Se declaran los atributos*/»
			«FOR attr : neededAttributes.entrySet»
			«IF attr.value.isCollection»
			
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
			
			
				«/* Se inyectan controladores por defecto*/»
			«FOR invokedController : getActionCallControllers(controller)»
			/**
			 * Instance of the controller named «invokedController.name.toFirstUpper»
			 */
			@Inject
			private «invokedController.name.toFirstUpper» «invokedController.name.toFirstLower»; 
			«ENDFOR»			
			
		«/* Se crea un metodo init con los metodos o servicios declarados como default*/»			
		 @PostConstruct
		 public void init() {
		 	listAll();
					«««»«FOR action : controller.actions»
					«««»	«IF action.isDefault»
						«««»	«FOR st:action.body»
						«««»		«IF !(st instanceof Show)»
						«««»			«writeStatement(st as MethodStatement)»
						«««»		«ENDIF»
					«««»		«ENDFOR»
				«««»		«ENDIF»
				«««»    «ENDFOR»
				    
		
				    
		  }
			
		«/* Se declaran los metodos relacionados como acciones del controlador*/»
			«FOR method : controller.actions»
				/**
				 * Action method named «method.name»
				 «FOR param: method.parameters»
				 	* @param «param.name.toFirstLower» Parameter from type «param.type.typeSpecification.name.toFirstUpper»
				 «ENDFOR»
				 *
				 * @return String value with some navigation outcome
				 */
				«««public String «method.name»(«FOR param : method.parameters SEPARATOR ','»«param.type.typeSpecification.typeSpecificationString.toFirstUpper» «param.name.toFirstLower»«ENDFOR»){getCollectionString(param.value as ParameterizedType)

				public String «method.name»(«FOR param : method.parameters SEPARATOR ','»«IF param.type.collection» List  «param.name.toFirstLower»«ELSE» «param.type.typeSpecification.typeSpecificationString.toFirstUpper» «param.name.toFirstLower»«ENDIF»«ENDFOR»){

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
						JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DietaCreated"));
						
					} 
					return "";
					
				}
			«ENDFOR»
			
			«/* Se los metodos set y get para los atributos declarados respectivamente*/»
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
			public «IF service.type.typeSpecification.typeSpecificationString == "Persistence"» «FOR param: (service.type as ParameterizedType).typeParameters SEPARATOR ','»«param.writeType(true)»«ENDFOR»«'__General__'»«ELSE» «service.type.typeSpecification.typeSpecificationString.toFirstUpper»«ENDIF» «IF service.name != null»get«service.name.toFirstUpper»«ELSE»get«service.type.typeSpecification.
			name.toFirstUpper»«ENDIF»(){
				return «IF service.name != null»«service.name.toFirstLower»«ELSE»«service.
			name.toFirstLower»«ENDIF»;
				}
				/**
				 * Sets the value for the «IF service.name != null»«service.name»«ELSE»«service.type.typeSpecification.typeSpecificationString»«ENDIF» EJB
				 * @param «service.name.toFirstLower» The value to set
				 */
			public void «IF service.name != null»set«service.name.toFirstUpper»«ELSE»set«service.
			name.toFirstUpper»«ENDIF»(«IF service.type.typeSpecification.typeSpecificationString == "Persistence"» «FOR param: (service.type as ParameterizedType).typeParameters SEPARATOR ','»«param.writeType(true)»«ENDFOR»«'__General__'»«ELSE» «service.type.typeSpecification.typeSpecificationString.toFirstUpper»«ENDIF»  «IF service.name != null»«service.name.toFirstLower»«ELSE»set«service.
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

	def boolean actionRequiresReturnSentence(EList<?> body) {
		var boolean requires = true
		if (body != null) {
			for (stmnt : body) {
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
