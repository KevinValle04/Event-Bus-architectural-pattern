package taberna.componentes;

import taberna.bus.Evento;
import taberna.bus.EventBus;
import taberna.eventos.*;

/** Suscriptor de TODOS los eventos (suscripción a la interfaz Evento). */
public class PanelLED {
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String AMARILLO = "\u001B[33m";
    private static final String VERDE = "\u001B[32m";
    private static final String MAGENTA = "\u001B[35m";

    public PanelLED(EventBus bus) {
        bus.suscribir(Evento.class, this::mostrar);
    }

    private void mostrar(Evento evento) {
        String mensaje = switch (evento) {
            case PedidoRealizadoEvent e ->
                    CYAN + "¡Nuevo pedido " + e.pedidoId() + " en la " + e.mesaId() + "!" + RESET;
            case BebidaServidaEvent e ->
                    AMARILLO + "¡Nueva bebida servida en la " + e.mesaId() + " (" + e.bebida() + ")!" + RESET;
            case ComidaPreparadaEvent e ->
                    VERDE + "¡" + e.plato() + " lista para el " + e.pedidoId() + "!" + RESET;
            case BandaTocandoEvent e ->
                    MAGENTA + "♪ " + e.nombreBanda() + " - " + e.nombreCancion() + " ♪" + RESET;
            default -> "Evento: " + evento;
        };
        System.out.println("[PANEL LED] " + mensaje);
    }
}
