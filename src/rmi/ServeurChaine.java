package rmi;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ServeurChaine extends UnicastRemoteObject implements _Chaine
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -489163781128576314L;
	private List<Hotel> hotel;
	
	public ServeurChaine(String file) throws RemoteException
	{
		hotel = new ArrayList<Hotel>();DocumentBuilder docBuilder = null;
		Document doc=null;
		try
		{
			docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();doc = docBuilder.parse(new File(file));
			
			String name, localisation1;
			NodeList list = doc.getElementsByTagName("Hotel");
			NamedNodeMap attrs;

			for(int i =0; i<list.getLength();i++)
			{
				attrs = list.item(i).getAttributes();
				name=attrs.getNamedItem("name").getNodeValue();
				localisation1=attrs.getNamedItem("localisation").getNodeValue();
				hotel.add(new Hotel(name,localisation1));
			}
		
			// DocumentBuilder docBuilder1 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			// Document doc1 = docBuilder1.parse(new File(file));

			// String arguments = doc1.getElementsByTagName("service").item(0).getAttributes().getNamedItem("args").getNodeValue();
		}
		catch (ParserConfigurationException | SAXException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Hotel> get(String file)
	{
		return hotel;
	}
	
}
