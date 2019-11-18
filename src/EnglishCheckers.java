import java.util.Scanner;
import java.lang.Math;

public class EnglishCheckers {

	// Global constants
	public static final int RED   = 1;
	public static final int BLUE  = -1;
	public static final int EMPTY = 0;

	public static final int SIZE  = 8;

	// You can ignore these constants
	public static final int MARK  = 3;
	public static EnglishCheckersGUI grid;

	public static Scanner getPlayerFullMoveScanner = null;
	public static Scanner getStrategyScanner = null;

	public static final int RANDOM			= 1;
	public static final int DEFENSIVE		= 2;
	public static final int SIDES				= 3;
	public static final int CUSTOM			= 4;


	public static void main(String[] args) {

		// ******** Don't delete ********* 
		// CREATE THE GRAPHICAL GRID
		grid = new EnglishCheckersGUI(SIZE);
		// ******************************* 


		//showBoard(example);
		showBoard(createBoard());
		//printMatrix(example);
		printMatrix(createBoard());
		
		//interactivePlay();
		//twoPlayers();


		/* ******** Don't delete ********* */    
		if (getPlayerFullMoveScanner != null){
			getPlayerFullMoveScanner.close();
		}
		if (getStrategyScanner != null){
			getStrategyScanner.close();
		}
		/* ******************************* */

	}

//make new Board with Discs in Starting positions. 
	public static int[][] createBoard() {
		
		int[][] board = new int [8][8];
		//printing the red solduers
		for (int line =0,column=0;line<3;line=line+1){
			if (line%2==0){column=0;}
			else {column=1;}
			for (;column<8;column=column+2){
				board [line][column] = 1;
			}
		}
		//printing the blu solduers
		for (int line =5,column=0;line<8;line=line+1){
			if (line%2==0){column=0;}
			else {column=1;}
			for (;column<8;column=column+2){
				board [line][column] = -1;
			}
		}			
		return board;
	}

//how match player Discs on Board (input- bord Current & Which pleyer(red or blue) ,return map (x,y) wher the Soldier)
	public static int[][] playerDiscs(int[][] board, int player) {
			
		int SumP = 0;
		//how many solduers do the red player have
		if (player==1){
			for (int line=0;line<8;line=line+1){
				for (int column=0;column<8;column=column+1){
					if (board[line][column]>0){SumP=SumP+1;}	
				}				
			}
		}
		//how many solduers do the blue player have
		if (player==-1){
			for (int line=0;line<8;line=line+1){
				for (int column=0;column<8;column=column+1){
					if (board[line][column]<0){SumP=SumP+1;}
				}
			}
		}
		//printing the arry whith the solduers
		int[][] positions = new int [SumP][2];
			for (int line=0,i=0;line<8;line=line+1){
				for (int column=0;column<8;column=column+1){
					if (player==1){
						if (player<=board[line][column]){
							positions [i][0] = line;
							positions [i][1] = column;
							i=i+1;
						}
					}
					if (player==-1){
						if (player>=board[line][column]){
							positions [i][0] = line;
							positions [i][1] = column;	
							i=i+1;
						}
					}
				}	
			}
		return positions;
	}

//input Which player and when he want to move, return if is Basic Move Valid
	public static boolean isBasicMoveValid(int[][] board, int player, int fromRow, int fromCol, int toRow, int toCol) {
			
		boolean ans = false;
		
		/*Checking if coordinates are OK*/
		if(fromRow<8&fromCol<8&toRow<8&toCol<8&fromRow>-1&fromCol>-1&toRow>-1&toCol>-1&player>-2&player<2&player!=0){
			if (board [toRow][toCol]==0){/*Checks if the tile is empty*/
				if (player==1){/*Checks if player is red*/
					if (board[fromRow][fromCol]==1){/*Checks if in tile there is soldier red */
						if(fromRow==toRow-1&&(fromCol==toCol+1||fromCol==toCol-1)){/*Did he move diagonally upward*/
							ans = true;
						}
					}	
					if (board[fromRow][fromCol]==2){/*Checks if in tile there is queen red*/
						if(((fromRow==toRow+1)||(fromRow==toRow-1))&&((fromCol==toCol+1)||(fromCol==toCol-1))){/*Did he move diagonally */
							ans = true;
						}
					}	
				}
				if (player==-1){/*Checks if player is blue*/
					if (board[fromRow][fromCol]==-1){/*Checks if in tile there is soldier blue */
						if(fromRow==toRow+1&(fromCol==toCol+1||fromCol==toCol-1)){/*Did he move diagonally down*/
							ans = true;
						}
					}
					if (board[fromRow][fromCol]==-2){/*Checks if in tile there is queen blue*/
						if(((fromRow==toRow+1)||(fromRow==toRow-1))&&((fromCol==toCol+1)||(fromCol==toCol-1))){/*Did he move diagonally */
							ans = true;
						}
					}
				}
			}
		}
		return ans;
	}

//input- bord Current & Which pleyer(red or blue), return all Basic Move Valid
	public static int[][] getAllBasicMoves(int[][] board, int player) {
		
		int[][] moves = new int [0][4];
		int[][] SaveMoves = new int [0][4];/*save our moves of the soldures*/
		int[][] positions = playerDiscs(board,player);
		int SumAllMove=0;/*varibel ho count all the posible moves*/
		
		for(int IPD=0;IPD<positions.length;IPD=IPD+1){/*running on all the solduers*/
			//tow loops running in the 4 moves of the soldures
			for(int indxMove1=-1;indxMove1<2;indxMove1=indxMove1+2){
				for(int indxMove2=-1;indxMove2<2;indxMove2=indxMove2+2){
					boolean MovePossible = isBasicMoveValid(board,player,positions[IPD][0],positions[IPD][1],positions[IPD][0]+indxMove1,positions[IPD][1]+indxMove2);/*is the move is legal*/
					if (MovePossible){/*if the move legal enter the move in the arry*/
						SumAllMove=SumAllMove+1;
						SaveMoves = moves;
						moves = new int [SumAllMove][4];
						moves [SumAllMove-1][0]=positions[IPD][0];
						moves [SumAllMove-1][1]=positions[IPD][1];
						moves [SumAllMove-1][2]=positions[IPD][0]+indxMove1;
						moves [SumAllMove-1][3]=positions[IPD][1]+indxMove2;
						if (SumAllMove>1) {
							for (int idxSAM1=SumAllMove-2;idxSAM1>-1;idxSAM1=idxSAM1-1){
							for (int idxSAM2=0;idxSAM2<4;idxSAM2=idxSAM2+1){
								moves[idxSAM1][idxSAM2]=SaveMoves[idxSAM1][idxSAM2];
								}	
							}
						}	
					}
				}
			}
		}
		return moves;
	}

//input Which player and where he want to move (to eat), return if is Valid
	public static boolean isBasicJumpValid(int[][] board, int player, int fromRow, int fromCol, int toRow, int toCol) {
		
		boolean ans = false;
		//is the impot is legal
		if(fromRow<8&fromCol<8&toRow<8&toCol<8&fromRow>-1&fromCol>-1&toRow>-1&toCol>-1&player>-2&player<2&player!=0){
			if (board [toRow][toCol]==0){/*is the target slot avalibel*/
				if (board[fromRow][fromCol]==1||board[fromRow][fromCol]==2){/*is the player is red*/			
					if((board[(fromRow+toRow)/2][(fromCol+toCol)/2]==-1)||(board[(fromRow+toRow)/2][(fromCol+toCol)/2]==-2)){/*is thier enemy solduers between the begning and the target*/
						if (board[fromRow][fromCol]==1){/*is thie simpol soldiuer in the bignning slot*/
							if(fromRow==toRow-2&&(fromCol==toCol+2||fromCol==toCol-2)){/*is eating diagonally upward and the move is two square*/
								ans = true;
							}
						}
						else{/*is thir queen in the starting slot*/
							if(((fromRow==toRow+2)||(fromRow==toRow-2))&&((fromCol==toCol+2)||(fromCol==toCol-2))){
								/*if is eating diagonally and the move is two square*/
								ans = true;
							}
						}			
					}
				}
				if (board[fromRow][fromCol]==-1|board[fromRow][fromCol]==-2){/*is the solduer is blue*/
					if((board[(fromRow+toRow)/2][(fromCol+toCol)/2]==1)||(board[(fromRow+toRow)/2][(fromCol+toCol)/2]==2)){/*thie is an enemu betwen the teget and the begning*/
						if (board[fromRow][fromCol]==-1){
							if(fromRow==toRow+2&&(fromCol==toCol+2||fromCol==toCol-2)){/*is eating diagonally downward and the move is two square*/
								ans = true;
							}
						}
						else{
							if(((fromRow==toRow+2)||(fromRow==toRow-2))&&((fromCol==toCol+2)||(fromCol==toCol-2))){
								/*if is eating diagonally and the move is two square*/
								ans = true;
							}
						}			
					}
				}
			}
		}
		return ans;
	}

//input bord Current & Which pleyer & Location Discs , return all Jump Valid from it
	public static int [][] getRestrictedBasicJumps(int[][] board, int player, int row, int col) {
		
		int[][] moves = new int [0][4];
		int[][] SaveMoves = new int [0][4];/*save our moves of the soldures*/
		int SumAllMove=0; /*varibel ho count all the posible moves*/
		
		//tow loops running in the 4 Jumps of the soldures
		for(int indxMove1=-2;indxMove1<3;indxMove1=indxMove1+4){
			for(int indxMove2=-2;indxMove2<3;indxMove2=indxMove2+4){
				boolean MovePossible = isBasicJumpValid (board,player,row,col,row+indxMove1,col+indxMove2);/*Check if the move is legal*/
				if (MovePossible){/*if the move legal enter the move in the arry*/
					SumAllMove=SumAllMove+1;
					SaveMoves = moves;
					moves = new int [SumAllMove][4];
					moves [SumAllMove-1][0]=row;
					moves [SumAllMove-1][1]=col;
					moves [SumAllMove-1][2]=row+indxMove1;
					moves [SumAllMove-1][3]=col+indxMove2;			
					if (SumAllMove>1) {
						for (int idxSAM1=SumAllMove-2;idxSAM1>-1;idxSAM1=idxSAM1-1){
							for (int idxSAM2=0;idxSAM2<4;idxSAM2=idxSAM2+1){
								moves[idxSAM1][idxSAM2]=SaveMoves[idxSAM1][idxSAM2];
							}	
						}
					}	
				}
			}
		}
		return moves;
	}

//input bord Current & Which pleyer, return all Jump Valid
	public static int[][] getAllBasicJumps(int[][] board, int player) {
			
		int[][] moves = new int [0][4];
		int[][] SaveMoves = new int [0][4]; /*save our moves of the soldures*/
		int[][] positions = playerDiscs(board,player);
		int SumAllJump=0; /*varibel ho count all the posible Jumps*/
			
		for(int IPD=0;IPD<positions.length;IPD=IPD+1){/*running on all the solduers*/
			/*Building an array with all the move possible for a specific soldier*/
			int[][] JumpsPossible = getRestrictedBasicJumps (board,player,positions[IPD][0],positions[IPD][1]); 
			/*if the move legal enter the move in the arry*/
			if (JumpsPossible.length>0){/*If the soldier has jumps legality, From one with jumps by other soldiers*/				
				SumAllJump=SumAllJump+JumpsPossible.length;
				SaveMoves = moves;
				moves = new int [SumAllJump][4];
				if (SumAllJump>1) {
					for (int idxSAM1=SaveMoves.length-1;idxSAM1>-1;idxSAM1=idxSAM1-1){
						for (int idxSAM2=0;idxSAM2<4;idxSAM2=idxSAM2+1){
							moves[idxSAM1][idxSAM2]=SaveMoves[idxSAM1][idxSAM2];
						}
					}
				}			
				for(int indx=0;indx<JumpsPossible.length;indx=indx+1){
					moves [moves.length-JumpsPossible.length+indx][0]=JumpsPossible [indx][0];
					moves [moves.length-JumpsPossible.length+indx][1]=JumpsPossible [indx][1];
					moves [moves.length-JumpsPossible.length+indx][2]=JumpsPossible [indx][2];
					moves [moves.length-JumpsPossible.length+indx][3]=JumpsPossible [indx][3];
				}	
			}
		}	
		return moves;
	}

//input bord Current & Which pleyer, return If it has Jump Valid
	public static boolean canJump(int[][] board, int player) {
		
		boolean ans = false;
		int [][]AlBJ = getAllBasicJumps(board,player);
		
		if (AlBJ.length>0){ans	= true;}
		return ans;
	}

//input bord Current & Which pleyer & Location Discs & Location target, return If Is Move Valid (Jump/Basic Move)
	public static boolean isMoveValid(int[][] board, int player, int fromRow, int fromCol, int toRow, int toCol) {
		
		boolean ans = false;
		
		if (board[toRow][toCol]==0){/*is the target slot ia emtey*/
			if (player==1){
				if (board[fromRow][fromCol]==1||board[fromRow][fromCol]==2){ /*is the starting slot is red player queen olayer solduer*/
					if (isBasicJumpValid(board,player,fromRow,fromCol,toRow,toCol)){/*is the eating is legal*/
						ans = true;	
					}
					else{
						if (canJump(board,player)==false){/*is ther no eating jump?*/
							if (isBasicMoveValid(board,player,fromRow,fromCol,toRow,toCol)){/*is this is legal move*/
								ans = true;
							}
						}
					}
				}
			}		
			if (player==-1){
				if (board[fromRow][fromCol]==-1||board[fromRow][fromCol]==-2){/*is the starting slot is blue player queen olayer solduer*/
					if (isBasicJumpValid(board,player,fromRow,fromCol,toRow,toCol)){/*is the eating is legal*/
						ans = true;	
					}
					else{
						if (canJump(board,player)==false){/*is ther are no eating move*/
							if (isBasicMoveValid(board,player,fromRow,fromCol,toRow,toCol)){/*is the move is legal*/
								ans = true;
							}
						}
					}
				}		
			}
		}
		return ans;
	}

//input bord Current & Which pleyer , return If pleyer can to play
	public static boolean hasValidMoves(int[][] board, int player) {
		
		boolean ans = false;
		int[][]GABMoves=getAllBasicMoves(board,player);
		int[][]GABJumps=getAllBasicJumps(board,player);
		
		if (GABMoves.length==0){
			if (GABJumps.length==0){
				ans = true;
			}
		}
		return ans;
	}

//input bord Current & Which pleyer & Location Discs & Location target , return bord After changing
	public static int[][] playMove(int[][] board, int player, int fromRow, int fromCol, int toRow, int toCol) {
			
			boolean IBMV = isBasicMoveValid (board,player,fromRow,fromCol,toRow,toCol);
			boolean IBJV = isBasicJumpValid(board,player,fromRow,fromCol,toRow,toCol);
			int Discs = board[fromRow][fromCol];
			
			if (player==-1){//red qween crowns
				for (int i =1;i<8;i=i+2){
					if (board[7][i]==1){
						board[7][i]=2;
					}
				}	
			}	
			if (player==1){//blue qween crowns
				for (int i =0;i<8;i=i+2){
					if (board[0][i]==-1){
						board[0][i]=-2;
					}
				}
			}
			if (IBMV){/*operation step change*/
				board[fromRow][fromCol]	=0;
				board[toRow][toCol]=Discs;
			}
			if (IBJV){/*operation step eating*/
				board[fromRow][fromCol]	=0;
				board[(fromRow+toRow)/2][(fromCol+toCol)/2]=0;
				board[toRow][toCol]=Discs;
				
			}
			
			return board;
		}

//input bord Current & Which pleyer , return if the gama Over
	public static boolean gameOver(int[][] board, int player) {

		boolean ans = false;
		int [][] positions = playerDiscs(board,player);
		boolean MoveOrJumps  = hasValidMoves(board,player);

		if (positions.length==0||MoveOrJumps==true){
			ans = true;
		}
		return ans;
	}

//input bord Current, return Who leads
	public static int findTheLeader(int[][] board) {
		
		int ans = 0;
		int REDDiscs = playerDiscs(board,1).length;
		int BLUEDiscs = playerDiscs(board,-1).length;
		
		for (int line=0;line<8;line=line+1){
			for (int column=0;column<8;column=column+1){
				if (board[line][column]==2){REDDiscs=REDDiscs+1;}
			}
		}
		for (int line=0;line<8;line=line+1){
			for (int column=0;column<8;column=column+1){
				if (board[line][column]==-2){BLUEDiscs=BLUEDiscs+1;}
			}
		}		
		if (REDDiscs>BLUEDiscs){ans = 1;}
		if (REDDiscs<BLUEDiscs){ans = -1;}

		return ans;
	}

//Random strategic game for PC
	public static int[][] randomPlayer(int[][] board, int player) {
		
		int numrandom = (int)((Math.random())*100); /*varible to recive a random number*/
		int fromRow;
		int fromCol;
		int toRow;
		int toCol;
		/*builed array whit the all posibles jumps and choos one of them randomy*/
		if (canJump(board,player)){
				int [][] moveJump = getAllBasicJumps (board,player);
				numrandom = numrandom%(moveJump.length);
				fromRow = moveJump[numrandom][0];
				fromCol = moveJump[numrandom][1]; 
				toRow = moveJump[numrandom][2];
				toCol = moveJump[numrandom][3];
				board=playMove(board,player,fromRow,fromCol,toRow,toCol);
				int [][] morJump = getRestrictedBasicJumps(board,player,toRow,toCol);
				while (morJump.length>0){
					numrandom = numrandom%(morJump.length);
					fromRow = morJump[numrandom][0];
					fromCol = morJump[numrandom][1]; 
					toRow = morJump[numrandom][2];
					toCol = morJump[numrandom][3];
					board=playMove(board,player,fromRow,fromCol,toRow,toCol);
					morJump = getRestrictedBasicJumps(board,player,toRow,toCol);		
				}
		}				
		else {/*if ther are no posibele jums bulding an arry */
			int [][] BasicMove = getAllBasicMoves (board,player);
			numrandom = numrandom%(BasicMove.length);
			fromRow = BasicMove[numrandom][0];
			fromCol = BasicMove[numrandom][1];
			toRow = BasicMove[numrandom][2];
			toCol = BasicMove[numrandom][3];
			board=playMove(board,player,fromRow,fromCol,toRow,toCol);
		}
		
		return board;
	}

//defensive strategic game for PC
	public static int[][] defensivePlayer(int[][] board, int player) {
		
		int [][] BasicMove = getAllBasicMoves (board,player);
		int [][] savedefensMove = new int [BasicMove.length][4]; /*arry had all the definding moves*/
		int[][] boardnow = new int [8][8];
		int numrandom = (int)((Math.random())*100);/*reciving varible random*/
		int sumdefensMove=0;/*varible that count defince moves*/
		int fromRow=0;
		int fromCol=0;
		int toRow=0;
		int toCol=0;
		
		if (canJump(board,player)){/*if ther jump choosed randomaly*/
			board = randomPlayer(board,player);
		}
		else {
			//copieng the board 
			for (int line=0;line<8;line=line+1){
				for (int column=0;column<8;column=column+1){
					boardnow [line][column] = board[line][column];
				}
			}
			/*running an all the posible moves */
			for(int index=0;index<BasicMove.length;index=index+1){
				fromRow = BasicMove[index][0];
				fromCol = BasicMove[index][1];
				toRow = BasicMove[index][2];
				toCol = BasicMove[index][3];
				board = playMove(board,player,fromRow,fromCol,toRow,toCol);
				/*if we can do eating in the next jump*/
				if (canJump(board,(player*(-1)))==false){
					savedefensMove[sumdefensMove][0]=fromRow;
					savedefensMove[sumdefensMove][1]=fromCol;
					savedefensMove[sumdefensMove][2]=toRow;
					savedefensMove[sumdefensMove][3]=toCol;
					sumdefensMove=sumdefensMove+1;
				}
				/*copieng the board*/
				for (int line=0;line<8;line=line+1){
					for (int column=0;column<8;column=column+1){
						board[line][column] = boardnow[line][column];
					}
				}
			}
			/*if there is posible defence move chose one randomaly */
			if (sumdefensMove>0){
				numrandom = numrandom%sumdefensMove;
				fromRow = savedefensMove[numrandom][0];
				fromCol = savedefensMove[numrandom][1];
				toRow = savedefensMove[numrandom][2];
				toCol = savedefensMove[numrandom][3];
				board=playMove(board,player,fromRow,fromCol,toRow,toCol);
			}
			
			else{/*if there is no posible defence move chose one randomaly*/
				board=randomPlayer(boardnow,player);
			}
				
		}
		return board;
	}

//move sides strategic game for PC
	public static int[][] sidesPlayer(int[][] board, int player) {
		
		int [][] BasicMove = getAllBasicMoves (board,player);
		int [][] savesidesMove = new int [8][4];
		savesidesMove[0][0]= -1;
		int sumsidesMove=0;/*varible count all the moves to the sides*/
		int numrandom = (int)((Math.random())*100);/*varible to recive random number*/
		
		if (canJump(board,player)){/*if there is jump is choosed randomly*/
			board = randomPlayer(board,player);
		}
		else { /*if there is moves to tle lines 1-8*/
			for(int index=0;index<BasicMove.length;index=index+1){
				if(BasicMove[index][3]==7||BasicMove[index][3]==0){
					savesidesMove[sumsidesMove][0]=BasicMove[index][0];
					savesidesMove[sumsidesMove][1]=BasicMove[index][1];
					savesidesMove[sumsidesMove][2]=BasicMove[index][2];
					savesidesMove[sumsidesMove][3]=BasicMove[index][3];
					sumsidesMove=sumsidesMove+1;
				}
			}
			if (savesidesMove[0][0]==-1){/*if ther is no moves to 1 or 8 chick if there is posibble moves to 2 or 7*/
				for(int index=0;index<BasicMove.length;index=index+1){
					if(BasicMove[index][3]==6||BasicMove[index][3]==1){
						savesidesMove[sumsidesMove][0]=BasicMove[index][0];
						savesidesMove[sumsidesMove][1]=BasicMove[index][1];
						savesidesMove[sumsidesMove][2]=BasicMove[index][2];
						savesidesMove[sumsidesMove][3]=BasicMove[index][3];
						sumsidesMove=sumsidesMove+1;
					}
				}
			}
			if (savesidesMove[0][0]==-1){/*if ther is no moves to 2 or 7 chick if there is posibble moves to 3 or 6*/
				for(int index=0;index<BasicMove.length;index=index+1){
					if(BasicMove[index][3]==5||BasicMove[index][3]==2){
						savesidesMove[sumsidesMove][0]=BasicMove[index][0];
						savesidesMove[sumsidesMove][1]=BasicMove[index][1];
						savesidesMove[sumsidesMove][2]=BasicMove[index][2];
						savesidesMove[sumsidesMove][3]=BasicMove[index][3];
						sumsidesMove=sumsidesMove+1;
					}
				}
			}
			if (savesidesMove[0][0]==-1){/*if ther is no moves to 3 or 6 chick if there is posibble moves to 4 or 5*/
			board = randomPlayer(board,player);
			}
			if (savesidesMove[0][0]!=-1){/*doing a randomly moves of ehat he find to the side*/
				numrandom = numrandom%sumsidesMove;
				board=playMove(board,player,savesidesMove[numrandom][0],savesidesMove[numrandom][1],savesidesMove[numrandom][2],savesidesMove[numrandom][3]);
			}
			
		}
		
		
		return board;
	}







	
	
	
	
	
	//******************************************************************************//

	/* ---------------------------------------------------------- *
	 * Play an interactive game between the computer and you      *
	 * ---------------------------------------------------------- */
	public static void interactivePlay() {
		int[][] board = createBoard();
		showBoard(board);

		System.out.println("Welcome to the interactive Checkers Game !");

		int strategy = getStrategyChoice();
		System.out.println("You are the first player (RED discs)");

		boolean oppGameOver = false;
		while (!gameOver(board, RED) && !oppGameOver) {
			board = getPlayerFullMove(board, RED);

			oppGameOver = gameOver(board, BLUE);
			if (!oppGameOver) {
				EnglishCheckersGUI.sleep(200);

				board = getStrategyFullMove(board, BLUE, strategy);
			}
		}

		int winner = 0;
		if (playerDiscs(board, RED).length == 0  |  playerDiscs(board, BLUE).length == 0){
			winner = findTheLeader(board);
		}

		if (winner == RED) {
			System.out.println();
			System.out.println("\t *************************");
			System.out.println("\t * You are the winner !! *");
			System.out.println("\t *************************");
		}
		else if (winner == BLUE) {
			System.out.println("\n======= You lost :( =======");
		}
		else
			System.out.println("\n======= DRAW =======");
	}


	/* --------------------------------------------------------- *
	 * A game between two players                                *
	 * --------------------------------------------------------- */
	public static void twoPlayers() {
		int[][] board = createBoard();
		showBoard(board);

		System.out.println("Welcome to the 2-player Checkers Game !");

		boolean oppGameOver = false;
		while (!gameOver(board, RED)  &  !oppGameOver) {
			System.out.println("\nRED's turn");
			board = getPlayerFullMove(board, RED);

			oppGameOver = gameOver(board, BLUE);
			if (!oppGameOver) {
				System.out.println("\nBLUE's turn");
				board = getPlayerFullMove(board, BLUE);
			}
		}

		int winner = 0;
		if (playerDiscs(board, RED).length == 0  |  playerDiscs(board, BLUE).length == 0)
			winner = findTheLeader(board);

		System.out.println();
		System.out.println("\t ************************************");
		if (winner == RED)
			System.out.println("\t * The red player is the winner !!  *");
		else if (winner == BLUE)
			System.out.println("\t * The blue player is the winner !! *");
		else
			System.out.println("\t * DRAW !! *");
		System.out.println("\t ************************************");
	}


	/* --------------------------------------------------------- *
	 * Get a complete (possibly a sequence of jumps) move        *
	 * from a human player.                                      *
	 * --------------------------------------------------------- */
	public static int[][] getPlayerFullMove(int[][] board, int player) {
		// Get first move/jump
		int fromRow = -1, fromCol = -1, toRow = -1, toCol = -1;
		boolean jumpingMove = canJump(board, player);
		boolean badMove   = true;
		getPlayerFullMoveScanner = new Scanner(System.in);//I've modified it
		while (badMove) {
			if (player == 1){
				System.out.println("Red, Please play:");
			} else {
				System.out.println("Blue, Please play:");
			}

			fromRow = getPlayerFullMoveScanner.nextInt();
			fromCol = getPlayerFullMoveScanner.nextInt();

			int[][] moves = jumpingMove ? getAllBasicJumps(board, player) : getAllBasicMoves(board, player);
			markPossibleMoves(board, moves, fromRow, fromCol, MARK);
			toRow   = getPlayerFullMoveScanner.nextInt();
			toCol   = getPlayerFullMoveScanner.nextInt();
			markPossibleMoves(board, moves, fromRow, fromCol, EMPTY);

			badMove = !isMoveValid(board, player, fromRow, fromCol, toRow, toCol); 
			if (badMove)
				System.out.println("\nThis is an illegal move");
		}

		// Apply move/jump
		board = playMove(board, player, fromRow, fromCol, toRow, toCol);
		showBoard(board);

		// Get extra jumps
		if (jumpingMove) {
			boolean longMove = (getRestrictedBasicJumps(board, player, toRow, toCol).length > 0);
			while (longMove) {
				fromRow = toRow;
				fromCol = toCol;

				int[][] moves = getRestrictedBasicJumps(board, player, fromRow, fromCol);

				boolean badExtraMove = true;
				while (badExtraMove) {
					markPossibleMoves(board, moves, fromRow, fromCol, MARK);
					System.out.println("Continue jump:");
					toRow = getPlayerFullMoveScanner.nextInt();
					toCol = getPlayerFullMoveScanner.nextInt();
					markPossibleMoves(board, moves, fromRow, fromCol, EMPTY);

					badExtraMove = !isMoveValid(board, player, fromRow, fromCol, toRow, toCol); 
					if (badExtraMove)
						System.out.println("\nThis is an illegal jump destination :(");
				}

				// Apply extra jump
				board = playMove(board, player, fromRow, fromCol, toRow, toCol);
				showBoard(board);

				longMove = (getRestrictedBasicJumps(board, player, toRow, toCol).length > 0);
			}
		}
		return board;
	}


	/* --------------------------------------------------------- *
	 * Get a complete (possibly a sequence of jumps) move        *
	 * from a strategy.                                          *
	 * --------------------------------------------------------- */
	public static int[][] getStrategyFullMove(int[][] board, int player, int strategy) {
		if (strategy == RANDOM)
			board = randomPlayer(board, player);
		else if (strategy == DEFENSIVE)
			board = defensivePlayer(board, player);
		else if (strategy == SIDES)
			board = sidesPlayer(board, player);

		showBoard(board);
		return board;
	}


	/* --------------------------------------------------------- *
	 * Get a strategy choice before the game.                    *
	 * --------------------------------------------------------- */
	public static int getStrategyChoice() {
		int strategy = -1;
		getStrategyScanner = new Scanner(System.in);
		System.out.println("Choose the strategy of your opponent:" +
				"\n\t(" + RANDOM + ") - Random player" +
				"\n\t(" + DEFENSIVE + ") - Defensive player" +
				"\n\t(" + SIDES + ") - To-the-Sides player player");
		while (strategy != RANDOM  &  strategy != DEFENSIVE
				&  strategy != SIDES) {
			strategy=getStrategyScanner.nextInt();
		}
		return strategy;
	}


	/* --------------------------------------- *
	 * Print the possible moves                *
	 * --------------------------------------- */
	public static void printMoves(int[][] possibleMoves) {
		for (int i = 0;  i < 4;  i = i+1) {
			for (int j = 0;  j < possibleMoves.length;  j = j+1)
				System.out.print(" " + possibleMoves[j][i]);
			System.out.println();
		}
	}


	/* --------------------------------------- *
	 * Mark/unmark the possible moves          *
	 * --------------------------------------- */
	public static void markPossibleMoves(int[][] board, int[][] moves, int fromRow, int fromColumn, int value) {
		for (int i = 0;  i < moves.length;  i = i+1)
			if (moves[i][0] == fromRow  &  moves[i][1] == fromColumn)
				board[moves[i][2]][moves[i][3]] = value;

		showBoard(board);
	}


	/* --------------------------------------------------------------------------- *
	 * Shows the board in a graphic window                                         *
	 * you can use it without understanding how it works.                          *                                                     
	 * --------------------------------------------------------------------------- */
	public static void showBoard(int[][] board) {
		grid.showBoard(board);
	}


	/* --------------------------------------------------------------------------- *
	 * Print the board              					                           *
	 * you can use it without understanding how it works.                          *                                                     
	 * --------------------------------------------------------------------------- */
	public static void printMatrix(int[][] matrix){
		for (int i = matrix.length-1; i >= 0; i = i-1){
			for (int j = 0; j < matrix.length; j = j+1){
				System.out.format("%4d", matrix[i][j]);
			}
			System.out.println();
		}
	}

}
