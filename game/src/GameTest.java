import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class GameTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private Game game;

  @org.junit.Before
  public void setUp() throws Exception {
    game = new Game();
  }

  @Test
  public void canMakeAPlay() throws Exception {
    game.makePlay(0, 0, ' ');
  }

  @Test
  public void afterAPlayTheBoardShouldCheckTheCell() throws Exception {
    game.makePlay(0,0,'X');
    assertTrue(game.isBoardCellChecked(0,0));
  }

  @Test
  public void aPlayCoordinateIsValid() throws Exception {
    thrown.expect(InvalidPlayException.class);
    game.makePlay(5,5,' ');

  }

  @Test
  public void aPlayercantPlayTwoTimesInTheSameCell() throws Exception {
    thrown.expect(InvalidPlayException.class);
    game.makePlay(0,0,'X');
    game.makePlay(0,0,'X');
  }

  @Test
  public void aPlayerWinAFullRowWins() throws Exception {
    game.makePlay(1,0,'X');
    game.makePlay(1,1,'X');
    game.makePlay(1,2,'X');
    assertTrue(game.aPlayerWon());
  }

  @Test
  public void aPlayerWinAFullColumnWins() throws Exception {
    game.makePlay(0,0,'X');
    game.makePlay(1,0,'X');
    game.makePlay(2,0,'X');
    assertTrue(game.aPlayerWon());
  }

  @Test
  public void aPlayerWithAFullPrimaryDiagonalWins() throws Exception {
    game.makePlay(0,0,'X');
    game.makePlay(1,1,'X');
    game.makePlay(2,2,'X');
    assertTrue(game.aPlayerWon());
  }

  @Test
  public void aPlayerWithAFullSecondaryDiagonalWins() throws Exception {
    game.makePlay(0,2,'X');
    game.makePlay(1,1,'X');
    game.makePlay(2,0,'X');
    assertTrue(game.aPlayerWon());
  }

  @Test
  public void cantFindAPlayIfTheBoardIsFull() throws Exception {
    thrown.expect(InvalidPlayException.class);
    game.fillTheBoard('X');
    game.makePlay(0,0,'X');
  }

  @Test
  public void test1() throws Exception {
    Play play;

    game.makePlay(0,0,'X');
    game.makePlay(0,1,'X');
    play = game.botChooseWhereToPlay();
    assertTrue(play.getX() == 0 && play.getY()==2 && play.getSymbol()=='X');
  }
  @Test
  public void test2() throws Exception {
    game.makePlay(0,1,'X');
    game.makePlay(0,2,'X');
    Play play = game.botChooseWhereToPlay();
    assertTrue(play.getX() == 0 && play.getY() == 0 && play.getSymbol() == 'X');
  }

  @Test
  public void test3() throws Exception {
    game.makePlay(0,0,'X');
    game.makePlay(0,2,'X');
    Play play = game.botChooseWhereToPlay();
    assertTrue(play.getX() == 0 && play.getY() == 1 && play.getSymbol() == 'X');
  }

  @Test
  public void test4() throws Exception {
    game.makePlay(1,0,'X');
    game.makePlay(1,1,'X');
    Play play = game.botChooseWhereToPlay();
    assertTrue(play.getX() == 1 && play.getY() == 2 && play.getSymbol() == 'X');
  }

  @Test
  public void gameCanShowStatus() throws Exception {
    game.getStatus();
  }

  @Test
  public void whenAGameStartTheStatusIsInitialize() throws Exception {
    assertEquals("Game running", game.getStatus());
  }

  @Test
  public void whenAGameEndsTheStatusChange() throws Exception {
    game.gameEnds();
    assertEquals("Game ends", game.getStatus());
  }

  @org.junit.After
  public void tearDown() throws Exception {
  }


  private class Game {

    public static final String CANT_FIND_A_PLAY = "Can't find a play... GG :(";
    private char[][] board = new char[3][3];
    private String status = " ";

    Game(){
      for (int i = 0 ; i < 3 ; i++)
        for (int j = 0; j <3 ; j++)
          board[i][j] = ' ';
      status = "Game running";
    }

    private void makePlay(int x, int y, char symbol) throws InvalidPlayException, CanFindAPlayException {
      if (coordinatesAreInvalid(x, y))
        throw new InvalidPlayException("Invalid cell coordinate");

      if(board[x][y] == ' ')
        board[x][y] = symbol;
      else
        throw new InvalidPlayException("Cell is not empty");

    }

    private boolean coordinatesAreInvalid(int x, int y) {
      return (x > 2) || (x < 0) || (y > 2) || (y < 0);
    }

    private boolean isBoardCellChecked(int x, int y) {
      return board[x][y] != ' ';
    }

    private boolean aPlayerWon() {
      return aPlayerWonByColumn() || aPlayerWonByRow() || aPlayerWonByDiagonal();
    }

    private boolean aPlayerWonByDiagonal() {
      int scorePlayerX = 0;
      int scorePlayerO = 0;
      boolean aPlayerWon = false;

      for (int i = 0 ; i < 3 ; i++) {
        if (board[i][i] == 'X')
          scorePlayerX++;
        if (board[i][i] == 'O')
          scorePlayerO++;
      }
      if(scorePlayerX == 3 || scorePlayerO == 3)
        aPlayerWon = true;
      else{
        scorePlayerX = 0;
        scorePlayerO = 0;
      }

      for (int i = 0 ; i < 3 ; i++) {
        for (int j = 2; j >= 0; j--) {
          if (board[i][j] == 'X')
            scorePlayerX++;
          if (board[i][j] == 'O')
            scorePlayerO++;
        }
      }

      if(scorePlayerX == 3 || scorePlayerO == 3)
        aPlayerWon = true;

      return aPlayerWon;

    }

    private boolean aPlayerWonByColumn() {
      int scorePlayerX = 0;
      int scorePlayerO = 0;
      boolean aPlayerWon = false;

      for (int i = 0 ; i < 3 ; i++){
        for (int j = 0; j < 3 ; j++){
          if (board[j][i] == 'X')
            scorePlayerX++;
          if(board[j][i] == 'O')
            scorePlayerO++;
        }

        if(scorePlayerX == 3 || scorePlayerO == 3)
          aPlayerWon = true;
        else {
          scorePlayerX = 0;
          scorePlayerO = 0;
        }
      }
      return aPlayerWon;
    }

    private boolean aPlayerWonByRow() {
      int scorePlayerX = 0;
      int scorePlayerO = 0;
      boolean aPlayerWon = false;

      for (int i = 0 ; i < 3 ; i++){
        for (int j = 0 ; j < 3 ; j++){
          if (board[i][j] == 'X')
            scorePlayerX++;
          if(board[i][j] == 'O')
            scorePlayerO++;
        }
        if(scorePlayerX == 3 || scorePlayerO == 3)
          aPlayerWon = true;
        else {
          scorePlayerX = 0;
          scorePlayerO = 0;
        }
      }
      return  aPlayerWon;
    }

    private String getStatus() {
      return status;
    }

    private void gameEnds() {
      status = "Game ends";
    }

    private Play botChooseWhereToPlay() throws InvalidPlayException, CanFindAPlayException {
      Play play = new Play();
      int numberOfX = 0;

      for (int i = 0 ; i < 3 ; i++) {
        for (int j = 0 ; j < 3 ; j++) {
          if (board[i][j] == 'X'){
            numberOfX++;
          }else{
            if( board[i][j] == ' '){
              play.setMove(i, j, 'X');
            }
          }
        }
        if(numberOfX == 2)
          return play;
        else
          numberOfX = 0;
      }
      return play;

//      if(play.isValid()){
//        makePlay(play.getX(), play.getY(), play.getSymbol());
//        if(aPlayerWon()){
//          gameEnds();
//        }
//      }else{
//        throw new CanFindAPlayException(CANT_FIND_A_PLAY);
//      }

    }


    private void fillTheBoard(char x) {
      for (int i = 0 ; i < 3 ; i++)
        for (int j = 0 ; j < 3 ; j++)
          board[i][j] = 'X';
    }




  }

  private class Play {
    int x;
    int y;
    char symbol;

    Play(int x, int y, char symbol) {
      this.x = x;
      this.y = y;
      this.symbol = symbol;
    }

    public Play() {
      x = -1;
      y = -1;
      symbol = ' ';
    }

    public boolean isValid(){
      return (x != -1 && y != -1);
    }

    private int getX() {
      return x;
    }

    private int getY() {
      return y;
    }

    private char getSymbol() {
      return symbol;
    }

    public void setMove(int x, int y, char c) {
      this.x = x;
      this.y = y;
      this.symbol = c;
    }
  }
  private class CanFindAPlayException extends Exception {
    CanFindAPlayException(String message) {
      super(message);
    }
  }

  private class InvalidPlayException extends Exception {
    InvalidPlayException(String message) {
      super(message);
    }
  }
}