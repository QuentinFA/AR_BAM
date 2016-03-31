package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AgentServer extends Thread {
	
	//TODO Initialiser le port
	private int port;
	private String SrvName;
	private boolean isAlive;

	/*
	 * TODO : Le reste du code (il y a juste de la structure ici et quelque ligne)(non-Javadoc)
	 *  l'idée est d'avoir une base pour tout le monde (qui veut(/peut) fait ^^)
	 */
	
	public void run(){
		
		ServerSocket entry = null;
		try {
			entry = new ServerSocket(this.port);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		isAlive = true;
		do{
			try {
				_Agent incoming = null;
				Socket entering = entry.accept();
			
			
			//TODO charger Agent SOS Comment on fait? (faut parser la reception, oui et?)
			
			//TODO Executer Agent
			
				incoming.reInit(this, this.getName());
				incoming.run();
			
			//TODO Fermer la socket
			
				entering.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}while(isAlive);
		
			
	}
}
