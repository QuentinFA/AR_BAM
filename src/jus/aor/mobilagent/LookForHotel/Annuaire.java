package jus.aor.mobilagent.LookForHotel;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jus.aor.mobilagent.kernel._Service;

public class Annuaire implements _Service<HashMap<String,String>>{

	public HashMap<String,String> annuaireT = new HashMap<String,String>();
	public String annuaire;
	
	public Annuaire(Object... args){
		System.out.println(args[0]);
		this.annuaire =(String) args[0];
		this.getTel();
	}
	
	public void getTel() {
		// TODO Auto-generated method stub
		
		DocumentBuilder docBuilder = null;
		Document doc = null;
		
		
		try {
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			doc = docBuilder.parse(new File(annuaire));
		} catch (IOException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String name, numero;
		NodeList list = doc.getElementsByTagName("Telephone");
		NamedNodeMap attrs;
		/* acquisition de toutes les entrées de l'annuaire */
		for(int i =0; i<list.getLength();i++) {
			attrs = list.item(i).getAttributes();
			name=attrs.getNamedItem("name").getNodeValue();
			numero=attrs.getNamedItem("numero").getNodeValue();
			
			annuaireT.put(name,numero);
		}
		
	}
	
	
	@Override
	public HashMap<String,String> call(Object... params)
			throws IllegalArgumentException {
		
		Object[] arg = params;
		Set<String> liste  = (Set<String>) arg[0];
		
		HashMap<String,String> hotels = new HashMap<String,String>();
		
		for (String entry : liste) {
			if (this.annuaireT.containsKey(entry)) {
				hotels.put(entry,annuaireT.get(entry));
			}
			else{
				hotels.put(entry, null);
			}
		}
	
		return hotels;
	}

}