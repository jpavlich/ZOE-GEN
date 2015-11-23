package co.edu.javeriana.zoe.generator.jee7.templates;

import co.edu.javeriana.isml.generator.common.SimpleTemplate;
import co.edu.javeriana.isml.isml.Action;
import co.edu.javeriana.isml.isml.ActionCall;
import co.edu.javeriana.isml.isml.Controller;
import co.edu.javeriana.isml.isml.Entity;
import co.edu.javeriana.isml.isml.Feature;
import co.edu.javeriana.isml.isml.For;
import co.edu.javeriana.isml.isml.ForView;
import co.edu.javeriana.isml.isml.If;
import co.edu.javeriana.isml.isml.MethodStatement;
import co.edu.javeriana.isml.isml.Page;
import co.edu.javeriana.isml.isml.Parameter;
import co.edu.javeriana.isml.isml.ParameterizedType;
import co.edu.javeriana.isml.isml.Primitive;
import co.edu.javeriana.isml.isml.Return;
import co.edu.javeriana.isml.isml.Show;
import co.edu.javeriana.isml.isml.Type;
import co.edu.javeriana.isml.isml.TypeSpecification;
import co.edu.javeriana.isml.isml.Variable;
import co.edu.javeriana.isml.isml.While;
import co.edu.javeriana.isml.scoping.IsmlModelNavigation;
import co.edu.javeriana.isml.validation.TypeChecker;
import co.edu.javeriana.zoe.generator.jee7.templates.ExpressionTemplate;
import co.edu.javeriana.zoe.generator.jee7.templates.ServicesValidator;
import co.edu.javeriana.zoe.generator.jee7.templates.StatementTemplate;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class ZoeControllerTemplate extends SimpleTemplate<Controller> {
  @Inject
  @Extension
  private IQualifiedNameProvider _iQualifiedNameProvider;
  
  @Inject
  @Extension
  private IsmlModelNavigation _ismlModelNavigation;
  
  @Inject
  @Extension
  private TypeChecker _typeChecker;
  
  @Inject
  @Extension
  private ExpressionTemplate _expressionTemplate;
  
  @Inject
  @Extension
  private StatementTemplate _statementTemplate;
  
  @Inject
  @Extension
  private ServicesValidator _servicesValidator;
  
  private EList<Entity> controllerEntities;
  
  private Entity entityToList;
  
  private Map<String, Type> neededAttributes = new HashMap<String, Type>();
  
  public void preprocess(final Controller controller) {
    Map<String, Object> neededData = this._ismlModelNavigation.getNeededAttributes(controller);
    Object _get = neededData.get("controllerEntities");
    this.controllerEntities = ((EList<Entity>) _get);
    Object _get_1 = neededData.get("entityToList");
    this.entityToList = ((Entity) _get_1);
    Object _get_2 = neededData.get("neededAttributes");
    this.neededAttributes = ((Map<String, Type>) _get_2);
    final EList<Page> controlledPages = this._ismlModelNavigation.getControlledPages(controller);
    final ArrayList<ForView> forViews = new ArrayList<ForView>();
    for (final Page p : controlledPages) {
      TreeIterator<EObject> _eAllContents = p.eAllContents();
      Iterator<ForView> _filter = Iterators.<ForView>filter(_eAllContents, ForView.class);
      Iterable<ForView> _iterable = IteratorExtensions.<ForView>toIterable(_filter);
      Iterables.<ForView>addAll(forViews, _iterable);
    }
    for (final ForView p_1 : forViews) {
      {
        Variable _variable = p_1.getVariable();
        final String key = _variable.getName();
        Variable _variable_1 = p_1.getVariable();
        final Type value = _variable_1.getType();
        InputOutput.<String>println(((key + "->") + value));
        this.neededAttributes.put(key, value);
      }
    }
  }
  
  /**
   * @«constraint.type.typeSpecification.typeSpecificationString»(«FOR Expression ex : constraint.parameters SEPARATOR ","»«ex.toString.length»«ENDFOR»)
   */
  public CharSequence template(final Controller controller) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\t");
    _builder.append("package ");
    EObject _eContainer = controller.eContainer();
    QualifiedName _fullyQualifiedName = this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer);
    _builder.append(_fullyQualifiedName, "\t");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("import java.io.Serializable;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("import javax.inject.*;\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("import ");
    EObject _eContainer_1 = controller.eContainer();
    QualifiedName _fullyQualifiedName_1 = this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer_1);
    _builder.append(_fullyQualifiedName_1, "\t");
    _builder.append(".JsfUtil;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("import javax.enterprise.context.*;\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("import java.util.*;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("import javax.faces.application.FacesMessage;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("import javax.faces.context.FacesContext;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("import javax.faces.bean.ManagedBean;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("import javax.annotation.PostConstruct;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("import javax.ejb.EJB;");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    {
      EList<Controller> _actionCallControllers = this._expressionTemplate.getActionCallControllers(controller);
      for(final Controller invokedController : _actionCallControllers) {
        {
          EObject _eContainer_2 = invokedController.eContainer();
          QualifiedName _fullyQualifiedName_2 = this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer_2);
          EObject _eContainer_3 = controller.eContainer();
          QualifiedName _fullyQualifiedName_3 = this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer_3);
          boolean _equals = _fullyQualifiedName_2.equals(_fullyQualifiedName_3);
          boolean _not = (!_equals);
          if (_not) {
            _builder.append("import ");
            QualifiedName _fullyQualifiedName_4 = this._iQualifiedNameProvider.getFullyQualifiedName(invokedController);
            _builder.append(_fullyQualifiedName_4, "");
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.newLine();
    {
      Set<Map.Entry<String, Type>> _entrySet = this.neededAttributes.entrySet();
      for(final Map.Entry<String, Type> attr : _entrySet) {
        {
          Type _value = attr.getValue();
          boolean _not_1 = (!(_value instanceof ParameterizedType));
          if (_not_1) {
            {
              boolean _and = false;
              Type _value_1 = attr.getValue();
              TypeSpecification _typeSpecification = this._ismlModelNavigation.getTypeSpecification(_value_1);
              EObject _eContainer_4 = _typeSpecification.eContainer();
              QualifiedName _fullyQualifiedName_5 = this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer_4);
              EObject _eContainer_5 = controller.eContainer();
              QualifiedName _fullyQualifiedName_6 = this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer_5);
              boolean _equals_1 = _fullyQualifiedName_5.equals(_fullyQualifiedName_6);
              boolean _not_2 = (!_equals_1);
              if (!_not_2) {
                _and = false;
              } else {
                Type _value_2 = attr.getValue();
                TypeSpecification _typeSpecification_1 = this._ismlModelNavigation.getTypeSpecification(_value_2);
                boolean _not_3 = (!(_typeSpecification_1 instanceof Primitive));
                _and = _not_3;
              }
              if (_and) {
                _builder.append("\t");
                _builder.append("import ");
                Type _value_3 = attr.getValue();
                TypeSpecification _typeSpecification_2 = this._ismlModelNavigation.getTypeSpecification(_value_3);
                QualifiedName _fullyQualifiedName_7 = this._iQualifiedNameProvider.getFullyQualifiedName(_typeSpecification_2);
                _builder.append(_fullyQualifiedName_7, "\t");
                _builder.append(";");
                _builder.newLineIfNotEmpty();
              }
            }
          } else {
            {
              boolean _and_1 = false;
              Type _value_4 = attr.getValue();
              EList<Type> _typeParameters = ((ParameterizedType) _value_4).getTypeParameters();
              Type _get = _typeParameters.get(0);
              TypeSpecification _typeSpecification_3 = this._ismlModelNavigation.getTypeSpecification(_get);
              EObject _eContainer_6 = _typeSpecification_3.eContainer();
              QualifiedName _fullyQualifiedName_8 = this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer_6);
              EObject _eContainer_7 = controller.eContainer();
              QualifiedName _fullyQualifiedName_9 = this._iQualifiedNameProvider.getFullyQualifiedName(_eContainer_7);
              boolean _equals_2 = _fullyQualifiedName_8.equals(_fullyQualifiedName_9);
              boolean _not_4 = (!_equals_2);
              if (!_not_4) {
                _and_1 = false;
              } else {
                Type _value_5 = attr.getValue();
                TypeSpecification _typeSpecification_4 = this._ismlModelNavigation.getTypeSpecification(_value_5);
                boolean _not_5 = (!(_typeSpecification_4 instanceof Primitive));
                _and_1 = _not_5;
              }
              if (_and_1) {
                _builder.append("\t");
                _builder.append("import ");
                Type _value_6 = attr.getValue();
                EList<Type> _typeParameters_1 = ((ParameterizedType) _value_6).getTypeParameters();
                Type _get_1 = _typeParameters_1.get(0);
                TypeSpecification _typeSpecification_5 = this._ismlModelNavigation.getTypeSpecification(_get_1);
                QualifiedName _fullyQualifiedName_10 = this._iQualifiedNameProvider.getFullyQualifiedName(_typeSpecification_5);
                _builder.append(_fullyQualifiedName_10, "\t");
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
    {
      Map<QualifiedName, TypeSpecification> _neededImportsInActions = this._ismlModelNavigation.getNeededImportsInActions(controller);
      Set<Map.Entry<QualifiedName, TypeSpecification>> _entrySet_1 = _neededImportsInActions.entrySet();
      for(final Map.Entry<QualifiedName, TypeSpecification> entity : _entrySet_1) {
        _builder.append("\t");
        _builder.append("import ");
        TypeSpecification _value_7 = entity.getValue();
        QualifiedName _fullyQualifiedName_11 = this._iQualifiedNameProvider.getFullyQualifiedName(_value_7);
        _builder.append(_fullyQualifiedName_11, "\t");
        _builder.append(";");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/**");
    _builder.newLine();
    _builder.append("\t ");
    _builder.append("* This class represents a controller with name ");
    String _name = controller.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name);
    _builder.append(_firstUpper, "\t ");
    _builder.newLineIfNotEmpty();
    _builder.append("\t ");
    _builder.append("*/");
    _builder.newLine();
    _builder.append("\t ");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@ManagedBean(name = \"");
    String _name_1 = controller.getName();
    String _firstLower = StringExtensions.toFirstLower(_name_1);
    _builder.append(_firstLower, "\t");
    _builder.append("\")");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("@RequestScoped");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("public class ");
    String _name_2 = controller.getName();
    String _firstUpper_1 = StringExtensions.toFirstUpper(_name_2);
    _builder.append(_firstUpper_1, "\t\t\t");
    _builder.append(" implements Serializable {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
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
    _builder.append("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
    _builder.newLine();
    _builder.append("\t\t\t\t\t\t\t\t\t\t\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    {
      Iterable<Feature> _services = this._ismlModelNavigation.getServices(controller);
      for(final Feature service : _services) {
        _builder.append("\t\t");
        _builder.append("/**");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append(" ");
        _builder.append("* Injection for the component named ");
        Type _type = service.getType();
        TypeSpecification _typeSpecification_6 = this._ismlModelNavigation.getTypeSpecification(_type);
        String _typeSpecificationString = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_6);
        String _firstUpper_2 = StringExtensions.toFirstUpper(_typeSpecificationString);
        _builder.append(_firstUpper_2, "\t\t ");
        _builder.append(" ");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append(" ");
        _builder.append("*/");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("@EJB");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("\t\t\t\t\t\t\t\t");
        _builder.append("private ");
        {
          Type _type_1 = service.getType();
          EList<Type> _typeParameters_2 = ((ParameterizedType) _type_1).getTypeParameters();
          boolean _hasElements = false;
          for(final Type param : _typeParameters_2) {
            if (!_hasElements) {
              _hasElements = true;
            } else {
              _builder.appendImmediate(",", "\t\t\t\t\t\t\t\t\t\t");
            }
            String _writeType = this._ismlModelNavigation.writeType(param, true);
            _builder.append(_writeType, "\t\t\t\t\t\t\t\t\t\t");
          }
        }
        _builder.append("General ");
        {
          String _name_3 = service.getName();
          boolean _notEquals = (!Objects.equal(_name_3, null));
          if (_notEquals) {
            String _name_4 = service.getName();
            String _firstLower_1 = StringExtensions.toFirstLower(_name_4);
            _builder.append(_firstLower_1, "\t\t\t\t\t\t\t\t\t\t");
          } else {
            String _name_5 = service.getName();
            String _firstLower_2 = StringExtensions.toFirstLower(_name_5);
            _builder.append(_firstLower_2, "\t\t\t\t\t\t\t\t\t\t");
          }
        }
        _builder.append("; ");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t    ");
    _builder.newLine();
    {
      Set<Map.Entry<String, Type>> _entrySet_2 = this.neededAttributes.entrySet();
      for(final Map.Entry<String, Type> attr_1 : _entrySet_2) {
        {
          Type _value_8 = attr_1.getValue();
          boolean _isCollection = this._typeChecker.isCollection(_value_8);
          if (_isCollection) {
            _builder.append("\t\t");
            _builder.append("/**");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("* Attribute for the type ");
            Type _value_9 = attr_1.getValue();
            String _collectionString = this._ismlModelNavigation.getCollectionString(((ParameterizedType) _value_9));
            _builder.append(_collectionString, "\t\t ");
            _builder.append("<");
            Type _value_10 = attr_1.getValue();
            EList<Type> _typeParameters_3 = ((ParameterizedType) _value_10).getTypeParameters();
            Type _get_2 = _typeParameters_3.get(0);
            TypeSpecification _typeSpecification_7 = this._ismlModelNavigation.getTypeSpecification(_get_2);
            String _typeSpecificationString_1 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_7);
            _builder.append(_typeSpecificationString_1, "\t\t ");
            _builder.append(">»  ");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("*/");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("private ");
            Type _value_11 = attr_1.getValue();
            String _collectionString_1 = this._ismlModelNavigation.getCollectionString(((ParameterizedType) _value_11));
            _builder.append(_collectionString_1, "\t\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("<");
            Type _value_12 = attr_1.getValue();
            EList<Type> _typeParameters_4 = ((ParameterizedType) _value_12).getTypeParameters();
            Type _get_3 = _typeParameters_4.get(0);
            TypeSpecification _typeSpecification_8 = this._ismlModelNavigation.getTypeSpecification(_get_3);
            String _typeSpecificationString_2 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_8);
            _builder.append(_typeSpecificationString_2, "\t\t");
            _builder.append("> ");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            String _key = attr_1.getKey();
            _builder.append(_key, "\t\t");
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("\t\t");
            _builder.append("/**");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("* Attribute for the type ");
            Type _value_13 = attr_1.getValue();
            TypeSpecification _typeSpecification_9 = this._ismlModelNavigation.getTypeSpecification(_value_13);
            String _typeSpecificationString_3 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_9);
            String _firstUpper_3 = StringExtensions.toFirstUpper(_typeSpecificationString_3);
            _builder.append(_firstUpper_3, "\t\t ");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("*/");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("private ");
            Type _value_14 = attr_1.getValue();
            TypeSpecification _typeSpecification_10 = this._ismlModelNavigation.getTypeSpecification(_value_14);
            String _typeSpecificationString_4 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_10);
            String _firstUpper_4 = StringExtensions.toFirstUpper(_typeSpecificationString_4);
            _builder.append(_firstUpper_4, "\t\t");
            _builder.append(" ");
            String _key_1 = attr_1.getKey();
            _builder.append(_key_1, "\t\t");
            {
              Type _value_15 = attr_1.getValue();
              TypeSpecification _typeSpecification_11 = this._ismlModelNavigation.getTypeSpecification(_value_15);
              boolean _not_6 = (!(_typeSpecification_11 instanceof Primitive));
              if (_not_6) {
                _builder.append("=new ");
                Type _value_16 = attr_1.getValue();
                TypeSpecification _typeSpecification_12 = this._ismlModelNavigation.getTypeSpecification(_value_16);
                String _typeSpecificationString_5 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_12);
                String _firstUpper_5 = StringExtensions.toFirstUpper(_typeSpecificationString_5);
                _builder.append(_firstUpper_5, "\t\t");
                _builder.append("()");
              }
            }
            _builder.append(";");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.newLine();
    {
      EList<Controller> _actionCallControllers_1 = this._expressionTemplate.getActionCallControllers(controller);
      for(final Controller invokedController_1 : _actionCallControllers_1) {
        _builder.append("\t\t");
        _builder.append("/**");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append(" ");
        _builder.append("* Instance of the controller named ");
        String _name_6 = invokedController_1.getName();
        String _firstUpper_6 = StringExtensions.toFirstUpper(_name_6);
        _builder.append(_firstUpper_6, "\t\t ");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append(" ");
        _builder.append("*/");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("@Inject");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("private ");
        String _name_7 = invokedController_1.getName();
        String _firstUpper_7 = StringExtensions.toFirstUpper(_name_7);
        _builder.append(_firstUpper_7, "\t\t");
        _builder.append(" ");
        String _name_8 = invokedController_1.getName();
        String _firstLower_3 = StringExtensions.toFirstLower(_name_8);
        _builder.append(_firstLower_3, "\t\t");
        _builder.append("; ");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t\t\t");
    _builder.newLine();
    _builder.append("\t ");
    _builder.append("@PostConstruct");
    _builder.newLine();
    _builder.append("\t ");
    _builder.append("public void init() {");
    _builder.newLine();
    {
      Iterable<Action> _actions = this._ismlModelNavigation.getActions(controller);
      for(final Action action : _actions) {
        {
          boolean _isDefault = action.isDefault();
          if (_isDefault) {
            {
              EList<MethodStatement> _body = action.getBody();
              for(final MethodStatement st : _body) {
                {
                  if ((!(st instanceof Show))) {
                    _builder.append("\t\t\t\t");
                    CharSequence _writeStatement = this._statementTemplate.writeStatement(((MethodStatement) st));
                    _builder.append(_writeStatement, "\t\t\t\t");
                    _builder.newLineIfNotEmpty();
                  }
                }
              }
            }
          }
        }
      }
    }
    _builder.append("\t  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    {
      Iterable<Action> _actions_1 = this._ismlModelNavigation.getActions(controller);
      for(final Action method : _actions_1) {
        _builder.append("\t\t");
        _builder.append("/**");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append(" ");
        _builder.append("* Action method named ");
        String _name_9 = method.getName();
        _builder.append(_name_9, "\t\t ");
        _builder.newLineIfNotEmpty();
        {
          EList<Parameter> _parameters = method.getParameters();
          for(final Parameter param_1 : _parameters) {
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("* @param ");
            String _name_10 = param_1.getName();
            String _firstLower_4 = StringExtensions.toFirstLower(_name_10);
            _builder.append(_firstLower_4, "\t\t ");
            _builder.append(" Parameter from type ");
            Type _type_2 = param_1.getType();
            TypeSpecification _typeSpecification_13 = this._ismlModelNavigation.getTypeSpecification(_type_2);
            String _name_11 = _typeSpecification_13.getName();
            String _firstUpper_8 = StringExtensions.toFirstUpper(_name_11);
            _builder.append(_firstUpper_8, "\t\t ");
            _builder.newLineIfNotEmpty();
          }
        }
        _builder.append("\t\t");
        _builder.append(" ");
        _builder.append("*");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append(" ");
        _builder.append("* @return String value with some navigation outcome");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append(" ");
        _builder.append("*/");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("public String ");
        String _name_12 = method.getName();
        _builder.append(_name_12, "\t\t");
        _builder.append("(");
        {
          EList<Parameter> _parameters_1 = method.getParameters();
          boolean _hasElements_1 = false;
          for(final Parameter param_2 : _parameters_1) {
            if (!_hasElements_1) {
              _hasElements_1 = true;
            } else {
              _builder.appendImmediate(",", "\t\t");
            }
            {
              Type _type_3 = param_2.getType();
              boolean _isCollection_1 = this._typeChecker.isCollection(_type_3);
              if (_isCollection_1) {
                _builder.append(" List  ");
                String _name_13 = param_2.getName();
                String _firstLower_5 = StringExtensions.toFirstLower(_name_13);
                _builder.append(_firstLower_5, "\t\t");
              } else {
                _builder.append(" ");
                Type _type_4 = param_2.getType();
                TypeSpecification _typeSpecification_14 = this._ismlModelNavigation.getTypeSpecification(_type_4);
                String _typeSpecificationString_6 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_14);
                String _firstUpper_9 = StringExtensions.toFirstUpper(_typeSpecificationString_6);
                _builder.append(_firstUpper_9, "\t\t");
                _builder.append(" ");
                String _name_14 = param_2.getName();
                String _firstLower_6 = StringExtensions.toFirstLower(_name_14);
                _builder.append(_firstLower_6, "\t\t");
              }
            }
          }
        }
        _builder.append("){");
        _builder.newLineIfNotEmpty();
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("\t");
        _builder.append("try{");
        _builder.newLine();
        {
          EList<Parameter> _parameters_2 = method.getParameters();
          for(final Parameter param_3 : _parameters_2) {
            {
              String _name_15 = param_3.getName();
              boolean _containsKey = this.neededAttributes.containsKey(_name_15);
              if (_containsKey) {
                _builder.append("\t\t");
                _builder.append("\t\t");
                _builder.append("if(");
                String _name_16 = param_3.getName();
                _builder.append(_name_16, "\t\t\t\t");
                _builder.append("!=null){");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append("\t\t");
                _builder.append("\t");
                _builder.append("this.set");
                String _name_17 = param_3.getName();
                String _firstUpper_10 = StringExtensions.toFirstUpper(_name_17);
                _builder.append(_firstUpper_10, "\t\t\t\t\t");
                _builder.append("(");
                String _name_18 = param_3.getName();
                _builder.append(_name_18, "\t\t\t\t\t");
                _builder.append(");");
                _builder.newLineIfNotEmpty();
                _builder.append("\t\t");
                _builder.append("\t\t");
                _builder.append("}");
                _builder.newLine();
              } else {
                {
                  Map.Entry<String, Type> _obtainAttribute = this.obtainAttribute(param_3);
                  boolean _notEquals_1 = (!Objects.equal(_obtainAttribute, null));
                  if (_notEquals_1) {
                    _builder.append("\t\t");
                    _builder.append("\t\t");
                    _builder.append("if(");
                    String _name_19 = param_3.getName();
                    _builder.append(_name_19, "\t\t\t\t");
                    _builder.append("!=null){");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t");
                    _builder.append("\t\t");
                    _builder.append("\t");
                    _builder.append("this.set");
                    Map.Entry<String, Type> _obtainAttribute_1 = this.obtainAttribute(param_3);
                    String _key_2 = _obtainAttribute_1.getKey();
                    String _firstUpper_11 = StringExtensions.toFirstUpper(_key_2);
                    _builder.append(_firstUpper_11, "\t\t\t\t\t");
                    _builder.append("(");
                    String _name_20 = param_3.getName();
                    _builder.append(_name_20, "\t\t\t\t\t");
                    _builder.append(");");
                    _builder.newLineIfNotEmpty();
                    _builder.append("\t\t");
                    _builder.append("\t\t");
                    _builder.append("}");
                    _builder.newLine();
                  }
                }
              }
            }
          }
        }
        _builder.append("\t\t");
        _builder.append("\t\t");
        EList<MethodStatement> _body_1 = method.getBody();
        CharSequence _writeStatements = this._statementTemplate.writeStatements(_body_1);
        _builder.append(_writeStatements, "\t\t\t\t");
        _builder.newLineIfNotEmpty();
        {
          EList<MethodStatement> _body_2 = method.getBody();
          boolean _actionRequiresReturnSentence = this.actionRequiresReturnSentence(_body_2);
          if (_actionRequiresReturnSentence) {
            _builder.append("\t\t");
            _builder.append("\t\t");
            _builder.append("return \"\";");
            _builder.newLine();
          }
        }
        _builder.append("\t\t");
        _builder.append("\t");
        _builder.append("}catch (Exception e)\t{");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("\t\t");
        _builder.append("JsfUtil.addSuccessMessage(ResourceBundle.getBundle(\"/Bundle\").getString(\"DietaCreated\"));");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("\t\t");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("\t");
        _builder.append("} ");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("\t");
        _builder.append("return \"\";");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("}");
        _builder.newLine();
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    {
      Set<Map.Entry<String, Type>> _entrySet_3 = this.neededAttributes.entrySet();
      for(final Map.Entry<String, Type> attr_2 : _entrySet_3) {
        {
          Type _value_17 = attr_2.getValue();
          boolean _isCollection_2 = this._typeChecker.isCollection(_value_17);
          if (_isCollection_2) {
            _builder.append("\t\t");
            _builder.append("/**");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("* Returns the current value for the attribute ");
            String _key_3 = attr_2.getKey();
            _builder.append(_key_3, "\t\t ");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("*");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("* @return current instance for ");
            String _key_4 = attr_2.getKey();
            String _firstLower_7 = StringExtensions.toFirstLower(_key_4);
            _builder.append(_firstLower_7, "\t\t ");
            _builder.append(" attribute");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("*/");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("public ");
            Type _value_18 = attr_2.getValue();
            String _collectionString_2 = this._ismlModelNavigation.getCollectionString(((ParameterizedType) _value_18));
            _builder.append(_collectionString_2, "\t\t");
            _builder.append("<");
            Type _value_19 = attr_2.getValue();
            EList<Type> _typeParameters_5 = ((ParameterizedType) _value_19).getTypeParameters();
            Type _get_4 = _typeParameters_5.get(0);
            TypeSpecification _typeSpecification_15 = this._ismlModelNavigation.getTypeSpecification(_get_4);
            String _typeSpecificationString_7 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_15);
            _builder.append(_typeSpecificationString_7, "\t\t");
            _builder.append("> get");
            String _key_5 = attr_2.getKey();
            String _firstUpper_12 = StringExtensions.toFirstUpper(_key_5);
            _builder.append(_firstUpper_12, "\t\t");
            _builder.append("(){\t\t\t\t\t\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("return ");
            String _key_6 = attr_2.getKey();
            String _firstLower_8 = StringExtensions.toFirstLower(_key_6);
            _builder.append(_firstLower_8, "\t\t\t");
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
            String _key_7 = attr_2.getKey();
            _builder.append(_key_7, "\t\t ");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("* @param ");
            String _key_8 = attr_2.getKey();
            String _firstLower_9 = StringExtensions.toFirstLower(_key_8);
            _builder.append(_firstLower_9, "\t\t ");
            _builder.append(" The value to set");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("*/");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("public void set");
            String _key_9 = attr_2.getKey();
            String _firstUpper_13 = StringExtensions.toFirstUpper(_key_9);
            _builder.append(_firstUpper_13, "\t\t");
            _builder.append("(");
            Type _value_20 = attr_2.getValue();
            String _collectionString_3 = this._ismlModelNavigation.getCollectionString(((ParameterizedType) _value_20));
            _builder.append(_collectionString_3, "\t\t");
            _builder.append("<");
            Type _value_21 = attr_2.getValue();
            EList<Type> _typeParameters_6 = ((ParameterizedType) _value_21).getTypeParameters();
            Type _get_5 = _typeParameters_6.get(0);
            TypeSpecification _typeSpecification_16 = this._ismlModelNavigation.getTypeSpecification(_get_5);
            String _typeSpecificationString_8 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_16);
            _builder.append(_typeSpecificationString_8, "\t\t");
            _builder.append("> ");
            String _key_10 = attr_2.getKey();
            String _firstLower_10 = StringExtensions.toFirstLower(_key_10);
            _builder.append(_firstLower_10, "\t\t");
            _builder.append("){");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("this.");
            String _key_11 = attr_2.getKey();
            String _firstLower_11 = StringExtensions.toFirstLower(_key_11);
            _builder.append(_firstLower_11, "\t\t\t");
            _builder.append("=");
            String _key_12 = attr_2.getKey();
            String _firstLower_12 = StringExtensions.toFirstLower(_key_12);
            _builder.append(_firstLower_12, "\t\t\t");
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
            _builder.append("* Returns the current value for the attribute ");
            String _key_13 = attr_2.getKey();
            _builder.append(_key_13, "\t\t ");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("* @return current instance for ");
            String _key_14 = attr_2.getKey();
            String _firstLower_13 = StringExtensions.toFirstLower(_key_14);
            _builder.append(_firstLower_13, "\t\t ");
            _builder.append(" attribute");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("*/");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("public ");
            Type _value_22 = attr_2.getValue();
            TypeSpecification _typeSpecification_17 = this._ismlModelNavigation.getTypeSpecification(_value_22);
            String _typeSpecificationString_9 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_17);
            String _firstUpper_14 = StringExtensions.toFirstUpper(_typeSpecificationString_9);
            _builder.append(_firstUpper_14, "\t\t");
            _builder.append(" get");
            String _key_15 = attr_2.getKey();
            String _firstUpper_15 = StringExtensions.toFirstUpper(_key_15);
            _builder.append(_firstUpper_15, "\t\t");
            _builder.append("(){");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("return ");
            String _key_16 = attr_2.getKey();
            String _firstLower_14 = StringExtensions.toFirstLower(_key_16);
            _builder.append(_firstLower_14, "\t\t\t");
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
            String _key_17 = attr_2.getKey();
            _builder.append(_key_17, "\t\t ");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("* @param ");
            String _key_18 = attr_2.getKey();
            String _firstLower_15 = StringExtensions.toFirstLower(_key_18);
            _builder.append(_firstLower_15, "\t\t ");
            _builder.append(" The value to set");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append(" ");
            _builder.append("*/");
            _builder.newLine();
            _builder.append("\t\t");
            _builder.append("public void set");
            String _key_19 = attr_2.getKey();
            String _firstUpper_16 = StringExtensions.toFirstUpper(_key_19);
            _builder.append(_firstUpper_16, "\t\t");
            _builder.append("(");
            Type _value_23 = attr_2.getValue();
            TypeSpecification _typeSpecification_18 = this._ismlModelNavigation.getTypeSpecification(_value_23);
            String _typeSpecificationString_10 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_18);
            String _firstUpper_17 = StringExtensions.toFirstUpper(_typeSpecificationString_10);
            _builder.append(_firstUpper_17, "\t\t");
            _builder.append(" ");
            String _key_20 = attr_2.getKey();
            String _firstLower_16 = StringExtensions.toFirstLower(_key_20);
            _builder.append(_firstLower_16, "\t\t");
            _builder.append("){");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("\t");
            _builder.append("this.");
            String _key_21 = attr_2.getKey();
            String _firstLower_17 = StringExtensions.toFirstLower(_key_21);
            _builder.append(_firstLower_17, "\t\t\t");
            _builder.append("=");
            String _key_22 = attr_2.getKey();
            String _firstLower_18 = StringExtensions.toFirstLower(_key_22);
            _builder.append(_firstLower_18, "\t\t\t");
            _builder.append(";");
            _builder.newLineIfNotEmpty();
            _builder.append("\t\t");
            _builder.append("}");
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("\t\t");
    _builder.newLine();
    {
      Iterable<Feature> _services_1 = this._ismlModelNavigation.getServices(controller);
      for(final Feature service_1 : _services_1) {
        _builder.append("\t\t");
        _builder.append("/**");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append(" ");
        _builder.append("* Returns the instance for the ");
        {
          String _name_21 = service_1.getName();
          boolean _notEquals_2 = (!Objects.equal(_name_21, null));
          if (_notEquals_2) {
            String _name_22 = service_1.getName();
            _builder.append(_name_22, "\t\t ");
          } else {
            Type _type_5 = service_1.getType();
            TypeSpecification _typeSpecification_19 = this._ismlModelNavigation.getTypeSpecification(_type_5);
            String _typeSpecificationString_11 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_19);
            _builder.append(_typeSpecificationString_11, "\t\t ");
          }
        }
        _builder.append(" EJB");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append(" ");
        _builder.append("*");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append(" ");
        _builder.append("* @return current instance for ");
        String _name_23 = service_1.getName();
        String _firstLower_19 = StringExtensions.toFirstLower(_name_23);
        _builder.append(_firstLower_19, "\t\t ");
        _builder.append(" attribute");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append(" ");
        _builder.append("*/");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("public ");
        Type _type_6 = service_1.getType();
        TypeSpecification _typeSpecification_20 = this._ismlModelNavigation.getTypeSpecification(_type_6);
        String _typeSpecificationString_12 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_20);
        String _firstUpper_18 = StringExtensions.toFirstUpper(_typeSpecificationString_12);
        _builder.append(_firstUpper_18, "\t\t\t");
        {
          Type _type_7 = service_1.getType();
          if ((_type_7 instanceof ParameterizedType)) {
            _builder.append("<");
            {
              Type _type_8 = service_1.getType();
              EList<Type> _typeParameters_7 = ((ParameterizedType) _type_8).getTypeParameters();
              boolean _hasElements_2 = false;
              for(final Type param_4 : _typeParameters_7) {
                if (!_hasElements_2) {
                  _hasElements_2 = true;
                } else {
                  _builder.appendImmediate(",", "\t\t\t");
                }
                String _writeType_1 = this._ismlModelNavigation.writeType(param_4, true);
                _builder.append(_writeType_1, "\t\t\t");
              }
            }
            _builder.append(">");
          }
        }
        _builder.append(" ");
        {
          String _name_24 = service_1.getName();
          boolean _notEquals_3 = (!Objects.equal(_name_24, null));
          if (_notEquals_3) {
            _builder.append("get");
            String _name_25 = service_1.getName();
            String _firstUpper_19 = StringExtensions.toFirstUpper(_name_25);
            _builder.append(_firstUpper_19, "\t\t\t");
          } else {
            _builder.append("get");
            Type _type_9 = service_1.getType();
            TypeSpecification _typeSpecification_21 = this._ismlModelNavigation.getTypeSpecification(_type_9);
            String _name_26 = _typeSpecification_21.getName();
            String _firstUpper_20 = StringExtensions.toFirstUpper(_name_26);
            _builder.append(_firstUpper_20, "\t\t\t");
          }
        }
        _builder.append("(){");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("return ");
        {
          String _name_27 = service_1.getName();
          boolean _notEquals_4 = (!Objects.equal(_name_27, null));
          if (_notEquals_4) {
            String _name_28 = service_1.getName();
            String _firstLower_20 = StringExtensions.toFirstLower(_name_28);
            _builder.append(_firstLower_20, "\t\t");
          } else {
            String _name_29 = service_1.getName();
            String _firstLower_21 = StringExtensions.toFirstLower(_name_29);
            _builder.append(_firstLower_21, "\t\t");
          }
        }
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("}");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append("/**");
        _builder.newLine();
        _builder.append("\t\t");
        _builder.append(" ");
        _builder.append("* Sets the value for the ");
        {
          String _name_30 = service_1.getName();
          boolean _notEquals_5 = (!Objects.equal(_name_30, null));
          if (_notEquals_5) {
            String _name_31 = service_1.getName();
            _builder.append(_name_31, "\t\t ");
          } else {
            Type _type_10 = service_1.getType();
            TypeSpecification _typeSpecification_22 = this._ismlModelNavigation.getTypeSpecification(_type_10);
            String _typeSpecificationString_13 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_22);
            _builder.append(_typeSpecificationString_13, "\t\t ");
          }
        }
        _builder.append(" EJB");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append(" ");
        _builder.append("* @param ");
        String _name_32 = service_1.getName();
        String _firstLower_22 = StringExtensions.toFirstLower(_name_32);
        _builder.append(_firstLower_22, "\t\t ");
        _builder.append(" The value to set");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append(" ");
        _builder.append("*/");
        _builder.newLine();
        _builder.append("\t\t\t");
        _builder.append("public void ");
        {
          String _name_33 = service_1.getName();
          boolean _notEquals_6 = (!Objects.equal(_name_33, null));
          if (_notEquals_6) {
            _builder.append("set");
            String _name_34 = service_1.getName();
            String _firstUpper_21 = StringExtensions.toFirstUpper(_name_34);
            _builder.append(_firstUpper_21, "\t\t\t");
          } else {
            _builder.append("set");
            String _name_35 = service_1.getName();
            String _firstUpper_22 = StringExtensions.toFirstUpper(_name_35);
            _builder.append(_firstUpper_22, "\t\t\t");
          }
        }
        _builder.append("(");
        Type _type_11 = service_1.getType();
        TypeSpecification _typeSpecification_23 = this._ismlModelNavigation.getTypeSpecification(_type_11);
        String _typeSpecificationString_14 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_23);
        String _firstUpper_23 = StringExtensions.toFirstUpper(_typeSpecificationString_14);
        _builder.append(_firstUpper_23, "\t\t\t");
        {
          Type _type_12 = service_1.getType();
          if ((_type_12 instanceof ParameterizedType)) {
            _builder.append("<");
            {
              Type _type_13 = service_1.getType();
              EList<Type> _typeParameters_8 = ((ParameterizedType) _type_13).getTypeParameters();
              boolean _hasElements_3 = false;
              for(final Type param_5 : _typeParameters_8) {
                if (!_hasElements_3) {
                  _hasElements_3 = true;
                } else {
                  _builder.appendImmediate(",", "\t\t\t");
                }
                String _writeType_2 = this._ismlModelNavigation.writeType(param_5, true);
                _builder.append(_writeType_2, "\t\t\t");
              }
            }
            _builder.append(">");
          }
        }
        _builder.append(" ");
        {
          String _name_36 = service_1.getName();
          boolean _notEquals_7 = (!Objects.equal(_name_36, null));
          if (_notEquals_7) {
            String _name_37 = service_1.getName();
            String _firstLower_23 = StringExtensions.toFirstLower(_name_37);
            _builder.append(_firstLower_23, "\t\t\t");
          } else {
            _builder.append("set");
            String _name_38 = service_1.getName();
            String _firstLower_24 = StringExtensions.toFirstLower(_name_38);
            _builder.append(_firstLower_24, "\t\t\t");
          }
        }
        _builder.append("){");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("this.");
        {
          String _name_39 = service_1.getName();
          boolean _notEquals_8 = (!Objects.equal(_name_39, null));
          if (_notEquals_8) {
            String _name_40 = service_1.getName();
            String _firstLower_25 = StringExtensions.toFirstLower(_name_40);
            _builder.append(_firstLower_25, "\t\t");
          } else {
            String _name_41 = service_1.getName();
            String _firstLower_26 = StringExtensions.toFirstLower(_name_41);
            _builder.append(_firstLower_26, "\t\t");
          }
        }
        _builder.append("=");
        {
          String _name_42 = service_1.getName();
          boolean _notEquals_9 = (!Objects.equal(_name_42, null));
          if (_notEquals_9) {
            String _name_43 = service_1.getName();
            String _firstLower_27 = StringExtensions.toFirstLower(_name_43);
            _builder.append(_firstLower_27, "\t\t");
          } else {
            String _name_44 = service_1.getName();
            String _firstLower_28 = StringExtensions.toFirstLower(_name_44);
            _builder.append(_firstLower_28, "\t\t");
          }
        }
        _builder.append(";");
        _builder.newLineIfNotEmpty();
        _builder.append("\t\t");
        _builder.append("} ");
        _builder.newLine();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public Map.Entry<String, Type> obtainAttribute(final Parameter parameter) {
    Set<Map.Entry<String, Type>> _entrySet = this.neededAttributes.entrySet();
    for (final Map.Entry<String, Type> entry : _entrySet) {
      Type _value = entry.getValue();
      TypeSpecification _typeSpecification = this._ismlModelNavigation.getTypeSpecification(_value);
      String _typeSpecificationString = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification);
      Type _type = parameter.getType();
      TypeSpecification _typeSpecification_1 = this._ismlModelNavigation.getTypeSpecification(_type);
      String _typeSpecificationString_1 = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification_1);
      boolean _equalsIgnoreCase = _typeSpecificationString.equalsIgnoreCase(_typeSpecificationString_1);
      if (_equalsIgnoreCase) {
        return entry;
      }
    }
    return null;
  }
  
  public boolean actionRequiresReturnSentence(final EList<?> body) {
    boolean requires = true;
    boolean _notEquals = (!Objects.equal(body, null));
    if (_notEquals) {
      for (final Object stmnt : body) {
        boolean _or = false;
        if (((stmnt instanceof Show) || (stmnt instanceof ActionCall))) {
          _or = true;
        } else {
          _or = (stmnt instanceof Return);
        }
        if (_or) {
          requires = false;
        } else {
          if ((stmnt instanceof If)) {
            EList<MethodStatement> _body = ((If)stmnt).getBody();
            boolean _actionRequiresReturnSentence = this.actionRequiresReturnSentence(_body);
            requires = _actionRequiresReturnSentence;
            EList<MethodStatement> _elseBody = ((If)stmnt).getElseBody();
            boolean _notEquals_1 = (!Objects.equal(_elseBody, null));
            if (_notEquals_1) {
              EList<MethodStatement> _elseBody_1 = ((If)stmnt).getElseBody();
              boolean _actionRequiresReturnSentence_1 = this.actionRequiresReturnSentence(_elseBody_1);
              requires = _actionRequiresReturnSentence_1;
            }
          } else {
            if ((stmnt instanceof While)) {
              EList<MethodStatement> _body_1 = ((While)stmnt).getBody();
              boolean _actionRequiresReturnSentence_2 = this.actionRequiresReturnSentence(_body_1);
              requires = _actionRequiresReturnSentence_2;
            } else {
              if ((stmnt instanceof For)) {
                EList<MethodStatement> _body_2 = ((For)stmnt).getBody();
                boolean _actionRequiresReturnSentence_3 = this.actionRequiresReturnSentence(_body_2);
                requires = _actionRequiresReturnSentence_3;
              }
            }
          }
        }
      }
    }
    return requires;
  }
}
