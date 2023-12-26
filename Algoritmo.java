import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Algoritmo {
  static int seleccion;
  static Stack<Proceso> pilaLIFO = new Stack<>();
  static Stack<Proceso> pilaLIFOSec = new Stack<>();
  static LinkedList<Proceso> colaFIFO = new LinkedList<>();
  static LinkedList<Proceso> colaFIFOSec = new LinkedList<>();
  static Random random = new Random();
  static int quantumProcesador = 5;
  Semaphore semaforo = new Semaphore(4);
  private static Lock lock = new ReentrantLock();

  public static boolean comprobarEspacio(Proceso[] memoria, int indice, int tamano) {
    for (int i = 0; i < tamano; i++) {
      if (memoria[indice + i] != null)
        return false;
    }
    return true;
  }

  public static boolean comprobarEspacioDisponibleEnMemorias(Proceso[] memoria, Proceso[] memoriaSecundaria,
      int tamano) {
    int contador = 0;
    for (int i = 0; i < memoria.length; i++) {
      if (memoria[i] == null)
        contador++;
    }
    for (int i = 0; i < memoriaSecundaria.length; i++) {
      if (memoriaSecundaria[i] == null)
        contador++;
    }
    if (contador < tamano)
      return false;
    else
      return true;

  }

  public static int buscarEspacioGrande(Proceso[] memoria, int tamano) {
    int indice = -1;
    int tamanoLibreActual = 0;
    int tamanoLibreMaximo = 0;

    for (int i = 0; i < memoria.length; i++) {
      if (memoria[i] == null) {
        tamanoLibreActual++;
      } else {
        // Si encontramos una partición ocupada, comprobamos si es la más grande hasta
        // ahora
        if (tamanoLibreActual > tamanoLibreMaximo) {
          tamanoLibreMaximo = tamanoLibreActual;
          indice = i - tamanoLibreActual; // Guardamos el índice de inicio de la partición libre más grande
        }
        tamanoLibreActual = 0;
      }
    }

    // Comprobamos también la última partición en caso de que sea la más grande
    if (tamanoLibreActual > tamanoLibreMaximo) {
      tamanoLibreMaximo = tamanoLibreActual;
      indice = memoria.length - tamanoLibreActual; // Guardamos el índice de inicio de la última partición libre más
                                                   // grande
    }

    // Devolvemos el índice de inicio de la partición libre más grande si es
    // suficientemente grande para el proceso
    return (tamanoLibreMaximo >= tamano) ? indice : -1;
  }

  public static int buscarEspacioPequeno(Proceso[] memoria, int tamano) {
    int indice = -1;
    int espacioMenor = Integer.MAX_VALUE;
    int espacio = 0;
    for (int i = 0; i < memoria.length; i++) {
      if (memoria[i] == null) {
        espacio++;
      } else if (memoria[i] != null) {
        if (espacio >= tamano) {
          if (espacio < espacioMenor) {
            espacioMenor = espacio;
            indice = i - espacio + 1;
            espacio = 0;
          }
        }
      }

      if (i == memoria.length - 1) {
        if (espacio >= tamano) {
          espacioMenor = espacio;
          indice = i - espacio + 1;
          // System.out.println("Indice: "+indice+ " espacio: "+espacioMenor);
          espacio = 0;
        }
      }
    }
    // System.out.println("Indice: "+indice+ " espacio: "+espacioMenor);
    return indice;
  }

  static void imprimirArreglo(Proceso[] memoria) {
    for (int i = 0; i < memoria.length; i++) {
      System.out.println(memoria[i] + " ");
    }
    System.out.println();
  }

  public boolean inicioPrimerAjuste(Proceso[] memoria, Proceso p, Proceso[] memoriaSecundaria) {
    boolean resultado = false;
    System.out.println("---Proceso " + p.id + " solicitado---");
    int aux = determinarOperacion(memoria, p, memoriaSecundaria);
    if (aux == 1) {
      System.out.println("Proceso en memoria principal");
    } else if (aux == 2) {
      System.out.println("Proceso en memoria secundaria");
    }

    if (seleccion == 1) {
      if (primerAjuste(memoria, p, memoriaSecundaria, pilaLIFO, pilaLIFOSec)) {
        System.out.println("---Proceso " + p.id + " con tamano:" + p.tamano + " insertado en memoria---\n");
        resultado = true;
      } else {
        if (aux == 0) {
          System.out.println("!!!No hay espacio suficiente en memoria para el proceso " + p + "!!!\n");
        }
      }
    } else if (seleccion == 2) {
      if (primerAjuste(memoria, p, memoriaSecundaria, colaFIFO, colaFIFOSec)) {
        System.out.println("---Proceso " + p.id + " con tamano:" + p.tamano + " insertado en memoria---\n");
        resultado = true;
      } else {
        if (aux == 0) {
          System.out.println("!!!No hay espacio suficiente en memoria para el proceso " + p + "!!!\n");
        }
      }
    }
    System.out.println("=================================================================");
    System.out.println("........................Memoria pricipal:........................");
    imprimirArreglo(memoria);
    System.out.println("=================================================================");
    System.out.println("........................Memoria Secundaria:......................");
    imprimirArreglo(memoriaSecundaria);
    System.out.println("=================================================================");
    System.out.println("#################################################################\n\n\n");
    return resultado;
  }

  public boolean inicioMejorAjuste(Proceso[] memoria, Proceso p, Proceso[] memoriaSecundaria) {
    boolean resultado = false;
    System.out.println("---Proceso " + p.id + " solicitado---");
    int aux = determinarOperacion(memoria, p, memoriaSecundaria);
    if (aux == 1) {
      System.out.println("Proceso en memoria principal");
    } else if (aux == 2) {
      System.out.println("Proceso en memoria secundaria");
    }

    if (seleccion == 1) {
      if (mejorAjuste(memoria, p, memoriaSecundaria, pilaLIFO, pilaLIFOSec)) {
        System.out.println("---Proceso " + p.id + " con tamano:" + p.tamano + " insertado en memoria---\n");
        resultado = true;
      } else {
        if (aux == 0) {
          System.out.println("!!!No hay espacio suficiente en memoria para el proceso " + p + "!!!\n");
        }
      }
    } else if (seleccion == 2) {
      if (mejorAjuste(memoria, p, memoriaSecundaria, colaFIFO, colaFIFOSec)) {
        System.out.println("---Proceso " + p.id + " con tamano:" + p.tamano + " insertado en memoria---\n");
        resultado = true;
      } else {
        if (aux == 0) {
          System.out.println("!!!No hay espacio suficiente en memoria para el proceso " + p + "!!!\n");
        }
      }
    }
    System.out.println("=================================================================");
    System.out.println("........................Memoria pricipal:........................");
    imprimirArreglo(memoria);
    System.out.println("=================================================================");
    System.out.println("........................Memoria Secundaria:......................");
    imprimirArreglo(memoriaSecundaria);
    System.out.println("=================================================================");
    System.out.println("#################################################################\n\n\n");
    return resultado;
  }

  public boolean inicioPeorAjuste(Proceso[] memoria, Proceso p, Proceso[] memoriaSecundaria) {
    boolean resultado = false;
    System.out.println("---Proceso " + p.id + " solicitado---");
    int aux = determinarOperacion(memoria, p, memoriaSecundaria);
    if (aux == 1) {
      System.out.println("Proceso en memoria principal");
    } else if (aux == 2) {
      System.out.println("Proceso en memoria secundaria");
    }

    if (seleccion == 1) {
      if (peorAjuste(memoria, p, memoriaSecundaria, pilaLIFO, pilaLIFOSec)) {
        System.out.println("---Proceso " + p.id + " con tamano:" + p.tamano + " insertado en memoria---\n");
        resultado = true;
      } else {
        if (aux == 0) {
          System.out.println("!!!No hay espacio suficiente en memoria para el proceso " + p + "!!!\n");
        }
      }
    } else if (seleccion == 2) {
      if (peorAjuste(memoria, p, memoriaSecundaria, colaFIFO, colaFIFOSec)) {
        System.out.println("---Proceso " + p.id + " con tamano:" + p.tamano + " insertado en memoria---\n");
        resultado = true;
      } else {
        if (aux == 0) {
          System.out.println("!!!No hay espacio suficiente en memoria para el proceso " + p + "!!!\n");
        }
      }
    }
    System.out.println("=================================================================");
    System.out.println("........................Memoria pricipal:........................");
    imprimirArreglo(memoria);
    System.out.println("=================================================================");
    System.out.println("........................Memoria Secundaria:......................");
    imprimirArreglo(memoriaSecundaria);
    System.out.println("=================================================================");
    System.out.println("#################################################################\n\n\n");
    return resultado;
  }

  public static int determinarOperacion(Proceso[] memoria, Proceso p, Proceso[] memoriaSecundaria) {
    for (int i = 0; i < memoria.length; i++) {
      if (memoria[i] != null && memoria[i].id == p.id) {
        return 1;
      }
    }
    for (int i = 0; i < memoriaSecundaria.length; i++) {
      if (memoriaSecundaria[i] != null && memoriaSecundaria[i].id == p.id) {
        return 2;
      }
    }
    return 0;
  }

  public static boolean primerAjuste(Proceso[] memoria, Proceso p, Proceso[] memoriaSecundaria, Stack<Proceso> pilaLIFO,
      Stack<Proceso> pilaLIFOSec) {

    int aux = determinarOperacion(memoria, p, memoriaSecundaria);

    if (aux != 1 && !comprobarEspacioDisponibleEnMemorias(memoria, memoriaSecundaria, p.tamano)) {
      return false;
    }

    // System.out.println("Proceso " + p.id + " solicitado");
    if (aux == 1) {
      System.out.println("Proceso " + p + " ejecutado por cpu\n");
      restarTiempo(memoria, p);
    } else if (aux == 2) {
      // System.out.println("Proceso en memoria secundaria");
      Proceso nuevo = null;
      if (pilaLIFO.isEmpty())
        return false;
      else
        nuevo = pilaLIFO.pop();

      pilaLIFOSec.remove(p);
      pilaLIFOSec.push(p);
      for (int i = 0; i < memoria.length; i++) {
        if (memoria[i] == null) {
          if (p.tamano <= memoria.length - i) {
            if (Algoritmo.comprobarEspacio(memoria, i, p.tamano)) {
              for (int j = 0; j < p.tamano; j++) {
                memoria[i + j] = p;
              }
              pilaLIFOSec.remove(p);
              pilaLIFO.push(nuevo);
              pilaLIFO.push(p);
              SeleccionProceso.eliminarProceso(memoriaSecundaria, p);
              System.out.println("Proceso " + p + " ejecutado por cpu\n");
              restarTiempo(memoria, p);
              return true;
            }
          }
        }
      }
      SeleccionProceso.LIFO(memoriaSecundaria, memoria, nuevo, pilaLIFOSec, pilaLIFO);
      restarTiempo(memoria, p);
    } else {
      // System.out.println("Inserta en memoria");
      for (int i = 0; i < memoria.length; i++) {
        if (memoria[i] == null) {
          if (p.tamano <= memoria.length - i) {
            if (comprobarEspacio(memoria, i, p.tamano)) {

              for (int j = 0; j < p.tamano; j++) {
                memoria[i + j] = p;
              }
              // System.out.println("Proceso " + p.id + " con tamanio:" + p.tamano + "
              // insertado en memoria");

              pilaLIFO.push(p);
              return true;
            }
          }
        }
      }
      // System.out.println("No hay espacio suficiente en memoria para el proceso
      // "+p);
      if (pilaLIFO.isEmpty())
        return false;

      SeleccionProceso.LIFO(memoria, memoriaSecundaria, p, pilaLIFO, pilaLIFOSec);

    }
    return false;
  }

  public static boolean primerAjuste(Proceso[] memoria, Proceso p, Proceso[] memoriaSecundaria,
      LinkedList<Proceso> colaFIFO, LinkedList<Proceso> colaFIFOSec) {

    int aux = determinarOperacion(memoria, p, memoriaSecundaria);

    if (aux != 1 && !comprobarEspacioDisponibleEnMemorias(memoria, memoriaSecundaria, p.tamano)) {
      return false;
    }

    if (aux == 1) {
      System.out.println("Proceso " + p + " ejecutado por cpu\n");
      restarTiempo(memoria, p);
    } else if (aux == 2) {
      Proceso nuevo = null;
      if (colaFIFO.isEmpty())
        return false;
      else
        nuevo = colaFIFO.poll();

      colaFIFOSec.remove(p);
      colaFIFOSec.addFirst(p);
      for (int i = 0; i < memoria.length; i++) {
        if (memoria[i] == null) {
          if (p.tamano <= memoria.length - i) {
            if (Algoritmo.comprobarEspacio(memoria, i, p.tamano)) {
              for (int j = 0; j < p.tamano; j++) {
                memoria[i + j] = p;
              }
              colaFIFOSec.remove(p);
              colaFIFO.addFirst(nuevo);
              colaFIFO.add(p);
              SeleccionProceso.eliminarProceso(memoriaSecundaria, p);
              System.out.println("Proceso " + p + " ejecutado por cpu\n");
              restarTiempo(memoria, p);
              return true;
            }
          }
        }
      }
      SeleccionProceso.FIFO(memoriaSecundaria, memoria, nuevo, colaFIFOSec, colaFIFO);
      System.out.println("Proceso " + p + " ejecutado por cpu\n");
      restarTiempo(memoria, p);
    } else {

      for (int i = 0; i < memoria.length; i++) {
        if (memoria[i] == null) {
          if (p.tamano <= memoria.length - i) {
            if (comprobarEspacio(memoria, i, p.tamano)) {
              for (int j = 0; j < p.tamano; j++) {
                memoria[i + j] = p;
              }
              // System.out.println("---Proceso " + p.id + " con tamanio:" + p.tamano + "
              // insertado en memoria---");
              colaFIFO.add(p);
              return true;
            }
          }
        }

      }
      // System.out.println("---No hay espacio suficiente en memoria para el proceso "
      // + p + "---");
      if (colaFIFO.isEmpty())
        return false;

      SeleccionProceso.FIFO(memoria, memoriaSecundaria, p, colaFIFO, colaFIFOSec);
    }
    return false;
  }

  public static boolean mejorAjuste(Proceso[] memoria, Proceso p, Proceso[] memoriaSecundaria, Stack<Proceso> pilaLIFO,
      Stack<Proceso> pilaLIFOSec) {

    int aux = determinarOperacion(memoria, p, memoriaSecundaria);
    if (aux != 1 && !comprobarEspacioDisponibleEnMemorias(memoria, memoriaSecundaria, p.tamano)) {
      return false;
    }

    if (aux == 1) {
      restarTiempo(memoria, p);
    } else if (aux == 2) {
      Proceso nuevo = null;
      if (pilaLIFO.isEmpty())
        return false;
      else
        nuevo = pilaLIFO.pop();

      pilaLIFOSec.remove(p);
      pilaLIFOSec.push(p);
      int indice = buscarEspacioPequeno(memoria, p.tamano);
      if (indice != -1) {
        for (int i = 0; i < p.tamano; i++) {
          memoria[indice + i] = p;

        }
        pilaLIFOSec.remove(p);
        pilaLIFO.push(nuevo);
        pilaLIFO.push(p);
        SeleccionProceso.eliminarProceso(memoriaSecundaria, p);
        System.out.println("Proceso " + p + " ejecutado por cpu\n");
        restarTiempo(memoria, p);

        return true;
      }

      SeleccionProceso.LIFO(memoriaSecundaria, memoria, nuevo, pilaLIFOSec, pilaLIFO);
      System.out.println("Proceso " + p + " ejecutado por cpu\n");
      restarTiempo(memoria, p);
    } else {
      int indice = buscarEspacioPequeno(memoria, p.tamano);
      if (indice != -1) {
        for (int i = 0; i < p.tamano; i++) {
          memoria[indice + i] = p;

        }
        pilaLIFO.push(p);
        return true;
      } else {
        if (pilaLIFO.isEmpty())
          return false;
        SeleccionProceso.LIFO(memoria, memoriaSecundaria, p, pilaLIFO, pilaLIFOSec);
      }
    }
    return false;
  }

  public static boolean mejorAjuste(Proceso[] memoria, Proceso p, Proceso[] memoriaSecundaria,
      LinkedList<Proceso> colaFIFO, LinkedList<Proceso> colaFIFOSec) {

    int aux = determinarOperacion(memoria, p, memoriaSecundaria);
    if (aux != 1 && !comprobarEspacioDisponibleEnMemorias(memoria, memoriaSecundaria, p.tamano)) {
      return false;
    }

    if (aux == 1) {
      restarTiempo(memoria, p);
    } else if (aux == 2) {
      Proceso nuevo = null;
      if (colaFIFO.isEmpty())
        return false;
      else
        nuevo = colaFIFO.poll();

      colaFIFOSec.remove(p);
      colaFIFOSec.addFirst(p);
      int indice = buscarEspacioPequeno(memoria, p.tamano);
      if (indice != -1) {
        for (int i = 0; i < p.tamano; i++) {
          memoria[indice + i] = p;
        }
        colaFIFOSec.remove(p);
        colaFIFO.addFirst(nuevo);
        colaFIFO.add(p);
        SeleccionProceso.eliminarProceso(memoriaSecundaria, p);
        System.out.println("Proceso " + p + " ejecutado por cpu\n");
        restarTiempo(memoria, p);
        return true;
      }

      SeleccionProceso.FIFO(memoriaSecundaria, memoria, nuevo, colaFIFOSec, colaFIFO);
      System.out.println("Proceso " + p + " ejecutado por cpu\n");
      restarTiempo(memoria, p);
    } else {
      int indice = buscarEspacioPequeno(memoria, p.tamano);
      if (indice != -1) {
        for (int i = 0; i < p.tamano; i++) {
          memoria[indice + i] = p;
        }
        colaFIFO.add(p);
        return true;
      } else {
        if (colaFIFO.isEmpty())
          return false;

        SeleccionProceso.FIFO(memoria, memoriaSecundaria, p, colaFIFO, colaFIFOSec);
      }
    }
    return false;
  }

  public static boolean peorAjuste(Proceso[] memoria, Proceso p, Proceso[] memoriaSecundaria, Stack<Proceso> pilaLIFO,
      Stack<Proceso> pilaLIFOSec) {
    int aux = determinarOperacion(memoria, p, memoriaSecundaria);
    if (aux != 1 && !comprobarEspacioDisponibleEnMemorias(memoria, memoriaSecundaria, p.tamano)) {
      return false;
    }
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    if (aux == 1) {
      restarTiempo(memoria, p);
    } else if (aux == 2) {
      Proceso nuevo = null;
      if (pilaLIFO.isEmpty())
        return false;
      else
        nuevo = pilaLIFO.pop();

      pilaLIFOSec.remove(p);
      pilaLIFOSec.push(p);
      int indice = buscarEspacioGrande(memoria, p.tamano);
      if (indice != -1) {
        for (int i = 0; i < p.tamano; i++) {
          memoria[indice + i] = p;
        }
        pilaLIFOSec.remove(p);
        pilaLIFO.push(nuevo);
        pilaLIFO.push(p);
        SeleccionProceso.eliminarProceso(memoriaSecundaria, p);
        System.out.println("Proceso " + p + " ejecutado por cpu\n");
        restarTiempo(memoria, p);
        return true;
      }

      SeleccionProceso.LIFO(memoriaSecundaria, memoria, nuevo, pilaLIFOSec, pilaLIFO);
      System.out.println("Proceso " + p + " ejecutado por cpu\n");
      restarTiempo(memoria, p);

    } else {
      int indice = buscarEspacioGrande(memoria, p.tamano);
      if (indice != -1) {
        for (int i = 0; i < p.tamano; i++) {
          memoria[indice + i] = p;
        }
        pilaLIFO.push(p);
        return true;
      } else {
        if (pilaLIFO.isEmpty())
          return false;

        SeleccionProceso.LIFO(memoria, memoriaSecundaria, p, pilaLIFO, pilaLIFOSec);
      }
    }
    return false;
  }

  public static boolean peorAjuste(Proceso[] memoria, Proceso p, Proceso[] memoriaSecundaria,
      LinkedList<Proceso> colaFIFO, LinkedList<Proceso> colaFIFOSec) {
    int aux = determinarOperacion(memoria, p, memoriaSecundaria);
    if (aux != 1 && !comprobarEspacioDisponibleEnMemorias(memoria, memoriaSecundaria, p.tamano)) {
      return false;
    }

    if (aux == 1) {
      restarTiempo(memoria, p);
    } else if (aux == 2) {
      Proceso nuevo = null;
      if (colaFIFO.isEmpty())
        return false;
      else
        nuevo = colaFIFO.poll();

      colaFIFOSec.remove(p);
      colaFIFOSec.addFirst(p);
      int indice = buscarEspacioGrande(memoria, p.tamano);
      if (indice != -1) {
        for (int i = 0; i < p.tamano; i++) {
          memoria[indice + i] = p;
        }
        colaFIFOSec.remove(p);
        colaFIFO.addFirst(nuevo);
        colaFIFO.add(p);
        SeleccionProceso.eliminarProceso(memoriaSecundaria, p);
        System.out.println("Proceso " + p + " ejecutado por cpu\n");
        restarTiempo(memoria, p);
        return true;
      }

      SeleccionProceso.FIFO(memoriaSecundaria, memoria, nuevo, colaFIFOSec, colaFIFO);
      System.out.println("Proceso " + p + " ejecutado por cpu\n");
      restarTiempo(memoria, p);

    } else {
      int indice = buscarEspacioGrande(memoria, p.tamano);
      if (indice != -1) {
        for (int i = 0; i < p.tamano; i++) {
          memoria[indice + i] = p;
        }
        colaFIFO.push(p);
        return true;
      } else {
        if (colaFIFO.isEmpty())
          return false;

        SeleccionProceso.FIFO(memoria, memoriaSecundaria, p, colaFIFO, colaFIFOSec);
      }
    }
    return false;
  }

  static void restarTiempo(Proceso[] memoria, Proceso p) {
    for (int i = 0; i < memoria.length; i++) {
      if (memoria[i] != null && memoria[i].id == p.id) {
        memoria[i].quantum -= quantumProcesador;
        if (memoria[i].quantum <= 0) {
          for (int j = i; j < p.tamano + i; j++) {
            memoria[j] = null;
          }
          p.quantum = random.nextInt(5) + 1;
        }
        break;
      }
    }
  }

}