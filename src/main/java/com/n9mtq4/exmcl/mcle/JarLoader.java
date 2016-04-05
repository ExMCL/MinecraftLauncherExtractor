package com.n9mtq4.exmcl.mcle;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * A class that handles adding jars to the classpath.
 * 
 * <p>Created by will on 3/31/15.</p>
 */
class JarLoader {
	
	private static final Class[] parameters = new Class[]{URL.class};
	
	/**
	 * Add File f to this classpath.
	 *
	 * @param f the file
	 * @throws IOException if something didn't work
	 */
	static void addFile(File f) throws IOException {
		addURL(f.toURI().toURL());
	}
	
	/**
	 * Helper method for addFile.
	 *
	 * @param u the url
	 * @throws IOException if something didn't work
	 */
	private static void addURL(URL u) throws IOException {
		URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Class sysclass = URLClassLoader.class;
		try {
			Method method = sysclass.getDeclaredMethod("addURL", parameters);
			method.setAccessible(true);
			method.invoke(sysloader, new Object[]{u});
		}catch (Throwable t) {
			t.printStackTrace();
			throw new IOException("Error, could not add URL to system classloader");
		}
	}
	
}
