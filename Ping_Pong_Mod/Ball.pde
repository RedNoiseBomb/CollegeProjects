class Ball {
float x; float y;
float dx; float dy;
int radius;
color ballColor = color(200, 100, 50);

Ball(){
    x = random(SCREENX/4, SCREENX/2);
    y = random(SCREENY/4, SCREENY/2);
    dx = 2; dy = 2;
    radius=5;
  }
void move(){
  x = x+dx; y = y+dy;
}
void draw(){
  fill(ballColor);
  ellipse(int(x), int(y), radius, radius);
}
void collide(Player tp){
  if(x-radius <=0) dx=-dx;
  
  else if(x+radius>=SCREENX) dx=-dx;
  
  if(y+radius >= tp.ypos && y-radius<tp.ypos+PADDLEHEIGHT &&
  x >=tp.xpos && x <=
  tp.xpos+PADDLEWIDTH && x + radius < PADDLEWIDTH/2){
  println("collided!");
  dy=-dy;
  dx=-dx;
  }
  else if (y+radius >= tp.ypos && y-radius<tp.ypos+PADDLEHEIGHT &&
  x >=tp.xpos && x <= tp.xpos+PADDLEWIDTH && x + radius >= PADDLEWIDTH/2) {
    dy=-dy;
   
  }
}
void collideComp (Computer tp) {
  if(y+radius >= tp.yposC && y-radius<tp.yposC+PADDLEHEIGHT &&
  x >=tp.xposC && x <=
  tp.xposC+PADDLEWIDTH){
  println("collided!");
  dy=-dy;
  }
}
}
