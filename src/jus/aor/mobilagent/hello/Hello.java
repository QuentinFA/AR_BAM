package jus.aor.mobilagent.hello;

import java.net.URI;
import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;

import jus.aor.mobilagent.kernel._Action;
import jus.aor.mobilagent.kernel.Agent;
import jus.aor.mobilagent.kernel.Starter;

/**
 * Classe de test élémentaire pour le bus à agents mobiles
 * @author  Morat
 */
public class Hello extends Agent{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 3882387847447156013L;
	/**
	  * construction d'un agent de type hello.
	  * @param args aucun argument n'est requis
	  */
	 public Hello(Object... args) {
		 // ....
	 }
	 /**
	 * l'action à entreprendre sur les serveurs visités  
	 */
	public _Action doIt = new _Action(){

		/**
		 * 
		 */
		private static final long serialVersionUID = -9129644307555501553L;

		@Override
		public void execute() {
			Starter.getLogger().log(Level.FINE, " Just DO IT (cause Hello is too mainstream)");
			System.out.println("JUST DO IT, DON'T LET YOUR DREAMS BE DREAMS");
		}
		// ...
	};
	/* (non-Javadoc)
	 * @see jus.aor.mobilagent.kernel.Agent#retour()
	 */
	@Override
	public _Action retour(){
		return new _Action() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void execute() {
				System.out.println("l'agent a dis bonjour a tout le monde ");
			}
		};
	}
	// ...
}
