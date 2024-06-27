package com.blog.common.util;

import java.util.EnumSet;

public class EnumUtil {

	/**
	 * 获取枚举
	 * @param value
	 * @param enumClass
	 * @return E
	 **/
	public static <E extends Enum<E>> E getEnum(String value, Class<E> enumClass) {
		if (value == null || enumClass == null || !enumClass.isEnum()) {
			throw new IllegalArgumentException("Invalid input parameters");
		}

		EnumSet<E> enumSet = EnumSet.allOf(enumClass);
		for (E enumValue : enumSet) {
			if (enumValue.name().equalsIgnoreCase(value.trim())) {
				return enumValue;
			}
		}

		throw new IllegalArgumentException("No enum constant with name " + value + " found in " + enumClass.getName());
	}

}
