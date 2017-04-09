import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Skills {
	public static Map<String, Skill> skills = new HashMap<String, Skill>();
	public Skills(){
		//Loads skills
		skills = new HashMap<String,Skill>();
		File dir = new File("data/skills");
		File[] files = dir.listFiles(new FilenameFilter(){ 
			public boolean accept(File dir, String name){
				return name.toLowerCase().endsWith(".s");
			}
		});
		
		for (int i = 0; i < files.length; i++) {
			try {
			BufferedReader br = new BufferedReader(new FileReader(files[i]));
			String rivi;
			Skill s = new Skill();
			while((rivi = br.readLine())!= null){
				if(rivi.startsWith("/")) continue;
				s.setAttr(rivi);
			}
			skills.put(s.name, s);
			br.close();
			} catch (IOException e) {
			// TODO: handle exception
		}
		}
		
	}
	public static Skill get(String string) {
		Skill ret = skills.get(string);
		return ret;
	}
}
