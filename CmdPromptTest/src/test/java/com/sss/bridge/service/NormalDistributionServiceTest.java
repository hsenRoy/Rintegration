package com.sss.bridge.service;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import com.sss.bridge.bean.NormalDistribution;
import com.sss.bridge.exception.NormalDistributionException;

public class NormalDistributionServiceTest {

	public static void main(String[] args) throws FileNotFoundException, NormalDistributionException {
		NormalDistribution nd = new NormalDistribution();
		nd.setSamples(10);
		nd.setMean(5.2D);
		nd.setVariance(1.2D);
		NormalDistributionService service = new NormalDistributionService(args[0]);
		String graphInBytes = service.plotScatter(nd);
		System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("output.txt")), true));
		System.out.println(graphInBytes);
	}

}
