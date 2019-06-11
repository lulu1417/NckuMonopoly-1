//author: 彭寶儒
package sound;

import java.io.File;
import java.net.URI;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Music {
	public Music(String musicPath) {
		this.playing = true;
		this.clipTimePosition = 0;
		try {
			URL musicLocation = this.getClass().getResource(musicPath);
			AudioInputStream audio = AudioSystem.getAudioInputStream(musicLocation);
			clip = AudioSystem.getClip();
			clip.open(audio);
			setVol(-20, clip);
			clip.start(); 
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void check() {
		if(this.playing) {
			this.playing = false;
			clip.stop();
			clipTimePosition = clip.getMicrosecondPosition();
		}
		else {
			this.playing = true;
			clip.setMicrosecondPosition(clipTimePosition);
			clip.start();
		}
		
	}
	public void setVol(double vol, Clip clip) {
		FloatControl gain = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		float db = (float)vol;
		gain.setValue(db);
	}
	public boolean getPlaying() {
		return this.playing;
	}
	private Clip clip;
	private long clipTimePosition;
	private boolean playing;
}
