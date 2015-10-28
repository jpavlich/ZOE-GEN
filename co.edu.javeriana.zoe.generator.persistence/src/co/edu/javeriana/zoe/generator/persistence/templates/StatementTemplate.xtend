package co.edu.javeriana.zoe.generator.persistence.templates

import co.edu.javeriana.isml.isml.Action
import co.edu.javeriana.isml.isml.Assignment
import co.edu.javeriana.isml.isml.Attribute
import co.edu.javeriana.isml.isml.Controller
import co.edu.javeriana.isml.isml.For
import co.edu.javeriana.isml.isml.If
import co.edu.javeriana.isml.isml.Instance
import co.edu.javeriana.isml.isml.MethodStatement
import co.edu.javeriana.isml.isml.Reference
import co.edu.javeriana.isml.isml.Return
import co.edu.javeriana.isml.isml.Show
import co.edu.javeriana.isml.isml.Type
import co.edu.javeriana.isml.isml.Variable
import co.edu.javeriana.isml.isml.VariableReference
import co.edu.javeriana.isml.isml.While
import co.edu.javeriana.isml.scoping.IsmlModelNavigation
import com.google.inject.Inject
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.mwe2.language.scoping.QualifiedNameProvider
import co.edu.javeriana.isml.isml.Statement
import co.edu.javeriana.isml.isml.CompositeElement
import org.eclipse.emf.ecore.EObject
import java.util.Collection

class StatementTemplate {
	@Inject extension ExpressionTemplate
	@Inject extension IsmlModelNavigation
	@Inject extension QualifiedNameProvider
	@Inject extension ReferenceTemplate

	/**
	 * Este método escribe los Statement contenidos en un Body
	 */
	def CharSequence writeStatements(EList<MethodStatement> body) '''		
		«FOR statement : body»
			«writeStatement(statement as MethodStatement)»	
		«ENDFOR»
		
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
			«IF statement.findAncestor(Action) != null»
				«IF statement.isUniqueStatement»
					return "";
				«ENDIF»
			«ENDIF»
		«ENDIF»
	'''
	

	def dispatch CharSequence writeStatement(For statement) '''
		for («statement.variable.type.typeSpecification.typeSpecificationString.toFirstUpper» «statement.variable.name.toFirstLower»:«statement.
			collection.referencedElement.name.toFirstLower»){
			«writeStatements(statement.body)»
		}
	'''

	def dispatch CharSequence writeStatement(Variable statement) '''
		«statement.type.typeSpecification.typeSpecificationString.toFirstUpper» «statement.name.toFirstLower»«IF statement.value != null»=«IF !evaluateToCast(
			statement).empty && (evaluateToCast(statement).get(0) as Boolean)»(«writeType(
			(evaluateToCast(statement).get(1) as Type),true)»)«ENDIF»«writeExpression(statement.value)»«ENDIF»;
	'''

	def dispatch CharSequence writeStatement(Show statement) '''		
		myController.navegar("«(statement.findAncestor(Controller) as Controller).controlledPages.get(0).name.toFirstUpper»","«(statement.expression as Instance).type.typeSpecification.fullyQualifiedName.toString("/")»");		
	'''

	def dispatch CharSequence writeStatement(Return statement) '''
		return «IF statement.expression != null»«writeExpression(statement.expression)»«ENDIF»;
	'''

	def dispatch CharSequence writeStatement(Assignment statement) '''	
		«IF statement.left instanceof VariableReference && (statement.left as VariableReference).tail!=null &&
			(statement.left as VariableReference).tail.referencedElement instanceof Attribute && statement.right != null»
			«writeExpression(statement.left)» ( «IF !evaluateToCast(statement).empty &&
			(evaluateToCast(statement).get(0) as Boolean)»(«writeType((evaluateToCast(statement).get(1) as Type),true)»)«ENDIF»«writeExpression(
			statement.right)»);
		«ELSE»
			«writeExpression(statement.left)» «statement.symbol» «writeExpression(statement.right)»;
		«ENDIF»
	'''
	
}
