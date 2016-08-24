import java.awt.Canvas;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static java.awt.BorderLayout.*;

public class Draw {
	private Frame f = new Frame("Map");
	private Mycanvas draw_shape = new Mycanvas();
	private List<Polygon> region_polygon = new ArrayList<Polygon>();
	private List<My_Point> pond_upper_left_point = new ArrayList<My_Point>();
	private List<My_Point> lion_position = new ArrayList<My_Point>();
	private List<My_Point> show_selected_pond = new ArrayList<My_Point>();
	private List<My_Point> show_selected_lion = new ArrayList<My_Point>();
	private Checkbox show_selected_element = new Checkbox("show lions and ponds in the selected region", false);
	private int flag = -1;
	public void init() throws SQLException
	{
		//Panel p = new Panel();
		Collect_info d = new Collect_info();
		d.connect();
		d.getAllElement();
		d.show_region();
		
		region_polygon = d.provide_region_info();
		pond_upper_left_point = d.provide_pond_upper_left_point();
		lion_position = d.provide_lion_position();
		
		draw_shape.addMouseListener(new MouseListener());
		draw_shape.setPreferredSize(new Dimension(500, 500));
		show_selected_element.addItemListener(new CheckBox_Listener());
		
		f.addWindowListener(new window_listener());
		f.add(draw_shape);
		f.add(show_selected_element,SOUTH );
		f.setSize(530, 570);
		f.setVisible(true);
		draw_shape.repaint();
	}
	class CheckBox_Listener implements ItemListener
	{
		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			Checkbox tmp = (Checkbox) e.getSource();
			if(tmp.getState() == true)
			{
				flag = 1;
			}
			else
				flag = -1;
			show_selected_pond.clear();
			show_selected_lion.clear();
			draw_shape.repaint();
		}	
	}
	class MouseListener implements java.awt.event.MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent e) {
			Point mouse_point = e.getPoint();
			System.out.println(""+mouse_point.getX()+" \t"+mouse_point.getY());
			int i = 0;
			for(i = 0; i < region_polygon.size(); i ++)
			{
				if(region_polygon.get(i).contains(mouse_point) == true)
				{
					System.out.println("You press region: "+i);
					break;
				}
			}
			if(flag == -1)
				return;
			Polygon p = region_polygon.get(i);
			show_selected_pond.clear();
			show_selected_lion.clear();
			draw_shape.repaint();	
			for(i = 0; i < pond_upper_left_point.size(); i++)
			{
				if(p.contains(pond_upper_left_point.get(i).getX(), pond_upper_left_point.get(i).getY(), 30, 30) == true)
				{
					show_selected_pond.add(pond_upper_left_point.get(i));		
					//System.out.println("pond "+i);
				}		
			}
			for(i = 0; i < lion_position.size(); i++)
			{
				if(p.contains(lion_position.get(i).getX()-2, lion_position.get(i).getY()-2, 4, 4) == true)
				{
					show_selected_lion.add(lion_position.get(i));		
				}		
			}
			draw_shape.repaint();
		}
		@Override
		public void mousePressed(MouseEvent e) {}
		@Override
		public void mouseReleased(MouseEvent e) {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseExited(MouseEvent e) {}
		
	}
	public static void main(String args[]) throws SQLException
	{
		new Draw().init();
	}
	class Mycanvas extends Canvas
	{
		public void paint(Graphics g)
		{
			g.setColor(Color.black);
			for(int i = 0; i < region_polygon.size(); i++ )
			{
				g.drawPolygon(region_polygon.get(i));
				//g.drawString("R:"+i, region_polygon.get(i).xpoints[0]+10,region_polygon.get(i).ypoints[0]+20 );
			}
				
			for(int i = 0; i < pond_upper_left_point.size(); i++ )
			{
				//g.setColor(new Color(240,255,255));
				g.setColor(Color.blue);
				g.fillOval((int)pond_upper_left_point.get(i).getX(), (int)pond_upper_left_point.get(i).getY(), 30, 30);
				g.setColor(Color.black);
				g.drawOval((int)pond_upper_left_point.get(i).getX(), (int)pond_upper_left_point.get(i).getY(), 30, 30);
				//g.drawString("P:"+i, (int)pond_upper_left_point.get(i).getX(),(int)pond_upper_left_point.get(i).getY());
				//System.out.println("Pond "+i+": ("+pond_upper_left_point.get(i).getX()+", "+pond_upper_left_point.get(i).getY());
			}
			for(int i = 0; i < lion_position.size(); i++ )
			{
				//g.setColor(new Color(240,255,255));
				g.setColor(Color.green);
				g.fillOval((int)lion_position.get(i).getX()-2, (int)lion_position.get(i).getY()-2, 4, 4);	
			}
			if(show_selected_pond.size() > 0)
			{
				for(int i = 0; i < show_selected_pond.size(); i++)
				{
					g.setColor(Color.red);
					g.fillOval((int)show_selected_pond.get(i).getX(), (int)show_selected_pond.get(i).getY(), 30, 30);
					//System.out.println("("+show_selected_pond.get(i).getX()+", "+show_selected_pond.get(i).getY());
				}
			}
			if(show_selected_lion.size() > 0)
			{
				for(int i = 0; i < show_selected_lion.size(); i++)
				{
					g.setColor(Color.red);
					g.fillOval((int)show_selected_lion.get(i).getX()-2, (int)show_selected_lion.get(i).getY()-2, 4, 4);
				}
			}
		}
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
}
