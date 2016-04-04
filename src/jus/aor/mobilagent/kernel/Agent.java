package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.logging.Level;

public abstract class Agent implements _Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1603445867875802091L;
	private transient Jar myJar;
	private Route road;
	private boolean inited = false;
	private transient String currentSrvName;
	private transient AgentServer currentsrv;
	

	/**
	 * 
	 */

	@Override
	public void run() {
		
		Etape to_do = road.next();
		to_do.getAction().execute();
		Starter.getLogger().log(Level.FINE, "fin de l'éxécution d'une action par " + this.toString() + " sur le serveur " + currentSrvName + "a l'adresse : "+ currentsrv);
		if(this.road.hasNext()){
			try {
				Socket destSrv = new Socket(road.get().getServer().getHost(), road.get().getServer().getPort());
				ObjectOutputStream roadToDest = new ObjectOutputStream(destSrv.getOutputStream());
				Starter.getLogger().log(Level.FINE," envoi de l'agent " + this.toString() + " vers sa prochaine étape\n");
			
				roadToDest.writeObject(myJar);
				roadToDest.writeObject(this);
				
				roadToDest.close();
				destSrv.close();
			
			} catch (NoSuchElementException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void addEtape(Etape etape) {
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
	
	public void setJar(Jar jar){
		this.myJar = jar;
	}

}
