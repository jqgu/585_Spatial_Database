import java.awt.Button;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BoxLayout;

public class sql_dialog {
	private Frame f = new Frame("Sql Query");
	private TextArea input_area = new TextArea(10,20);
	private TextArea output_area = new TextArea(6,20);
	private Button run_sql = new Button("Run");
	private String input_string = null;
	private String output_string = null;
	
	private Connection con = null;
	private Statement stmt = null;
	
	public void init() throws SQLException
	{
		run_sql.addActionListener(new listener());
		output_area.setEditable(false);
		f.setLayout(new BoxLayout(f, BoxLayout.Y_AXIS));
		f.addWindowListener(new window_listener());
		f.add(input_area);
		f.add(run_sql);
		f.add(output_area);
		f.pack();
		f.setSize(500, 400);
		f.setVisible(true);
		con = spatial_db.getConnection();
		test("连接成功：" + con);
		stmt = con.createStatement();
	}
	
	public void test(String str)
	{
		output_area.setText(str);
	}
	
 class window_listener implements WindowListener
	{
		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			System.exit(0);
		}
		@Override
		public void windowOpened(WindowEvent e) {}
		@Override
		public void windowClosed(WindowEvent e) {}
		@Override
		public void windowIconified(WindowEvent e) {}
		@Override
		public void windowDeiconified(WindowEvent e) {}
		@Override
		public void windowActivated(WindowEvent e) {}
		@Override
		public void windowDeactivated(WindowEvent e) {}
	}
 
	class listener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			input_string = input_area.getText();
			//System.out.println(input_string);
			boolean hasResult = false;
			try {
				hasResult = stmt.execute(input_string);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(hasResult)
			{
				output_string = "Result:\n";
				ResultSet rs = null;
				try {
					rs = stmt.getResultSet();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ResultSetMetaData rsmd = null;;
				try {
					rsmd = rs.getMetaData();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				int columnCounter = 0;
				try {
					columnCounter = rsmd.getColumnCount();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					while(rs.next())
					{
						for(int i = 0; i < columnCounter; i++)
							output_string = output_string+rs.getString(i+1)+"\t";		
						output_string += "\n";
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				output_area.setText(output_string);
			}
		}
	}

	public static void main(String args[]) throws SQLException
	{
		new sql_dialog().init();
	}
}
