import java.util.*;

public class Tarea1
{
    public void agregarEstado(ArrayList lista, int contador)
    {
        String estado = crearEstado(contador);
        lista.add(estado);
    }
    
    public String crearEstado(int contador)
    {
        //contador++;
        return ("q" + contador);
    }
    
    public String obtenerTransicion(int estadoI, int sigmaJ, ArrayList<String> states, ArrayList<Character> sigma, ArrayList<Trans> trans)
    {
        String estadoL = states.get(estadoI);
        char sigmaL = sigma.get(sigmaJ);
        
        String transicion = "";
        
        for (int i = 0; i < trans.size(); i++)
        {
            if(trans.get(i).getStart().equals(estadoL) && trans.get(i).getCharacter() == '_')
            {
                estadoL = trans.get(i).getEnd();
            }
            
            if(trans.get(i).getStart().equals(estadoL) && trans.get(i).getCharacter() == sigmaL)
            {
                String local = (trans.get(i).getEnd() + " ");
                transicion = (transicion + local);
            }
        }
        return transicion;
    }

	public static void main(String[] args) {
		new Tarea1();
    }

    public Tarea1()
    {
        Scanner sc = new Scanner(System.in);
                //System.out.println("Ingrese la expresion regular: ");
		String regex = sc.nextLine();
                //System.out.println("Ingrese el texto: ");
		String text = sc.nextLine();

		//hacer un split para quitar los | 

		ArrayList<Character> sigma = new ArrayList<>();
		ArrayList<String> states = new ArrayList<>();
		ArrayList<Trans> trans = new ArrayList<>();
        String s;
        ArrayList<String> finals = new ArrayList<>();

		String q = "q0";
                s = q;
		int cont = 1;

		states.add(q);
        String[] textSplit = regex.split("[|]");
                
                /*for (int i = 0; i < textSplit.length; i++)
                {
                    System.out.println("j: " + textSplit[i]);

                }*/
                
                //clausula |
                 //agregar estados             
                //agregarEstado(states, cont);
                //agregarEstado(states, cont);
                
                
        //for (int i = 0; i < textSplit.length; i++)
        //{
            System.out.println("s: " + states.get(0));            
        //}
		
         
		for (int j = 0;j < textSplit.length ;j++ ) {
				
			char[] characters = textSplit[j].toCharArray();
			String start = crearEstado(cont);
			cont++;
			states.add(start);
			Trans t = new Trans(s, start, '_');
                        trans.add(t);
			String aux = "a"; //parche 

			for (int i = 0; i < characters.length ; i++) {
				if(characters[i] != '.' && characters[i] != '*')
				{
					String stat = crearEstado(cont);
					cont++;
					states.add(stat);
					Trans t1 = new Trans(start, stat, characters[i]);
					trans.add(t1);

					start = stat;
				}

				if(characters[i] == '.')
				{
					String stat = crearEstado(cont);
					cont++;
					states.add(stat);
					Trans t1 = new Trans(start, stat, '_');
					trans.add(t1);

					start = stat;
				}
				else if(characters[i] == '*')
				{

					String end = crearEstado(cont);
					states.add(end);
					cont++;
					Trans revT = new Trans(end,start, '_');
					trans.add(revT);

					String aux2 = crearEstado(cont);
					cont++;
					states.add(aux2);
					Trans t2 = new Trans(start, aux2, '_');
					Trans t3 = new Trans(end, aux2, '_');
					trans.add(t2);
					trans.add(t3);

					start = aux2;
		
				}
				
				else
				{
					if(!sigma.contains(characters[i])){sigma.add(characters[i]);}
				}	
			}

			finals.add(start);

		}

		String finalState = crearEstado(cont);
		states.add(finalState);

		for(int i = 0; i < finals.size() ; i++)
		{
			Trans t = new Trans(finals.get(i), finalState, '_');
			trans.add(t);
		}

		System.out.print("Estados: ");
		for (int i = 0; i < states.size()-1; i++) {
			System.out.print(states.get(i) + ", ");
		}
		System.out.println(states.get(states.size()-1));
		
		System.out.print("Transiciones: ");
		for (int i = 0; i < trans.size(); i++ ) {
			trans.get(i).printTransition();
		}

                
        //conversion a AFND
	String[][] matriz = new String[states.size()][sigma.size()+1];
        for (int i = 0; i < states.size(); i++)
        {
            for (int j = 0; j < sigma.size()+1; j++)
            {
                if(j == 0)
                    matriz[i][j] = states.get(i);
                
                else if(j <= sigma.size() )
                    matriz[i][j] = obtenerTransicion(i, j-1, states, sigma, trans);
            }
            
        }
        System.out.println("matriz");
        for (int i = 0; i < states.size(); i++)
        {
            for (int j = 0; j < sigma.size()+1; j++)
            {
                System.out.println("i: " + i + " j: " + j + " " +matriz[i][j]);
            }
        }
        
        
    }
}


class Trans
{
	String start, end;
	char character;

	public Trans(String s, String e, char c)
	{
		this.start = s;
		this.character = c;
		this.end = e;
                
	} 

        public String getStart()
        {
            return start;
        }

        public String getEnd()
        {
            return end;
        }

        public char getCharacter()
        {
            return character;
        }

	public void printTransition()
	{
		System.out.print(this.start + " , ");
		System.out.print(this.character + " , ");
		System.out.println(this.end);
	}
}