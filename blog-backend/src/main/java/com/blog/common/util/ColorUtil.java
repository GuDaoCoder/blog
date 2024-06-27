package com.blog.common.util;

import java.security.SecureRandom;

public class ColorUtil {

	public static String generateHexColor() {
		SecureRandom random = new SecureRandom();
		int rgb = random.nextInt(0xFFFFFF + 1);
		return String.format("#%06x", rgb);
	}

}
