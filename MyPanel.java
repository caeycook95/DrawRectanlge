package ilstu.edu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Custom JPanel. Mouse Listeners for JPanel are here.
 * Allow overrided paintComponent methods to draw rectangles.
 *
 * @author Casey Cook
 *
 */
public class MyPanel extends JPanel 
{
	Rectangle rec;
	Rectangle recDraw;
	Rectangle unionRec;
	Rectangle commonRec;
	ArrayList<Rectangle> myRecs = new ArrayList<Rectangle>();
	ArrayList<Rectangle> intersectRecs = new ArrayList<Rectangle>();
	boolean drawIntersections = false;
	boolean drawUnion = false;
	boolean drawCommon = false;
	boolean allowClear = false;
	
	public void changeDrawIntersections()
	{
		if(drawIntersections)
		{
			drawIntersections = false;
		}
		else
		{
			drawIntersections = true;
		}
	}
	public void changeDrawUnion()
	{
		if(drawUnion)
		{
			drawUnion = false;
		}
		else
		{
			drawUnion = true;
		}
	}
	
	public void changeDrawCommon()
	{
		if(drawCommon)
		{
			drawCommon = false;
		}
		else
		{
			drawCommon = true;
		}
	}
	
	
	JPanel myPanel = new JPanel();
	public MyPanel()
	{
		
		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				int x = e.getX();
				int y = e.getY();
				rec = new Rectangle(x, y, 0, 0);
				updateDrawableRect(getWidth(), getHeight());
				repaint();
			}
			@Override
			public void mouseReleased(MouseEvent e)
			{
				updateSize(e);
				myRecs.add(recDraw);
			}
		});
		addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{
				updateSize(e);
			}
		});
	}
	
	void updateSize(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();
		rec.setWidth(x - rec.getX());
		rec.setHeight(y - rec.getY());
		updateDrawableRect(getWidth(), getHeight());
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (rec != null)
		{
			g.setColor(Color.BLACK);
			g.drawRect(recDraw.getX(), recDraw.getY(), recDraw.getWidth(), recDraw.getHeight());
			g.setColor(Color.PINK);
			g.fillRect(recDraw.getX() + 1, recDraw.getY() + 1, recDraw.getWidth() - 1, recDraw.getHeight() - 1);
			for(int i = 0; i < myRecs.size(); i ++)
			{
				g.setColor(Color.BLACK);
				g.drawRect(myRecs.get(i).getX(), myRecs.get(i).getY(), myRecs.get(i).getWidth(), myRecs.get(i).getHeight());
				g.setColor(Color.PINK);
				g.fillRect(myRecs.get(i).getX() + 1, myRecs.get(i).getY() + 1, myRecs.get(i).getWidth() - 1, myRecs.get(i).getHeight() - 1);
			}
			
			findIntersectingRectangles();
			if(drawIntersections)
			{
			
			for(int i = 0; i < intersectRecs.size(); i ++)
			{
				g.setColor(Color.BLACK);
				g.drawRect(intersectRecs.get(i).getX(), intersectRecs.get(i).getY(), intersectRecs.get(i).getWidth(), intersectRecs.get(i).getHeight());
				g.setColor(Color.CYAN);
				g.fillRect(intersectRecs.get(i).getX() + 1, intersectRecs.get(i).getY() + 1, intersectRecs.get(i).getWidth() - 1, intersectRecs.get(i).getHeight() - 1);
			}
			}
			else
			{
				for(int i = 0; i < intersectRecs.size(); i ++)
				{
					g.setColor(Color.PINK);
					g.drawRect(intersectRecs.get(i).getX(), intersectRecs.get(i).getY(), intersectRecs.get(i).getWidth(), intersectRecs.get(i).getHeight());
					g.setColor(Color.PINK);
					g.fillRect(intersectRecs.get(i).getX() + 1, intersectRecs.get(i).getY() + 1, intersectRecs.get(i).getWidth() - 1, intersectRecs.get(i).getHeight() - 1);
				}
				for(int i = 0; i < myRecs.size(); i ++)
				{
					g.setColor(Color.BLACK);
					g.drawRect(myRecs.get(i).getX(), myRecs.get(i).getY(), myRecs.get(i).getWidth(), myRecs.get(i).getHeight());
					g.setColor(Color.PINK);
					g.fillRect(myRecs.get(i).getX() + 1, myRecs.get(i).getY() + 1, myRecs.get(i).getWidth() - 1, myRecs.get(i).getHeight() - 1);
				}
			}
			if(drawUnion)
			{
				findUnion();
				if (myRecs.size() > 0)
				{ 
					for(int i = 0; i < myRecs.size(); i++)
					{
						Graphics2D g2 = (Graphics2D) g.create();
						Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
						g2.setColor(Color.BLACK);
						g2.setStroke(dashed);
						g2.drawRect(unionRec.getX(), unionRec.getY(), unionRec.getWidth(), unionRec.getHeight());
					}
				}
			}
			if(drawCommon)
			{
				if(commonAreaExist())
				{
					for(int i = 0; i < intersectRecs.size(); i++)
					{
					g.setColor(Color.BLACK);
					g.drawRect(findCommonArea().getX(), findCommonArea().getY(), findCommonArea().getWidth(), findCommonArea().getHeight());
					g.setColor(Color.BLUE);
					g.fillRect(findCommonArea().getX() + 1, findCommonArea().getY() + 1, findCommonArea().getWidth() + 1, findCommonArea().getHeight() + 1);
					}
				}
			}
		}
		if(allowClear)
		{
			for(int i = 0; i < myRecs.size(); i++)
			{
			g.clearRect(myRecs.get(i).getX(), myRecs.get(i).getY(), myRecs.get(i).getWidth(), myRecs.get(i).getHeight());
			}
			for(int i = 0; i < myRecs.size(); i++)
			{
			g.clearRect(intersectRecs.get(i).getX(), intersectRecs.get(i).getY(), intersectRecs.get(i).getWidth(), intersectRecs.get(i).getHeight());
			g.clearRect(findCommonArea().getX(), findCommonArea().getY(), findCommonArea().getWidth(), findCommonArea().getHeight());
			}
			
		}
	}
	
	
	private void findIntersectingRectangles()
	{
		if (myRecs.size() > 1)
		{
			for(int i = 0; i < myRecs.size() - 1; i ++) // was size - 1
			{
				Rectangle temp = myRecs.get(i).intersect(myRecs.get(myRecs.size()-1));
				if((temp.getX() + temp.getY() + temp.getHeight() + temp.getWidth()) != 0)
				{
					intersectRecs.add(temp);		
				}
			}
		}
	}
	
	private void findUnion()
	{
		unionRec = myRecs.get(0);
		if(myRecs.size() == 1)
		{
		}
		else if(myRecs.size() == 2)
		{
			unionRec = myRecs.get(0).union(myRecs.get(1));
		}
		else if (myRecs.size() >= 3)
		{
			for (int i = 1; i < myRecs.size(); i++)
			{
				unionRec = unionRec.union(myRecs.get(i));
			}
		}
	}
	
	/**
	 * Does a common area exist for all the intersecting rectangles
	 * @return boolean
	 */
	private boolean commonAreaExist()
	{
		if (!(intersectRecs.size() < 2))
		{
		for(int i = 0; i < intersectRecs.size(); i++)
		{
			for(int j = 1; j < intersectRecs.size(); j++)
			{
				if(!(intersectRecs.get(i).overlaps(intersectRecs.get(j))))
				{
					return false;
				}
			}
		}
		return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Finds the rectangle that is the common area of the
	 * intersecting rectangles
	 * @return Rectangle
	 */
	private Rectangle findCommonArea()
	{
		if(commonAreaExist())
		{
			commonRec = intersectRecs.get(0);
			for(int i = 1; i < intersectRecs.size(); i++)
			{
				commonRec = commonRec.intersect(intersectRecs.get(i));
			}
		}
		else
		{
			commonRec = new Rectangle(0, 0, 0, 0);
		}
		return commonRec;
	}
	
	private void updateDrawableRect(int compWidth, int compHeight)
	{
		int x = rec.getX();
		int y = rec.getY();
		int width = rec.getWidth();
		int height = rec.getHeight();
		
		if (width < 0)
		{
			width = 0 - width;
			x = x - width + 1;
			if(x < 0)
			{
				width += x;
				x = 0;
			}
		}
		if (height < 0)
		{
			height = 0 - height;
			y = y - height;
			if (y < 0)
			{
				height += y;
				y = 0;
			}
		}
		
		if ((x + width) > compWidth)
		{
			width = compWidth - x;
		}
		if ((y + height) > compHeight)
		{
			height = compHeight - y;
		}
		
		recDraw = new Rectangle(x, y, width, height);
	}
	
	public void clearPanel()
	{
		allowClear = true;
		rec = null;
		repaint();
		recDraw = null;
		unionRec = null;
		commonRec = null;
		myRecs.clear();
		intersectRecs.clear();
		drawIntersections = false;
		drawUnion = false;
		drawCommon = false;
		allowClear = false;
	}
	
}