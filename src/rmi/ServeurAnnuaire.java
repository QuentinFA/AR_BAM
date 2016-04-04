package rmi;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ServeurAnnuaire implements _Annuaire
{
	private static final String TAG_TELEPHONE = "Telephone";
	private static final String TAG_NAME = "name";
	private static final String TAG_NUM = "numero";
	
	private Map<String, Numero> annuaire;
	
	public ServeurAnnuaire(String file)
	{
		annuaire = new HashMap<String, Numero>();
		
		DocumentBuilder docBuilder = null;
		Document doc = null;
		try
		{
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = docBuilder.parse(new File(file));
			
			NodeList nl = doc.getElementsByTagName(TAG_TELEPHONE);
			
			for(int i = 0; i < nl.getLength(); i++)
			{
				NamedNodeMap n = nl.item(i).getAttributes();
				annuaire.put(n.getNamedItem(TAG_NAME).getNodeValue(),
						new Numero(n.getNamedItem(TAG_NUM).getNodeValue()));
			}
		}
		catch (ParserConfigurationException | SAXException | IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public Numero get(String abonne)
	{
		return annuaire.get(abonne);
	}
	
}
