package jus.aor.mobilagent.kernel;

import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil.ToStringAdapter;

public class BAMAgentClassLoader extends ClassLoader{

	private Jar jarResources;
	private ClassLoader loader;
	
	public BAMAgentClassLoader(String str, ClassLoader cl) {
		this.loader = cl;
	}
	
	public BAMAgentClassLoader(ClassLoader cl) {
		this.loader = cl;
	}

	static void integrateCode(Jar jar){
		//TODO
	}
	
	
	static Jar extractCode(){
		return jarResources;
		//TODO
	}
	
	@Override
	public String toString() {
		return "BAMAgentClassLoader [jarResources=" + jarResources + ", loader=" + loader + "]";
	}
	
}
