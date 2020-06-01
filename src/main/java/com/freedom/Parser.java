package com.freedom;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Parser {
	private String xml;

	public Parser(String xml) {
		super();
		this.xml = xml.toUpperCase();
		validateAndRemoveComments();
	}

	public String getXml() {
		return xml;
	}

	private void validateAndRemoveComments() {
		if (this.xml.contains("/*")) {
			String s = xml.substring(0, this.xml.indexOf("/*"));
			String e = xml.substring(xml.indexOf("*/") + 2, xml.length());
			xml = s + e;
		}
	}

	public int getNumberOfFields(String tag) {
		tag = tag.toUpperCase();
		int count = 0;
		String startingTag = "<" + tag + ">";
		String endTag = "</" + tag + ">";
		Pattern pattern = Pattern
				.compile(startingTag, Pattern.CASE_INSENSITIVE);
		Pattern endPattern = Pattern.compile(endTag, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(xml);
		Matcher endMatcher = endPattern.matcher(xml);
		matcher = pattern.matcher(xml);
		while (matcher.find())
			if (endMatcher.find())
				count++;
		return count;
	}

	public int getNumberOfFields(String tag, int start, int end) {
		String tempXML = xml;
		xml = xml.substring(start, end);
		int count = this.getNumberOfFields(tag);
		xml = tempXML;
		return count;
	}

	public String getField(String toFind) {
		toFind = toFind.toUpperCase();
		String startingTag = "<" + toFind + ">";
		String endTag = "</" + toFind + ">";
		Pattern pattern = Pattern
				.compile(startingTag, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(xml);
		if (matcher.find()) {
			int startIndex = matcher.start();
			pattern = Pattern.compile(endTag, Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(xml);
			if (matcher.find()) {
				int endIndex = matcher.start();
				return xml
						.substring(startIndex + toFind.length() + 2, endIndex);
			}
		}
		return "";
	}

	public String getField(String toFind, int start) {
		String tempXML = xml;
		xml = xml.substring(start);
		String field = this.getField(toFind);
		xml = tempXML;
		return field;
		/*
		 * toFind = toFind.toUpperCase(); String startingTag = "<" + toFind +
		 * ">"; String endTag = "</" + toFind + ">"; Pattern pattern =
		 * Pattern.compile(startingTag, Pattern.CASE_INSENSITIVE); Matcher
		 * matcher = pattern.matcher(xml); if (matcher.find(start)) { int
		 * startIndex = matcher.start(); if (startIndex > -1) { pattern =
		 * Pattern.compile(endTag, Pattern.CASE_INSENSITIVE); matcher =
		 * pattern.matcher(xml); if (matcher.find(startIndex)) { int endIndex =
		 * matcher.start(); return xml.substring(startIndex + toFind.length() +
		 * 2, endIndex); } } }
		 */
	}

	public String getField(String toFind, int start, int end) {
		String tempXML = xml;
		xml = xml.substring(start, end);
		String field = this.getField(toFind);
		xml = tempXML;
		return field;
	}

	public String getAllFields(String toFind, int start, int end) {
		String allFields = "";
		String tempXML = xml;
		try {
			xml = xml.substring(start, end);
			String startingTag = "<" + toFind + ">";
			String endTag = "</" + toFind + ">";
			Pattern pattern = Pattern.compile(startingTag,
					Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(xml);
			Pattern endPattern = Pattern.compile(endTag,
					Pattern.CASE_INSENSITIVE);
			Matcher endMatcher = endPattern.matcher(xml);
			while (matcher.find()) {
				int startIndex = matcher.start();
				if (endMatcher.find()) {
					int endIndex = endMatcher.start();
					allFields += xml.substring(
							startIndex + toFind.length() + 2, endIndex) + ",";
				}
			}
			if (allFields.endsWith(","))
				allFields = allFields.substring(0, allFields.length() - 1);
		} catch (Exception e) {
			xml = tempXML;
			return "";
		}
		return allFields;
	}

}