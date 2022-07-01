package com.darkerbox.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class StrUtils {
	// 去重操作
	public static List removeDuplicationByStream(List<String> list) {
		List<String> newList = list.stream().distinct().collect(Collectors.toList());
		return newList;
	}

	public static List<String> removeDuplicationByStream(String[] arr) {
		// 字符串数组转list
		List<String> strsToList1= Arrays.asList(arr);

		return removeDuplicationByStream(strsToList1);
	}

	public static String connectStringOfOne(List<String> list,String spilt){
		return list.stream().map(Objects::toString).collect(Collectors.joining(spilt));
	}


}
