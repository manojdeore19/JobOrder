package com.example.job.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.example.job.Job;
import com.example.job.OrderJobs;

public class OrderJobsTest {

	@Test
	public void testWithoutJob() throws Exception {
		List<Job> jobs = new ArrayList<>();
		
		OrderJobs orderJobs = new OrderJobs(jobs);
		Assert.assertEquals(0, orderJobs.execute().length());
		Assert.assertEquals("", orderJobs.execute());
	}
	
	@Test
	public void testSingleJob() throws Exception {
		List<Job> jobs = new ArrayList<>();
		jobs.add(new Job("a",null));
		
		OrderJobs orderJobs = new OrderJobs(jobs);
		Assert.assertEquals(1, orderJobs.execute().length());
		Assert.assertTrue(orderJobs.execute().contains("a"));
		Assert.assertEquals("a", orderJobs.execute());
	}

	@Test
	public void testMultipleJob() throws Exception {
		List<Job> jobs = new ArrayList<>();
		jobs.add(new Job("a",null));
		jobs.add(new Job("b",null));
		jobs.add(new Job("c",null));
		
		OrderJobs orderJobs = new OrderJobs(jobs);
		Assert.assertEquals(3, orderJobs.execute().length());
		Assert.assertTrue(orderJobs.execute().contains("a"));
		Assert.assertTrue(orderJobs.execute().contains("b"));
		Assert.assertTrue(orderJobs.execute().contains("c"));
		Assert.assertEquals("abc", orderJobs.execute());
	}
	
	@Test
	public void testMultipleJobWithDependeny() throws Exception {
		List<Job> jobs = new ArrayList<>();
		jobs.add(new Job("a",null));
		jobs.add(new Job("b","c"));
		jobs.add(new Job("c",null));
		
		OrderJobs orderJobs = new OrderJobs(jobs);
		Assert.assertEquals(3, orderJobs.execute().length());
		Assert.assertTrue(orderJobs.execute().contains("a"));
		Assert.assertTrue(orderJobs.execute().contains("b"));
		Assert.assertTrue(orderJobs.execute().contains("c"));
		Assert.assertTrue(orderJobs.execute().indexOf("c") < orderJobs.execute().indexOf("b"));
		Assert.assertEquals("acb", orderJobs.execute());
	}
	
	@Test
	public void testMultipleJobWithMultipleDependeny() throws Exception {
		List<Job> jobs = new ArrayList<>();
		jobs.add(new Job("a",null));
		jobs.add(new Job("b","c"));
		jobs.add(new Job("c","f"));
		jobs.add(new Job("d","a"));
		jobs.add(new Job("e","b"));
		jobs.add(new Job("f",null));
		
		OrderJobs orderJobs = new OrderJobs(jobs);
		Assert.assertEquals(6, orderJobs.execute().length());
		Assert.assertTrue(orderJobs.execute().contains("a"));
		Assert.assertTrue(orderJobs.execute().contains("b"));
		Assert.assertTrue(orderJobs.execute().contains("c"));
		Assert.assertTrue(orderJobs.execute().contains("d"));
		Assert.assertTrue(orderJobs.execute().contains("e"));
		Assert.assertTrue(orderJobs.execute().contains("f"));
		Assert.assertTrue(orderJobs.execute().indexOf("c") < orderJobs.execute().indexOf("b"));
		Assert.assertTrue(orderJobs.execute().indexOf("f") < orderJobs.execute().indexOf("c"));
		Assert.assertTrue(orderJobs.execute().indexOf("a") < orderJobs.execute().indexOf("d"));
		Assert.assertTrue(orderJobs.execute().indexOf("b") < orderJobs.execute().indexOf("e"));
		Assert.assertEquals("afcbde", orderJobs.execute());
	}
	
	@Test
	public void testJobWithSelfDependeny() {
		List<Job> jobs = new ArrayList<>();
		jobs.add(new Job("a",null));
		jobs.add(new Job("b",null));
		jobs.add(new Job("c","c"));

		Exception e = null;
		try {
			OrderJobs orderJobs = new OrderJobs(jobs);
			orderJobs.execute();
		} catch (Exception ex) {
			e = ex;
		}
		Assert.assertTrue(e instanceof Exception);
		Assert.assertEquals("jobs can’t depend on themselves", e.getMessage());
	}

	@Test
	public void testJobWithCircularDependeny() {
		List<Job> jobs = new ArrayList<>();
		jobs.add(new Job("a",null));
		jobs.add(new Job("b","c"));
		jobs.add(new Job("c","f"));
		jobs.add(new Job("d","a"));
		jobs.add(new Job("e",null));
		jobs.add(new Job("f","b"));
		
		Exception e = null;
		try {
			OrderJobs orderJobs = new OrderJobs(jobs);
			orderJobs.execute();
		} catch (Exception ex) {
			e = ex;
		}
		Assert.assertTrue(e instanceof Exception);
		Assert.assertEquals("jobs can’t have circular dependencies", e.getMessage());
	}
}
