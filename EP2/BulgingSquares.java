/******************************************************************************
 *  Compilation:  javac BulgingSquares.java
 *  Execution:    java BulgingSquares
 *  Dependencies: StdDraw.java, java.awt.Color
 *
 *  Program draws an optical illusion from Akiyoshi Kitaoka. The center appears 
 *  to bulge outwards even though all squares are the same size. 
 *
 *  meu_prompt > java BulgingSquares
 *
 *  Exercise 14 http://introcs.cs.princeton.edu/java/15inout/
 * 
 ******************************************************************************/

// Standard draw. This class provides a basic capability for creating
// drawings with your programs. It uses a simple graphics model that
// allows you to create drawings consisting of points, lines, and
// curves in a window on your computer and to save the drawings to a
// file.
// https://www.ime.usp.br/~pf/sedgewick-wayne/stdlib/documentation/index.html
// http://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html
import edu.princeton.cs.algs4.StdDraw; // StdDraw.setXscale, StdDraw.setYscale, ...

import java.awt.Color; // StdDraw.WHITE, StdDraw.BLACK

public class BulgingSquares {
    // constantes... vixe! use se desejar
    private static final double XMIN   = -75;
    private static final double XMAX   =  75;
    private static final double YMIN   = -75;
    private static final double YMAX   =  75;
    private static final double MARGIN =   2;
    private static final double RADIUS_MAX =   5;
    private static final double DIAM_MAX   = 2*RADIUS_MAX;
    private static final double RADIUS_MIN = 1.5;
    private static final double DIAM_MIN   = 2*RADIUS_MIN;

    public static void drawBoard() {
        //Draws a 150x150 black and white checked board with 10x10 (dimension) squares;
        double x, y, offset = 0;
        StdDraw.square(0, 0, 75);
        for (y = YMIN + RADIUS_MAX ; y <= YMAX - RADIUS_MAX; y += DIAM_MAX) {
            for (x = XMIN + RADIUS_MAX + offset; x <= XMAX - RADIUS_MAX; x += 2*DIAM_MAX)
                StdDraw.filledSquare(x, y, RADIUS_MAX);
            if (offset == DIAM_MAX)
                offset = 0;
            else
                offset = DIAM_MAX;
        } 
    }

    public static void squareArtist(double x, double y, int squareType, java.awt.Color color) {
        /*Receives the center coordinates for a square, an integer 1<=squareType<=6 representing a template to be
         *drawn in the square, and the pen color.
         *Draws the specified template in the square of center (x,y) with the "color" pen.*
         *
         *Square types in order from 1 to 6 (the x's represent the small squares drawn in the square of center (x, y)):
         *  ___    ___    ___    ___    ___    ___
         * |x  |  |  x|  |x x|  |   |  |x  |  |  x|
         * |__x|  |x__|  |___|  |x_x|  |x__|  |__x|     
         *
         */       

        StdDraw.setPenColor(color);
        switch (squareType) {
            case 1:
                StdDraw.filledSquare(x + DIAM_MIN, y - DIAM_MIN, RADIUS_MIN);
                StdDraw.filledSquare(x - DIAM_MIN, y + DIAM_MIN, RADIUS_MIN);
                break;
            case 2:
                StdDraw.filledSquare(x + DIAM_MIN, y + DIAM_MIN, RADIUS_MIN);
                StdDraw.filledSquare(x - DIAM_MIN, y - DIAM_MIN, RADIUS_MIN);
                break;
            case 3:
                StdDraw.filledSquare(x + DIAM_MIN, y + DIAM_MIN, RADIUS_MIN);
                StdDraw.filledSquare(x - DIAM_MIN, y + DIAM_MIN, RADIUS_MIN);
                break;
            case 4:
                StdDraw.filledSquare(x + DIAM_MIN, y - DIAM_MIN, RADIUS_MIN);
                StdDraw.filledSquare(x - DIAM_MIN, y - DIAM_MIN, RADIUS_MIN);
                break;
            case 5:
                StdDraw.filledSquare(x - DIAM_MIN, y + DIAM_MIN, RADIUS_MIN);
                StdDraw.filledSquare(x - DIAM_MIN, y - DIAM_MIN, RADIUS_MIN);
                break;
            case 6:
                StdDraw.filledSquare(x + DIAM_MIN, y + DIAM_MIN, RADIUS_MIN);
                StdDraw.filledSquare(x + DIAM_MIN, y - DIAM_MIN, RADIUS_MIN);
                break;
        }
    }

    public static void colorSwitch() {
        /*Checks the current pen color and switches it (black or white).*/
        if (StdDraw.getPenColor() == StdDraw.WHITE)
                StdDraw.setPenColor(StdDraw.BLACK);
        else 
            StdDraw.setPenColor(StdDraw.WHITE);
    }

    public static void main(String[] args) {
        // set the scale of the coordinate system
        StdDraw.setXscale(XMIN-MARGIN, XMAX+MARGIN);
        StdDraw.setYscale(YMIN-MARGIN, YMAX+MARGIN);
        StdDraw.enableDoubleBuffering();
        
        // clear the background
        StdDraw.clear(StdDraw.WHITE);

        // Escreva sua solução a seguir
        drawBoard();

        //line 2
        squareArtist(XMIN + RADIUS_MAX + 6*DIAM_MAX, YMIN + RADIUS_MAX + 1*DIAM_MAX, 1, StdDraw.BLACK);
        squareArtist(XMIN + RADIUS_MAX + 7*DIAM_MAX, YMIN + RADIUS_MAX + 1*DIAM_MAX, 3, StdDraw.WHITE);
        squareArtist(XMIN + RADIUS_MAX + 8*DIAM_MAX, YMIN + RADIUS_MAX + 1*DIAM_MAX, 2, StdDraw.BLACK);

        //line 3
        StdDraw.setPenColor(StdDraw.WHITE);
        for (int i = 4 ; i < 7; i++) {
            squareArtist(XMIN + RADIUS_MAX + i*DIAM_MAX, YMIN + RADIUS_MAX + 2*DIAM_MAX, 1, StdDraw.getPenColor());
            colorSwitch();
        }
        squareArtist(XMIN + RADIUS_MAX + 7*DIAM_MAX, YMIN + RADIUS_MAX + 2*DIAM_MAX, 3, StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        for (int i = 8; i < 11; i++) {
            squareArtist(XMIN + RADIUS_MAX + i*DIAM_MAX, YMIN + RADIUS_MAX + 2*DIAM_MAX, 2, StdDraw.getPenColor());
            colorSwitch();
        }
        
        //line 4
        StdDraw.setPenColor(StdDraw.WHITE);
        for (int i = 3 ; i < 7; i++) {
            squareArtist(XMIN + RADIUS_MAX + i*DIAM_MAX, YMIN + RADIUS_MAX + 3*DIAM_MAX, 1, StdDraw.getPenColor());
            colorSwitch();
        }
        squareArtist(XMIN + RADIUS_MAX + 7*DIAM_MAX, YMIN + RADIUS_MAX + 3*DIAM_MAX, 3, StdDraw.WHITE);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 8; i < 12; i++) {
            squareArtist(XMIN + RADIUS_MAX + i*DIAM_MAX, YMIN + RADIUS_MAX + 3*DIAM_MAX, 2, StdDraw.getPenColor());
            colorSwitch();
        }

        //line 5
        StdDraw.setPenColor(StdDraw.WHITE);
        for (int i = 2 ; i < 7; i++) {
            squareArtist(XMIN + RADIUS_MAX + i*DIAM_MAX, YMIN + RADIUS_MAX + 4*DIAM_MAX, 1, StdDraw.getPenColor());
            colorSwitch();
        }
        squareArtist(XMIN + RADIUS_MAX + 7*DIAM_MAX,  YMIN + RADIUS_MAX + 4*DIAM_MAX, 3, StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        for (int i = 8; i < 13; i++) {
            squareArtist(XMIN + RADIUS_MAX + i*DIAM_MAX, YMIN + RADIUS_MAX + 4*DIAM_MAX, 2, StdDraw.getPenColor());
            colorSwitch();
        }
        
        //line 6
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 2 ; i < 7; i++) {
            squareArtist(XMIN + RADIUS_MAX + i*DIAM_MAX, YMIN + RADIUS_MAX + 5*DIAM_MAX, 1, StdDraw.getPenColor());
            colorSwitch();
        }
        squareArtist(XMIN + RADIUS_MAX + 7*DIAM_MAX, YMIN + RADIUS_MAX + 5*DIAM_MAX, 3, StdDraw.WHITE);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 8; i < 13; i++) {
            squareArtist(XMIN + RADIUS_MAX + i*DIAM_MAX, YMIN + RADIUS_MAX + 5*DIAM_MAX, 2, StdDraw.getPenColor());
            colorSwitch();
        }
        
        //line 7
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 1 ; i < 7; i++) {
            squareArtist(XMIN + RADIUS_MAX + i*DIAM_MAX, YMIN + RADIUS_MAX + 6*DIAM_MAX, 1, StdDraw.getPenColor());
            colorSwitch();
        }
        squareArtist(XMIN + RADIUS_MAX + 7*DIAM_MAX, YMIN + RADIUS_MAX + 6*DIAM_MAX, 3, StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        for (int i = 8; i < 14; i++) {
            squareArtist(XMIN + RADIUS_MAX + i*DIAM_MAX, YMIN + RADIUS_MAX + 6*DIAM_MAX, 2, StdDraw.getPenColor());
            colorSwitch();
        }

        //line 8 
        StdDraw.setPenColor(StdDraw.WHITE);
        for (int i = 1 ; i < 7; i++) {
            squareArtist(XMIN + RADIUS_MAX + i*DIAM_MAX, YMIN + RADIUS_MAX + 7*DIAM_MAX, 6, StdDraw.getPenColor());
            colorSwitch();
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 8; i < 14; i++) {
            squareArtist(XMIN + RADIUS_MAX + i*DIAM_MAX, YMIN + RADIUS_MAX + 7*DIAM_MAX, 5, StdDraw.getPenColor());
            colorSwitch();
        }
        
        //line 9
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 1 ; i < 7; i++) {
            squareArtist(XMIN + RADIUS_MAX + i*DIAM_MAX, YMIN + RADIUS_MAX + 8*DIAM_MAX, 2, StdDraw.getPenColor());
            colorSwitch();
        }
        squareArtist(XMIN + RADIUS_MAX + 7*DIAM_MAX, YMIN + RADIUS_MAX + 8*DIAM_MAX, 4, StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        for (int i = 8; i < 14; i++) {
            squareArtist(XMIN + RADIUS_MAX + i*DIAM_MAX, YMIN + RADIUS_MAX + 8*DIAM_MAX, 1, StdDraw.getPenColor());
            colorSwitch();
        }

        //line 10
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 2 ; i < 7; i++) {
            squareArtist(XMIN + RADIUS_MAX + i*DIAM_MAX, YMIN + RADIUS_MAX + 9*DIAM_MAX, 2, StdDraw.getPenColor());
            colorSwitch();
        }
        squareArtist(XMIN + RADIUS_MAX + 7*DIAM_MAX, YMIN + RADIUS_MAX + 9*DIAM_MAX, 4, StdDraw.WHITE);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 8; i < 13; i++) {
            squareArtist(XMIN + RADIUS_MAX + i*DIAM_MAX, YMIN + RADIUS_MAX + 9*DIAM_MAX, 1, StdDraw.getPenColor());
            colorSwitch();
        }
        
        //line 11
        StdDraw.setPenColor(StdDraw.WHITE);
        for (int i = 2 ; i < 7; i++) {
            squareArtist(XMIN + RADIUS_MAX + i*DIAM_MAX, YMIN + RADIUS_MAX + 10*DIAM_MAX, 2, StdDraw.getPenColor());
            colorSwitch();
        }
        squareArtist(XMIN + RADIUS_MAX + 7*DIAM_MAX, YMIN + RADIUS_MAX + 10*DIAM_MAX, 4, StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        for (int i = 8; i < 13; i++) {
            squareArtist(XMIN + RADIUS_MAX + i*DIAM_MAX, YMIN + RADIUS_MAX + 10*DIAM_MAX, 1, StdDraw.getPenColor());
            colorSwitch();
        }

        //line 12
        StdDraw.setPenColor(StdDraw.WHITE);
        for (int i = 3 ; i < 7; i++) {
            squareArtist(XMIN + RADIUS_MAX + i*DIAM_MAX, YMIN + RADIUS_MAX + 11*DIAM_MAX, 2, StdDraw.getPenColor());
            colorSwitch();
        }
        squareArtist(XMIN + RADIUS_MAX + 7*DIAM_MAX, YMIN + RADIUS_MAX + 11*DIAM_MAX, 4, StdDraw.WHITE);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 8; i < 12; i++) {
            squareArtist(XMIN + RADIUS_MAX + i*DIAM_MAX, YMIN + RADIUS_MAX + 11*DIAM_MAX, 1, StdDraw.getPenColor());
            colorSwitch();
        }

        //line 13
        StdDraw.setPenColor(StdDraw.WHITE);
        for (int i = 4 ; i < 7; i++) {
            squareArtist(XMIN + RADIUS_MAX + i*DIAM_MAX, YMIN + RADIUS_MAX + 12*DIAM_MAX, 2, StdDraw.getPenColor());
            colorSwitch();
        }
        squareArtist(XMIN + RADIUS_MAX + 7*DIAM_MAX, YMIN + RADIUS_MAX + 12*DIAM_MAX, 4, StdDraw.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        for (int i = 8; i < 11; i++) {
            squareArtist(XMIN + RADIUS_MAX + i*DIAM_MAX, YMIN + RADIUS_MAX + 12*DIAM_MAX, 1, StdDraw.getPenColor());
            colorSwitch();
        }
        
        //line 14
        squareArtist(XMIN + RADIUS_MAX + 6*DIAM_MAX, YMIN + RADIUS_MAX + 13*DIAM_MAX, 2, StdDraw.BLACK);
        squareArtist(XMIN + RADIUS_MAX + 7*DIAM_MAX, YMIN + RADIUS_MAX + 13*DIAM_MAX, 4, StdDraw.WHITE);
        squareArtist(XMIN + RADIUS_MAX + 8*DIAM_MAX, YMIN + RADIUS_MAX + 13*DIAM_MAX, 1, StdDraw.BLACK);

        // copy offscreen buffer to onscreen
        StdDraw.show();
    }
} 
