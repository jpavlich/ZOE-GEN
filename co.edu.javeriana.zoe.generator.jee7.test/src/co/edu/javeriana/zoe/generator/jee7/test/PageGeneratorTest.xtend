package co.edu.javeriana.zoe.generator.jee7.test

import co.edu.javeriana.isml.IsmlInjectorProvider
import co.edu.javeriana.isml.generator.test.TestGeneratorHelper
import co.edu.javeriana.isml.isml.InformationSystem
import co.edu.javeriana.isml.isml.Package
import co.edu.javeriana.isml.isml.Page
import co.edu.javeriana.isml.scoping.IsmlModelNavigation
import co.edu.javeriana.isml.tests.CommonTests
import com.google.inject.Inject
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.eclipse.xtext.junit4.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith
import co.edu.javeriana.zoe.generator.jee7.templates.ZoePagesTemplate

@InjectWith(IsmlInjectorProvider)
@RunWith(XtextRunner)
class PageGeneratorTest extends CommonTests {
	@Inject extension ParseHelper<InformationSystem>
	@Inject extension ValidationTestHelper
	@Inject extension TestGeneratorHelper
	@Inject extension IsmlModelNavigation
	@Inject ZoePagesTemplate template

	@Test
	def dataTableGeneration() {
		val obj = '''
			package co.edu.javeriana;
			import co.edu.javeriana.prenat.resource.*;
			import co.edu.javeriana.entities.* ;
			
			page DietaList( Collection<Dieta> dietaList) controlledBy DietaManager  {
			
				Form {
				       Panel("Collection<Dieta>") {
				       	
				           DataTable("Collection<Dieta>", null) {
				               header : {                    
				                   Label("Desayuno");
				                   Label("Almuerzo");
				                   Label("Cena");
				                   Label("Merienda");
				                   Label("Patologia");
				                   Label("View");
				                   Label("Edit");
				                   Label("Delete");
				               }
				               body : 
				               for(Dieta dieta in dietaList) {
				               		Label(dieta.desayuno);
				               		Label(dieta.almuerzo);
				               		Label(dieta.cena);
				               		Label(dieta.merienda);
				               		Label(dieta.patologia);
				                   	Button("View")-> viewDieta(dieta);
				                   	Button("Edit") -> editDieta(dieta);
				                   	Button("Delete") -> deleteDieta(dieta);
				                  
				          			
				               }
							
				           }
			
					}
				} 
				
			}
			
			
		'''.parse(rs)
		obj.assertNoErrors
		val page = obj.body.head.cast(Package).body.get(2).cast(Page)
		template.assertGenerates(
			page,
			'''
			<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
				<html xmlns="http://www.w3.org/1999/xhtml"
				xmlns:ui="http://java.sun.com/jsf/facelets"
				xmlns:f="http://java.sun.com/jsf/core"
				xmlns:h="http://java.sun.com/jsf/html"
				xmlns:c="http://java.sun.com/jsp/jstl/core"
				xmlns:p="http://primefaces.org/ui">	
				
			
			   <ui:define name="metadata">
						<f:metadata>
								<f:event type="preRenderView" listener="#{dietaManager.init()}" />
						</f:metadata>
				</ui:define>
					
				<ui:composition template="/template.xhtml">
				<ui:define name="content">
					
					<h:form id= "form1">
						<script src="http://maps.google.com/maps/api/js?sensor=true"></script>
							 <p:draggable for="panel2" />
							 <p:panel id= "panel2" >
								<p:dataTable  id= "dataTable3" paginator="true" paginatorPosition="bottom" var="dieta" 
									
								
								 value="#{dietaManager.dietaList}" selection= "#{dietaManager.dieta}">
									<p:column id= "label4">
										<f:facet name="header">
											<p:outputLabel id= "label5" value="Desayuno" />
										</f:facet>
										<p:outputLabel id= "label6" value="#{dieta.desayuno}" />
									</p:column>
									<p:column id= "label7">
										<f:facet name="header">
											<p:outputLabel id= "label8" value="Almuerzo" />
										</f:facet>
										<p:outputLabel id= "label9" value="#{dieta.almuerzo}" />
									</p:column>
									<p:column id= "label10">
										<f:facet name="header">
											<p:outputLabel id= "label11" value="Cena" />
										</f:facet>
										<p:outputLabel id= "label12" value="#{dieta.cena}" />
									</p:column>
									<p:column id= "label13">
										<f:facet name="header">
											<p:outputLabel id= "label14" value="Merienda" />
										</f:facet>
										<p:outputLabel id= "label15" value="#{dieta.merienda}" />
									</p:column>
									<p:column id= "label16">
										<f:facet name="header">
											<p:outputLabel id= "label17" value="Patologia" />
										</f:facet>
										<p:outputLabel id= "label18" value="#{dieta.patologia}" />
									</p:column>
									<p:column id= "label19">
										<f:facet name="header">
											<p:outputLabel id= "label20" value="View" />
										</f:facet>
										
										<p:commandButton id= "button21" value="View" 
										 action= "#{dietaManager.viewDieta(dieta)}" update=":form1"  async="true" />
									</p:column>
									<p:column id= "label22">
										<f:facet name="header">
											<p:outputLabel id= "label23" value="Edit" />
										</f:facet>
										
										<p:commandButton id= "button24" value="Edit" 
										 action= "#{dietaManager.editDieta(dieta)}" update=":form1"  async="true" />
									</p:column>
									<p:column id= "label25">
										<f:facet name="header">
											<p:outputLabel id= "label26" value="Delete" />
										</f:facet>
										
										<p:commandButton id= "button27" value="Delete" 
										 action= "#{dietaManager.deleteDieta(dieta)}" update=":form1"  async="true" />
									</p:column>
								</p:dataTable>
							</p:panel>	
							
						
					</h:form>
					
						
					</ui:define>			
				</ui:composition>	
				
			</html>	
				
	

			'''
		)

	}
}