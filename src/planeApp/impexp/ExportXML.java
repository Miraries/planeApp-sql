package planeApp.impexp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class ExportXML {
	public static String generateXML(Object o) throws Exception {
		ObjectMapper xmlMapper = new XmlMapper();
		xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
		String xml = xmlMapper.writeValueAsString(o);
		return xml;
	}
	
	public static String generateCorrectedXML(Object o) throws Exception {
		return generateXML(o).replaceAll("ArrayList", "flights");
	}
}
