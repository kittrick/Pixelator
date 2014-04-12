// Convert RGB to XYZ colorspace
float[] convertXYZ(float r, float g, float b){
  // Same values from 0 to 1
  r = r/255;
  g = g/255;
  b = b/255;
  
  // adjusting values
  if(r>0.04045){
       r = (r+0.055)/1.055;
       r = pow(r,2.4);
  }
  else{
       r = r/12.92;
  }
  if(g>0.04045){
       g = (g+0.055)/1.055;
       g = pow(g,2.4);     
  }
  else{
       g = g/12.92;
  }
  if(b>0.04045){
       b = (b+0.055)/1.055;
       b = pow(b,2.4);     
  }
  else{
       b = b/12.92;
  }
  
  r *= 100;
  g *= 100;
  b *= 100;
  
  // applying the matrix
  float x = r * 0.4124 + g * 0.3576 + b * 0.1805;
  float y = r * 0.2126 + g * 0.7152 + b * 0.0722;
  float z = r * 0.0193 + g * 0.1192 + b * 0.9505;
  
  // displaying the values
  float[] output = new float[3];
  output[0] = x;
  output[1] = y;
  output[2] = z;
  return output;
}

// Compute Delta E value http://en.wikipedia.org/wiki/Color_difference#CIE76
float deltaE( float r1, float r2, float g1, float g2, float b1, float b2){
  float deltaE;
  float[] xyz1 = convertXYZ(r1,g1,b1);
  float[] xyz2 = convertXYZ(r2,g2,b2);
  deltaE = sqrt(pow(xyz1[0]-xyz2[0],2)+pow(xyz1[1]-xyz2[1],2)+pow(xyz1[2]-xyz2[2],2));
  return deltaE;
}
