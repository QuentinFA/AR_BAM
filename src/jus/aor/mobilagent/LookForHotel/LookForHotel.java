package jus.aor.mobilagent.LookForHotel;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import jus.aor.mobilagent.kernel.Agent;
import jus.aor.mobilagent.kernel._Action;
import jus.aor.mobilagent.kernel._Service;
import rmi.Hotel;

public class LookForHotel extends Agent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9140260559401846017L;
	private HashMap<String,String> HotelsFound;
	private String localisation;
	private long StartTime;
	
	
	public LookForHotel(Object... args ) {
		if (args.length ==1){
			this.localisation = (String) args[0];
		}
	}
	
	public _Action findHotel = new _Action() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = -436006694923584375L;

			@Override
			public void execute() {
				_Service<List<Hotel>> HotelsHere = (_Service<List<Hotel>>) currentsrv.getService("Hotels");
				if (HotelsHere != null){
						if (HotelsFound == null){
							HotelsFound = new HashMap<String,String>();
						}
						for (Hotel hot : HotelsHere.call(new Object[]{localisation})) {
							HotelsFound.put(hot.name, null);
						}
					
					}
				}
			
		}
		;
	
	public _Action findNumber= new _Action() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void execute() {
				System.out.println("get numbres");
				_Service<HashMap<String, String>> HotelsHere = (_Service<HashMap<String,String>>) currentsrv.getService("Telephones");
				if (HotelsHere != null){
					System.out.println("check");
					Set<String> unknowns = HotelsFound.keySet();
					for( String entry : unknowns){
						if (HotelsFound.get(entry)!= null) {
							unknowns.remove(entry);
						}
					}
					HotelsFound = HotelsHere.call(HotelsFound.keySet());
				}
			}
		};

	public _Action Start(){
		
		return new _Action(){

			/**
			 * 
			 */
			private static final long serialVersionUID = -436006694923584375L;

			@Override
			public void execute() {
				_Service<Long> duration = (_Service<Long>) currentsrv.getService("Duration");
				if (duration == null){
					System.out.println("le serveur de base ne permet pas de charger le timer");
				}
				else{
					StartTime= duration.call(new Object[]{});
			
				}
			}
		};
	}
		
	@Override
	public _Action retour() {
		return new _Action() {
			
		/**
			 * 
			 */
			private static final long serialVersionUID = 3705430024653448191L;
		private long ReturnTime;

			@Override
			public void execute() {
				if (HotelsFound != null){
					_Service<Long> duration = (_Service<Long>) currentsrv.getService("Duration");
					if (duration == null){
						System.out.println("le serveur d'arivé ne permet pas de charger le timer");
					}
					else{
						ReturnTime= duration.call(new Object[]{});
				
					}
					System.out.println("On a trouvé des hotels");
					for(java.util.Map.Entry<String, String> entry : HotelsFound.entrySet()){
						System.out.println("Hotel : " + entry.getKey() + " ; Numéro : " + entry.getValue());
					}
					
					System.out.println("cela a pris : " + (ReturnTime-StartTime) + "secondes pour :" + HotelsFound.size() + "Hotels" );
				}
				
			}
		};
		
	}

}
