import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;




public class Mob implements Cloneable {

	
	//soon to be stuff
	protected Map<String, Attribute> stats;	
	
	//Only NPC
	protected Skill skill;
	
	//calculated values
	protected int HP, maxHP;
	protected int defense;
	
	//Read values only non NPC
	protected Inventory inventory;
	protected Gear gear;
	
	

	public Mob() {
		
		initAttributes();
	}
	
	private void initAttributes() {
		this.stats = new HashMap<String, Attribute>();
		this.stats.put("str", new Attribute(0, "Strength"));
		this.stats.put("agi", new Attribute(0, "Agility"));
		this.stats.put("inte", new Attribute(0, "Intellignce"));
		this.stats.put("luck", new Attribute(0, "Luck"));
		this.stats.put("bty", new Attribute(0, "Charisma"));
		this.stats.put("lvl", new Attribute(1,"Level"));
		this.stats.put("skillrate", new Attribute(0,"Skill Rate"));
		this.stats.put("xp", new Attribute(0,"XP"));
		this.stats.put("maxXP", new Attribute(500,"next level"));
		this.stats.put("gold", new Attribute(0,"Gold"));
		this.stats.put("name", new Attribute("","Name"));
		this.stats.put("luokka", new Attribute("","Class"));
		
	}

	public void gainXP(int value){
		this.stats.get("xp").add(value);
		System.out.println("| Gained " + value + " xp |");
		if(this.stats.get("xp").nvalue() >= this.stats.get("maxXP").nvalue()){
			levelUp();
		}
	}
	
	public void levelUp() {
		this.stats.get("xp").add(-this.stats.get("maxXP").nvalue());
		this.stats.get("maxXP").add(this.stats.get("maxXP").nvalue()/2);
		
		this.stats.get("lvl").add(1);
		addPrimaryStat(4);
		addAllNonPrimaryStat(2);
		update();
		this.restoreFullHP();
		printStats();
		if(this.stats.get("xp").nvalue() >= this.stats.get("maxXP").nvalue()){
			levelUp();
		}
		
	}

	public void printStats() {
		System.out.println(".....................................");
		System.out.println("|Name  	"+ this.stats.get("name").svalue());
		System.out.println("|Level 	" + this.stats.get("lvl").nvalue() + " " + this.stats.get("luokka").svalue());
		System.out.println("|Strength 	" + this.stats.get("str").nvalue());
		System.out.println("|Agility	" + this.stats.get("agi").nvalue());
		System.out.println("|Intelligence	" + this.stats.get("inte").nvalue());
		System.out.println("|Charisma 	" + this.stats.get("bty").nvalue());
		System.out.println("|Luck		" + this.stats.get("luck").nvalue());
		System.out.println("|Primary stat: "+this.getPrimaryAttribute().name());
		System.out.println("ииииииииииииииииииииииииииииииииииииии");
		System.out.println("		Battle");
		System.out.println("|Damage 	" + minMaxDmg());
		System.out.println("|Defense	" + this.defense);
	}

	private void addAllNonPrimaryStat(int i) {
		String[] keys = {"str", "agi", "inte", "bty", "luck"};
		for (int j = 0; j < keys.length; j++) {
			if(!this.stats.get(keys[j]).primary()){
				this.stats.get(keys[j]).add(i);
			}
		}
		
	}

	public void save(){
		File dir = new File("data/saves/"+get("name"));
		if(!dir.exists()){
			dir.mkdir();
		}
		
		try {
			BufferedWriter br = new BufferedWriter(new FileWriter("data/saves/"+this.get("name")+"/"+this.get("name")+".sav"));
			Object[] keys = this.stats.keySet().toArray();
			for (int i = 0; i < keys.length; i++) {
				br.write(keys[i] + ":" + this.stats.get(keys[i]).nvalue());
				br.newLine();
			}
			//Inventory save different dir
			//gear save
			br.flush();
			br.close();
		} catch (IOException e) {
			// TODO: handle exception
		}
		System.out.println(this.get("name")+" progress saved!");
	}

	/**
	 * gets the key represented attribute
	 * @param string
	 * @return
	 */
	public String get(String key) {
		return this.stats.get(key).value();
	}

	public void addStat(String key, int value){
		this.stats.get(key).add(value);
	}
	
	public void setStat(String key, int value){
		this.stats.get(key).set(value);
	}
	
	
	



	public void setStat(String key, String string) {
		int value = 0;
		try {
			value = Integer.valueOf(string);
		} catch (NumberFormatException e) {
			try {
				this.stats.get(key).set(string);
			} catch (NullPointerException ee) {
				System.out.println("Error ");
				return;
			}
		}
		try {
			this.stats.get(key).set(value);
		} catch (NullPointerException e) {
			return;
		} 
	}

	public int speed() {
		return this.stats.get("agi").nvalue();
	}

	public void recDmg(int dmg) {
		this.HP -= dmg;
		
	}
	
	public int recDmg(int damage, String type) {
		//physical damage
		if(type.equals("phys")){
			int dmg = (int) (damage*(1.-defense()));
			this.HP -= dmg;
			return dmg;
		}
		//magical damage
		if(type.equals("mag")){
			
			return damage;
		}
		//unknown or pure damage
		this.HP -= damage;
		return damage;
		
	}


	public Mob copy() {
		Mob r = null;
		try {
			r = (Mob) this.clone();
		} catch (CloneNotSupportedException e) {
			System.err.println("Something went wrong while copying new mob " + e.getMessage());
		}		
		return r;
	}

	public void restoreFullHP() {
		this.HP = this.maxHP;
		
	}

	public int damage() {
		int damage = this.stats.get("str").nvalue()/4 + Game.rand.nextInt(1 + this.stats.get("str").nvalue()/3);
		try {
			damage = this.getPrimaryAttribute().nvalue()/4 + Game.rand.nextInt(1 + this.getPrimaryAttribute().nvalue()/3);
		} catch (NullPointerException e) {
			//do nothing
		}
		return damage;
	}
	
	public String minMaxDmg(){
		return ""+this.getPrimaryAttribute().nvalue()/4+" - "+(this.getPrimaryAttribute().nvalue()/4+this.getPrimaryAttribute().nvalue()/3);
	}

	public Skill skill() {
		return this.skill;
	}
	
	public void setPrimaryAttribute(String string) {
		this.stats.get(string).setPrimary(true);
		
	}
	
	public Attribute getAttribute(String dmgsrc) {
		return this.stats.get(dmgsrc);
	}
	
	public void addPrimaryStat(int i) {
		try{
			getPrimaryAttribute().add(i);
		}catch(NullPointerException e){
			System.err.println("Primary attribute not found");
		}
	}

	private Attribute getPrimaryAttribute() {
		Object[] ats = this.stats.values().toArray();
		for (int i = 0; i < ats.length; i++) {
			if(((Attribute) ats[i]).primary()){
				return (Attribute) ats[i];
			}
		}
		return null;
	}

	public void setSkill(Skill s) {
		this.skill = s;
		
	}

	public void update(){
		updateDefense();
		updateMaxHP();
	}
	
	private void updateDefense(){
		this.defense = this.stats.get("agi").nvalue()/4 + this.stats.get("luck").nvalue()/5;
	}

	/**
	 * Updates all the max hp value
	 */
	public void updateMaxHP() {
		this.maxHP = this.stats.get("str").nvalue() * 2 + this.stats.get("agi").nvalue() + this.stats.get("luck").nvalue()/3 + this.stats.get("bty").nvalue()/3 + this.stats.get("inte").nvalue()/4;
	}

	private double defense() {
		double mult = 0.02*this.defense;
		return mult;
	}

	
	public int lvl() {
		return this.stats.get("lvl").nvalue();
	}

	public int skillRate() {
		Attribute rate = this.stats.get("skillrate");
		if( rate== null){
			return 0;
		}
		return rate.nvalue();
	}
	
	public int yieldXP(){
		int extra = 0;
		if (this.skill != null) {
			extra = 100*this.stats.get("lvl").nvalue();
		}
		return extra + 10*(this.stats.get("lvl").nvalue() + this.stats.get("str").nvalue() + this.stats.get("agi").nvalue() + this.stats.get("inte").nvalue()+this.stats.get("bty").nvalue() + Game.rand.nextInt(this.stats.get("luck").nvalue()));
	}

}
