/**
 * J<i>ava</i> U<i>tilities</i> for S<i>tudents</i>
 */
package jus.aor.mobilagent.kernel;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import jus.aor.mobilagent.kernel._Agent;

/**
 * Le serveur principal permettant le lancement d'un serveur d'agents mobiles et les fonctions permettant de déployer des services et des agents.
 * @author     Morat
 */
public final class Server implements _Server {
	/** le nom logique du serveur */
	protected String name;
	/** le port où sera ataché le service du bus à agents mobiles. Pafr défaut on prendra le port 10140 */
	protected int port=10140;
	/** le server d'agent démarré sur ce noeud */
	protected AgentServer agentServer;
	/** le nom du logger */
	protected String loggerName;
	/** le logger de ce serveur */
	protected Logger logger=null;
	
	private BAMServerClassLoader loaderSrv;
	/**
	 * Démarre un serveur de type mobilagent 
	 * @param port le port d'écuote du serveur d'agent 
	 * @param name le nom du serveur
	 */
	public Server(final int port, final String name){
		this.name=name;
		try {
			this.port=port;
			/* mise en place du logger pour tracer l'application */
			loggerName = "jus/aor/mobilagent/"+InetAddress.getLocalHost().getHostName()+"/"+this.name;
			logger=Logger.getLogger(loggerName);
			/* démarrage du server d'agents mobiles attaché à cette machine */
			loaderSrv = new BAMServerClassLoader(new URL[]{}, this.getClass().getClassLoader());
			agentServer = new AgentServer();
			agentServer.setPort(port);
			agentServer.setName(name);
			new Thread(agentServer).start();
			/* temporisation de mise en place du server d'agents */
			Thread.sleep(1000);
		}catch(Exception ex){
			logger.log(Level.FINE," erreur durant le lancement du serveur"+this,ex);
			return;
		}
	}
	/**
	 * Ajoute le service caractérisé par les arguments
	 * @param name nom du service
	 * @param classeName classe du service
	 * @param codeBase codebase du service
	 * @param args arguments de construction du service
	 */
	public final void addService(String name, String classeName, String codeBase, Object... args) {
		try {
			loaderSrv.addUrl(new URL(codeBase));
			Class<?> serviceClass= Class.forName(classeName,true,loaderSrv);
			_Service<?> servicetoadd = (_Service<?>) serviceClass.getConstructor(Object[].class).newInstance(new Object[]{args});
			agentServer.addService(name, servicetoadd);
		}catch(Exception ex){
			ex.printStackTrace();
			logger.log(Level.FINE," erreur durant le lancement du serveur"+this,ex);
			return;
		}
	}
	/**
	 * deploie l'agent caractérisé par les arguments sur le serveur
	 * @param classeName classe du service
	 * @param args arguments de construction de l'agent
	 * @param codeBase codebase du service
	 * @param etapeAddress la liste des adresse des étapes
	 * @param etapeAction la liste des actions des étapes
	 */
	public final void deployAgent(String classeName, Object[] args, String codeBase, List<String> etapeAddress, List<String> etapeAction) {
		try {	

			Starter.getLogger().log(Level.FINE, "debut d'un agent "+ classeName);
			BAMAgentClassLoader agentLoader = new BAMAgentClassLoader(codeBase,loaderSrv.getParent());

			Class<?> agentclass = Class.forName(classeName, true, agentLoader);
			_Agent tostart = (_Agent) agentclass.getConstructor(Object[].class).newInstance(new Object[]{args});
			tostart.addEtape(new Etape(new URI("mobilagent://localhost:"+port), tostart.retour())); 

			//sert d'action "vide" d'initialisation et de d�part a l'agent
			tostart.addEtape(new Etape(null,tostart.Start()));

			//assert(etapeAction.size()==etapeAddress.size());
			for(int i =0; i< etapeAddress.size();i++){
				//on ajoute toutes les �tape
				//THX JavaDoc et google
				System.out.println(etapeAddress.get(i));
				tostart.addEtape(new Etape(new URI(etapeAddress.get(i)), (_Action) tostart.getClass().getDeclaredField(etapeAction.get(i)).get(tostart)));
			}

			this.startAgent(tostart, agentLoader);
		}catch(Exception ex){
			ex.printStackTrace();
			logger.log(Level.SEVERE," erreur durant le lancement du serveur"+this,ex);
			return;
		}
	}
	/**
	 * Primitive permettant de "mover" un agent sur ce serveur en vue de son exécution
	 * immédiate.
	 * @param agent l'agent devant être exécuté
	 * @param loader le loader à utiliser pour charger les classes.
	 * @throws Exception
	 */
	protected void startAgent(_Agent agent, BAMAgentClassLoader loader) throws Exception {

		((Agent)agent).setJar(loader.extractCode());

		agent.init(this.agentServer, this.name);
		new Thread(agent).start();
		//on peut faire �a car on met une action Nihil en d�but de route
	}
}
