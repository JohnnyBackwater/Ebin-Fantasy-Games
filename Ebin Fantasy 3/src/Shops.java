import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;


public class Shops {

	public ArrayList<Shop> shops = new ArrayList<Shop>();
	
	
	public Shops(){
		loadShops();
	}


	private void loadShops() {
		File dir = new File("data/shops");
		if(!dir.exists()){
			System.out.println("SHIEET. \"Shops\" directory not there!");
		}
		
		File[] files = dir.listFiles(new FilenameFilter(){ 
			public boolean accept(File dir, String name){
				return name.toLowerCase().endsWith(".s");
			}
		});
		
		for (int i = 0; i < files.length; i++) {			
			try {
				BufferedReader br = new BufferedReader(new FileReader(files[i]));
				String rivi;
				Shop s = new Shop();	
				while((rivi = br.readLine()) != null){
					if(rivi.startsWith("/")){ continue; }	
					String[] data = rivi.split(":");
					
					if(data[0].equals("name")){
						s.name = data[1];
					}
					if(data[0].equals("items:")){
						String[] itms = data[1].split(",");
						for (int j = 0; j < itms.length; j++) {
							s.items.add(itms[i]);
						}
					}
					
				}
				
				
			} catch (Exception e) {
				System.out.println("ERROR AT SHOP LOADING THING");
			}
		}
		
		
	}


	public Shop getShop(String name) {
		for (int i = 0; i < this.shops.size(); i++) {
			if (this.shops.get(i).equals(name)) {
				return this.shops.get(i);
			}
		}
		return null;
	}
	
}

class Shop{
	
	public String name;
	public ArrayList<String> items = new ArrayList<String>();
	
	public Shop(){
		
	}
}
