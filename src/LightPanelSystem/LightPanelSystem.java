package LightPanelSystem;

import Animations.*;
import processing.core.PApplet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import hype.*;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class LightPanelSystem extends PApplet{

    // OpenPixelControl client
    OPC opc;

    Animation previousAnimation;
    Animation currentAnimation;

    Map<String, Animation> animations;

    // state management
    String state;
    String previousState;
    WipeUp wipeUpAnimation;

    ColorWheel colorWheel;


    // TODO: register strings automatically
    // TODO: register keys automatically
    // TODO: register button modifiers
    // TODO: HTTP server / websocket
    // TODO: Implement blackout, audio transform animations
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
    final String HYPE_TEST = "hypeTest";

    int currentStateStartedAtMs = 0;
    int currentTimerMillis = 0;
    int timerDifference = 0;



    boolean whiteLatched = false;
    boolean redLatched = false;


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

        animations = new HashMap<String, Animation>();

        animations.put(FIRE_ANIMATION, new FireAnimation(this));
        animations.put(RAINBOW_SLOW_ANIMATION, new LongRainbowFade(this, colorWheel));
        animations.put(RAINBOW_FAST_ANIMATION, new FastRainbowFade(this, colorWheel));
        animations.put(DOTS_ANIMATION, new Dots(this));
        animations.put(BUBBLE_SINE_ANIMATION, new BubbleSine(this));
        animations.put(SPIRAL_ANIMATION, new Spiral(this));
        animations.put(TRIPPY_TRIANGLES_ANIMATION, new TrippyTriangles(this));
        animations.put(WIPE_UP,  new WipeUp(this));
        animations.put(MOVIE, new MovieTest(this));
        animations.put(HYPE_TEST, new HypeTest(this));

        H.init(this);

        for (Map.Entry<String, Animation>  animation : animations.entrySet()) {
            animation.getValue().setup();
        }

        resetTimer();

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
        currentAnimation = animations.get(FIRE_ANIMATION);
        currentAnimation.prepare();
    }

    @Override
    public void draw() {

        currentAnimation.play();

//            case BLACKOUT:
//                if (whiteLatched == true) {
//                    background(255, 255, 255);
//                } else if (redLatched == true) {
//                    background(255,0,0);
//                } else {
//                    background(0,0,0);
//                }
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

    private void switchToState(String newState)
    {
        if (state == newState) {
            return;
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
            //case CAMERA:
            case WIPE_UP:
            case MOVIE:
            case HYPE_TEST:
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
        previousAnimation = currentAnimation;
        previousAnimation.cleanup();
        currentAnimation = animations.get(newState);
        currentAnimation.prepare();
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
            case 'S':
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
            case 'b':
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
            case 'h':
                switchToState(HYPE_TEST);
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