//author: 彭寶儒
package sound;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
//walk, rolling, good/bad event, add, minus, win  
public class Sound{
	public Sound(String soundPath) {
		try {
			URL soundLocation = this.getClass().getResource(soundPath);
			AudioInputStream audio = AudioSystem.getAudioInputStream(soundLocation);
			Clip clip = AudioSystem.getClip();
			clip.open(audio);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void playSound(String musicLocation) {
		new Sound(musicLocation);
	}
}
