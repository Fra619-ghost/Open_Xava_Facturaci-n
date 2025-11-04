package ni.edu.uam.facturacion.calculadores;

import javax.persistence.*;

import org.openxava.annotations.DefaultValueCalculator;
import org.openxava.annotations.PropertyValue;
import org.openxava.calculators.*;
import org.openxava.jpa.*;
import lombok.*;

public class CalculadorSiguienteNumeroParaAnyo
        implements ICalculator { // Un calculador tiene que implementar ICalculator

    @Getter @Setter
    int anyo; // Este valor se inyectará antes de calcular

    public Object calculate() throws Exception { // Hace el cálculo
        Query query = XPersistence.getManager() // Una consulta JPA
                .createQuery("select max(f.numero) from Factura f where f.anyo = :anyo"); // La consulta devuelve
        // el número de factura máximo del año indicado
        query.setParameter("anyo", anyo); // Ponemos el año inyectado como parámetro de la consulta
        Integer ultimoNumero = (Integer) query.getSingleResult();
        return ultimoNumero == null ? 1 : ultimoNumero + 1; // Devuelve el último número
        // de factura del año + 1 o 1 si no hay último número
    }

    @DefaultValueCalculator(value=CalculadorSiguienteNumeroParaAnyo.class,
            properties=@PropertyValue(name="anyo") // Para inyectar el valor de anyo de Factura
            // en el calculador antes de llamar a calculate()
    )
    int numero;



}