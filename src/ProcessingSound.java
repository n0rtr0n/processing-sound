import processing.core.PApplet;
import processing.core.PImage;
import processing.sound.*;

public class ProcessingSound extends PApplet{

    // OpenPixelControl client
    OPC opc;


    // state
    String state;

    // Declare the processing sound variables
    SoundFile sample;
    Amplitude rms;
    AudioIn in;


    // Audio transform 1


    // Declare a scaling factor
    float scale = 5.0f;

    // Declare a smooth factor
    float smoothFactor = 0.25f;

    // Used for smoothing
    float sum;



    // Audio transform 2
    FFT fft;
    int bands = 512;
    float[] spectrum = new float[bands];



    // Fire animation
    PImage fireImage;
    

    // Dots
    PImage dot;

    @Override
    public void settings() {
//        size(640, 360);
        size(800, 200);
    }

    @Override
    public void setup() {

        // Connect to the local instance of fcserver
        opc = new OPC(this, "127.0.0.1", 7890);

        setupFireAnimation();
        setuDots();


        float bottomCenter = width / 4;
        float topCenter = width - (width / 4);

        float spacing = width / 125;
        float bottomAngle = (float) Math.PI;
        float topAngle = 0;
        // Map one 64-LED strip to the center of the window
        opc.ledStrip(0, 64, bottomCenter, (float) (height * 0.20), spacing, bottomAngle, false);
        opc.ledStrip(64, 64, bottomCenter, (float) (height * 0.40), spacing, bottomAngle, false);
        opc.ledStrip(128, 64, bottomCenter, (float) (height * 0.60), spacing, bottomAngle, false);
        opc.ledStrip(192, 64, bottomCenter, (float) (height * 0.80), spacing, bottomAngle, false);
        opc.ledStrip(256, 64, topCenter, (float) (height * 0.20), spacing, topAngle, false);
        opc.ledStrip(320, 64, topCenter, (float) (height * 0.40), spacing, topAngle, false);
        opc.ledStrip(384, 64, topCenter, (float) (height * 0.60), spacing, topAngle, false);
        opc.ledStrip(448, 64, topCenter, (float) (height * 0.80), spacing, topAngle, false);


        // initialize to default state
        state = "default";

    }

    @Override
    public void draw() {

        switch(state) {
            case "default":
                defaultDraw();
                break;
            case "fireAnimation":
                drawFireAnimation();
                break;
            case "dotAnimation":
                drawDots();
                break;
            case "audio1":
                drawAudioTransform1();
                break;
            case "audio2":
                drawAudioTransform2();
                break;
            default:
                defaultDraw();
                break;

        }
    }

    public static void main (String... args) {
        ProcessingSound ps = new ProcessingSound();
        PApplet.runSketch(new String[]{"ProcessingSound"}, ps);
    }

    private void defaultDraw()
    {

    }

    private void setupFireAnimation()
    {
        // Load a sample image
        fireImage = loadImage("flames.jpeg");

        // Connect to the local instance of fcserver
        opc = new OPC(this, "127.0.0.1", 7890);
    }

    private void setuDots()
    {
        // Load a sample image
        dot = loadImage("color-dot.png");
    }

    private void setupAudioTransform1()
    {
        //Load and play a soundfile and loop it
        sample = new SoundFile(this, "TessellatedEscher.mp3");
        sample.loop();

        // Create and patch the rms tracker
        rms = new Amplitude(this);
        rms.input(sample);
    }

    private void setupAudioTransform2()
    {
        // Create an Input stream which is routed into the Amplitude analyzer
        fft = new FFT(this, bands);
        in = new AudioIn(this, 0);

        // start the Audio Input
        in.start();

        // patch the AudioIn
        fft.input(in);
    }


    private void drawFireAnimation()
    {
        // Scale the image so that it matches the width of the window
        int imHeight = fireImage.height * width / fireImage.width;

        // Scroll down slowly, and wrap around
        float speed = 0.05f;
        float y = (millis() * -speed) % imHeight;

        // Use two copies of the image, so it seems to repeat infinitely
        image(fireImage, 0, y, width, imHeight);
        image(fireImage, 0, y + imHeight, width, imHeight);
    }

    private void drawDots()
    {
        background(0);

        // Draw the image, centered at the mouse location
        double dotSize = width * 0.2;
        image(dot, (float) (mouseX - dotSize / 2) , (float) (mouseY - dotSize / 2), (float) dotSize, (float) dotSize);
    }

    private void drawAudioTransform1()
    {
        // Set background color, noStroke and fill color
        background(0, 0, 255);
        noStroke();
        fill(255, 0, 150);

        // Smooth the rms data by smoothing factor
        sum += (rms.analyze() - sum) * smoothFactor;

        // rms.analyze() return a value between 0 and 1. It's
        // scaled to height/2 and then multiplied by a scale factor
        float rmsScaled = sum * (height/2) * scale;

        // Draw an ellipse at a size based on the audio analysis
        ellipse(width/2, height/2, rmsScaled, rmsScaled);
    }

    private void drawAudioTransform2()
    {
        background(0);

        fft.analyze(spectrum);

        for(int i = 0; i < bands; i++){
            // The result of the FFT is normalized
            // draw the line for frequency band i scaling it up by 5 to get more amplitude.
            stroke(255);
            line( i, height, i * 2, height - spectrum[i] * height * 15 );
        }
    }
}