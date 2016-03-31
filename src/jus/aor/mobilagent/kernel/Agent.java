package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.logging.Level;

public abstract class Agent implements _Agent {

	
	private Route road;
	private boolean inited = false;
	private transient String currentSrvName;
	private transient AgentServer currentsrv;
	

	/**
	 * 
	 */

	@Override
	public void run() {
		
		do{
			Etape to_do = road.next();
			to_do.getAction().execute();
			if(!this.road.hasNext()){
				break;
			}
			/* TODO Verifier si la prochaine étape deoit pas etre faite ICI. sert a rien de send...
			 * if(this.road.get().server.getHost()!= this.currentSrvName)
			 */
			try {
				Socket destSrv = new Socket(road.get().getServer().getHost(), road.get().getServer().getPort());
				ObjectOutputStream roadToDest = new ObjectOutputStream(destSrv.getOutputStream());
				Starter.getLogger().log(Level.FINE," envoi de l'agent " + this.toString() + " vers sa prochaine étape\n");
			
				//TODO envoi de l'agent (verifier la forme)
				roadToDest.writeObject(this);
				
				//TODO ce code est il atteint? (habitude de système ^^) sinon mettre un TimeOut a la creation suffisament élevé
				roadToDest.close();
				destSrv.close();
			
			} catch (NoSuchElementException | IOException e) {
				e.printStackTrace();
			}
			//TODO voir au dessus du try : si on arrive la et que c'est implémenté, faut pas oublier le break (on veut pas que le thread continue sans raison)
			
		} while (true);
		Starter.getLogger().log(Level.FINE, "L'agent "+this.toString()+" est de retour a son point de départ sans échec\n");
	}

	@Override
	public void addEtape(Etape etape) {
		// TODO Auto-generated method stub
		road.add(etape);
	}

	@Override
	public void init(AgentServer agentServer, String serverName) {
		if(this.inited ){
			;
			//this.reInit(agentServer, serverName);
		}
		else{
			this.inited = true;
			this.road.add(new Etape(road.retour.server,_Action.NIHIL));
		}
		
	}

	@Override
	public void reInit(AgentServer server, String serverName) {
		this.currentsrv = server;
		this.currentSrvName = serverName;
	}

}
