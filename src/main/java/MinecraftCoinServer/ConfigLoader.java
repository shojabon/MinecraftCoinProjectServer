package MinecraftCoinServer;

import java.io.*;
import java.util.HashMap;

public class ConfigLoader {

    HashMap<String, String> map = new HashMap<>();

    public ConfigLoader(File f){
        try {
            FileReader filereader = new FileReader(f);
            BufferedReader br = new BufferedReader(filereader);
            String str = br.readLine();
            while(str != null){
                str = br.readLine();
                if(str != null && str.contains("=")){
                    String[] ar = str.split("=");
                    if(ar.length == 2){
                        map.put(ar[0], ar[1]);
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String get(String key){
        return map.get(key);
    }
}
