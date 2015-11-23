package co.edu.javeriana.zoe.generator.persistence.generators;

import co.edu.javeriana.isml.generator.common.SimpleGenerator;
import co.edu.javeriana.isml.generator.common.SimpleTemplate;
import co.edu.javeriana.isml.isml.Entity;
import co.edu.javeriana.zoe.generator.persistence.ZoePersistenceGenerator;
import co.edu.javeriana.zoe.generator.persistence.templates.ServiceGeneralTemplate;
import com.google.inject.Inject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class ServiceGeneralGenerator extends SimpleGenerator<Entity> {
  @Inject
  @Extension
  private IQualifiedNameProvider _iQualifiedNameProvider;
  
  public String getOutputConfigurationName() {
    return ZoePersistenceGenerator.SERVICE_GENERAL;
  }
  
  protected String getFileName(final Entity e) {
    EObject _eContainer = e.eContainer();
    QualifiedName _fullyQualifiedName = null;
    if (_eContainer!=null) {
      _fullyQualifiedName=this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer);
    }
    String _string = _fullyQualifiedName.toString("/");
    String _lowerCase = _string.toLowerCase();
    String _plus = (_lowerCase + "/");
    String _name = e.getName();
    String _plus_1 = (_plus + _name);
    String _plus_2 = (_plus_1 + "General");
    return (_plus_2 + ".java");
  }
  
  public SimpleTemplate<Entity> getTemplate() {
    return new ServiceGeneralTemplate();
  }
}
