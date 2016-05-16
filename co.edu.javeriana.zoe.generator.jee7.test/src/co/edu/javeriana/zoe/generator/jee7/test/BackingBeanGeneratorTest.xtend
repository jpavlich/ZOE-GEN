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
import co.edu.javeriana.isml.isml.Controller

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
			
		entity Dieta {

				    String desayuno;
				    String almuerzo;
				    String cena;
				    String merienda;
				    Integer patologia;
	
			}


			controller DietaManager {
				has Persistence<Dieta> persistence;
				
				/**
				* Lists all instances of Dieta.
				*/
				default listAll() {
					show DietaList(persistence.findAll());
				}
			
				/**
				* Lists instances of Dieta.
				* @param dietaList The list of instances of Dieta to show.
				*/
				listDieta(Collection<Dieta> dietaList) {
					show DietaList(dietaList);
				}
				
			
			
				
				createDietaToAdd(Any container, Collection<Dieta> collection) {
					show CreateDietaToAdd(container, collection, new Dieta);
						
				}
			
				
				
			
				/**
				* Views an instance of Dieta.
				* @param dieta the Dieta to open.
				*/		
				viewDieta(Dieta dieta) {
					show ViewDieta(dieta);
				}
			
			
				/**
				* Edits an existing instance of Dieta.
				* @param dieta the Dieta to open.
				*/			
				editDieta(Dieta dieta) {
					show EditDieta(dieta);
				}
			
				/**
				* Creates a a new instance of Dieta.
				*/			
				createDieta() {
					show EditDieta(new Dieta);
				}
			
			
				/**
				* Saves an instance of Dieta.
				* @param dieta the Dieta to save.
				*/			
				saveDieta(Dieta dieta) {
				if(persistence.isPersistent(dieta)){
					persistence.save(dieta);
				} else {
					persistence.create(dieta);
				}
				-> listAll();
				}
			
			
				/**
				* Deletes an instance of Dieta.
				* @param dieta the Dieta to delete.
				*/		
				deleteDieta(Dieta dieta) {
					persistence.remove(dieta);
					-> listAll();
				}
			
			
			}
			
			
					page DietaList(Collection<Dieta> dietaList) controlledBy DietaManager  {
					Form {
				       Panel("Collection<Dieta>") {
				           DataTable("Collection<Dieta>", null) {
				               header : {                    
				                   Label("Desayuno");
				                   Label("Almuerzo");
				                   Label("Cena");
				                   Label("Merienda");
				                   Label("Patologia");
				                   Label("View");
				                   Label("Edit");
				                   Label("Delete");
				               }
				               body : 
				               for(Dieta dieta in dietaList) {
				               		Label(dieta.desayuno);
				               		Label(dieta.almuerzo);
				               		Label(dieta.cena);
				               		Label(dieta.merienda);
				               		Label(dieta.patologia);
				                   	Button("View",false)-> viewDieta(dieta);
				                   	Button("Edit",false) -> editDieta(dieta);
				                   	Button("Delete",false) -> deleteDieta(dieta);
				               }
			
				           }
				        	
					}
				} 
				
			}
			
			
		'''.parse(rs)
		obj.assertNoErrors
		val controller = obj.body.head.cast(Package).body.get(2).cast(Controller)
		assertGenerates(template,
			controller,
			'''package co.edu.javeriana;
			
			import java.io.Serializable;
			import javax.inject.*;
			import co.edu.javeriana.util.JsfUtil;
			import javax.enterprise.context.*;
			import java.util.*;
			import javax.faces.application.FacesMessage;
			import javax.faces.context.FacesContext;
			import javax.faces.bean.ManagedBean;
			import javax.annotation.PostConstruct;
			import javax.ejb.EJB;
			import org.primefaces.model.map.MapModel;
			import org.primefaces.model.map.DefaultMapModel;
			import org.primefaces.model.map.LatLng;
			import org.primefaces.model.map.Marker;
			import co.edu.javeriana.sesion.Query;
			import co.edu.javeriana.sesion.Convert;
			
			import co.edu.javeriana.entities.Dieta;
			import co.edu.javeriana.entities.Dieta;
			
			import co.edu.javeriana.entities.services.Lugares__General__;
			import co.edu.javeriana.entities.*;
			
			import co.edu.javeriana.entities.services.Dieta__General__;
			import co.edu.javeriana.entities.*;
			
			import co.edu.javeriana.interfaces.DietaPersonal;
			
			/**
			 * This class represents a controller with name DietaManager
			 */
			@ManagedBean(name = "dietaManager")
			@RequestScoped
			public class DietaManager implements Serializable {
			
			    /**
			     * The serialVersionUID
			     */
			    private static final long serialVersionUID = 1L;
			
			    /**
			     * Injection for the component named Persistence
			     */
			    @EJB
			    private Dieta__General__ persistence;
			
			    /**
			     * Injection for the component named Persistence
			     */
			    @EJB
			    private Lugares__General__ persistenceLug;
			
			    /**
			     * Injection for the component named DietaPersonal
			     */
			    @EJB
			    private DietaPersonal dietapersonal;
			
			    /**
			     * Injection for the component named Query
			     */
			    @EJB
			    private Query query;
			
			    private List<Lugares> model;
			    /**
			     * Attribute for the type MapModel
			     */
			    private MapModel simpleModel;
			
			    private List<Dieta> dietaAdaptativaList;
			    /**
			     * Attribute for the type Object
			     */
			    private Object container;
			    /**
			     * Attribute for the type Dieta
			     */
			    private Dieta dietaAdaptativa = new Dieta();
			    /**
			     * Attribute for the type String
			     */
			    private String attribute;
			    /**
			     * Attribute for the type Dieta
			     */
			    private Dieta dieta = new Dieta();
			
			    private List<Dieta> collection;
			
			    private List<Dieta> dietaList;
			
			    @PostConstruct
			    public void init() {
			        listAll();
			
			    }
			
			    /**
			     * Action method named listAll
			     *
			     * @return String value with some navigation outcome
			     */
			    public String listAll() {
			
			        try {
			
			            this.dietaList = getPersistence().findAll();
			            return "goToDietaList";
			
			        } catch (Exception e) {
			            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DietaCreated"));
			
			        }
			        return "";
			
			    }
			
			    /**
			     * Action method named initTest
			     *
			     * @return String value with some navigation outcome
			     */
			
			    public String initTest() {
			
			        try {
			
			            return listAll();
			
			        } catch (Exception e) {
			            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DietaCreated"));
			
			        }
			        return "";
			
			    }
			
			    /**
			     * Action method named listDieta
			     *
			     * @param dietaList Parameter from type Collection
			     *
			     * @return String value with some navigation outcome
			     */
			
			    public String listDieta(List dietaList) {
			
			        try {
			
			            if (dietaList != null) {
			                this.setDietaList(dietaList);
			            }
			            ;
			            return "goToDietaList";
			
			        } catch (Exception e) {
			            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DietaCreated"));
			
			        }
			        return "";
			
			    }
			
			    /**
			     * Action method named createDietaToAdd
			     *
			     * @param container Parameter from type Any
			     * @param collection Parameter from type Collection
			     *
			     * @return String value with some navigation outcome
			     */
			
			    public String createDietaToAdd(Object container, List collection) {
			
			        try {
			
			            if (container != null) {
			                this.setContainer(container);
			            }
			
			            if (collection != null) {
			                this.setCollection(collection);
			            }
			            ;
			            ;
			            this.dieta = new Dieta();
			            return "goToCreateDietaToAdd";
			
			        } catch (Exception e) {
			            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DietaCreated"));
			
			        }
			        return "";
			
			    }
			
			    /**
			     * Action method named viewDieta
			     *
			     * @param dieta Parameter from type Dieta
			     *
			     * @return String value with some navigation outcome
			     */
			
			    public String viewDieta(Dieta dieta) {
			
			        try {
			
			            if (dieta != null) {
			                this.setDieta(dieta);
			            }
			            ;
			            return "goToViewDieta";
			
			        } catch (Exception e) {
			            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DietaCreated"));
			
			        }
			        return "";
			
			    }
			
			    /**
			     * Action method named editDieta
			     *
			     * @param dieta Parameter from type Dieta
			     *
			     * @return String value with some navigation outcome
			     */
			
			    public String editDieta(Dieta dieta) {
			
			        try {
			
			            if (dieta != null) {
			                this.setDieta(dieta);
			            }
			            ;
			            return "goToEditDieta";
			
			        } catch (Exception e) {
			            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DietaCreated"));
			
			        }
			        return "";
			
			    }
			
			    /**
			     * Action method named createDieta
			     *
			     * @return String value with some navigation outcome
			     */
			
			    public String createDieta() {
			
			        try {
			
			            this.dieta = new Dieta();
			            return "goToEditDieta";
			
			        } catch (Exception e) {
			            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DietaCreated"));
			
			        }
			        return "";
			
			    }
			
			    /**
			     * Action method named saveDieta
			     *
			     * @param dieta Parameter from type Dieta
			     *
			     * @return String value with some navigation outcome
			     */
			
			    public String saveDieta(Dieta dieta) {
			
			        try {
			
			            if (dieta != null) {
			                this.setDieta(dieta);
			            }
			            if (getPersistence().isPersistent(dieta)) {
			                getPersistence().save(dieta);
			
			            } else {
			                getPersistence().create(dieta);
			
			            }
			            return listAll();
			
			        } catch (Exception e) {
			            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DietaCreated"));
			
			        }
			        return "";
			
			    }
			
			    /**
			     * Action method named showHospitales
			     *
			     * @return String value with some navigation outcome
			     */
			
			    public String showHospitales() {
			
			        try {
			
			            Query q = getQuery().select().from("Lugares").where("tipo = 'hospital'");
			
			            List model = getPersistenceLug().execute(q);
			
			            this.model = model;
			            this.simpleModel = new DefaultMapModel();
			            for (Lugares lugar : this.model) {
			
			                LatLng coord1 = new LatLng(Double.parseDouble(lugar.getCoordenada1()), Double.parseDouble(lugar.getCoordenada2()));
			                getSimpleModel().addOverlay(new Marker(coord1, lugar.getDescripcion()));
			            };
			            return "goToRestaurantMapView";
			
			        } catch (Exception e) {
			            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DietaCreated"));
			
			        }
			        return "";
			
			    }
			
			    /**
			     * Action method named showTiendas
			     *
			     * @return String value with some navigation outcome
			     */
			
			    public String showTiendas() {
			
			        try {
			
			            Query q = getQuery().select().from("Lugares").where("tipo = 'Tienda'");
			
			            List model = getPersistenceLug().execute(q);
			
			            this.model = model;
			            this.simpleModel = new DefaultMapModel();
			            for (Lugares lugar : this.model) {
			
			                LatLng coord1 = new LatLng(Double.parseDouble(lugar.getCoordenada1()), Double.parseDouble(lugar.getCoordenada2()));
			                getSimpleModel().addOverlay(new Marker(coord1, lugar.getDescripcion()));
			            };
			            return "goToRestaurantMapView";
			
			        } catch (Exception e) {
			            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DietaCreated"));
			
			        }
			        return "";
			
			    }
			
			    /**
			     * Action method named showDietaAdaptativa
			     *
			     * @return String value with some navigation outcome
			     */
			
			    public String showDietaAdaptativa() {
			
			        try {
			
			            Query q = getQuery().select().from("Dieta").where("patologia = 3");
			
			            List dietaAdaptativaList = getPersistence().execute(q);
			            ;
			            return "goToDietaAdaptativa";
			
			        } catch (Exception e) {
			            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DietaCreated"));
			
			        }
			        return "";
			
			    }
			
			    /**
			     * Action method named deleteDieta
			     *
			     * @param dieta Parameter from type Dieta
			     *
			     * @return String value with some navigation outcome
			     */
			
			    public String deleteDieta(Dieta dieta) {
			
			        try {
			
			            if (dieta != null) {
			                this.setDieta(dieta);
			            }
			            getPersistence().remove(dieta);
			            return listAll();
			
			        } catch (Exception e) {
			            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DietaCreated"));
			
			        }
			        return "";
			
			    }
			
			    /**
			     * Returns the current value for the attribute model
			     *
			     * @return current instance for model attribute
			     */
			    public List<Lugares> getModel() {
			        return model;
			    }
			
			    /**
			     * Sets the value for the attribute model
			     *
			     * @param model The value to set
			     */
			    public void setModel(List<Lugares> model) {
			        this.model = model;
			    }
			
			    /**
			     * Returns the current value for the attribute simpleModel
			     *
			     * @return current instance for simpleModel attribute
			     */
			    public MapModel getSimpleModel() {
			        return simpleModel;
			    }
			
			    /**
			     * Sets the value for the attribute simpleModel
			     *
			     * @param simpleModel The value to set
			     */
			    public void setSimpleModel(MapModel simpleModel) {
			        this.simpleModel = simpleModel;
			    }
			
			    /**
			     * Returns the current value for the attribute dietaAdaptativaList
			     *
			     * @return current instance for dietaAdaptativaList attribute
			     */
			    public List<Dieta> getDietaAdaptativaList() {
			        return dietaAdaptativaList;
			    }
			
			    /**
			     * Sets the value for the attribute dietaAdaptativaList
			     *
			     * @param dietaAdaptativaList The value to set
			     */
			    public void setDietaAdaptativaList(List<Dieta> dietaAdaptativaList) {
			        this.dietaAdaptativaList = dietaAdaptativaList;
			    }
			
			    /**
			     * Returns the current value for the attribute container
			     *
			     * @return current instance for container attribute
			     */
			    public Object getContainer() {
			        return container;
			    }
			
			    /**
			     * Sets the value for the attribute container
			     *
			     * @param container The value to set
			     */
			    public void setContainer(Object container) {
			        this.container = container;
			    }
			
			    /**
			     * Returns the current value for the attribute dietaAdaptativa
			     *
			     * @return current instance for dietaAdaptativa attribute
			     */
			    public Dieta getDietaAdaptativa() {
			        return dietaAdaptativa;
			    }
			
			    /**
			     * Sets the value for the attribute dietaAdaptativa
			     *
			     * @param dietaAdaptativa The value to set
			     */
			    public void setDietaAdaptativa(Dieta dietaAdaptativa) {
			        this.dietaAdaptativa = dietaAdaptativa;
			    }
			
			    /**
			     * Returns the current value for the attribute attribute
			     *
			     * @return current instance for attribute attribute
			     */
			    public String getAttribute() {
			        return attribute;
			    }
			
			    /**
			     * Sets the value for the attribute attribute
			     *
			     * @param attribute The value to set
			     */
			    public void setAttribute(String attribute) {
			        this.attribute = attribute;
			    }
			
			    /**
			     * Returns the current value for the attribute dieta
			     *
			     * @return current instance for dieta attribute
			     */
			    public Dieta getDieta() {
			        return dieta;
			    }
			
			    /**
			     * Sets the value for the attribute dieta
			     *
			     * @param dieta The value to set
			     */
			    public void setDieta(Dieta dieta) {
			        this.dieta = dieta;
			    }
			
			    /**
			     * Returns the current value for the attribute collection
			     *
			     * @return current instance for collection attribute
			     */
			    public List<Dieta> getCollection() {
			        return collection;
			    }
			
			    /**
			     * Sets the value for the attribute collection
			     *
			     * @param collection The value to set
			     */
			    public void setCollection(List<Dieta> collection) {
			        this.collection = collection;
			    }
			
			    /**
			     * Returns the current value for the attribute dietaList
			     *
			     * @return current instance for dietaList attribute
			     */
			    public List<Dieta> getDietaList() {
			        return dietaList;
			    }
			
			    /**
			     * Sets the value for the attribute dietaList
			     *
			     * @param dietaList The value to set
			     */
			    public void setDietaList(List<Dieta> dietaList) {
			        this.dietaList = dietaList;
			    }
			
			    /**
			     * Returns the instance for the persistence EJB
			     *
			     * @return current instance for persistence attribute
			     */
			    public Dieta__General__ getPersistence() {
			        return persistence;
			    }
			
			    /**
			     * Sets the value for the persistence EJB
			     *
			     * @param persistence The value to set
			     */
			    public void setPersistence(Dieta__General__ persistence) {
			        this.persistence = persistence;
			    }
			
			    /**
			     * Returns the instance for the persistenceLug EJB
			     *
			     * @return current instance for persistenceLug attribute
			     */
			    public Lugares__General__ getPersistenceLug() {
			        return persistenceLug;
			    }
			
			    /**
			     * Sets the value for the persistenceLug EJB
			     *
			     * @param persistenceLug The value to set
			     */
			    public void setPersistenceLug(Lugares__General__ persistenceLug) {
			        this.persistenceLug = persistenceLug;
			    }
			
			    /**
			     * Returns the instance for the dietapersonal EJB
			     *
			     * @return current instance for dietapersonal attribute
			     */
			    public DietaPersonal getDietapersonal() {
			        return dietapersonal;
			    }
			
			    /**
			     * Sets the value for the dietapersonal EJB
			     *
			     * @param dietapersonal The value to set
			     */
			    public void setDietapersonal(DietaPersonal dietapersonal) {
			        this.dietapersonal = dietapersonal;
			    }
			
			    /**
			     * Returns the instance for the query EJB
			     *
			     * @return current instance for query attribute
			     */
			    public Query getQuery() {
			        return query;
			    }
			
			    /**
			     * Sets the value for the query EJB
			     *
			     * @param query The value to set
			     */
			    public void setQuery(Query query) {
			        this.query = query;
			    }
			
			}
			'''
		)

	}
}