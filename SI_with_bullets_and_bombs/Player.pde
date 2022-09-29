class Player {
  int xpos; int ypos;
  color paddlecolor = color(0);

  Player(int screen_y) {
    xpos=SCREENX/2;
    ypos=screen_y;
  }
  void move(int mouse_x){
    if(mouse_x>SCREENX-SHIPWIDTH) xpos = SCREENX-SHIPWIDTH;
    else xpos=mouse_x;
  }
  void draw() {
    fill(paddlecolor);
    rect(xpos, ypos,  SHIPWIDTH, SHIPHEIGHT);
  }
}
