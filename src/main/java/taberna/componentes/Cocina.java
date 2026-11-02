package taberna.componentes;

import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import taberna.bus.EventBus;
import taberna.eventos.ComidaPreparadaEvent;
import taberna.eventos.PedidoRealizadoEvent;

/** Suscriptor de PedidoRealizadoEvent y publicador de ComidaPreparadaEvent. */
public class Cocina {
    private static final Set<String> PLATOS = Set.of("hamburguesa", "pizza", "nachos", "alitas");
    private static final int SEGUNDOS_COCCION = 5;

    private final EventBus bus;
    private final ScheduledExecutorService planificador;

    public Cocina(EventBus bus, ScheduledExecutorService planificador) {
        this.bus = bus;
        this.planificador = planificador;
        bus.suscribir(PedidoRealizadoEvent.class, this::alRecibirPedido);
    }

    private void alRecibirPedido(PedidoRealizadoEvent pedido) {
        for (String item : pedido.items()) {
            if (PLATOS.contains(item.toLowerCase())) {
                System.out.println("[COCINA] Cocinando " + item + " (" + pedido.pedidoId() + ")");
                planificador.schedule(
                        () -> bus.publicar(new ComidaPreparadaEvent(pedido.pedidoId(), item)),
                        SEGUNDOS_COCCION, TimeUnit.SECONDS);
            }
        }
    }
}
