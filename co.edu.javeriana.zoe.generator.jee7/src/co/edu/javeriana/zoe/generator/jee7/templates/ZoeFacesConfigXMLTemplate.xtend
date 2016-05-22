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

/**
 * 
 * Clase que retorna la plantilla para el archivo faces config
 * 
 * autor:john.olarte@javeriana.edu.co
 */
class FacesConfigXMLTemplate extends SimpleTemplate<List<Page>> {

	/*Inyección de las clases auxiliares con metodos utilitarios*/
	@Inject extension IsmlModelNavigation
	@Inject extension IQualifiedNameProvider

/**
 * Método que retorna una plantilla para el archivo faces-config que recibe como parámetro la lista de paginas.
 * 
 */

	override def CharSequence template(List<Page> totalPages) '''
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
			 <!-- <application>
		        <resource-bundle>
		            <base-name>co.edu.javeriana.prenat.resource.Resources_es_CO</base-name>
		            <var>messages</var>
		        </resource-bundle>
		        <locale-config>
		            <default-locale>es</default-locale>
		            <supported-locale>en</supported-locale>
		        </locale-config>
	 		</application> -->
			<!-- Navigation Rules -->
			
			<navigation-rule>
				<from-view-id>/login.xhtml</from-view-id>
				<navigation-case>
					<from-action>#{login.login}</from-action>
					<from-outcome>sucess</from-outcome>
					<to-view-id>/index.xhtml</to-view-id>
				</navigation-case>
			</navigation-rule>
			«FOR p : totalPages»
				«IF p.controller != null && !getShowActions(p).empty»
					<navigation-rule>
						<from-view-id>/«p?.eContainer?.fullyQualifiedName.toString("/") + "/" + p.name + ".xhtml"»</from-view-id>
						«FOR method : p.showActions»							
							«FOR view : getShowStatements(method).uniqueActions.entrySet»
								<navigation-case>									   
									<from-outcome>«view.key»</from-outcome>
									<to-view-id>/«view.value»</to-view-id>	
								</navigation-case>
							«ENDFOR»													
						«ENDFOR»
					</navigation-rule>
				«ENDIF»
				
			«ENDFOR»
		</faces-config>
			
	'''

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

	
	


}
