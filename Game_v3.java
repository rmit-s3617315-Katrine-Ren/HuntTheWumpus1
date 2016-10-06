package assignment2;

import java.util.Random;
import java.util.Scanner;

public class Game {
	
	private static final int X = 0,Y = 1, left = 1, right=2, up=3, down=4, end=5;
	private GameItem board[][] = new GameItem[4][4];/*Board array*/
	private int player[] = new int[2];/*Player coordinate*/
	private int score = 0, input;
	private boolean error;
	private int Goldamount=0, Goldmax = 3, Pitsno=3, Wumpusno = 1;
	private Random randomGenerator = new Random(); 
	private Scanner scanner = new Scanner(System.in);

	public void runGame(){
		    setBoard();
		    
		    display(); 
			senseNearby();
			displayScore();
			menu();
			
			/*Continue loop until game end
			 */
			while(true){
				System.out.println("Please enter your input : ");
				checkInput();
				movePlayer();
			  	winLose();			
				display();
				senseNearby();
				displayScore();
				menu();
			}
	}
	
	
	private void setBoard(){
		
		/*Generate Wumpus at random location*/
		GameItem wumpus = new Wumpus();
		wumpus.GameItemRandom(wumpus, board);
		
		/*Generate minimum of 1 gold randomly*/
		GameItem gold = new Gold();
		Goldamount = randomGenerator.nextInt(Goldmax)+1;
		for(int i = 0 ; i < Goldamount ; i ++){
			gold.GameItemRandom(gold, board);
		}
		
		/*Generate Pits at random location*/
		GameItem pit = new Pit();
		for(int i = 0 ; i < Pitsno ; i ++){
			pit.GameItemRandom(pit, board);
		}
		
		/* Randomize player's starting position */
		int position = randomGenerator.nextInt(16-Wumpusno-Goldamount-Pitsno);
		   for(int i = 0 ; i < board.length ; i ++)
			for(int j = 0 ; j < board[i].length ; j ++) {
				if(board[i][j] == null){
					board[i][j] = new ClearGround();
					/*Place Player*/
					if(position == 0){ // when position decrement reach 0, the game will
						player[X] = i; // replace player with ClearGound
						player[Y] = j;
						position = 16; // and initilze 16 to position and make it never 
					}else{                //meet the if condition"(position ==0 )" till the end of loop
						position --; //position will decrement with loop until it reach 0
					}
				}
			
			}
	}
	
	/*Display player current score*/
	private void displayScore(){
		System.out.println("Your score is  :" + score);
		System.out.println();

	}
	
	/*Display movement guide*/
	private void menu(){
		System.out.println("=====Wumpus Guide=====    ");
		System.out.println("1.Move player left ");
		System.out.println("2.Move player right");
		System.out.println("3.Move player up");
		System.out.println("4.Move player down");
		System.out.println("5.Quit Game\n");	

	}
	
	/*Display the game board*/
	private void display(){
		
		
		System.out.println("=====Wumpus Game=====");
		
		/*Print coordinates of the board and player position*/
		System.out.println("      A  B  C  D ");
		for(int i = 0 ; i < board.length ; i ++){
			System.out.print("  "+(i+1)+"  ");
			for(int j = 0 ; j < board[i].length ; j ++){
				if(player[X] == i && player[Y] == j){
					System.out.print("[*]");
				}else{
					board[i][j].display();
				}
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
		System.out.println();
		return;
	}
	
	private void senseNearby(){
		int x,y;
		/*Sense left of player position*/
		y = player[X];
		x = player[Y]-1;
		if(x < 0) x = board[0].length-1;
		System.out.println( "On my left "+ getSenseStrNearby(x,y));
		
		/*Sense right of player position*/
		y = player[X];
		x = player[Y]+1;
		if(x >= board[0].length) x = 0;
		System.out.println("On my right "+ getSenseStrNearby(x,y));
		
		/*Sense top of player position*/
		y = player[X]-1;
		x = player[Y];
		if(y < 0) y = board.length-1;
		System.out.println("At the top " +  getSenseStrNearby(x,y));
		
		/*Sense bottom of player position*/
		y = player[X]+1;
		x = player[Y];
		if(y >= board.length) y = 0;
		System.out.println( "At the bottom "+   getSenseStrNearby(x,y));
		System.out.println();

	}
	private String getSenseStrNearby(int x, int y){
		
		if(board[y][x] instanceof Gold){
			return "I see something glitter. ";
			
		}else if(board[y][x] instanceof Pit){
			return "I feel a draft. ";
			
		}else if(board[y][x] instanceof Wumpus){
			return "I smell something vile. ";
			
		}else{
			return "seems normal. ";
		}
		
	}
	
	private void checkInput(){
		/*Check input is an Integer or not
		 */
	  do{
		try{
			int instruction = scanner.nextInt();
			input = instruction;
			error = false;
		}
		catch(java.util.InputMismatchException e){
			System.out.println("Input error!!!\n"
					+"Please input only number between 1-5 : ");
			scanner.next();
			/*If input is an integer break the loop*/
			error = true;
		}
	  }while(error);
	}
	
	private void movePlayer(){
		 /*Take player input and move them
		 * */
		
		switch(input){
		case left:
			player[Y]--;
			if(player[Y] < 0) 
				player[Y] = board[0].length-1;
			if(player[Y] >=board[0].length) 
				player[Y] = 0;
			break;
		case right:
			player[Y]++;
			if(player[Y] < 0) 
				player[Y] = board[0].length-1;
			if(player[Y] >= board[0].length) 
				player[Y] = 0;
			break;
		case up:
			player[X]--;
			if(player[X] < 0) 
				player[X] = board.length-1;
			if(player[X] >= board.length) 
				player[X] = 0;
			break;
		case down:
			player[X]++;
			if(player[X] < 0) 
				player[X] = board.length-1;
			if(player[X] >= board.length) 
				player[X] = 0;
			break;
		case end:
			System.out.println("Game over!");
			displayScore();
			System.exit(0);

		default:
			System.out.println("\nPlease follow Wumpus Guide!"
					+ "\nInput only number between 1-5!\n");
			break;
		}
	}
	
	private void winLose(){
		/*Count score &
		 * Victory/defeat condition*/
		if(board[player[X]][player[Y]] instanceof Gold){
			score ++;
			board[player[X]][player[Y]] = new ClearGround(); /*Player picked up gold*/
			if(score == Goldamount){
				System.out.println("You got all golds!!!"+"\n You win this game!");
				System.exit(0);
			}
		
		}else if(board[player[X]][player[Y]] instanceof Pit){
			System.out.println("Oh no! You fell into the pit!!"+
					"\nYou lost this game!");
			System.exit(0);
			
		}else if(board[player[X]][player[Y]] instanceof Wumpus){
			System.out.println("Oh, no!! Wumpus caught you!!!"+
					"\n You lost this game! ");
			System.exit(0);
		}
	}

}
