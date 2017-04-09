import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Location {
	
	private String name;
	
	private ArrayList<String> locations = new ArrayList<String>();
	private ArrayList<String> mobs = new ArrayList<String>();
	private ArrayList<Location> nextLoc = new ArrayList<Location>();
	private ArrayList<String> shops = new ArrayList<String>();
	private int hostility;
	
	/**
	 * This reads the source file and imports it into a location
	 * 
	 * Something about syntax
	 * 
	 * name:<name> = is the name in String
	 * locations:<name>,<name> ... = list of locations
	 * mobs:<name>,<name> ... = list of mobs at arrival if empty, no mobs
	 * hostility: int = possibility to encounter mobs (percent)
	 * shops:<name>,<name> ... = list of shops
	 * 
	 * 
	 * @param name
	 */
	public Location(String name){
		try {
			BufferedReader br = new BufferedReader(new FileReader("data/loc/"+name+".loc"));
			String rivi;
			
			while((rivi = br.readLine()) != null){
				if(rivi.startsWith("/")){ continue; }
				String[] data = rivi.split(":");
				
				if(data[0].equals("name")){
					this.name = data[1];
					continue;
				}
				
				if(data[0].equals("locations")){
					String[] locs = data[1].split(",");
					for (int i = 0; i < locs.length; i++) {
						this.locations.add(locs[i]);
					}
					continue;
				}
				
				if(data[0].equals("mobs")){
					if(data.length < 2){
						continue;
					}
					String[] moblist = data[1].split(",");
					for (int i = 0; i < moblist.length; i++) {
						this.mobs.add(moblist[i]);
					}
					continue;
				}
				
				if(data[0].equals("hostility")){
					try {
						this.hostility = Integer.valueOf(data[1]);
					} catch (Exception e) {
						this.hostility = 0;
					}
				}
				
				if(data[0].equals("shops")){
					String[] shop = data[1].split(",");
					for (int i = 0; i < shop.length; i++) {
						shops.add(shop[i]);
					}
				}
						
				
			}
			
			
		} catch (IOException e) {
			
		}
		this.name = name;
	}
	
	

	/**
	 * Random mob name to fight with
	 * @return
	 */
	public String encounter(){
		
		return this.mobs.get(Game.rand.nextInt(this.mobs.size()));
	}

	
	

	public void setHostility(int i) {
		this.hostility = i;	
	}

	public int hostility() {
		return this.hostility;
	}

	public void setName(String name) {
		this.name = name;
		
	}



	public boolean isMobs() {
		if(this.mobs == null){
			return false;
		}
		if(this.mobs.size() == 0){
			return false;
		}
		return true;
	}



	public String name() {
		return this.name;
	}


	public String whatDo() {
		StringBuilder sb = new StringBuilder();
		sb.append("You");
		if(!this.shops.isEmpty()){
			sb.append(",Shop");
		}
		if(!this.mobs.isEmpty()){
			sb.append(",Seek creatures");
		}
		sb.append(",Travel");
		
		String[] valinnat = sb.toString().split(",");
		return valinnat[Game.choise(sb.toString())];
	}



	public String whichShop() {
		Object[] s = this.shops.toArray();
		return (String) s[Game.choise(s)];
	}
	
}
