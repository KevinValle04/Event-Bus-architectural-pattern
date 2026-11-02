package taberna.bus;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Event Bus en memoria, seguro para uso concurrente.
 * Los publicadores y suscriptores solo se conocen a través de este bus.
 * Un suscriptor registrado para un tipo (p. ej. Evento.class) recibe también
 * todos los subtipos, lo que permite suscribirse a "todo".
 */
public class EventBus {

    private final Map<Class<? extends Evento>, List<Suscriptor<? extends Evento>>> suscriptores =
            new ConcurrentHashMap<>();

    public <T extends Evento> void suscribir(Class<T> tipoDeEvento, Suscriptor<T> suscriptor) {
        suscriptores.computeIfAbsent(tipoDeEvento, k -> new CopyOnWriteArrayList<>()).add(suscriptor);
    }

    public <T extends Evento> void desuscribir(Class<T> tipoDeEvento, Suscriptor<T> suscriptor) {
        List<Suscriptor<? extends Evento>> lista = suscriptores.get(tipoDeEvento);
        if (lista != null) {
            lista.remove(suscriptor);
        }
    }

    @SuppressWarnings("unchecked")
    public void publicar(Evento evento) {
        suscriptores.forEach((tipo, lista) -> {
            if (tipo.isInstance(evento)) {
                for (Suscriptor<? extends Evento> s : lista) {
                    try {
                        ((Suscriptor<Evento>) s).alRecibir(evento);
                    } catch (RuntimeException e) {
                        // Un suscriptor defectuoso no debe afectar a los demás.
                        System.err.println("[EventBus] Error en suscriptor: " + e);
                    }
                }
            }
        });
    }
}
