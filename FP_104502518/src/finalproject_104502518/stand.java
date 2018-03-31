package finalproject_104502518;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class stand extends JLabel implements ActionListener{
	
	int time = 5;
	ImageIcon exist = new ImageIcon("mysource/blocks.png");
	ImageIcon vanish = new ImageIcon("mysource/block.png");
	static int width; 
	static int height;
	boolean use = false;
	Timer counttime = new Timer(1000,this);
	
	stand(){
		
		width = exist.getIconWidth();
		height = exist.getIconHeight();
	}
	
	void block1(int t){
		time = t;
		this.setIcon(exist);
		use = true;
	}
	
	void block2(){
		this.setIcon(vanish);
	}
	
	void block3(){
		use =false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == counttime){
			time--;
			if(time == 2){
				block2();
			}
			else if(time <= 0){
				block3();
			}
		}
	}
}
