import java.awt.Polygon;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import oracle.spatial.geometry.JGeometry;
import oracle.sql.STRUCT;

public class Collect_info {
	private Connection con;
	private List<lion> lion_list;
	private List<pond> pond_list;
	private List<ambulance> ambulance_list;
	private List<region> region_list;

	public void connect()
	{
		con = new spatial_db().getConnection();
		System.out.println(con);
	}
	public void getAllElement() throws SQLException
	{
		lion_list = new ArrayList<lion>();
		pond_list = new ArrayList<pond>();
		ambulance_list = new ArrayList<ambulance>();
		region_list = new ArrayList<region>();
		getLion();
		getPond();
		getAmbulance();
		getRegion();
	}
	
	private void getLion() throws SQLException
	{
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select lion_id, position from lion");
		String ID =null;
		double points[] = null;
		while(rs.next())
		{
			ID = rs.getString(1);
			STRUCT st = (oracle.sql.STRUCT)rs.getObject(2);
			JGeometry j_geom = JGeometry.load(st);
			points = j_geom.getPoint();
			lion l = new lion(ID, points);
			lion_list.add(l);
		}
	}
	private void getPond() throws SQLException
	{
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select pond_id, shape from pond");
		String ID =null;
		double points[] = null;
		while(rs.next())
		{
			ID = rs.getString(1);
			STRUCT st = (oracle.sql.STRUCT)rs.getObject(2);
			JGeometry j_geom = JGeometry.load(st);
			points = j_geom.getOrdinatesArray();
			pond p = new pond(ID, points);
			pond_list.add(p);
		}
	}
	private void getAmbulance() throws SQLException
	{
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select ambulance_id, cover from ambulance");
		String ID =null;
		double points[] = null;
		while(rs.next())
		{
			ID = rs.getString(1);
			STRUCT st = (oracle.sql.STRUCT)rs.getObject(2);
			JGeometry j_geom = JGeometry.load(st);
			points = j_geom.getOrdinatesArray();
			ambulance a = new ambulance(ID, points);
			ambulance_list.add(a);
		}
	}
	private void getRegion() throws SQLException
	{
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select region_id, shape from region");
		String ID =null;
		double points[] = null;
		while(rs.next())
		{
			ID = rs.getString(1);
			STRUCT st = (oracle.sql.STRUCT)rs.getObject(2);
			JGeometry j_geom = JGeometry.load(st);
			points = j_geom.getOrdinatesArray();
			region r = new region(ID, points);
			region_list.add(r);
		}
	}
	
	public void show_lion()
	{
		int i;
		for(i = 0; i < lion_list.size(); i++)
		{
			lion l = lion_list.get(i);
			l.print_lion();
		}
	}
	public void show_pond()
	{
		int i;
		for(i = 0; i < pond_list.size(); i++)
		{
			pond p = pond_list.get(i);
			p.print_pond();
		}
	}
	public void show_ambulance()
	{
		int i;
		for(i = 0; i < ambulance_list.size(); i++)
		{
			ambulance a = ambulance_list.get(i);
			a.print_ambulance();
		}
	}
	public void show_region()
	{
		int i;
		for(i = 0; i < region_list.size(); i++)
		{
			region r = region_list.get(i);
			r.print_region();
		}
	}
	
	public List<Polygon> provide_region_info()
	{
		List<Polygon> region_polygon_info = new ArrayList<Polygon>();
		for(int i = 0; i < region_list.size(); i++)
			region_polygon_info.add(region_list.get(i).getRegionPolygon());
		return region_polygon_info;
	}
	public List<My_Point>provide_pond_upper_left_point()
	{
		List<My_Point> point_info = new ArrayList<My_Point>();
		for(int i = 0; i < pond_list.size(); i++)
			point_info.add(pond_list.get(i).get_pond_upper_left_point());
		return point_info;
	}
	public List<My_Point> provide_lion_position()
	{
		List<My_Point> lion_position = new ArrayList<My_Point>();
		for(int i = 0; i < lion_list.size(); i++)
			lion_position.add(lion_list.get(i).getLionPosition());
		return lion_position;
	}
	
	public static void main(String args[]) throws SQLException
	{
		Collect_info d = new Collect_info();
		d.connect();
		d.getAllElement();
		d.show_lion();
		d.show_pond();
		d.show_ambulance();
		d.show_region();
	}
}
