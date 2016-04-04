package rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Serveur
{
	public static void main(String[] args)
	{
		Registry registry;
		
		try
		{
			registry = LocateRegistry.createRegistry(Main.PORT);
			for(int i = 0; i < Main.NB_CHAINE; i++)
				registry.bind(Main.RMI_CHAINE + i, new ServeurChaine("Configurations/Hotels" + i + ".xml"));
			registry.bind(Main.RMI_ANNUAIRE, new ServeurAnnuaire(Main.FILE_ANNUAIRE));
		}
		catch (RemoteException | AlreadyBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
