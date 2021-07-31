package com.mrh0.arclang;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.mrh0.arclang.exception.ArcException;

public class Main {

	public static void main(String[] args) throws Exception {
		// File path to evaluate.
		String code = fromFile(args.length > 1 ? args[1] : "C:\\Development\\GitHub\\arclang\\test\\fib.arc");
		System.out.println(code);
		
		ArcLang.SystemsTest(code);
		//ArcLang.RoutesTest(code);
	}
	
	public static String fromFile(String path) {
		String r = "";
		try {
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			while(br.ready())
				r += br.readLine()+"\n";
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return r;
	}
}
