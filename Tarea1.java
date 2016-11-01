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

			for (int i = 1; i < characters.length ; i++) {
				if(characters[i-1] == '.')
				{
					if( i-2 > 0 && characters[i-2] == '*')
					{
						String eFirst = crearEstado(cont);
						cont++;
						states.add(eFirst);
						Trans consumeTrans = new Trans(start, eFirst,'_');
						trans.add(consumeTrans);
 
						String sStart = crearEstado(cont);
						cont++;
						states.add(sStart);
						Trans t2 = new Trans(eFirst, sStart, characters[i]);
						trans.add(t2);

						start = sStart;
					}
					else
					{
						String eFirst = crearEstado(cont);
						cont++;
						states.add(eFirst);
						Trans t1 = new Trans(start, eFirst, characters[i-2]);
						trans.add(t1);

						String empty = crearEstado(cont);
						cont++;
						states.add(empty);
						Trans t2 = new Trans(eFirst, empty, '_');
						trans.add(t2);

						/*String eSecond = crearEstado(cont);
						cont++;
						states.add(eSecond);
						Trans t3 = new Trans(empty, eSecond, characters[i]);
						trans.add(t3);*/

						start = empty;
						aux = eFirst;
					}
				}
				else if(characters[i] == '*')
				{
					if(i-2 > 0 && characters[i-2] == '.')
					{
					//conectar estado inicial con el inicial de la clausula de kleene
						Trans v = new Trans(start, aux, '_');
						trans.add(v);
						String end = crearEstado(cont);
						cont++;
						states.add(end);
						Trans consume = new Trans(aux, end, '_'); 
						trans.add(consume);
						Trans con = new Trans(start, end, '_');
						trans.add(con);

						start = end;
					}
					else
					{
						String end = crearEstado(cont);
						states.add(end);
						cont++;
						Trans t1 = new Trans(start, end, characters[i-1]);
						Trans revT = new Trans(end,start, '_');
						trans.add(t1);
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
				}
				
				else
				{
					i--;
					if(!sigma.contains(characters[i])){sigma.add(characters[i]);}
					i++;
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


		HashMap<Integer,ArrayList<String>> statesMap = new HashMap<>();
		cont = 0;
		

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

	public void printTransition()
	{
		System.out.print(this.start + " , ");
		System.out.print(this.character + " , ");
		System.out.println(this.end);
	}
}