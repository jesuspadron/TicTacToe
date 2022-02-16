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
  public void canFindWinnerPlayInRow() throws Exception {
    Play play;

    game.makePlay(0,0,'X');
    game.makePlay(0,1,'X');
    play = game.botFindWhereToPlay();
    assertTrue(play.is( 0, 2, 'X'));

    game = new Game();
    game.makePlay(0,1,'X');
    game.makePlay(0,2,'X');
    play = game.botFindWhereToPlay();
    assertTrue(play.is( 0, 0, 'X'));

    game = new Game();
    game.makePlay(0,0,'X');
    game.makePlay(0,2,'X');
    play = game.botFindWhereToPlay();
    assertTrue(play.is( 0, 1, 'X'));

    game = new Game();
    game.makePlay(1,0,'X');
    game.makePlay(1,1,'X');
    play = game.botFindWhereToPlay();
    assertTrue(play.is( 1, 2, 'X'));

    game = new Game();
    game.makePlay(1,0,'X');
    game.makePlay(1,2,'X');
    play = game.botFindWhereToPlay();
    assertTrue(play.is( 1, 1, 'X'));

    game = new Game();
    game.makePlay(1,1,'X');
    game.makePlay(1,2,'X');
    play = game.botFindWhereToPlay();
    assertTrue(play.is( 1, 0, 'X'));

    game = new Game();
    game.makePlay(2,0,'X');
    game.makePlay(2,1,'X');
    play = game.botFindWhereToPlay();
    assertTrue(play.is( 2, 2, 'X'));

    game = new Game();
    game.makePlay(2,0,'X');
    game.makePlay(2,2,'X');
    play = game.botFindWhereToPlay();
    assertTrue(play.is( 2, 1, 'X'));

    game = new Game();
    game.makePlay(2,2,'X');
    game.makePlay(2,1,'X');
    play = game.botFindWhereToPlay();
    assertTrue(play.is( 2, 0, 'X'));
  }


  @Test
  public void canFindWinnerPlayInColumn() throws Exception {
    Play play;

    game.makePlay(0, 0, 'X');
    game.makePlay(1, 0, 'X');
    play = game.botFindWhereToPlay();
    assertTrue(play.is( 2, 0, 'X'));

    game = new Game();
    game.makePlay(0, 0, 'X');
    game.makePlay(2, 0, 'X');
    play = game.botFindWhereToPlay();
    assertTrue(play.is( 1, 0, 'X'));

  }
  @Test
  public void canFindWinnerPlayInDiagonal() throws Exception {
    Play play;

    game = new Game();
    game.makePlay(0,0,'X');
    game.makePlay(1,1,'X');
    play = game.botFindWhereToPlay();
    assertTrue(play.is( 2, 2, 'X'));

    game = new Game();
    game.makePlay(0,0,'X');
    game.makePlay(2,2,'X');
    play = game.botFindWhereToPlay();
    assertTrue(play.is( 1, 1, 'X'));

    game = new Game();
    game.makePlay(0,2,'X');
    game.makePlay(1,1,'X');
    play = game.botFindWhereToPlay();
    assertTrue(play.is( 2, 0, 'X'));

    game = new Game();
    game.makePlay(0,2,'X');
    game.makePlay(2,0,'X');
    play = game.botFindWhereToPlay();
    assertTrue(play.is( 1, 1, 'X'));
  }

  @Test
  public void canBlockOpponentWinnerPlay() throws Exception {
    Play play;
    game.makePlay(0,0,'O');
    game.makePlay(0,2,'O');
    game.makePlay(1,1,'X');
    play = game.botFindWhereToPlay();
    assertTrue(play.is( 0, 1, 'X'));

    game = new Game();
    game.makePlay(0,0,'O');
    game.makePlay(0,1,'O');
    game.makePlay(1,1,'X');
    play = game.botFindWhereToPlay();
    assertTrue(play.is( 0, 2, 'X'));

    game = new Game();
    game.makePlay(0,0,'O');
    game.makePlay(1,0,'O');
    game.makePlay(1,1,'X');
    play = game.botFindWhereToPlay();
    assertTrue(play.is( 2, 0, 'X'));

    game = new Game();
    game.makePlay(0,0,'O');
    game.makePlay(1,1,'O');
    game.makePlay(1,0,'X');
    play = game.botFindWhereToPlay();
    assertTrue(play.is( 2, 2, 'X'));
  }

  @Test
  public void canCreateAFork() throws Exception {
    Play play;
    game.makePlay(0,0,'X');
    game.makePlay(2,0,'X');
    game.makePlay(1,0,'O');
    play = game.botFindWhereToPlay();
    assertTrue(play.is( 0, 2, 'X') || play.is(2,2, 'X'));
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
    private int[] rowScore;
    private int[] columnScore;

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

    private Play botFindWhereToPlay() throws InvalidPlayException, CanFindAPlayException {
      Play play;
      play = findWinnerPlay('X');
      if(play.isValid())
        return play;
      
      play = findBlockPlay();
      if(play.isValid())
        play.setSymbol('X');

      int winnerPlays = 0;
      for (int i = 0 ; i < 3 ; i++) {
        for (int j = 0 ; j < 3 ; j++) {
          boolean changed = false;
          char prev =' ';
          if (board[i][j] == ' '){
            prev = board[i][j];
            board[i][j] = 'X';
            changed =true;
            if (findWinnerPlayInRowOrColumn('X').isValid())
              winnerPlays++;
            if(findWinnerPlayInDiagonal('X').isValid())
              winnerPlays++;
          }
          if (winnerPlays == 2){
            play.setMove(i,j,'X');
          }else if(changed){
            board[i][j] = prev;
            winnerPlays = 0;
          }
        }
      }

      return play;
    }

    private Play findBlockPlay() {
      return findWinnerPlay('O');
    }

    private Play findWinnerPlay(char symbol) {

      Play play;
      play = findWinnerPlayInRowOrColumn(symbol);

      if(play.isValid())
        return play;

      play = findWinnerPlayInDiagonal(symbol);
      return play;
    }

    private Play findWinnerPlayInDiagonal(char symbol) {
      Play playOne = new Play();
      Play playTwo = new Play();
      int diagonalOne = 0;
      int diagonalTwo = 0;
      for(int i = 0; i < 3 ; i++){
        if(board[i][i] == symbol)
          diagonalOne++;
        else if(board[i][i] == ' ')
          playOne.setMove(i, i, symbol);
        if(board[i][2-i] == symbol)
          diagonalTwo++;
        else if(board[i][2 -i] == ' ')
          playTwo.setMove(i, 2 -i, symbol);
      }
      if(diagonalOne == 2)
        return playOne;
      if(diagonalTwo == 2)
        return playTwo;
      return new Play();
    }

    private Play findWinnerPlayInRowOrColumn(char symbol) {
      countRowsAndColumnsScores(symbol);
      Play play= new Play();
      for(int i = 0; i < 3 ; i++){
        if(columnScore[i] == 2) {
          for (int j = 0; j < 3; j++)
            if (rowScore[j] == 0 && board[j][i] == ' ')
              play.setMove(j, i, symbol);
        }
        if(rowScore[i] == 2)
        {
          for (int j = 0; j < 3; j++)
            if (columnScore[j] == 0 && board[i][j] == ' ')
              play.setMove(i, j, symbol);
        }
      }
      return play;
    }

    private void countRowsAndColumnsScores(char symbol) {
      rowScore = new int[]{0,0,0};
      columnScore = new int[]{0,0,0};
      for (int i = 0 ; i < 3 ; i++) {
        for (int j = 0 ; j < 3 ; j++) {
          if (board[i][j] == symbol){
            rowScore[i]++;
            columnScore[j]++;
          }
        }
      }
    }

    private void fillTheBoard(char symbol) {
      for (int i = 0 ; i < 3 ; i++)
        for (int j = 0 ; j < 3 ; j++)
          board[i][j] = symbol;
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

    public boolean is(int x, int y, char c) {
      return (this.x == x && this.y == y && this.symbol == c);
    }

    public void setSymbol(char symbol) {
      this.symbol = symbol;
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