import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Mobs {
	
	private Map<String,Mob> mobs;
	public Mobs(){
		this.mobs = new HashMap<String,Mob>();
		load();
	}
	private void load() {
		File dir = new File("data/mobs");
		String[] allfiles = dir.list();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < allfiles.length; i++) {
			if((new File("data/mobs/"+allfiles[i])).isDirectory()){
				continue;
			}
			sb.append(allfiles[i]+"@");
		}
		String[] files = sb.toString().split("@");
		for (int i = 0; i < files.length; i++) {
			try {
				Mob m = new Mob();
				BufferedReader br = new BufferedReader(new FileReader("data/mobs/"+files[i]));
				String rivi;
				while((rivi=br.readLine())!=null){
					if(rivi.startsWith("/")){
						continue;
					}
					String[] data = rivi.split(":");
										
					if(data[0].equals("skill")){
						m.setSkill(Game.skills.get(data[1]));
						continue;
					}
					if(data[0].equals("primary")){
						m.setPrimaryAttribute(data[1]);
						continue;
					}
					
					//this is how it should go
					m.setStat(data[0], data[1]);
				}
				m.updateMaxHP();
				m.restoreFullHP();
				this.mobs.put(files[i].substring(0,files[i].length() - 2), m);
				br.close();
			} catch (IOException e) {
				System.err.println("Something went wrong baldy when creating a mob " + e.getMessage());
			}
		}
		
		
	}
	
	public Mob getMob(String name){
		if(this.mobs.get(name) == null){
			Object[] keys = this.mobs.keySet().toArray();
			for (int i = 0; i < keys.length; i++) {
				if(this.mobs.get(keys[i]).get("name").equals(name)){
					return this.mobs.get(keys[i]).copy();
				}
			}
			return this.mobs.get("orge").copy();
		}
		
		return this.mobs.get(name).copy();
	}
}
