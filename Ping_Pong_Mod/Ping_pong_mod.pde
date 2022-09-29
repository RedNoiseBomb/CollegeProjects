Player thePlayer; //<>// //<>//
Computer theComputer;
Ball theBall;
void settings() {
  size(SCREENX, SCREENY);
}
void setup() {
  thePlayer = new Player(SCREENY-MARGIN-PADDLEHEIGHT);
  theBall = new Ball();
  theComputer = new Computer(0+MARGIN);
  ellipseMode(RADIUS);
}
void draw() {
  frameRate(120);
  background(0);
  thePlayer.move(mouseX);
  theBall.move();
  theComputer.move(theBall.x);
  theBall.collide(thePlayer);
  theBall.collideComp(theComputer);
  reset();
  thePlayer.draw();
  theComputer.draw();
  theBall.draw();
  PFont myFont = loadFont("ArialMT-18.vlw");
  textFont(myFont);
  text(speed, SCREENX-300, SCREENY/2);
}
void reset() {
  if ( theBall.y+theBall.radius>SCREENY || theBall.y-theBall.radius<0) {
    if ( theBall.y+theBall.radius>SCREENY) {
      countP--;
      theBall.dx += speed;
      theBall.dy += speed;
    } else {
      theBall.dx += speed;
      theBall.dy += speed;
      countC--;
      speed+=0.25;
    }
    theBall.x = random(SCREENX/4, SCREENX/2);
    theBall.y = random(SCREENY/4, SCREENY/2);
    theBall.dx = 0;
    theBall.dy = 0;
  }
  if (countP == 0) {
    PFont myFont = loadFont("ArialMT-18.vlw");
    textFont(myFont);
    text("Game Over!", SCREENX/2-50, SCREENY/2);
    speed = 1;
  }

  if (countC == 0) {
    PFont myFont = loadFont("ArialMT-18.vlw");
    textFont(myFont);
    text("You won!", SCREENX/2-70, SCREENY/2);
    speed=1;
  }
}
void mousePressed() {
  if (countP == 0) {
    countP = 3;
    countC = 3;
  }
  if (countC == 0) {
    countC = 3;
    countP = 3;
  }
  theBall.dx = 2;
  theBall.dy = 2;
}
