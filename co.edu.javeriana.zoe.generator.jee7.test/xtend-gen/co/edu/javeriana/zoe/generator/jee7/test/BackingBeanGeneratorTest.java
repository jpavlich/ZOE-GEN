package co.edu.javeriana.zoe.generator.jee7.test;

import co.edu.javeriana.isml.IsmlInjectorProvider;
import co.edu.javeriana.isml.isml.InformationSystem;
import co.edu.javeriana.isml.scoping.IsmlModelNavigation;
import co.edu.javeriana.isml.tests.CommonTests;
import co.edu.javeriana.zoe.generator.jee7.templates.ZoeControllerTemplate;
import com.google.inject.Inject;
import org.eclipse.xtext.junit4.InjectWith;
import org.eclipse.xtext.junit4.XtextRunner;
import org.eclipse.xtext.junit4.util.ParseHelper;
import org.eclipse.xtext.junit4.validation.ValidationTestHelper;
import org.eclipse.xtext.xbase.lib.Extension;
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
  private /* TestGeneratorHelper */Object _testGeneratorHelper;
  
  @Inject
  @Extension
  private IsmlModelNavigation _ismlModelNavigation;
  
  @Inject
  private ZoeControllerTemplate template;
  
  @Test
  public Object dataTableGeneration() {
    throw new Error("Unresolved compilation problems:"
      + "\nThe method assertGenerates is undefined for the type BackingBeanGeneratorTest");
  }
}
