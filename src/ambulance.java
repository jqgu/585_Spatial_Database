
public class ambulance {
	private String ambulance_id = null;
	private My_Point cover[];
	public ambulance(String id, double points[])
	{
		cover = new My_Point[3];
		ambulance_id = id;
		for(int i = 0; i < 3; i++)
		{
			cover[i] = new My_Point();
			cover[i].setLocation(points[i*2], points[i*2+1]);
		}		
	}
	public String getAmbulanceID() 
	{
		return ambulance_id;
	}
	public My_Point[] getAmbulanceCover()
	{
		return cover;
	}
	public void print_ambulance()
	{
		System.out.print("Ambulance ID: "+ambulance_id+"\tcover: ");
		for(int i = 0; i < 3; i++)
		{
			System.out.print("\t("+cover[i].getX()+","+cover[i].getY()+")\t");
		}
		System.out.print("\n");
	}
}
