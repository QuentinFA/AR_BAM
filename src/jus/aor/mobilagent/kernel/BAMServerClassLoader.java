package jus.aor.mobilagent.kernel;

import java.net.URL;
import java.net.URLClassLoader;

public class BAMServerClassLoader extends URLClassLoader{

	public BAMServerClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
	}
	
	public void addUrl(URL url){
		this.addURL(url);
	}
	

}
