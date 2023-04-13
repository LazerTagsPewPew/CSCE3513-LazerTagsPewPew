package teamseven.lasertag;
// author : Nathan Sampara and Drew Stull
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import java.util.Random;

public class musicGenerator 
{
    public void startPlaying()
    {
        //random number is chosen and stored in trackSelector
        Random rand = new Random();
        int trackSelector = rand.nextInt(3);
        trackSelector = trackSelector + 1;
        //System.out.println(trackSelector);
        String testFile = ("src/main/java/teamseven/lasertag/Track0" + trackSelector + ".wav");
        System.out.println(testFile);
        PlayMusic(testFile);
        //JOptionPane.showMessageDialog(null, "Press Ok to stop playing");
    }

    public static void PlayMusic(String fileName)
    {
        try
        {
            File musicFile = new File(fileName);

            if(musicFile.exists())
            {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
            }
            else
            {
                System.out.println("Cant read the audio file");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
