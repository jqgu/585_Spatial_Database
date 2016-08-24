import java.awt.Polygon;


public class region  {
	private String region_id = null;
	private My_Point shape[];
	int axis_x[];
	int axis_y[];
	private Polygon poly = null;
	public region(String id, double points[])
	{
		shape = new My_Point[5];
		region_id = id;
		axis_x = new int[5];
		axis_y = new int[5];
		for(int i = 0; i < 5; i++)
		{
			shape[i] = new My_Point();
			shape[i].setLocation(points[i*2], points[i*2+1]);
			axis_x[i] =(int) points[i*2];		
			axis_y[i] = (int)points[i*2+1];
		}	
		poly = new Polygon(axis_x, axis_y, 5);
	}
	public String getRegionID() 
	{
		return region_id;
	}
	public My_Point[] getRegionShape()
	{
		return shape;
	}
	public Polygon getRegionPolygon()
	{
		return poly;
	}
	public void print_region()
	{
		System.out.print("Region ID: "+region_id+"\tshape: ");
		for(int i = 0; i < 5; i++)
		{
			System.out.print("\t("+shape[i].getX()+","+shape[i].getY()+")\t");
		}
		System.out.print("\n");
	}
}
