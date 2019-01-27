package fr.dauphine.ja.student.pandemiage.ai;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.dauphine.ja.student.pandemiage.gameengine.City;
import fr.dauphine.ja.student.pandemiage.gameengine.Player;


public class AiMethods extends Player {
	
	public AiMethods(City depart, List<City> listc) {
		super(depart, listc);
		// TODO Auto-generated constructor stub
	}
	List<City> citylist;
    Set<City> visitedCities;
    Set<City> unknownCities;
    Map<City, City> predecessors;
    Map<City, Integer> distance;

	 public void dijkstra(City startingPoint) {
		 	visitedCities = new HashSet<City>();
		 	unknownCities = new HashSet<City>();
	        distance = new HashMap<City, Integer>();
	        predecessors = new HashMap<City, City>();
	        distance.put(startingPoint, 0);
	        unknownCities.add(startingPoint);
	        while (unknownCities.size() != 0) {
	            City node = getMinimum(unknownCities);
	            visitedCities.add(node);
	            unknownCities.remove(node);
	            findMinimalDistances(node);
	        }
	    }

	    private void findMinimalDistances(City node) {
	        List<City> neighborCities = this.getCurrentCity().getNeighbours();
	        for (City destination : neighborCities) {
	            if (getShortestDistance(destination) > getShortestDistance(node)
	                    + 1) {
	                distance.put(destination, getShortestDistance(node)
	                        + 1);
	                predecessors.put(destination, node);
	                unknownCities.add(destination);
	            }
	        }

	    }
	   

	    private City getMinimum(Set<City> Cityes) {
	        City minimum = null;
	        for (City City : Cityes) {
	            if (minimum == null) {
	                minimum = City;
	            } else {
	                if (getShortestDistance(City) < getShortestDistance(minimum)) {
	                    minimum = City;
	                }
	            }
	        }
	        return minimum;
	    }

	    private boolean isSettled(City City) {
	        return visitedCities.contains(City);
	    }

	    private int getShortestDistance(City destination) {
	        Integer d = distance.get(destination);
	        if (d == null) {
	            return Integer.MAX_VALUE;
	        } else {
	            return d;
	        }
	    }

	    /*
	     * This method returns the path from the starting Point to the selected destination and
	     * NULL if no path exists
	     */
	    public LinkedList<City> getPath(City destination) {
	        LinkedList<City> path = new LinkedList<City>();
	        City step = destination;
	        // check if a path exists
	        if (predecessors.get(step) == null) {
	            return null;
	        }
	        path.add(step);
	        while (predecessors.get(step) != null) {
	            step = predecessors.get(step);
	            path.add(step);
	        }
	        // Put it into the correct order
	        Collections.reverse(path);
	        return path;
	    }

	
	// All the edges have a weight of 1, unnecessary.
	public int shortestPathCost(City destination) {
		
		int cost = 0;
		City startingPoint = this.getCurrentCity();
		City destinationPoint = destination;
		
		
		
		
		return cost;
	}
	
	
}
