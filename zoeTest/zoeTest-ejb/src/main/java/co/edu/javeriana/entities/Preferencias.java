	package co.edu.javeriana.entities;
	
	import javax.persistence.*;
	import javax.validation.constraints.*;
	import java.io.Serializable;
	import javax.xml.bind.annotation.XmlRootElement;
			
	
			
	
			
	@XmlRootElement
	@Entity
	
	
	
	public  class Preferencias implements Serializable {
		/**
		 * The serialVersionUID
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * The unique id for the entity
		 */		
		private Long id = null;
		
							
		/**
		 * The preferencia for the Preferencias
		 */
		private String preferencia;
							
		/**
		 * The otro for the Preferencias
		 */
		private String otro;
		

		public Preferencias(){
		}
		
			
	
		public Preferencias(String preferencia,String otro){
			setPreferencia(preferencia);
			setOtro(otro);
		}

		

		
		
		/**
		 * Returns the current value for the attibute preferencia
		 *
		 * @return current instance for preferencia attribute
		 */
		public String getPreferencia(){
			return preferencia;
		}
		
		/**
		 * Sets the value for the attribute preferencia
		 * @param preferencia The value to set
		 */
		public void setPreferencia(String preferencia){
			this.preferencia=preferencia;
		}
		

		
		
		/**
		 * Returns the current value for the attibute otro
		 *
		 * @return current instance for otro attribute
		 */
		public String getOtro(){
			return otro;
		}
		
		/**
		 * Sets the value for the attribute otro
		 * @param otro The value to set
		 */
		public void setOtro(String otro){
			this.otro=otro;
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
			if (!(obj instanceof Preferencias)) {
				return false;
			}
			final Preferencias other = (Preferencias) obj;
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
        					return "co.edu.javeriana.entities.Preferencias [ id=" + id + " ]";
    				}
			
	}
