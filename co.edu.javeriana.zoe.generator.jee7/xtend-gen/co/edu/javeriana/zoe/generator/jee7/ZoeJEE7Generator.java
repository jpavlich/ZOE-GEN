package co.edu.javeriana.zoe.generator.jee7;

import co.edu.javeriana.isml.generator.common.GeneratorSuite;
import co.edu.javeriana.isml.generator.common.OutputConfiguration;
import co.edu.javeriana.isml.generator.common.SimpleGenerator;
import co.edu.javeriana.zoe.generator.jee7.generators.BackingBeanGenerator;
import co.edu.javeriana.zoe.generator.jee7.generators.FacesConfigXMLGenerator;
import co.edu.javeriana.zoe.generator.jee7.generators.PagesGenerator;
import co.edu.javeriana.zoe.generator.jee7.generators.QualifierGenerator;
import co.edu.javeriana.zoe.generator.jee7.generators.ServiceImplementationGenerator;
import co.edu.javeriana.zoe.generator.jee7.generators.ServiceInterfaceGenerator;
import java.util.Collections;
import java.util.Set;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

/**
 * Generates code from your model files on save.
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#TutorialCodeGeneration
 */
@SuppressWarnings("all")
public class ZoeJEE7Generator extends GeneratorSuite {
  @OutputConfiguration
  public final static String PAGES = "pages";
  
  @OutputConfiguration
  public final static String BACKING_BEANS = "backing.beans";
  
  @OutputConfiguration
  public final static String SERVICE_INTERFACE = "service.interface";
  
  @OutputConfiguration
  public final static String SERVICE_IMPL = "service.impl";
  
  @OutputConfiguration
  public final static String SERVICE_QUALIFIER = "service.qualifier";
  
  public Set<? extends SimpleGenerator<?>> getGenerators() {
    BackingBeanGenerator _backingBeanGenerator = new BackingBeanGenerator();
    PagesGenerator _pagesGenerator = new PagesGenerator();
    FacesConfigXMLGenerator _facesConfigXMLGenerator = new FacesConfigXMLGenerator();
    ServiceInterfaceGenerator _serviceInterfaceGenerator = new ServiceInterfaceGenerator();
    ServiceImplementationGenerator _serviceImplementationGenerator = new ServiceImplementationGenerator();
    QualifierGenerator _qualifierGenerator = new QualifierGenerator();
    return Collections.<SimpleGenerator<?>>unmodifiableSet(CollectionLiterals.<SimpleGenerator<?>>newHashSet(_backingBeanGenerator, _pagesGenerator, _facesConfigXMLGenerator, _serviceInterfaceGenerator, _serviceImplementationGenerator, _qualifierGenerator));
  }
}
