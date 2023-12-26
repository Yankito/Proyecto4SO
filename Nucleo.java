import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.Semaphore;

public class Nucleo extends Thread {
  Semaphore semaforo = new Semaphore(4);
  private static Lock lock = new ReentrantLock();
  int idNucleo;
  int quantum;
  Random random = new Random();
  Proceso memoriaPrincipal[] = new Proceso[Main.tamanoMemoriaPrincipal];
  Proceso memoriaSecundaria[] = new Proceso[Main.tamanoMemoriaSecundaria];
  ArrayList<Proceso> procesos = new ArrayList<>();
  LinkedList<Proceso> colaProcesos = new LinkedList<>();
  Algoritmo algoritmo = new Algoritmo();
  Scanner scn = new Scanner(System.in);
  int opcion = 0;
  int ID, Tamanio, Quantum;

  public Nucleo(int idNucleo, Proceso memoriaPrincipal[], Proceso memoriaSecundaria[], ArrayList<Proceso> procesos,
      LinkedList<Proceso> colaProcesos) {
    this.idNucleo = idNucleo;
    this.memoriaPrincipal = memoriaPrincipal;
    this.memoriaSecundaria = memoriaSecundaria;
    this.procesos = procesos;
    this.colaProcesos = colaProcesos;
  }

  @Override
  public void run() {
    while (true) { //Se ejecutan los hilos
      System.out.println("Nucleo " + idNucleo + " corriendo");
        switch (Main.algoritmo) {
          case 1:
            algoritmo.inicioPrimerAjuste(memoriaPrincipal, memoriaSecundaria, colaProcesos);
            break;
          case 2:
            algoritmo.inicioMejorAjuste(memoriaPrincipal, memoriaSecundaria, colaProcesos);
            break;
          case 3:
            algoritmo.inicioPeorAjuste(memoriaPrincipal, memoriaSecundaria, colaProcesos);
            break;
          default:
            System.out.println("Algoritmo no valido");
            break;
        }
    }

  }

}
