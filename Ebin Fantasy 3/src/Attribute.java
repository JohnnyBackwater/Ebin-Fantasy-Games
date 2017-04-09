
public class Attribute {

	private String name;
	private int nvalue = -1;
	private String svalue;
	private boolean primary = false;;
	
	/**
	 * Can hold string or int type values.
	 * @param value
	 * @param name
	 */
	public Attribute(int value, String name){
		this.nvalue = value;
		this.name = name;
		this.primary = false;
		this.svalue = "";
	}
	
	public Attribute(String value, String name) {
		this.name = name;
		this.svalue = value;
		this.nvalue = -1;
	}

	/**
	 * Used with numerical values
	 * @return
	 */
	public boolean primary(){
		return this.primary;
	}
	
	public String svalue(){
		return this.svalue;
	}
	
	public int nvalue(){
		return this.nvalue;
	}
	
	public void add(int a){
		this.nvalue += a;
	}
	
	public void setPrimary(boolean bool){
		this.primary = bool;
	}
	
	public void set(String value){
		this.svalue = value;
	}

	public void set(int value2) {
		this.nvalue = value2;	
	}

	public String name() {
		return this.name;
	}

	public String value() {
		if(this.svalue.isEmpty()){
			return ""+nvalue;
		}else{
			return svalue;
		}
	}
	
}
