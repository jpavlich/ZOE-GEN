	package co.edu.javeriana.entities;
	
	import javax.persistence.*;
	import javax.validation.constraints.*;
	import java.io.Serializable;
	import javax.xml.bind.annotation.XmlRootElement;
			
	
			
	
			
	@XmlRootElement
	@Entity
	
	
	
	public  class Dieta implements Serializable {
		/**
		 * The serialVersionUID
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * The unique id for the entity
		 */		
		private Long id = null;
		
							
		/**
		 * The desayuno for the Dieta
		 */
		private String desayuno;
							
		/**
		 * The almuerzo for the Dieta
		 */
		private String almuerzo;
							
		/**
		 * The cena for the Dieta
		 */
		private String cena;
							
		/**
		 * The merienda for the Dieta
		 */
		private String merienda;
							
		/**
		 * The patologia for the Dieta
		 */
		private Long patologia;
		

		public Dieta(){
		}
		
			
	
		public Dieta(String desayuno,String almuerzo,String cena,String merienda,Long patologia){
			setDesayuno(desayuno);
			setAlmuerzo(almuerzo);
			setCena(cena);
			setMerienda(merienda);
			setPatologia(patologia);
		}

		

		
		
		/**
		 * Returns the current value for the attibute desayuno
		 *
		 * @return current instance for desayuno attribute
		 */
		public String getDesayuno(){
			return desayuno;
		}
		
		/**
		 * Sets the value for the attribute desayuno
		 * @param desayuno The value to set
		 */
		public void setDesayuno(String desayuno){
			this.desayuno=desayuno;
		}
		

		
		
		/**
		 * Returns the current value for the attibute almuerzo
		 *
		 * @return current instance for almuerzo attribute
		 */
		public String getAlmuerzo(){
			return almuerzo;
		}
		
		/**
		 * Sets the value for the attribute almuerzo
		 * @param almuerzo The value to set
		 */
		public void setAlmuerzo(String almuerzo){
			this.almuerzo=almuerzo;
		}
		

		
		
		/**
		 * Returns the current value for the attibute cena
		 *
		 * @return current instance for cena attribute
		 */
		public String getCena(){
			return cena;
		}
		
		/**
		 * Sets the value for the attribute cena
		 * @param cena The value to set
		 */
		public void setCena(String cena){
			this.cena=cena;
		}
		

		
		
		/**
		 * Returns the current value for the attibute merienda
		 *
		 * @return current instance for merienda attribute
		 */
		public String getMerienda(){
			return merienda;
		}
		
		/**
		 * Sets the value for the attribute merienda
		 * @param merienda The value to set
		 */
		public void setMerienda(String merienda){
			this.merienda=merienda;
		}
		

		
		
		/**
		 * Returns the current value for the attibute patologia
		 *
		 * @return current instance for patologia attribute
		 */
		public Long getPatologia(){
			return patologia;
		}
		
		/**
		 * Sets the value for the attribute patologia
		 * @param patologia The value to set
		 */
		public void setPatologia(Long patologia){
			this.patologia=patologia;
		}
		
	

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		public Long getId() {
			return id;
		}
		
		/**
		 * Sets the value for the unique id
		 * @param id The value to set
		 */
		public void setId(final Long id) {
			this.id = id;
		}
		
	
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof Dieta)) {
				return false;
			}
			final Dieta other = (Dieta) obj;
			if (id == null) {
				if (other.getId() != null) {
					return false;
				}
			} else if (!id.equals(other.getId())) {
				return false;
			}
			return true;
		}
		
	
		@Override
		public int hashCode() {

				int hash = 0;
				hash += (id != null ? id.hashCode() : 0);
      					return hash;
		}	
		        
		
		 @Override
    			public String toString() {
        					return "co.edu.javeriana.entities.Dieta [ id=" + id + " ]";
    				}
			
	}
