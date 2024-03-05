import java.util.*;

public class Game {
  private Sudoku sudoku;

  Game(Sudoku sudoku) {
    this.sudoku = sudoku;
  }
  private int amountOfPolls = 0; // Counter to keep track of the number of polls during the AC-3 algorithm.

  public void showSudoku() {
    System.out.println(sudoku);
  }

  /**
   * Implementation of the AC-3 algorithm
   *
   * @return true if the constraints can be satisfied, else false
   */
  public boolean solve() {
    PriorityQueue<Field> queue = new PriorityQueue<>((a, b) -> a.getNeighbourAmount() - b.getNeighbourAmount());

    // Initialize the priority queue with empty cells
    for (int i = 0; i < 9; i++) {
      for (int j = 0; j < 9; j++) {
        if (sudoku.getBoard()[i][j].getValue() == 0) {
          queue.add(sudoku.getBoard()[i][j]);
        }
      }
    }

    // Process the queue until it's empty
    while (!queue.isEmpty()) {
      Field currentField = queue.poll();
      amountOfPolls++; // Increment counter when polling a field from the queue.

      if (revise(currentField)) {
        if (currentField.getDomainSize() == 0) {
          // Inconsistent assignment, backtracking needed
          return false;
        }

        // Add neighbors to the queue if their domain is affected
        for (Field neighbor : currentField.getNeighbours()) {
          if (neighbor.getDomainSize() > 1) {
            queue.add(neighbor);
          }
        }
      }
    }

    return true; // Constraints satisfied successfully
  }

  // Reduce the domain of a variable by eliminating inconsistent values
  private boolean revise(Field currentField) {
    boolean revised = false;

    for (int value : new ArrayList<>(currentField.getDomain())) {
      if (!isValueConsistent(currentField, value)) {
        currentField.removeFromDomain(value);
        revised = true;
      }
    }

    return revised;
  }

  // Check if a value is consistent with the neighbors of a field
  private boolean isValueConsistent(Field field, int value) {
    for (Field neighbor : field.getNeighbours()) {
      if (neighbor.getValue() == value) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks the validity of a sudoku solution
   * 
   * @return true if the sudoku solution is correct
   */
  public boolean validSolution() {
    // TODO: implement validSolution function
    Field[][] board = sudoku.getBoard();
    //check horizontally
    for (int c = 0; c < 8; c++) {
      for (int r = 0; r < 9; r++) {
        for (int i = c + 1; i < 9; i++) {
          if (board[r][c].getValue() == board[r][i].getValue() || board[r][c].getValue() == 0) {
            return false;
          }
        }
      }
    }
    //check vertically
    for (int c = 0; c < 9; c++) {
      for (int r = 0; r < 8; r++) {
        for (int i = r + 1; i < 9; i++) {
          if (board[r][c].getValue() == board[i][c].getValue() || board[r][c].getValue() == 0) {
            return false;
          }
        }
      }
    }
    //check 3x3
    for (int c = 0; c < 9; c += 3) {
      for (int r = 0; r < 9; r += 3) {
        //checking each square in each 3x3
        for (int collum = 0; collum < 8; collum++) {
          for (int row = collum+1; row < 9; row++) {
            if (board[r + row % 3][c + row / 3].getValue() == board[r + collum % 3][c + collum / 3].getValue())
              return false;
          }
        }
      }
    }
    return true;
  }
  // Getter method to retrieve the amount of polls made during the AC-3 algorithm
  public int getAmountOfPolls() {
    return amountOfPolls;
  }
}
