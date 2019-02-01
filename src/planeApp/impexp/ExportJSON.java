package planeApp.impexp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ExportJSON {
	public static String generateJSON(Object o) throws Exception {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(o);
		return json;
	}
}
