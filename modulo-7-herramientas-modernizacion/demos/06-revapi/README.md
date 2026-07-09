# Demo: Revapi

Análisis de compatibilidad de API entre dos versiones de un artefacto.

## Cuándo usarlo

- Librerías internas compartidas (`com.empresa:commons`)
- SDKs de clientes
- Antes de publicar un major tras migrar a Java 21

## Ejemplo conceptual

```text
v1.0.0                          v2.0.0 (Java 21)
---------                       ----------------
PedidoService.find(id)          PedidoService.find(id)     OK
PedidoService.deleteAll()       (eliminado)                BREAKING
interface Listener { a(); }     interface Listener { a(); b(); }  BREAKING
```

## Plugin Maven (patrón)

```xml
<plugin>
  <groupId>org.revapi</groupId>
  <artifactId>revapi-maven-plugin</artifactId>
  <version>0.15.0</version>
  <executions>
    <execution>
      <goals><goal>check</goal></goals>
    </execution>
  </executions>
  <configuration>
    <oldArtifacts>
      <artifact>com.empresa:api:1.0.0</artifact>
    </oldArtifacts>
  </configuration>
</plugin>
```

```bash
mvn revapi:check
```

## En clase (sin publicar a Nexus)

1. Dibujar dos firmas de API en pizarra.
2. Clasificar: compatible / breaking / deprecación.
3. Conectar con SemVer: breaking ⇒ major.

## Takeaway

Revapi protege a **los consumidores** de tu API mientras tú modernizas el runtime.
