package jus.aor.mobilagent.kernel;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;


/**
 * ObjectInputStream spécifique au bus à agents mobiles. Il permet d'utiliser le loader de l'agent.
 * @author   Morat
 */
class AgentInputStream extends ObjectInputStream{
	/**
	 * le classLoader à utiliser
	 */
	BAMAgentClassLoader loader;

	AgentInputStream(InputStream is, BAMAgentClassLoader cl) throws IOException{
		super(is);
		loader = cl;
	}
	
	public _Agent getAgent(){
		Jar basis;
		try {
			basis = (Jar) this.readObject();
			loader.integrateCode(basis);
			_Agent agent = (_Agent) this.readObject();
			return agent;
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	protected Class<?> resolveClass(ObjectStreamClass cl) throws IOException,ClassNotFoundException{
		return loader.loadClass(cl.getName());
	}
}
