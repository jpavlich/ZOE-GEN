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
import co.edu.javeriana.zoe.generator.jee7.templates.ZoeControllerTemplate
import co.edu.javeriana.isml.isml.Controller

@InjectWith(IsmlInjectorProvider)
@RunWith(XtextRunner)
class BackingBeanGeneratorTest extends CommonTests {
	@Inject extension ParseHelper<InformationSystem>
	@Inject extension ValidationTestHelper
	@Inject extension TestGeneratorHelper
	@Inject extension IsmlModelNavigation
	@Inject ZoeControllerTemplate template

	@Test
	def dataTableGeneration() {
		val obj = '''
			package test;
			
		entity Dieta {

				    String desayuno;
				    String almuerzo;
				    String cena;
				    String merienda;
				    Integer patologia;
	
			}


			controller DietaManager {
				has Persistence<Dieta> persistence;
				
				/**
				* Lists all instances of Dieta.
				*/
				default listAll() {
					show DietaList(persistence.findAll());
				}
			
				/**
				* Lists instances of Dieta.
				* @param dietaList The list of instances of Dieta to show.
				*/
				listDieta(Collection<Dieta> dietaList) {
					show DietaList(dietaList);
				}
				
			
			
				
				createDietaToAdd(Any container, Collection<Dieta> collection) {
					show CreateDietaToAdd(container, collection, new Dieta);
						
				}
			
				
				
			
				/**
				* Views an instance of Dieta.
				* @param dieta the Dieta to open.
				*/		
				viewDieta(Dieta dieta) {
					show ViewDieta(dieta);
				}
			
			
				/**
				* Edits an existing instance of Dieta.
				* @param dieta the Dieta to open.
				*/			
				editDieta(Dieta dieta) {
					show EditDieta(dieta);
				}
			
				/**
				* Creates a a new instance of Dieta.
				*/			
				createDieta() {
					show EditDieta(new Dieta);
				}
			
			
				/**
				* Saves an instance of Dieta.
				* @param dieta the Dieta to save.
				*/			
				saveDieta(Dieta dieta) {
				if(persistence.isPersistent(dieta)){
					persistence.save(dieta);
				} else {
					persistence.create(dieta);
				}
				-> listAll();
				}
			
			
				/**
				* Deletes an instance of Dieta.
				* @param dieta the Dieta to delete.
				*/		
				deleteDieta(Dieta dieta) {
					persistence.remove(dieta);
					-> listAll();
				}
			
			
			}
			
			
					page DietaList(Collection<Dieta> dietaList) controlledBy DietaManager  {
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
				                   	Button("View",false)-> viewDieta(dieta);
				                   	Button("Edit",false) -> editDieta(dieta);
				                   	Button("Delete",false) -> deleteDieta(dieta);
				               }
			
				           }
				        	
					}
				} 
				
			}
			
			
		'''.parse(rs)
		obj.assertNoErrors
		val controller = obj.body.head.cast(Package).body.get(2).cast(Controller)
		assertGenerates(template,
			controller,
			'''
				Entity {
					name = Person
					extends = [Animal, Sentient]
					body = {
						Attribute {
							name = name
							type = String
							constraints = [Size]
						}
						Attribute {
							name = age
							type = Integer
							constraints = []
						}
					}
				}
			'''
		)

	}
}