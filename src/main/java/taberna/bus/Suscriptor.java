package taberna.bus;

/** Receptor de eventos de tipo T. Es una interfaz funcional: admite lambdas. */
@FunctionalInterface
public interface Suscriptor<T extends Evento> {
    void alRecibir(T evento);
}
