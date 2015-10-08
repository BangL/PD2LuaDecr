package de.bangl.don_heidi.pd2luadecr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

/**
 *
 * @author Don_Heidi
 * @author BangL
 */
public class PD2LuaDecr {

	final static String _version = "1.0";
	//final static char[] _key = ">S4?fo%k4_5u2(3_=cRj".toCharArray(); // OLD KEY
	final static char[] _key = "asljfowk4_5u2333crj".toCharArray();

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {

		// parse cmd args
		String path = null;
		char[] key = _key;
		boolean verbose = false;
		int i;
		for (i = 0; i < args.length; ++i) {
			if (args[i].startsWith("-")) {
				if (args[i].contains("v")) {
					verbose = true;
				}
				if (args[i].contains("k")) {
					key = args[++i].toCharArray();
				}
			} else {
				path = args[i];
			}
		}

		if (path != null) {
			processFile(path, verbose, _key);
		} else {
			System.out.println("PD2LuaDecr v" + _version);
			System.out.println(" Usage: PD2LuaDecr.jar [options] <source file>");
			System.out.println("");
			System.out.println(" Option:   Description:");
			System.out.println("  -v        verbose");
			System.out.println("  -k <key>  use custom key");
			System.out.println("");
			System.out.println(" Example: PD2LuaDecr.jar -vk asljfowk4_5u2333crj C:\\example.lua");
		}
	}

	private static void processFile(String path, boolean verbose, char[] key) {
		try {
			if (path != null && path.endsWith(".lua")) {
				File file = new File(path + "c");
				byte[] cache = Helpers.decryptLuaFile(path, key);
				if (cache != null) {
					try (FileOutputStream fos = new FileOutputStream(file)) {
						fos.write(cache);
					}
					if (verbose) {
						System.out.println("Successfully decrypted \"" + file.getName() + "\".");
					}
				} else {
					System.out.println("ERROR: Decryption of \"" + path + "\" failed.");
				}
			}
		} catch (NoSuchFileException ex) {
			System.out.println("ERROR: File not found.");
		} catch (IOException ex) {
			System.out.println("ERROR: " + ex.getLocalizedMessage());
		}
	}
}
