 package co.edu.javeriana.zoe.generator.jee7.templates

import co.edu.javeriana.isml.generator.common.SimpleTemplate
import co.edu.javeriana.isml.isml.Action
import co.edu.javeriana.isml.isml.Block
import co.edu.javeriana.isml.isml.Controller
import co.edu.javeriana.isml.isml.For
import co.edu.javeriana.isml.isml.If
import co.edu.javeriana.isml.isml.Instance
import co.edu.javeriana.isml.isml.Page
import co.edu.javeriana.isml.isml.Show
import co.edu.javeriana.isml.isml.Statement
import co.edu.javeriana.isml.isml.While
import co.edu.javeriana.isml.scoping.TypeExtension
import com.google.inject.Inject
import java.util.List
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.eclipse.xtext.naming.IQualifiedNameProvider
import java.util.Map
import java.util.HashMap

class FacesConfigXMLTemplate extends SimpleTemplate<List<Page>> {

	/*Inyección de las clases auxiliares con metodos utilitarios*/
	@Inject extension IQualifiedNameProvider
	@Inject extension TypeExtension

	//	override preprocess(List<Page> is) {
	//		allPages = new HashSet
	//		allPages.addAll(is.eResource.getAllInstances(IsmlPackage.eINSTANCE.page).filter(Page))
	//	}
	override def CharSequence template(List<Page> allPages) '''
		<?xml version="1.0" encoding="UTF-8"?>
		<!-- JBoss, Home of Professional Open Source Copyright 2013, Red Hat, Inc. 
			and/or its affiliates, and individual contributors by the @authors tag. See 
			the copyright.txt in the distribution for a full listing of individual contributors. 
			Licensed under the Apache License, Version 2.0 (the "License"); you may not 
			use this file except in compliance with the License. You may obtain a copy 
			of the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required 
			by applicable law or agreed to in writing, software distributed under the 
			License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS 
			OF ANY KIND, either express or implied. See the License for the specific 
			language governing permissions and limitations under the License. -->
		<!-- This file is not required if you don't need any extra configuration. -->
		<faces-config version="2.0" xmlns="http://java.sun.com/xml/ns/javaee"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xsi:schemaLocation="
			       http://java.sun.com/xml/ns/javaee
			       http://java.sun.com/xml/ns/javaee/web-facesconfig_2_0.xsd">
		
			<!-- This descriptor activates the JSF 2.0 Servlet -->
		
			<!-- Write your navigation rules here. You are encouraged to use CDI for 
				creating @Named managed beans. -->		
			
			<application>
		        <resource-bundle>
		            <base-name>co.com.heinsohn.lion.common.web.util.MessageProperties</base-name>
		            <var>messages</var>
		        </resource-bundle>
		        <locale-config>
		            <default-locale>es</default-locale>
		            <supported-locale>en</supported-locale>
		        </locale-config>
		    </application>
		    <converter>
		        <converter-id>permission.ConverterPermissionPick</converter-id>
		        <converter-class>co.com.heinsohn.lion.security.permission.converter.ConverterPermissionPick</converter-class>
		    </converter>
		    <converter>
		        <converter-id>userGestion.ProfilesConverter</converter-id>
		        <converter-class>co.com.heinsohn.lion.security.user.converter.ConverterPick</converter-class>
		    </converter>
		    <converter>
		        <converter-id>objectConvert</converter-id>
		        <converter-class>
					co.com.heinsohn.lion.common.web.jsfconvertidores.ObjectConvert
				</converter-class>
		    </converter>
		    <validator>
		        <validator-id>dateFormatValidator</validator-id>
		        <validator-class>co.com.heinsohn.lion.common.web.jsfvalidators.DateFormatValidator</validator-class>
		    </validator>
		    <validator>
		        <validator-id>emptyStringValidator</validator-id>
		        <validator-class>co.com.heinsohn.lion.common.web.jsfvalidators.EmptyStringValidator</validator-class>
		    </validator>
		    <validator>
		        <validator-id>patternValidator</validator-id>
		        <validator-class>co.com.heinsohn.lion.common.web.jsfvalidators.PatternValidator</validator-class>
		    </validator> 
			<converter>
				<converter-id>dateCalendarConverter</converter-id>
				<converter-class>co.com.heinsohn.lion.common.web.jsfconvertidores.DateCalendarConverter</converter-class>
			</converter>
			
			<!-- Navigation Rules -->
			
			<navigation-rule>
				<from-view-id>/login.xhtml</from-view-id>
				<navigation-case>
					<from-action>#{login.login}</from-action>
					<from-outcome>sucess</from-outcome>
					<to-view-id>/index.xhtml</to-view-id>
				</navigation-case>
			</navigation-rule>
			«FOR page : allPages»
				«IF page.controller != null && !getShowActions(page).empty»
					<navigation-rule>
						<from-view-id>/«page?.eContainer?.fullyQualifiedName.toString("/") + "/" + page.name + ".xhtml"»</from-view-id>
						«FOR action : page.showActions»							
							«FOR show : getShowStatements(action.body).uniqueActions.entrySet»
								<navigation-case>									   
									<from-outcome>«show.key»</from-outcome>
									<to-view-id>/«show.value»</to-view-id>	
								</navigation-case>
							«ENDFOR»													
						«ENDFOR»
					</navigation-rule>
				«ENDIF»
				
			«ENDFOR»
		</faces-config>
			
	'''

	def Map<String, String> getUniqueActions(EList<Show> showStmnts) {
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
			if (seekOnBody(action.body)) {
				actions.add(action)
			}
		}
		return actions
	}

	/**
	 * Método que determina si existen statements de tipo show un Block
	 */
	def boolean seekOnBody(Block body) {
		for (statement : body.statements) {
			return seek(statement)
		}
		return false
	}

	/**
	 * Método recursivo para determinar si hay métodos show en un statement que contenga Block
	 */
	def boolean seek(Statement statement) {
		if (statement instanceof Show) {
			return true;
		} else if (statement instanceof If) {
			if (statement.elseBody != null) {
				return seekOnBody(statement.elseBody)
			}
			return seekOnBody(statement.body)
		} else if (statement instanceof While) {
			return seekOnBody(statement.body)
		} else if (statement instanceof For) {
			return seekOnBody(statement.body)
		}
		return false;
	}

}