package co.edu.javeriana.zoe.generator.persistence.templates;

import co.edu.javeriana.isml.generator.common.SimpleTemplate;
import co.edu.javeriana.isml.isml.Attribute;
import co.edu.javeriana.isml.isml.Constraint;
import co.edu.javeriana.isml.isml.ConstraintInstance;
import co.edu.javeriana.isml.isml.Entity;
import co.edu.javeriana.isml.isml.Expression;
import co.edu.javeriana.isml.isml.Instance;
import co.edu.javeriana.isml.isml.LiteralValue;
import co.edu.javeriana.isml.isml.Parameter;
import co.edu.javeriana.isml.isml.ParameterizedType;
import co.edu.javeriana.isml.isml.Primitive;
import co.edu.javeriana.isml.isml.Type;
import co.edu.javeriana.isml.isml.TypeSpecification;
import co.edu.javeriana.isml.scoping.IsmlModelNavigation;
import co.edu.javeriana.isml.validation.TypeChecker;
import com.google.inject.Inject;
import java.util.Collection;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class ZoeEntityTemplate extends SimpleTemplate<Entity> {
  @Inject
  @Extension
  private TypeChecker _typeChecker;
  
  /**
   * Inyección de las clases auxiliares con metodos utilitarios
   */
  @Inject
  @Extension
  private IQualifiedNameProvider _iQualifiedNameProvider;
  
  @Inject
  @Extension
  private IsmlModelNavigation _ismlModelNavigation;
  
  /**
   * Metodo callback llamado previo a la ejecución del template
   */
  public void preprocess(final Entity e) {
  }
  
  /**
   * Plantilla para generar entidades
   */
  public CharSequence template(final Entity entity) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\t");
    _builder.append("package ");
    EObject _eContainer = entity.eContainer();
    QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer);
    _builder.append(_fullyQualifiedName, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("import javax.persistence.*;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("import javax.validation.constraints.*;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("import java.io.Serializable;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("import javax.xml.bind.annotation.XmlRootElement;");
    _builder.newLine();
    {
      boolean _isDatePresent = this.isDatePresent(entity);
      if (_isDatePresent) {
        _builder.append("\t");
        _builder.append("import java.util.Date;");
        _builder.newLine();
      }
    }
    _builder.append("\t\t\t");
    _builder.newLine();
    {
      Collection<Type> _parents = this._ismlModelNavigation.getParents(entity);
      for(final Type parent : _parents) {
        {
          EObject _eContainer_1 = parent.eContainer();
          EObject _eContainer_2 = _eContainer_1.eContainer();
          QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer_2);
          EObject _eContainer_3 = entity.eContainer();
          QualifiedName _fullyQualifiedName_2 = this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer_3);
          boolean _equals = _fullyQualifiedName_1.equals(_fullyQualifiedName_2);
          boolean _not = (!_equals);
          if (_not) {
            _builder.append("\t");
            _builder.append("import ");
            TypeSpecification _typeSpecification = this._ismlModelNavigation.getTypeSpecification(parent);
            QualifiedName _fullyQualifiedName_3 = this._iQualifiedNameProvider.getFullyQualifiedName(_typeSpecification);
            _builder.append(_fullyQualifiedName_3, "\t");
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.newLine();
    {
      Iterable<Attribute> _attributes = this._ismlModelNavigation.getAttributes(entity);
      for(final Attribute attribute : _attributes) {
        {
          boolean _or = false;
          Type _type = attribute.getType();
          TypeSpecification _typeSpecification_1 = this._ismlModelNavigation.getTypeSpecification(_type);
          boolean _not_1 = (!(_typeSpecification_1 instanceof Primitive));
          if (_not_1) {
            _or = true;
          } else {
            Type _type_1 = attribute.getType();
            boolean _isCollection = this._typeChecker.isCollection(_type_1);
            _or = _isCollection;
          }
          if (_or) {
            {
              boolean _evaluateAttributeToImport = this.evaluateAttributeToImport(attribute, entity);
              if (_evaluateAttributeToImport) {
                {
                  Type _type_2 = attribute.getType();
                  if ((_type_2 instanceof ParameterizedType)) {
                    {
                      Type _type_3 = attribute.getType();
                      EList<Type> _typeParameters = ((ParameterizedType) _type_3).getTypeParameters();
                      Type _get = _typeParameters.get(0);
                      TypeSpecification _typeSpecification_2 = this._ismlModelNavigation.getTypeSpecification(_get);
                      boolean _not_2 = (!(_typeSpecification_2 instanceof Primitive));
                      if (_not_2) {
                        _builder.append("\t");
                        _builder.append("import ");
                        Type _type_4 = attribute.getType();
                        EList<Type> _typeParameters_1 = ((ParameterizedType) _type_4).getTypeParameters();
                        Type _get_1 = _typeParameters_1.get(0);
                        TypeSpecification _typeSpecification_3 = this._ismlModelNavigation.getTypeSpecification(_get_1);
                        QualifiedName _fullyQualifiedName_4 = this._iQualifiedNameProvider.getFullyQualifiedName(_typeSpecification_3);
                        _builder.append(_fullyQualifiedName_4, "\t");
                        _builder.append(";");
                        _builder.newLineIfNotEmpty();
                      }
                    }
                  } else {
                    _builder.append("\t");
                    _builder.append("import ");
                    Type _type_5 = attribute.getType();
                    TypeSpecification _typeSpecification_4 = this._ismlModelNavigation.getTypeSpecification(_type_5);
                    QualifiedName _fullyQualifiedName_5 = this._iQualifiedNameProvider.getFullyQualifiedName(_typeSpecification_4);
                    _builder.append(_fullyQualifiedName_5, "\t");
                    _builder.append(";");
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
          }
        }
      }
    }
    _builder.append("\t\t\t");
    _builder.newLine();
    {
      Iterable<Attribute> _attributes_1 = this._ismlModelNavigation.getAttributes(entity);
      boolean _isArrayPresent = this._ismlModelNavigation.isArrayPresent(_attributes_1);
      if (_isArrayPresent) {
        _builder.append("\t");
        _builder.append("import java.util.*;");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    {
      boolean _isParent = this._ismlModelNavigation.isParent(entity);
      if (_isParent) {
        _builder.append("\t");
        _builder.append("import javax.persistence.Inheritance;");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("import javax.persistence.InheritanceType;");
        _builder.newLine();
      }
    }
    _builder.append("\t\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@XmlRootElement");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Entity");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    {
      boolean _isParent_1 = this._ismlModelNavigation.isParent(entity);
      if (_isParent_1) {
        _builder.append("\t");
        _builder.append("@Inheritance(strategy=InheritanceType.JOINED)");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("@DiscriminatorColumn(name=\"");
        String _name = entity.getName();
        String _upperCase = _name.toUpperCase();
        _builder.append(_upperCase, "\t");
        _builder.append("_TYPE\", discriminatorType=DiscriminatorType.STRING)");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    {
      boolean _isSon = this._ismlModelNavigation.isSon(entity);
      if (_isSon) {
        _builder.append("\t");
        _builder.append("@DiscriminatorValue(value=\"");
        String _name_1 = entity.getName();
        String _upperCase_1 = _name_1.toUpperCase();
        _builder.append(_upperCase_1, "\t");
        _builder.append("\")");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public ");
    {
      boolean _isAbstract = entity.isAbstract();
      if (_isAbstract) {
        _builder.append("abstract");
      }
    }
    _builder.append(" class ");
    String _name_2 = entity.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name_2);
    _builder.append(_firstUpper, "\t");
    _builder.append(" ");
    {
      EList<Type> _superTypes = entity.getSuperTypes();
      boolean _isEmpty = _superTypes.isEmpty();
      boolean _not_3 = (!_isEmpty);
      if (_not_3) {
        _builder.append("extends ");
        EList<Type> _superTypes_1 = entity.getSuperTypes();
        Type _get_2 = _superTypes_1.get(0);
        TypeSpecification _typeSpecification_5 = this._ismlModelNavigation.getTypeSpecification(_get_2);
        String _typeSpecificationString = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_5);
        _builder.append(_typeSpecificationString, "\t");
        _builder.append(" ");
      }
    }
    _builder.append("implements Serializable {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("/**");
    _builder.newLine();
    _builder.append("\t\t ");
    _builder.append("* The serialVersionUID");
    _builder.newLine();
    _builder.append("\t\t ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("private static final long serialVersionUID = 1L;");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    {
      Collection<Type> _parents_1 = this._ismlModelNavigation.getParents(entity);
      boolean _isEmpty_1 = _parents_1.isEmpty();
      if (_isEmpty_1) {
        _builder.append("\t\t");
        _builder.append("/**");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append(" ");
        _builder.append("* The unique id for the entity");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append(" ");
        _builder.append("*/\t\t");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("private Long id = null;");
        _builder.newLine();
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    {
      Iterable<Attribute> _attributes_2 = this._ismlModelNavigation.getAttributes(entity);
      for(final Attribute attributes : _attributes_2) {
        _builder.append("\t\t");
        _builder.append("\t\t\t\t\t");
        _builder.newLine();
        {
          EList<ConstraintInstance> _constraints = attributes.getConstraints();
          for(final ConstraintInstance constraint : _constraints) {
            _builder.append("\t\t");
            _builder.append("@");
            Type _type_6 = constraint.getType();
            TypeSpecification _typeSpecification_6 = this._ismlModelNavigation.getTypeSpecification(_type_6);
            String _typeSpecificationString_1 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_6);
            String _firstUpper_1 = StringExtensions.toFirstUpper(_typeSpecificationString_1);
            _builder.append(_firstUpper_1, "\t\t");
            CharSequence _parametersTemplate = this.getParametersTemplate(constraint);
            _builder.append(_parametersTemplate, "\t\t");
            _builder.newLineIfNotEmpty();
          }
        }
        {
          Type _type_7 = attributes.getType();
          TypeSpecification _typeSpecification_7 = this._ismlModelNavigation.getTypeSpecification(_type_7);
          String _typeSpecificationString_2 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_7);
          boolean _equalsIgnoreCase = _typeSpecificationString_2.equalsIgnoreCase("email");
          if (_equalsIgnoreCase) {
            _builder.append("\t\t");
            _builder.append("/**");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("* The ");
            String _name_3 = attributes.getName();
            _builder.append(_name_3, "\t\t ");
            _builder.append(" for the ");
            String _name_4 = entity.getName();
            String _firstUpper_2 = StringExtensions.toFirstUpper(_name_4);
            _builder.append(_firstUpper_2, "\t\t ");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("*/");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("private String ");
            String _name_5 = attributes.getName();
            _builder.append(_name_5, "\t\t");
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          } else {
            {
              Type _type_8 = attributes.getType();
              boolean _isCollection_1 = this._typeChecker.isCollection(_type_8);
              if (_isCollection_1) {
                _builder.append("\t\t");
                _builder.append("/**");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append(" ");
                _builder.append("* The ");
                String _name_6 = attributes.getName();
                _builder.append(_name_6, "\t\t ");
                _builder.append(" for the ");
                String _name_7 = entity.getName();
                String _firstUpper_3 = StringExtensions.toFirstUpper(_name_7);
                _builder.append(_firstUpper_3, "\t\t ");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append(" ");
                _builder.append("*/");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append("private ");
                Type _type_9 = attributes.getType();
                String _collectionString = this._ismlModelNavigation.getCollectionString(((ParameterizedType) _type_9));
                _builder.append(_collectionString, "\t\t");
                _builder.append("<");
                Type _type_10 = attributes.getType();
                EList<Type> _typeParameters_2 = ((ParameterizedType) _type_10).getTypeParameters();
                Type _get_3 = _typeParameters_2.get(0);
                TypeSpecification _typeSpecification_8 = this._ismlModelNavigation.getTypeSpecification(_get_3);
                String _typeSpecificationString_3 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_8);
                _builder.append(_typeSpecificationString_3, "\t\t");
                _builder.append("> ");
                String _name_8 = attributes.getName();
                _builder.append(_name_8, "\t\t");
                _builder.append(";");
                _builder.newLineIfNotEmpty();
              } else {
                _builder.append("\t\t");
                _builder.append("/**");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append(" ");
                _builder.append("* The ");
                String _name_9 = attributes.getName();
                _builder.append(_name_9, "\t\t ");
                _builder.append(" for the ");
                String _name_10 = entity.getName();
                String _firstUpper_4 = StringExtensions.toFirstUpper(_name_10);
                _builder.append(_firstUpper_4, "\t\t ");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append(" ");
                _builder.append("*/");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append("private ");
                Type _type_11 = attributes.getType();
                TypeSpecification _typeSpecification_9 = this._ismlModelNavigation.getTypeSpecification(_type_11);
                String _typeSpecificationString_4 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_9);
                String _firstUpper_5 = StringExtensions.toFirstUpper(_typeSpecificationString_4);
                _builder.append(_firstUpper_5, "\t\t");
                _builder.append(" ");
                String _name_11 = attributes.getName();
                _builder.append(_name_11, "\t\t");
                _builder.append(";");
                _builder.newLineIfNotEmpty();
              }
            }
          }
        }
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("public ");
    String _name_12 = entity.getName();
    String _firstUpper_6 = StringExtensions.toFirstUpper(_name_12);
    _builder.append(_firstUpper_6, "\t\t");
    _builder.append("(){");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("public ");
    String _name_13 = entity.getName();
    String _firstUpper_7 = StringExtensions.toFirstUpper(_name_13);
    _builder.append(_firstUpper_7, "\t\t");
    _builder.append("(");
    {
      Iterable<Attribute> _attributes_3 = this._ismlModelNavigation.getAttributes(entity);
      boolean _hasElements = false;
      for(final Attribute a : _attributes_3) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(",", "\t\t");
        }
        {
          Type _type_12 = a.getType();
          TypeSpecification _typeSpecification_10 = this._ismlModelNavigation.getTypeSpecification(_type_12);
          String _typeSpecificationString_5 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_10);
          boolean _equalsIgnoreCase_1 = _typeSpecificationString_5.equalsIgnoreCase("email");
          if (_equalsIgnoreCase_1) {
            _builder.append("String ");
            String _name_14 = a.getName();
            _builder.append(_name_14, "\t\t");
          } else {
            {
              Type _type_13 = a.getType();
              boolean _isCollection_2 = this._typeChecker.isCollection(_type_13);
              if (_isCollection_2) {
                Type _type_14 = a.getType();
                String _collectionString_1 = this._ismlModelNavigation.getCollectionString(((ParameterizedType) _type_14));
                _builder.append(_collectionString_1, "\t\t");
                _builder.append("<");
                Type _type_15 = a.getType();
                EList<Type> _typeParameters_3 = ((ParameterizedType) _type_15).getTypeParameters();
                Type _get_4 = _typeParameters_3.get(0);
                TypeSpecification _typeSpecification_11 = this._ismlModelNavigation.getTypeSpecification(_get_4);
                String _typeSpecificationString_6 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_11);
                _builder.append(_typeSpecificationString_6, "\t\t");
                _builder.append("> ");
                String _name_15 = a.getName();
                _builder.append(_name_15, "\t\t");
              } else {
                Type _type_16 = a.getType();
                TypeSpecification _typeSpecification_12 = this._ismlModelNavigation.getTypeSpecification(_type_16);
                String _typeSpecificationString_7 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_12);
                String _firstUpper_8 = StringExtensions.toFirstUpper(_typeSpecificationString_7);
                _builder.append(_firstUpper_8, "\t\t");
                _builder.append(" ");
                String _name_16 = a.getName();
                _builder.append(_name_16, "\t\t");
              }
            }
          }
        }
      }
    }
    _builder.append("){");
    _builder.newLineIfNotEmpty();
    {
      Iterable<Attribute> _attributes_4 = this._ismlModelNavigation.getAttributes(entity);
      for(final Attribute attr : _attributes_4) {
        _builder.append("\t\t\t");
        _builder.append("set");
        String _name_17 = attr.getName();
        String _firstUpper_9 = StringExtensions.toFirstUpper(_name_17);
        _builder.append(_firstUpper_9, "\t\t\t");
        _builder.append("(");
        String _name_18 = attr.getName();
        String _firstLower = StringExtensions.toFirstLower(_name_18);
        _builder.append(_firstLower, "\t\t\t");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    {
      Iterable<Attribute> _attributes_5 = this._ismlModelNavigation.getAttributes(entity);
      for(final Attribute attribute_1 : _attributes_5) {
        _builder.append("\t\t");
        CharSequence _associationAnnotation = this._ismlModelNavigation.associationAnnotation(attribute_1);
        _builder.append(_associationAnnotation, "\t\t");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        _builder.append("\t\t");
        _builder.newLine();
        {
          Type _type_17 = attribute_1.getType();
          TypeSpecification _typeSpecification_13 = this._ismlModelNavigation.getTypeSpecification(_type_17);
          String _typeSpecificationString_8 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_13);
          boolean _equalsIgnoreCase_2 = _typeSpecificationString_8.equalsIgnoreCase("email");
          if (_equalsIgnoreCase_2) {
            _builder.append("\t\t");
            _builder.append("/**");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("* Returns the current value for the attibute ");
            String _name_19 = attribute_1.getName();
            _builder.append(_name_19, "\t\t ");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("*");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("* @return current instance for ");
            String _name_20 = attribute_1.getName();
            String _firstLower_1 = StringExtensions.toFirstLower(_name_20);
            _builder.append(_firstLower_1, "\t\t ");
            _builder.append(" attribute");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("*/\t\t\t");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("@Pattern(regexp=\"^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$\")");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("public String get");
            String _name_21 = attribute_1.getName();
            String _firstUpper_10 = StringExtensions.toFirstUpper(_name_21);
            _builder.append(_firstUpper_10, "\t\t");
            _builder.append("(){");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("return ");
            String _name_22 = attribute_1.getName();
            _builder.append(_name_22, "\t\t\t");
            _builder.append(";");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("}");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("/**");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("* Sets the value for the attribute ");
            String _name_23 = attribute_1.getName();
            _builder.append(_name_23, "\t\t ");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("* @param ");
            String _name_24 = attribute_1.getName();
            String _firstLower_2 = StringExtensions.toFirstLower(_name_24);
            _builder.append(_firstLower_2, "\t\t ");
            _builder.append(" The value to set");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("*/");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("public void set");
            String _name_25 = attribute_1.getName();
            String _firstUpper_11 = StringExtensions.toFirstUpper(_name_25);
            _builder.append(_firstUpper_11, "\t\t");
            _builder.append("(String ");
            String _name_26 = attribute_1.getName();
            _builder.append(_name_26, "\t\t");
            _builder.append("){");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("this.");
            String _name_27 = attribute_1.getName();
            _builder.append(_name_27, "\t\t\t");
            _builder.append("=");
            String _name_28 = attribute_1.getName();
            _builder.append(_name_28, "\t\t\t");
            _builder.append(";");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("}\t\t\t\t");
            _builder.newLine();
          } else {
            _builder.append("\t\t");
            _builder.newLine();
            {
              Type _type_18 = attribute_1.getType();
              boolean _isCollection_3 = this._typeChecker.isCollection(_type_18);
              if (_isCollection_3) {
                _builder.append("\t\t");
                _builder.append("/**");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append(" ");
                _builder.append("* Returns the current value for the attibute ");
                String _name_29 = attribute_1.getName();
                _builder.append(_name_29, "\t\t ");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append(" ");
                _builder.append("*");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append(" ");
                _builder.append("* @return current instance for ");
                String _name_30 = attribute_1.getName();
                String _firstLower_3 = StringExtensions.toFirstLower(_name_30);
                _builder.append(_firstLower_3, "\t\t ");
                _builder.append(" attribute");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append(" ");
                _builder.append("*/");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append("public ");
                Type _type_19 = attribute_1.getType();
                String _collectionString_2 = this._ismlModelNavigation.getCollectionString(((ParameterizedType) _type_19));
                _builder.append(_collectionString_2, "\t\t");
                _builder.append("<");
                Type _type_20 = attribute_1.getType();
                EList<Type> _typeParameters_4 = ((ParameterizedType) _type_20).getTypeParameters();
                Type _get_5 = _typeParameters_4.get(0);
                TypeSpecification _typeSpecification_14 = this._ismlModelNavigation.getTypeSpecification(_get_5);
                String _typeSpecificationString_9 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_14);
                _builder.append(_typeSpecificationString_9, "\t\t");
                _builder.append("> get");
                String _name_31 = attribute_1.getName();
                String _firstUpper_12 = StringExtensions.toFirstUpper(_name_31);
                _builder.append(_firstUpper_12, "\t\t");
                _builder.append("(){");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("return ");
                String _name_32 = attribute_1.getName();
                _builder.append(_name_32, "\t\t\t");
                _builder.append(";");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append("}");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append("/**");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append(" ");
                _builder.append("* Sets the value for the attribute ");
                String _name_33 = attribute_1.getName();
                _builder.append(_name_33, "\t\t ");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append(" ");
                _builder.append("* @param ");
                String _name_34 = attribute_1.getName();
                String _firstLower_4 = StringExtensions.toFirstLower(_name_34);
                _builder.append(_firstLower_4, "\t\t ");
                _builder.append(" The value to set");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append(" ");
                _builder.append("*/");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append("public void set");
                String _name_35 = attribute_1.getName();
                String _firstUpper_13 = StringExtensions.toFirstUpper(_name_35);
                _builder.append(_firstUpper_13, "\t\t");
                _builder.append("(");
                Type _type_21 = attribute_1.getType();
                String _collectionString_3 = this._ismlModelNavigation.getCollectionString(((ParameterizedType) _type_21));
                _builder.append(_collectionString_3, "\t\t");
                _builder.append("<");
                Type _type_22 = attribute_1.getType();
                EList<Type> _typeParameters_5 = ((ParameterizedType) _type_22).getTypeParameters();
                Type _get_6 = _typeParameters_5.get(0);
                TypeSpecification _typeSpecification_15 = this._ismlModelNavigation.getTypeSpecification(_get_6);
                String _typeSpecificationString_10 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_15);
                _builder.append(_typeSpecificationString_10, "\t\t");
                _builder.append("> ");
                String _name_36 = attribute_1.getName();
                _builder.append(_name_36, "\t\t");
                _builder.append("){");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("this.");
                String _name_37 = attribute_1.getName();
                _builder.append(_name_37, "\t\t\t");
                _builder.append("=");
                String _name_38 = attribute_1.getName();
                _builder.append(_name_38, "\t\t\t");
                _builder.append(";");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append("}");
                _builder.newLine();
              } else {
                _builder.append("\t\t");
                _builder.append("/**");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append(" ");
                _builder.append("* Returns the current value for the attibute ");
                String _name_39 = attribute_1.getName();
                _builder.append(_name_39, "\t\t ");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append(" ");
                _builder.append("*");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append(" ");
                _builder.append("* @return current instance for ");
                String _name_40 = attribute_1.getName();
                String _firstLower_5 = StringExtensions.toFirstLower(_name_40);
                _builder.append(_firstLower_5, "\t\t ");
                _builder.append(" attribute");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append(" ");
                _builder.append("*/");
                _builder.newLine();
                _builder.append("\t\t");
                {
                  Type _type_23 = attribute_1.getType();
                  TypeSpecification _typeSpecification_16 = this._ismlModelNavigation.getTypeSpecification(_type_23);
                  if ((_typeSpecification_16 instanceof co.edu.javeriana.isml.isml.Enum)) {
                    _builder.append("@Enumerated");
                  }
                }
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append("public ");
                Type _type_24 = attribute_1.getType();
                TypeSpecification _typeSpecification_17 = this._ismlModelNavigation.getTypeSpecification(_type_24);
                String _typeSpecificationString_11 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_17);
                String _firstUpper_14 = StringExtensions.toFirstUpper(_typeSpecificationString_11);
                _builder.append(_firstUpper_14, "\t\t");
                _builder.append(" get");
                String _name_41 = attribute_1.getName();
                String _firstUpper_15 = StringExtensions.toFirstUpper(_name_41);
                _builder.append(_firstUpper_15, "\t\t");
                _builder.append("(){");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("return ");
                String _name_42 = attribute_1.getName();
                _builder.append(_name_42, "\t\t\t");
                _builder.append(";");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append("}");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append("/**");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append(" ");
                _builder.append("* Sets the value for the attribute ");
                String _name_43 = attribute_1.getName();
                _builder.append(_name_43, "\t\t ");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append(" ");
                _builder.append("* @param ");
                String _name_44 = attribute_1.getName();
                String _firstLower_6 = StringExtensions.toFirstLower(_name_44);
                _builder.append(_firstLower_6, "\t\t ");
                _builder.append(" The value to set");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append(" ");
                _builder.append("*/");
                _builder.newLine();
                _builder.append("\t\t");
                _builder.append("public void set");
                String _name_45 = attribute_1.getName();
                String _firstUpper_16 = StringExtensions.toFirstUpper(_name_45);
                _builder.append(_firstUpper_16, "\t\t");
                _builder.append("(");
                Type _type_25 = attribute_1.getType();
                TypeSpecification _typeSpecification_18 = this._ismlModelNavigation.getTypeSpecification(_type_25);
                String _typeSpecificationString_12 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_18);
                String _firstUpper_17 = StringExtensions.toFirstUpper(_typeSpecificationString_12);
                _builder.append(_firstUpper_17, "\t\t");
                _builder.append(" ");
                String _name_46 = attribute_1.getName();
                _builder.append(_name_46, "\t\t");
                _builder.append("){");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("this.");
                String _name_47 = attribute_1.getName();
                _builder.append(_name_47, "\t\t\t");
                _builder.append("=");
                String _name_48 = attribute_1.getName();
                _builder.append(_name_48, "\t\t\t");
                _builder.append(";");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append("}");
                _builder.newLine();
              }
            }
            _builder.append("\t\t");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.newLine();
    {
      Collection<Type> _parents_2 = this._ismlModelNavigation.getParents(entity);
      boolean _isEmpty_2 = _parents_2.isEmpty();
      if (_isEmpty_2) {
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("@Id");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("@GeneratedValue(strategy = GenerationType.AUTO)");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("public Long getId() {");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("return id;");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("/**");
        _builder.newLine();
        _builder.append("\t\t ");
        _builder.append("* Sets the value for the unique id");
        _builder.newLine();
        _builder.append("\t\t ");
        _builder.append("* @param id The value to set");
        _builder.newLine();
        _builder.append("\t\t ");
        _builder.append("*/");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("public void setId(final Long id) {");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("this.id = id;");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("@Override");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("public boolean equals(Object obj) {");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("if (this == obj) {");
        _builder.newLine();
        _builder.append("\t\t\t\t");
        _builder.append("return true;");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("if (obj == null) {");
        _builder.newLine();
        _builder.append("\t\t\t\t");
        _builder.append("return false;");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("if (!(obj instanceof ");
        String _name_49 = entity.getName();
        _builder.append(_name_49, "\t\t\t");
        _builder.append(")) {");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t\t\t");
        _builder.append("return false;");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("final ");
        String _name_50 = entity.getName();
        _builder.append(_name_50, "\t\t\t");
        _builder.append(" other = (");
        String _name_51 = entity.getName();
        _builder.append(_name_51, "\t\t\t");
        _builder.append(") obj;");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t\t");
        _builder.append("if (id == null) {");
        _builder.newLine();
        _builder.append("\t\t\t\t");
        _builder.append("if (other.getId() != null) {");
        _builder.newLine();
        _builder.append("\t\t\t\t\t");
        _builder.append("return false;");
        _builder.newLine();
        _builder.append("\t\t\t\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("} else if (!id.equals(other.getId())) {");
        _builder.newLine();
        _builder.append("\t\t\t\t");
        _builder.append("return false;");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("return true;");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("@Override");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("public int hashCode() {");
        _builder.newLine();
        _builder.newLine();
        _builder.append("\t\t\t\t");
        _builder.append("int hash = 0;");
        _builder.newLine();
        _builder.append("\t\t\t\t");
        _builder.append("hash += (id != null ? id.hashCode() : 0);");
        _builder.newLine();
        _builder.append("      \t\t\t\t\t");
        _builder.append("return hash;");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("}\t");
        _builder.newLine();
        _builder.append("\t\t        ");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.newLine();
        _builder.append("\t\t ");
        _builder.append("@Override");
        _builder.newLine();
        _builder.append("    \t\t\t");
        _builder.append("public String toString() {");
        _builder.newLine();
        _builder.append("        \t\t\t\t\t");
        _builder.append("return \"");
        EObject _eContainer_4 = entity.eContainer();
        QualifiedName _fullyQualifiedName_6 = this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer_4);
        _builder.append(_fullyQualifiedName_6, "        \t\t\t\t\t");
        _builder.append(".");
        String _name_52 = entity.getName();
        _builder.append(_name_52, "        \t\t\t\t\t");
        _builder.append(" [ id=\" + id + \" ]\";");
        _builder.newLineIfNotEmpty();
        _builder.append("    \t\t\t\t");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("\t\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public boolean evaluateAttributeToImport(final Attribute attribute, final Entity entity) {
    boolean needImport = false;
    Type _type = attribute.getType();
    boolean _isCollection = this._typeChecker.isCollection(_type);
    boolean _not = (!_isCollection);
    if (_not) {
      Type _type_1 = attribute.getType();
      TypeSpecification _typeSpecification = this._ismlModelNavigation.getTypeSpecification(_type_1);
      EObject _eContainer = _typeSpecification.eContainer();
      QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer);
      EObject _eContainer_1 = entity.eContainer();
      QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer_1);
      boolean _equals = _fullyQualifiedName.equals(_fullyQualifiedName_1);
      boolean _not_1 = (!_equals);
      needImport = _not_1;
    } else {
      Type _type_2 = attribute.getType();
      EList<Type> _typeParameters = ((ParameterizedType) _type_2).getTypeParameters();
      Type _get = _typeParameters.get(0);
      TypeSpecification _typeSpecification_1 = this._ismlModelNavigation.getTypeSpecification(_get);
      EObject _eContainer_2 = _typeSpecification_1.eContainer();
      QualifiedName _fullyQualifiedName_2 = this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer_2);
      EObject _eContainer_3 = entity.eContainer();
      QualifiedName _fullyQualifiedName_3 = this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer_3);
      boolean _equals_1 = _fullyQualifiedName_2.equals(_fullyQualifiedName_3);
      boolean _not_2 = (!_equals_1);
      needImport = _not_2;
    }
    return needImport;
  }
  
  public boolean isDatePresent(final Entity entity) {
    boolean hasDate = false;
    Iterable<Attribute> _attributes = this._ismlModelNavigation.getAttributes(entity);
    for (final Attribute attr : _attributes) {
      boolean _and = false;
      Type _type = attr.getType();
      TypeSpecification _typeSpecification = this._ismlModelNavigation.getTypeSpecification(_type);
      if (!(_typeSpecification instanceof Primitive)) {
        _and = false;
      } else {
        Type _type_1 = attr.getType();
        TypeSpecification _typeSpecification_1 = this._ismlModelNavigation.getTypeSpecification(_type_1);
        String _typeSpecificationString = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_1);
        boolean _equalsIgnoreCase = _typeSpecificationString.equalsIgnoreCase("Date");
        _and = _equalsIgnoreCase;
      }
      if (_and) {
        hasDate = true;
      }
    }
    return hasDate;
  }
  
  public CharSequence getParametersTemplate(final Instance constraint) {
    StringConcatenation _builder = new StringConcatenation();
    {
      EList<Expression> _parameters = constraint.getParameters();
      boolean _isEmpty = _parameters.isEmpty();
      boolean _not = (!_isEmpty);
      if (_not) {
        _builder.append("(");
        {
          Type _type = constraint.getType();
          TypeSpecification _typeSpecification = this._ismlModelNavigation.getTypeSpecification(_type);
          EList<Parameter> _parameters_1 = ((Constraint) _typeSpecification).getParameters();
          EList<Expression> _parameters_2 = constraint.getParameters();
          EList<Parameter> _setParametersValue = this._ismlModelNavigation.setParametersValue(_parameters_1, _parameters_2);
          boolean _hasElements = false;
          for(final Parameter parameter : _setParametersValue) {
            if (!_hasElements) {
              _hasElements = true;
            } else {
              _builder.appendImmediate(",", "");
            }
            _builder.newLineIfNotEmpty();
            String _name = parameter.getName();
            _builder.append(_name, "");
            _builder.append("=");
            {
              Type _type_1 = parameter.getType();
              TypeSpecification _typeSpecification_1 = this._ismlModelNavigation.getTypeSpecification(_type_1);
              String _typeSpecificationString = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_1);
              boolean _equalsIgnoreCase = _typeSpecificationString.equalsIgnoreCase("string");
              if (_equalsIgnoreCase) {
                _builder.append("\"");
                Expression _value = parameter.getValue();
                Object _literal = ((LiteralValue) _value).getLiteral();
                _builder.append(_literal, "");
                _builder.append("\"");
              } else {
                Expression _value_1 = parameter.getValue();
                Object _literal_1 = ((LiteralValue) _value_1).getLiteral();
                _builder.append(_literal_1, "");
              }
            }
          }
        }
        _builder.append(")");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
}
