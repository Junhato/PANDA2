/**
 * Class representing a node of the graph
 */
public class Node
{
    private String name;
    private Double distance;

    /**
     * Node constructor
     * @param n Name that will be given to the node
     */
    Node(String n)
    {
        name = n;
	distance = 10000.0;
    }

    /**
     * Function that gets the name that has been assigned to the node
     * @return
     */
    String name()
    {
        return name;
    }

    Double distance()
    {
	return distance;
    }

    public void setdistance(double d) {
	
	this.distance = d;
    }
    
    public String toString() 
    {
    	return name;
    }
}
