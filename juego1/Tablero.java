package juego1;

public class Tablero {
	int[][] pirata;
	int[][] tesoro;
	int[][]tablero;
	
	public void inicializarTablero(int dificultad){
		tablero = new int[dificultad][dificultad];
		boolean exist=false;
		int posPiratai = 0,posPirataj=0;
		for (int i =0; i<tablero.length;i++) {
			for (int j =0; i<tablero[0].length;j++) {
				if (((int)Math.random()*10) == 2) {
					pirata[i][j]=1;
					posPiratai=i;posPirataj=j;
					exist=true;
				}
				
				if (exist) break;
			}
			if (exist) break;
		}
		exist=false;
		for (int i =0; i<tablero.length;i++) {
			for (int j =0; i<tablero[0].length;j++) {
				if (((int)Math.random()*10) == 2&&pirata[posPiratai][posPirataj]!=1) {
					tesoro[i][j]=1;
					exist=true;
				}
				if (exist) break;
			}
			if (exist) break;
		}
		
		
	}
}
