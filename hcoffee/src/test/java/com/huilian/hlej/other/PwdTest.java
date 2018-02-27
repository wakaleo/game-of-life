package com.huilian.hlej.other;

import org.junit.Test;

import com.huilian.hlej.jet.common.security.Digests;
import com.huilian.hlej.jet.common.utils.Encodes;

public class PwdTest {

	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	
	@Test
	public void test(){
		String plainPassword = "123456";
		String plain = Encodes.unescapeHtml(plainPassword);
	    byte[] salt = Digests.generateSalt(SALT_SIZE);
	    byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
	    System.out.println(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword)); 
	}
	
}
