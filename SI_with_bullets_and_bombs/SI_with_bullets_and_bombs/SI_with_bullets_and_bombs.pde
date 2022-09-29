PImage invader;
PImage explosion;
Alien alienArray[];
Bomb bombArray[];
Player thePlayer;
Bullet theBullet;

void settings() {
  size(SCREENX, SCREENY);
}

void setup() {
  thePlayer = new Player(SCREENY-MARGIN-SHIPHEIGHT);
  ellipseMode(RADIUS);
  invader = loadImage("invaders.gif");
  explosion = loadImage("exploding.gif");
  alienArray = new Alien[10];
  bombArray = new Bomb[10];
  init_array(alienArray);
}

void init_array (Alien alienArray[]) {
  int f = 0;
  int m = 0;
  for (int k = 0; k < alienArray.length; k++) {
    alienArray[k] = new Alien(-200 + f, random(0, SCREENY-200), invader, explosion, m);
    m++;
    f += 60;
  }
}


void draw() {
  frameRate(90);
  background(255);
  thePlayer.move(mouseX);
  thePlayer.draw();
  if (trigger == 1) {
    theBullet.draw();
    theBullet.move();
  }
  for (int s = 0; s < alienArray.length; s++) {
    alienArray[s].draw();
    if (boom == 1) {
      theBullet.collide(alienArray[s]);
    }
    alienArray[s].move();
  }
  if (collided) {
    PFont myFont = loadFont("Candara-Light-48.vlw");
    textFont(myFont);
    text("Game Over", SCREENX/2-110, SCREENY/2);
    gamble = false;
  }
  if (gamble) {
    for (int k =0; k < 10; k++) {
      if (alienArray[k].dx == 0 && alienArray[k].dy == 0) {
        killcount++;
      }
    }
    
    if (killcount != 10) {
      killcount=0;
    }
    else if (killcount >= 10) {
      change = true;
      killcount = 10;
    }
    if (change) {
      PFont myFont = loadFont("Candara-Light-48.vlw");
      textFont(myFont);
      text("You win!", SCREENX/2-100, SCREENY/2);
    }
  }
}

void mousePressed() {
  boom = 1;
  theBullet = new Bullet(thePlayer.xpos, thePlayer.ypos);
  trigger = 1;
}
