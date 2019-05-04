/**
 * Game.java:
 * Implementation of the game mechanics in Reversi

 */
public class Game {

    private final char[][] board;
    private final char[] boardX = new char[]{'1', '2', '3', '4', '5', '6', '7', '8'};
    public Point p;
    public int wScore;
    public int bScore;
    public int remaining;

    public Game() {
        board = new char[][]{
                {'_', '_', '_', '_', '_', '_', '_', '_', },
                {'_', '_', '_', '_', '_', '_', '_', '_', },
                {'_', '_', '_', '_', '_', '_', '_', '_', },
                {'_', '_', '_', 'W', 'B', '_', '_', '_', },
                {'_', '_', '_', 'B', 'W', '_', '_', '_', },
                {'_', '_', '_', '_', '_', '_', '_', '_', },
                {'_', '_', '_', '_', '_', '_', '_', '_', },
                {'_', '_', '_', '_', '_', '_', '_', '_', }, };

        wScore = 0;
        bScore = 0;
        remaining = 0;

    }

    public void displayBoard(Game b) {
        //displayed game board

        System.out.print("  ");
        for (int q = 0; q < boardX.length; q++) {
            System.out.print(boardX[q] + " ");
        }
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            System.out.print(boardX[i] + " ");
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    //There are three cases black win = -1, white win = 1, draw = 0

    public int gameResult(Point[] whitePlaceableLocations, Point[] blackPlaceableLocations) {
         //The integer corresponding to the game result: -1 for black win, 0 for draw, 1 for white win

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == '_') {
                    remaining++;
                }
            }
        }

        if (remaining == 0) {
            if (wScore > bScore) {
                return 1;
            } else if (wScore == bScore) {
                return 0;
            } else {
                return -1;
            }
        } else {
            return 0;
        }

    }

    public Point[] getPlaceableLocations(char player, char opponent) {
         //placeablePositions array, with corresponding point for a valid location and (-1,-1) for an invalid location

        Point[] placeablePositions = new Point[64];
        for (int y = 0; y < placeablePositions.length; y++) {
            placeablePositions[y] = new Point(-1, -1);
        }
        int count = 0;
        for (int i = 0; i < board.length; i++) { //rows
            for (int j = 0; j < board[i].length; j++) { //columns
                for (int k = 0; k < board.length; k++) {
                    if (i - k - 1 >= 0 && i + k + 1 <= 7 && j - k - 1 >= 0 && j + k + 1 <= 7) {
                        if (board[i][j] == player && board[i + k][j] == opponent) { //up to down
                            if (board[i + k + 1][j] == '_') {
                                placeablePositions[count] = new Point(i + k + 1, j);
                                count++;
                            }
                        }

                        if (board[i][j] == player && board[i][j + k] == opponent && board[i][j + k + 1] == '_') {
                            placeablePositions[count] = new Point(i, j + k + 1);
                            count++;
                        }

                        if (board[i][j] == player && board[i - k][j] == opponent && board[i - k - 1][j] == '_') {
                            placeablePositions[count] = new Point(i - k - 1, j);
                            count++;
                        }

                        if (board[i][j] == player && board[i][j - k] == opponent && board[i][j - k - 1] == '_') {
                            placeablePositions[count] = new Point(i, j - k - 1);
                            count++;
                        }

                        if (board[i][j] == player && board[i + k][j + k] == opponent
                                && board[i + k + 1][j + k + 1] == '_') { //southeast
                            placeablePositions[count] = new Point(i + k + 1, j + k + 1);
                            count++;
                        }

                        if (board[i][j] == player && board[i - k][j - k] == opponent
                                && board[i - k - 1][j - k - 1] == '_') { //northwest
                            placeablePositions[count] = new Point(i - k - 1, j - k - 1);
                            count++;
                        }

                        if (board[i][j] == player && board[i + k][j - k] == opponent
                                && board[i + k + 1][j - k - 1] == '_') { //southwest
                            placeablePositions[count] = new Point(i + k + 1, j - k - 1);
                            count++;
                        }

                        if (board[i][j] == player && board[i - k][j + k] == opponent
                                && board[i - k - 1][j + k + 1] == '_') { //southeast
                            placeablePositions[count] = new Point(i - k - 1, j + k + 1);
                            count++;
                        }
                    }
                }
            }
        }

        return placeablePositions;
    }

    public void showPlaceableLocations(Point[] locations, char player, char opponent) {
        // locations Array containing placeable locations for the player
        // player Current player
        // opponent player's opponent

        for (int w = 0; w < board.length; w++) {
            if (locations[w].x != -1 && locations[w].y != -1) {
                board[locations[w].x][locations[w].y] = '*';
            }
        }

        displayBoard(this);

        for (int q = 0; q < locations.length; q++) {
            if (locations[q].x != -1 && locations[q].y != -1) {
                board[locations[q].x][locations[q].y] = '_';
            }
        }
    }

    public void placeMove(Point po, char player, char opponent) {
        // p Point corresponding to the location of the piece to be placed
        // player Current player
        // opponent player's opponent

        int a = po.x;
        int b = po.y;
        board[a][b] = player;

        int eighth = 0;
        if (a + 1 < 8 && board[a + 1][b] == opponent) {
            for (int c = 1; c < 8; c++) {
                if (a + c < 8 && board[a + c][b] == opponent) {
                    eighth++;
                } else if (a + c < 8 && board[a + c][b] == player) {
                    for (int d = 1; d < eighth + 1; d++) {
                        board[a + d][b] = player;
                    }
                    break;
                } else {
                    break;
                }
            }
        }

        int seventh = 0;
        if (b + 1 < 8 && board[a][b + 1] == opponent) {
            for (int c = 1; c < 8; c++) {
                if (b + c < 8 && board[a][b + c] == opponent) {
                    seventh++;
                } else if (b + c < 8 && board[a][b + c] == player) {
                    for (int d = 1; d < seventh + 1; d++) {
                        board[a][b + d] = player;
                    }
                    break;
                } else {
                    break;
                }
            }
        }

        int sixth = 0;
        if (a - 1 >= 0 && board[a - 1][b] == opponent) {
            for (int c = 1; c < 8; c++) {
                if (a - c >= 0 && board[a - c][b] == opponent) {
                    sixth++;
                } else if (a - c >= 0 && board[a - c][b] == player) {
                    for (int d = 1; d < sixth + 1; d++) {
                        board[a - d][b] = player;
                    }
                    break;
                } else {
                    break;
                }
            }
        }

        int fifth = 0;
        if (b - 1 >= 0 && board[a][b - 1] == opponent) {
            for (int c = 1; c < 8; c++) {
                if (b - c >= 0 && board[a][b - c] == opponent) {
                    fifth++;
                } else if (b - c >= 0 && board[a][b - c] == player) {
                    for (int d = 1; d < fifth + 1; d++) {
                        board[a][b - d] = player;
                    }
                    break;
                } else {
                    break;
                }
            }
        }

        int fourth = 0;
        if (a + 1 < 8 && b + 1 < 8 && board[a + 1][b + 1] == opponent) {
            for (int c = 1; c < 8; c++) {
                if (a + c < 8 && b + c < 8 && board[a + c][b + c] == opponent) {
                    fourth++;
                } else if (a + c < 8 && b + c < 8 && board[a + c][b + c] == player) {
                    for (int d = 1; d < fourth + 1; d++) {
                        board[a + d][b + d] = player;
                    }
                    break;
                } else {
                    break;
                }
            }
        }

        int third = 0;
        if (a + 1 < 8 && b - 1 >= 0 && board[a + 1][b - 1] == opponent) {
            for (int c = 1; c < 8; c++) {
                if (a + c < 8 && b - c >= 0 && board[a + c][b - c] == opponent) {
                    third++;
                } else if (a + c < 8 && b - c >= 0 && board[a + c][b - c] == player) {
                    for (int d = 1; d < third + 1; d++) {
                        board[a + d][b - d] = player;
                    }
                    break;
                } else {
                    break;
                }
            }
        }

        int first = 0;
        if (a - 1 >= 0 && b + 1 < 8 && board[a - 1][b + 1] == opponent) {
            for (int c = 1; c < 8; c++) {
                if (a - c >= 0 && b + c < 8 && board[a - c][b + c] == opponent) {
                    first++;
                } else if (a - c >= 0 && b + c < 8 && board[a - c][b + c] == player) {
                    for (int d = 1; d < first + 1; d++) {
                        board[a - d][b + d] = player;
                    }
                    break;
                } else {
                    break;
                }
            }
        }

        int second = 0;
        if (a - 1 >= 0 && b - 1 >= 0 && board[a - 1][b - 1] == opponent) {
            for (int c = 1; c < 8; c++) {
                if (a - c >= 0 && b - c >= 0 && board[a - c][b - c] == opponent) {
                    second++;
                } else if (a - c >= 0 && b - c >= 0 && board[a - c][b - c] == player) {
                    for (int d = 1; d < second + 1; d++) {
                        board[a - d][b - d] = player;
                    }
                    break;
                } else {
                    break;
                }
            }
        }
    }

    public void updateScores() {
        wScore = 0;
        bScore = 0;
        remaining = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 'W') {
                    wScore++;
                }

                if (board[i][j] == 'B') {
                    bScore++;
                }

                if (board[i][j] == '_') {
                    remaining++;
                }
            }
        }
    }
}

