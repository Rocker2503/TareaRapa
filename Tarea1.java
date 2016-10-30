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
                ArrayList<String> F = new ArrayList<>();

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
                
                
                for (int i = 0; i < textSplit.length; i++)
        {
            System.out.println("s: " + states.get(i));            
        }
		char[] characters = regex.toCharArray();
                
		for (int i = 1; i < characters.length ; i++) {
			if(characters[i-1] == '.')
			{

			}
			else if(characters[i] == '*')
			{
				String start = crearEstado(cont);
				cont++;
				String end = crearEstado(cont);
				cont++;
				states.add(start);
				states.add(end);
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