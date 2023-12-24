import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Nucleo extends Thread{
    int idNucleo;
    int quantum;
    Random random = new Random();
    Proceso memoriaPrincipal[] = new Proceso[Main.tamanoMemoriaPrincipal];
    Proceso memoriaSecundaria[] = new Proceso[Main.tamanoMemoriaSecundaria];
    ArrayList<Proceso> procesos = new ArrayList<>();
    LinkedList<Proceso> colaProcesos = new LinkedList<>();
    Algoritmo algoritmo = new Algoritmo();
    

    public Nucleo(int idNucleo, Proceso memoriaPrincipal[], Proceso memoriaSecundaria[], ArrayList<Proceso> procesos, LinkedList<Proceso> colaProcesos) {
        this.idNucleo = idNucleo;
        this.memoriaPrincipal = memoriaPrincipal;
        this.memoriaSecundaria = memoriaSecundaria;
        this.procesos = procesos;
        this.colaProcesos = colaProcesos;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Nucleo " + idNucleo + " corriendo");
            //int id = random.nextInt(10);
            Proceso p = colaProcesos.poll();
            switch (Main.algoritmo) {
              case 1:
                if(!algoritmo.inicioPrimerAjuste(memoriaPrincipal, p, memoriaSecundaria))
                    colaProcesos.add(p);
                break;
              case 2:
                if(!algoritmo.inicioMejorAjuste(memoriaPrincipal, p, memoriaSecundaria))
                    colaProcesos.add(p);
                break;
              case 3:
                if(!algoritmo.inicioPeorAjuste(memoriaPrincipal, p, memoriaSecundaria))
                    colaProcesos.add(p);
                break;
              default:
                System.out.println("Algoritmo no valido");
                break;
            }
          }

    }


}
