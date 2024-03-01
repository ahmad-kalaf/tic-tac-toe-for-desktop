package sounds;

import javax.sound.sampled.*;
import java.net.URL;

public class ClickSound
{
	static Clip clip;
    public void startSound() throws Exception
    {
        URL file = getClass().getResource("click.wav");
        
        AudioInputStream ais = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(ais);
        clip.start();
    }
}
