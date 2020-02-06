/**
 * The Competition class represents a Nim competition between two players, consisting of a given number of
 * rounds. It also keeps track of the number of victories of each player.
 */
import java.util.Scanner;

public class Competition {

    Player player1,player2;
    boolean displayMessage;
    private int p1wins = 0;//player 1 wins counter
    private int p2wins = 0;//player 2 wins counter

    /**
     *
     * @param player1 player2 Receives two Player objects, representing the two competing opponents
     * @param displayMessage a flag indicating whether game play messages should be printed to the console
     */
    public Competition(Player player1, Player player2 , boolean displayMessage){
        this.player1 = player1;
        this.player2 = player2;
        this.displayMessage = displayMessage;
    }

    /**
     *
     * @param playerPosition Player number
     * @returnIf the results of the player is returned.
     */
    public int getPlayerScore(int playerPosition){
        if (playerPosition == 1){return p1wins;}
        else {return p2wins;}
    }

    /**
     * Run the game for the given number of rounds
     * @param numberOfRounds number of rounds to play
     */
    public void playMultipleRounds(int numberOfRounds){
        for(int i = 0; i<numberOfRounds;i++){playOneRound();}
        System.out.println("The results are "+getPlayerScore
                (1)+":"+getPlayerScore(2));
    }


    /**
     * Runs one round of the Nim game
     */
    private void playOneRound(){
        Board board = new Board();// initialization borad game
        boolean nowPlay = true;//Marks whose turn it is true-->player 1, false-->player 2
        if (displayMessage) {System.out.println("Welcome to the sticks game!");}
        while (board.getNumberOfUnmarkedSticks() != 0) {
            if (nowPlay){//Player 1 turn
                if (displayMessage){System.out.println("Player 1, it is now your turn!");}
                playMove(board,player1);
                nowPlay = false; //transfer turn to  player 2
            }
            else { //Player 2 turn
                if (displayMessage){System.out.println("Player 2, it is now your turn!");}
                playMove(board, player2);
                nowPlay = true;//transfer turn to  player 1
            }
        }
        if(!nowPlay){//This means that a player 2 won .Because player 1 has last played
            p2wins++;
            if(displayMessage){System.out.println("Player 2 won!");}
        }
        else{//This means that a player 1 won .Because player 2 has last played
            p1wins++;
            if(displayMessage){System.out.println("Player 1 won!");}
        }
    }

    /**
     * A method that runs one step
     * @param board The current game board
     * @param player The player whose turn to play
     */
    private void playMove(Board board, Player player){
        Move curStep = player.produceMove(board);//Initialization step game
        while (board.markStickSequence(curStep) != 0){//Testing if the step is right
            if (displayMessage) {System.out.println("Invalid move. Enter another:");}
            curStep = player.produceMove(board);//Initialization new step game to get right step
        }
        if (displayMessage){System.out.println("Player "+player.getPlayerId()+" made the move: "
                +curStep.toString());}
    }

    /**
     * The method runs a Nim competition between two players according to the three user-specified arguments.
     * (1) The type of the first player, which is a positive integer between 1 and 4: 1 for a Random computer
     *     player, 2 for a Heuristic computer player, 3 for a Smart computer player and 4 for a human player.
     * (2) The type of the second player, which is a positive integer between 1 and 4.
     * (3) The number of rounds to be played in the competition.
     * @param args an array of string representations of the three input arguments, as detailed above.
     */
    public static void main(String[] args) {

        int p1Type = Integer.parseInt(args[0]);
        int p2Type = Integer.parseInt(args[1]);
        int numGames = Integer.parseInt(args[2]);
        Scanner scanner = new Scanner(System.in);
        boolean displayMessage;

        Player player1 = new Player(p1Type, 1, scanner);
        Player player2 = new Player(p2Type, 2, scanner);

        //check the flag indicating whether game play messages should be printed to the console
        if (player1.getTypeName().equals("Human") || player2.getTypeName().equals("Human")){
            displayMessage = true;}
        else {displayMessage = false;}

        Competition competition = new Competition(player1,player2,displayMessage);
        //Initialization Competition

        System.out.println("Starting a Nim competition of "+ numGames +" rounds between a " +
                player1.getTypeName() + " player and a " + player2.getTypeName() + " player.");
        competition.playMultipleRounds(numGames);//runs Competition
        scanner.close();
    }
}
