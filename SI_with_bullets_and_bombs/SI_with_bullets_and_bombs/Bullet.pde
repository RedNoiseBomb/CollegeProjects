class Bullet {
  float x; float y;
  float dx; float dy;
  int radius;
  color bulletColor = color (200, 100, 50);
  
  Bullet(float xpos, float ypos) {
    x = xpos+SHIPWIDTH/2;
    y = ypos+radius;
    dy = 8;
    radius = 5;
  }
  void move() {
    y = y - dy;
  }
  void draw() {
    fill(bulletColor);
    ellipse(x, y, radius, radius);
  }
  
  void collide(Alien tp) {
    if (y+radius >= tp.y  && y+radius < tp.y + 25 && x < tp.x + 30 && x > tp.x ) {
      tp.explosion();
    }
  }
}
