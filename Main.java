import java.util.Scanner;
import java.util.Stack;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

  static int algoritmo;
  static int tamanoMemoriaPrincipal, tamanoMemoriaSecundaria;

  public static void main(String[] args) {

    ArrayList<Proceso> procesos = new ArrayList<>();
    LinkedList<Proceso> colaProcesos = new LinkedList<>();
    Random random = new Random();
    int seleccion, tamanoProceso, quantum;
    Scanner sc = new Scanner(System.in);
    System.out.println("Ingrese tamano de memoria principal");
    tamanoMemoriaPrincipal = sc.nextInt();
    System.out.println("Ingrese tamano de memoria secundaria");
    tamanoMemoriaSecundaria = sc.nextInt();
    int numNucleos = 4;

    Proceso memoriaPrincipal[] = new Proceso[tamanoMemoriaPrincipal];
    Proceso memoriaSecundaria[] = new Proceso[tamanoMemoriaSecundaria];

    System.out.println("Escoja Algoritmo:\n1)Primer Ajuste\n2)Mejor Ajuste\n3)Peor Ajuste\nOpcion:");
    algoritmo = sc.nextInt();
    System.out.println("Escoja Algoritmo de seleccion:\n1)LIFO\n2)FIFO");
    seleccion = sc.nextInt();
    SeleccionProceso.algoritmo = algoritmo;
    SeleccionProceso.seleccion = seleccion;
    Algoritmo.seleccion = seleccion;
    // System.out.println("Ingrese tamano del proceso a insertar");
    ////// CREACION DE LOS PROCESOS
    for (int j = 0; j < 10; j++) {
      tamanoProceso = random.nextInt(3) + 1;
      quantum = random.nextInt(5) + 1;
      procesos.add(new Proceso(tamanoProceso, quantum, j));
      colaProcesos.add(procesos.get(j));
    }

    Nucleo nucleos[] = new Nucleo[numNucleos];
    for (int i = 0; i < numNucleos; i++) {
      nucleos[i] = new Nucleo(i, memoriaPrincipal, memoriaSecundaria, procesos, colaProcesos);
      nucleos[i].start();
    }

  }

  /*
   * int j=0;
   * while (j<5) {
   * restarTiempo(memoriaPrincipal);
   * imprimirArreglo(memoriaPrincipal);
   * System.out.println();
   * j++;
   * }
   */

}