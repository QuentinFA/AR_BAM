package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Serveur
{
	/**
	 * Constructor used for a Chaine server
	 * @param file The path to the Hotel.xml file
	 * @param port The port on which the registry will be
	 */
	public Serveur(String file, int port)
	{
		try
		{
			LocateRegistry.createRegistry(port);
			
			Naming.rebind(Values.RMI_CHAINE + port, new ServeurChaine(file));
		}
		catch (RemoteException | MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructor used for an Annuaire server
	 * @param file The path to Annuaire.xml
	 */
	public Serveur(String file)
	{
		try
		{
			LocateRegistry.createRegistry(Values.ANNUAIRE_PORT);
			Naming.rebind(Values.RMI_ANNUAIRE, new ServeurAnnuaire(Values.FILE_ANNUAIRE));
		}
		catch (RemoteException | MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		try
		{	
			LocateRegistry.createRegistry(Values.BASE_PORT);
			
			for(int i = 0; i < Values.NB_CHAINE; i++)
				Naming.rebind(Values.RMI_CHAINE + i, new ServeurChaine("DataStore/Hotels" + (i + 1) + ".xml"));
			
			Naming.rebind(Values.RMI_ANNUAIRE, new ServeurAnnuaire(Values.FILE_ANNUAIRE));
		}
		catch (RemoteException | MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
