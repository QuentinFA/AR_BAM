package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
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
	private static List<String> localisation;
	private static List<Hotel> hList;
	private static List<Numero> nList;
	/**
	 * Définition de l'objet représentant l'interrogation.
	 * @param args les arguments n'en comportant qu'un seul qui indique le critère
	 *          de localisation
	 */
	public LookForHotel(String... args)
	{
		localisation = Arrays.asList(args);
		nList = new ArrayList<>();
		hList = new ArrayList<>();
	}
	
	/**
	 * réalise une intérrogation
	 * @return la durée de l'interrogation
	 * @throws RemoteException
	 */
	public long call() 
	{
		long begining = System.currentTimeMillis();
		
		_Annuaire annuaire;
		
		try
		{
			for(int i = 0; i < Values.NB_CHAINE; i++)
			{
				for(String loc : localisation)
				{
					_Chaine c = (_Chaine) Naming.lookup(Values.RMI_CHAINE + i);
					hList.addAll(c.get(loc));
				}
			}
			
			annuaire = (_Annuaire) Naming.lookup(Values.RMI_ANNUAIRE);
			
			for(Hotel h : hList)
				nList.add(annuaire.get(h.name));
			
			System.out.println(nList.toString());
		}
		catch (RemoteException | NotBoundException | MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return begining - System.currentTimeMillis();
	}
	
	public static void main(String[] args)
	{
		localisation = Arrays.asList(args);
		hList = new ArrayList<>();
		nList = new ArrayList<>();
		
		_Annuaire annuaire;
	
		try
		{
			for(int i = 0; i < Values.NB_CHAINE; i++)
			{
				for(String loc : localisation)
				{
					_Chaine c = (_Chaine) Naming.lookup(Values.RMI_CHAINE + i);
					hList.addAll(c.get(loc));
				}
			}
			
			annuaire = (_Annuaire) Naming.lookup(Values.RMI_ANNUAIRE);
			
			for(Hotel h : hList)
				nList.add(annuaire.get(h.name));
			
			System.out.println(nList.toString());
		}
		catch (RemoteException | NotBoundException | MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
