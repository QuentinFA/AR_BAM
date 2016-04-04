package jus.aor.mobilagent.kernel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class BAMAgentClassLoader extends ClassLoader{

	private HashMap<String,byte[]> lib = new HashMap<String,byte[]>();
	private Jar baselib;
	public BAMAgentClassLoader(ClassLoader cl) {
		super(cl);
	}
	
	
	public BAMAgentClassLoader(String codeBase, ClassLoader cl) {
		super(cl);
		try {
				Jar temp = new Jar(codeBase);
				integrateCode(temp);
				baselib = temp;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}


	/**
	 * transforme le Jar en une liste de classes dans le loader
	 * on ajoute a la lib, il est donc peut etre conseillé de vider la lib sinon le Jar et la lib peuvent etre différentes
	 * @param jar
	 */
	public void integrateCode(Jar jar){
		
		for( Entry<String, byte[]> rsc: jar.classIterator()){
			IntegrateCode(rsc.getKey(),rsc.getValue());
		}
	}
	/**
	 * ajoute la classe Key definie par value dans le loader
	 * @param key
	 * @param value
	 */
	private void IntegrateCode(String key, byte[] value) {
		if (!this.lib.containsKey(key)){
			this.lib.put(key, value);
		}
	}
	
	public Jar extractCode(){
		File Tempf;
		try {
			Tempf = File.createTempFile("temp", ".jar");
			JarOutputStream temp = new JarOutputStream(new FileOutputStream(Tempf));
			for (Entry<String,byte[]> entry : lib.entrySet()){
				temp.putNextEntry(new JarEntry(entry.getKey()));
				temp.write(entry.getValue());
			}
			temp.close();
			baselib = new Jar(Tempf.getAbsolutePath());
			while(!Tempf.delete());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return baselib;
		
	}
	

	@Override
	public String toString() {
		return "BAMAgentClassLoader [jarResources=" + baselib + ", loader=" + this.getParent() + "]";
	}
	
}
