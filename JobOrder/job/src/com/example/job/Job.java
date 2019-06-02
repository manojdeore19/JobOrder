package com.example.job;

public class Job {
	private String name;
	private String dependency;
	public Job(String name, String dependency) {
	    this.name = name;
	    this.dependency = dependency;
	}
	public boolean hasDependency() {
		return dependency != null;
	}
	public String getName() {
		return name;
	}
	public String getDependency() {
		return dependency;
	}
}
