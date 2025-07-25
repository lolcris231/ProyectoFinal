package juego1;


public class Tablero {
	int[][] pirata;
	int[][] tesoro;
	int[][]tablero;
	int[][]agua;
	
	public Tablero() {
		// TODO Auto-generated constructor stub
	}

	public void inicializarTablero(int dificultad){
		tablero = new int[dificultad][dificultad];
		pirata = new int[dificultad][dificultad];
		tesoro = new int[dificultad][dificultad];
		agua = new int[dificultad][dificultad];
		int posPiratai =0,posPirataj =0;
		int posTesoroi =0,posTesoroj =0;
		int temp=0;
		
		for (int i=0;i<dificultad;i++) {
			for (int j=0;j<tablero[0].length;j++) {
				tablero[i][j] = 0;
				pirata[i][j]=0;
				tesoro[i][j]=0;
				agua[i][j]=0;
			}
		}
		
		//agua
		for (int i=0;i<dificultad;i++) {
			tablero[i][0] = 1;
			tablero[0][i] = 1;
			tablero[i][dificultad-1] = 1;
			tablero[dificultad-1][i] = 1;
		}
		temp = (int) (Math.random()*dificultad);
		if (temp !=0 && temp!= dificultad) { 
			posPiratai = temp;
		}
		temp = (int) (Math.random()*dificultad);
		if (temp !=0 && temp!= dificultad) posPirataj = temp;
		
		temp = (int) (Math.random()*dificultad);
		if( temp != posPiratai && temp !=0 && temp!= dificultad) posTesoroi=temp;
		temp = (int) (Math.random()*dificultad);
		if( temp != posPirataj && temp !=0 && temp!= dificultad) posTesoroj=temp;
		
		tablero[posPiratai][posPirataj] = 2;
		tablero[posTesoroi][posTesoroj] = 3;
		
	}
	
	public void imprimirTablero () {
		for (int i=0; i<tablero.length;i++) {
			for(int j=0;j<tablero[0].length;j++) {
				System.out.print(tablero[i][j] + " ");
			}
			System.out.println();
		}
	}

	public int[][] getPirata() {
		return pirata;
	}

	public void setPirata(int[][] pirata) {
		this.pirata = pirata;
	}

	public int[][] getTesoro() {
		return tesoro;
	}

	public void setTesoro(int[][] tesoro) {
		this.tesoro = tesoro;
	}

	public int[][] getTablero() {
		return tablero;
	}

	public void setTablero(int[][] tablero) {
		this.tablero = tablero;
	}

	public int[][] getAgua() {
		return agua;
	}

	public void setAgua(int[][] agua) {
		this.agua = agua;
	}
	
	
}
