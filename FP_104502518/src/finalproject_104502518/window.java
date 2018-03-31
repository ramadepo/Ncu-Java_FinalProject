package finalproject_104502518;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;

import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;
import org.dyn4j.geometry.Vector3;

public class window extends JFrame implements MouseListener, ActionListener ,KeyListener{
	
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Image cursorimage = toolkit.getImage("mysource/cursor2.png");
	Cursor c = toolkit.createCustomCursor(cursorimage, new Point(this.getX(),this.getY()), "img");
	
	boolean first = true;
	boolean gameornot = true;
	private shoot[] bullet = new shoot[20];
	private stand[] block = new stand[10];
	private scene all; //背景
	private intro introduce,introduce1; //羊皮紙  羊皮紙+字
	private JLabel start; //start鍵
	private JLabel exit; //exit鍵
	private JLabel space = new JLabel(); //debug用
	
	private float alpha = 1; //顏色透明度傳入值
	private int n,second = 0;
	private int standtime = 5;
	long littletime;
	
	private boolean change = false;  //狀態設定用
	private boolean rightmove = false;
	private boolean leftmove = false;
	private boolean standing = false;
	private boolean jumping = false;
	private boolean boss1dead = false;
	private boolean boss2dead = false;
	
	private music bgm0;
	private music bgm1 = new music("mysource/mainbgm.wav");
	private music buttonsound = new music("mysource/02_decide.wav");
	
	private ImageIcon gameover = new ImageIcon("mysource/101204_e.png");
	private ImageIcon background;
	private ImageIcon starticon = new ImageIcon("mysource/start.png");
	private ImageIcon exiticon = new ImageIcon("mysource/exit.png");
	private ImageIcon starticon2 = new ImageIcon("mysource/start2.png");
	private ImageIcon exiticon2 = new ImageIcon("mysource/exit2.png");
	private ImageIcon paper = new ImageIcon("mysource/0426gg.png");
	private ImageIcon paperword = new ImageIcon("mysource/0426ggg.png");
	private ImageIcon charactershitr = new ImageIcon("mysource/characterr.gif");
	private ImageIcon boss1 = new ImageIcon("mysource/ticktackman2013.png");
	private ImageIcon boss11 = new ImageIcon("mysource/ticktackman2014.png");
	private ImageIcon boss2 = new ImageIcon("mysource/catastrophe.png");
	private ImageIcon boss22 = new ImageIcon("mysource/catastrophe2.png");
	private ImageIcon boss3 = new ImageIcon("mysource/lucifer.png");
	private ImageIcon boss33 = new ImageIcon("mysource/lucifer2.png");
	private ImageIcon reaper = new ImageIcon("mysource/reaper.png");
	private ImageIcon bird = new ImageIcon("mysource/suzaku.png");
	private maincharacter shit = new maincharacter(charactershitr);
	private boss lucifer = new boss(boss3,300,3);
	private boss catastrophe = new boss(boss2,100,1);
	private boss ticktackman = new boss(boss1,200,2);
	
	private Timer timer0 = new Timer(10,this); //第一淡化用
	private Timer timer1 = new Timer(10,this); //第二淡化用
	private Timer timer2 = new Timer(10,this); // gameover
	private Timer gametimer = new Timer(1000,this);  //遊戲時間
	private Timer physicaltimer = new Timer(16,this);  //物理時間
	private Timer bosstimer = new Timer(1000,this);  //BOSS時間
	
	World world = new World();
	Circle mainshape = new Circle(20);
	Rectangle blockshape = new Rectangle(55, 55);
	Body maincharacter = new Body();
	Body[] ground = new Body[10];
	Body[] bon = new Body[20];
	
	monster newmonster;
	Body newmonsterbody;
	Rectangle monstershape = new Rectangle(118,152);
	Rectangle bulletshape = new Rectangle(34,34);
	ArrayList<monster> monsterlist = new ArrayList<monster>();
	int monsternumber = 0;
	int where;
	ArrayList<Body> monsterbody = new ArrayList<Body>();
	Random appear = new Random();
	
	Body boss1body = new Body();
	Body boss2body = new Body();
	Body boss3body = new Body();
	Rectangle bossshape = new Rectangle(461,288);
	
	int modenumber;
	int bosssecond = 0;
	private ImageIcon bubbled = new ImageIcon("mysource/bubbledown.png");
	private ImageIcon bubbles = new ImageIcon("mysource/boss1attack.gif");
	private ImageIcon light = new ImageIcon("mysource/boss2attack.gif");
	private ImageIcon Light = new ImageIcon("mysource/boss2attack3.gif");
	private bossattack[] bubble = new bossattack[10];
	Body[] bubblebody = new Body[10]; 
	Rectangle bubbleshape = new Rectangle(36,77);
	Rectangle bubbleshape2 = new Rectangle(71,108);
	Rectangle bubbleshape3 = new Rectangle(320,420);
	boolean startornot = false;
	
	Thread worldtime = new Thread(new Runnable() { 
        public void run() {
            while(true){
            	if(catastrophe.use == true){
            		if(maincharacter.isInContact(boss1body)){
            			shit.damage();
            		}
            	}
            	else if(ticktackman.use == true){
            		if(maincharacter.isInContact(boss2body)){
            			shit.damage();
            		}
            	}
                try{
                	littletime = System.nanoTime();
                	shit.setLocation((int) maincharacter.getTransform().getTranslationX(),(int) maincharacter.getTransform().getTranslationY());
                	if(maincharacter.getTransform().getTranslationY()>=750){
                		if(gameornot){
                			gameornot = false;
                			gameend();
                		}
                	}
                	/*if(shit.hp <= 0){
                		if(gameornot){
                			gameornot = false;
                			gameend();
                		}
                	}*/
                	Thread.sleep(16);
                	world.update(System.nanoTime() - littletime);
                }catch(Exception e){
                	System.out.println("哇塞還真的會出現錯誤喔!?");
                }
                
            }
         } 
    });
	Thread bullettime = new Thread(new Runnable() { 
        public void run() {
            while(true){
                try{
                	for(int i = 0; i < 20; i++){
						if(bullet[i].use == true){
							if(bullet[i].bodyuse == true){
								bon[i].getTransform().setTranslationX(bullet[i].getX());
								bon[i].getTransform().setTranslationY(bullet[i].getY());
							}
							
						}
					}
                	Thread.sleep(16);
                }catch(Exception e){
                	System.out.println("哇塞還真的會出現錯誤喔!?");
                }
                
            }
         } 
    });
	Thread bossattacktime = new Thread(new Runnable() { 
        public void run() {
            while(true){
                try{
                	for(int i = 0; i < 10; i++){
						if(bubble[i].use == true){
								bubblebody[i].getTransform().setTranslationX(bubble[i].getX());
								bubblebody[i].getTransform().setTranslationY(bubble[i].getY());
								if(bubblebody[i].isInContact(maincharacter)){
									shit.damage();
									System.out.println("你死定了被淹沒~囉");
								}
						}
					}
                	Thread.sleep(16);
                }catch(Exception e){
                	System.out.println("哇塞還真的會出現錯誤喔!?");
                }
                
            }
         } 
    });
	window(){
		
		this.setCursor(c);
		background = new ImageIcon("mysource/01.jpg");
		all = new scene(background);
		all.setLayout(new BorderLayout());
		add(all); //背景panel設定

		start = new JLabel(starticon);
		start.setBounds(400, 400, starticon.getIconWidth(), starticon.getIconHeight());
		start.addMouseListener(this);
		all.add(start);  //start鍵設定
		
		exit = new JLabel(exiticon);
		exit.setBounds(400, 500, exiticon.getIconWidth(), exiticon.getIconHeight());
		exit.addMouseListener(this);
		all.add(exit);  //exit鍵設定
		all.add(space);
		
		
		
		
		for(int i = 0; i < 10 ; i++){
			block[i] = new stand();
			ground[i] = new Body();
			ground[i].addFixture(blockshape);
			ground[i].setMass(MassType.INFINITE);
			ground[i].rotate(0.001);
		}
		for(int i = 0; i<20 ; i++){
			bullet[i] = new shoot();
			bon[i] = new Body();
			bon[i].addFixture(bulletshape);
			bon[i].setMass(MassType.NORMAL);
		}
		for(int i = 0; i<10 ; i++){
			bubble[i] = new bossattack(1);
			bubble[i].setIcon(bubbled);
			bubblebody[i] = new Body();
			bubblebody[i].addFixture(bubbleshape);
			bubblebody[i].setMass(MassType.INFINITE);
		}
		setTitle("時空射手");
		setLocation(450,40);
		setSize(background.getIconWidth()+6,background.getIconHeight()+35);
		setResizable(false);
		
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //視窗設定開始
		setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
			
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == start){
			start.setIcon(starticon2); //滑鼠碰到start變色
		}
		else if(e.getSource() == exit){
			exit.setIcon(exiticon2); //滑鼠碰到exit變色
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == start){
			start.setIcon(starticon);  //滑鼠離開start變色
		}
		else if(e.getSource() == exit){
			exit.setIcon(exiticon);  //滑鼠離開exit變色
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		/*放在這裡*/
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getX()+"  "+e.getY());  //debug用
		if(e.getSource() == start){
			System.out.println("start");
			buttonsound.play();
			all.remove(start);
			all.remove(exit);
			all.remove(space);
			timer0.start();
		}
		else if(e.getSource() == exit){
			System.out.println("exit");
			buttonsound.play();
			this.dispose();
			System.exit(1);
		}
		else if(e.getSource() == introduce1){
			timer1.start();
			if(bgm1.start == false){
				bgm1.replay();
			}
		}
		else if(e.getSource() == all){
			
			if(e.getButton() == MouseEvent.BUTTON3){  //右鍵建方塊
				for(int i = 0; i < 10; i++){
					if(block[i].use == false){
						all.remove(space);
						ground[i].getTransform().setTranslationX(e.getX()-33);
						ground[i].getTransform().setTranslationY(e.getY()-32);
						world.addBody(ground[i]);
						block[i].block1(standtime);
						block[i].setBounds(e.getX()-33, e.getY()-32, block[i].width, block[i].height);
						all.add(block[i]);
						all.add(space);
						all.repaint();
						break;
					}
				}
			}
			else if(e.getButton() == MouseEvent.BUTTON1){  //左鍵射擊
				for(int i = 0; i < 20; i++){
					if(bullet[i].use == false){
						if(bullet[i].bodyuse == false){
							System.out.println(i+"號子彈");
							all.remove(space);
							bullet[i].setLocation(shit.getX()+10, shit.getY()+10);
							world.addBody(bon[i]);
							bullet[i].setSize(34, 34);
							bullet[i].shoothappen();
							all.add(bullet[i]);
							all.add(space);
							bullet[i].shooting(e.getX(),e.getY());
							all.repaint();
							break;
						}
						
					}
				}
			}
		}
		/**/
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == timer0){  //第一淡化
			if(change == false){
				all.setAlphab(alpha);
				all.repaint();
				alpha -= 0.025;
				if(alpha <= 0){
					change = true;
					alpha = 0;
					all.setIcon(new ImageIcon("mysource/02.jpg"));
					introduce = new intro(paper);
					introduce.setBounds(125, 80, paper.getIconWidth(), paper.getIconHeight());
					all.add(introduce);
					all.add(space);
					introduce.setAlphab(0);
					all.repaint();
					introduce.repaint();
				}
			}
			if(change == true){  //第一顯示
				all.setAlphab(alpha);
				introduce.setAlphab(alpha);
				all.repaint();
				introduce.repaint();
				alpha += 0.025;
				if(alpha >= 1){
					change = false;
					alpha = 0;
					timer0.stop();
					timer1.start();
					introduce1 = new intro(paperword);
					introduce1.setBounds(125, 80, paperword.getIconWidth(), paperword.getIconHeight());
					all.remove(space);
					all.add(introduce1);
					all.add(space);
					introduce1.addMouseListener(this);
					all.repaint();
					introduce1.repaint();
				}
			}
		}
		
		else if(e.getSource() == timer1){  //第二淡化
			if(change == false){
				introduce.setAlpha(1-alpha);
				alpha += 0.05;
				introduce1.repaint();
				if(alpha>=1){
					alpha = 1;
					timer1.stop();
					all.remove(introduce);
					all.repaint();
					change = true;
				}
			}
			else{  //第二淡化完遊戲開始
				alpha -= 0.05;
				introduce1.setAlpha(alpha);
				introduce1.repaint();
				if(alpha<=0){
					timer1.stop();
					all.removeAll();
					all.repaint();
					shit.setBounds(486,505,charactershitr.getIconWidth(),charactershitr.getIconHeight());
					all.add(shit);
					
					ground[0].translate(473, 556);
					world.addBody(ground[0]);
					block[0].block1(10);
					block[0].setBounds(473, 556, block[0].width, block[0].height);
					all.add(block[0]);
					all.add(space);
					all.addMouseListener(this);
					this.addKeyListener(this);
					gametimer.start();
					world.setGravity(new Vector2(0,9.8));
					maincharacter.setGravityScale(10);
					maincharacter.addFixture(mainshape);
					maincharacter.setMass(MassType.NORMAL);
					maincharacter.getTransform().setTranslationX(486);
					maincharacter.getTransform().setTranslationY(505);
					world.addBody(maincharacter);
					
					gameornot = true;
					
					worldtime.start();
					bullettime.start();
					physicaltimer.start();
					
				}
			}
		}
		
		else if(e.getSource() == timer2){  //gameover
			if(change == false){
				all.setAlphab(alpha);
				alpha -= 0.025;
				all.repaint();
				if(alpha <= 0){
					alpha = 0;
					all.removeAll();
					world.removeAllBodies();
					//gametimer.stop();
					//physicaltimer.stop();
					//bosstimer.stop();
					//bossattacktime.stop();
					//worldtime.stop();System.out.println("剛進"); 
					//bullettime.stop();
					all.repaint();
					all.setIcon(gameover);
					change = true;
				}
			}
			else{
				all.setAlphab(alpha);
				alpha += 0.025;
				all.repaint();
				if(alpha >= 1){
					alpha = 1;
					change = false;
					all.add(exit);
					all.add(space);
					timer2.stop();
				}
			}
		}
		
		else if(e.getSource() == gametimer){  //遊戲時間
			second++;
			if((second == 15)&&!boss1dead){
				modenumber =1;
				bosstimer.start();
				catastrophe.comeup();
				catastrophe.setBounds(290, 0, boss2.getIconWidth(), boss2.getIconHeight());
				boss1body.addFixture(bossshape);
				boss1body.setMass(MassType.INFINITE);
				boss1body.translate(500, 120);
				all.remove(space);
				all.add(catastrophe);
				all.add(space);
				world.addBody(boss1body);
			}
			if(((second == 15)&&boss1dead)&&!boss2dead){
				bosstimer.start();
				ticktackman.comeup();
				ticktackman.setBounds(290, 0, boss2.getIconWidth(), boss2.getIconHeight());
				boss2body.addFixture(bossshape);
				boss2body.setMass(MassType.INFINITE);
				boss2body.translate(500, 120);
				all.remove(space);
				all.add(ticktackman);
				all.add(space);
				world.addBody(boss2body);
			}
			/*if(((second == 3)&&boss1dead)&&boss2dead){
				bosstimer.start();
				lucifer.comeup();
				lucifer.setBounds(290, 0, boss2.getIconWidth(), boss2.getIconHeight());
				boss3body.addFixture(bossshape);
				boss3body.setMass(MassType.INFINITE);
				boss3body.translate(500, 120);
				all.remove(space);
				all.add(lucifer);
				all.add(space);
				world.addBody(boss3body);
			}*/
			for(int i = 0; i < 10; i++){
				if(block[i].use == true){
					block[i].time -= 1;
					if(block[i].time == 2){
						block[i].block2();
					}
					else if(block[i].time == 0){				
						world.removeBody(ground[i]);
						all.remove(block[i]);
						all.repaint();
						block[i].block3();
					}
				}
			}
			if((second%5) == 0){
				where = appear.nextInt(4);
				if(appear.nextInt(2) == 0){
					newmonster = new monster(reaper,0);
					newmonsterbody = new Body();
					newmonsterbody.addFixture(monstershape);
					newmonsterbody.setMass(MassType.INFINITE);
					monsterlist.add(newmonster);
					monsterbody.add(newmonsterbody);
					all.remove(space);
					if(where == 0){
						monsterlist.get(monsternumber).setBounds(appear.nextInt(1030), 1, reaper.getIconWidth(), reaper.getIconHeight());
						newmonsterbody.translate(monsterlist.get(monsternumber).getX(), monsterlist.get(monsternumber).getY());
					}
					else if(where == 1){
						monsterlist.get(monsternumber).setBounds(appear.nextInt(1030), 790, reaper.getIconWidth(), reaper.getIconHeight());
						newmonsterbody.translate(monsterlist.get(monsternumber).getX(), monsterlist.get(monsternumber).getY());
					}
					else if(where == 2){
						monsterlist.get(monsternumber).setBounds(1, appear.nextInt(803), reaper.getIconWidth(), reaper.getIconHeight());
						newmonsterbody.translate(monsterlist.get(monsternumber).getX(), monsterlist.get(monsternumber).getY());
					}
					else if(where == 3){
						monsterlist.get(monsternumber).setBounds(1020, appear.nextInt(803), reaper.getIconWidth(), reaper.getIconHeight());
						newmonsterbody.translate(monsterlist.get(monsternumber).getX(), monsterlist.get(monsternumber).getY());
					}
					world.addBody(newmonsterbody);
					all.add(monsterlist.get(monsternumber));
					all.add(space);
					System.out.println(monsternumber+"+"+monsterlist.get(monsternumber).getX()+"+"+monsterlist.get(monsternumber).getY());
					System.out.println(monsterlist.get(monsternumber).getWidth()+"+"+monsterlist.get(monsternumber).getHeight());
					monsternumber++;
				}
				else{
					newmonster = new monster(bird,1);
					newmonsterbody = new Body();
					newmonsterbody.addFixture(monstershape);
					newmonsterbody.setMass(MassType.INFINITE);
					monsterlist.add(newmonster);
					monsterbody.add(newmonsterbody);
					all.remove(space);
					if(where == 0){
						monsterlist.get(monsternumber).setBounds(appear.nextInt(1030)-118, 1, bird.getIconWidth(), bird.getIconHeight());
						newmonsterbody.translate(monsterlist.get(monsternumber).getX(), monsterlist.get(monsternumber).getY());
					}
					else if(where == 1){
						monsterlist.get(monsternumber).setBounds(appear.nextInt(1030)-118, 638, bird.getIconWidth(), bird.getIconHeight());
						newmonsterbody.translate(monsterlist.get(monsternumber).getX(), monsterlist.get(monsternumber).getY());
					}
					else if(where == 2){
						monsterlist.get(monsternumber).setBounds(1, appear.nextInt(803)-152, bird.getIconWidth(), bird.getIconHeight());
						newmonsterbody.translate(monsterlist.get(monsternumber).getX(), monsterlist.get(monsternumber).getY());
					}
					else if(where == 3){
						monsterlist.get(monsternumber).setBounds(902, appear.nextInt(803)-152, bird.getIconWidth(), bird.getIconHeight());
						newmonsterbody.translate(monsterlist.get(monsternumber).getX(), monsterlist.get(monsternumber).getY());
					}
					world.addBody(newmonsterbody);
					all.add(monsterlist.get(monsternumber));
					all.add(space);
					System.out.println(monsternumber+"+"+monsterlist.get(monsternumber).getX()+"+"+monsterlist.get(monsternumber).getY());
					System.out.println(monsterlist.get(monsternumber).getWidth()+"+"+monsterlist.get(monsternumber).getHeight());
					monsternumber++;
				}
			}
		}
		else if(e.getSource() == physicaltimer){  //物理偵測
			if(leftmove == true){
				if(maincharacter.getTransform().getTranslationX()>0){
					maincharacter.translate(-5,0);
				}
			}
			if(rightmove == true){
				if(maincharacter.getTransform().getTranslationX()<723){
					maincharacter.translate(5,0);
				}
			}
			for(int i = 0; i < 10; i++){
				if(block[i].use == true){
					if(maincharacter.isInContact(ground[i])){
						jumping = false;
					}
				}
			}
			if(catastrophe.use == true){
				for(int i=0; i<20; i++){
					if(bullet[i].use == true){
						if(bullet[i].bodyuse == true){
							if(catastrophe.alpha >= 1){
								if(bon[i].isInContact(boss1body)){
									bullet[i].crashhappen();
									world.removeBody(bon[i]);
									catastrophe.damage();
									if(catastrophe.hp == 50){
										catastrophe.setIcon(boss22);
										modenumber = 2;
									}
									
								}
							}
						}
					}
				}
			} 
			if(ticktackman.use == true){
				for(int i=0; i<20; i++){
					if(bullet[i].use == true){
						if(bullet[i].bodyuse == true){
							if(ticktackman.alpha >= 1){
								if(bon[i].isInContact(boss2body)){
									bullet[i].crashhappen();
									world.removeBody(bon[i]);
									ticktackman.damage();
									if(ticktackman.hp == 100){
										ticktackman.setIcon(boss11);
										standtime = 3;
									}
									
								}
							}
						}
					}
				}
			}
			/*if(lucifer.use == true){
				for(int i=0; i<20; i++){
					if(bullet[i].use == true){
						if(bullet[i].bodyuse == true){
							if(lucifer.alpha >= 1){
								if(bon[i].isInContact(boss3body)){
									bullet[i].crashhappen();
									world.removeBody(bon[i]);
									lucifer.damage();
									if(lucifer.hp == 300){
										lucifer.setIcon(boss33);
										modenumber = 8;
									}
									
								}
							}
						}
					}
				}
			}*/
			for(int i = 1; i <= monsternumber;i++){
				monsterlist.get(i-1).getxy(shit.getX(), shit.getY());
				monsterbody.get(i-1).getTransform().setTranslationX(monsterlist.get(i-1).getX()+40);
				monsterbody.get(i-1).getTransform().setTranslationY(monsterlist.get(i-1).getY()+60);
				/**/if(maincharacter.isInContact(monsterbody.get(i-1))){
					shit.damage();
					//System.out.println(shit.hp);
				}
				for(int j = 0; j < 20; j++){
					if(bullet[j].use == true){
						if(bullet[j].canoutornot()){
							all.remove(bullet[j]);
							
						}
						if(bullet[j].bodyuse == true){
							if(bullet[j].getX()>1060){
								bullet[j].crashhappen();
								world.removeBody(bon[j]);
							}
							else if(bullet[j].getX()<-40){
								bullet[j].crashhappen();
								world.removeBody(bon[j]);
							}
							else if(bullet[j].getY()>810){
								bullet[j].crashhappen();
								world.removeBody(bon[j]);
							}
							else if(bullet[j].getY()<-40){
								bullet[j].crashhappen();
								world.removeBody(bon[j]);
							}
							if(monsterlist.get(i-1).alpha >= 1){
								if(bon[j].isInContact(monsterbody.get(i-1))){
									System.out.println("3level");
									bullet[j].crashhappen();
									world.removeBody(bon[j]);
									monsterlist.get(i-1).damage();
								}
							}
							
						}
						
					}
					
				}
				if(monsterlist.get(i-1).canoutornot()){
					all.remove(monsterlist.get(i-1));
					world.removeBody(monsterbody.get(i-1));
					monsterlist.remove(i-1);
					monsterbody.remove(i-1);
					monsternumber--;
					i--;
					System.out.println(i);
				}
			}
		}
		else if(e.getSource() == bosstimer){//BOSS時間
			if(catastrophe.use == true){
				if(catastrophe.canoutornot()){
					System.out.println("get out!!!!!!!");
					boss1dead = true;
					all.remove(catastrophe);
					world.removeBody(boss1body);
					bosssecond = 0;
					bosstimer.stop();
					//bossattacktime.stop();
					//startornot = false;
					second = 0;
				}
				bosssecond++;
				if(modenumber == 1){
					if(bosssecond%5 == 0){
						for(int i = 0; i <10; i++){
							bubble[i].setBounds(appear.nextInt(1024),215,36,77);
							if(bubble[i].use == false){
								all.remove(space);
								all.add(bubble[i]);
								all.add(space);
								world.addBody(bubblebody[i]);
							}
							bubble[i].shoothappen();
						}
					}
					if(startornot == false){
						bossattacktime.start();
						startornot = true;
						System.out.println("sayhello");
					}
				}
				else if(modenumber == 2){
					for(int i=0;i<10;i++){
						bubble[i].setIcon(bubbles);
						bubble[i].setmode(3);
						bubblebody[i].addFixture(bubbleshape2);
					}
					modenumber = 3;
				}
				else if(modenumber == 3){
					if(bosssecond%3 == 0){
						int whatever = appear.nextInt(524);
						for(int i = 0; i <5; i++){
							bubble[i].setBounds(whatever+100*i,205,99,99);
							
							bubble[i].shoothappen();
						}
						for(int i = 5; i <10; i++){
							bubble[i].setBounds(whatever+100*(i-4),105,99,99);
							
							bubble[i].shoothappen();
						}
					}
				}
			}
			if(ticktackman.use == true){
			 	if(ticktackman.canoutornot()){
			 		System.out.println("get out!!!!!!!");
					boss2dead = true;
					all.remove(ticktackman);
					world.removeBody(boss2body);
					bosssecond = 0;
					modenumber = 5;
					standtime=5;
					bosstimer.stop();
					//bossattacktime.stop();
					//startornot = false;
					second = 0;
				}
				bosssecond++;
				if(modenumber == 3){
					for(int i=0;i<10;i++){
						bubble[i].setIcon(Light);/*5k4xu35k3jxfkdshjlgd*/
						bubble[i].setmode(4);
						bubblebody[i].addFixture(bubbleshape3);
					}
					modenumber = 4;
				}
				else if(modenumber == 4){
					if(bosssecond%5 == 0){
						for(int i = 0; i < 10; i++){
							bubble[i].setBounds(i*100-100,205,320,240);
							bubble[i].setmode(4);
							bubble[i].shoothappen();
						}
					}
					/*if(startornot == false){
						//bossattacktime.resume();
						startornot = true;
						System.out.println("sayhello");
					}*/
				}
				else if(modenumber == 5){
					for(int i = 0; i <10; i++){
						bubble[i].setBounds(1600,205,36,77);
						
					}
					modenumber = 6;
				}
			}
			/*if(lucifer.use == true){
			 	if(lucifer.canoutornot()){
			 		System.out.println("get out!!!!!!!");
			 		gameend();
				}
				bosssecond++;
				if(modenumber == 6){
					for(int i=0;i<10;i++){
						bubble[i].setIcon(////////);
						bubble[i].setmode(7);
						bubblebody[i].addFixture(/////////);
					}
					modenumber = 7;
				}
				else if(modenumber == 7){
					for(int i = 0; i < 10; i++){
						bubble[i].setBounds(//////////);
						bubble[i].shoothappen();
					}
				}
				else if(modenumber == 8){
					for(int i = 0; i <10; i++){
						//////////////////////
						 * setmode9
					}
					modenumber = 9;
				}
				else if(modenumber == 9){
					for(int i = 0; i < 10; i++){
						bubble[i].setBounds(//////////);
						bubble[i].shoothappen();
					}
				}
			}*/
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_A){  //左移
			shit.changesidel();
			leftmove = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_D){  //右移
			shit.changesider();
			rightmove = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_W){  //跳
			if(jumping == false){
				jumping = true;
				maincharacter.applyForce(new Vector2(0,-10000000));
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_A){  //左移
			leftmove = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_D){  //右移
			rightmove = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	void gameend(){
		change = false;
		alpha = 1;
		timer2.start();
		
	}
}
