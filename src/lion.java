
public class lion {
	private String lion_id = null;
	private My_Point position = null;
	public lion(String id, double points[])
	{
		lion_id = id;
		position = new My_Point();
		position.setLocation(points[0], points[1]);
	}
	public String getLionID() 
	{
		return lion_id;
	}
	public My_Point getLionPosition()
	{
		return position;
	}
	public void print_lion()
	{
		System.out.println("Lion ID: "+lion_id+"\tposition: ("+position.getX()+", "+position.getY()+")");
	}
}
