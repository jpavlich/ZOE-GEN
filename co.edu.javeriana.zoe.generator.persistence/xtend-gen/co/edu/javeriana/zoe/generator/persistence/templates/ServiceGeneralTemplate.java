package co.edu.javeriana.zoe.generator.persistence.templates;

import co.edu.javeriana.isml.generator.common.SimpleTemplate;
import co.edu.javeriana.isml.isml.Entity;
import co.edu.javeriana.isml.isml.Feature;
import co.edu.javeriana.isml.isml.GenericTypeSpecification;
import co.edu.javeriana.isml.isml.Method;
import co.edu.javeriana.isml.isml.MethodStatement;
import co.edu.javeriana.isml.isml.Parameter;
import co.edu.javeriana.isml.isml.ParameterizedType;
import co.edu.javeriana.isml.isml.Primitive;
import co.edu.javeriana.isml.isml.Type;
import co.edu.javeriana.isml.isml.TypeSpecification;
import co.edu.javeriana.isml.scoping.IsmlModelNavigation;
import co.edu.javeriana.isml.validation.TypeChecker;
import com.google.common.base.Objects;
import com.google.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class ServiceGeneralTemplate extends SimpleTemplate<Entity> {
  @Inject
  @Extension
  private IQualifiedNameProvider _iQualifiedNameProvider;
  
  @Inject
  @Extension
  private IsmlModelNavigation _ismlModelNavigation;
  
  @Inject
  @Extension
  private TypeChecker _typeChecker;
  
  public void preprocess(final Entity service) {
  }
  
  /**
   * @«constraint.type.typeSpecification.typeSpecificationString»(«FOR Expression ex : constraint.parameters SEPARATOR ","»«ex.toString.length»«ENDFOR»)
   */
  public CharSequence template(final Entity entity) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    EObject _eContainer = entity.eContainer();
    QualifiedName _fullyQualifiedName = null;
    if (_eContainer!=null) {
      _fullyQualifiedName=this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer);
    }
    QualifiedName _lowerCase = _fullyQualifiedName.toLowerCase();
    _builder.append(_lowerCase, "");
    _builder.append(";\t\t");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      Map<QualifiedName, TypeSpecification> _neededImportsInMethods = this.getNeededImportsInMethods(entity);
      Set<Map.Entry<QualifiedName, TypeSpecification>> _entrySet = _neededImportsInMethods.entrySet();
      for(final Map.Entry<QualifiedName, TypeSpecification> entiti : _entrySet) {
        _builder.append("import ");
        TypeSpecification _value = entiti.getValue();
        QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(_value);
        _builder.append(_fullyQualifiedName_1, "");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.newLine();
    _builder.append("import java.util.*;");
    _builder.newLine();
    _builder.append("import javax.ejb.Local;");
    _builder.newLine();
    _builder.append("import javax.ejb.Stateless;");
    _builder.newLine();
    _builder.append("import javax.persistence.EntityManager;\t");
    _builder.newLine();
    _builder.append("import javax.persistence.PersistenceContext;");
    _builder.newLine();
    _builder.append("import ");
    EObject _eContainer_1 = entity.eContainer();
    QualifiedName _fullyQualifiedName_2 = null;
    if (_eContainer_1!=null) {
      _fullyQualifiedName_2=this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer_1);
    }
    QualifiedName _lowerCase_1 = _fullyQualifiedName_2.toLowerCase();
    _builder.append(_lowerCase_1, "");
    _builder.append(".AbstractFacade;");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("@Stateless");
    _builder.newLine();
    _builder.append("public class ");
    String _name = entity.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name);
    _builder.append(_firstUpper, "");
    _builder.append("General extends AbstractFacade<");
    String _name_1 = entity.getName();
    String _firstUpper_1 = StringExtensions.toFirstUpper(_name_1);
    _builder.append(_firstUpper_1, "");
    _builder.append(">{");
    _builder.newLineIfNotEmpty();
    _builder.append("\t    ");
    _builder.append("@PersistenceContext(unitName = \"co.edu.javeriana.javemovil_javemovil-web_war_1.0-SNAPSHOTPU\")");
    _builder.newLine();
    _builder.append("    \t\t\t");
    _builder.append("private EntityManager em;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("protected EntityManager getEntityManager() {");
    _builder.newLine();
    _builder.append("\t        ");
    _builder.append("return em;");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t    ");
    _builder.append("public ");
    String _name_2 = entity.getName();
    String _firstUpper_2 = StringExtensions.toFirstUpper(_name_2);
    _builder.append(_firstUpper_2, "\t    ");
    _builder.append("General() {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t        ");
    _builder.append("super(");
    String _name_3 = entity.getName();
    String _firstUpper_3 = StringExtensions.toFirstUpper(_name_3);
    _builder.append(_firstUpper_3, "\t        ");
    _builder.append(".class);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("}\t");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    return _builder;
  }
  
  public Map<QualifiedName, TypeSpecification> getNeededImportsInMethods(final TypeSpecification service) {
    Map<QualifiedName, TypeSpecification> imports = new HashMap<QualifiedName, TypeSpecification>();
    Iterable<Feature> _features = this._ismlModelNavigation.getFeatures(service);
    for (final Feature feature : _features) {
      {
        Type _type = feature.getType();
        boolean _isCollection = this._typeChecker.isCollection(_type);
        boolean _not = (!_isCollection);
        if (_not) {
          boolean _and = false;
          Type _type_1 = feature.getType();
          boolean _notEquals = (!Objects.equal(_type_1, null));
          if (!_notEquals) {
            _and = false;
          } else {
            Type _type_2 = feature.getType();
            TypeSpecification _typeSpecification = this._ismlModelNavigation.getTypeSpecification(_type_2);
            EObject _eContainer = _typeSpecification.eContainer();
            QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer);
            EObject _eContainer_1 = service.eContainer();
            QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer_1);
            boolean _equals = _fullyQualifiedName.equals(_fullyQualifiedName_1);
            boolean _not_1 = (!_equals);
            _and = _not_1;
          }
          if (_and) {
            Type _type_3 = feature.getType();
            TypeSpecification _typeSpecification_1 = this._ismlModelNavigation.getTypeSpecification(_type_3);
            boolean _not_2 = (!(_typeSpecification_1 instanceof Primitive));
            if (_not_2) {
              Type _type_4 = feature.getType();
              TypeSpecification _typeSpecification_2 = this._ismlModelNavigation.getTypeSpecification(_type_4);
              boolean _not_3 = (!(_typeSpecification_2 instanceof GenericTypeSpecification));
              if (_not_3) {
                Type _type_5 = feature.getType();
                TypeSpecification _typeSpecification_3 = this._ismlModelNavigation.getTypeSpecification(_type_5);
                QualifiedName _fullyQualifiedName_2 = this._iQualifiedNameProvider.getFullyQualifiedName(_typeSpecification_3);
                boolean _containsKey = imports.containsKey(_fullyQualifiedName_2);
                boolean _not_4 = (!_containsKey);
                if (_not_4) {
                  Type _type_6 = feature.getType();
                  TypeSpecification _typeSpecification_4 = this._ismlModelNavigation.getTypeSpecification(_type_6);
                  QualifiedName _fullyQualifiedName_3 = this._iQualifiedNameProvider.getFullyQualifiedName(_typeSpecification_4);
                  Type _type_7 = feature.getType();
                  TypeSpecification _typeSpecification_5 = this._ismlModelNavigation.getTypeSpecification(_type_7);
                  imports.put(_fullyQualifiedName_3, _typeSpecification_5);
                }
              }
            }
          }
        } else {
          Type _type_8 = feature.getType();
          if ((_type_8 instanceof ParameterizedType)) {
            boolean _and_1 = false;
            Type _type_9 = feature.getType();
            boolean _notEquals_1 = (!Objects.equal(_type_9, null));
            if (!_notEquals_1) {
              _and_1 = false;
            } else {
              Type _type_10 = feature.getType();
              EList<Type> _typeParameters = ((ParameterizedType) _type_10).getTypeParameters();
              Type _get = _typeParameters.get(0);
              TypeSpecification _typeSpecification_6 = this._ismlModelNavigation.getTypeSpecification(_get);
              EObject _eContainer_2 = _typeSpecification_6.eContainer();
              QualifiedName _fullyQualifiedName_4 = this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer_2);
              EObject _eContainer_3 = service.eContainer();
              QualifiedName _fullyQualifiedName_5 = this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer_3);
              boolean _equals_1 = _fullyQualifiedName_4.equals(_fullyQualifiedName_5);
              boolean _not_5 = (!_equals_1);
              _and_1 = _not_5;
            }
            if (_and_1) {
              Type _type_11 = feature.getType();
              EList<Type> _typeParameters_1 = ((ParameterizedType) _type_11).getTypeParameters();
              Type _get_1 = _typeParameters_1.get(0);
              TypeSpecification _typeSpecification_7 = this._ismlModelNavigation.getTypeSpecification(_get_1);
              boolean _not_6 = (!(_typeSpecification_7 instanceof Primitive));
              if (_not_6) {
                Type _type_12 = feature.getType();
                EList<Type> _typeParameters_2 = ((ParameterizedType) _type_12).getTypeParameters();
                Type _get_2 = _typeParameters_2.get(0);
                TypeSpecification _typeSpecification_8 = this._ismlModelNavigation.getTypeSpecification(_get_2);
                boolean _not_7 = (!(_typeSpecification_8 instanceof GenericTypeSpecification));
                if (_not_7) {
                  Type _type_13 = feature.getType();
                  EList<Type> _typeParameters_3 = ((ParameterizedType) _type_13).getTypeParameters();
                  Type _get_3 = _typeParameters_3.get(0);
                  TypeSpecification _typeSpecification_9 = this._ismlModelNavigation.getTypeSpecification(_get_3);
                  QualifiedName _fullyQualifiedName_6 = this._iQualifiedNameProvider.getFullyQualifiedName(_typeSpecification_9);
                  boolean _containsKey_1 = imports.containsKey(_fullyQualifiedName_6);
                  boolean _not_8 = (!_containsKey_1);
                  if (_not_8) {
                    Type _type_14 = feature.getType();
                    EList<Type> _typeParameters_4 = ((ParameterizedType) _type_14).getTypeParameters();
                    Type _get_4 = _typeParameters_4.get(0);
                    TypeSpecification _typeSpecification_10 = this._ismlModelNavigation.getTypeSpecification(_get_4);
                    QualifiedName _fullyQualifiedName_7 = this._iQualifiedNameProvider.getFullyQualifiedName(_typeSpecification_10);
                    Type _type_15 = feature.getType();
                    EList<Type> _typeParameters_5 = ((ParameterizedType) _type_15).getTypeParameters();
                    Type _get_5 = _typeParameters_5.get(0);
                    TypeSpecification _typeSpecification_11 = this._ismlModelNavigation.getTypeSpecification(_get_5);
                    imports.put(_fullyQualifiedName_7, _typeSpecification_11);
                  }
                }
              }
            }
          }
        }
        if ((feature instanceof Method)) {
          EList<Parameter> _parameters = ((Method)feature).getParameters();
          for (final Parameter param : _parameters) {
            Type _type_16 = param.getType();
            boolean _isCollection_1 = this._typeChecker.isCollection(_type_16);
            boolean _not_9 = (!_isCollection_1);
            if (_not_9) {
              Type _type_17 = param.getType();
              TypeSpecification _typeSpecification_12 = this._ismlModelNavigation.getTypeSpecification(_type_17);
              EObject _eContainer_4 = _typeSpecification_12.eContainer();
              QualifiedName _fullyQualifiedName_8 = this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer_4);
              EObject _eContainer_5 = service.eContainer();
              QualifiedName _fullyQualifiedName_9 = this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer_5);
              boolean _equals_2 = _fullyQualifiedName_8.equals(_fullyQualifiedName_9);
              boolean _not_10 = (!_equals_2);
              if (_not_10) {
                Type _type_18 = param.getType();
                TypeSpecification _typeSpecification_13 = this._ismlModelNavigation.getTypeSpecification(_type_18);
                boolean _not_11 = (!(_typeSpecification_13 instanceof Primitive));
                if (_not_11) {
                  Type _type_19 = param.getType();
                  TypeSpecification _typeSpecification_14 = this._ismlModelNavigation.getTypeSpecification(_type_19);
                  boolean _not_12 = (!(_typeSpecification_14 instanceof GenericTypeSpecification));
                  if (_not_12) {
                    Type _type_20 = param.getType();
                    TypeSpecification _typeSpecification_15 = this._ismlModelNavigation.getTypeSpecification(_type_20);
                    QualifiedName _fullyQualifiedName_10 = this._iQualifiedNameProvider.getFullyQualifiedName(_typeSpecification_15);
                    boolean _containsKey_2 = imports.containsKey(_fullyQualifiedName_10);
                    boolean _not_13 = (!_containsKey_2);
                    if (_not_13) {
                      Type _type_21 = param.getType();
                      TypeSpecification _typeSpecification_16 = this._ismlModelNavigation.getTypeSpecification(_type_21);
                      QualifiedName _fullyQualifiedName_11 = this._iQualifiedNameProvider.getFullyQualifiedName(_typeSpecification_16);
                      Type _type_22 = param.getType();
                      TypeSpecification _typeSpecification_17 = this._ismlModelNavigation.getTypeSpecification(_type_22);
                      imports.put(_fullyQualifiedName_11, _typeSpecification_17);
                    }
                  }
                }
              }
            } else {
              Type _type_23 = param.getType();
              if ((_type_23 instanceof ParameterizedType)) {
                Type _type_24 = param.getType();
                EList<Type> _typeParameters_6 = ((ParameterizedType) _type_24).getTypeParameters();
                Type _get_6 = _typeParameters_6.get(0);
                TypeSpecification _typeSpecification_18 = this._ismlModelNavigation.getTypeSpecification(_get_6);
                EObject _eContainer_6 = _typeSpecification_18.eContainer();
                QualifiedName _fullyQualifiedName_12 = this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer_6);
                EObject _eContainer_7 = service.eContainer();
                QualifiedName _fullyQualifiedName_13 = this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer_7);
                boolean _equals_3 = _fullyQualifiedName_12.equals(_fullyQualifiedName_13);
                boolean _not_14 = (!_equals_3);
                if (_not_14) {
                  Type _type_25 = param.getType();
                  EList<Type> _typeParameters_7 = ((ParameterizedType) _type_25).getTypeParameters();
                  Type _get_7 = _typeParameters_7.get(0);
                  TypeSpecification _typeSpecification_19 = this._ismlModelNavigation.getTypeSpecification(_get_7);
                  boolean _not_15 = (!(_typeSpecification_19 instanceof Primitive));
                  if (_not_15) {
                    Type _type_26 = param.getType();
                    EList<Type> _typeParameters_8 = ((ParameterizedType) _type_26).getTypeParameters();
                    Type _get_8 = _typeParameters_8.get(0);
                    TypeSpecification _typeSpecification_20 = this._ismlModelNavigation.getTypeSpecification(_get_8);
                    boolean _not_16 = (!(_typeSpecification_20 instanceof GenericTypeSpecification));
                    if (_not_16) {
                      Type _type_27 = param.getType();
                      EList<Type> _typeParameters_9 = ((ParameterizedType) _type_27).getTypeParameters();
                      Type _get_9 = _typeParameters_9.get(0);
                      TypeSpecification _typeSpecification_21 = this._ismlModelNavigation.getTypeSpecification(_get_9);
                      QualifiedName _fullyQualifiedName_14 = this._iQualifiedNameProvider.getFullyQualifiedName(_typeSpecification_21);
                      boolean _containsKey_3 = imports.containsKey(_fullyQualifiedName_14);
                      boolean _not_17 = (!_containsKey_3);
                      if (_not_17) {
                        Type _type_28 = param.getType();
                        EList<Type> _typeParameters_10 = ((ParameterizedType) _type_28).getTypeParameters();
                        Type _get_10 = _typeParameters_10.get(0);
                        TypeSpecification _typeSpecification_22 = this._ismlModelNavigation.getTypeSpecification(_get_10);
                        QualifiedName _fullyQualifiedName_15 = this._iQualifiedNameProvider.getFullyQualifiedName(_typeSpecification_22);
                        Type _type_29 = param.getType();
                        EList<Type> _typeParameters_11 = ((ParameterizedType) _type_29).getTypeParameters();
                        Type _get_11 = _typeParameters_11.get(0);
                        TypeSpecification _typeSpecification_23 = this._ismlModelNavigation.getTypeSpecification(_get_11);
                        imports.put(_fullyQualifiedName_15, 
                          ((Entity) _typeSpecification_23));
                      }
                    }
                  }
                }
              }
            }
          }
          EList<MethodStatement> _body = ((Method)feature).getBody();
          boolean _notEquals_2 = (!Objects.equal(_body, null));
          if (_notEquals_2) {
            EList<MethodStatement> _body_1 = ((Method)feature).getBody();
            for (final MethodStatement stmnt : _body_1) {
              TreeIterator<EObject> _eAllContents = stmnt.eAllContents();
              List<EObject> _list = IteratorExtensions.<EObject>toList(_eAllContents);
              this._ismlModelNavigation.isNeededImportInBody(_list, imports, service);
            }
          }
        }
      }
    }
    return imports;
  }
}
