package finalproject_104502518;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;


class maincharacter extends JLabel implements ActionListener{  //主角
	
	int hp = 3;
	private ImageIcon charactershitr = new ImageIcon("mysource/characterr.gif");
	private ImageIcon charactershitl = new ImageIcon("mysource/characterl.gif");
	
	maincharacter(ImageIcon icon){
		super(icon);
	}
	
	void changesider(){
		this.setIcon(charactershitr);
	}
	
	void changesidel(){
		this.setIcon(charactershitl);
	}
	
	void damage(){
		hp--;
		System.out.println("你的HP："+hp);
	}
	
	int gethp(){
		return hp;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

class monster extends intro implements ActionListener{  //小怪
	
	int hp;
	int x,y;
	int youare;
	float alpha = 1;
	Timer behind = new Timer(10,this);
	Timer disappear = new Timer(10,this);
	Timer appear = new Timer(10,this);
	Timer reaperskill1 = new Timer(1000,this);
	Timer reaperskill2 = new Timer(10,this);
	ImageIcon alive = new ImageIcon("mysource/suzaku2.png");
	boolean reaperchange = false;
	boolean birdchange = false;
	boolean isout = false;
	int second = 0;
	
	monster(ImageIcon icon,int x){
		super(icon);
		hp = 3;
		youare = x;
		this.behind.start();
		if(youare == 0){
			this.reaperskill1.start();
		}
	}
	void getxy(int X, int Y){
		x=X;
		y=Y;
	}
	void damage(){
		hp--;
		if(hp <= 0){
			this.behind.stop();
			this.disappear.start();
			if((youare == 1) && (birdchange == false)){
				isout = false;
			}
			else{
				if(youare == 0){
					reaperskill1.stop();
					reaperskill2.stop();
				}
				isout = true;
			}
		}
		else{
			isout = false;
		}
	}
	boolean canoutornot(){
		if(alpha == 0){
			return isout;
		}
		else{
			return false;
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == behind){
			if(this.getX()+59 > x+22){
				this.setLocation(this.getX()-1, this.getY());
			}
			else if(this.getX()+59 < x+22){
				this.setLocation(this.getX()+1, this.getY());
			}
			if(this.getY()+76 > y+22){
				this.setLocation(this.getX(), this.getY()-1);
			}
			else if(this.getY()+76 < y+22){
				this.setLocation(this.getX(), this.getY()+1);
			}
		}
		else if(e.getSource() == disappear){
			alpha -= 0.05;
			this.setAlpha(alpha);
			this.repaint();
			if(alpha <= 0){
				alpha = 0;
				this.disappear.stop();
				if((youare == 1) && (birdchange == false)){
					birdchange = true;
					hp = 5;
					this.setIcon(alive);
					appear.start();
				}
			}
		}
		else if(e.getSource() == appear){
			alpha += 0.05;
			this.setAlpha(alpha);
			this.repaint();
			if(alpha >= 1){
				alpha = 1;
				this.appear.stop();
				this.behind.start();
			}
		}
		else if(e.getSource() == reaperskill1){
			second++;
			if((second%2 == 0)){
				this.reaperskill2.start();
			}
		}
		else if(e.getSource() == reaperskill2){
			if(reaperchange == false){
				alpha -=0.025;
				this.setAlphab(alpha);
				this.repaint();
				if(alpha <= 0){
					alpha = 0;
					reaperchange = true;
				}
			}
			else if(reaperchange == true){
				alpha +=0.025;
				this.setAlphab(alpha);
				this.repaint();
				if(alpha >= 1){
					alpha = 1;
					reaperchange = false;
					this.reaperskill2.stop();
				}
			}
		}
	}
}

class boss extends intro implements ActionListener{  //boss
	
	int n;
	int hp;
	float alpha = 0;
	Timer appear = new Timer(10,this);
	Timer disappear = new Timer(10,this);
	boolean use = false;
	boolean isout = false;
	
	boss(ImageIcon icon,int HP,int number){
		super(icon);
		hp = HP;
		n = number;
	}
	
	void comeup(){
		appear.start();
		use = true;
	}
	
	boolean canoutornot(){
		if(alpha <=0){
			use = false;
			return isout;
		}
		else{
			return false;
		}
	}
	
	void damage(){
		hp=hp-2;
		if(n == 1){
			System.out.println(hp);
			if(hp == 50){
				System.out.println("+++++++++++");
				System.out.println("+++++++++++");
				System.out.println("+++++++++++");
				System.out.println("+++++++++++");
			}
			if(hp <= 0){
				disappear.start();
			}
		}
		else if(n == 2){
			System.out.println(hp);
			if(hp == 100){
				System.out.println("+++++++++++");
				System.out.println("+++++++++++");
				System.out.println("+++++++++++");
				System.out.println("+++++++++++");
			}
			if(hp <= 0){
				disappear.start();
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == appear){
			alpha += 0.05;
			this.setAlpha(alpha);
			this.repaint();
			if(alpha >= 1){
				alpha = 1;
				this.appear.stop();
			}
		}
		else if(e.getSource() == disappear){
			alpha -= 0.05;
			this.setAlpha(alpha);
			this.repaint();
			
			if(alpha <= 0){
				alpha = 0;
				isout = true;
				this.disappear.stop();
			}
		}
	}
	
}