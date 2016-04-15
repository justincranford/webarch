package org.justin.util;

import java.util.Collection;

public class StringUtil {
	private StringUtil() {
		// do nothing, hide constructor
	}

	public static String toString(final Collection<? extends Object> collection, final String seperator) {
		if ((collection == null) || (seperator == null)) {
			return null;
		} else if (collection.isEmpty()) {
			return "";
		}
		final StringBuilder sb = new StringBuilder(256);
		for (Object e : collection) {
			sb.append(e).append(seperator);	// ASSUMPTION: append(String) converts null to "null"
		}
		return sb.substring(0, sb.length()-seperator.length());
	}

	public static String toString(final Object[] array, final String seperator) {
		if ((array == null) || (seperator == null)) {
			return null;
		} else if (array.length == 0) {
			return "";
		}
		final StringBuilder sb = new StringBuilder(256);
		for (Object e : array) {
			sb.append(e).append(seperator);	// ASSUMPTION: append(String) converts null to "null"
		}
		return sb.substring(0, sb.length()-seperator.length());
	}

    public static String trimIgnoreNull(String s) {
    	return(null == s ? null : s.trim());
    }
}