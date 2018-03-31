package finalproject_104502518;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class music {
	
	//AudioInputStream buttonstream = AudioSystem.getAudioInputStream("mysource/button.wav");
	private File bgm;
	boolean start;
	Clip clip;
	music(String source){
		bgm = new File(source);
		start = false;
	}
	void replay(){
		try{
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(bgm));
			clip.loop(clip.LOOP_CONTINUOUSLY);
			start = true;
		}catch(Exception e){
			
		}
	}
	
	void play(){
		try{
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(bgm));
			clip.start();
		}catch(Exception e){
			
		}
	}
	
	void stop(){
		clip.stop();
	}
}
