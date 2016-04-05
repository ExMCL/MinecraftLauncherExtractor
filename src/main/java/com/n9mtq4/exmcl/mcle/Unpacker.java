package com.n9mtq4.exmcl.mcle;

import LZMA.LzmaInputStream;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Pack200;

/**
 * Created by will on 4/5/16 at 3:09 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
class Unpacker {
	
	static void unpack(File packedLauncherJar, File launcherJar) {
		File lzmaUnpacked = getUnpackedLzmaFile(packedLauncherJar);
		InputStream inputHandle = null;
		OutputStream outputHandle = null;
		
		System.out.println("Reversing LZMA on " + packedLauncherJar + " to " + lzmaUnpacked.getAbsolutePath());
		try {
			System.out.println(lzmaUnpacked.getAbsolutePath());
			inputHandle = new LzmaInputStream(new FileInputStream(packedLauncherJar));
			outputHandle = new FileOutputStream(lzmaUnpacked);
			
			byte[] buffer = new byte[65536];
			
			int read = inputHandle.read(buffer);
			while (read >= 1) {
				outputHandle.write(buffer, 0, read);
				read = inputHandle.read(buffer);
			}
		} catch (Exception e) {
			throw new RuntimeException("Unable to un-lzma: " + e);
		} finally {
			closeSilently(inputHandle);
			closeSilently(outputHandle);
		}
		
		System.out.println("Unpacking " + lzmaUnpacked + " to " + launcherJar.getAbsolutePath());
		
		JarOutputStream jarOutputStream = null;
		try {
			jarOutputStream = new JarOutputStream(new FileOutputStream(launcherJar));
			Pack200.newUnpacker().unpack(lzmaUnpacked, jarOutputStream);
		} catch (Exception e) {
			throw new RuntimeException("Unable to un-pack200: " + e.toString());
		} finally {
			closeSilently(jarOutputStream);
		}
		
		System.out.println("Cleaning up " + lzmaUnpacked.getAbsolutePath());
		
		lzmaUnpacked.delete();
	}
	
	private static File getUnpackedLzmaFile(File packedLauncherJar) {
		String filePath = packedLauncherJar.getAbsolutePath();
		if (filePath.endsWith(".lzma")) {
			filePath = filePath.substring(0, filePath.length() - 5);
		}
		return new File(filePath);
	}
	
	private static void closeSilently(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			}catch (IOException ignored) {
			}
		}
	}
	
}
