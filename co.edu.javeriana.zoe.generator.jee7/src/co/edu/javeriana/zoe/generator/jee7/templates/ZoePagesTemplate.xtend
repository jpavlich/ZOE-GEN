 package co.edu.javeriana.zoe.generator.jee7.templates

import co.edu.javeriana.isml.generator.common.SimpleTemplate
import co.edu.javeriana.isml.isml.Controller
import co.edu.javeriana.isml.isml.Entity
import co.edu.javeriana.isml.isml.Enum
import co.edu.javeriana.isml.isml.Expression
import co.edu.javeriana.isml.isml.ForView
import co.edu.javeriana.isml.isml.IfView
import co.edu.javeriana.isml.isml.LiteralValue
import co.edu.javeriana.isml.isml.Page
import co.edu.javeriana.isml.isml.Reference
import co.edu.javeriana.isml.isml.ResourceReference
import co.edu.javeriana.isml.isml.VariableReference
import co.edu.javeriana.isml.isml.ViewInstance
import co.edu.javeriana.isml.isml.ViewStatement
import co.edu.javeriana.isml.scoping.IsmlModelNavigation
import co.edu.javeriana.isml.validation.TypeChecker
import com.google.inject.Inject
import java.util.HashMap
import java.util.LinkedHashMap
import java.util.Map
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import co.edu.javeriana.isml.isml.NamedViewBlock

class ZoePagesTemplate extends SimpleTemplate<Page> {
	@Inject extension TypeChecker
	@Inject extension IsmlModelNavigation	
	@Inject extension ExpressionTemplate
	int i;
	Map<ViewInstance,String> forms
	
	int j;

	override preprocess(Page e) {
		i = 1;
			
		forms=new HashMap
	}
	
	

	override def CharSequence template(Page page) '''
		<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
		<html xmlns="http://www.w3.org/1999/xhtml"
		xmlns:ui="http://java.sun.com/jsf/facelets"
		xmlns:f="http://java.sun.com/jsf/core"
		xmlns:h="http://java.sun.com/jsf/html"
		xmlns:c="http://java.sun.com/jsp/jstl/core"
		xmlns:p="http://primefaces.org/ui">	
		
	
	   <ui:define name="metadata">
				<f:metadata>
						<f:event type="preRenderView" listener="#{«page.containerController.name.toFirstLower».init()}" />
				</f:metadata>
		</ui:define>
			
		<ui:composition template="/template.xhtml">
		<ui:define name="content">
			«IF page.body != null»
			«widgetTemplate(page.body)»
			«ENDIF»				
			</ui:define>			
		</ui:composition>	
		
	</html>	
		
	'''
	
	def dispatch CharSequence widgetTemplate(ViewInstance viewInstance) {
	
		switch (viewInstance.type.typeSpecification.typeSpecificationString) {
		
			case "Label": label(viewInstance)
			case "Text": inputText(viewInstance)
			case "Button": button(viewInstance)
			case "Form": form(viewInstance)
			case "Panel": panel(viewInstance)	
			case "PanelButton": panelButton(viewInstance)
			case "DataTable": dataTable(viewInstance)
			case "Password": password(viewInstance)
			case "CheckBox": checkBox(viewInstance)
			case "Calendar": calendar(viewInstance)
			case "Link": link(viewInstance)
			case "ComboChooser": comboChooser(viewInstance)
			case "ListChooser": listChooser(viewInstance)  
			case "Spinner": spinner(viewInstance)
			case "PickList": pickList(viewInstance)
			case "OutputText": outputText(viewInstance)
			case "GMap": Map(viewInstance)
			case "RadioChooser": radioChooser(viewInstance)
			case "Image": image(viewInstance)
		    case "OrderList": orderList(viewInstance)
		}
		
	}
	
	def CharSequence outputText(ViewInstance part) '''
		<p:outputText id= "«part.id»" label=«part.parameters.get(0).writeExpression» value=«part.parameters.get(1).valueTemplate»/>
	'''
	def CharSequence pickList(ViewInstance part)'''
	<p:pickList id="«part.id»"
		itemLabel=«part.parameters.get(0).writeExpression» itemValue="#{pickValue}"
		value=«IF part.parameters.get(1) instanceof ResourceReference»«part.parameters.get(1).writeExpression»«ELSE»"#{«part.containerController.name.toFirstLower».«part.parameters.get(1).writeExpression»}"«ENDIF»
		var="«part.containerController.name»"></p:pickList>
	''' 
	
	def CharSequence listChooser(ViewInstance part)'''
		
	''' 
	
	
	def CharSequence spinner(ViewInstance part)'''
		<p:spinner id="«part.id»" label=«part.parameters.get(0).writeExpression» title=«part.parameters.get(0).writeExpression» 
			value=«IF part.parameters.get(1) instanceof ResourceReference»«part.parameters.get(1).writeExpression»«ELSE»"#{«part.containerController.name.toFirstLower».«part.parameters.get(1).writeExpression»}"«ENDIF»
			stepFactor="«part.parameters.get(2).writeExpression»" min="«part.parameters.get(3).writeExpression»" max="«part.parameters.get(4).writeExpression»" prefix=«part.parameters.get(5).writeExpression»/>
	'''
	
	
	def CharSequence image(ViewInstance part)'''
		<p:commandLink id="«part.id»" «IF part.actionCall!=null»action="#{«(part.actionCall.referencedElement.eContainer as Controller).name.toFirstLower».«part.actionCall.referencedElement.name»()}"«ENDIF»>
			<img id="«part.id»Image" src=«part.parameters.get(0).writeExpression»/>
		</p:commandLink>
	'''

	def CharSequence calendar(ViewInstance part) '''
		 <p:calendar id= "«part.id»" label=«part.parameters.get(0).writeExpression» 
		 	value=«IF part.parameters.get(1) instanceof ResourceReference»«part.parameters.get(1).writeExpression»«ELSE»"#{«part.containerController.name.toFirstLower».«part.parameters.get(1).writeExpression»}"«ENDIF» pattern=«part.parameters.get(3).writeExpression» 
		 	navigator="«part.parameters.get(4).writeExpression»" mode=«part.parameters.get(5).writeExpression» />
	'''
	def CharSequence checkBox(ViewInstance part) '''
		<p:selectBooleanCheckbox id= "«part.id»" label=«part.parameters.get(0).writeExpression» value="#{«part.containerController.name.toFirstLower».«part.parameters.get(1).writeExpression»}"/>		
	'''
	
	def CharSequence link(ViewInstance part)'''
		<h:outputLink id="«part.id»"  value=«part.parameters.get(1).writeExpression»>
			<h:outputText value=«part.parameters.get(0).writeExpression» />
		</h:outputLink>
	'''
	
	def CharSequence comboChooser(ViewInstance part)'''
		«IF part.parameters.get(1).writeExpression instanceof Reference && (part.parameters.get(1).writeExpression as Reference).referencedElement.type.typeSpecification instanceof Entity»
		<h:entitySelectOneMenu id="«part.id»" label=«part.parameters.get(0).writeExpression» 
			valueList="#{«part.containerController.name.toFirstLower».«part.parameters.get(1).writeExpression»}" value=«IF part.parameters.get(2) instanceof ResourceReference»«part.parameters.get(2).writeExpression»«ELSE»"#{«part.containerController.name.toFirstLower».«part.parameters.get(2).writeExpression»}"«ENDIF» noSelectionLabel=«part.parameters.get(3).writeExpression» labelSelection="#{_eachItem}"/>
		«ELSE»
		«IF part.parameters.get(1).writeExpression instanceof Reference && (part.parameters.get(1).writeExpression as Reference).referencedElement.type.typeSpecification instanceof Enum»
		<lion:enumSelectOneMenu id="«part.id»" label=«part.parameters.get(0).writeExpression» 
			valueList="#{«part.containerController.name.toFirstLower».«part.parameters.get(1).writeExpression»}" value=«IF part.parameters.get(2) instanceof ResourceReference»«part.parameters.get(2).writeExpression»«ELSE»"#{«part.containerController.name.toFirstLower».«part.parameters.get(2).writeExpression»}"«ENDIF» noSelectionLabel=«part.parameters.get(3).writeExpression» labelSelection="#{_eachItem}"
		«ELSE»
		<lion:objectSelectOneMenu id="«part.id»" label=«part.parameters.get(0).writeExpression» 
			valueList="#{«part.containerController.name.toFirstLower».«part.parameters.get(1).writeExpression»}" value=«IF part.parameters.get(2) instanceof ResourceReference»«part.parameters.get(2).writeExpression»«ELSE»"#{«part.containerController.name.toFirstLower».«part.parameters.get(2).writeExpression»}"«ENDIF» noSelectionLabel=«part.parameters.get(3).writeExpression» labelSelection="#{_eachItem}"/>
			«ENDIF»
		«ENDIF»
	'''
	

	def CharSequence password(ViewInstance part) '''		
		<p:password  id= "«part.id»" label=«part.parameters.get(0).writeExpression» value=«IF part.parameters.get(1) instanceof ResourceReference»«part.parameters.get(1).writeExpression»«ELSE»"#{«part.containerController.name.toFirstLower».«part.parameters.get(1).writeExpression»}"«ENDIF»/>
	'''
	
	def dispatch CharSequence widgetTemplate(IfView table) '''
	'''
	
	def dispatch CharSequence widgetTemplate(ForView table) '''
	'''

	def dispatch CharSequence widgetTemplate(Reference table) '''
	'''
	
	def CharSequence label(ViewInstance part) '''
		«IF part.actionCall==null»
		<p:outputLabel id= "«part.id»" value=«part.parameters.get(0).valueTemplate» />
		«ELSE»
		<p:commandLink id="«part.id»" value=«part.parameters.get(0).valueTemplate» action="#{«(part.actionCall.referencedElement.eContainer as Controller).name.toFirstLower».«part.actionCall.referencedElement.name»()}"/>
		«ENDIF»'''
		

	def CharSequence inputText(ViewInstance part) '''
	«IF part.rows <= 1 »	
		<p:inputText id= "«part.id»" label=«part.parameters.get(0).writeExpression» value=«part.parameters.get(1).valueTemplate»/>
	 «ELSE» 		 
		<p:inputTextArea id="«part.id»" label=«part.parameters.get(0).writeExpression» value=«IF part.parameters.get(1) instanceof ResourceReference»«part.parameters.get(1).writeExpression»«ELSE»"#{«part.containerController.name.toFirstLower».«part.parameters.get(1).writeExpression»}"«ENDIF» />
 	«ENDIF»
	'''
		 
		
	def CharSequence button(ViewInstance part) '''

	<p:commandButton id= "«part.id»" value=«part.parameters.get(0).writeExpression» 
	 action= "#{«part.containerController.name.toFirstLower».«part.actionCall?.action?.name»(«FOR param:part.actionCall.parameters SEPARATOR ','»«IF param instanceof VariableReference && (param as VariableReference).referencedElement.eContainer instanceof Page»«(part.findAncestor(Page)as Page).controller.name.toFirstLower».«ENDIF»«writeExpression(param)»«ENDFOR»)}" update="«updateTemplate(part)»"  async="true" />
	'''	
	
	def CharSequence updateTemplate(ViewInstance part){
		var EObject tmp=part
		while(tmp!=null){
			if (tmp!=null && tmp instanceof ViewInstance && (tmp as ViewInstance).type.typeSpecification.typeSpecificationString.equals("Form")){
				return ":"+forms.get(tmp as ViewInstance)
			}
			tmp=tmp.eContainer
		}
	}
	
	def CharSequence form(ViewInstance viewInstance) '''
		«val id=viewInstance.id»
		«forms.put(viewInstance,id)»
		<h:form id= "«id»">
			<script src="http://maps.google.com/maps/api/js?sensor=true"></script>
				«FOR partBlock : viewInstance.getBody»
				«widgetTemplate(partBlock)»
				«ENDFOR»
			
		</h:form>
	'''

	def CharSequence Map(ViewInstance part) '''
		
		<p:gmap id= "«part.id»" center=«part.parameters.get(0).writeExpression» zoom=«part.parameters.get(1).writeExpression» type=«part.parameters.get(2).writeExpression» 
		model= "#{«part.containerController.name.toFirstLower».«part.parameters.get(3).writeExpression»}"
		style="width:100%;height:400px" />		
	'''
	def CharSequence radioChooser(ViewInstance part) '''
		<p:selectOneRadio id="«part.id»" label=«part.parameters.get(0).writeExpression»  value=«IF part.parameters.get(2) instanceof ResourceReference»«part.parameters.get(2).writeExpression»«ELSE»"#{«part.containerController.name.toFirstLower».«part.parameters.get(2).writeExpression»}"«ENDIF»
		valueList="#{«part.containerController.name.toFirstLower».«part.parameters.get(1).writeExpression»}"/> 
	'''
	
	def CharSequence panel(ViewInstance viewInstance) '''
	 <p:draggable for="«viewInstance.thisId»" />
	 <p:panel id= "«viewInstance.id»" >
		«FOR partBlock : viewInstance.body»
			«widgetTemplate(partBlock)»
		«ENDFOR»
	</p:panel>	
	
	'''
	
	def CharSequence panelButton(ViewInstance viewAttribute) '''
	'''
	def CharSequence orderList(ViewInstance orderList) '''
		
		
		<p:orderList id= "«orderList.id»" value="#{«orderList.containerController.name.toFirstLower».«orderList.forViewInBody?.collection.referencedElement.name»}" 
		var=var="«orderList.forViewInBody.variable.name»" controlsLocation="none" itemLabel="#{city.almuerzo}" itemValue="#{city.almuerzo}" />
	'''
	
	def CharSequence dataTable(ViewInstance table) '''
		<p:dataTable  id= "«table.id»" paginator="true" paginatorPosition="bottom" var="«table.forViewInBody.variable.name»" 
	
		
		 value="#{«table.containerController.name.toFirstLower».«table.forViewInBody?.collection.referencedElement.name»}" selection= "#{«table.containerController.name.toFirstLower».«table.forViewInBody?.variable.name»}">
			«FOR pair : table.getColumnsDataTable.entrySet»
				«val viewInstance = pair.key as ViewInstance»
				<p:column id= "«viewInstance.id»">
					<f:facet name="header">
						«widgetTemplate(viewInstance)»
					</f:facet>
					«widgetTemplate(pair.value)»
				</p:column>
			«ENDFOR»		
		</p:dataTable>
	'''
	
	
	
	def getColumnsDataTable(ViewInstance table) {
		val columns = new LinkedHashMap<ViewStatement, ViewStatement>
		val header = table.body.get(0) as NamedViewBlock
		val forView = table.body.get(1).cast(NamedViewBlock).body.get(0) as ForView
		for (i : 0 ..< header.body.size) {
			columns.put(header.body.get(i), forView.body.get(i))
			
		}
		return columns
	}
	
	def getId(ViewInstance part){
		if(part.name!= null){
			return part.name
		}else{
			return part.view.name.toFirstLower + i++;
		}		
	}
	
		def getThisId(ViewInstance part){
			j = 0;
		if(part.name!= null){
			return part.name
		}else{
			j =i;
			return part.view.name.toFirstLower+j;
		}		
	}
	
	
	def dispatch CharSequence widgetTemplate(EList<ViewStatement> partBlock)'''
		«IF partBlock != null»
			«FOR part : partBlock»
				
				«widgetTemplate(part)»		
				
			«ENDFOR»
		«ENDIF»
			
	'''
	def CharSequence valueTemplate(Expression expression){		
		if(expression instanceof Reference){
			if (expression instanceof ResourceReference){
				return expression.writeExpression
			}else if(expression instanceof VariableReference){
				if (expression.referencedElement.findAncestor(ForView) != null){ 
				 	return "\""+"#{"+expression.writeExpression+"}"+"\""
				} else {
					return "\""+"#{"+expression.containerController.name.toFirstLower+"."+expression.writeExpression+"}"+"\""
				}				
			}
		} else{
			return expression.writeExpression
		}
	}	
}
