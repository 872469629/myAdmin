package cn.gleme.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.SecureRandom;

public class DesUtil {

	private final static String DES = "DES";
	private final static String KEY = "XJANYXJAN";

	public static void main(String[] args) throws Exception {
		String data = "1";
		String key = "XJANYXJAN";
		System.err.println(encrypt(data, key));
		System.err.println(decrypt(encrypt(data, key), key));
	}

	static final char intToBase64[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
			'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
			'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', '.', '_' };

	static final byte base64ToInt[] = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
			-1, -1, 62, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1,
			-1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
			15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1,
			26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42,
			43, 44, 45, 46, 47, 48, 49, 50, 51 };

	
	/**
	 * 加密
	 * @author LiXiang
	 * @date 2013-11-11 上午9:57:50
	 * @param a
	 * @return
	 * @throws
	 */
	private static String byteArrayToBase64(byte[] a) {
		int aLen = a.length; // 总长度
		int numFullGroups = aLen / 3; // 以3个byte组成以4个字符为一组的组数
		int numBytesInPartialGroup = aLen - 3 * numFullGroups; // 余数
		int resultLen = 4 * ((aLen + 2) / 3); // 输出长度总是4倍数，如果有余数，(aLen+2)/3保证将余数包含，并有空间放置填充符=
		StringBuffer result = new StringBuffer(resultLen);
		int inCursor = 0;
		for (int i = 0; i < numFullGroups; i++) {
			int byte0 = a[inCursor++] & 0xff;
			int byte1 = a[inCursor++] & 0xff;
			int byte2 = a[inCursor++] & 0xff;
			result.append(intToBase64[byte0 >> 2]);
			result.append(intToBase64[(byte0 << 4) & 0x3f | (byte1 >> 4)]);
			result.append(intToBase64[(byte1 << 2) & 0x3f | (byte2 >> 6)]);
			result.append(intToBase64[byte2 & 0x3f]);
		}
		// 处理余数
		if (numBytesInPartialGroup != 0) {
			int byte0 = a[inCursor++] & 0xff;
			result.append(intToBase64[byte0 >> 2]);
			// 余数为1
			if (numBytesInPartialGroup == 1) {
				result.append(intToBase64[(byte0 << 4) & 0x3f]);
				result.append("==");
			} else {
				// 余数为2
				int byte1 = a[inCursor++] & 0xff;
				result.append(intToBase64[(byte0 << 4) & 0x3f | (byte1 >> 4)]);
				result.append(intToBase64[(byte1 << 2) & 0x3f]);
				result.append('=');
			}
		}
		return result.toString();

	}
	
	
	
	/**
	 * 解码
	 * @author LiXiang
	 * @date 2013-11-11 上午9:56:06
	 * @param s
	 * @return
	 * @throws Exception
	 * @throws
	 */
	private static byte[] base64ToByteArray(String s) throws Exception {
		// 字符总长必须是4的倍数
		int sLen = s.length();
		int numGroups = sLen / 4;
		if (4 * numGroups != sLen)
			throw new IllegalArgumentException(
			"字串长度必须是4的倍数");
		// 余1个byte则算漏了两个byte，余2个byte则算漏掉了1个byte
		int missingBytesInLastGroup = 0;
		int numFullGroups = numGroups;
		if (sLen != 0) {
			// 余2个byte的情况
			if (s.charAt(sLen - 1) == '=') {
				missingBytesInLastGroup++;
				// 如果有余数发生，则完整3个byte组数少一个。
				numFullGroups--;
			}
			// 余1个byte的情况
			if (s.charAt(sLen - 2) == '=')
				missingBytesInLastGroup++;
		}
		// 总字节长度
		byte[] result = new byte[3 * numGroups - missingBytesInLastGroup];
		try {
			int inCursor = 0, outCursor = 0;
			for (int i = 0; i < numFullGroups; i++) {
				int ch0 = base64toInt(s.charAt(inCursor++), base64ToInt);
				int ch1 = base64toInt(s.charAt(inCursor++), base64ToInt);
				int ch2 = base64toInt(s.charAt(inCursor++), base64ToInt);
				int ch3 = base64toInt(s.charAt(inCursor++), base64ToInt);
				result[outCursor++] = (byte) ((ch0 << 2) | (ch1 >> 4));
				result[outCursor++] = (byte) ((ch1 << 4) | (ch2 >> 2));
				result[outCursor++] = (byte) ((ch2 << 6) | ch3);
			}
			if (missingBytesInLastGroup != 0) {
				int ch0 = base64toInt(s.charAt(inCursor++), base64ToInt);
				int ch1 = base64toInt(s.charAt(inCursor++), base64ToInt);
				// 不管余1还是余2个byte，肯定要解码一个byte。
				result[outCursor++] = (byte) ((ch0 << 2) | (ch1 >> 4));
				// 如果余2个，即差一个才构成3byte，那么还要解码第二个byte。
				if (missingBytesInLastGroup == 1) {
					int ch2 = base64toInt(s.charAt(inCursor++), base64ToInt);
					result[outCursor++] = (byte) ((ch1 << 4) | (ch2 >> 2));
				}
			}
		} catch (Exception e) {
			throw e;
		}
		return result;
	}

	private static int base64toInt(char c, byte[] alphaToInt) throws Exception {
		int result = alphaToInt[c];
		if (result < 0)
			throw new Exception("非法索引值");
		return result;
	}
	
	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data, String key) throws Exception {
		byte[] bt = encrypt(data.getBytes(), key.getBytes());
		String strs = byteArrayToBase64(bt);
		return strs;
	}
	public static String encrypt(String data) throws Exception {
		byte[] bt = encrypt(data.getBytes(), KEY.getBytes());
		String strs = byteArrayToBase64(bt);
		return strs;
	}

	/**
	 * Description 根据键值进行解密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String decrypt(String data, String key) throws IOException,
            Exception {
		if (data == null)
			return null;
		byte[] buf = base64ToByteArray(data);
		byte[] bt = decrypt(buf, key.getBytes());
		return new String(bt);
	}
	public static String decrypt(String data) throws IOException,
            Exception {
		if (data == null)
			return null;
		byte[] buf = base64ToByteArray(data);
		byte[] bt = decrypt(buf, KEY.getBytes());
		return new String(bt);
	}

	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}

	/**
	 * Description 根据键值进行解密
	 * 
	 * @param data
	 * @param key
	 * 加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		// 生成一个可信任的随机数源
		SecureRandom sr = new SecureRandom();

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(DES);

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);

		return cipher.doFinal(data);
	}
}
