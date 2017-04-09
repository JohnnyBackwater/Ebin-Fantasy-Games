
public class Skill {

	//Read values 
	protected String name;
	private String dmgsrc;
	private double dmgmult;
	private String type;
	private Effect effect;
	
	
	public int damage(Mob user) {
		return (int) (user.getAttribute(this.dmgsrc).nvalue()*this.dmgmult);
	}


	public void setAttr(String rivi) {
		String[] data = rivi.split(":");
		if(data.length != 2){
			System.err.println("Error in data ");
			return;
		}
		
		if(data[0].equals("type")) this.type = data[1];
		if(data[0].equals("name")) this.name = data[1];
		if(data[0].equals("dmgsrc")) this.dmgsrc = data[1];
		if(data[0].equals("dmgmult")) {
			try {
				this.dmgmult = Double.valueOf(data[1]);
			} catch (NumberFormatException e) {
				System.err.println("Error assignint dmgmultiplier " + e.getMessage());
			}
		}
		if(data[0].equals("")) this.effect = Effects.get(data[1]);
		return;
		
	}


	public String type() {
		return this.type;
	}
}
