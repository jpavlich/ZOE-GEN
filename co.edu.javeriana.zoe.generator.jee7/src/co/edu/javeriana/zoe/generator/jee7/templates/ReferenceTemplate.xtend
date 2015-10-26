 package co.edu.javeriana.zoe.generator.jee7.templates

import co.edu.javeriana.isml.isml.Action
import co.edu.javeriana.isml.isml.ActionCall
import co.edu.javeriana.isml.isml.Assignment
import co.edu.javeriana.isml.isml.Attribute
import co.edu.javeriana.isml.isml.Controller
import co.edu.javeriana.isml.isml.Entity
import co.edu.javeriana.isml.isml.EnumItem
import co.edu.javeriana.isml.isml.MethodCall
import co.edu.javeriana.isml.isml.Parameter
import co.edu.javeriana.isml.isml.ParameterizedType
import co.edu.javeriana.isml.isml.Reference
import co.edu.javeriana.isml.isml.ResourceReference
import co.edu.javeriana.isml.isml.Show
import co.edu.javeriana.isml.isml.Type
import co.edu.javeriana.isml.isml.TypeSpecification
import co.edu.javeriana.isml.isml.VariableReference
import co.edu.javeriana.isml.isml.ViewInstance
import co.edu.javeriana.isml.scoping.TypeExtension
import java.util.Map
import javax.inject.Inject
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import co.edu.javeriana.isml.isml.GenericTypeSpecification

class ReferenceTemplate {
	@Inject extension TypeExtension
	@Inject extension ExpressionTemplate

	def dispatch CharSequence writeReference(MethodCall reference) '''		
		«reference.method.name»(«FOR parameter : reference.parameters SEPARATOR ','»«writeExpression(parameter)»«ENDFOR»)'''



	def dispatch CharSequence writeReference(ActionCall reference) '''		
		«IF reference.findAncestor(Controller) != null
			»return «ENDIF»«IF !(reference.action.eContainer as Controller).name.equals((reference.findAncestor(Controller)as Controller).name)»«(reference.action.eContainer as Controller).name.toFirstLower».«ENDIF»«reference.
			action.name»(«FOR parameter : validateParameterForActionCall(reference) SEPARATOR ','»«writeExpression(parameter)»«ENDFOR»)'''

	def dispatch CharSequence writeReference(ResourceReference reference) '''
		"#{messages['«reference.referencedElement.name.substring(1)»']}"'''

	def ViewInstance getViewInstanceIfExists(EObject object){
		var EObject returnObject=null
		var tmpObject=object
		while(tmpObject!=null && returnObject==null){
			if(tmpObject.eContainer!=null && tmpObject.eContainer instanceof ViewInstance && tmpObject.eContainer.eContainer!=null && !(tmpObject.eContainer.eContainer instanceof Show)){
				returnObject=tmpObject.eContainer
			}
			tmpObject=tmpObject.eContainer
		}
		return returnObject as ViewInstance
	}

	def dispatch CharSequence writeReference(VariableReference reference) {
		var str = reference.referencedElement.name
		var show = reference.eContainer.eContainer;
		if (reference.referencedElement instanceof Attribute) {
			var Attribute attr = reference.referencedElement as Attribute
			if (attr.eContainer instanceof TypeSpecification && getViewInstanceIfExists(reference)==null) {
				str = "get" + reference.referencedElement.name.toFirstUpper + "()"
			} 
			if (reference.eContainer.eContainer instanceof Assignment) {
				var Assignment assignment = reference.eContainer.eContainer as Assignment
				if ((assignment.left as VariableReference).referencedElement.name.equals(
					(reference.eContainer as VariableReference).referencedElement.name)) {
					str = "set" + reference.referencedElement.name.toFirstUpper
				} else if ((assignment.right as VariableReference).referencedElement.name.equals(
					(reference.eContainer as VariableReference).referencedElement.name)) {
					str = "get" + reference.referencedElement.name.toFirstUpper + "()"
				}

			}
		} else if(reference.referencedElement instanceof EnumItem){
			str=reference.referencedElement.name.toUpperCase
		}
		if (show instanceof Show) {
			var Parameter param = null
			for (methodParam : (show.expression as ViewInstance).type.typeSpecification.parameters) {
				if (reference.referencedElement.type.typeSpecification.typeSpecificationString.equals(
					methodParam.type.typeSpecification.typeSpecificationString)||(reference.tail!=null)) {
					if (evaluateReference(reference)) {
						param = methodParam
					}
					if(param == null){
						if(reference.tail!=null){							
							param=methodParam							
						}
					}					
				}
			}
			if (param != null) {
				var String cast=""
				var retData=evaluateToCastShowParameter(param,reference)
				if(retData.get(0)as Boolean){
					cast="("+retData.get(1).toString+")"
				}
				str = "this." + param.name + "="+cast+ str
			} else {
				return ""
			}

		}

		var r = reference.tail
		while (r != null) {
			str += "." + writeReference(r)
			r = r.tail
		}
		return str
	}	

	def dispatch CharSequence writeReference(Type reference){
		var String text
		if(reference.referencedElement instanceof Entity && reference.tail==null){
			text=reference.referencedElement.name.toFirstUpper+".class"
		} else{
			text=reference.referencedElement.name.toFirstUpper
			if (reference.tail!=null){
				text+="."+writeReference(reference.tail)
			}
		}
		
		return text
	}		
	
	
	def evaluateReference(Reference parameter) {
		var pass = true
		if (parameter.referencedElement instanceof Parameter) {
			if (getActionIfExists(parameter)!=null) {
				var controller = parameter.eContainer.controllerIfExists
				var attributesMap = controller.neededAttributes.get("neededAttributes") as Map<String,Type>
				if (attributesMap.containsKey((parameter.referencedElement as Parameter).name) && parameter.tail == null ) {
					pass = false
				}
			}
		}
		return pass
	}
	
	def getActionIfExists(EObject o){
		var Action a=null
		var EObject tmp=o
		while(tmp != null && a==null){
			if(tmp.eContainer!=null && tmp.eContainer instanceof Action){
				a=tmp.eContainer as Action
			}
			tmp=tmp.eContainer
		}
		return a
	}
	
	def EList<Object> evaluateToCastShowParameter(Parameter param,Reference reference){
		var doCast=false
		var neededAttributes=getNeededAttributes(reference.findAncestor(Controller)as Controller).get("neededAttributes")as Map<String,Type>
		var tailType=reference.tailType
		var EList<Object> returnData=new BasicEList
		var castText=""
		doCast=!tailType.isDescendentOf(param.type.typeSpecification)&&!tailType.typeSpecification.typeSpecificationString.equalsIgnoreCase((neededAttributes).get(param.name).typeSpecification.typeSpecificationString)
		if(doCast){
			if(tailType instanceof ParameterizedType && neededAttributes.get(param.name) instanceof ParameterizedType){
				doCast=!(tailType as ParameterizedType).typeParameters.get(0).typeSpecification.typeSpecificationString.equalsIgnoreCase((neededAttributes.get(param.name)as ParameterizedType).typeParameters.get(0).typeSpecification.typeSpecificationString)
				if(doCast){
					castText=tailType.writeType(false);
				}
			}
		}
		if(doCast && castText.equals("")){
			if(tailType.typeSpecification instanceof GenericTypeSpecification){
				doCast=false;
			}else{				
				castText=tailType.writeType(true)	
			}
			
		}		
		returnData.add(doCast)
		returnData.add(castText)
		return returnData
	}
	
	def boolean isDescendentOf(Type type,TypeSpecification ts){
		for(superType:type.typeSpecification.superTypes){
			if(superType.typeSpecification.name.equalsIgnoreCase(ts.name)){
				return true;
			}
		}
		return false;
	}

}
