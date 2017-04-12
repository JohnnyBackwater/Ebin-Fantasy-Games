package GameObjects;

public class Player {

	public String Name, Class;
	public int HP, MaxHP, Str, Int, Agi, Luck, Level, XP, MaxXP, Gold, Def, ActID;
	public int Happyending;
	public Player(){
		Name = "Anon";
		HP = 100;
		MaxHP = 100;
		Str = 5;
		Int = 5;
		Agi = 5;
		Luck = 1;
		Level = 1;
		XP = 0;
		Def = 0;
		MaxXP = 200;
	}
	
	public boolean currentAct(int actid) {
		if(ActID == actid){
			return true;
		}
		return false;
	}
	/**
	 * Increase stats of the character and also displays the results
	 * @param HP
	 * @param str
	 * @param intr
	 * @param agi
	 * @param luck
	 */
	public void IncreaseStats(int hp, int str, int inte, int agi, int luck) {
		MaxHP += hp;
		Str += str;
		Int += inte;
		Agi += agi;
		Luck += luck;		
	}
	
	public int Damage(){
		int dmg = 1;
		if(Class.equals("White Warrior")){
			dmg += Str;
		}
		if(Class.equals("Burglar")){
			dmg += Agi;
		}
		if(Class.equals("Wizard")){
			dmg += Int;
		}
		return dmg;
	}

	public boolean isWarrior() {
		return Class.equals("White Warrior");
	}
	public boolean isBurglar() {
		return Class.equals("Burglar");
	}
	public boolean isWizard() {
		return Class.equals("Wizard");
	}
}
