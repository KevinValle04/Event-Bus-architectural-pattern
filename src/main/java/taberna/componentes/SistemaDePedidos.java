package taberna.componentes;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import taberna.bus.EventBus;
import taberna.eventos.PedidoRealizadoEvent;

/** Publicador. */
public class SistemaDePedidos {
    private final EventBus bus;
    private final AtomicInteger contador = new AtomicInteger(122);

    public SistemaDePedidos(EventBus bus) {
        this.bus = bus;
    }

    public String realizarPedido(String mesaId, List<String> items) {
        String pedidoId = "pedido_" + contador.incrementAndGet();
        bus.publicar(new PedidoRealizadoEvent(mesaId, pedidoId, items));
        return pedidoId;
    }
}
