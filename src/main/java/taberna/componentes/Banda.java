package taberna.componentes;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import taberna.bus.EventBus;
import taberna.eventos.BandaTocandoEvent;

/** Publicador: toca una canción del repertorio cada X segundos. */
public class Banda {
    public record Cancion(String nombre, int duracion) {}

    private final EventBus bus;
    private final String nombre;
    private final List<Cancion> repertorio;
    private int siguiente = 0;

    public Banda(EventBus bus, String nombre, List<Cancion> repertorio) {
        this.bus = bus;
        this.nombre = nombre;
        this.repertorio = List.copyOf(repertorio);
    }

    public void iniciar(ScheduledExecutorService planificador, int cadaSegundos) {
        planificador.scheduleAtFixedRate(this::tocarCancion, 0, cadaSegundos, TimeUnit.SECONDS);
    }

    private void tocarCancion() {
        Cancion c = repertorio.get(siguiente++ % repertorio.size());
        System.out.println("[BANDA] " + nombre + " empieza a tocar: " + c.nombre());
        bus.publicar(new BandaTocandoEvent(nombre, c.nombre(), c.duracion()));
    }
}
