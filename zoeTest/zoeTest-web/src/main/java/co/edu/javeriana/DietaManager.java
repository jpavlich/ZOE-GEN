	package co.edu.javeriana;
			
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
	
	
	
	import co.edu.javeriana.entities.Dieta;
		
	import co.edu.javeriana.entities.services.Dieta__General__;
	
	
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
				private  Dieta__General__ persistence;
			
		/**
		 * Injection for the component named DietaPersonal 
		 */
		@EJB
				private  DietaPersonal dietapersonal;
			
	    
		/**
		 * Attribute for the type Object
		 */
		private Object container;
		/**
		 * Attribute for the type String
		 */
		private String attribute;
		/**
		 * Attribute for the type Dieta
		 */
		private Dieta dieta=new Dieta();
		
			private List
			<Dieta> 
			collection;
		
			private List
			<Dieta> 
			dietaList;
		
		
			
		
				
	 @PostConstruct
	 public void init() {
																																																			  }
		
	
		/**
		 * Action method named listAll
		 *
		 * @return String value with some navigation outcome
		 */
		
		public String listAll(){

			try{
				this.dietaList=getPersistence().findAll();
				return "goToDietaList";		
				
			}catch (Exception e)	{
				JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DietaCreated"));
				
			} 
			return "";
			
		}
		/**
		 * Action method named initTest
		 *
		 * @return String value with some navigation outcome
		 */
		
		public String initTest(){

			try{
				return listAll();
				
			}catch (Exception e)	{
				JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DietaCreated"));
				
			} 
			return "";
			
		}
		/**
		 * Action method named listDieta
		 * @param dietaList Parameter from type Collection
		 *
		 * @return String value with some navigation outcome
		 */
		
		public String listDieta( List  dietaList){

			try{
				if(dietaList!=null){
					this.setDietaList(dietaList);
				}
				;
				return "goToDietaList";		
				
			}catch (Exception e)	{
				JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DietaCreated"));
				
			} 
			return "";
			
		}
		/**
		 * Action method named createDietaToAdd
		 * @param container Parameter from type Any
		 * @param collection Parameter from type Collection
		 *
		 * @return String value with some navigation outcome
		 */
		
		public String createDietaToAdd( Object container, List  collection){

			try{
				if(container!=null){
					this.setContainer(container);
				}
				if(collection!=null){
					this.setCollection(collection);
				}
				;
				;
				this.dieta=new Dieta();
				return "goToCreateDietaToAdd";		
				
			}catch (Exception e)	{
				JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DietaCreated"));
				
			} 
			return "";
			
		}
		/**
		 * Action method named viewDieta
		 * @param dieta Parameter from type Dieta
		 *
		 * @return String value with some navigation outcome
		 */
		
		public String viewDieta( Dieta dieta){

			try{
				if(dieta!=null){
					this.setDieta(dieta);
				}
				;
				return "goToViewDieta";		
				
			}catch (Exception e)	{
				JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DietaCreated"));
				
			} 
			return "";
			
		}
		/**
		 * Action method named editDieta
		 * @param dieta Parameter from type Dieta
		 *
		 * @return String value with some navigation outcome
		 */
		
		public String editDieta( Dieta dieta){

			try{
				if(dieta!=null){
					this.setDieta(dieta);
				}
				;
				return "goToEditDieta";		
				
			}catch (Exception e)	{
				JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DietaCreated"));
				
			} 
			return "";
			
		}
		/**
		 * Action method named createDieta
		 *
		 * @return String value with some navigation outcome
		 */
		
		public String createDieta(){

			try{
				this.dieta=new Dieta();
				return "goToEditDieta";		
				
			}catch (Exception e)	{
				JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DietaCreated"));
				
			} 
			return "";
			
		}
		/**
		 * Action method named saveDieta
		 * @param dieta Parameter from type Dieta
		 *
		 * @return String value with some navigation outcome
		 */
		
		public String saveDieta( Dieta dieta){

			try{
				if(dieta!=null){
					this.setDieta(dieta);
				}
				if(getPersistence().isPersistent(dieta)){
					getPersistence();
					
				} else {
					getPersistence();
					
				}	
				return listAll();
				
			}catch (Exception e)	{
				JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DietaCreated"));
				
			} 
			return "";
			
		}
		/**
		 * Action method named deleteDieta
		 * @param dieta Parameter from type Dieta
		 *
		 * @return String value with some navigation outcome
		 */
		
		public String deleteDieta( Dieta dieta){

			try{
				if(dieta!=null){
					this.setDieta(dieta);
				}
				getPersistence();
				return listAll();
				
			}catch (Exception e)	{
				JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DietaCreated"));
				
			} 
			return "";
			
		}
		
		
		/**
		 * Returns the current value for the attribute container
		 * @return current instance for container attribute
		 */
		public Object getContainer(){
			return container;
		}
		
		/**
		 * Sets the value for the attribute container
		 * @param container The value to set
		 */
		public void setContainer(Object container){
			this.container=container;
		}
		/**
		 * Returns the current value for the attribute attribute
		 * @return current instance for attribute attribute
		 */
		public String getAttribute(){
			return attribute;
		}
		
		/**
		 * Sets the value for the attribute attribute
		 * @param attribute The value to set
		 */
		public void setAttribute(String attribute){
			this.attribute=attribute;
		}
		/**
		 * Returns the current value for the attribute dieta
		 * @return current instance for dieta attribute
		 */
		public Dieta getDieta(){
			return dieta;
		}
		
		/**
		 * Sets the value for the attribute dieta
		 * @param dieta The value to set
		 */
		public void setDieta(Dieta dieta){
			this.dieta=dieta;
		}
		/**
		 * Returns the current value for the attribute collection
		 *
		 * @return current instance for collection attribute
		 */
		public List<Dieta> getCollection(){						
			return collection;
		}
		
		/**
		 * Sets the value for the attribute collection
		 * @param collection The value to set
		 */
		public void setCollection(List<Dieta> collection){
			this.collection=collection;
		}
		/**
		 * Returns the current value for the attribute dietaList
		 *
		 * @return current instance for dietaList attribute
		 */
		public List<Dieta> getDietaList(){						
			return dietaList;
		}
		
		/**
		 * Sets the value for the attribute dietaList
		 * @param dietaList The value to set
		 */
		public void setDietaList(List<Dieta> dietaList){
			this.dietaList=dietaList;
		}
		
		
		
		/**
		 * Returns the instance for the persistence EJB
		 *
		 * @return current instance for persistence attribute
		 */
			public  Dieta__General__ getPersistence(){
		return persistence;
		}
		/**
		 * Sets the value for the persistence EJB
		 * @param persistence The value to set
		 */
			public void setPersistence( Dieta__General__  persistence){
		this.persistence=persistence;
		} 
		/**
		 * Returns the instance for the dietapersonal EJB
		 *
		 * @return current instance for dietapersonal attribute
		 */
			public  DietaPersonal getDietapersonal(){
		return dietapersonal;
		}
		/**
		 * Sets the value for the dietapersonal EJB
		 * @param dietapersonal The value to set
		 */
			public void setDietapersonal( DietaPersonal  dietapersonal){
		this.dietapersonal=dietapersonal;
		} 
	
	}
