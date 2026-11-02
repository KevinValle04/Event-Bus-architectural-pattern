package taberna.componentes;

import taberna.bus.EventBus;
import taberna.eventos.BandaTocandoEvent;

/** Suscriptor de BandaTocandoEvent. */
public class SistemaDeSonido {
    public SistemaDeSonido(EventBus bus) {
        bus.suscribir(BandaTocandoEvent.class,
                e -> System.out.println("[SONIDO] Ajustando ecualizador para: " + e.nombreCancion()));
    }
}
