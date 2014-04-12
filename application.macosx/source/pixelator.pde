// Set Colors
color[] colors = new color[24];

// Get the Image
PImage photo;
float photoWidth;
float photoHeight;

void setup() {
  
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

void selectColors(){
  for(int i = 0; i < colors.length; i++){
    colors[i] = color(random(255),random(255),random(255));
  }
}

void randomColor() {
  int nextColor = int(random(colors.length));
  fill(colors[nextColor]);
}

color pixelCompare(color pixel){
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
  float lowestDistance = 99999999.99;
  color colorChoice = #FFFFFF;
  for(int i = 0; i < colors.length; i++){
    if(colorDistance[i][1] < lowestDistance){
      lowestDistance = colorDistance[i][1];
      colorChoice = colors[i];
    }
  }
  return colorChoice;
}

void draw() {
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
void mousePressed() {
  // Save as PNG
  save("desktop.png");
  println("Finished.");
  exit();
}
void keyPressed() {
  // Save as PNG
  save("desktop.png");
  println("Finished.");
  exit();
}
