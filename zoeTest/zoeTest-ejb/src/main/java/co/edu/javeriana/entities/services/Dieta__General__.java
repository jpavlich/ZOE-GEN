package co.edu.javeriana.entities.services;		



import java.util.*;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;	
import javax.persistence.PersistenceContext;
import co.edu.javeriana.sesion.Persistence;
import co.edu.javeriana.entities.Dieta;

	
@Stateless
public class Dieta__General__ extends Persistence<Dieta>{
	    @PersistenceContext(unitName = "co.edu.javeriana_prenat-ejb_ejb_1.0-SNAPSHOTPU")
    			private EntityManager em;

	    @Override
	    protected EntityManager getEntityManager() {
	        return em;
	    }
	
	    public Dieta__General__() {
	        super(Dieta.class);
	    }
	
}	

	
