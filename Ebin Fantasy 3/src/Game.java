import java.io.BufferedReader;
import java.io.FileReader;
import java.text.NumberFormat;
import java.util.Random;
import java.util.Scanner;

public class Game {
	public static Random rand = new Random();
	public static Dialogs dialogs;
	public static Mobs mobs;
	public static Skills skills;
	public static Player p;
	public static Location location;
	public static Shops shops;

	// Temporary solutions
	public static Location jungle;

	// THE GAME
	public static void main(String[] args) {
		//Load shops
		shops = new Shops();
		// Load Dialogs
		dialogs = new Dialogs();
		// Load skills
		skills = new Skills();
		// Load Mobs
		mobs = new Mobs();

		jungle = new Location("Jungle");
		jungle.setName("Jungle");
		jungle.setHostility(100);

		gameMessage("       Backwatwer Software presents");
		gameMessage("Ebin Fantasy III - Lord of organized chaos");
		System.out.println();
		gameMessage("build 0.069");
		PressEnter();
		Clear();
		boolean start = true;
		p = null;
		while (start) {
			int valinta = choise("New game,Load");
			if (valinta == 1) {
				p = newGame();
				start = false;
			}
			if (valinta == 2) {
				// p = loadPlayer();
				gameMessage("This option is not yet available, ba a patient!");
				input();
			}
			Clear();

		}
		Clear();
		Dialog("intro");
		Battle("Orge", p);
		Dialog("intro2");
		p.save();
		Dialog("theresnothing");

		Location nextLocation;
		while (true) {
			travelToLocation(jungle);
			nextLocation = doStuff();
			Dialog("wanderAround");
		}

	}

	/**
	 * This method is THE GAME. Everything happens here. Possible activities are
	 * determined in each location's source file ex. "jungle.loc".
	 * 
	 * These you can do:
	 * 
	 * 			Nothing
	 * 
	 * 
	 * These features need to be added:
	 * 
	 * 		- get the next location
	 * 		- meet NPC
	 * 		- shop
	 */
	private static Location doStuff() {
		boolean travel = false;
		while (!travel) {
			gameMessage("You are at " + location.name());
			gameMessage("What do?");
			String act = location.whatDo();
			if(act.equals("You")){
				p.printStats();
				PressEnter();
			}
			if(act.equals("Shop")){
				goToShop(location.whichShop());
			}
			Clear();
		}
		return jungle;
	}

	
	
	private static void goToShop(String whichShop) {
		
		boolean done = false;
		Shop s = shops.getShop(whichShop);
		while(!done){
			gameMessage("You are at " + s.name);
			gameMessage("What do?");
			int valinta = choise("Buy,Sell,Get out");			
			if(valinta == 1){
				
			}
			if(valinta == 2){
				
			}
			if(valinta == 3){
				return;
			}
		}
		
	}

	private static void travelToLocation(Location loc) {
		location = loc;
		if(!location.isMobs()){
			return;
		}
		if (location.hostility() < rand.nextInt(100)) {
			Battle(location.encounter(), p);
		}

	}


	private static Player loadPlayer() {
		gameMessage("Player name");
		String name = input();
		// To do: TODO!
		Player pp = new Player(name);
		try {
			BufferedReader br = new BufferedReader(new FileReader("data/saves/" + name + ".sav"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	// EN OF THE GAME

	private static void Dialog(String string) {
		String[] rivit = dialogs.dialogi(string);
		for (int i = 0; i < rivit.length; i++) {
			storydialog(rivit[i]);
		}
		Clear();
	}

	private static void Battle(String string, Player p) {
		Mob m = mobs.getMob(string);
		storydialog(m.get("name") + " encountered!");
		while (m.HP > 0) {
			boolean done = false;
			boolean playerFirst = true;

			if (p.speed() >= m.speed()) {
				playerFirst = true;
			} else {
				playerFirst = false;
			}

			if (!playerFirst) {
				mobturn(m, p);
			}
			// PLAYER TURN start

			while (!done) {
				if (p.HP <= 0) {
					Dialog("youDied");
					p.update();
					p.restoreFullHP();
					return;
				}
				printStatus(m, 14);
				space(6);
				printStatus(p, 0);
				gameMessage("What do?");
				int val = choise("Attack");
				if (val == 1) {
					int dmg = p.damage();
					BattleMessage("You attack " + m.get("name") + ", deal " + dmg + " damage.");
					m.recDmg(dmg);
					done = true;
				}
			}
			// PLAYER TURN end
			if (playerFirst) {
				mobturn(m, p);
			}

		}

		gameMessage(m.get("name") + " died!");
		p.gainXP(m.yieldXP());
		PressEnter();
		Clear();
	}

	private static void space(int i) {
		for (int j = 0; j < i; j++) {
			System.out.println();
		}

	}

	private static void printStatus(Mob p, int offset) {
		String off = getXOffset(offset);
		System.out.println(off + "[" + p.get("name") + " | level " + p.lvl() + " " + p.get("name") + "]");
		printHP(p, off);
		printMana(p, off);
		printBuffs(p, off);

	}

	private static String getXOffset(int offset) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < offset; i++) {
			sb.append(" ");
		}
		return sb.toString();
	}

	private static void printBuffs(Mob p, String offset) {
		// TODO Auto-generated method stub

	}

	private static void printMana(Mob p, String offset) {
		// TODO Auto-generated method stub

	}

	private static void printHP(Mob p, String offset) {
		System.out.print(offset + "|HP:");
		for (int i = 0; i < 21; i++) {
			double hp = p.HP;
			double maxhp = p.maxHP;
			if (hp >= (i * p.maxHP) / 20) {
				System.out.print("+");
			} else {
				System.out.print(" ");
			}
		}
		System.out.print(": " + p.HP + "/" + p.maxHP + "|");
		System.out.println();
	}

	public static void printOffset(String text, String offset) {
		System.out.println(offset + text);
	}

	private static void mobturn(Mob m, Player p) {
		// MOB turn start (first)

		// TODO Proper method
		if (m.skill() != null && (m.skillRate() > rand.nextInt(100))) {
			// Uses skill
			int indamage = p.recDmg(m.skill.damage(m), m.skill.type());
			BattleMessage(m.get("name") + " used " + m.skill().name + " does " + indamage + " damage");

		} else {
			// Does regular damage
			int damage = p.recDmg(m.damage(), "phys");
			BattleMessage(m.get("name") + " hits you doing " + damage);
		}

		// MOB turn end

	}

	private static void BattleMessage(String string) {
		gameMessage(string);
		PressEnter();

	}

	private static void storydialog(String string) {
		System.out.print(string);
		PressEnter();
	}

	private static Player newGame() {
		Player p = new Player();
		gameMessage("What is your name warrior?");
		String name = input();
		p.setStat("name", name);
		gameMessage("What is your preference?");
		int valinta = choise("Strength,Intelligence,Agility,Charisma,Lucky");
		switch (valinta) {
		case 1: {
			p.setStat("luokka", "Warior");
			p.setPrimaryAttribute("str");
			break;
		}
		case 2: {
			p.setStat("luokka", "Wiser");
			p.setPrimaryAttribute("inte");
			break;
		}
		case 3: {
			p.setStat("luokka", "Speeder");
			p.setPrimaryAttribute("agi");
			break;
		}
		case 4: {
			p.setStat("luokka", "Alfa");
			p.setPrimaryAttribute("bty");
			break;
		}
		case 5: {
			p.setStat("luokka", "Lucker");
			p.setPrimaryAttribute("luck");
			break;
		}
		}
		
		boolean rollok = true;
		while (rollok) {
			p.nullaaStats();
			p.addPrimaryStat(8);
			p.addStat("str", 4 + rand.nextInt(4));
			p.addStat("inte", 4 + rand.nextInt(4));
			p.addStat("agi", 4 + rand.nextInt(4));
			p.addStat("luck", 4 + rand.nextInt(4));
			p.addStat("bty", 4 + rand.nextInt(4));
			p.update();
			p.fillHP();
			p.printStats();
			System.out.println(" REROLL ?");
			int re = choise("Yes,No");
			if (re != 1) {
				rollok = false;
			}
		}
		Clear();
		return p;
	}

	private static String input() {
		Scanner s = new Scanner(System.in);
		String input = "";
		boolean inproper = true;
		while (inproper) {
			input = s.nextLine();
			if (input.isEmpty())
				return input;
			boolean pass = false;
			for (int i = 0; i < input.length(); i++) {
				if (input.charAt(i) == ':') {
					System.out.println("Don't use \":\" character in any input. please type again");
					break;
				}
				inproper = false;
			}
		}

		if (input.equals("LEVELUP")) {
			p.levelUp();
		}
		return input;
	}

	public static int choise(String string) {
		String[] valinnat = string.split(",");
		int val = 0;
		for (int i = 0; i < valinnat.length; i++) {
			System.out.println((i + 1) + ":" + valinnat[i]);
		}
		boolean sopiva = true;
		while (sopiva) {
			val = askNumber();
			if (val < valinnat.length + 1) {
				sopiva = false;
			}
		}

		return val;
	}

	public static int choise(Object[] valinnat) {
		
		int val = 0;
		for (int i = 0; i < valinnat.length; i++) {
			System.out.println((i + 1) + ":" + valinnat[i]);
		}
		boolean sopiva = true;
		while (sopiva) {
			val = askNumber();
			if (val < valinnat.length + 1) {
				sopiva = false;
			}
		}

		return val;
	}

	public static int askNumber() {
		int tulos = 0;
		boolean onnistui = true;
		while (onnistui) {
			try {
				tulos = Integer.valueOf(input());
				onnistui = false;
			} catch (NumberFormatException e) {
				onnistui = true;
			}
		}
		return tulos;
	}

	private static void Clear() {
		for (int i = 0; i < 25; i++) {
			System.out.println();
		}

	}

	private static void PressEnter() {
		input();
	}

	private static void gameMessage(String string) {
		// TODO sophisticated layout
		System.out.println(string);
	}

	private static void gameMessage(String string, int offset) {
		// TODO sophisticated layout
		System.out.println(getXOffset(offset) + string);
	}

}
