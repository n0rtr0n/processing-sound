package Animations;

import LightPanelSystem.LightPanelSystem;
import processing.sound.FFT;
import processing.sound.AudioIn;

public class AudioTest2 implements Animation {
    private LightPanelSystem applet;

    AudioIn in;
    FFT fft;
    float x1, x2, y1, y2, y3;
    int width, height;
    int bands = 64;
    float[] spectrum = new float[bands];

    public AudioTest2(LightPanelSystem applet) {
        this.applet = applet;
    }

    // TODO: fix malloc_error that occasionally pops up
    public void play() {
        applet.background(0);

        fft.analyze(spectrum);

        for(int i = 0; i < bands; i++){
            // The result of the FFT is normalized
            // draw the line for frequency band i scaling it up by 5 to get more amplitude.
            applet.stroke(255);
            x1 = (width / bands) * i;
            y1 = height / 2;
            x2 = x1;
            y2 = (height / 2) - spectrum[i] * (height / 2) * 15;
            y3 = (height / 2) + spectrum[i] * (height / 2) * 15;
            applet.line(x1, y1, x2, y2);
            applet.line(x1, y1, x2, y3);
        }
    }

    public ColorMode getColorMode() {
        return ColorMode.RGB;
    }

    public void setup() {
        // Create an Input stream which is routed into the Amplitude analyzer
        fft = new FFT(applet, bands);
        in = new AudioIn(applet, 0);
        width = applet.width;
        height = applet.height;
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