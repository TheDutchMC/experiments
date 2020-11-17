package nl.thedutchmc.lpp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

public class App {
	final long dayLimit = 45L;
	
	public static void main(String[] args) {
		new App().application(args);
	}
	
	void application(String[] args) {
		if(args.length == 0) {
			System.err.println("No path provided!");
			System.exit(-1);
		}
		
		String arg0 = args[0];		
		File jsonDir = new File(arg0);
		
		System.out.println("File path: " + jsonDir.getAbsolutePath());
		
		if(!jsonDir.exists()) {
			System.err.println("Folder not found!");
			System.exit(-1);
		}
		
		//Discover all the files we want to parse
		List<String> files = discover(jsonDir);
		
		System.out.println("Discovered " + files.size() + " files ready for parsing.");
		System.out.println("Players who were recently online (less than 45 days):");
		
		//Get the current time
		LocalDate now = LocalDate.now();
		
		//Iterate over all the playerdata files
		for(String file : files) {
			try {
				//Get the JSONObject of the file
				JSONObject root = new JSONObject(new String(Files.readAllBytes(Paths.get(file))));
				
				//Get the NBT values
				JSONArray nbtVals = root.getJSONArray("nbt").getJSONObject(0).getJSONArray("value");
				
				//Iterate over the NBT values to find the Bukkit NBT values.
				AtomicReference<JSONObject> bukkitNbtValsRef = new AtomicReference<>();
				nbtVals.forEach(obj -> {
					JSONObject objJson = (JSONObject) obj;
					if(objJson.getString("name").equals("bukkit")) bukkitNbtValsRef.set(objJson);
				});
								
				boolean inWindow = false;
				String lastKnownName = "";
				
				//Iterate over the Bukkit NBT values to find the lastKnownName and lastPlayed JSONObjects
				for(Object o: bukkitNbtValsRef.get().getJSONArray("value")) {
					JSONObject oJson = (JSONObject) o;
					
					//Check if the current JSONObject is the lastKnownName one
					//If this is the case, set the lastKnownName String to the value of this JSONObject
					if(oJson.getString("name").equals("lastKnownName")) lastKnownName = oJson.getString("value");
					
					//Check if the current JSONObject is the lastPlayed one
					//If this is the case, get the value, convert it to a LocalDate object and determine
					//the amount of days between now and the lastPlayed date. If this falls within
					//the threshold, we accept it.
					if(oJson.getString("name").equals("lastPlayed")) {
						long lastPlayedTime = Long.valueOf(oJson.getString("value"));
						LocalDate lastPlayedDate = Instant.ofEpochMilli(lastPlayedTime).atZone(ZoneId.systemDefault()).toLocalDate();
						long days = ChronoUnit.DAYS.between(lastPlayedDate, now);
												
						if(days <= dayLimit) inWindow = true;
					}
				}
				
				//Value is within the provided window, print the name.
				if(inWindow) {
					System.out.println(lastKnownName);
				}
			} catch (IOException e) {
				System.err.println("Error in " + file + ": " + e.getMessage());
			}
		}
	}

	private List<String> discover(File storageFolder) {		
		try {
			Stream<Path> walk = Files.walk(Paths.get(storageFolder.getAbsolutePath()));
			List<String> result = walk.map(x -> x.toString()).filter(f -> f.endsWith(".json")).collect(Collectors.toList());
			walk.close();
			return result;
		} catch(IOException e) {		
			System.err.println(e.getMessage());
			return null;
		}
	}
}
