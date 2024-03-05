public class App {
    public static void main(String[] args) throws Exception {
        start("Sudoku5.txt");
    }
    /*
     Quince Kersten S1073935
	 Gregors Lasenbergs S1075747
     */


    /**
     * Start AC-3 using the sudoku from the given filepath, and reports whether the sudoku could be solved or not, and how many steps the algorithm performed
     * 
     * @param filePath
     */
    public static void start(String filePath){
        Game game1 = new Game(new Sudoku(filePath));
        game1.showSudoku();

        if (game1.solve() && game1.validSolution()){
            System.out.println("Solved!");
        }
        else{
            System.out.println("Could not solve this sudoku :(");
        }
        System.out.println("Amount of fields polled from queue: "+game1.getAmountOfPolls());
        game1.showSudoku();
    }
}
