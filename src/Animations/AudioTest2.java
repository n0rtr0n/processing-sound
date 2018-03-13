package Animations;

import LightPanelSystem.LightPanelSystem;
import processing.sound.FFT;
import processing.sound.AudioIn;

public class AudioTest2 implements Animation {
    private LightPanelSystem applet;

    AudioIn in;
    FFT fft;
    int bands = 512;
    float[] spectrum = new float[bands];

    public AudioTest2(LightPanelSystem applet) {
        this.applet = applet;
    }

    public void play() {
        applet.background(0);

        fft.analyze(spectrum);

        for(int i = 0; i < bands; i++){
            // The result of the FFT is normalized
            // draw the line for frequency band i scaling it up by 5 to get more amplitude.
            applet.stroke(255);
            applet.line( i, applet.height, i * 2, applet.height - spectrum[i] * applet.height * 15 );
        }
    }

    public ColorMode getColorMode() {
        return ColorMode.RGB;
    }

    public void setup() {
        // Create an Input stream which is routed into the Amplitude analyzer
        fft = new FFT(applet, bands);
        in = new AudioIn(applet, 0);
    }

    public void prepare() {
        // start the Audio Input
        in.start();

        // patch the AudioIn
        fft.input(in);
    }

    public void cleanup() {
        in.stop();
    }
}