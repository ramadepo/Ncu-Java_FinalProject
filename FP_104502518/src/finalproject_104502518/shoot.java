package finalproject_104502518;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class shoot extends JLabel implements ActionListener{
	
	boolean isout = false;
	boolean use = false;
	boolean bodyuse = false;
	int second = 0;
	ImageIcon bullet = new ImageIcon("mysource/bullet.gif");
	ImageIcon crash = new ImageIcon("mysource/crash.gif");
	Timer crashtime = new Timer(100,this);
	Timer shoottime = new Timer(1,this);
	int x,y;
	double temp;

	
	shoot(){
		this.setIcon(bullet);
	}
	
	void crashhappen(){
		bodyuse = false;
		shoottime.stop();
		this.setIcon(crash);
		crashtime.start();
	}
	
	 void shoothappen(){
		use = true;
		bodyuse = true;
		isout = false;
		this.setIcon(bullet);
		x = this.getX();
		y = this.getY();
	}
	 
	 boolean canoutornot(){
		 if(isout == true){
			 use = false;
		 }
		 return isout;
	 }
	 
	 void shooting(int a, int b){
		 /*快想想怎麼讓它飛出去阿*/
		 temp = Math.sqrt((double)(((a-x)*(a-x))+((b-y)*(b-y))));

		 x = (int)(((double)20*(a-x))/temp);

		 y = (int)(((double)20*(b-y))/temp);
		 
		 
		 shoottime.start();
	 }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == crashtime){
			second++;
			if(second>=5){
				this.setIcon(bullet);
				isout = true;
				crashtime.stop();
			}
		}
		else if(e.getSource() == shoottime){
			this.setLocation(this.getX()+x, this.getY()+y);
		}
	}
}
