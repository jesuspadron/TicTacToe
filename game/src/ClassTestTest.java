import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class ClassTestTest {

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private final Game game = new Game();

  @org.junit.Before
  public void setUp() throws Exception {

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
  public void cantPlayTwoTimesInTheSameCell() throws Exception {
    thrown.expect(InvalidPlayException.class);
    game.makePlay(0,0,'X');
    game.makePlay(0,0,'X');
  }

  @Test
  public void aPlayerWinAFullRowWins() throws Exception {
    game.makePlay(1,0,'X');
    game.makePlay(1,1,'X');
    game.makePlay(1,2,'X');
    assertTrue(game.playerWon());
  }

  @Test
  public void aPlayerWinAFullColumnWins() throws Exception {
    game.makePlay(0,0,'X');
    game.makePlay(1,0,'X');
    game.makePlay(2,0,'X');
    assertTrue(game.playerWon());
  }

  @Test
  public void aPlayerWithAFullDiagonalWins() throws Exception {
    game.makePlay(0,0,'X');
    game.makePlay(1,1,'X');
    game.makePlay(2,2,'X');
    assertTrue(game.playerWon());

  }

  @org.junit.After
  public void tearDown() throws Exception {
  }

  private class Game {

    private char[][] board = new char[3][3];

    public Game(){
      for (int i = 0 ; i < 3 ; i++)
        for (int j = 0; j <3 ; j++)
          board[i][j] = ' ';
    }

    public void makePlay(int x, int y, char symbol) throws InvalidPlayException {
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

    public boolean isBoardCellChecked(int x, int y) {
      return board[x][y] != ' ';
    }

    public boolean playerWon() {
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
        for (int j = 2; j > 0; j--) {
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
  }

  private class InvalidPlayException extends Exception {
    public InvalidPlayException(String message) {
      super(message);
    }
  }
}