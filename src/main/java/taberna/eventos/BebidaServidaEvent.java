package taberna.eventos;

import taberna.bus.Evento;

public record BebidaServidaEvent(String mesaId, String bebida) implements Evento {
}
