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
        contador++;
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
		int cont = 0;

		states.add(q);
                String[] textSplit = regex.split("[|]");
                
                /*for (int i = 0; i < textSplit.length; i++)
                {
                    System.out.println("j: " + textSplit[i]);

                }*/
                
                //clausula |
                 //agregar estados             
                agregarEstado(states, cont);
                agregarEstado(states, cont);
                
                
                for (int i = 0; i < textSplit.length; i++)
        {
            System.out.println("s: " + states.get(i));            
        }
		char[] characters = regex.toCharArray();
                
		for (int i = 0; i < characters.length ; i++) {
			if(characters[i] == '.' || characters[i] == '*')
			{
				if(characters[i] == '.')
				{

				}
				else if(characters[i] == '*')
				{

				}
			}
			else
			{
				if(!sigma.contains(characters[i])){sigma.add(characters[i]);}
				else{continue;}
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
}