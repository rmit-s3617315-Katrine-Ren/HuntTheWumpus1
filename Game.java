import java.util.Random;
import java.util.Scanner;

public class Game {
	
	private static final int X = 0,Y = 1, left = 1, right=2, up=3, down=4;
	private GameItem board[][] = new GameItem[4][4];//{ X, Y}
	private int player[] = new int[2];//{ X, Y}coordinate
	private int score = 0;
	private int Goldamount = 0;
	private Random randomGenerator = new Random(); 
	private Scanner scanner = new Scanner(System.in);

	public void runGame(){
		    setBoard();
		    display();
			senseNearby();
			displayScore();
			menu();
			outer:while(true){
				int instruction = scanner.nextInt();
				switch(instruction){
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
				default:
					System.out.println("Game over!");
					displayScore();
					break outer;

				}
				if(board[player[X]][player[Y]] instanceof Gold){
					score ++;
					board[player[X]][player[Y]] = new ClearGround();
					if(score == Goldamount){
						System.out.println("Congrantation!!!You got all golds , win this game!");
						break outer;
					}
				
				}else if(board[player[X]][player[Y]] instanceof Pit){
					System.out.println("oh,sorry!You got the pit ,you lost this game!");
					break outer;
					
				}else if(board[player[X]][player[Y]] instanceof Wumpus){
					System.out.println("OH, sorry!!You met the Wumpus ,you lost this game! ");
					break outer;
				}
			
	display();
	senseNearby();
	displayScore();
	menu();
}
	}
	
	
	private void setBoard(){
		
		// Wumpus randomly
		GameItem wumpus = new Wumpus();
		wumpus.GameItemRandom(wumpus, board);
		
		// Golds randomly
		Goldamount = randomGenerator.nextInt(3)+1;
		for(int i = 0 ; i < Goldamount ; i ++){
			new Gold().GameItemRandom(new Gold(), board);
		}
		
		// Pits randomly
		for(int i = 0 ; i < 3 ; i ++){
			new Pit().GameItemRandom(new Pit(), board);
		}
		
		// random of player's position
		int position = randomGenerator.nextInt(16-1-Goldamount-3);
		   for(int i = 0 ; i < board.length ; i ++)
			for(int j = 0 ; j < board[i].length ; j ++) {
				if(board[i][j] == null){
					board[i][j] = new ClearGround();
					// player position
					if(position == 0){
						player[X] = i;
						player[Y] = j;
						position = 16;
					}else{
						position --;
					}
				}
			
			}
	}
	
	private void displayScore(){
		System.out.println("Your got the score is  :" + score);
		System.out.println();
		System.out.println();
		System.out.println();
	}

	private void menu(){
		System.out.println("_____Wumpus Guide_____    ");
		System.out.println("1.  Player move to left ");
		System.out.println("2.  Player move to right");
		System.out.println("3.  Player move to up");
		System.out.println("4.  Player move to down");
		System.out.println("5.      Quit Game");	
		
		System.out.println();
		System.out.println();
		System.out.println();
	}
	
	private void display(){
		
		
		System.out.println("_____Wumpus Game_____");
		
		System.out.println("        1  2  3  4 ");
		for(int i = 0 ; i < board.length ; i ++){
			System.out.print("    "+i+"  ");
			for(int j = 0 ; j < board[i].length ; j ++){
				if(player[X] == i && player[Y] == j){
					System.out.print(" * ");
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
		// left display
		y = player[X];
		x = player[Y]-1;
		if(x < 0) x = board[0].length-1;
		if(x >= board[0].length) x = board[0].length-1;
		System.out.println( "My left has"+ getSenseStrNearby(x,y) );
		//right display
		y = player[X];
		x = player[Y]+1;
		if(x < 0) x = board[0].length-1;
		if(x >= board[0].length) x = board[0].length-1;
		System.out.println("My right has"+ getSenseStrNearby(x,y));
		//top display
		y = player[X]-1;
		x = player[Y];
		if(y < 0) y = board.length-1;
		if(y >= board.length) y = 0;
		System.out.println("My top has" +  getSenseStrNearby(x,y));
		//bottom display
		y = player[X]+1;
		x = player[Y];
		if(y < 0) y = board.length-1;
		if(y >= board.length) y = 0;
		System.out.println( "My bottom has"+   getSenseStrNearby(x,y));
		System.out.println();
		System.out.println();
		System.out.println();
	}
	private String getSenseStrNearby(int x, int y){
		
		if(board[y][x] instanceof Gold){
			return "  gold ";
			
		}else if(board[y][x] instanceof Pit){
			return "   pit ";
			
		}else if(board[y][x] instanceof Wumpus){
			return "  wumpus Be careful! ";
			
		}else{
			return "  nothing ";
		}
		
	}

}