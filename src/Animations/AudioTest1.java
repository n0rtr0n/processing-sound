package Animations;

import LightPanelSystem.LightPanelSystem;
import processing.sound.SoundFile;
import processing.sound.Amplitude;


public class AudioTest1 implements Animation {
    private LightPanelSystem applet;
    // Declare a scaling factor
    float scale = 5.0f;

    // Declare a smooth factor
    float smoothFactor = 0.25f;

    // Used for smoothing
    float sum;

    SoundFile sample;
    Amplitude rms;

    public AudioTest1(LightPanelSystem applet) {
        this.applet = applet;
    }

    public void play() {
        // Set background color, noStroke and fill color
        applet.background(0, 0, 255);
        applet.noStroke();
        applet.fill(255, 0, 150);

        // Smooth the rms data by smoothing factor
        sum += (rms.analyze() - sum) * smoothFactor;

        // rms.analyze() return a value between 0 and 1. It's
        // scaled to height/2 and then multiplied by a scale factor
        float rmsScaled = sum * (applet.height/2) * scale;

        // Draw an ellipse at a size based on the audio analysis
        applet.ellipse(applet.width/2, applet.height/2, rmsScaled, rmsScaled);
    }

    public ColorMode getColorMode() {
        return ColorMode.RGB;
    }

    public void setup() {
        // TODO: only load when switched to
        //Load and play a soundfile and loop it
        sample = new SoundFile(applet, "TessellatedEscher.mp3");

        //Create and patch the rms tracker
        rms = new Amplitude(applet);
        rms.input(sample);
    }

    public void prepare() {
        sample.loop();
    }

    public void cleanup() {
        sample.stop();
    }
}