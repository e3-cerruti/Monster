/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monster;

import java.io.*; 
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

public class Monster extends JFrame implements Runnable {
    static final int WINDOW_WIDTH = 1000;
    static final int WINDOW_HEIGHT = 700;
    final int XBORDER = 0;
    final int YBORDER = 0;
    final int YTITLE = 25;
    boolean animateFirstTime = true;
    int xsize = -1;
    int ysize = -1;
    Image image;
    Graphics2D g;

//Monster variables.
    int monsterX;
    int monsterY;
    double monsterRotate;
//Person variables.
    int personX;
    int personY;
//Cloud variables.
    int cloudX;
    int cloudY;
    int cloudDirection;
//Time variables.
    int frameRate = 20;
    int timeCount;
    
    static Monster frame;
    public static void main(String[] args) {
        frame = new Monster();
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public Monster() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.BUTTON1 == e.getButton()) {
                    //left button


// location of the cursor.
                    int xpos = e.getX();
                    int ypos = e.getY();

                }
                if (e.BUTTON3 == e.getButton()) {
                    //right button
                    reset();
                }
                repaint();
            }
        });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        repaint();
      }
    });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {

        repaint();
      }
    });

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.VK_UP == e.getKeyCode()) {
                } else if (e.VK_DOWN == e.getKeyCode()) {
                } else if (e.VK_LEFT == e.getKeyCode()) {
                } else if (e.VK_RIGHT == e.getKeyCode()) {
                }
                repaint();
            }
        });
        init();
        start();
    }
    Thread relaxer;
////////////////////////////////////////////////////////////////////////////
    public void init() {
        requestFocus();
    }
////////////////////////////////////////////////////////////////////////////
    public void destroy() {
    }



////////////////////////////////////////////////////////////////////////////
    public void paint(Graphics gOld) {
        if (image == null || xsize != getSize().width || ysize != getSize().height) {
            xsize = getSize().width;
            ysize = getSize().height;
            image = createImage(xsize, ysize);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
//fill background
        g.setColor(Color.cyan);
        g.fillRect(0, 0, xsize, ysize);

        int x[] = {getX(0), getX(getWidth2()), getX(getWidth2()), getX(0), getX(0)};
        int y[] = {getY(0), getY(0), getY(getHeight2()), getY(getHeight2()), getY(0)};
//fill border
        g.setColor(Color.white);
        g.fillPolygon(x, y, 4);
// draw border
        g.setColor(Color.red);
        g.drawPolyline(x, y, 5);

        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }
        g.setColor(new Color(200,200,255));
        g.fillRect(0, 0, 1000, 1000);
        g.setColor(Color.green);
        g.fillRect(0, 600, 1000, 1000);

//Draw the cloud.        
        drawCloud(getX(cloudX),getYNormal(cloudY),0,1,1);
//Draw the mountain.        
//Needs to be fixed.  Move the mountains down so that they are not floating.
        drawMountain(getX(300),getYNormal(300),0,5,1);
//Draw the building.        
        drawBuilding(getX(840),getYNormal(150),0,2,2);
//Draw the monster.        
        drawDino(getX(monsterX),getYNormal(monsterY),monsterRotate,1.6,1.6);
//Draw the building.        
        drawBuilding(getX(900),getYNormal(120),0,3,3);
//Draw the person.        
        drawPerson(getX(personX),getYNormal(personY),0,2,2);

        
//Display the proper text and the correct time.
//Needs to be fixed.  Text appears too early.  Change so that the text gets displayed 
//                    after the monster first stops moving.
        if (timeCount > 2*frameRate && timeCount < 9.5*frameRate)
        {
            g.setColor(Color.black);
            g.setFont(new Font("Comic Sans MS",Font.PLAIN,50));
            g.drawString("It's The Monster.",200,200);
        }
        else if (timeCount > 13*frameRate && timeCount < 15*frameRate)
        {
            g.setColor(Color.black);
            g.setFont(new Font("Comic Sans MS",Font.PLAIN,50));
//Needs to be fixed.  Replace ???? with the text you want displayed.             
            g.drawString("????. Run !!!",100,200);
            
        }        
        else if (timeCount > 24*frameRate && timeCount < 26*frameRate)
        {
            g.setColor(Color.black);
            g.setFont(new Font("Comic Sans MS",Font.PLAIN,50));
//Needs to be fixed.  Replace ???? with the text you want displayed.             
            g.drawString("????",100,200);            
        }        
        else if (timeCount > 31*frameRate && timeCount < 34*frameRate)
        {
            g.setColor(Color.black);
            g.setFont(new Font("Comic Sans MS",Font.PLAIN,50));
            g.drawString("Big Mistake Using An",100,200);
            g.drawString("Orthographic Camera.",100,250);
            
        }        
        else if (timeCount > 34*frameRate)
        {
            g.setColor(Color.black);
            g.setFont(new Font("Comic Sans MS",Font.PLAIN,50));
            g.drawString("The End.",100,200);            
        }        
        
//        {
//            g.setColor(Color.black);
//            g.setFont(new Font("Comic Sans MS",Font.PLAIN,20));
//            g.drawString("timeCount = " + timeCount,20,80);
//        }


        gOld.drawImage(image, 0, 0, null);
    }

////////////////////////////////////////////////////////////////////////////
// needed for     implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
            double seconds = 1.0/frameRate;    //time that 1 frame takes.
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }
/////////////////////////////////////////////////////////////////////////
    public void reset() {
//Position the initial location for the monster.
        monsterX = 1100;
        monsterY = 150;
        monsterRotate = 0;
//Position the initial location for the person.        
        personX = 70;
        personY = 140;
//Position the clouds.
        cloudX = 200;
        cloudY = 600;
        cloudDirection = 2;
        
        timeCount = 0;
    }
/////////////////////////////////////////////////////////////////////////
    public void animate() {
        if (animateFirstTime) {
            animateFirstTime = false;
            if (xsize != getSize().width || ysize != getSize().height) {
                xsize = getSize().width;
                ysize = getSize().height;
            }
         
            reset();

        }
//Have the cloud oscillate left and right.
        cloudX += cloudDirection;
        if (cloudX > 500)
        {
            cloudDirection = -2;
        }
//Needs to be fixed.  Add code so that the cloud will oscillate left and right.
        


//Have the monster move left and then pause for a few seconds.
        if (timeCount < 7*frameRate)
        {
            monsterX -= 3;
        }
//Have the monster move left and down and then pause for a few seconds.
        else if (timeCount > 10*frameRate && timeCount < 13*frameRate)
        {
            monsterX -= 1;
//Needs to be fixed.  The monster should move down not up.
            monsterY += 1;
        }
//Have the monster move left and down.
        else if (timeCount > 15*frameRate && timeCount < 18*frameRate)
        {
            monsterX -= 2;
            monsterY -= 1;
        }
//Have the monster move left and pause for a few seconds.
        else if (timeCount > 18*frameRate && timeCount < 24*frameRate)
        {
            monsterX -= 2;
        }         
//Have the person move right and hit the monster.
        else if (timeCount > 26*frameRate && timeCount < 29*frameRate)
        {
            personX += 2;
        }
//Have the monster rotate to simulate falling.
        else if (timeCount > 29*frameRate && timeCount < 30.5*frameRate)
        {
//Needs to be fixed.  Have the monster rotate by the correct amount so that it ends up sideways.            
            monsterRotate += 10;
        }
        
        
        timeCount++;
    }
    ////////////////////////////////////////////////////////////////////////////
    public void drawCloud(int xpos,int ypos,double rot,double xscale,double yscale)
    {
        g.setColor(Color.white);
        g.translate(xpos,ypos);
        g.rotate(rot  * Math.PI/180.0);
        g.scale( xscale , yscale );
        
        g.setColor(Color.white);
        g.fillOval(-20, -10, 70, 50);
        g.fillOval(20, -20, 60, 40);
        g.fillOval(50, 0, 70, 40);
        g.fillOval(0, 10, 90, 50);
        
        g.scale( 1.0/xscale,1.0/yscale );
        g.rotate(-rot  * Math.PI/180.0);
        g.translate(-xpos,-ypos);
    }     
///////////////////////////////////////////////////////////////////////////////
   public void drawMountain(int xpos,int ypos,double rot,double xscale,double yscale)
   {
        g.translate(xpos,ypos);
        g.rotate(rot  * Math.PI/180.0);
        g.scale( xscale , yscale );

        g.setColor(Color.green);
        int xvals2[] = {0,30,40,60,80,90,114,137,170,0};
        int yvals2[] = {0,-100,-50,-300,-100,-214,-114,-230,0,0};
        g.fillPolygon(xvals2,yvals2,xvals2.length);
         
        
        g.scale( 1.0/xscale,1.0/yscale );
        g.rotate(-rot  * Math.PI/180.0);
        g.translate(-xpos,-ypos);
   }
    
        
       ////////////////////////////////////////////////////////////////////////////
    public void drawDino(int xpos,int ypos,double rot,double xscale,double yscale)
    {
        g.translate(xpos,ypos);
        g.rotate(rot  * Math.PI/180.0);
        g.scale( xscale , yscale );

       
          int xvals[] = {0,10,20,30,50,50,60,60,40,10,0,-20,0,-20,0};
   int yvals[] ={-70,-70,-50,-20,-30,-60,-50,-20,0,0,-40,-50,-60,-60,-70};
   Color dino = new Color (90, 145, 89);
    Color dinoNails = new Color (12, 12, 12);
  Color dinoArms = new Color (65, 119, 26);
   g.setColor(dinoArms);
  g.fillRect(10, -53, 7, 17);
  g.fillRect(-5, -43, 20, 7);
   g.setColor(dinoNails);
  g.fillRect(-7, -43, 5, 2);
    g.fillRect(-7, -39, 5, 2);
    //////////////////////////////
 g.setColor(Color.black);
g.fillArc(7, -67, 15, 15, 80,280 );
g.fillArc(14, -52, 15, 15, 80,280 );
g.fillArc(19, -37, 15, 15, 80,280 );
g.fillArc(3, -75, 10, 10, 40,320 );

////////////////////////////////
g.setColor(dino);
   g.fillPolygon(xvals, yvals,xvals.length);
   
   g.fillRect(10, 0, 10, 10);
  g.fillRect(30, 0, 10, 10);
  ///////////////////////
  g.setColor(dinoNails);
  g.fillRect(10, 6, 4, 4);
   g.fillRect(30, 6, 4, 4);
   g.fillRect(15, 6, 4, 4);
   g.fillRect(35, 6, 4, 4);
  ////////////////////////////////
    g.setColor(dinoArms);
  g.fillRect(10, -45, 7, 17);
  g.fillRect(-5, -35, 20, 7);
  g.setColor(dinoNails);
  g.fillRect(-7, -35, 5, 2);
    g.fillRect(-7, -31, 5, 2);
    /////////////////////////
    g.setColor(Color.WHITE);
      
    g.fillOval(0, -68, 5, 5);
      g.setColor(Color.black);
     g.fillOval(1, -66, 2, 2);
     g.fillRect(0, -70, 5, 2);
     
        g.scale( 1.0/xscale,1.0/yscale );
        g.rotate(-rot  * Math.PI/180.0);
        g.translate(-xpos,-ypos);
    }
 
    ////////////////////////////////////////////////////////////////////////////
    public void drawPerson(int xpos,int ypos,double rot,double xscale,double yscale)
    {
        g.translate(xpos,ypos);
        g.rotate(rot  * Math.PI/180.0);
        g.scale( xscale , yscale );
        
        Color HatGreen = new Color(0,139,69);
        
        g.setColor(Color.black);
        int xvals[] = {0,5,5,30,35,5,5,20,10,0,-10,-20,-5,-5,-35,-30,-5,-5,-15,-15,15,15,0};
        int yvals[] = {0,0,10,-10,0,20,40,60,60,45,60,60,40,20,0,-10,10,0,0,-30,-30,0,0};
        g.fillPolygon(xvals,yvals,xvals.length);
        
        g.setColor(HatGreen);
        int xvals2[] = {25,25,15,15,-15,-15,-25,-25,25};
        int yvals2[] = {-30,-35,-35,-50,-50,-35,-35,-30,-30};
       g.fillPolygon(xvals2,yvals2,xvals2.length);
        
        
        g.scale( 1.0/xscale,1.0/yscale );
        g.rotate(-rot  * Math.PI/180.0);
        g.translate(-xpos,-ypos);
    }
    
      ////////////////////////////////////////////////////////////////////////////
    public void drawBuilding(int xpos,int ypos,double rot,double xscale,double yscale)
    {
        g.translate(xpos,ypos);
        g.rotate(rot  * Math.PI/180.0);
        g.scale( xscale , yscale );

       
          int xvals[] = {0,0,10,20,20,0};
   int yvals[] ={0,-60,-60,-60,0,0};
       Color newCol = new Color (103, 118, 118);
       g.setColor(newCol);
   g.fillPolygon(xvals, yvals,xvals.length);
    g.setColor(Color.YELLOW);
g.fillRect(12, -50, 5, 15);
g.fillRect(3, -50, 5, 15);
g.fillRect(12, -30, 5, 15);
g.fillRect(3, -30, 5, 15);
 ////////////////////////
g.setColor(Color.black);
g.fillRect(3, -10, 15, 10);

 Color dinoNails = new Color (103, 118, 118);
g.setColor(dinoNails);
g.fillOval(5, -5, 3, 3);
g.fillOval(13, -5, 3, 3);
        g.scale( 1.0/xscale,1.0/yscale );
        g.rotate(-rot  * Math.PI/180.0);
        g.translate(-xpos,-ypos);
    }    
////////////////////////////////////////////////////////////////////////////
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }
////////////////////////////////////////////////////////////////////////////
    public void stop() {
        if (relaxer.isAlive()) {
            relaxer.stop();
        }
        relaxer = null;
    }
/////////////////////////////////////////////////////////////////////////
    public int getX(int x) {
        return (x + XBORDER);
    }

    public int getY(int y) {
        return (y + YBORDER + YTITLE);
    }

    public int getYNormal(int y) {
        return (-y + YBORDER + YTITLE + getHeight2());
    }
    
    
    public int getWidth2() {
        return (xsize - getX(0) - XBORDER);
    }

    public int getHeight2() {
        return (ysize - getY(0) - YBORDER);
    }
}

