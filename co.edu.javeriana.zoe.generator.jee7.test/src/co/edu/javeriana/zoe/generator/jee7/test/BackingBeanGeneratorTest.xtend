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
			
			entity MyEntity {
				String name;
			}
			
			controller Controller;
			
			page MyPage(Collection<MyEntity> list) controlledBy Controller  {
			        DataTable("Collection<Dieta>", null) {
			            header : {                    
			                Label("Name");
			            }
			            body : 
			            for(MyEntity e in list) {
			            	Label(e.name);
			            }
			
			         }
			}
			
		'''.parse(rs)
		obj.assertNoErrors
		val page = obj.body.head.cast(Package).body.get(2).cast(Page)
		assertGenerates(template,
			page,
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