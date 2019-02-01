package fr.dauphine.ja.student.pandemiage.gameengine;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLReader;

import fr.dauphine.ja.pandemiage.common.Disease;
 

public class GMLReader {
	
	
	
	
	// Usage : GMLReader.readGML( The file goes here) in main in GameEngine.java. 
	// Returns a list of all cities that we can exploit (
	public static ArrayList<City> readGML(String XML_file) throws IOException {
		
		//Class City to be created
		ArrayList<City> cityList = new ArrayList<City>();
		
		//Declaration of City attributes
		String label;
		double eigencentrality;
		int r, g, b, degree, id;
		float x, y, size;
		
		Graph graph = new TinkerGraph();
	    GraphMLReader reader = new GraphMLReader(graph);
	 
	    InputStream is = new BufferedInputStream(new FileInputStream(XML_file));
	    reader.inputGraph(is);
	    
	    // In order to be able to browse through the GML
	    Iterable<Vertex> vertices = graph.getVertices();
	    Iterator<Vertex> verticesIterator = vertices.iterator();
	    
	    // Loops over Vertices than Edges, the Edges represent a connexion between two cities(source->target)
	    // When over a vertex it scans the data keys and affects them to a new City
	    //When looping over a vertex edges we can populate the vertex's neighbours list
	    while (verticesIterator.hasNext()) {
	    	 
	        Vertex vertex = verticesIterator.next();
	        id = Integer.parseInt((String)vertex.getId());
	        label = (String) vertex.getProperty("label");
	          eigencentrality = (double)vertex.getProperty("Eigenvector Centrality");
	          degree = (int)vertex.getProperty("Degree");
	          size = (float)vertex.getProperty("size");
	          r = (int)vertex.getProperty("r");
	          g = (int)vertex.getProperty("g");
	          b = (int)vertex.getProperty("b");
	          x = (float)vertex.getProperty("x");
	          y = (float)vertex.getProperty("y");
	          
	          Iterable<Edge> edges = vertex.getEdges(Direction.BOTH);
	          Iterator<Edge> edgesIterator = edges.iterator();
	          List<City> neighboursList = new ArrayList<City>();
	         
	          while (edgesIterator.hasNext()) {
	        	  Edge c= edgesIterator.next();
	        	  City a = vertexToCity(c.getVertex(Direction.OUT));
	        	  City a1 = vertexToCity(c.getVertex(Direction.IN));

	        	  if(a.getName()!=vertexToCity(vertex).getName()) {
	        		  neighboursList.add(a);
	        		 
	        	  }
	        	  if(a1.getName()!=vertexToCity(vertex).getName()) {
	        		  neighboursList.add(a1);
	        		
	        	  }
	        		  
	        	  
	          	}
	          City paris = new City(id ,label, r, g, b, 0.0, x, y, size, eigencentrality	," ", " ", degree , neighboursList);
	          cityList.add(paris);
	       
	      
	    }
	   
          
	    return cityList;    
	}
	
	public static City vertexToCity(Vertex vertex) {
		int id = Integer.parseInt((String)vertex.getId());
		String label = (String) vertex.getProperty("label");
        double eigencentrality = (double)vertex.getProperty("Eigenvector Centrality");
        int degree = (int)vertex.getProperty("Degree");
        float size = (float)vertex.getProperty("size");
        int r = (int)vertex.getProperty("r");
        int g = (int)vertex.getProperty("g");
        int b = (int)vertex.getProperty("b");
        float x = (float)vertex.getProperty("x");
        float y = (float)vertex.getProperty("y");
        List<City> neighboursList = new ArrayList<City>();
        City oran = new City(id, label, r, g, b, 0.0, x, y, size, eigencentrality	," ", " ", degree , neighboursList);
        
        
        return oran;
	}
	
	

	

}
