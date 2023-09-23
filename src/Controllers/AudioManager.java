package Controllers;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * The AudioManager class is responsible for managing audio playback.
 *
 * It provides a method to play an audio file.
 * @Author Daniele Marchetilli
 */
public class AudioManager {

    /**
     * The private static instance of the AudioManager class.
     */
    private static AudioManager instance;

    /**
     * Gets the singleton instance of the AudioManager class.
     *
     * @return The singleton instance of the AudioManager class.
     */
    public static AudioManager getInstance() {
        if (instance == null)
            instance = new AudioManager();
        return instance;
    }

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private AudioManager() {

    }

    /**
     * Plays an audio file adn return its clip class.
     *
     * @param filename The name of the audio file to play.
     * @return The Clip object for the audio file.
     */
    public Clip play(String filename) {


        try {
            InputStream in = new BufferedInputStream(new FileInputStream(filename));
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(in);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
            return clip;
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (UnsupportedAudioFileException e1) {
            e1.printStackTrace();
        } catch (LineUnavailableException e1) {
            e1.printStackTrace();
        }
        return null;
    }
}
