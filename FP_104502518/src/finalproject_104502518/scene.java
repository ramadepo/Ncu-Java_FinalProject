package finalproject_104502518;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class scene extends JPanel{  //panel可設定透明度和顏色
	
	public BufferedImage bi;
	private float[] scales = {1f, 1f, 1f, 1f};
	private float[] offsets = new float[4];
	private RescaleOp rop;
	
	
	scene(ImageIcon icon){
		
		int width = (int) icon.getIconWidth();
		int height = (int) icon.getIconHeight();
		this.setPreferredSize(new Dimension(width, height));
		this.bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.bi.createGraphics().drawImage(icon.getImage(), 0, 0, width, height, null);
		rop = new RescaleOp(scales, offsets, null);
		
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, rop, 0, 0);
	}
	
	public void setAlphab(float alpha){
		this.scales[0] = this.scales[1] = this.scales[2] = alpha;
		this.rop = new RescaleOp(scales, offsets, null);
	}
	
	public void setAlpha(float alpha){
		this.scales[3] = alpha;
		this.rop = new RescaleOp(scales, offsets, null);
	}
	
	public void setIcon(ImageIcon icon){
		
		int width = (int) icon.getIconWidth();
		int height = (int) icon.getIconHeight();
		this.setPreferredSize(new Dimension(width, height));
		this.bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.bi.createGraphics().drawImage(icon.getImage(), 0, 0, width, height, null);
		rop = new RescaleOp(scales, offsets, null);
		
	}
}
