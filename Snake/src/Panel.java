import java.awt.*; //Import packages etc.
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class Panel extends JPanel implements ActionListener{            //main class implements is abstract, Jpanel is inherited

    static final int SCREEN_WIDTH = 1300;    //variables
    static final int SCREEN_HEIGHT = 750;
    static final int UNIT_SIZE = 50;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
    static final int DELAY = 175;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    static boolean gameOn = false;

    Panel(){   //sets panel size
        random = new Random(); //creates random int
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT)); //sets screen size
        this.setBackground(Color.black); //sets background to black
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame(); //starts the game by calling the startgame function
    }
    public void startGame() {
        newApple(); //calls the function to create new apple
        running = true; //checks if the game is running
        timer = new Timer(DELAY,this); //creates timer
        timer.start(); //starts the timer
    }
    public void pause() { //class for gameOn stops the game
        Panel.gameOn = true;
        timer.stop();
    }

    public void resume() { //resumes the game 
        Panel.gameOn = false;
        timer.start();
    }

    public void paintComponent(Graphics g) { //graphic set up
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {

        if(running) { //checks if the game is running
			/*
			for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			}
			*/
            g.setColor(Color.red); //sets color of apple
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE); //size of the apple

            for(int i = 0; i< bodyParts;i++) { //creates the head of the snake
                if(i == 0) { //creates this when the if == 0 so the first body part created is different color
                    g.setColor(Color.green); //sts color
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE); //sets size
                }
                else { //continues with this to get differentation between the head and body
                    g.setColor(new Color(45,180,0)); //sets the body part color change values acording to "java color codes"
                    //g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);//sets body part size == to head
                }
            }
            g.setColor(Color.white); //color of the score
            g.setFont( new Font("Ink Free",Font.BOLD, 40)); //font
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize()); //writes score
        }
        else {
            gameOver(g); //ends game if the running=false
        }

    }
    public void newApple(){ //creates apple
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;//random pixel on screen in the y axys
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE; //random pixel on screen in the x axys
    }
    public void move(){ //player controls
        for(int i = bodyParts;i>0;i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch(direction) { //controls can edit to change them
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }
    public void checkApple() {// increases score + spoawns 1 more bodypart
        if((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }
    public void checkCollisions() { //checks for collision ends game if the player hits his body or edge of the map
        //checks if head collides with body
        for(int i = bodyParts;i>0;i--) {
            if((x[0] == x[i])&& (y[0] == y[i])) {
                running = false;
            }
        }
        //check if head touches left border
        if(x[0] < 0) {
            running = false;
        }
        //check if head touches right border
        if(x[0] > SCREEN_WIDTH) {
            running = false;
        }
        //check if head touches top border
        if(y[0] < 0) {
            running = false;
        }
        //check if head touches bottom border
        if(y[0] > SCREEN_HEIGHT) {
            running = false;
        }

        if(!running) {
            timer.stop();
        }
    }
    public void gameOver(Graphics g) { //creates the game over screen
        //Score
        g.setColor(Color.red); //sets color
        g.setFont( new Font("Ink Free",Font.BOLD, 40)); //sets font
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, g.getFont().getSize());
        //Game Over text
        g.setColor(Color.red);
        g.setFont( new Font("Ink Free",Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2); //writes game over in the set up color and font
    }
    @Override //overides this class
    public void actionPerformed(ActionEvent e) {

        if(running) { //performs the game
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) { //player movement controls
            switch(e.getKeyCode()) { //switch to await button presses
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_SPACE:// game pauses
                    if(Panel.gameOn) {
                        resume();
                    } else {
                        pause();
                    }
                    break;
            }
        }

    }
}