class Alien {
  float x;
  float y;
  int dx;
  int dy;
  int i =0;
  int numberofAliens;
  boolean offscreen = true;
  float time;
  PImage alien;
  PImage destruction;

  Alien(float xpos, float ypos, PImage alienImage, PImage boomImage, int number) {
    x = xpos;
    y = ypos;
    dx = 2;
    dy = 2;
    i =0;
    alien = alienImage;
    destruction = boomImage;
    time = random(1000, 10000);
    numberofAliens = number;
  }
  void draw() {
    image (alien, x, y);
    if (millis() >= time && offscreen) {
      if (dx != 0 || dy != 0) {
        bombArray[numberofAliens] = new Bomb (x, y, speed);  
        ofscreen();
      }
    }
    if (bombArray[numberofAliens] != null && alien != destruction) {
        bombArray[numberofAliens].draw();
        
        bombArray[numberofAliens].move();
        bombArray[numberofAliens].collide(thePlayer);
        
        ofscreen();
      }
    
  }
  void move() {
    if (i==0) {
      if (x < SCREENX - 30) {
        x += dx;
      } else {
        y += 25;
        i=1;
      }
    }

    if (i == 1) {
      if (x >= 1) x -= dx;
      else {
        y += 25;
        i=0;
      }
    }
  }
  void explosion() {
     alien = destruction;
     dx=0;
     dy=0;
     i=3;
     
  }
  
  void ofscreen() {
  if (bombArray[numberofAliens].ybomb >= SCREENY) {
    offscreen = true;
    }
  else offscreen = false;
  }
  
}
