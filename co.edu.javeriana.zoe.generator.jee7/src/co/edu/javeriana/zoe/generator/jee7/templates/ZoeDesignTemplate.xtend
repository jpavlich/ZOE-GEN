 package co.edu.javeriana.zoe.generator.jee7.templates

import co.edu.javeriana.isml.generator.common.SimpleTemplate
import co.edu.javeriana.isml.isml.Action
import co.edu.javeriana.isml.isml.Controller
import co.edu.javeriana.isml.isml.For
import co.edu.javeriana.isml.isml.If
import co.edu.javeriana.isml.isml.Instance
import co.edu.javeriana.isml.isml.Page
import co.edu.javeriana.isml.isml.Show
import co.edu.javeriana.isml.isml.Statement
import co.edu.javeriana.isml.isml.While
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

	//	override preprocess(List<Page> is) {
	//		allPages = new HashSet
	//		allPages.addAll(is.eResource.getAllInstances(IsmlPackage.eINSTANCE.page).filter(Page))
	//	}
	override def CharSequence template(List<Page> allPages) '''
		<?xml version='1.0' encoding='UTF-8' ?> 
		<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
		<html xmlns="http://www.w3.org/1999/xhtml"
		      xmlns:ui="http://xmlns.jcp.org/jsf/facelets"
		      xmlns:h="http://xmlns.jcp.org/jsf/html"
		      xmlns:p="http://primefaces.org/ui">
		
		    <h:head>
		        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		        <h:outputStylesheet name="./css/default.css"/>
		        <h:outputStylesheet name="./css/cssLayout.css"/>
		        <title>Facelets Template</title>
		    </h:head>
		
		    <h:body>
		
		
		        <div id="top" class="top">
		            <ui:insert name="top">Top</ui:insert>
		        </div>
		        <div>
		            <div id="left">
		                <ui:insert name="left">Left</ui:insert>
		                 <h:form id="menuForm">
		                    <p:menubar>
		                        <p:menuitem value="#{bundle.Home}" outcome="/index" icon="ui-icon-home"/>
		                        <p:submenu label="#{bundle.Maintenance}">
		                            <p:menuitem value="Dieta" outcome="/co/edu/javeriana/ViewDieta.xhtml" />
		                        </p:submenu>
		                    </p:menubar>
		                </h:form>
		            </div>
		            <div id="content" class="left_content">
		                <ui:insert name="content">Content</ui:insert>
		            </div>
		        </div>
		    </h:body>
		
		</html>
			
	'''

	def Map<String, String> getUniqueActions(Iterable<Show> showStmnts) {
		var Map<String, String> uniqueActions = new HashMap
		for (stmnt : showStmnts) {
			var String key = "goTo" + ((stmnt.expression as Instance).type.typeSpecification as Page).name
			if (!uniqueActions.containsKey(key)) {
				var String value = ((stmnt.expression as Instance).type.typeSpecification as Page).eContainer.
					fullyQualifiedName.toString("/") + "/" + ((stmnt.expression as Instance).type.typeSpecification as Page).name +
					".xhtml"
				uniqueActions.put(key, value);
			}
		}
		return uniqueActions;
	}

	//	/**
	//	 * Este método obtiene las acciones que contienen acciones de tipo show invocadas en una página determinada
	//	 */
	//	def EList<Action> getShowActions(Page page) {
	//		var EList<Action> actions = new BasicEList
	//		for (component : page.body.statements.filter(ViewInstance)) {
	//			if (component instanceof ViewBlock) {
	//
	//				getShowActionsViewBlock(component);
	//			}
	//			if (component.actionCall != null) {
	//				if (seekOnBody((component.actionCall.action as Action).body)) {
	//					actions.add(component.actionCall.action as Action)
	//				}
	//			}
	//		}
	//		return actions
	//	}
	//
	//	def EList<Action> getShowActionsViewBlock(ViewInstance viewInstance) {
	//
	//		var EList<Action> actions = new BasicEList
	//		for (component : viewInstance.parameters.filter(ViewBlock)) {
	//			for (componentViewins : viewInstance.parameters.filter(ViewInstance)) {
	//				if (componentViewins.actionCall != null) {
	//					if (seekOnBody((componentViewins.actionCall.action as Action).body)) {
	//						actions.add(componentViewins.actionCall.action as Action)
	//					}
	//				}
	//			}
	//
	//		}
	//		return actions
	//
	//	}
	/**
	 * Este método obtiene las acciones que contienen statements de tipo Show de un Controlador dado
	 */
	def EList<Action> getShowActions(Controller controller) {
		var EList<Action> actions = new BasicEList
		for (action : controller.actions) {
			if (action.showStatements.toList.size >0)  {
				actions.add(action)
			}
		}
		return actions
	}


}
