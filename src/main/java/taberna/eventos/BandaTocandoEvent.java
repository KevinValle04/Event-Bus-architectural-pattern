package taberna.eventos;

import taberna.bus.Evento;

/** duracion en segundos. */
public record BandaTocandoEvent(String nombreBanda, String nombreCancion, int duracion) implements Evento {
}
