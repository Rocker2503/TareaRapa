import java.util.*;
import java.util.regex.*;

public class Tarea1
{
    public void agregarEstado(ArrayList lista, int contador)
    {
        String estado = crearEstado(contador);
        lista.add(estado);
    }
    
    public String crearEstado(int contador)
    {
//        contador++;
        return ("q" + contador);
    }
    
    public String obtenerTransicion(int estadoI, int sigmaJ, ArrayList<String> states, ArrayList<Character> sigma, ArrayList<Trans> trans)
    {
        String estadoL = states.get(estadoI);
        String estadoInicio = estadoL;
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
                if(!transicion.contains(trans.get(i).getEnd()) )
                    {
                        String local = (trans.get(i).getEnd() + " ");
                        transicion = (transicion + local);
                    }
            }
            String estadoL2 = estadoInicio;
            
            //encontrar |
            if(trans.get(i).getStart().equals(estadoInicio))
            {
                for (int j = i; j < trans.size(); j++)
                {
                    if(trans.get(j).getStart().equals(estadoL2) && trans.get(j).getCharacter() == '_')
                    {
                        estadoL2 = trans.get(j).getEnd();
                    }
                    if(trans.get(j).getStart().equals(estadoL2) && trans.get(j).getCharacter() == sigmaL)
                    {
                        if(!transicion.contains(trans.get(j).getEnd()) )
                        {
                            String local = (trans.get(j).getEnd() + " ");
                            transicion = (transicion + local);
                        }
                    }
                }
            }
        }
        //termina y no encontro la estrella
        estadoL = estadoInicio;
        for (int i = 0; i < trans.size(); i++)
        {
            if(trans.get(i).getStart().equals(estadoL) && trans.get(i).getCharacter() == '_')
            {
                estadoL = trans.get(i).getEnd();
                for (int j = i; j >= 0; j--)
                {
                    if(trans.get(j).getStart().equals(estadoL) && trans.get(j).getCharacter() == sigmaL)
                    {
                        if(!transicion.contains(trans.get(j).getEnd()) )
                        {
                            String local = (trans.get(j).getEnd() + " ");
                            transicion = (transicion + local);
                        }
                    }
                }
            }
            estadoL = estadoInicio;
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

					aux = start;
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
					Trans revT = new Trans(start,aux, '_');
					trans.add(revT);
					Trans t2 = new Trans(start, end, '_');
					Trans t3 = new Trans(aux, end, '_');
					trans.add(t2);
					trans.add(t3);

					aux = start;
					start = end;
		
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
                finals.add(finalState);
                
                System.out.println("AFND: ");
		System.out.print("Estados: ");
		for (int i = 0; i < states.size()-1; i++) {
			System.out.print(states.get(i) + ", ");
		}
		System.out.println(states.get(states.size()-1));
		
		System.out.print("Transiciones: ");
		for (int i = 0; i < trans.size(); i++ ) {
			trans.get(i).printTransition();
		}
                System.out.println("s: " + s);
                System.out.print("F:");
                for (int i = 0; i < finals.size()-1; i++)
                {
                    System.out.print(finals.get(i) + ", ");
                }
                System.out.println(finals.get(finals.size()-1));

                
        String s2 = states.get(0);
        ArrayList<String> states2 = new ArrayList<>();
        ArrayList<Trans> trans2 = new ArrayList<>();
        ArrayList<String> finals2 = new ArrayList<>();
        
        cont = 0;
        String estado = crearEstado(cont);
        cont++;
        states2.add(estado);

        //conversion a AFD
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
/*
        for (int i = 0; i < states.size(); i++) {
        	for (int j = 0;j < sigma.size()+1 ; j++) {
        		System.out.println("i: " + i + " j: " + j + "  "+ matriz[i][j]);
        	}
        	System.out.println(" ");
        }*/
        
        String qa = "";
        String sa = "";
        String st = "";
        String last = s;
        String[] transSplit = null;
        boolean flag = false;
        boolean isKleene = false;

        int size = states.size();	
        
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < sigma.size()+1; j++)
            {  
            	//System.out.println("i: " + i);	
            	if(j == 0){
                    qa = matriz[i][j];
                }
                else if(j <= sigma.size())
                {
                    sa = matriz[i][j];
                    transSplit = sa.split(" ");
                    for (int k = 0; k < transSplit.length; k++)
                    {
                        String estadoSplit = transSplit[k];
                        if(i-1 >= 0 && !matriz[i][j].equals(matriz[i-1][j]) && !flag)
                        {
                            s = last;
                            k = 0;
                            j = 1;
                            flag = true;
                            //System.out.println("entra cuando no son iguales" + " s: " + s);
                        }
                        
                        if(states.contains(estadoSplit))
                        {
                            for (int l = 0; l < size ; l++) 
                            {
                                if(matriz[l][0].equals(estadoSplit))
                                {
                                    String[] auxSplit = matriz[l][j].split(" ");
                                    for(int p = 0; p < auxSplit.length ; p++)
                                    {
                                            if(matriz[l][0].equals(auxSplit[p]))
                                            {
                                                    isKleene = true;
                                                    i = l;
                                                    Trans kl = new Trans(last,last,sigma.get(j-1));
                                                    trans2.add(kl);
                                            }
                                    }

                                }
                        }
                        if(!isKleene)
                        {
                            st = crearEstado(cont);
                            cont++;
                            states2.add(st);
                            if(j <= sigma.size() )
                            {
                                Trans t = new Trans(s, st, sigma.get(j-1));
                                trans2.add(t);

                            }
                            //System.out.println("Crea un nuevo estado" + "st: "  + st);
                            states.remove(estadoSplit);
                        }
                        }
                        else if(i-1 >= 0 && matriz[i][j].equals(matriz[i-1][j]) && !flag)
                        {
                            k = transSplit.length;
                            //System.out.println("entra cuando son iguales");
                        }
                        else if (matriz[i][j].equals(""))
                        {
                            continue; 
                        }

                    }
                    isKleene = false;
                }
                
                
                
            }
            flag = false;
            if(!st.equals(""))
            {
            	last = st; 
            }
                
        }
        
        //encontrar finales
        String localInicio = s2;
        boolean cambiado = false;
        for (int i = 0; i < trans2.size(); i++)
        {
            for (int j = i; j < trans2.size(); j++)
            {
                if(trans2.get(j).getStart().equals(localInicio))
                {
                    localInicio = trans2.get(j).getEnd();
                    cambiado = true;
                }
            }
            if(cambiado == true)
            {
                finals2.add(localInicio);
                cambiado = false;
            }
            localInicio = s2;
            
            
        }
        
        //System.out.println("#######################################");
        System.out.println("");
        System.out.println("AFD: ");
        System.out.print("Estados: ");
        for (int i = 0; i < states2.size()-1; i++) {
                System.out.print(states2.get(i) + ", ");
        }
        System.out.println(states2.get(states2.size()-1));

        System.out.print("Transiciones: ");
        for (int i = 0; i < trans2.size(); i++ ) {
                trans2.get(i).printTransition();
        }
        System.out.println("s: " + s2);
        System.out.print("F: ");
        for (int i = 0; i < finals2.size()-1; i++)
                {
                    System.out.print(finals2.get(i) + ", ");
                }
                System.out.println(finals2.get(finals2.size()-1));
        
        //ocurrencias
        /*Pattern patron = Pattern.compile(regex);
        Matcher encaja = patron.matcher(text);
//        System.out.println("paiosk: " + encaja.replaceAll("1"));
        System.out.println("TEXT: "+text);
        System.out.println("");
        System.out.println("paiosk: " + encaja.find());
        System.out.println("matches: "+encaja.matches());
        System.out.println("paiosk: " + encaja.replaceFirst("."));
        text = encaja.replaceFirst(".");
        System.out.println("text: "+ text);
        System.out.println("matches: "+encaja.matches());*/
        

    }
}


class Trans implements Comparable<Trans>
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

    @Override
    public int compareTo(Trans o)
    {
        if(this.start.equals(o.start) && this.end.equals(o.end) && this.character == o.character)
            return 1;
        else
            return 0;
    }
}