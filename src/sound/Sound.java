//author: 彭寶儒
package sound;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
//walk, rolling, good/bad event, add, minus, win  
public class Sound{

	public void playSound(String musicLocation, boolean off) {
		File soundEffectPath = new File (musicLocation);
		try {
			if(soundEffectPath.exists() ) {
				if(!off) {
					AudioInputStream audio = AudioSystem.getAudioInputStream(soundEffectPath);
					Clip clip = AudioSystem.getClip();
					clip.open(audio);
					clip.start();
				}
				else {
					System.out.println("Turn off Sound effect");
				}
			}
			else {
				System.out.println("Sound effect File isn't exists");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
