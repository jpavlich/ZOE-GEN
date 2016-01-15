package co.edu.javeriana.zoe.generator.jee7.templates;

import co.edu.javeriana.isml.generator.common.SimpleTemplate;
import co.edu.javeriana.isml.isml.Action;
import co.edu.javeriana.isml.isml.ActionCall;
import co.edu.javeriana.isml.isml.Controller;
import co.edu.javeriana.isml.isml.Entity;
import co.edu.javeriana.isml.isml.Expression;
import co.edu.javeriana.isml.isml.ForView;
import co.edu.javeriana.isml.isml.IfView;
import co.edu.javeriana.isml.isml.LiteralValue;
import co.edu.javeriana.isml.isml.NamedElement;
import co.edu.javeriana.isml.isml.NamedViewBlock;
import co.edu.javeriana.isml.isml.Page;
import co.edu.javeriana.isml.isml.Reference;
import co.edu.javeriana.isml.isml.ResourceReference;
import co.edu.javeriana.isml.isml.Type;
import co.edu.javeriana.isml.isml.TypeSpecification;
import co.edu.javeriana.isml.isml.Variable;
import co.edu.javeriana.isml.isml.VariableReference;
import co.edu.javeriana.isml.isml.VariableTypeElement;
import co.edu.javeriana.isml.isml.View;
import co.edu.javeriana.isml.isml.ViewInstance;
import co.edu.javeriana.isml.isml.ViewStatement;
import co.edu.javeriana.isml.scoping.IsmlModelNavigation;
import co.edu.javeriana.isml.validation.TypeChecker;
import co.edu.javeriana.zoe.generator.jee7.templates.ExpressionTemplate;
import com.google.common.base.Objects;
import com.google.inject.Inject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.ExclusiveRange;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class ZoePagesTemplate extends SimpleTemplate<Page> {
  @Inject
  @Extension
  private TypeChecker _typeChecker;
  
  @Inject
  @Extension
  private IsmlModelNavigation _ismlModelNavigation;
  
  @Inject
  @Extension
  private ExpressionTemplate _expressionTemplate;
  
  private int i;
  
  private Map<ViewInstance, String> forms;
  
  public void preprocess(final Page e) {
    this.i = 1;
    HashMap<ViewInstance, String> _hashMap = new HashMap<ViewInstance, String>();
    this.forms = _hashMap;
  }
  
  public CharSequence template(final Page page) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("\t");
    _builder.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<html xmlns=\"http://www.w3.org/1999/xhtml\"");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("xmlns:ui=\"http://java.sun.com/jsf/facelets\"");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("xmlns:f=\"http://java.sun.com/jsf/core\"");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("xmlns:h=\"http://java.sun.com/jsf/html\"");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("xmlns:c=\"http://java.sun.com/jsp/jstl/core\"");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("xmlns:p=\"http://primefaces.org/ui\">\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<ui:composition template=\"/template.xhtml\">");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<ui:define name=\"body\">");
    _builder.newLine();
    {
      EList<ViewStatement> _body = page.getBody();
      boolean _notEquals = (!Objects.equal(_body, null));
      if (_notEquals) {
        _builder.append("\t\t");
        EList<ViewStatement> _body_1 = page.getBody();
        CharSequence _widgetTemplate = this.widgetTemplate(_body_1);
        _builder.append(_widgetTemplate, "\t\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t");
    _builder.append("</ui:define>\t\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("</ui:composition>\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("</html>\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    return _builder;
  }
  
  protected CharSequence _widgetTemplate(final ViewInstance viewInstance) {
    CharSequence _switchResult = null;
    Type _type = viewInstance.getType();
    TypeSpecification _typeSpecification = this._ismlModelNavigation.getTypeSpecification(_type);
    String _typeSpecificationString = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification);
    boolean _matched = false;
    if (!_matched) {
      if (Objects.equal(_typeSpecificationString, "Label")) {
        _matched=true;
        _switchResult = this.label(viewInstance);
      }
    }
    if (!_matched) {
      if (Objects.equal(_typeSpecificationString, "Text")) {
        _matched=true;
        _switchResult = this.inputText(viewInstance);
      }
    }
    if (!_matched) {
      if (Objects.equal(_typeSpecificationString, "Button")) {
        _matched=true;
        _switchResult = this.button(viewInstance);
      }
    }
    if (!_matched) {
      if (Objects.equal(_typeSpecificationString, "Form")) {
        _matched=true;
        _switchResult = this.form(viewInstance);
      }
    }
    if (!_matched) {
      if (Objects.equal(_typeSpecificationString, "Panel")) {
        _matched=true;
        _switchResult = this.panel(viewInstance);
      }
    }
    if (!_matched) {
      if (Objects.equal(_typeSpecificationString, "RadioChooser")) {
        _matched=true;
        _switchResult = this.radioChooser(viewInstance);
      }
    }
    if (!_matched) {
      if (Objects.equal(_typeSpecificationString, "Image")) {
        _matched=true;
        _switchResult = this.image(viewInstance);
      }
    }
    if (!_matched) {
      if (Objects.equal(_typeSpecificationString, "PanelButton")) {
        _matched=true;
        _switchResult = this.panelButton(viewInstance);
      }
    }
    if (!_matched) {
      if (Objects.equal(_typeSpecificationString, "DataTable")) {
        _matched=true;
        _switchResult = this.dataTable(viewInstance);
      }
    }
    if (!_matched) {
      if (Objects.equal(_typeSpecificationString, "Password")) {
        _matched=true;
        _switchResult = this.password(viewInstance);
      }
    }
    if (!_matched) {
      if (Objects.equal(_typeSpecificationString, "CheckBox")) {
        _matched=true;
        _switchResult = this.checkBox(viewInstance);
      }
    }
    if (!_matched) {
      if (Objects.equal(_typeSpecificationString, "Calendar")) {
        _matched=true;
        _switchResult = this.calendar(viewInstance);
      }
    }
    if (!_matched) {
      if (Objects.equal(_typeSpecificationString, "Link")) {
        _matched=true;
        _switchResult = this.link(viewInstance);
      }
    }
    if (!_matched) {
      if (Objects.equal(_typeSpecificationString, "ComboChooser")) {
        _matched=true;
        _switchResult = this.comboChooser(viewInstance);
      }
    }
    if (!_matched) {
      if (Objects.equal(_typeSpecificationString, "ListChooser")) {
        _matched=true;
        _switchResult = this.listChooser(viewInstance);
      }
    }
    if (!_matched) {
      if (Objects.equal(_typeSpecificationString, "Spinner")) {
        _matched=true;
        _switchResult = this.spinner(viewInstance);
      }
    }
    if (!_matched) {
      if (Objects.equal(_typeSpecificationString, "PickList")) {
        _matched=true;
        _switchResult = this.pickList(viewInstance);
      }
    }
    if (!_matched) {
      if (Objects.equal(_typeSpecificationString, "OutputText")) {
        _matched=true;
        _switchResult = this.outputText(viewInstance);
      }
    }
    if (!_matched) {
      if (Objects.equal(_typeSpecificationString, "Map")) {
        _matched=true;
        _switchResult = this.outputText(viewInstance);
      }
    }
    return _switchResult;
  }
  
  public CharSequence outputText(final ViewInstance part) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<p:outputText id= \"");
    String _id = this.getId(part);
    _builder.append(_id, "");
    _builder.append("\" label=");
    EList<Expression> _parameters = part.getParameters();
    Expression _get = _parameters.get(0);
    CharSequence _writeExpression = this._expressionTemplate.writeExpression(_get);
    _builder.append(_writeExpression, "");
    _builder.append(" value=");
    EList<Expression> _parameters_1 = part.getParameters();
    Expression _get_1 = _parameters_1.get(1);
    CharSequence _valueTemplate = this.valueTemplate(_get_1);
    _builder.append(_valueTemplate, "");
    _builder.append("/>");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence pickList(final ViewInstance part) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<p:pickList id=\"");
    String _id = this.getId(part);
    _builder.append(_id, "");
    _builder.append("\"");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("itemLabel=");
    EList<Expression> _parameters = part.getParameters();
    Expression _get = _parameters.get(0);
    CharSequence _writeExpression = this._expressionTemplate.writeExpression(_get);
    _builder.append(_writeExpression, "\t");
    _builder.append(" itemValue=\"#{pickValue}\"");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("value=");
    {
      EList<Expression> _parameters_1 = part.getParameters();
      Expression _get_1 = _parameters_1.get(1);
      if ((_get_1 instanceof ResourceReference)) {
        EList<Expression> _parameters_2 = part.getParameters();
        Expression _get_2 = _parameters_2.get(1);
        CharSequence _writeExpression_1 = this._expressionTemplate.writeExpression(_get_2);
        _builder.append(_writeExpression_1, "\t");
      } else {
        _builder.append("\"#{");
        Controller _containerController = this._ismlModelNavigation.getContainerController(part);
        String _name = _containerController.getName();
        String _firstLower = StringExtensions.toFirstLower(_name);
        _builder.append(_firstLower, "\t");
        _builder.append(".");
        EList<Expression> _parameters_3 = part.getParameters();
        Expression _get_3 = _parameters_3.get(1);
        CharSequence _writeExpression_2 = this._expressionTemplate.writeExpression(_get_3);
        _builder.append(_writeExpression_2, "\t");
        _builder.append("}\"");
      }
    }
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("var=\"");
    Controller _containerController_1 = this._ismlModelNavigation.getContainerController(part);
    String _name_1 = _containerController_1.getName();
    _builder.append(_name_1, "\t");
    _builder.append("\"></p:pickList>");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence listChooser(final ViewInstance part) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  public CharSequence spinner(final ViewInstance part) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<p:spinner id=\"");
    String _id = this.getId(part);
    _builder.append(_id, "");
    _builder.append("\" label=");
    EList<Expression> _parameters = part.getParameters();
    Expression _get = _parameters.get(0);
    CharSequence _writeExpression = this._expressionTemplate.writeExpression(_get);
    _builder.append(_writeExpression, "");
    _builder.append(" title=");
    EList<Expression> _parameters_1 = part.getParameters();
    Expression _get_1 = _parameters_1.get(0);
    CharSequence _writeExpression_1 = this._expressionTemplate.writeExpression(_get_1);
    _builder.append(_writeExpression_1, "");
    _builder.append(" ");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("value=");
    {
      EList<Expression> _parameters_2 = part.getParameters();
      Expression _get_2 = _parameters_2.get(1);
      if ((_get_2 instanceof ResourceReference)) {
        EList<Expression> _parameters_3 = part.getParameters();
        Expression _get_3 = _parameters_3.get(1);
        CharSequence _writeExpression_2 = this._expressionTemplate.writeExpression(_get_3);
        _builder.append(_writeExpression_2, "\t");
      } else {
        _builder.append("\"#{");
        Controller _containerController = this._ismlModelNavigation.getContainerController(part);
        String _name = _containerController.getName();
        String _firstLower = StringExtensions.toFirstLower(_name);
        _builder.append(_firstLower, "\t");
        _builder.append(".");
        EList<Expression> _parameters_4 = part.getParameters();
        Expression _get_4 = _parameters_4.get(1);
        CharSequence _writeExpression_3 = this._expressionTemplate.writeExpression(_get_4);
        _builder.append(_writeExpression_3, "\t");
        _builder.append("}\"");
      }
    }
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("stepFactor=\"");
    EList<Expression> _parameters_5 = part.getParameters();
    Expression _get_5 = _parameters_5.get(2);
    CharSequence _writeExpression_4 = this._expressionTemplate.writeExpression(_get_5);
    _builder.append(_writeExpression_4, "\t");
    _builder.append("\" min=\"");
    EList<Expression> _parameters_6 = part.getParameters();
    Expression _get_6 = _parameters_6.get(3);
    CharSequence _writeExpression_5 = this._expressionTemplate.writeExpression(_get_6);
    _builder.append(_writeExpression_5, "\t");
    _builder.append("\" max=\"");
    EList<Expression> _parameters_7 = part.getParameters();
    Expression _get_7 = _parameters_7.get(4);
    CharSequence _writeExpression_6 = this._expressionTemplate.writeExpression(_get_7);
    _builder.append(_writeExpression_6, "\t");
    _builder.append("\" prefix=");
    EList<Expression> _parameters_8 = part.getParameters();
    Expression _get_8 = _parameters_8.get(5);
    CharSequence _writeExpression_7 = this._expressionTemplate.writeExpression(_get_8);
    _builder.append(_writeExpression_7, "\t");
    _builder.append("/>");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence image(final ViewInstance part) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<p:commandLink id=\"");
    String _id = this.getId(part);
    _builder.append(_id, "");
    _builder.append("\" ");
    {
      ActionCall _actionCall = part.getActionCall();
      boolean _notEquals = (!Objects.equal(_actionCall, null));
      if (_notEquals) {
        _builder.append("action=\"#{");
        ActionCall _actionCall_1 = part.getActionCall();
        Action _referencedElement = _actionCall_1.getReferencedElement();
        EObject _eContainer = _referencedElement.eContainer();
        String _name = ((Controller) _eContainer).getName();
        String _firstLower = StringExtensions.toFirstLower(_name);
        _builder.append(_firstLower, "");
        _builder.append(".");
        ActionCall _actionCall_2 = part.getActionCall();
        Action _referencedElement_1 = _actionCall_2.getReferencedElement();
        String _name_1 = _referencedElement_1.getName();
        _builder.append(_name_1, "");
        _builder.append("()}\"");
      }
    }
    _builder.append(">");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<img id=\"");
    String _id_1 = this.getId(part);
    _builder.append(_id_1, "\t");
    _builder.append("Image\" src=");
    EList<Expression> _parameters = part.getParameters();
    Expression _get = _parameters.get(0);
    CharSequence _writeExpression = this._expressionTemplate.writeExpression(_get);
    _builder.append(_writeExpression, "\t");
    _builder.append("/>");
    _builder.newLineIfNotEmpty();
    _builder.append("</p:commandLink>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence calendar(final ViewInstance part) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<p:calendar id= \"");
    String _id = this.getId(part);
    _builder.append(_id, "");
    _builder.append("\" label=");
    EList<Expression> _parameters = part.getParameters();
    Expression _get = _parameters.get(0);
    CharSequence _writeExpression = this._expressionTemplate.writeExpression(_get);
    _builder.append(_writeExpression, "");
    _builder.append(" ");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("value=");
    {
      EList<Expression> _parameters_1 = part.getParameters();
      Expression _get_1 = _parameters_1.get(1);
      if ((_get_1 instanceof ResourceReference)) {
        EList<Expression> _parameters_2 = part.getParameters();
        Expression _get_2 = _parameters_2.get(1);
        CharSequence _writeExpression_1 = this._expressionTemplate.writeExpression(_get_2);
        _builder.append(_writeExpression_1, "\t");
      } else {
        _builder.append("\"#{");
        Controller _containerController = this._ismlModelNavigation.getContainerController(part);
        String _name = _containerController.getName();
        String _firstLower = StringExtensions.toFirstLower(_name);
        _builder.append(_firstLower, "\t");
        _builder.append(".");
        EList<Expression> _parameters_3 = part.getParameters();
        Expression _get_3 = _parameters_3.get(1);
        CharSequence _writeExpression_2 = this._expressionTemplate.writeExpression(_get_3);
        _builder.append(_writeExpression_2, "\t");
        _builder.append("}\"");
      }
    }
    _builder.append(" pattern=");
    EList<Expression> _parameters_4 = part.getParameters();
    Expression _get_4 = _parameters_4.get(3);
    CharSequence _writeExpression_3 = this._expressionTemplate.writeExpression(_get_4);
    _builder.append(_writeExpression_3, "\t");
    _builder.append(" ");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("navigator=\"");
    EList<Expression> _parameters_5 = part.getParameters();
    Expression _get_5 = _parameters_5.get(4);
    CharSequence _writeExpression_4 = this._expressionTemplate.writeExpression(_get_5);
    _builder.append(_writeExpression_4, "\t");
    _builder.append("\" mode=");
    EList<Expression> _parameters_6 = part.getParameters();
    Expression _get_6 = _parameters_6.get(5);
    CharSequence _writeExpression_5 = this._expressionTemplate.writeExpression(_get_6);
    _builder.append(_writeExpression_5, "\t");
    _builder.append(" />");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence checkBox(final ViewInstance part) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<p:selectBooleanCheckbox id= \"");
    String _id = this.getId(part);
    _builder.append(_id, "");
    _builder.append("\" label=");
    EList<Expression> _parameters = part.getParameters();
    Expression _get = _parameters.get(0);
    CharSequence _writeExpression = this._expressionTemplate.writeExpression(_get);
    _builder.append(_writeExpression, "");
    _builder.append(" value=\"#{");
    Controller _containerController = this._ismlModelNavigation.getContainerController(part);
    String _name = _containerController.getName();
    String _firstLower = StringExtensions.toFirstLower(_name);
    _builder.append(_firstLower, "");
    _builder.append(".");
    EList<Expression> _parameters_1 = part.getParameters();
    Expression _get_1 = _parameters_1.get(1);
    CharSequence _writeExpression_1 = this._expressionTemplate.writeExpression(_get_1);
    _builder.append(_writeExpression_1, "");
    _builder.append("}\"/>\t\t");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence link(final ViewInstance part) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<h:outputLink id=\"");
    String _id = this.getId(part);
    _builder.append(_id, "");
    _builder.append("\"  value=");
    EList<Expression> _parameters = part.getParameters();
    Expression _get = _parameters.get(1);
    CharSequence _writeExpression = this._expressionTemplate.writeExpression(_get);
    _builder.append(_writeExpression, "");
    _builder.append(">");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<h:outputText value=");
    EList<Expression> _parameters_1 = part.getParameters();
    Expression _get_1 = _parameters_1.get(0);
    CharSequence _writeExpression_1 = this._expressionTemplate.writeExpression(_get_1);
    _builder.append(_writeExpression_1, "\t");
    _builder.append(" />");
    _builder.newLineIfNotEmpty();
    _builder.append("</h:outputLink>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence comboChooser(final ViewInstance part) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _and = false;
      EList<Expression> _parameters = part.getParameters();
      Expression _get = _parameters.get(1);
      CharSequence _writeExpression = this._expressionTemplate.writeExpression(_get);
      if (!(_writeExpression instanceof Reference)) {
        _and = false;
      } else {
        EList<Expression> _parameters_1 = part.getParameters();
        Expression _get_1 = _parameters_1.get(1);
        CharSequence _writeExpression_1 = this._expressionTemplate.writeExpression(_get_1);
        NamedElement _referencedElement = ((Reference) _writeExpression_1).getReferencedElement();
        Type _type = this._typeChecker.getType(_referencedElement);
        TypeSpecification _typeSpecification = this._ismlModelNavigation.getTypeSpecification(_type);
        _and = (_typeSpecification instanceof Entity);
      }
      if (_and) {
        _builder.append("<h:entitySelectOneMenu id=\"");
        String _id = this.getId(part);
        _builder.append(_id, "");
        _builder.append("\" label=");
        EList<Expression> _parameters_2 = part.getParameters();
        Expression _get_2 = _parameters_2.get(0);
        CharSequence _writeExpression_2 = this._expressionTemplate.writeExpression(_get_2);
        _builder.append(_writeExpression_2, "");
        _builder.append(" ");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("valueList=\"#{");
        Controller _containerController = this._ismlModelNavigation.getContainerController(part);
        String _name = _containerController.getName();
        String _firstLower = StringExtensions.toFirstLower(_name);
        _builder.append(_firstLower, "\t");
        _builder.append(".");
        EList<Expression> _parameters_3 = part.getParameters();
        Expression _get_3 = _parameters_3.get(1);
        CharSequence _writeExpression_3 = this._expressionTemplate.writeExpression(_get_3);
        _builder.append(_writeExpression_3, "\t");
        _builder.append("}\" value=");
        {
          EList<Expression> _parameters_4 = part.getParameters();
          Expression _get_4 = _parameters_4.get(2);
          if ((_get_4 instanceof ResourceReference)) {
            EList<Expression> _parameters_5 = part.getParameters();
            Expression _get_5 = _parameters_5.get(2);
            CharSequence _writeExpression_4 = this._expressionTemplate.writeExpression(_get_5);
            _builder.append(_writeExpression_4, "\t");
          } else {
            _builder.append("\"#{");
            Controller _containerController_1 = this._ismlModelNavigation.getContainerController(part);
            String _name_1 = _containerController_1.getName();
            String _firstLower_1 = StringExtensions.toFirstLower(_name_1);
            _builder.append(_firstLower_1, "\t");
            _builder.append(".");
            EList<Expression> _parameters_6 = part.getParameters();
            Expression _get_6 = _parameters_6.get(2);
            CharSequence _writeExpression_5 = this._expressionTemplate.writeExpression(_get_6);
            _builder.append(_writeExpression_5, "\t");
            _builder.append("}\"");
          }
        }
        _builder.append(" noSelectionLabel=");
        EList<Expression> _parameters_7 = part.getParameters();
        Expression _get_7 = _parameters_7.get(3);
        CharSequence _writeExpression_6 = this._expressionTemplate.writeExpression(_get_7);
        _builder.append(_writeExpression_6, "\t");
        _builder.append(" labelSelection=\"#{_eachItem}\"/>");
        _builder.newLineIfNotEmpty();
      } else {
        {
          boolean _and_1 = false;
          EList<Expression> _parameters_8 = part.getParameters();
          Expression _get_8 = _parameters_8.get(1);
          CharSequence _writeExpression_7 = this._expressionTemplate.writeExpression(_get_8);
          if (!(_writeExpression_7 instanceof Reference)) {
            _and_1 = false;
          } else {
            EList<Expression> _parameters_9 = part.getParameters();
            Expression _get_9 = _parameters_9.get(1);
            CharSequence _writeExpression_8 = this._expressionTemplate.writeExpression(_get_9);
            NamedElement _referencedElement_1 = ((Reference) _writeExpression_8).getReferencedElement();
            Type _type_1 = this._typeChecker.getType(_referencedElement_1);
            TypeSpecification _typeSpecification_1 = this._ismlModelNavigation.getTypeSpecification(_type_1);
            _and_1 = (_typeSpecification_1 instanceof co.edu.javeriana.isml.isml.Enum);
          }
          if (_and_1) {
            _builder.append("<lion:enumSelectOneMenu id=\"");
            String _id_1 = this.getId(part);
            _builder.append(_id_1, "");
            _builder.append("\" label=");
            EList<Expression> _parameters_10 = part.getParameters();
            Expression _get_10 = _parameters_10.get(0);
            CharSequence _writeExpression_9 = this._expressionTemplate.writeExpression(_get_10);
            _builder.append(_writeExpression_9, "");
            _builder.append(" ");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("valueList=\"#{");
            Controller _containerController_2 = this._ismlModelNavigation.getContainerController(part);
            String _name_2 = _containerController_2.getName();
            String _firstLower_2 = StringExtensions.toFirstLower(_name_2);
            _builder.append(_firstLower_2, "\t");
            _builder.append(".");
            EList<Expression> _parameters_11 = part.getParameters();
            Expression _get_11 = _parameters_11.get(1);
            CharSequence _writeExpression_10 = this._expressionTemplate.writeExpression(_get_11);
            _builder.append(_writeExpression_10, "\t");
            _builder.append("}\" value=");
            {
              EList<Expression> _parameters_12 = part.getParameters();
              Expression _get_12 = _parameters_12.get(2);
              if ((_get_12 instanceof ResourceReference)) {
                EList<Expression> _parameters_13 = part.getParameters();
                Expression _get_13 = _parameters_13.get(2);
                CharSequence _writeExpression_11 = this._expressionTemplate.writeExpression(_get_13);
                _builder.append(_writeExpression_11, "\t");
              } else {
                _builder.append("\"#{");
                Controller _containerController_3 = this._ismlModelNavigation.getContainerController(part);
                String _name_3 = _containerController_3.getName();
                String _firstLower_3 = StringExtensions.toFirstLower(_name_3);
                _builder.append(_firstLower_3, "\t");
                _builder.append(".");
                EList<Expression> _parameters_14 = part.getParameters();
                Expression _get_14 = _parameters_14.get(2);
                CharSequence _writeExpression_12 = this._expressionTemplate.writeExpression(_get_14);
                _builder.append(_writeExpression_12, "\t");
                _builder.append("}\"");
              }
            }
            _builder.append(" noSelectionLabel=");
            EList<Expression> _parameters_15 = part.getParameters();
            Expression _get_15 = _parameters_15.get(3);
            CharSequence _writeExpression_13 = this._expressionTemplate.writeExpression(_get_15);
            _builder.append(_writeExpression_13, "\t");
            _builder.append(" labelSelection=\"#{_eachItem}\"");
            _builder.newLineIfNotEmpty();
          } else {
            _builder.append("<lion:objectSelectOneMenu id=\"");
            String _id_2 = this.getId(part);
            _builder.append(_id_2, "");
            _builder.append("\" label=");
            EList<Expression> _parameters_16 = part.getParameters();
            Expression _get_16 = _parameters_16.get(0);
            CharSequence _writeExpression_14 = this._expressionTemplate.writeExpression(_get_16);
            _builder.append(_writeExpression_14, "");
            _builder.append(" ");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("valueList=\"#{");
            Controller _containerController_4 = this._ismlModelNavigation.getContainerController(part);
            String _name_4 = _containerController_4.getName();
            String _firstLower_4 = StringExtensions.toFirstLower(_name_4);
            _builder.append(_firstLower_4, "\t");
            _builder.append(".");
            EList<Expression> _parameters_17 = part.getParameters();
            Expression _get_17 = _parameters_17.get(1);
            CharSequence _writeExpression_15 = this._expressionTemplate.writeExpression(_get_17);
            _builder.append(_writeExpression_15, "\t");
            _builder.append("}\" value=");
            {
              EList<Expression> _parameters_18 = part.getParameters();
              Expression _get_18 = _parameters_18.get(2);
              if ((_get_18 instanceof ResourceReference)) {
                EList<Expression> _parameters_19 = part.getParameters();
                Expression _get_19 = _parameters_19.get(2);
                CharSequence _writeExpression_16 = this._expressionTemplate.writeExpression(_get_19);
                _builder.append(_writeExpression_16, "\t");
              } else {
                _builder.append("\"#{");
                Controller _containerController_5 = this._ismlModelNavigation.getContainerController(part);
                String _name_5 = _containerController_5.getName();
                String _firstLower_5 = StringExtensions.toFirstLower(_name_5);
                _builder.append(_firstLower_5, "\t");
                _builder.append(".");
                EList<Expression> _parameters_20 = part.getParameters();
                Expression _get_20 = _parameters_20.get(2);
                CharSequence _writeExpression_17 = this._expressionTemplate.writeExpression(_get_20);
                _builder.append(_writeExpression_17, "\t");
                _builder.append("}\"");
              }
            }
            _builder.append(" noSelectionLabel=");
            EList<Expression> _parameters_21 = part.getParameters();
            Expression _get_21 = _parameters_21.get(3);
            CharSequence _writeExpression_18 = this._expressionTemplate.writeExpression(_get_21);
            _builder.append(_writeExpression_18, "\t");
            _builder.append(" labelSelection=\"#{_eachItem}\"/>");
            _builder.newLineIfNotEmpty();
          }
        }
      }
    }
    return _builder;
  }
  
  public CharSequence password(final ViewInstance part) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<p:password  id= \"");
    String _id = this.getId(part);
    _builder.append(_id, "");
    _builder.append("\" label=");
    EList<Expression> _parameters = part.getParameters();
    Expression _get = _parameters.get(0);
    CharSequence _writeExpression = this._expressionTemplate.writeExpression(_get);
    _builder.append(_writeExpression, "");
    _builder.append(" value=");
    {
      EList<Expression> _parameters_1 = part.getParameters();
      Expression _get_1 = _parameters_1.get(1);
      if ((_get_1 instanceof ResourceReference)) {
        EList<Expression> _parameters_2 = part.getParameters();
        Expression _get_2 = _parameters_2.get(1);
        CharSequence _writeExpression_1 = this._expressionTemplate.writeExpression(_get_2);
        _builder.append(_writeExpression_1, "");
      } else {
        _builder.append("\"#{");
        Controller _containerController = this._ismlModelNavigation.getContainerController(part);
        String _name = _containerController.getName();
        String _firstLower = StringExtensions.toFirstLower(_name);
        _builder.append(_firstLower, "");
        _builder.append(".");
        EList<Expression> _parameters_3 = part.getParameters();
        Expression _get_3 = _parameters_3.get(1);
        CharSequence _writeExpression_2 = this._expressionTemplate.writeExpression(_get_3);
        _builder.append(_writeExpression_2, "");
        _builder.append("}\"");
      }
    }
    _builder.append("/>");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  protected CharSequence _widgetTemplate(final IfView table) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  protected CharSequence _widgetTemplate(final ForView table) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  protected CharSequence _widgetTemplate(final Reference table) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  public CharSequence label(final ViewInstance part) {
    StringConcatenation _builder = new StringConcatenation();
    {
      ActionCall _actionCall = part.getActionCall();
      boolean _equals = Objects.equal(_actionCall, null);
      if (_equals) {
        _builder.append("<p:outputLabel id= \"");
        String _id = this.getId(part);
        _builder.append(_id, "");
        _builder.append("\" value=");
        EList<Expression> _parameters = part.getParameters();
        Expression _get = _parameters.get(0);
        CharSequence _valueTemplate = this.valueTemplate(_get);
        _builder.append(_valueTemplate, "");
        _builder.append(" />");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("<p:commandLink id=\"");
        String _id_1 = this.getId(part);
        _builder.append(_id_1, "");
        _builder.append("\" value=");
        EList<Expression> _parameters_1 = part.getParameters();
        Expression _get_1 = _parameters_1.get(0);
        CharSequence _valueTemplate_1 = this.valueTemplate(_get_1);
        _builder.append(_valueTemplate_1, "");
        _builder.append(" action=\"#{");
        ActionCall _actionCall_1 = part.getActionCall();
        Action _referencedElement = _actionCall_1.getReferencedElement();
        EObject _eContainer = _referencedElement.eContainer();
        String _name = ((Controller) _eContainer).getName();
        String _firstLower = StringExtensions.toFirstLower(_name);
        _builder.append(_firstLower, "");
        _builder.append(".");
        ActionCall _actionCall_2 = part.getActionCall();
        Action _referencedElement_1 = _actionCall_2.getReferencedElement();
        String _name_1 = _referencedElement_1.getName();
        _builder.append(_name_1, "");
        _builder.append("()}\"/>");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence inputText(final ViewInstance part) {
    StringConcatenation _builder = new StringConcatenation();
    {
      int _rows = this._ismlModelNavigation.getRows(part);
      boolean _lessEqualsThan = (_rows <= 1);
      if (_lessEqualsThan) {
        _builder.append("<p:inputText id= \"");
        String _id = this.getId(part);
        _builder.append(_id, "");
        _builder.append("\" label=");
        EList<Expression> _parameters = part.getParameters();
        Expression _get = _parameters.get(0);
        CharSequence _writeExpression = this._expressionTemplate.writeExpression(_get);
        _builder.append(_writeExpression, "");
        _builder.append(" value=");
        EList<Expression> _parameters_1 = part.getParameters();
        Expression _get_1 = _parameters_1.get(1);
        CharSequence _valueTemplate = this.valueTemplate(_get_1);
        _builder.append(_valueTemplate, "");
        _builder.append("/>");
        _builder.newLineIfNotEmpty();
      } else {
        _builder.append("<p:inputTextArea id=\"");
        String _id_1 = this.getId(part);
        _builder.append(_id_1, "");
        _builder.append("\" label=");
        EList<Expression> _parameters_2 = part.getParameters();
        Expression _get_2 = _parameters_2.get(0);
        CharSequence _writeExpression_1 = this._expressionTemplate.writeExpression(_get_2);
        _builder.append(_writeExpression_1, "");
        _builder.append(" value=");
        {
          EList<Expression> _parameters_3 = part.getParameters();
          Expression _get_3 = _parameters_3.get(1);
          if ((_get_3 instanceof ResourceReference)) {
            EList<Expression> _parameters_4 = part.getParameters();
            Expression _get_4 = _parameters_4.get(1);
            CharSequence _writeExpression_2 = this._expressionTemplate.writeExpression(_get_4);
            _builder.append(_writeExpression_2, "");
          } else {
            _builder.append("\"#{");
            Controller _containerController = this._ismlModelNavigation.getContainerController(part);
            String _name = _containerController.getName();
            String _firstLower = StringExtensions.toFirstLower(_name);
            _builder.append(_firstLower, "");
            _builder.append(".");
            EList<Expression> _parameters_5 = part.getParameters();
            Expression _get_5 = _parameters_5.get(1);
            CharSequence _writeExpression_3 = this._expressionTemplate.writeExpression(_get_5);
            _builder.append(_writeExpression_3, "");
            _builder.append("}\"");
          }
        }
        _builder.append(" />");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence button(final ViewInstance part) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("<p:commandButton id= \"");
    String _id = this.getId(part);
    _builder.append(_id, "");
    _builder.append("\" value=");
    EList<Expression> _parameters = part.getParameters();
    Expression _get = _parameters.get(0);
    CharSequence _writeExpression = this._expressionTemplate.writeExpression(_get);
    _builder.append(_writeExpression, "");
    _builder.append(" ");
    _builder.newLineIfNotEmpty();
    _builder.append(" ");
    _builder.append("action= \"#{");
    Controller _containerController = this._ismlModelNavigation.getContainerController(part);
    String _name = _containerController.getName();
    String _firstLower = StringExtensions.toFirstLower(_name);
    _builder.append(_firstLower, " ");
    _builder.append(".");
    ActionCall _actionCall = part.getActionCall();
    Action _action = null;
    if (_actionCall!=null) {
      _action=this._ismlModelNavigation.getAction(_actionCall);
    }
    String _name_1 = null;
    if (_action!=null) {
      _name_1=_action.getName();
    }
    _builder.append(_name_1, " ");
    _builder.append("(");
    {
      ActionCall _actionCall_1 = part.getActionCall();
      EList<Expression> _parameters_1 = _actionCall_1.getParameters();
      boolean _hasElements = false;
      for(final Expression param : _parameters_1) {
        if (!_hasElements) {
          _hasElements = true;
        } else {
          _builder.appendImmediate(",", " ");
        }
        {
          boolean _and = false;
          if (!(param instanceof VariableReference)) {
            _and = false;
          } else {
            VariableTypeElement _referencedElement = ((VariableReference) param).getReferencedElement();
            EObject _eContainer = _referencedElement.eContainer();
            _and = (_eContainer instanceof Page);
          }
          if (_and) {
            Page _findAncestor = this._ismlModelNavigation.<ViewInstance, Page>findAncestor(part, Page.class);
            Controller _controller = ((Page) _findAncestor).getController();
            String _name_2 = _controller.getName();
            String _firstLower_1 = StringExtensions.toFirstLower(_name_2);
            _builder.append(_firstLower_1, " ");
            _builder.append(".");
          }
        }
        CharSequence _writeExpression_1 = this._expressionTemplate.writeExpression(param);
        _builder.append(_writeExpression_1, " ");
      }
    }
    _builder.append(")}\" update=\"");
    CharSequence _updateTemplate = this.updateTemplate(part);
    _builder.append(_updateTemplate, " ");
    _builder.append("\"  async=\"true\" ");
    {
      EList<Expression> _parameters_2 = part.getParameters();
      Expression _get_1 = _parameters_2.get(1);
      Object _literal = ((LiteralValue) _get_1).getLiteral();
      boolean _equals = _literal.equals("false");
      if (_equals) {
        _builder.append("immediate=\"true\"");
      }
    }
    _builder.append("/>");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence updateTemplate(final ViewInstance part) {
    EObject tmp = part;
    while ((!Objects.equal(tmp, null))) {
      {
        boolean _and = false;
        boolean _and_1 = false;
        boolean _notEquals = (!Objects.equal(tmp, null));
        if (!_notEquals) {
          _and_1 = false;
        } else {
          _and_1 = (tmp instanceof ViewInstance);
        }
        if (!_and_1) {
          _and = false;
        } else {
          Type _type = ((ViewInstance) tmp).getType();
          TypeSpecification _typeSpecification = this._ismlModelNavigation.getTypeSpecification(_type);
          String _typeSpecificationString = this._ismlModelNavigation.getTypeSpecificationString(_typeSpecification);
          boolean _equals = _typeSpecificationString.equals("Form");
          _and = _equals;
        }
        if (_and) {
          String _get = this.forms.get(((ViewInstance) tmp));
          return (":" + _get);
        }
        EObject _eContainer = tmp.eContainer();
        tmp = _eContainer;
      }
    }
    return null;
  }
  
  public CharSequence form(final ViewInstance viewInstance) {
    StringConcatenation _builder = new StringConcatenation();
    final String id = this.getId(viewInstance);
    _builder.newLineIfNotEmpty();
    String _put = this.forms.put(viewInstance, id);
    _builder.append(_put, "");
    _builder.newLineIfNotEmpty();
    _builder.append("<h:form id= \"");
    _builder.append(id, "");
    _builder.append("\">");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    {
      EList<ViewStatement> _body = viewInstance.getBody();
      for(final ViewStatement partBlock : _body) {
        _builder.append("\t\t");
        CharSequence _widgetTemplate = this.widgetTemplate(partBlock);
        _builder.append(_widgetTemplate, "\t\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("</h:form>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence Map(final ViewInstance part) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.newLine();
    _builder.append("<p:gmap id= \"");
    String _id = this.getId(part);
    _builder.append(_id, "");
    _builder.append("\" center=\"41.381542, 2.122893\" zoom=\"15\" type=\"HYBRID\" style=\"width:100%;height:400px\" />\t\t");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence radioChooser(final ViewInstance part) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<p:selectOneRadio id=\"");
    String _id = this.getId(part);
    _builder.append(_id, "");
    _builder.append("\" label=");
    EList<Expression> _parameters = part.getParameters();
    Expression _get = _parameters.get(0);
    CharSequence _writeExpression = this._expressionTemplate.writeExpression(_get);
    _builder.append(_writeExpression, "");
    _builder.append("  value=");
    {
      EList<Expression> _parameters_1 = part.getParameters();
      Expression _get_1 = _parameters_1.get(2);
      if ((_get_1 instanceof ResourceReference)) {
        EList<Expression> _parameters_2 = part.getParameters();
        Expression _get_2 = _parameters_2.get(2);
        CharSequence _writeExpression_1 = this._expressionTemplate.writeExpression(_get_2);
        _builder.append(_writeExpression_1, "");
      } else {
        _builder.append("\"#{");
        Controller _containerController = this._ismlModelNavigation.getContainerController(part);
        String _name = _containerController.getName();
        String _firstLower = StringExtensions.toFirstLower(_name);
        _builder.append(_firstLower, "");
        _builder.append(".");
        EList<Expression> _parameters_3 = part.getParameters();
        Expression _get_3 = _parameters_3.get(2);
        CharSequence _writeExpression_2 = this._expressionTemplate.writeExpression(_get_3);
        _builder.append(_writeExpression_2, "");
        _builder.append("}\"");
      }
    }
    _builder.newLineIfNotEmpty();
    _builder.append("valueList=\"#{");
    Controller _containerController_1 = this._ismlModelNavigation.getContainerController(part);
    String _name_1 = _containerController_1.getName();
    String _firstLower_1 = StringExtensions.toFirstLower(_name_1);
    _builder.append(_firstLower_1, "");
    _builder.append(".");
    EList<Expression> _parameters_4 = part.getParameters();
    Expression _get_4 = _parameters_4.get(1);
    CharSequence _writeExpression_3 = this._expressionTemplate.writeExpression(_get_4);
    _builder.append(_writeExpression_3, "");
    _builder.append("}\"/> ");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence panel(final ViewInstance viewInstance) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<p:panel id= \"");
    String _id = this.getId(viewInstance);
    _builder.append(_id, "");
    _builder.append("\" >");
    _builder.newLineIfNotEmpty();
    {
      EList<ViewStatement> _body = viewInstance.getBody();
      for(final ViewStatement partBlock : _body) {
        CharSequence _widgetTemplate = this.widgetTemplate(partBlock);
        _builder.append(_widgetTemplate, "");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t\t\t");
    _builder.append("</p:panel>\t");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence panelButton(final ViewInstance viewAttribute) {
    StringConcatenation _builder = new StringConcatenation();
    return _builder;
  }
  
  public CharSequence dataTable(final ViewInstance table) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<p:dataTable  id= \"");
    String _id = this.getId(table);
    _builder.append(_id, "");
    _builder.append("\" paginator=\"true\" paginatorPosition=\"bottom\" var=\"");
    ForView _forViewInBody = this._ismlModelNavigation.getForViewInBody(table);
    Variable _variable = _forViewInBody.getVariable();
    String _name = _variable.getName();
    _builder.append(_name, "");
    _builder.append("\" ");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t\t\t\t ");
    _builder.append("value=\"#{");
    Controller _containerController = this._ismlModelNavigation.getContainerController(table);
    String _name_1 = _containerController.getName();
    String _firstLower = StringExtensions.toFirstLower(_name_1);
    _builder.append(_firstLower, "\t\t\t\t ");
    _builder.append(".");
    ForView _forViewInBody_1 = this._ismlModelNavigation.getForViewInBody(table);
    Reference _collection = null;
    if (_forViewInBody_1!=null) {
      _collection=_forViewInBody_1.getCollection();
    }
    NamedElement _referencedElement = _collection.getReferencedElement();
    String _name_2 = _referencedElement.getName();
    _builder.append(_name_2, "\t\t\t\t ");
    _builder.append("}\" selection= \"#{");
    Controller _containerController_1 = this._ismlModelNavigation.getContainerController(table);
    String _name_3 = _containerController_1.getName();
    String _firstLower_1 = StringExtensions.toFirstLower(_name_3);
    _builder.append(_firstLower_1, "\t\t\t\t ");
    _builder.append(".");
    ForView _forViewInBody_2 = this._ismlModelNavigation.getForViewInBody(table);
    Variable _variable_1 = null;
    if (_forViewInBody_2!=null) {
      _variable_1=_forViewInBody_2.getVariable();
    }
    String _name_4 = _variable_1.getName();
    _builder.append(_name_4, "\t\t\t\t ");
    _builder.append("}\">");
    _builder.newLineIfNotEmpty();
    {
      LinkedHashMap<ViewStatement, ViewStatement> _columnsDataTable = this.getColumnsDataTable(table);
      Set<Map.Entry<ViewStatement, ViewStatement>> _entrySet = _columnsDataTable.entrySet();
      for(final Map.Entry<ViewStatement, ViewStatement> pair : _entrySet) {
        ViewStatement _key = pair.getKey();
        final ViewInstance viewInstance = ((ViewInstance) _key);
        _builder.newLineIfNotEmpty();
        _builder.append("<p:column id= \"");
        String _id_1 = this.getId(viewInstance);
        _builder.append(_id_1, "");
        _builder.append("\">");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("<f:facet name=\"header\">");
        _builder.newLine();
        _builder.append("\t\t");
        CharSequence _widgetTemplate = this.widgetTemplate(viewInstance);
        _builder.append(_widgetTemplate, "\t\t");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.append("</f:facet>");
        _builder.newLine();
        _builder.append("\t");
        ViewStatement _value = pair.getValue();
        CharSequence _widgetTemplate_1 = this.widgetTemplate(_value);
        _builder.append(_widgetTemplate_1, "\t");
        _builder.newLineIfNotEmpty();
        _builder.append("</p:column>");
        _builder.newLine();
      }
    }
    _builder.append("</p:dataTable>");
    _builder.newLine();
    return _builder;
  }
  
  public LinkedHashMap<ViewStatement, ViewStatement> getColumnsDataTable(final ViewInstance table) {
    final LinkedHashMap<ViewStatement, ViewStatement> columns = new LinkedHashMap<ViewStatement, ViewStatement>();
    EList<ViewStatement> _body = table.getBody();
    ViewStatement _get = _body.get(0);
    final NamedViewBlock header = ((NamedViewBlock) _get);
    EList<ViewStatement> _body_1 = table.getBody();
    ViewStatement _get_1 = _body_1.get(1);
    NamedViewBlock _cast = this._ismlModelNavigation.<ViewStatement, NamedViewBlock>cast(_get_1, NamedViewBlock.class);
    EList<ViewStatement> _body_2 = _cast.getBody();
    ViewStatement _get_2 = _body_2.get(0);
    final ForView forView = ((ForView) _get_2);
    EList<ViewStatement> _body_3 = header.getBody();
    int _size = _body_3.size();
    ExclusiveRange _doubleDotLessThan = new ExclusiveRange(0, _size, true);
    for (final Integer i : _doubleDotLessThan) {
      EList<ViewStatement> _body_4 = header.getBody();
      ViewStatement _get_3 = _body_4.get((i).intValue());
      EList<ViewStatement> _body_5 = forView.getBody();
      ViewStatement _get_4 = _body_5.get((i).intValue());
      columns.put(_get_3, _get_4);
    }
    return columns;
  }
  
  public String getId(final ViewInstance part) {
    String _name = part.getName();
    boolean _notEquals = (!Objects.equal(_name, null));
    if (_notEquals) {
      return part.getName();
    } else {
      View _view = this._ismlModelNavigation.getView(part);
      String _name_1 = _view.getName();
      String _firstLower = StringExtensions.toFirstLower(_name_1);
      int _plusPlus = this.i++;
      return (_firstLower + Integer.valueOf(_plusPlus));
    }
  }
  
  protected CharSequence _widgetTemplate(final EList<ViewStatement> partBlock) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _notEquals = (!Objects.equal(partBlock, null));
      if (_notEquals) {
        {
          for(final ViewStatement part : partBlock) {
            _builder.newLine();
            CharSequence _widgetTemplate = this.widgetTemplate(part);
            _builder.append(_widgetTemplate, "");
            _builder.append("\t\t");
            _builder.newLineIfNotEmpty();
            _builder.newLine();
          }
        }
      }
    }
    _builder.append("\t");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence valueTemplate(final Expression expression) {
    if ((expression instanceof Reference)) {
      if ((expression instanceof ResourceReference)) {
        return this._expressionTemplate.writeExpression(expression);
      } else {
        if ((expression instanceof VariableReference)) {
          VariableTypeElement _referencedElement = ((VariableReference)expression).getReferencedElement();
          ForView _findAncestor = this._ismlModelNavigation.<VariableTypeElement, ForView>findAncestor(_referencedElement, ForView.class);
          boolean _notEquals = (!Objects.equal(_findAncestor, null));
          if (_notEquals) {
            CharSequence _writeExpression = this._expressionTemplate.writeExpression(expression);
            String _plus = (("\"" + "#{") + _writeExpression);
            String _plus_1 = (_plus + "}");
            return (_plus_1 + "\"");
          } else {
            Controller _containerController = this._ismlModelNavigation.getContainerController(expression);
            String _name = _containerController.getName();
            String _firstLower = StringExtensions.toFirstLower(_name);
            String _plus_2 = (("\"" + "#{") + _firstLower);
            String _plus_3 = (_plus_2 + ".");
            CharSequence _writeExpression_1 = this._expressionTemplate.writeExpression(expression);
            String _plus_4 = (_plus_3 + _writeExpression_1);
            String _plus_5 = (_plus_4 + "}");
            return (_plus_5 + "\"");
          }
        }
      }
    } else {
      return this._expressionTemplate.writeExpression(expression);
    }
    return null;
  }
  
  public CharSequence widgetTemplate(final Object table) {
    if (table instanceof ForView) {
      return _widgetTemplate((ForView)table);
    } else if (table instanceof IfView) {
      return _widgetTemplate((IfView)table);
    } else if (table instanceof ViewInstance) {
      return _widgetTemplate((ViewInstance)table);
    } else if (table instanceof Reference) {
      return _widgetTemplate((Reference)table);
    } else if (table instanceof EList) {
      return _widgetTemplate((EList<ViewStatement>)table);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(table).toString());
    }
  }
}
