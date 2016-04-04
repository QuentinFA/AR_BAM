package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

public class AgentServer extends Thread {
	
	private HashMap<String,_Service> services;
	private int port;
	private String SrvName;
	private boolean isAlive;

	/*
	 * TODO : Le reste du code (il y a juste de la structure ici et quelque ligne)(non-Javadoc)
	 *  l'idée est d'avoir une base pour tout le monde (qui veut(/peut) fait ^^)
	 */
	public void addService(String name ,_Service<?> actions) throws Exception{
		if(this.services.get(name)==null){
			this.services.put(name, actions);
		}
		else throw new Exception("Le service existe déja");
	}
	
	public _Service<?> getService(String name){
		return this.services.get(name);
	}
	
	
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
				Socket entering = entry.accept();
			
			
			//TODO charger Agent SOS Comment on fait? (faut parser la reception, oui et?)
				ObjectInputStream Agententry = new ObjectInputStream(entering.getInputStream());
				Jar agentJar = (Jar) Agententry.readObject();
				BAMAgentClassLoader bma = new BAMAgentClassLoader(this.getClass().getClassLoader());
				bma.integrateCode(agentJar);
				AgentInputStream Agentreader = new AgentInputStream(Agententry, bma);
				_Agent newcommer = (_Agent) Agentreader.readObject();

			//TODO Executer Agent
			
				newcommer.reInit(this,SrvName);
				new Thread(newcommer).start();
			//TODO Fermer la socket
				Agententry.close();
				Agentreader.close();
				entering.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}while(isAlive);
		
			
	}

	public void setPort(int port) {
		this.port = port;
	}
}
