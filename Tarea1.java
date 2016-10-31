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

        boolean isKleene = false;

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
		char[] characters = regex.toCharArray();
         

		for (int i = 1; i < characters.length ; i++) {
			if(characters[i-1] == '.')
			{
				//todo esto crea los estados para consumir la letra antes del .
				String sFirst = crearEstado(cont);
				cont++;
				String eFirst = crearEstado(cont);
				cont++;
				states.add(sFirst);
				states.add(eFirst);
				Trans startTrans = new Trans(s, sFirst, '_');
				Trans consumeTrans = new Trans(sFirst,eFirst,characters[i-2]);
				trans.add(startTrans);
				trans.add(consumeTrans);

				//Aca se hace la transicion vacia para la siguiente letra 
				String sStart = crearEstado(cont);
				cont++;
				states.add(sStart);
				Trans t2 = new Trans(eFirst, sStart, '_');
				trans.add(t2);

				//Consumi la letra despues del .
				String sEnd = crearEstado(cont);
				cont++;
				states.add(sEnd);
				Trans t3 = new Trans(sStart,sEnd, characters[i]);
				trans.add(t3);

				finals.add(sEnd);
			}
			else if(characters[i] == '*')
			{
				//conectar estado inicial con el inicial de la clausula de kleene
				isKleene = true;
				String start = crearEstado(cont);
				cont++;
				String end = crearEstado(cont);
				cont++;

				Trans ini = new Trans(s, start, '_');
				states.add(start);
				states.add(end);
				trans.add(ini);

				finals.add(end);
				Trans t = new Trans(start, end, characters[i-1]);
				Trans t2 = new Trans(end, start, '_');
				trans.add(t);
				trans.add(t2);

			}
			
			else
			{
				i--;
				if(!sigma.contains(characters[i])){sigma.add(characters[i]);}
				i++;
			}	
		}


		String finalState = crearEstado(cont);
		states.add(finalState);
		if(isKleene)
		{
			Trans t = new Trans(s,finalState, '_');
			trans.add(t);
		}

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