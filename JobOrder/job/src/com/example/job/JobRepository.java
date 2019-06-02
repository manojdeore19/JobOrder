package com.example.job;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class JobRepository {
	private List<Job> jobs;
	public JobRepository(List<Job> jobs) {
		this.jobs = jobs;
	}
	public String getNames() {
		return String.join("",jobs.stream().map(j->j.getName()).collect(Collectors.toList()));
		
	}
	public boolean has(Job job) {
		if(job != null && jobs != null && jobs.size() >0) {
			return getJobByName(job.getName()) != null;
		} 
		return false;
	}
	public Job getJobByName(String name) {
		if(name != null) {
			for (Job job : jobs) {
				if(name.equals(job.getName())){
					return job;
				}
			}
		}
		return null;
	}
	public void add(Job job) {
		if(!has(job)) jobs.add(job);
	}
	public void addBefore(Job job, Job before) {
		if (!has(before)) {
			int index = jobs.indexOf(getJobByName(job.getName()));
			if(index > 0) {
				jobs.add(index, before);
			} else {
				jobs.add(before);
			}
		}
	}
	public JobRepository sortByDependency() throws Exception {
		JobRepository orderedJobs = new JobRepository(new LinkedList<Job>());
		for (Job job : jobs) {
			if(job.getName().equals(job.getDependency())) {
				throw new Exception("jobs can’t depend on themselves");
			}
			Job dependentJob = getJobByName(job.getDependency());
			if(orderedJobs.has(job) && orderedJobs.has(dependentJob)){
				throw new Exception("jobs can’t have circular dependencies");
			}
			orderedJobs.add(job);
			if(job.hasDependency()) {
				orderedJobs.addBefore(job, dependentJob);
			}
		}
		return orderedJobs;
	}
}
