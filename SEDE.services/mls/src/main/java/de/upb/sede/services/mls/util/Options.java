package de.upb.sede.services.mls.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Options {
	public static String[] splitStringIntoArr(List optionList) {
		List<String> splittedOptions = new ArrayList<>();
		for(Object opt : optionList) {
			String optStr = opt.toString();
			splittedOptions.addAll(Arrays.asList(optStr.split("\\s")));
		}
		String[] optArr = splittedOptions.toArray(new String[0]);
		return optArr;
	}
}
