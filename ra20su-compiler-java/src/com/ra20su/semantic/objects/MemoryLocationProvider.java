package com.ra20su.semantic.objects;

public class MemoryLocationProvider {

	private static int MEM_LOCATION = 5000;

	public static int getMemLoaction() {
		return MEM_LOCATION++;
	}

	public static void reset() {
		MEM_LOCATION = 5000;
	}
}
