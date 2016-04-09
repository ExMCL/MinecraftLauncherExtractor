package com.n9mtq4.exmcl.mcle;

import java.io.File;
import java.io.IOException;

import static com.n9mtq4.exmcl.mcle.Unpacker.unpack;

/**
 * Created by will on 4/5/16 at 2:37 PM.
 *
 * @author Will "n9Mtq4" Bresnahan
 */
public class Main {
	
	/**
	 * 0 - Minecraft.jar location
	 * 1 - launcher.pack.lmza location
	 * 2 - desired launcher.jar location
	 * */
	public static void main(String[] args) throws IOException {
		
		if (args.length != 3) {
			System.out.println("Invalid Params");
			return;
		}
		
		JarLoader.addFile(new File(args[0]));
		System.out.println("Added " + args[0]);
		
		unpack(new File(args[1]), new File(args[2]));
		
	}
	
}
