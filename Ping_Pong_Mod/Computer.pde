class Computer {
  float xposC; int yposC;
  color paddlecolor = color(#FFFFFF);
 
  Computer(int screen_y) {
    xposC=SCREENX/2;
    yposC=screen_y;
  }
  void move(float x){
    if (xposC + PADDLEWIDTH/2<x) xposC+=speed; //<>//
    else {
      xposC-=speed; //<>//
    }
  }
  void draw() {
    fill(paddlecolor);
    rect(xposC, yposC, PADDLEWIDTH, PADDLEHEIGHT);
  }
}
