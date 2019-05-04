import java.util.Arrays;
import java.util.Scanner;
/**
 * Reversi.java:
 * Abstraction of the Reversi game and also launches the game
 */
public class Reversi {
    public static boolean isEmpty(Point[] p) {
        //return true if there are valid moves; false if there are no valid moves
        int count = 0;

        for (int j = 0; j < p.length; j++) {
            if (p[j].x != -1 && p[j].y != -1) {
                count++;
            }
        }
        return count > 0;
    }

    //Check whether a Point is the Points array or not
    public static boolean contains(Point[] points, Point p) {
        //return true if the board contains the point; false if the board does not contain the point

        for (int i = 0; i < points.length; i++) {
            if (p.x == points[i].x && p.y == points[i].y) {
                return true;
            }
        }
        return false;
    }

    public static void start(Game g) {
        //The Reversi game currently in play

        Scanner scanner = new Scanner(System.in);
        g.remaining = 1;
        while (g.remaining != 0) {
            boolean bool = false;
            boolean bool2 = true;

            while (!bool) {
                if (bool2) {
                    g.showPlaceableLocations(g.getPlaceableLocations('B', 'W'), 'B', 'W');
                }
                System.out.println("Place move (Black): row then column: ");
                String a = scanner.nextLine();
                if (a.equals("exit")) {
                    System.out.println("Exiting!");
                    return;
                } else if (a.length() == 2) {
                    if (Character.isDigit(a.charAt(0)) && Character.isDigit(a.charAt(1))) {
                        int o = Integer.parseInt(a.substring(0, 1));
                        int t = Integer.parseInt(a.substring(1));

                        if (o >= 1 && o <= 8 && t >= 1 && t <= 8 &&
                                Arrays.asList(g.getPlaceableLocations('B', 'W')).contains(new Point(o - 1, t - 1))) {
                            g.placeMove(new Point(o - 1, t - 1), 'B', 'W');
                            g.updateScores();
                            System.out.println("Black: " + g.bScore + " White: " + g.wScore);
                            System.out.println();
                            bool = true;
                        } else {
                            System.out.println("Invalid move!");
                        }
                    } else {
                        System.out.println("Invalid move!");
                    }
                } else {
                    System.out.println("Invalid move!");
                }
                bool2 = false;
            }

            bool2 = true;
            while (bool) {
                if (bool2)
                    g.showPlaceableLocations(g.getPlaceableLocations('W', 'B'), 'W', 'B');
            }
            System.out.println("Place move (White): row then column: ");
            String a = scanner.nextLine();
            if (a.equals("exit")) {
                System.out.println("Exiting!");
                return;
            } else if (a.length() == 2) {
                if (Character.isDigit(a.charAt(0)) && Character.isDigit(a.charAt(1))) {
                    int o = Integer.parseInt(a.substring(0, 1));
                    int t = Integer.parseInt(a.substring(1));

                    if (o >= 1 && o <= 8 && t >= 1 && t <= 8 &&
                            Arrays.asList(g.getPlaceableLocations('W', 'B')).contains(new Point(o - 1, t - 1))) {
                        g.placeMove(new Point(o - 1, t - 1), 'W', 'B');
                        g.updateScores();
                        System.out.println("White: " + g.wScore + " Black: " + g.bScore);
                        System.out.println();
                        bool = false;
                    } else {
                        System.out.println("Invalid move!");
                    }
                } else {
                    System.out.println("Invalid move!");
                }
            } else {
                System.out.println("Invalid move!");
            }
            bool2 = false;
        }

        int a = g.gameResult(g.getPlaceableLocations('W', 'B'),
                (g.getPlaceableLocations('B', 'W')));

        if (a == 1) {
            System.out.println("White wins: " + g.wScore + ":" + g.bScore);
        } else if (a == -1) {
            System.out.println("Black wins: " + g.bScore + ":" + g.wScore);
        } else {
            System.out.println("It is a draw.");
        }
    }

    public static void main(String[]args) {
        Game b = new Game();
        start(b);

        //extension of the Game class. This is a subclass for the instance
    }
}








