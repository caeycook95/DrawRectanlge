/*
 * File name: MainFrame.java
 *
 * Programmer: Casey Cook
 * ULID: clcoo10
 *
 * Date: Feb 15, 2016
 *
 * Class: IT 179
 * Instructor: Dr. Li
 */
package ilstu.edu;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 * Java Swing interface for rectangles problem
 *
 * @author Casey Cook
 *
 */
public class MainFrame extends JFrame
{
	MyPanel myPanel;
	MyPanel clearPanel = new MyPanel();
	
	/**
	 * Uses GridBag Layout to organize components. 
	 * Listeners for checkboxes and buttons are here.
	 */
	public MainFrame() 
	{
		JFrame frame = new JFrame("Rectangle");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		myPanel = new MyPanel();
		myPanel.setBackground(Color.white);
		myPanel.setOpaque(true);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 500;
		c.ipadx = 100;
		c.weightx = 0.0;
		c.gridwidth = 5;
		c.gridx = 0;
		c.gridy = 0;
		frame.add(myPanel, c);
	
		
		JCheckBox chkIntersections = new JCheckBox("Draw intersections");
		c.fill = GridBagConstraints.NONE;
		c.ipady = 0;
		c.ipadx = 0;
		c.weightx = 0.2;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridwidth = 1;
		c.gridy = 1;
		frame.add(chkIntersections, c);
		chkIntersections.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
					myPanel.changeDrawIntersections();
					myPanel.repaint();
			}
			
		});
		
		JCheckBox chkUnion = new JCheckBox("Draw union");
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.2;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 1;
		c.gridwidth = 1;
		c.gridy = 1;
		frame.add(chkUnion, c);
		
		chkUnion.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				myPanel.changeDrawUnion();
				myPanel.repaint();
			}
			
		});
		
		JCheckBox chkCommonArea = new JCheckBox("Draw common area");
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.2;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 2;
		c.gridwidth = 1;
		c.gridy = 1;
		frame.add(chkCommonArea, c);
		
		chkCommonArea.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent e)
			{
				myPanel.changeDrawCommon();
				myPanel.repaint();
			}
			
		});
		
		JButton btnSaveImage = new JButton("Save Image");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.2;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 3;
		c.gridwidth = 1;
		c.gridy = 1;
		frame.add(btnSaveImage, c);
		
		btnSaveImage.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showSaveDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION)
				{
					try
					{
						ImageIO.write(getImage(), "png", fc.getSelectedFile() );
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
			}
			
		});
		
		JButton btnClear = new JButton("Clear drawing");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.2;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 4;
		c.gridwidth = 1;
		c.gridy = 1;
		frame.add(btnClear, c);
		frame.pack();
		frame.setVisible(true);
		
		btnClear.addActionListener(new ActionListener() 
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				chkCommonArea.setSelected(false);
				chkUnion.setSelected(false);
				chkIntersections.setSelected(false);
				myPanel.clearPanel();
			}
		});
	}
	
	/**
	 * Makes the JPanel into a buffered image
	 * @return BufferedImage image
	 */
	public BufferedImage getImage()
	{
		BufferedImage image = new BufferedImage(myPanel.getWidth(), myPanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
		myPanel.paint(image.getGraphics());
		return image;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		MainFrame mf = new MainFrame();
	}
}


