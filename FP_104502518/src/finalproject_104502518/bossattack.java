package finalproject_104502518;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

public class bossattack extends JLabel implements ActionListener{
	
	Timer attacktime = new Timer(10,this);
	int number;
	boolean use = false;
	
	bossattack(int n){
		number = n;
	}
	
	void setmode(int n){
		number = n;
	}
	void shoothappen(){
		use =true;
		attacktime.start();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == attacktime){
			if(number == 1){
				this.setLocation(this.getX(), this.getY()+7);
				
				if(this.getY() > 1070){
					attacktime.stop();
				}
			}
			else if(number == 3){
				this.setLocation(this.getX()-2, this.getY()+7);
				if(this.getY() > 1070){
					attacktime.stop();
				}
				if(this.getX() < -100){
					attacktime.stop();
				}
			}
			else if(number == 4){
				
			}
		}
	}

}
