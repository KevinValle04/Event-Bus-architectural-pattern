package taberna.componentes;

import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import taberna.bus.EventBus;
import taberna.eventos.BebidaServidaEvent;
import taberna.eventos.PedidoRealizadoEvent;

/** Suscriptor de PedidoRealizadoEvent y publicador de BebidaServidaEvent. */
public class Barra {
    private static final Set<String> BEBIDAS = Set.of("cerveza", "vino", "refresco", "hidromiel");
    private static final int SEGUNDOS_PREPARACION = 2;

    private final EventBus bus;
    private final ScheduledExecutorService planificador;

    public Barra(EventBus bus, ScheduledExecutorService planificador) {
        this.bus = bus;
        this.planificador = planificador;
        bus.suscribir(PedidoRealizadoEvent.class, this::alRecibirPedido);
    }

    private void alRecibirPedido(PedidoRealizadoEvent pedido) {
        for (String item : pedido.items()) {
            if (BEBIDAS.contains(item.toLowerCase())) {
                System.out.println("[BARRA] Preparando " + item + " para " + pedido.mesaId());
                planificador.schedule(
                        () -> bus.publicar(new BebidaServidaEvent(pedido.mesaId(), item)),
                        SEGUNDOS_PREPARACION, TimeUnit.SECONDS);
            }
        }
    }
}
