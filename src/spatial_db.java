import java.sql.*;
import oracle.spatial.geometry.*;
import oracle.sql.STRUCT;

public class spatial_db {
	private static final String driverUrl = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String username = "jiaqigu";
	private static final String password = "920607";

	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(driverUrl);
			connection = DriverManager.getConnection(url, username, password);
			//connection.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	// 测试Oracle连接是否成功
   public static void main(String args[]) throws SQLException
   {
	   Connection con = spatial_db.getConnection();
	   System.out.println(con);
	   Statement stmt = con.createStatement();
	   ResultSet res = stmt.executeQuery("select Pond_id, shape from pond");
	   //region reg[] = new region[16];
	   pond p[] = new pond[8];
	   int j = 0;
	   while(res.next())
	   {
		   String id = res.getString(1);
		   STRUCT st = (oracle.sql.STRUCT)res.getObject(2);
		   JGeometry j_geom = JGeometry.load(st);
		  // System.out.println(j_geom.isMultiPoint());
		  // System.out.println(j_geom.getType());
		   double points[]=j_geom.getOrdinatesArray();
		   /*
		   System.out.println(points.length);
		   for(int i = 0; i < points.length; i++)
		   {
			   System.out.print(points[i]+"\t");
			   if((i+1)%2 == 0)
		   	   System.out.print("\n");
		   }  
		   */
		   p[j] = new pond(id, points);
		   j++;
	   }
	   for(j = 0; j < 8; j++)
	   {
		   My_Point shape[] = p[j].getPondShape();
		   System.out.print(p[j].getPondID()+"\t");
		   for(int i = 0; i < 3; i++)
		   {
			   System.out.print("("+shape[i].getX()+","+shape[i].getY()+")\t");
		   }
		   System.out.print("\n");
	   }
	   
   }

}
