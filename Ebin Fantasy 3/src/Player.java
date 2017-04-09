import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class Player extends Mob {

	public Story story;
	public Player(){
		super();
		stats.get("lvl").set(1);
		story = new Story();
	}
	
	
	public Player(String name){		
		super();
		try {
			BufferedReader br = new BufferedReader(new FileReader("data/saves/"+name+"/"+name+".sav"));
			String rivi;
			
			while((rivi = br.readLine())!= null){
				if(rivi.startsWith("/")){
					continue;
				}
				
				String[] data = rivi.split(":");
			}
		} catch (IOException e) {
			// TODO: handle exception
		}
	}


	public void fillHP() {
		this.HP = this.maxHP;
	}


	public void nullaaStats() {
		this.stats.get("str").set(0);
		this.stats.get("agi").set(0);
		this.stats.get("inte").set(0);
		this.stats.get("luck").set(0);
		this.stats.get("bty").set(0);
	}
	
}
