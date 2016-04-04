package rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

/**
 * J<i>ava</i> U<i>tilities</i> for S<i>tudents</i>
 */

/**
 * Représente un client effectuant une requête lui permettant d'obtenir 
 * les numéros de téléphone des hôtels répondant à son critère de choix.
 * @author  Morat
 */
public class LookForHotel{
	/** le critère de localisaton choisi */
	private String localisation;
	private List<Hotel> hList;
	private List<Numero> nList;
	/**
	 * Définition de l'objet représentant l'interrogation.
	 * @param args les arguments n'en comportant qu'un seul qui indique le critère
	 *          de localisation
	 */
	public LookForHotel(String... args)
	{
		_Annuaire annuaire;
		localisation = args[0];
		nList = new ArrayList<>();
		hList = new ArrayList<>();
		
		try
		{
			Registry registry = LocateRegistry.getRegistry(Main.PORT);
			for(int i = 0; i < Main.NB_CHAINE; i++)
				hList.addAll(((_Chaine) registry.lookup(Main.RMI_CHAINE + i)).get(localisation));
			annuaire = (_Annuaire) registry.lookup(Main.RMI_ANNUAIRE);
			for(Hotel h : hList)
				nList.add(annuaire.get(h.name));
		}
		catch (RemoteException | NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * réalise une intérrogation
	 * @return la durée de l'interrogation
	 * @throws RemoteException
	 */
	public long call() 
	{
		return 0;
	}
}
