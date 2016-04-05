package jus.aor.mobilagent.LookForHotel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import rmi.Hotel;
import jus.aor.mobilagent.kernel._Service;

public class Chaine implements _Service<List<Hotel>>{
	List<Hotel> hotels;
	
		public Chaine(Object... args){
			String docChaine=(String) args[0];
			hotels=new ArrayList<Hotel>();
			System.out.println("Service Chaine demarré "+docChaine); 
			DocumentBuilder docBuilder = null;
			org.w3c.dom.Document doc=null;
			try {
				docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				doc = docBuilder.parse(new File(docChaine));
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			String name, localisation;
			NodeList list = doc.getElementsByTagName("Hotel");
			NamedNodeMap attrs;
			/* acquisition de toutes les entrées de la base d'hôtels */
			for(int i =0; i<list.getLength();i++) {
				attrs = list.item(i).getAttributes();
				name=attrs.getNamedItem("name").getNodeValue();
				localisation=attrs.getNamedItem("localisation").getNodeValue();
				hotels.add(new Hotel(name,localisation));
			}

		}
	@Override
	public List<Hotel> call(Object... params) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		System.out.println("Service Chaine call");  
		List<Hotel> result=new ArrayList<Hotel>();
		for(Hotel temp: hotels){
			if(temp.localisation.equals(params[0])){
				result.add(temp);
			}
		}
		return result;
	}

}