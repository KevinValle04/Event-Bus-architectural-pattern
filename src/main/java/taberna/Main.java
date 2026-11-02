package taberna;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import taberna.bus.EventBus;
import taberna.componentes.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== El Gólem Alquímico: Taberna con Event Bus ===\n");

        EventBus bus = new EventBus();
        ScheduledExecutorService planificador = Executors.newScheduledThreadPool(4);

        // Suscriptores y componentes originales
        new PanelLED(bus);
        new SistemaDeSonido(bus);
        new Barra(bus, planificador);
        new Cocina(bus, planificador);

        Banda banda = new Banda(bus, "Los Seguidores de Dijkstra", List.of(
                new Banda.Cancion("El Algoritmo del Amor", 180),
                new Banda.Cancion("Through the Fire and Flames", 440)));
        SistemaDePedidos pedidos = new SistemaDePedidos(bus);

        // La banda empieza a tocar (una canción cada 4 s) y entra un pedido
        banda.iniciar(planificador, 4);
        pedidos.realizarPedido("Mesa 5", List.of("cerveza", "hamburguesa"));

        // --- Demostración de extensibilidad ---
        // Se añade un nuevo suscriptor en tiempo de ejecución, sin tocar Banda ni EventBus.
        Thread.sleep(2000);
        System.out.println("\n>>> Se instala el SistemaDeHumo (sin modificar Banda ni EventBus) <<<\n");
        new SistemaDeHumo(bus);

        Thread.sleep(8000);
        planificador.shutdownNow();
        planificador.awaitTermination(1, TimeUnit.SECONDS);
        System.out.println("\n=== Fin de la demostración ===");
    }
}
