package ilstu.edu;

public class Rectangle
{
private int x,y,width,height;
	
	public Rectangle(int x,int y,int width,int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		if (this.width<0)
			this.width = 0;
		if (this.height<0)
			this.height = 0;
	}
	
	public Rectangle(Rectangle a)
	{
		this.x = a.x;
		this.y = a.y;
		this.width = a.width;
		this.height = a.height;
	}
	
	@Override
	public String toString()
	{
		return "Start: ("+x+","+y+"), Width: "+width+", Height: "+height+"\n";
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public void setWidth(int width)
	{
		this.width = width;
	}
	
	public void setHeight(int height)
	{
		this.height = height;
	}
	
	public int area()
	{
		return width*height;
	}
	
	public boolean overlaps(Rectangle a)
	{
		if ((x>a.x+a.width) || (a.x>x+width) || (y>a.y+a.height) || (a.y>y+height))
		{
			return false;
		}
		return true;
	}
	
	public Rectangle intersect(Rectangle a)
	{
		if (!overlaps(a))
			return new Rectangle(0,0,0,0);
		
		int left,right,top,bottom;
		
		if (x<a.x)
			left = a.x;
		else
			left = x;
		
		if (y<a.y)
			bottom = a.y;
		else
			bottom = y;
		
		if ((x+width)<(a.x+a.width))
			right = x+width;
		else
			right = a.x+a.width;
		
		if ((y+height)<(a.y+a.height))
			top = y+height;
		else
			top = a.y+a.height;
		
		return new Rectangle(left,bottom,right-left,top-bottom);
	}
	
	public Rectangle union(Rectangle a)
	{
		int left,right,top,bottom;
		
		if (x<a.x)
			left = x;
		else
			left = a.x;
		
		if (y<a.y)
			bottom = y;
		else
			bottom = a.y;
		
		if ((x+width)<(a.x+a.width))
			right = a.x+a.width;
		else
			right = x+width;
		
		if ((y+height)<(a.y+a.height))
			top = a.y+a.height;
		else
			top = y+height;
		
		return new Rectangle(left,bottom,right-left,top-bottom);
	}
}
