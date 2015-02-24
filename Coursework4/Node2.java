/**
 * Class representing a node of the graph
 */
public class Node
{
    private String name;
    private int distance = 0;
	private Edge lastedge;
    /**
     * Node constructor
     * @param n Name that will be given to the node
     */
    Node(String n)
    {
        name = n;
    }

    /**
     * Function that gets the name that has been assigned to the node
     * @return
     */
    String name()
    {
        return name;
    }
    
    public String toString() 
    {
    	return name;
    }

	public int distance(){
		return distance;
	}
	public Edge lastedge(){
		return lastedge;
	}
	public void change(int d, Edge e){
		this.distance = d;
		this.lastedge = e;
	}
}
