package juego1;


public class Tablero {
	int[][] pirata;
	int[][] tesoro;
	int[][]tablero;
	int[][]agua;


	int posPiratai =0,posPirataj =0;
	int posTesoroi =0,posTesoroj =0;
	int temp=0;

	public Tablero() {
		// TODO Auto-generated constructor stub
	}

	public void inicializarTablero(int dificultad){

		tablero = new int[dificultad][dificultad];
		pirata = new int[dificultad][dificultad];
		tesoro = new int[dificultad][dificultad];
		agua = new int[dificultad][dificultad];

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

		do {
			posPiratai = 1 + (int)(Math.random() * (dificultad - 2));
			posPirataj = 1 + (int)(Math.random() * (dificultad - 2));
		} while (tablero[posPiratai][posPirataj] != 0);

		do {
			posTesoroi = 1 + (int)(Math.random() * (dificultad - 2));
			posTesoroj = 1 + (int)(Math.random() * (dificultad - 2));
		} while ((posTesoroi == posPiratai && posTesoroj == posPirataj) || tablero[posTesoroi][posTesoroj] != 0);

		tablero[posPiratai][posPirataj] = 2;
		tablero[posTesoroi][posTesoroj] = 3;

		int vidasPerdidas=0;
		int numMov=0;
		do {
			int mov = (int)(Math.random() * 4);
			switch  (mov){
			case 0:{
				if (tablero[posPiratai+1][posPirataj] == 1) {
					System.out.println("el pirata cayó al agua");
					vidasPerdidas++;
					break;
				}
				tablero[posPiratai][posPirataj] =0;
				tablero[posPiratai+1][posPirataj] = 2;
				posPiratai +=1;
			}break;
			case 1:{
				if (tablero[posPiratai][posPirataj+1] == 1) {
					System.out.println("el pirata cayó al agua");
					vidasPerdidas++;
					break;
				}
				tablero[posPiratai][posPirataj] =0;
				tablero[posPiratai][posPirataj+1] = 2;
				posPirataj+=1;
			}break;
			case 2:{
				if (tablero[posPiratai-1][posPirataj] == 1) {
					System.out.println("el pirata cayó al agua");
					vidasPerdidas++;
					break;
				}
				tablero[posPiratai][posPirataj] =0;
				tablero[posPiratai-1][posPirataj] = 2;
				posPiratai-=1;
			}break;
			case 3:{
				if (tablero[posPiratai][posPirataj-1] == 1) {
					System.out.println("el pirata cayó al agua");
					vidasPerdidas++;
					break;
				}
				tablero[posPiratai][posPirataj] =0;
				tablero[posPiratai][posPirataj-1] = 2;
				posPirataj-=1;
			}break;
			}
			numMov++;
		} while (2 != tablero[posTesoroi][posTesoroj]);
		if(numMov>1) {
			System.out.println("El pirata llego al tesoro en " + numMov +" movimientos");
		} else {
			System.out.println("El pirata llego al tesoro en " + numMov +" movimiento");
		}
		System.out.println("Numero de muertes: " + vidasPerdidas);
		System.out.println("Numero de movimientos:" + numMov);
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
