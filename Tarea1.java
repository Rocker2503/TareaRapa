import java.util.*;

public class Tarea1
{
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
                System.out.println("Ingrese la expresion regular: ");
		String regex = sc.nextLine();
                System.out.println("Ingrese el texto: ");
		String text = sc.nextLine();

		String[] parseReg = regex.split("[|]");
                
                for (int i = 0; i < parseReg.length; i++)
            {
                System.out.println("interno: " + parseReg[i]);
                
            }
	}
}