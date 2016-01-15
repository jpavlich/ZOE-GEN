package co.edu.javeriana.zoe.generator.persistence;

import co.edu.javeriana.isml.generator.common.GeneratorSuite;
import co.edu.javeriana.isml.generator.common.OutputConfiguration;
import co.edu.javeriana.isml.generator.common.SimpleGenerator;
import co.edu.javeriana.zoe.generator.persistence.generators.DTOGenerator;
import co.edu.javeriana.zoe.generator.persistence.generators.EntityGenerator;
import co.edu.javeriana.zoe.generator.persistence.generators.EnumGenerator;
import co.edu.javeriana.zoe.generator.persistence.generators.ResourceBundleGenerator;
import co.edu.javeriana.zoe.generator.persistence.generators.ServiceGeneralGenerator;
import java.util.Collections;
import java.util.Set;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

/**
 * Generates code from your model files on save.
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#TutorialCodeGeneration
 */
@SuppressWarnings("all")
public class ZoePersistenceGenerator extends GeneratorSuite {
  @OutputConfiguration
  public final static String ENTITIES = "entities";
  
  @OutputConfiguration
  public final static String RESOURCE_BUNDLE = "resource.bundle";
  
  @OutputConfiguration
  public final static String ENUM = "enum";
  
  @OutputConfiguration
  public final static String DTOS = "dto";
  
  @OutputConfiguration
  public final static String SERVICE_GENERAL = "service.general";
  
  public Set<? extends SimpleGenerator<?>> getGenerators() {
    EntityGenerator _entityGenerator = new EntityGenerator();
    ResourceBundleGenerator _resourceBundleGenerator = new ResourceBundleGenerator();
    EnumGenerator _enumGenerator = new EnumGenerator();
    DTOGenerator _dTOGenerator = new DTOGenerator();
    ServiceGeneralGenerator _serviceGeneralGenerator = new ServiceGeneralGenerator();
    return Collections.<SimpleGenerator<?>>unmodifiableSet(CollectionLiterals.<SimpleGenerator<?>>newHashSet(_entityGenerator, _resourceBundleGenerator, _enumGenerator, _dTOGenerator, _serviceGeneralGenerator));
  }
}
