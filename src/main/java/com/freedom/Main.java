package com.freedom;

public class Main {

	public static void main(String []aa) {

		String xml = "";
		Parser parser = new Parser(xml);
		System.out.println(parser.getField("haha"));
	}
}