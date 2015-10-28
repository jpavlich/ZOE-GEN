package co.edu.javeriana.zoe.generator.persistence.templates

import co.edu.javeriana.isml.isml.ActionCall
import co.edu.javeriana.isml.isml.BinaryOperator
import co.edu.javeriana.isml.isml.Controller
import co.edu.javeriana.isml.isml.Instance
import co.edu.javeriana.isml.isml.IntValue
import co.edu.javeriana.isml.isml.LiteralValue
import co.edu.javeriana.isml.isml.Page
import co.edu.javeriana.isml.isml.Reference
import co.edu.javeriana.isml.isml.Show
import co.edu.javeriana.isml.isml.StringValue
import co.edu.javeriana.isml.isml.Type
import co.edu.javeriana.isml.isml.UnaryOperator
import co.edu.javeriana.isml.scoping.IsmlModelNavigation
import java.util.Map
import javax.inject.Inject
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList

class ExpressionTemplate {
	@Inject extension IsmlModelNavigation
	@Inject extension ReferenceTemplate


	def dispatch CharSequence writeExpression(Type expression) '''
		«expression.typeSpecification.typeSpecificationString.toFirstUpper».class'''


	def dispatch CharSequence writeExpression(Instance expression) {
		var String text=""
		if(expression.eContainer.eContainer instanceof Show){
			var Controller controller=expression.findAncestor(Controller) as Controller
			var Map<String,Type> neededAttributes=controller.neededAttributes.get("neededAttributes") as Map<String,Type>
			for(entry:neededAttributes.entrySet){
				if(entry.value.typeSpecification.typeSpecificationString.equals(expression.type.typeSpecification.typeSpecificationString)){
					text+="this."+entry.key +"="
				}
			}
		}		
		text+="new "+ expression.type.typeSpecification.typeSpecificationString.toFirstUpper+"("
		var int i=0;
		for (parameter : expression.parameters){
			text+=writeExpression(parameter)
			if(i!=expression.parameters.size-1){
				text+=","
			}
			i++
		}
		text+=")"
		
	}

	def dispatch CharSequence writeExpression(BinaryOperator expression) '''
		«writeExpression(expression.left)» «writeSymbol(expression.symbol)» «writeExpression(expression.right)»'''

	def dispatch CharSequence writeExpression(UnaryOperator expression) '''
		«writeSymbol(expression.symbol)» «writeExpression(expression.expression)»'''

	def dispatch CharSequence writeExpression(LiteralValue expression) '''
		«writeLiteral(expression)»'''
	

	def dispatch CharSequence writeExpression(Reference reference) '''
		«writeReference(reference)»'''		
	

	/**
	 * Este método escribe el equivalente en java de los símbolos (Operadores Binarios)
	 * utilizados en el lenguaje ISML
	 */
	def CharSequence writeSymbol(String symbol) {
		if(symbol.equals("and")){
			return "&&"
		} else if (symbol.equals("or")){
			return "||"
		} else if (symbol.equals("not")){
			return "!"
		} else{
			return symbol
		}		
	}

	/**
	 * Este método escribe los valores literales para JAVA
	 */
	def CharSequence writeLiteral(LiteralValue literal) {
		if (literal instanceof StringValue){
			return "\""+literal.literal+"\""
		}else if(literal instanceof IntValue && literal.findAncestor(Page)==null){
			return literal.literal +"L"
		}else {
			return literal.literal.toString
		}
	}

	/**
	 * Este método busca los controladores de los ActionCall que puedan existir en el 
	 * Controller que entra por parámetros, para ser inyectados como atributos del
	 * BackingBean
	 */
	def EList<Controller> getActionCallControllers(Controller controller) {
		var EList<Controller> actionCallList = new BasicEList();
		for (action : controller.actions) {
			for (statement : action.body) {
				if (statement instanceof ActionCall) {
					var Controller c = statement.action.eContainer as Controller
					if (!actionCallList.contains(c) && !c.name.equals(controller.name)) {
						actionCallList.add(c);
					}
				}
			}
		}
		return actionCallList
	}

}