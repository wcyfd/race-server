package com.randioo.race_server.util;

import java.util.Set;

public class Sets {
	public static <T> void add(Set<T> set, T... arr) {
		for (T t : arr)
			set.add(t);
	}
}
