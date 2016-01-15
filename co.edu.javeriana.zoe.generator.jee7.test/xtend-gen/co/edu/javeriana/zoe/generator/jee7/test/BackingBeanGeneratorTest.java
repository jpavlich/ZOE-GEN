package co.edu.javeriana.zoe.generator.jee7.test;

import co.edu.javeriana.isml.IsmlInjectorProvider;
import co.edu.javeriana.isml.generator.test.TestGeneratorHelper;
import co.edu.javeriana.isml.isml.Controller;
import co.edu.javeriana.isml.isml.InformationSystem;
import co.edu.javeriana.isml.isml.NamedElement;
import co.edu.javeriana.isml.scoping.IsmlModelNavigation;
import co.edu.javeriana.isml.tests.CommonTests;
import co.edu.javeriana.zoe.generator.jee7.templates.ZoeControllerTemplate;
import com.google.inject.Inject;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.eclipse.xtext.junit4.validation.ValidationTestHelper;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Test;
import org.junit.runner.RunWith;

@InjectWith(IsmlInjectorProvider.class)
@RunWith(XtextRunner.class)
@SuppressWarnings("all")
public class BackingBeanGeneratorTest extends CommonTests {
  @Inject
  @Extension
  private ParseHelper<InformationSystem> _parseHelper;
  
  @Inject
  @Extension
  private ValidationTestHelper _validationTestHelper;
  
  @Inject
  @Extension
  private TestGeneratorHelper _testGeneratorHelper;
  
  @Inject
  @Extension
  private IsmlModelNavigation _ismlModelNavigation;
  
  @Inject
  private ZoeControllerTemplate template;
  
  @Test
  public void dataTableGeneration() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("package test;");
      _builder.newLine();
      _builder.newLine();
      _builder.append("entity MyEntity {");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("String name;");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
      _builder.append("controller Controller;");
      _builder.newLine();
      _builder.newLine();
      _builder.append("page MyPage(Collection<MyEntity> list) controlledBy Controller  {");
      _builder.newLine();
      _builder.append("        ");
      _builder.append("DataTable(\"Collection<Dieta>\", null) {");
      _builder.newLine();
      _builder.append("            ");
      _builder.append("header : {                    ");
      _builder.newLine();
      _builder.append("                ");
      _builder.append("Label(\"Name\");");
      _builder.newLine();
      _builder.append("            ");
      _builder.append("}");
      _builder.newLine();
      _builder.append("            ");
      _builder.append("body : ");
      _builder.newLine();
      _builder.append("            ");
      _builder.append("for(MyEntity e in list) {");
      _builder.newLine();
      _builder.append("            \t");
      _builder.append("Label(e.name);");
      _builder.newLine();
      _builder.append("            ");
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
      _builder.append("         ");
      _builder.append("}");
      _builder.newLine();
      _builder.append("}");
      _builder.newLine();
      _builder.newLine();
      final InformationSystem obj = this._parseHelper.parse(_builder, this.rs);
      this._validationTestHelper.assertNoErrors(obj);
      EList<NamedElement> _body = obj.getBody();
      NamedElement _head = IterableExtensions.<NamedElement>head(_body);
      co.edu.javeriana.isml.isml.Package _cast = this._ismlModelNavigation.<NamedElement, co.edu.javeriana.isml.isml.Package>cast(_head, co.edu.javeriana.isml.isml.Package.class);
      EList<NamedElement> _body_1 = _cast.getBody();
      NamedElement _get = _body_1.get(2);
      final Controller controller = this._ismlModelNavigation.<NamedElement, Controller>cast(_get, Controller.class);
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("Entity {");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("name = Person");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("extends = [Animal, Sentient]");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("body = {");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("Attribute {");
      _builder_1.newLine();
      _builder_1.append("\t\t\t");
      _builder_1.append("name = name");
      _builder_1.newLine();
      _builder_1.append("\t\t\t");
      _builder_1.append("type = String");
      _builder_1.newLine();
      _builder_1.append("\t\t\t");
      _builder_1.append("constraints = [Size]");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("Attribute {");
      _builder_1.newLine();
      _builder_1.append("\t\t\t");
      _builder_1.append("name = age");
      _builder_1.newLine();
      _builder_1.append("\t\t\t");
      _builder_1.append("type = Integer");
      _builder_1.newLine();
      _builder_1.append("\t\t\t");
      _builder_1.append("constraints = []");
      _builder_1.newLine();
      _builder_1.append("\t\t");
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.append("\t");
      _builder_1.append("}");
      _builder_1.newLine();
      _builder_1.append("}");
      _builder_1.newLine();
      this._testGeneratorHelper.<Controller>assertGenerates(this.template, controller, _builder_1);
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
