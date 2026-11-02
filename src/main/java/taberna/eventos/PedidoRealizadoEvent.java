package taberna.eventos;

import java.util.List;
import taberna.bus.Evento;

public record PedidoRealizadoEvent(String mesaId, String pedidoId, List<String> items) implements Evento {
    public PedidoRealizadoEvent {
        items = List.copyOf(items); // copia defensiva: inmutable
    }
}
