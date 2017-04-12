package GameObjects;

public class Mob {
	public String Name, Description;
	public int Level, HP, MaxHP, Dmg, Def, XP;
	public String[] Abilities;
	
	/**
	 * Mobs are created on the fly, this game is not sophisticated with fine databases
	 * @param name
	 * @param level
	 * @param hp
	 * @param dmg
	 * @param def
	 * @param xp
	 * @param descr
	 */
	public Mob(String name, int level, int hp, int dmg, int def, int xp, String descr, String abilities){
		Name = name;
		Level = level;
		MaxHP = hp;
		HP = hp;
		Dmg = dmg;
		Def = def;
		XP = xp;
		Description = descr;
		Abilities = abilities.split("_");
	}
}


