package com.sss.bridge.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.concurrent.Callable;

public class ProcessResultReader implements Callable<String> {

	private InputStream is;
    private StringWriter sw;
    
	public ProcessResultReader(InputStream is, String processName) {
		super();
		this.is = is;
		this.sw = new StringWriter();
		Thread currentThread = Thread.currentThread();
		currentThread.setName(processName);
	}

	@Override
	public String call() throws Exception {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		BufferedWriter bw = new BufferedWriter(sw);
		String line = "";
		while((line = br.readLine()) != null) {
			bw.write(line);
			bw.newLine();
		}
		bw.flush();
		return sw.toString();
	}
	
    

}
