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
    while (true) {
      try {
        semaforo.acquire();
        lock.lock();
        System.out.println("Nucleo " + idNucleo + " corriendo");
        // int id = random.nextInt(10);
        Proceso p = colaProcesos.poll();
        switch (Main.algoritmo) {
          case 1:
            if (!algoritmo.inicioPrimerAjuste(memoriaPrincipal, p, memoriaSecundaria))
              colaProcesos.add(p);
            break;
          case 2:
            if (!algoritmo.inicioMejorAjuste(memoriaPrincipal, p, memoriaSecundaria))
              colaProcesos.add(p);
            break;
          case 3:
            if (!algoritmo.inicioPeorAjuste(memoriaPrincipal, p, memoriaSecundaria))
              colaProcesos.add(p);
            break;
          default:
            System.out.println("Algoritmo no valido");
            break;
        }
        System.out.println("Opciones:\n1)Seguir\n2)Ejecutar proceso nuevo");
        opcion = scn.nextInt();
        switch (opcion) {
          case 1:
            break;

          case 2:
            System.out.println("ID del proceso:");
            ID = scn.nextInt();
            System.out.println("Tamaño del proceso:");
            Tamanio = scn.nextInt();
            System.out.println("Quantum del proceso:");
            Quantum = scn.nextInt();
            Proceso nuevoPro = new Proceso(Tamanio, Quantum, ID);
            colaProcesos.addFirst(nuevoPro);
            break;
          default:
            break;
        }

        semaforo.release();
        lock.unlock();
      } catch (Exception e) {
        // Error mi king
        e.printStackTrace();
      }
    }

  }

}
