import java.util.Random;

public class GameItem {
		private char symbol;
		private Random randomGenerator = new Random(); 
		
		protected GameItem(char symbol){
			this.symbol = symbol;
		}
		char getSymbol(){
			return symbol;
		}
		public void display(){
			  System.out.print(" "+symbol+" ");
		}
		
		public void GameItemRandom(GameItem item, GameItem board[][]){
			
			if(getEmptyCellamount(board) <= 0){
				
				System.out.println("error !");
				return;
			}
			
			int row,column;
			
			do{
				row = randomGenerator.nextInt(4);
				column = randomGenerator.nextInt(4);
				 
			} while(board[row][column] != null);
			
			board[row][column] = item;
			
		}
		
		public int getEmptyCellamount(GameItem[][] board){
			
			int amount = 0;
			for(int i = 0 ; i < board.length ; i ++)
				for(int j = 0 ; j <board[i].length ; j ++)
					if(board[i][j] == null) amount ++;
			
			return amount;
		}

}
