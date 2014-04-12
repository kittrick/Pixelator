import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class pixelator extends PApplet {

// Set Colors
int[] colors = new int[24];

// Get the Image
PImage photo;
float photoWidth;
float photoHeight;

public void setup() {
  
  // Defaults
  background(255);
  noStroke();
  
  // Load Image
  photo = loadImage("image.jpg");
  photoWidth = photo.width;
  photoHeight = photo.height;
  
  // Automatically Detect Photo Size
  size(photo.width, photo.height);
  
  // Select Color Palette
  selectColors();
}

public void selectColors(){
  for(int i = 0; i < colors.length; i++){
    colors[i] = color(random(255),random(255),random(255));
  }
}

public void randomColor() {
  int nextColor = PApplet.parseInt(random(colors.length));
  fill(colors[nextColor]);
}

public int pixelCompare(int pixel){
  float colorDistance[][] = new float[colors.length][2];
  for(int i = 0; i < colors.length; i++){
    float red1 = red(pixel);
    float red2 = red(colors[i]);
    float green1 = green(pixel);
    float green2 = green(colors[i]);
    float blue1 = blue(pixel);
    float blue2 = blue(colors[i]);
    colorDistance[i][0] = colors[i];
    colorDistance[i][1] = deltaE(red1, red2, green1, green2, blue1, blue2);
  }
  float lowestDistance = 99999999.99f;
  int colorChoice = 0xffFFFFFF;
  for(int i = 0; i < colors.length; i++){
    if(colorDistance[i][1] < lowestDistance){
      lowestDistance = colorDistance[i][1];
      colorChoice = colors[i];
    }
  }
  return colorChoice;
}

public void draw() {
  // No Looping
  noLoop();
  
  // Draw Image
  background(255);
  int dimension = photo.width*photo.height;
  for(int i = 0; i < dimension; i++){
      photo.pixels[i] = pixelCompare(photo.pixels[i]);
  }
  photo.updatePixels();
  image(photo,0,0);
}

// Exit on Mouse Click or Key Press
public void mousePressed() {
  // Save as PNG
  save("desktop.png");
  println("Finished.");
  exit();
}
public void keyPressed() {
  // Save as PNG
  save("desktop.png");
  println("Finished.");
  exit();
}
// Convert RGB to XYZ colorspace
public float[] convertXYZ(float r, float g, float b){
  // Same values from 0 to 1
  r = r/255;
  g = g/255;
  b = b/255;
  
  // adjusting values
  if(r>0.04045f){
       r = (r+0.055f)/1.055f;
       r = pow(r,2.4f);
  }
  else{
       r = r/12.92f;
  }
  if(g>0.04045f){
       g = (g+0.055f)/1.055f;
       g = pow(g,2.4f);     
  }
  else{
       g = g/12.92f;
  }
  if(b>0.04045f){
       b = (b+0.055f)/1.055f;
       b = pow(b,2.4f);     
  }
  else{
       b = b/12.92f;
  }
  
  r *= 100;
  g *= 100;
  b *= 100;
  
  // applying the matrix
  float x = r * 0.4124f + g * 0.3576f + b * 0.1805f;
  float y = r * 0.2126f + g * 0.7152f + b * 0.0722f;
  float z = r * 0.0193f + g * 0.1192f + b * 0.9505f;
  
  // displaying the values
  float[] output = new float[3];
  output[0] = x;
  output[1] = y;
  output[2] = z;
  return output;
}

// Compute Delta E value http://en.wikipedia.org/wiki/Color_difference#CIE76
public float deltaE( float r1, float r2, float g1, float g2, float b1, float b2){
  float deltaE;
  float[] xyz1 = convertXYZ(r1,g1,b1);
  float[] xyz2 = convertXYZ(r2,g2,b2);
  deltaE = sqrt(pow(xyz1[0]-xyz2[0],2)+pow(xyz1[1]-xyz2[1],2)+pow(xyz1[2]-xyz2[2],2));
  return deltaE;
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--full-screen", "--bgcolor=#666666", "--stop-color=#cccccc", "pixelator" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
