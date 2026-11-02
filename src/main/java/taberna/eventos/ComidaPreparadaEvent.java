package taberna.eventos;

import taberna.bus.Evento;

public record ComidaPreparadaEvent(String pedidoId, String plato) implements Evento {
}
