package com.sss.bridge.bean;

public class NormalDistribution {

	private Integer samples;
	
	private Double mean;
	
	private Double variance;

	public Integer getSamples() {
		return samples;
	}

	public void setSamples(Integer samples) {
		this.samples = samples;
	}

	public Double getMean() {
		return mean;
	}

	public void setMean(Double mean) {
		this.mean = mean;
	}

	public Double getVariance() {
		return variance;
	}

	public void setVariance(Double variance) {
		this.variance = variance;
	}

	@Override
	public String toString() {
		//return "NormalDistribution[samples=" + samples + ",mean=" + mean + ",variance=" + variance + "]";
		return samples + " " + mean + " " + variance;
	}

	public NormalDistribution(Integer samples) {
		super();
		this.samples = samples;
	}

	public NormalDistribution() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
