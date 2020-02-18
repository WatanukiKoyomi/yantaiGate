package com.huadong.hdgate.test;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

/**
 * 可以用此类生成存入数据库的密码
 */
public class PwdMD5Test {

	@Test
	public void pwdMD5Test(){
		String username = "admin";
		String pwd = "admin";
		ByteSource pwdSalt = ByteSource.Util.bytes(username);
		Object obj = new SimpleHash("MD5", pwd, pwdSalt, 1024);
		System.out.println(obj.toString());
	}

}
