package com.example.job;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String args[]) throws Exception {
		Scanner scanner = new Scanner(new InputStreamReader(System.in));
		System.out.println("Enter no. of jobs : ");
		int jobCount = Integer.parseInt(scanner.nextLine());
		List<Job> jobs = new ArrayList<>(jobCount);
		for(int i=1 ; i<= jobCount; i++) {
			System.out.println("Enter "+ i +" job name : ");
			String jobName = scanner.nextLine();
			if(jobName == null || jobName.isEmpty()) {
				System.out.println("Job Name should not be null or empty");
				System.exit(1);
			}
			System.out.println("Enter "+ i +" job dependency : ");
			String jobDependency = scanner.nextLine();
			if(jobDependency.isEmpty()){
				jobDependency=null;
			}
			jobs.add(new Job(jobName, jobDependency));
		}
		OrderJobs orderJobs = new OrderJobs(jobs);
		System.out.println(orderJobs.execute());
	}
}
