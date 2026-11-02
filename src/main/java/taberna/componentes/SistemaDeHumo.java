package taberna.componentes;

import taberna.bus.EventBus;
import taberna.eventos.BandaTocandoEvent;

/**
 * Componente NUEVO: demuestra extensibilidad. Se agrega sin modificar Banda ni EventBus.
 * Reacciona solo si la canción es "Through the Fire and Flames".
 */
public class SistemaDeHumo {
    private static final String CANCION_EPICA = "Through the Fire and Flames";

    public SistemaDeHumo(EventBus bus) {
        bus.suscribir(BandaTocandoEvent.class, e -> {
            if (CANCION_EPICA.equalsIgnoreCase(e.nombreCancion())) {
                System.out.println("[HUMO] ¡Canción épica! Activando máquinas de humo 🌫️🔥");
            }
        });
    }
}
