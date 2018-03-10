package LightPanelSystem;

import Animations.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.video.*;
//import processing.sound.*;

import java.io.IOException;
import java.io.OutputStream;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class LightPanelSystem extends PApplet{

    // OpenPixelControl client
    OPC opc;

    // state management
    String state;
    String previousState;
    FireAnimation fireAnimation;
    LongRainbowFade longRainbowFadeAnimation;
    FastRainbowFade fastRainbowFadeAnimation;
    BubbleSine bubbleSineAnimation;
    Spiral spiralAnimation;
    TrippyTriangles trippyTrianglesAnimation;
    WipeUp wipeUpAnimation;
    MovieTest movieAnimation;

    ColorWheel colorWheel;

    Capture cam;

    final String DEFAULT_ANIMATION = "defaultAnimation";
    final String FIRE_ANIMATION = "fireAnimation";
    final String DOTS_ANIMATION = "dotsAnimation";
    final String SPIRAL_ANIMATION = "spiralAnimation";
    final String PSYCH_CUBE_ANIMATION = "psychCubeAnimation";
    final String BUBBLE_SINE_ANIMATION = "bubbleSineAnimation";
    final String RAINBOW_SLOW_ANIMATION = "rainbowSlowAnimation";
    final String RAINBOW_FAST_ANIMATION = "rainbowFastAnimation";
    final String TRIPPY_TRIANGLES_ANIMATION = "trippyTrianglesAnimation";
    final String BLACKOUT = "blackout";
    final String CAMERA = "camera";
    final String WIPE_UP = "wipeUp";
    final String MOVIE = "movie";

    final int ANIMATION_LENGTH = 20 * 1000;

    boolean fireAnimationInitialized = false;
    boolean dotsInitialized = false;
    boolean audioTransform1Initialized = false;
    boolean initialized = false;


    boolean stateChangeRequested = false;


    int currentStateStartedAtMs = 0;
    int currentTimerMillis = 0;
    int timerDifference = 0;

    // Declare the processing sound variables
//    SoundFile sample;
//    Amplitude rms;
//    AudioIn in;


    // Audio transform 1

    // Declare a scaling factor
    float scale = 5.0f;

    // Declare a smooth factor
    float smoothFactor = 0.25f;

    // Used for smoothing
    float sum;

    boolean whiteLatched = false;
    boolean redLatched = false;


    // Audio transform 2
//    FFT fft;
//    int bands = 512;
//    float[] spectrum = new float[bands];

    // Dots
    PImage dot;


    // Psych cubes
//    int div=16;
//    int t=0;
//    float du, r;

    @Override
    public void settings() {
//        size(640, 360);
        // size(800, 200, P3D);
        // TODO: figure out OpenGLException issue
        size(800, 200);
    }

    @Override
    public void setup() {

        colorWheel = new ColorWheel();
        colorWheel.setLastColorChangeAt(millis());
        colorWheel.setRed(0);
        // Connect to the local instance of fcserver
        opc = new OPC(this, "127.0.0.1", 7890);

        setupFireAnimation();



        longRainbowFadeAnimation = new LongRainbowFade(this, colorWheel);
        fastRainbowFadeAnimation = new FastRainbowFade(this, colorWheel);
        bubbleSineAnimation = new BubbleSine(this);
        spiralAnimation = new Spiral(this);
        trippyTrianglesAnimation = new TrippyTriangles(this);
        wipeUpAnimation = new WipeUp(this);
        movieAnimation = new MovieTest(this);


        trippyTrianglesAnimation.setup();

        setuDots();
//        setupAudioTransform1();
//        setupAudioTransform2();
        setupPsychCubes();

        resetTimer();


        //TODO: make this work if a webcam is not plugged in
//        cam = new Capture(this, Capture.list()[1]);
//        cam.start();

//        float bottomCenter = width / 4;
//        float topCenter = width - (width / 4);
//
//        float spacing = width / 125;
//        float bottomAngle = (float) Math.PI;
//        float topAngle = 0;
//        // Map one 64-LED strip to the center of the window
//        opc.ledStrip(0, 64, bottomCenter, (float) (height * 0.20), spacing, bottomAngle, false);
//        opc.ledStrip(64, 64, bottomCenter, (float) (height * 0.40), spacing, bottomAngle, false);
//        opc.ledStrip(128, 64, bottomCenter, (float) (height * 0.60), spacing, bottomAngle, false);
//        opc.ledStrip(192, 64, bottomCenter, (float) (height * 0.80), spacing, bottomAngle, false);
//        opc.ledStrip(256, 64, topCenter, (float) (height * 0.20), spacing, topAngle, false);
//        opc.ledStrip(320, 64, topCenter, (float) (height * 0.40), spacing, topAngle, false);
//        opc.ledStrip(384, 64, topCenter, (float) (height * 0.60), spacing, topAngle, false);
//        opc.ledStrip(448, 64, topCenter, (float) (height * 0.80), spacing, topAngle, false);

        float center = width/2;
        float spacing = width / 64;
        float angle = 0;
        opc.ledStrip(0, 60, center, (float) (height * 0.11), spacing, angle, false);
        opc.ledStrip(64, 60, center, (float) (height * 0.22), spacing, angle, false);
        opc.ledStrip(128, 60, center, (float) (height * 0.33), spacing, angle, false);
        opc.ledStrip(192, 60, center, (float) (height * 0.44), spacing, angle, false);
        opc.ledStrip(256, 60, center, (float) (height * 0.55), spacing, angle, false);
        opc.ledStrip(320, 60, center, (float) (height * 0.66), spacing, angle, false);
        opc.ledStrip(384, 60, center, (float) (height * 0.77), spacing, angle, false);
        opc.ledStrip(448, 60, center, (float) (height * 0.88), spacing, angle, false);

        // initialize to default state
        state = DEFAULT_ANIMATION;

        currentStateStartedAtMs = millis();

    }

    @Override
    public void draw() {

        switch(state) {
            case DEFAULT_ANIMATION:
                //defaultDraw();
                fireAnimation.draw(); // setting fire animation to default for now
                break;
            case FIRE_ANIMATION:
                fireAnimation.draw();
                break;
            case DOTS_ANIMATION:
                drawDots();
                break;
            case SPIRAL_ANIMATION:
                spiralAnimation.play();
                break;
            case PSYCH_CUBE_ANIMATION:
                drawPsychCubeAnimation();
                break;
            case BUBBLE_SINE_ANIMATION:
                bubbleSineAnimation.play();
                break;
            case RAINBOW_SLOW_ANIMATION:
                longRainbowFadeAnimation.play();
                break;
            case RAINBOW_FAST_ANIMATION:
                fastRainbowFadeAnimation.play();
                break;
            case TRIPPY_TRIANGLES_ANIMATION:
                trippyTrianglesAnimation.play();
                break;
            case BLACKOUT:
                if (whiteLatched == true) {
                    background(255, 255, 255);
                } else if (redLatched == true) {
                    background(255,0,0);
                } else {
                    background(0,0,0);
                }
                break;
            case CAMERA:
                if(cam.available()) {
                    cam.read();
                    //cam.loadPixels();
                }
                image(cam, 0, 0);
                break;
            case WIPE_UP:
                wipeUpAnimation.play();
                break;
            case MOVIE:
                movieAnimation.play();
                break;
//            case "audio1":
//                drawAudioTransform1();
//                break;
//            case "audio2":
//                drawAudioTransform2();
//                break;
            default:
                fireAnimation.draw();
                break;
        }
    }

    public static void main (String... args) {
        try {
//            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
//            server.createContext("/test", new MyHandler());
//            server.setExecutor(null); // creates a default executor
//            server.start();

            LightPanelSystem ps = new LightPanelSystem();
            PApplet.runSketch(new String[]{"LightPanelSystem"}, ps);

        } catch (Exception e) {

        }
    }

    private void setupFireAnimation()
    {
        fireAnimation = new FireAnimation(this);
        fireAnimation.setup("flames.jpeg");
    }

    private void setuDots()
    {
        // Load a sample image
        dot = loadImage("color-dot.png");
    }

    private void setupAudioTransform1()
    {
        // TODO: only load when switched to
        //Load and play a soundfile and loop it
//        sample = new SoundFile(this, "TessellatedEscher.mp3");
//        sample.loop();

        // Create and patch the rms tracker
//        rms = new Amplitude(this);
//        rms.input(sample);
    }

//    private void setupAudioTransform2()
//    {
//        // Create an Input stream which is routed into the Amplitude analyzer
//        fft = new FFT(this, bands);
//        in = new AudioIn(this, 0);
//
//        // start the Audio Input
//        in.start();
//
//        // patch the AudioIn
//        fft.input(in);
//    }

    private void setupPsychCubes()
    {
        float du = max(width, height)/2;
        float r = du/16;
    }

    private void drawDots()
    {
        background(0);

        // Draw the image, centered at the mouse location
        double dotSize = width * 0.2;
        image(dot, (float) (mouseX - dotSize / 2) , (float) (mouseY - dotSize / 2), (float) dotSize, (float) dotSize);
    }

//    private void drawAudioTransform1()
//    {
//        // Set background color, noStroke and fill color
//        background(0, 0, 255);
//        noStroke();
//        fill(255, 0, 150);
//
//        // Smooth the rms data by smoothing factor
//        sum += (rms.analyze() - sum) * smoothFactor;
//
//        // rms.analyze() return a value between 0 and 1. It's
//        // scaled to height/2 and then multiplied by a scale factor
//        float rmsScaled = sum * (height/2) * scale;
//
//        // Draw an ellipse at a size based on the audio analysis
//        ellipse(width/2, height/2, rmsScaled, rmsScaled);
//    }
//
//    private void drawAudioTransform2()
//    {
//        background(0);
//
//        fft.analyze(spectrum);
//
//        for(int i = 0; i < bands; i++){
//            // The result of the FFT is normalized
//            // draw the line for frequency band i scaling it up by 5 to get more amplitude.
//            stroke(255);
//            line( i, height, i * 2, height - spectrum[i] * height * 15 );
//        }
//    }

    private void drawPsychCubeAnimation()
    {
//        t++;
//        background(t%256, 255, 192+64*cos(t*PI/12));
//
//        translate(width/2, height/2, -du);
//
//        rotateZ(t*PI/360);
//        for (int k=0; k<14; k++) {
//            rotateZ(t * PI / 1200);
//
//            for (int i = 0; i < div; i++) {
//                rotateZ(2 * PI / div);
//
//                pushMatrix();
//                translate(k * r, (float) (1.2 * r + k * k * r * 0.25), 0);
//
//                //立方体
//                pushMatrix();
//                rotateY(k * 2 * PI / div + t * PI / 48);
//                fill(i * 255 / div, 216, 216, 128);
//                strokeWeight(du / 384);
//                stroke(255, 48);
//                box((float) (r * 1.56 + k * r / 2.6));
//                popMatrix();
//
//                //直線
//                pushMatrix();
//                translate(0, 0, du / 4);
//                strokeWeight(du / 280);
//                stroke(255, 56);
//                line(0, 0, 0, -16 * du);
//                popMatrix();
//
//                popMatrix();
//            }
//        }
    }

    private void defaultCleanup()
    {

    }

    private void switchToState(String newState)
    {
        if (state == newState) {
            return;
        }

        // clean up old state
        switch (state) {
            default:
                defaultCleanup();
                break;
        }

        currentStateStartedAtMs = millis();

        // TODO: implement pre-state changes
        switch(newState) {
            case DEFAULT_ANIMATION:
            case FIRE_ANIMATION:
            case DOTS_ANIMATION:
            case SPIRAL_ANIMATION:
            case PSYCH_CUBE_ANIMATION:
            case BUBBLE_SINE_ANIMATION:
            case TRIPPY_TRIANGLES_ANIMATION:
            case BLACKOUT:
            case CAMERA:
            case WIPE_UP:
            case MOVIE:
                colorMode(RGB);
                break;
            case RAINBOW_SLOW_ANIMATION:
            case RAINBOW_FAST_ANIMATION:
                colorMode(HSB);
                break;
            default:
                return; // just return if we don't have an authorized state

        }

        previousState = state;
        state = newState;
    }

    private int timerDiff()
    {
        return millis() - currentTimerMillis;
    }

    private void resetTimer()
    {
        currentTimerMillis = millis();
    }

    public int msSinceCurrentStateStarted()
    {
        return millis() - currentStateStartedAtMs;
    }

    public int msSince(int timestamp)
    {
        return millis() - timestamp;
    }

    static class MyHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "This is the response";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public void keyPressed() {

        switch (key) {
            case 'f':
                switchToState(FIRE_ANIMATION);
                break;
            case '0':
                switchToState(DEFAULT_ANIMATION);
                break;
            case 'd':
                switchToState(DOTS_ANIMATION);
                break;
            case 's':
                switchToState(SPIRAL_ANIMATION);
                break;
            case 'b':
                switchToState(BUBBLE_SINE_ANIMATION);
                break;
            case 'B':
                switchToState(BLACKOUT);
                break;
            case 'r':
                if (state == BLACKOUT) {
                    redLatched = true;
                } else {
                    switchToState(RAINBOW_SLOW_ANIMATION);
                }
                break;
            case 'R':
                switchToState(RAINBOW_FAST_ANIMATION);
                break;
            case 't':
                switchToState(TRIPPY_TRIANGLES_ANIMATION);
                break;
            case 'w':
                if (state == BLACKOUT) {
                    whiteLatched = true;
                }
                break;
            case 'C':
                switchToState(CAMERA);
                break;
            case 'W':
                wipeUpAnimation.prepare();
                switchToState(WIPE_UP);
                break;
            case 'n':
                if (state == WIPE_UP) {
                    wipeUpAnimation.initiateWipeUp();
                }
                break;
            case 'm':
                switchToState(MOVIE);
                break;
            default:
                clearLatches();
                break;
        }
    }

    public void keyReleased()
    {
        clearLatches();
    }

    public void clearLatches()
    {
        whiteLatched = false;
        redLatched = false;
    }
}