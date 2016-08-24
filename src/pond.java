import java.util.List;


public class pond {
	private String pond_id = null;
	private My_Point shape[];
	private My_Point Upper_left_Point = new My_Point();
	public pond(String id, double points[])
	{
		shape = new My_Point[3];
		pond_id = id;
		for(int i = 0; i < 3; i++)
		{
			shape[i] = new My_Point();
			shape[i].setLocation(points[i*2], points[i*2+1]);
		}
		if(shape[0].getY() == shape[1].getY())
		{
			Upper_left_Point.setLocation((int)((shape[0].getX()+shape[1].getX())/2)-15, (int)shape[0].getY()-15);
		}
		else
			if(shape[1].getY() == shape[2].getY())
			{
				Upper_left_Point.setLocation((int)((shape[2].getX()+shape[1].getX())/2)-15, (int)shape[1].getY()-15);
			}
			else
				if(shape[0].getY() == shape[2].getY())
				{
					Upper_left_Point.setLocation((int)((shape[0].getX()+shape[2].getX())/2)-15, (int)shape[0].getY()-15);
				}
		System.out.println(Upper_left_Point.getX()+" \t"+Upper_left_Point.getY());
	}
	public String getPondID() 
	{
		return pond_id;
	}
	public My_Point[] getPondShape()
	{
		return shape;
	}
	public void print_pond()
	{
		System.out.print("Pond ID: "+pond_id+"\tshape: ");
		for(int i = 0; i < 3; i++)
		{
			System.out.print("\t("+shape[i].getX()+","+shape[i].getY()+")\t");
		}
		System.out.print("\n");
	}
	public My_Point get_pond_upper_left_point()
	{
		return Upper_left_Point;
	}
}
