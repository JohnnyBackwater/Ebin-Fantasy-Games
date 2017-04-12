package Game;

import java.util.Random;
import java.util.Scanner;

import GameObjects.Mob;
import GameObjects.Player;

public class Game {

	public static Player player;
	public static Scanner Reader;
	public static Random rand;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Reader = new Scanner(System.in);
		rand = new Random();
		
		player = new Player();
		player.Class = "White Warrior";
		boolean testOn = true;
		while(testOn){
			GainXP(100);
			
		}
		//Mainscreen only displayed at the start.
		boolean menu = true;
		int sayopt;
		while(menu){
			System.out.println("      Ebin Fantasy\n       Remastered\n\n");
			int Action = Choose("New Adventure_Continue_Exit");
			//New game
			if(Action == 1){
				player = new Player();
				player.ActID = 0;
				System.out.println("What kind of character you want to be?");
				int heroType = Choose("Warrior the White_Wizard the Wize_Black the Burglar");
				if(heroType == 1) {
					player.Class = "White Warrior";
					Storytext("Strength is your strength! Gods grant you extra strength!");
					IncreaseStats(5, 0, 0, 0);
					menu = false;
				}
				if(heroType == 2){
					Notify("Not implemented, try warrior");
				}
				if(heroType == 3){
					Notify("Not implemented, try wizard");
				}
				
			}
			
			//Load a saved game
			if (Action == 2) {
				Notify("Not implemented");
			}
			
			//Exit, Stop
			if (Action == 3) {
				System.exit(1);
			}
		}
		
		//act 0: Intro and tutorial
		if(player.currentAct(0)){

			Storytext("The journey begins.");
			Storytext("Troublesome times it is.");
			Storytext("Only One can save everything, from total destruction.");
			Storytext("Who migh it be?");
			Dialog("???","Heyyy! Wake up!");
			Say("ZzZzz...");
			Dialog("???","Oniichan~!! Get up, or your gonna miss the buss!");
			Say("Wh- wha- Oniiwhat? Miss the what?");
			Dialog("???","Stop joking, I'm not letting you be late again! Baka!");
			sayopt = Say("What's going on? Who are you?_*angrily* Go away!");
			if(sayopt == 1){
				player.Happyending = 1;
				Dialog("???","Haha, you're funny! Now get up and-...");
			}
			
			if(sayopt==2){
				player.Happyending = 0;
				Dialog("???","Uguu~ why are you so mean? Baka onii-...");
			}
			
			Dialog("World","SWOOOOOOOOOOSH!");
			Dialog("World","and BANG");
			Storytext("...");
			Storytext("......");
			Storytext("........alarm...");
			Storytext("ALAAAARM!!!");
			Dialog("Officer","You there! Were you sleeping on duty?!");
			sayopt = Say("*lie* No!_Y-yes_Problems?");
			if(sayopt == 2){
				Dialog("Officer","Well, we have to postpone the field trial for tomorrow.");
			}
			if(sayopt == 3){
				Dialog("Officer", "Yes! Big problems!");
			}
			Dialog("Officer","There's no time to explain, see those orges over there?");
			Say("Yes?");
			Dialog("Officer","Well? Soldier? Get in there and batter them away!");
			//Here 2 and 3 are game overs
			sayopt = Say("Will do it, sir!_But sir! I'm doing the unarmed service!_Wait a minute. Am I supposed to be with the ladies in the population shelter?");
			
			if(sayopt == 2){
				Dialog("Officer", "Really? In hhe famous Migs brigade!");
				Dialog("Officer", "Then you will go negotiate with them and make leave!");
				sayopt = Say("But sir! Orges do not listen!_Okay!");
				
				if(sayopt == 1){
					Dialog("Officer", "What are you good then?");
					ClearScreen();
					Storytext("As you were wasting time talking with the Officer, the orges advanced.");
					
				}
				if(sayopt == 2){
					ClearScreen();
					Storytext("You tried to negotiate with the orges in vain.");
				}
				Storytext("Your positions were overrun by the orges and Orge Warmaster destroyed the city.");
				Notify("You died");
				GameOver();
			}
			if(sayopt == 3){
				Dialog("Officer", "Are you a girl?");
				sayopt = Say("Yes_*lie*No");
				
				if(sayopt == 1){
					Dialog("Officer", "Then m'lady, get to the shelter fast! We'll protect you *tips helmet*");
					ClearScreen();
					Storytext("You go to the shelter with the women and children.");
					Storytext("However, towns defenses get overrun by the orges.");
					Storytext("You are captured and you end up serving as a slave in Orgitown.");
					Storytext("After few weeks you die in the Orge Warmasters palace.");
					Notify("You died");
					GameOver();
				}
				
				if(sayopt == 2){
					Dialog("Officer", "Now that's what I wanted to hear! Now get into the fight!");
					ClearScreen();
					Notify("So you ended up being a girl. Combat stats readjusted!");
					IncreaseStats(player.Str*-1 + 1, player.Int*-1 + 1, player.Agi*-1 +1, player.Luck*-1 + 1);
				}
			}
			
			Battle(new Mob("Orge", 1, 50, 9, 2, 100, "A very ugly creature. Green skin and few meters tall. The basic mobs around this world", "none"));
			Battle(new Mob("Orge", 1, 50, 9, 2, 100, "A very ugly creature. Green skin and few meters tall. The basic mobs around this world", "none"));
						
		}
		// The game ends here
		
		
		Notify("The end... For now");
	}
	
	/**
	 * 
	 * @param mob
	 */
	private static void Battle(Mob mob) {
		BMessage(mob.Name + " encountered!");
		
		//TODO player hp must be recovered other ways maybe?
		player.HP = player.MaxHP;
		while(mob.HP > 0 || player.HP > 0){
			
			//higher level enemies are faster
			if(mob.Level > player.Level){
				//Mob AI here
				MobAi(mob);
			}
			if(player.HP <= 0){
				//player is dead
				Notify("You died");
				GameOver();
			}
			//player action here if not dead
			
			//Loop untill the action can be done. Player can always attack
			boolean done = false;
			int action = 0;
			while(!done){
				//print hp of each
				System.out.println("                    " + mob.Name + " | Level " + mob.Level);
				System.out.print("                    ");
				System.out.println(makeBar("HP", mob.HP, mob.MaxHP, 15, "="));
				System.out.println("]");
				System.out.println("\n\n\n");
				System.out.println(player.Name + " | Level " + player.Level + " " + player.Class);
				System.out.println(makeBar("HP", player.HP, player.MaxHP, 20, "="));
				System.out.println(makeBar("XP", player.XP, player.MaxXP, 20, "¤"));
				System.out.println("What do?");
				action = Choose("Attack_Magic");
				
				if(action == 1){
					//player attacks
					//Calculate incoming damage, defense is straight damage reduction
					int damage = player.Damage() - mob.Def;
					//negative values of damage not allowed
					if(damage < 0){
						damage = 0;
					}
					BMessage("You attack the " + mob.Name + " dealing " + damage + " damage!");
					mob.HP -= damage;
					done = true;
				}
				
				if (action == 2) {
					BMessage("You don't know any magic!");
				}
			}
			
			
			//if mob didn't attack earlier, then here
			if(mob.Level <= player.Level){
				MobAi(mob);
			}
			if(player.HP <= 0){
				//player is dead
				Notify("You died");
				GameOver();
			}
		}
		if(player.HP <= 0){
			//player is dead
			Notify("You died");
			GameOver();
		}
		if(mob.HP <= 0){
			BMessage(mob.Name + " died!");
			int gold = 3 + rand.nextInt(20);
			BMessage("Found " + gold + " gold!");
			player.Gold += gold;
			GainXP(mob.XP);
		}
		
	}
	
	private static void GainXP(int xP) {
		System.out.println("Gained " + xP + " XP!");
		player.XP += xP;
		System.out.println(makeBar("XP", player.XP, player.MaxXP, 20, "¤"));
		PressEnter();
		while(player.XP >= player.MaxXP){
			LevelUp();
			
		}
	}

	private static void LevelUp() {
		System.out.println("!LEVEL UP!");
		PressEnterTo("Nice");
		player.Level++;
		player.XP -= player.MaxXP;
		player.MaxXP += 100 + player.MaxXP/6;
		//after lvl 50 a very steep curve
		if(player.Level > 50){
			player.MaxXP += player.MaxXP/3;
		}
		if(player.isWarrior()){
			IncreaseStats(2 + rand.nextInt(2), rand.nextInt(2), rand.nextInt(3), rand.nextInt(2));
		}
		if(player.isBurglar()){
			IncreaseStats(rand.nextInt(3), rand.nextInt(2), 2 + rand.nextInt(1), rand.nextInt(2));
		}
		if(player.isWizard()){
			IncreaseStats(rand.nextInt(2), 2 + rand.nextInt(3), rand.nextInt(3), rand.nextInt(2));
		}
		
	}

	private static void PressEnterTo(String string) {
		System.out.print("<enter> to:"+string);
		Reader.nextLine();
		
	}

	/**
	 * Makes an bar for any value to display
	 * @param title and name of the bar
	 * @param hp current value of the bar
	 * @param maxHP maximum value of the bar
	 * @param length length of the bar
	 * @param c character to use to fill the bar with
	 * @return
	 */
	private static String makeBar(String type,int hp, int maxHP, int length, String c) {
		StringBuffer sb = new StringBuffer();
		sb.append(type + " " + hp+"/"+maxHP + " [");
		for (int i = 1; i <= length; i++) {
			if((maxHP / length)*i <= hp){
				sb.append(c);
			}
			else{
				sb.append(" ");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	private static void MobAi(Mob mob) {
		//Mobs do one thing in the following priority 
		//1. specials, 2. attacks
		//if mob has no abilities the first item in the list is "none", no need to check else
		if(!mob.Abilities[0].equals("none")){
			//other abilities check here
			return;
		}
		
		//2. attacks
		int damage = mob.Dmg - player.Def;
		BMessage("Orge attacks you, dealing " + damage + " damage!");
		player.HP -= damage;
		
	}

	private static void BMessage(String string) {
		System.out.println("(| " + string + " |)");
		PressEnter();
		
	}

	private static void GameOver() {
		ClearScreen();
		System.out.println("[[ GAME OVER ]]");
		System.out.println();
		System.out.println("<Press enter to exit>");
		PressEnter();
		System.exit(0);
		
	}
	private static int Say(String string) {
		String[] items = string.split("_");
		for (int i = 0; i < items.length; i++) {
			System.out.println(" [" + (i + 1) + "] " + items[i]);
		}
		int item = TakeNumber(1, items.length);
		ClearScreen();
		Dialog(player.Name,items[item-1]);
		return item;
	}
	/**
	 * Who says and what. Must press enter to advance. Screen is not cleared after.
	 * @param who
	 * @param says
	 */
	private static void Dialog(String who, String says) {
		System.out.println("[ " + who + " ] \"" + says +"\"");
		PressEnterNc();
		
	}
	private static void Storytext(String string) {
		System.out.println("|<>| " + string);
		PressEnter();
				
	}
	/**
	 * Press enter and clear screen
	 */
	private static void PressEnter() {
		System.out.print(">");
		Reader.nextLine();
		ClearScreen();
	}
	/**
	 * Press enter with no clear screen
	 */
	private static void PressEnterNc() {
		Reader.nextLine();
	}
	/**
	 * Increase player stats and display the gains
	 * @param hp
	 * @param str
	 * @param inte
	 * @param agi
	 * @param luck
	 */
	private static void IncreaseStats(int str, int inte, int agi, int luck) {
		System.out.println("Stats increased\n");
		System.out.println(player.Name + " |  Level " + player.Level + " " + player.Class);
		System.out.println();
		System.out.println("HP:           " + player.MaxHP + "+(" + 5*str + ")");
		System.out.println("Strength:     " + player.Str + " +(" + str + ")");
		System.out.println("Intelligence: " + player.Int + " +(" + inte + ")");
		System.out.println("Agility:      " + player.Agi + " +(" + agi + ")");
		System.out.println("Luck:         " + player.Luck + " +(" + luck + ")");
		System.out.println("---------------------------------------");
		PressEnter();
		player.IncreaseStats(5*5, str, inte, agi, luck);
		PrintStats();
		PressEnter();
		
	}
	private static void PrintStats() {
		System.out.println(player.Name + " |  Level " + player.Level + " " + player.Class);
		System.out.println();
		System.out.println("HP:           " + player.MaxHP);
		System.out.println("Strength:     " + player.Str);
		System.out.println("Intelligence: " + player.Int);
		System.out.println("Agility:      " + player.Agi);
		System.out.println("Luck:         " + player.Luck);
		System.out.println(makeBar("XP", player.XP, player.MaxXP, 20, "¤"));
		
	}
	private static void Notify(String string) {
		System.out.println("<["+string+"]>");
		Choose("OK");
		
	}
	/**
	 * Write down possible list of options separated by '_' character like this: "option_option_option" is a list of three options
	 * User can only submit on of the choises. The screen is cleared after a prorper selection and the option is NOT printed
	 * Use this only as a option making. If you are making a dialog use "Say" method
	 * @param string
	 * @return
	 */
	private static int Choose(String string) {
		String[] items = string.split("_");
		for (int i = 0; i < items.length; i++) {
			System.out.println(" [" + (i + 1) + "] " + items[i]);
		}
		int item = TakeNumber(1, items.length);
		ClearScreen();
		return item;
	}
	private static void ClearScreen() {
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		
	}
	/**
	 * Waits for the user to give a positive number ranging from i to n both including
	 * 
	 * It will not return until the right number is given
	 * @param i
	 * @param n
	 */
	private static int TakeNumber(int i, int n) {
		
		
		int number = i-1;
		int failcount = 0;
		while (number < i || number > n) {
			try {
				String numberString = Input();
				number = Integer.parseInt(numberString);
			} catch (Exception e) {
				failcount++;
				if (failcount > 5) {
					Complain("Try to give an reasonable input");
				}
				number = -1;
			}
		}
		
		return number;
	}
	private static String Input() {
		System.out.print("[");
		String input = Reader.nextLine();
		return input;
	}
	private static void Complain(String string) {
		System.out.println("Baka! " + string);
		
	}

}
