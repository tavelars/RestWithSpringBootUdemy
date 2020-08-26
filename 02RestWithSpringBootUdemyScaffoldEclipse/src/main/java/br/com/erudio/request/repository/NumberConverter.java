package br.com.erudio.request.repository;

import java.util.Objects;

public class NumberConverter {

	public static Double convertToDouble(String strNumber) {
		if (Objects.isNull(strNumber)) {
			return 0D;
		}
		if (isNumeric(strNumber)) {
			return Double.parseDouble(strNumber);
		}
		return 0D;
	}

	public static boolean isNumeric(String strNumber) {
		if (Objects.isNull(strNumber)) {
			return false;
		}
		String number = strNumber.replaceAll(",", ".");
		return number.matches("^[0-9]");
	}

}
