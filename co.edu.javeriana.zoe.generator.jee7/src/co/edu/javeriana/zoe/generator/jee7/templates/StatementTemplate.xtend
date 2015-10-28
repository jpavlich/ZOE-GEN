package co.edu.javeriana.zoe.generator.jee7.templates

import co.edu.javeriana.isml.isml.Action
import co.edu.javeriana.isml.isml.Assignment
import co.edu.javeriana.isml.isml.Attribute
import co.edu.javeriana.isml.isml.Controller
import co.edu.javeriana.isml.isml.For
import co.edu.javeriana.isml.isml.If
import co.edu.javeriana.isml.isml.Instance
import co.edu.javeriana.isml.isml.MethodStatement
import co.edu.javeriana.isml.isml.NullValue
import co.edu.javeriana.isml.isml.Reference
import co.edu.javeriana.isml.isml.Return
import co.edu.javeriana.isml.isml.Show
import co.edu.javeriana.isml.isml.Type
import co.edu.javeriana.isml.isml.Variable
import co.edu.javeriana.isml.isml.VariableReference
import co.edu.javeriana.isml.isml.While
import co.edu.javeriana.isml.scoping.IsmlModelNavigation
import com.google.inject.Inject
import java.util.Collection
import java.util.Map
import org.eclipse.emf.ecore.EObject

class StatementTemplate {
	@Inject extension ExpressionTemplate
	@Inject extension IsmlModelNavigation
	@Inject extension ReferenceTemplate

	/**
	 * Este método escribe los Statement contenidos en un Body
	 */
	def CharSequence writeStatements(Block body) '''
		«IF body?.statements != null»		
			«FOR statement : body.statements»
				«writeStatement(statement as MethodStatement)»	
			«ENDFOR»
		«ENDIF»
		
	'''

	def dispatch CharSequence writeStatement(Reference statement) '''
		«writeReference(statement)»;
	'''

	def dispatch CharSequence writeStatement(While statement) '''
		while(«writeExpression(statement.condition)»){
			«writeStatements(statement.body)»
		}
	'''

	def dispatch CharSequence writeStatement(If statement) '''
		if(«writeExpression(statement.condition)»){
			«writeStatements(statement.body)»
		}«IF statement.elseBody != null» else {
				«writeStatements(statement.elseBody)»		
		}«ELSE»
				«IF statement.eContainer.eContainer instanceof Action»
					«IF statement.eContainer instanceof Block && (statement.eContainer as Block).statements.size == 1»
						return "";
					«ENDIF»
				«ENDIF»
		«ENDIF»
	'''

	def dispatch CharSequence writeStatement(
		For statement
	) '''
		for («statement.variable.type.typeSpecification.typeSpecificationString.toFirstUpper» «statement.variable.name.toFirstLower»:«statement.
			collection.referencedElement.name.toFirstLower»){
			«writeStatements(statement.body)»
		}
	'''

	def dispatch CharSequence writeStatement(
		Variable statement
	) '''
		«statement.type.typeSpecification.typeSpecificationString.toFirstUpper» «statement.name.toFirstLower»«IF statement.value != null»=«IF !evaluateToCast(
			statement).empty && (evaluateToCast(statement).get(0) as Boolean)»(«writeType(
			(evaluateToCast(statement).get(1) as Type),true)»)«ENDIF»«writeExpression(statement.value)»«ENDIF»;
		«IF statement.findAncestor(Action)!=null && (statement.findAncestor(Action) as Action).isDefault»
			«IF statement.findAncestor(Controller)!=null && ((statement.findAncestor(Controller) as Controller).neededAttributes.get("neededAttributes") as Map<String,Type>).containsKey(statement.name)&&((statement.findAncestor(Controller) as Controller).neededAttributes.get("neededAttributes") as Map<String,Type>).get(statement.name).typeSpecification.name.equals(statement.type.typeSpecification.name)»
				this.«statement.name»=«statement.name»;
			«ENDIF»
		«ENDIF»
		
	'''

	def dispatch CharSequence writeStatement(Show statement) '''
		«FOR param : (statement.expression as Instance).parameters»
			«IF !(param instanceof NullValue) »
				«writeExpression(param)»;
			«ENDIF»			
		«ENDFOR»		
		return "goTo«(statement.expression as Instance).type.typeSpecification.typeSpecificationString.toFirstUpper»";		
	'''

	def dispatch CharSequence writeStatement(Return statement) '''
		return «IF statement.expression != null»«writeExpression(statement.expression)»«ENDIF»;
	'''

	def dispatch CharSequence writeStatement(
		Assignment statement
	) '''	
		«IF statement.left instanceof VariableReference && (statement.left as VariableReference).tail!=null &&
			(statement.left as VariableReference).tail.referencedElement instanceof Attribute && statement.right != null»
			«writeExpression(statement.left)» ( «IF !evaluateToCast(statement).empty &&
			(evaluateToCast(statement).get(0) as Boolean)»(«writeType((evaluateToCast(statement).get(1) as Type),true)»)«ENDIF»«writeExpression(
			statement.right)»);
		«ELSE»
			«writeExpression(statement.left)» «statement.symbol» «writeExpression(statement.right)»;
		«ENDIF»
	'''

	def isUniqueStatement(EObject obj) {
		val container = obj.eContainer
		val containingFeature = obj.eContainingFeature
		if (containingFeature.upperBound == 1) {
			return true
		} else {
			val list = container.eGet(containingFeature) as Collection<?>
			return list.size() == 1
		}
	}

}
