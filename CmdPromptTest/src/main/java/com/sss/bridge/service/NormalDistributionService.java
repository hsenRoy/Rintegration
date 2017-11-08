package com.sss.bridge.service;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.commons.codec.binary.Base64;

import com.sss.bridge.bean.NormalDistribution;
import com.sss.bridge.dao.ProcessResultReader;
import com.sss.bridge.exception.NormalDistributionException;

public class NormalDistributionService {
	
	//load from properties file
	private static final String IMAGE_HEADER = "data:image/jpeg;base64,";
	
	private String rScriptPath;
	
	private String command;

	private FutureTask<String> ouputTask;

	private FutureTask<String> errorTask;
	
	private int nThreads;
	
	public NormalDistributionService(String rScriptPath) {
		this.rScriptPath = rScriptPath;
		this.command = "cmd /c Rscript %s %s";//read from properties file
		this.nThreads = 2; // one thread for output stream and one for error stream
	}
	
	public String plotScatter(NormalDistribution nd) throws NormalDistributionException {
		try {
			command = String.format(command, rScriptPath, nd);
			Runtime runtime = Runtime.getRuntime();
			System.out.println(command);
			Process process = runtime.exec(command);
			Callable<String> output = new ProcessResultReader(process.getInputStream(), "STDOUT");
			ouputTask = new FutureTask<>(output);
			Callable<String> error = new ProcessResultReader(process.getErrorStream(), "STDERR");
			errorTask = new FutureTask<>(error);
			ExecutorService threadPool = Executors.newFixedThreadPool(nThreads);
			threadPool.execute(ouputTask);
			threadPool.execute(errorTask);
			int exitValue = process.waitFor();
			threadPool.shutdown();
			if(exitValue == 0) {
				String outputResult = ouputTask.get();
				String base64 = outputResult.split("\"")[1];
				if(Base64.isBase64(base64)) {
					String graphInBytes = IMAGE_HEADER + base64;
					return graphInBytes;
				} else {
					//load message from properties file
					String message = rScriptPath + " didn't produce base64 string as output";
					throw new NormalDistributionException(message);
				}
			} else {
				String errorResult = errorTask.get();
				throw new NormalDistributionException(errorResult);
			}
		} catch (IOException | InterruptedException | ExecutionException e) {
			throw new NormalDistributionException(e.getMessage());
		}
	}
	
}
