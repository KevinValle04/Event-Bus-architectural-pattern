# La Taberna del Bus de Eventos – "El Gólem Alquímico"

Implementación en **Java 21** del patrón **Publisher/Subscriber** mediante un **Event Bus** en memoria.
Los componentes de la taberna (pedidos, barra, cocina, banda, sonido, panel LED, humo) no se conocen
entre sí: solo se comunican publicando y suscribiéndose a eventos en el bus.

## Requisitos
- JDK 21 o superior (Maven es opcional)

## Compilar y ejecutar

**Sin Maven**
```bash
mkdir out
javac -encoding UTF-8 -d out $(find src -name '*.java')
java -Dfile.encoding=UTF-8 -cp out taberna.Main
```
En Windows (PowerShell):
```powershell
mkdir out
javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -Dfile.encoding=UTF-8 -cp out taberna.Main
```

**Con Maven**
```bash
mvn compile exec:java
```

## Estructura
```
src/main/java/taberna/
├── Main.java                      # Orquesta la demo y la extensibilidad
├── bus/        Evento, Suscriptor, EventBus
├── eventos/    PedidoRealizadoEvent, BebidaServidaEvent, ComidaPreparadaEvent, BandaTocandoEvent (records inmutables)
└── componentes/ SistemaDePedidos, Barra, Cocina, Banda, SistemaDeSonido, PanelLED, SistemaDeHumo
docs/                              # Código PlantUML (componentes, clases, secuencia)
```

## Decisiones de diseño
- **EventBus**: `suscribir(Class, Suscriptor)`, `desuscribir(Class, Suscriptor)`, `publicar(Evento)`.
  Thread-safe (`ConcurrentHashMap` + `CopyOnWriteArrayList`). Un suscriptor registrado a `Evento.class`
  recibe **todos** los eventos (usado por `PanelLED`). Un error en un suscriptor no afecta a los demás.
- **Eventos**: `record`s inmutables (la lista de ítems se copia con `List.copyOf`).
- **Extensibilidad**: `SistemaDeHumo` se añade en `Main` sin modificar `Banda` ni `EventBus`.

## Salida esperada (resumen)
Pedido de la Mesa 5 → Barra sirve cerveza a los 2 s → Cocina termina hamburguesa a los 5 s;
la Banda toca cada 4 s, el Sonido ajusta el ecualizador, el Panel LED anuncia todo, y el Humo se activa
únicamente con "Through the Fire and Flames".
