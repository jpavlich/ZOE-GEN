package co.edu.javeriana.zoe.generator.persistence.templates

import co.edu.javeriana.isml.isml.ActionCall

import co.edu.javeriana.isml.isml.Instance
import co.edu.javeriana.isml.isml.IntValue
import co.edu.javeriana.isml.isml.LiteralValue
import co.edu.javeriana.isml.isml.Page
import co.edu.javeriana.isml.isml.UnaryOperator
import co.edu.javeriana.isml.scoping.IsmlModelNavigation
import java.util.Map
import javax.inject.Inject
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import co.edu.javeriana.isml.isml.Reference
import co.edu.javeriana.isml.isml.Show
import co.edu.javeriana.isml.isml.StringValue
import co.edu.javeriana.isml.isml.Type
import co.edu.javeriana.isml.isml.BinaryOperator
import co.edu.javeriana.isml.isml.Controller

class ExpressionTemplate {
	@Inject extension ReferenceTemplate
	@Inject extension IsmlModelNavigation
	


	def dispatch CharSequence writeExpression(Type exp) '''
		«exp.typeSpecification.typeSpecificationString.toFirstUpper».class'''




	def dispatch CharSequence writeExpression(UnaryOperator exp) '''
		«writeSymbol(exp.symbol)» «writeExpression(exp.expression)»'''


	

	def dispatch CharSequence writeExpression(Reference ref) '''
		«writeReference(ref)»'''		
	



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


	def dispatch CharSequence writeExpression(LiteralValue exp) '''
		«writeLiteral(exp)»'''
		
	def dispatch CharSequence writeExpression(Instance exp) {
		var String text=""
		if(exp.eContainer.eContainer instanceof Show){
			var Controller controller=exp.findAncestor(Controller) as Controller
			var Map<String,Type> neededAttributes=controller.neededAttributes.get("neededAttributes") as Map<String,Type>
			for(entry:neededAttributes.entrySet){
				if(entry.value.typeSpecification.typeSpecificationString.equals(exp.type.typeSpecification.typeSpecificationString)){
					text+="this."+entry.key +"="
				}
			}
		}		
		text+="new "+ exp.type.typeSpecification.typeSpecificationString.toFirstUpper+"("
		var int i=0;
		for (parameter : exp.parameters){
			text+=writeExpression(parameter)
			if(i!=exp.parameters.size-1){
				text+=","
			}
			i++
		}
		text+=")"
		
	}


	/**
	 * Este método escribe el equivalente en java de los símbolos (Operadores Binarios)
	 * utilizados en el lenguaje ISML
	 */
	def CharSequence writeSymbol(String sym) {
		if(sym.equals("and")){
			return "&&"
		} else if (sym.equals("or")){
			return "||"
		} else if (sym.equals("not")){
			return "!"
		} else{
			return sym
		}		
	}
	
		def dispatch CharSequence writeExpression(BinaryOperator exp) '''
		«writeExpression(exp.left)» «writeSymbol(exp.symbol)» «writeExpression(exp.right)»'''
}