//author: 彭寶儒
package sound;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Music {
	public void playMusic(String musicLocation) {
		File musicPath = new File (musicLocation);
		try {
			if(musicPath.exists()) {
				AudioInputStream audio = AudioSystem.getAudioInputStream(musicPath);
				clip = AudioSystem.getClip();
				clip.open(audio);
				setVol(-20, clip);
				clip.start(); 
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			else {
				System.out.println("Music File doesn't exist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void check(boolean off) {
		if(off) {
			clip.stop();
			clipTimePosition = clip.getMicrosecondPosition();
		}
		else {
			clip.setMicrosecondPosition(clipTimePosition);
			clip.start();
		}
		
	}
	public void setVol(double vol, Clip clip) {
		FloatControl gain = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		float db = (float)vol;
		gain.setValue(db);
		
	}
	static Clip clip;
	static long clipTimePosition;
}
