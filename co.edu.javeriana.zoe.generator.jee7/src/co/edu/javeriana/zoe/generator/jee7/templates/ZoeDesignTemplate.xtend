package co.edu.javeriana.zoe.generator.jee7.templates

import co.edu.javeriana.isml.generator.common.SimpleTemplate
import co.edu.javeriana.isml.isml.Action
import co.edu.javeriana.isml.isml.Controller
import co.edu.javeriana.isml.isml.Instance
import co.edu.javeriana.isml.isml.Page
import co.edu.javeriana.isml.isml.Show
import co.edu.javeriana.isml.scoping.IsmlModelNavigation
import com.google.inject.Inject
import java.util.HashMap
import java.util.List
import java.util.Map
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.eclipse.xtext.naming.IQualifiedNameProvider

class ZoeDesignTemplate extends SimpleTemplate<List<Page>> {

	/*Inyección de las clases auxiliares con metodos utilitarios*/
	@Inject extension IQualifiedNameProvider
	@Inject extension IsmlModelNavigation

	// override preprocess(List<Page> is) {
	// allPages = new HashSet
	// allPages.addAll(is.eResource.getAllInstances(IsmlPackage.eINSTANCE.page).filter(Page))
	// }
	override def CharSequence template(List<Page> allPages) '''
	<?xml version='1.0' encoding='UTF-8' ?> 
		<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
		<html xmlns="http://www.w3.org/1999/xhtml"
		      xmlns:h="http://java.sun.com/jsf/html"
		      xmlns:f="http://java.sun.com/jsf/core"
		      xmlns:ui="http://java.sun.com/jsf/facelets"
		      xmlns:p="http://primefaces.org/ui">
		    <f:view contentType="text/html">
		        <h:head>
		            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		            <link href="../resources/css/paginasControles.css" rel="stylesheet" type="text/css"/>
		            <title>ZOE-GEN - PRENAT - MISyC</title>
		        </h:head>
		        <h:body style="background: #045491" >
		            <p:layout fullPage="true" style="background: #045491">
		                <p:layoutUnit position="north" size="100" resizable="false" closable="true" collapsible="true" style="background: #045491">
		                    <p:layout style="min-width:400px;min-height:200px;">
		                        <p:layoutUnit position="west" size="300" minSize="40" maxSize="300">
		
		                            <ui:include src="headerLogin.xhtml"/>   
		
		                        </p:layoutUnit>
		                        <p:layoutUnit position="center" >
		                            <div>
		                                <h:outputLabel for="name" value="Bienvenido a Prenat" style="font-weight:bold" />
		                            </div>
		
		                        </p:layoutUnit>
		
		                    </p:layout>
		                </p:layoutUnit>
		                <p:layoutUnit position="south" size="100" closable="true" collapsible="true" resizable="false" style="background: #045491">
		                    <div>
		                        <h:outputText value="Aplicacion generada con el transformador ZOE-GEN" />
		                    </div>
		                    <div>
		                        <h:outputText value="Maestria en Ingenieria de Sistemas y Computacion" />
		                    </div>
		                    <div>
		                        <h:outputText value="Pontificia Universidad Javeriana" />
		                    </div>
		                    <div>
		                        <h:outputText value="Bogota D.C:" />
		                    </div>
		                    <div>
		                        <h:outputText value="Abril - 2016" />
		                    </div>
		                </p:layoutUnit>
		                <p:layoutUnit position="west" size="200" closable="true" resizable="false" collapsible="true" style="background: #045491" >
		                    <h:form>
		                        <p:growl autoUpdate="true"/>
		                        
		                                     
		                            <p:menu  style="width:170px">        

		
		
		                                   «FOR page : allPages»
		                                   	«IF page.controller != null && !getShowActions(page).empty»
		                                   		
		                                   		    <p:menuitem value="«page.name»" outcome="/«page?.eContainer?.fullyQualifiedName.toString("/") + "/" + page.name + ".xhtml"»" />
		                                   		
		                                   	«ENDIF»
		                                   «ENDFOR»
		                            </p:menu> 
		                      
		                    </h:form>
		                </p:layoutUnit>
		                <p:layoutUnit position="center" resizable="false" style="background: #045491">
		
		                    <ui:insert name="title"></ui:insert>
		                    <ui:insert name="content"></ui:insert>  
		
		                </p:layoutUnit>
		            </p:layout>
		        </h:body>
		    </f:view>
		</html>	
			
			
	'''

	def Map<String, String> getUniqueActions(Iterable<Show> showStmnts) {
		var Map<String, String> uniqueActions = new HashMap
		for (stmnt : showStmnts) {
			var String key = "goTo" + ((stmnt.expression as Instance).type.typeSpecification as Page).name
			if (!uniqueActions.containsKey(key)) {
				var String value = ((stmnt.expression as Instance).type.typeSpecification as Page).eContainer.
					fullyQualifiedName.toString("/") + "/" +
					((stmnt.expression as Instance).type.typeSpecification as Page).name + ".xhtml"
				uniqueActions.put(key, value);
			}
		}
		return uniqueActions;
	}

	// /**
	// * Este método obtiene las acciones que contienen acciones de tipo show invocadas en una página determinada
	// */
	// def EList<Action> getShowActions(Page page) {
	// var EList<Action> actions = new BasicEList
	// for (component : page.body.statements.filter(ViewInstance)) {
	// if (component instanceof ViewBlock) {
	//
	// getShowActionsViewBlock(component);
	// }
	// if (component.actionCall != null) {
	// if (seekOnBody((component.actionCall.action as Action).body)) {
	// actions.add(component.actionCall.action as Action)
	// }
	// }
	// }
	// return actions
	// }
	//
	// def EList<Action> getShowActionsViewBlock(ViewInstance viewInstance) {
	//
	// var EList<Action> actions = new BasicEList
	// for (component : viewInstance.parameters.filter(ViewBlock)) {
	// for (componentViewins : viewInstance.parameters.filter(ViewInstance)) {
	// if (componentViewins.actionCall != null) {
	// if (seekOnBody((componentViewins.actionCall.action as Action).body)) {
	// actions.add(componentViewins.actionCall.action as Action)
	// }
	// }
	// }
	//
	// }
	// return actions
	//
	// }
	/**
	 * Este método obtiene las acciones que contienen statements de tipo Show de un Controlador dado
	 */
	def EList<Action> getShowActions(Controller controller) {
		var EList<Action> actions = new BasicEList
		for (action : controller.actions) {
			if (action.showStatements.toList.size > 0) {
				actions.add(action)
			}
		}
		return actions
	}

}
