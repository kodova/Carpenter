package com.kodova.carpenter.fixture;

import java.util.Collection;
import java.util.Random;

public class Randoms {

	public static Random GENERATOR = new Random();

	public static int getInt(){
		return GENERATOR.nextInt();
	}

	public static int getInt(int max){
		return GENERATOR.nextInt(max + 1);
	}

	public static int getInt(int min, int max){
		return GENERATOR.nextInt(max - min) + min;
	}

	public static <T extends Enum> T pick(Class<T> enumType){
		return pick(enumType.getEnumConstants());
	}

	public static <T> T pick(Collection<T> items){
		return (T) pick(items.toArray());
	}

	public static <T> T pick(T... items){
		int index = getInt(0, items.length - 1);
		return items[index];
	}
}
