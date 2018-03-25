package Animations;

import LightPanelSystem.LightPanelSystem;
import ddf.minim.*;
import ddf.minim.analysis.FFT;

public class AudioTest3 implements Animation {
    private LightPanelSystem applet;

    Minim minim;
    AudioInput in;
    FFT fft;

    public AudioTest3(LightPanelSystem applet) {
        this.applet = applet;
    }

    public void play() {
//        applet.background(0);
//        applet.stroke(255);
//
//        // draw the waveforms so we can see what we are monitoring
//        for(int i = 0; i < in.bufferSize() - 1; i++)
//        {
//            applet.line( i, 50 + in.left.get(i)*50, i+1, 50 + in.left.get(i+1)*50 );
//            applet.line( i, 150 + in.right.get(i)*50, i+1, 150 + in.right.get(i+1)*50 );
//        }
//
//        String monitoringState = in.isMonitoring() ? "enabled" : "disabled";
//        applet.text( "Input monitoring is currently " + monitoringState + ".", 5, 15 );

        applet.background(0);
        // first perform a forward fft on one of song's buffers
        // I'm using the mix buffer
        //  but you can use any one you like
        fft.forward(in.mix);

        applet.stroke(255, 0, 0, 128);
        // draw the spectrum as a series of vertical lines
        // I multiple the value of getBand by 4
        // so that we can see the lines better
        for(int i = 0; i < fft.specSize(); i++)
        {
            applet.line(i, applet.height, (float)(i * (applet.width / fft.specSize())), applet.height - fft.getBand(i) * 4);
        }

        applet.stroke(255);
        // I draw the waveform by connecting
        // neighbor values with a line. I multiply
        // each of the values by 50
        // because the values in the buffers are normalized
        // this means that they have values between -1 and 1.
        // If we don't scale them up our waveform
        // will look more or less like a straight line.
        for(int i = 0; i < in.left.size() - 1; i++)
        {
            applet.line(i, 50 + in.left.get(i)*50, i+1, 50 + in.left.get(i+1)*50);
            applet.line(i, 150 + in.right.get(i)*50, i+1, 150 + in.right.get(i+1)*50);
        }
    }

    public ColorMode getColorMode() {
        return ColorMode.RGB;
    }

    public void setup() {
        minim = new Minim(applet);
        // use the getLineIn method of the Minim object to get an AudioInput
        in = minim.getLineIn();

        // an FFT needs to know how
        // long the audio buffers it will be analyzing are
        // and also needs to know
        // the sample rate of the audio it is analyzing
        fft = new FFT(in.bufferSize(), in.sampleRate());
    }

    public void prepare() {

    }

    public void cleanup() {

    }
}