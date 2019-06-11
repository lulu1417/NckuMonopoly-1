//author: 彭寶儒
package sound;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
//walk, rolling, good/bad event, add, minus, win  
public class Sound{
	public Sound(String soundPath) {
		try {
			URL soundLocation = this.getClass().getResource(soundPath);
			AudioInputStream audio = AudioSystem.getAudioInputStream(soundLocation);
			Clip clip = AudioSystem.getClip();
			clip.open(audio);
			clip.start();
			//vol
			if(soundPath.equals("/rolling.wav")) {
				FloatControl gain = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
				float db = (float)-20.0;
				gain.setValue(db);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void playSound(String musicLocation) {
		new Sound(musicLocation);
	}
}
