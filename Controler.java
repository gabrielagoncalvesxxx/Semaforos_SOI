import java.util.Random;
import java.util.concurrent.Semaphore;

public class Controler {
    private static final Semaphore porta = new Semaphore(1);
    private static final Random random = new Random();

    public static void executarSimulacao() {
        for (int i = 0; i < 4; i++) {
            new Thread(new Pessoa(i)).start();
        }
    }

    static class Pessoa implements Runnable {
        private final int id;

        Pessoa(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                int velocidade = 4 + random.nextInt(3); // Velocidade entre 4 e 6 m/s
                int tempoParaCruzar = 200 / velocidade; // Tempo para andar 200m
                int tempoParaAbrirPorta = 1 + random.nextInt(2); // Tempo para abrir e cruzar a porta

                View.mostrarAndando(id);
                Thread.sleep(tempoParaCruzar * 1000); 
                porta.acquire();
                View.mostrarCruzando(id);
                Thread.sleep(tempoParaAbrirPorta * 1000); 
                porta.release();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
