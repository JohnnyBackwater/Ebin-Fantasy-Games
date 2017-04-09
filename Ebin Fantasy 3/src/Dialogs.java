import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Dialogs {

	private Map<String,String[]> dialogs;
	
	public Dialogs(){
		dialogs = new HashMap<String, String[]>();
		Load();
	}
	public String[] dialogi(String string) {
		return this.dialogs.get(string+".d");
	}
	
	public void Load(){
		File dir = new File("data/dialog");
		String[] allfiles = dir.list();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < allfiles.length; i++) {
			if((new File("data/dialog/"+allfiles[i])).isDirectory()){
				continue;
			}
			sb.append(allfiles[i]+"@");
		}
		String[] files = sb.toString().split("@");
		for (int i = 0; i < files.length; i++) {
			try {
				BufferedReader br = new BufferedReader(new FileReader("data/dialog/"+files[i]));
				StringBuffer sb2 = new StringBuffer();
				String rivi;
				while((rivi=br.readLine()) != null){
					if(rivi.startsWith("/")){
						continue;
					}
					
					sb2.append(rivi+"@");	
				}
				this.dialogs.put(files[i], sb2.toString().split("@"));
				br.close();
			} catch (IOException e) {
				System.err.println("Something went wrong at the loading of dialogs "+e.getMessage());
			} 
		}
	}

}
