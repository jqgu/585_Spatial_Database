import java.awt.geom.Point2D;


public class My_Point extends Point2D{
	private double x;
	private double y;
	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return y;
	}

	@Override
	public void setLocation(double x, double y) {
		// TODO Auto-generated method stub
		this.x = x;
		this.y = y;
	}
	
	public static void main(String args[])
	{
		My_Point p = new My_Point();
		p.setLocation(0.0, 1.1);
		System.out.println(p.getX()+"  "+p.getY());
	}

}
