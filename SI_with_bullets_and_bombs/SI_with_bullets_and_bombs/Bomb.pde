class Bomb { //<>// //<>// //<>//
  float xbomb;
  float ybomb;
  float dxbomb;
  float dybomb = 1;
  int radius;
  color bombColor = color (150, 75, 25);
 

  Bomb (float x, float y, int speed) {
    xbomb = x;
    ybomb = y;
    radius = 7;
    dybomb = speed;
  }

  void move() {
    ybomb += dybomb;
  }

  void draw() {
    fill(bombColor);
    ellipse(xbomb, ybomb, radius, radius);
  }



  void collide(Player tp) {
    if (ybomb+radius >= tp.ypos  && ybomb+radius < tp.ypos + 50 && xbomb < tp.xpos + 20 && xbomb > tp.xpos ) {
      collided = true;
      for (int j = 0; j < alienArray.length; j++) {
        alienArray[j].dx = 0;
        alienArray[j].dy = 0;
      }
      speed = 0;
    }
  }
}
