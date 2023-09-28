////////////////////////////////////////////////////////////////////////////////
// Main File:        Greedy.java
// Other Files:      Makefile
// Semester:         CS 577 Spring 2023
// Instructor:       Mark
//
// Author:           Matej Popovski
// Email:            popovski@wisc.edu
////////////////////////////////////////////////////////////////////////////////

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Filename:   Greedy.java
 * Project:    Assignment 3 
 * Author:     Matej Popovski
 *
 * The interval scheduling problem can be solved using a greedy algorithm, which involves 
 * finding the best possible schedule from a list of schedules and identifying the maximum 
 * number of intervals that can be scheduled.
 */
public class Greedy {

    private PriorityQueue<Job> jobs;

    /**
     *  A Constructor for Greedy object
     * 
     * @param numJobs the number of jobs the priority queue should hold
     */
    public Greedy(int numJobs) {
        jobs = new PriorityQueue<Job>(numJobs, new endTimeComparator());
    }

    public static void main(String[] args) {
        Greedy[] instances = parse_input();
        for (Greedy g : instances) {
            Set<Job> schedule = g.createSchedule();
            System.out.println(schedule.size());
        }
    }

    /**
     * A Representation of a job to be processed with a start time and end time 
     */
    private class Job {
        private int startTime;
        private int endTime;

        private Job(int start, int end) {
            this.startTime = start;
            this.endTime = end;
        }
    }

    /**
     * A Custom comparator to sort jobs. This one sorts by increasing end time. 
     */
    private class endTimeComparator implements Comparator<Job> {

        @Override
        public int compare(Greedy.Job j1, Greedy.Job j2) {
            if (j1.endTime > j2.endTime) {
                return 1;
            } else if (j1.endTime < j2.endTime) {
                return -1;
            }
            return 0;
        }
    }

    /**
     * A Custom comparator to sort jobs. This one sorts by increasing start time. 
     */
    private class startTimeComparator implements Comparator<Job> {

        @Override
        public int compare(Greedy.Job j1, Greedy.Job j2) {
            if (j1.startTime > j2.startTime) {
                return 1;
            } else if (j1.startTime < j2.startTime) {
                return -1;
            }
            return 0;
        }
    }

/**
     * A Greedy algorithm that grabs jobs based on the earliest finish time heuristic 
     * 
     * @return a set of jobs
     */
    private Set<Job> createSchedule() {

        
        SortedSet<Job> schedule = new TreeSet<Job>(new startTimeComparator());

        
        while (!this.jobs.isEmpty()) {
            Job earliest = jobs.poll(); 
            if (!checkOverlap(schedule, earliest)) {
                schedule.add(earliest);
            }
        }
        return schedule;
    }


    /**
     * A Parse stdin and create instances with jobs
     */
    private static Greedy[] parse_input() {
        Scanner input = new Scanner(System.in);

        int numInstances = input.nextInt(); 
        Greedy instances[] = new Greedy[numInstances];

        
        for (int numInstance = 0; numInstance < numInstances; numInstance++) {
            int numJobs = input.nextInt(); 
            instances[numInstance] = new Greedy(numJobs); 
            input.nextLine();
            
            
            while (numJobs > 0) {
                int start = input.nextInt();
                int end = input.nextInt();
                
                instances[numInstance].addJob(start, end);
                numJobs--;
            }
        }
        input.close();
        return instances;
    }

    /**
     * Adding a job to the list of jobs
     * 
     * @param startTime starting time of job
     * @param endTime ending time of job
     */
    private void addJob(int startTime, int endTime) {
        Job j = new Job(startTime, endTime);
        jobs.add(j);
    }

    /**
     * Checking if there exists a job in the schedule that conflicts with a potential new job
     * 
     * @param schedule the set of jobs to examine
     * @param j job to check against
     * @return true if there is a conflict
     */
    private Boolean checkOverlap(Set<Job> schedule, Job j) {
        for (Job k : schedule) {
            if (k.startTime < j.endTime && k.endTime > j.startTime) {
                return true;
            }
        }
        return false;
    }
}