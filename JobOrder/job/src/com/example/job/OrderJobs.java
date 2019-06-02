package com.example.job;

import java.util.List;

public class OrderJobs {
	List<Job> jobs;
	public OrderJobs(List<Job> jobs) {
		this.jobs = jobs;
	}
	public String execute() throws Exception {
		JobRepository jobRepository = new JobRepository(jobs);
		return jobRepository.sortByDependency().getNames();
	 }
}
