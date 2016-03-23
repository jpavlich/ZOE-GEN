package temp;

public class Query {
	Query next;

	String select;
	String from;
	String where;
	
	
	class Select extends Query {

		//String select;
		
		public Select(String columnas) {
		 	
			super.select= "select "+columnas;
		}
		/*String[] columnas;

		public Select(Query previous, String[] columnas) {
			super(previous);
			this.columnas = columnas;
		}*/

	}

	class From extends Query {
		//String tabla;

		public From(String tabla) {
			super.from = " from "+ tabla;
		}

	}

	class Where extends Query {
		public Where(String criterio) {
			
			super.where = " where "+criterio;
		}

		//String criterio;
	}

	
	
	
	public Query() {
		//super();			
			//next = this;
		
	}
	
	
	/////////////

	public Query select(String columnas) {
		return new Select(columnas);
	}

	public Query from(String tabla) {
		return new From(tabla);
	}

	public Query where(String criterio) {
		return new Where(criterio);
	}
	
	public String toSQL(Query q) {
		return q.select+q.from+q.where;
	}
	
	/*public String select(String columnas){
		return "select " + columnas;
	}

	public String from(String tablas){
		return " from " + tablas;
	}
	*/
	////////////////

	public static void main(String[] args) {
		
		//System.out.println(new Query(null).select("almuerzo").from("dieta").where("..."));
		System.out.println();
		//System.out.println(new Query(null).select("almuerzo").from("dieta").where("..."));
		Query q = new Query();
		System.out.println(q.select("almuerzo").from("Dieta").where("almuerzo = 1"));
		System.out.println(q.toSQL(q));
		

	}
}
