package fr.dauphine.ja.student.pandemiage.gameengine;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
// The blueprints Library is used here to manipulate and read GML files
//https://github.com/tinkerpop/blueprints/wiki/GraphML-Reader-and-Writer-Library
//Do not forget to add to the Java build path the library
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.TinkerGraph;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLReader;
 

public class GMLReader {
	// Usage : GMLReader.readGML( The file goes here) in main in GameEngine.java. 
	// Returns a list of all cities that we can exploit (
	public ArrayList<City> readGML(String XML_file) {
		
		//Class City to be created
		ArrayList<City> cityList = new ArrayList();
		
		//Declaration of City attributes
		String label;
		double eigencentrality;
		int r, g, b, degree;
		float x, y, size;
		
		Graph graph = new TinkerGraph();
	    GraphMLReader reader = new GraphMLReader(graph);
	 
	    InputStream is = new BufferedInputStream(new FileInputStream(XML_file));
	    reader.inputGraph(is);
	    
	    // In order to be able to browse through the GML
	    Iterable<Vertex> vertices = graph.getVertices();
	    Iterator<Vertex> verticesIterator = vertices.iterator();
	    
	    // Loops over Vertices than Edges, The given GML has only one edge but it doesn't matter
	    while (verticesIterator.hasNext()) {
	    	 
	        Vertex vertex = verticesIterator.next();
	        Iterable<Edge> edges = vertex.getInEdges();
	        Iterator<Edge> edgesIterator = edges.iterator();
	   
	        while (edgesIterator.hasNext()) {
	   
	          Edge edge = edgesIterator.next();
	          Vertex outVertex = edge.getOutVertex();
	          Vertex inVertex = edge.getInVertex();
	          
	          //Attributes assignement
	          label = (String) outVertex.getProperty("label");
	          eigencentrality = (double)outVertex.getProperty("eigencentrality");
	          degree = (int)outVertex.getProperty("degree");
	          size = (float)outVertex.getProperty("size");
	          r = (int)outVertex.getProperty("r");
	          g = (int)outVertex.getProperty("g");
	          b = (int)outVertex.getProperty("b");
	          x = (float)outVertex.getProperty("r");
	          y = (float)outVertex.getProperty("y");
	          
	          cityList<City>.add(new City(label, eigencentrality, degree, size, r, g, b, x, y));
	          
	        }
	          
		return cityList;
	
	    }
	    }

}
