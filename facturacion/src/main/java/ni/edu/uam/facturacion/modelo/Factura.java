package ni.edu.uam.facturacion.modelo;

import java.time.*;
import java.util.Collection;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.*;
import org.openxava.calculators.*;
import lombok.*;

@View(members= // Esta vista no tiene nombre, por tanto será la vista usada por defecto
        "anyo, numero, fecha;" + // Separados por coma significa en la misma línea
                "cliente;" + // Punto y coma significa nueva línea
                "detalles;" +
                "observaciones"
)

@Entity @Getter @Setter
public class Factura {

    @Id
    @GeneratedValue(generator="system-uuid")
    @Hidden
    @GenericGenerator(name="system-uuid", strategy="uuid")
    @Column(length=32)
    String oid;

    @Column(length=4)
    @DefaultValueCalculator(CurrentYearCalculator.class) // Año actual
    int anyo;

    @Column(length=6)
    int numero;

    @Required
    @DefaultValueCalculator(CurrentLocalDateCalculator.class) // Fecha actual
    LocalDate fecha;

    @TextArea
    String observaciones;

    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @ReferenceView("Simple") // La vista llamada 'Simple' se usará para visualizar esta referencia
    Cliente cliente;

    @ElementCollection
    @ListProperties("producto.numero, producto.descripcion, cantidad")
    Collection<Detalle> detalles;


}