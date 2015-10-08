package de.bangl.don_heidi.pd2luadecr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Don_Heidi
 * @author BangL
 */
public class Helpers {

	/**
	 * see help
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] getByteArrayFromFile(String file) throws IOException {
		Path path = Paths.get(file);
		return Files.readAllBytes(path);
	}

	/**
	 * <p>
	 * Decrypts an array of type byte</p>
	 *
	 * @param file path to the file to decrypt
	 * @param key lua decrypt key
	 * @return encrypted data
	 * @throws IOException
	 */
	public static byte[] decryptLuaFile(String file, char[] key) throws IOException {
		byte[] data = Helpers.getByteArrayFromFile(file);
		int keyIndex;
		for (int i = 0; i < data.length; ++i) {
			keyIndex = ((data.length + i) * 7) % key.length;
			data[i] ^= key[keyIndex] * (data.length - i);
		}
		if (isValidLuac(data)) {
			return data;
		} else {
			return null;
		}
	}

	/**
	 *
	 * @param data
	 * @return
	 */
	private static boolean isValidLuac(byte[] data) {
		final byte[] pattern = "LuaQ".getBytes();
		final int offset = 1;

		if (data.length >= pattern.length + offset) {
			int i;
			for (i = 0; i < pattern.length; ++i) {
				if ((pattern[i]) != data[i + offset]) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
}
