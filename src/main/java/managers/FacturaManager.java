package managers;

import org.example.Articulo;
import org.example.Factura;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

public class FacturaManager {
    EntityManagerFactory emf = null;
    EntityManager em = null;

    public FacturaManager(boolean anularShowSQL) {
        Map<String, Object> properties = new HashMap<>();
        if(anularShowSQL){
            // Desactivar el show_sql (si está activado en el persistence.xml o configuración por defecto)
            properties.put("hibernate.show_sql", "false");
        }else{
            properties.put("hibernate.show_sql", "true");
        }
        emf = Persistence.createEntityManagerFactory("example-unit", properties);
        em = emf.createEntityManager();

    }

    //Ejercicio 2
    public List<Factura> getFacturasUltimoMes() {
        // Calculamos la fecha hace un mes a partir de hoy
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date fechaHaceUnMes = cal.getTime();

        String jpql = "FROM Factura f WHERE f.fechaAlta >= :fechaLimite";
        Query query = em.createQuery(jpql);
        query.setParameter("fechaLimite", fechaHaceUnMes);
        List<Factura> facturas = query.getResultList();
        return facturas;
    }

    //Ejercicio 4
    public List<Object[]> getArticulosMasVendidos(){
        String jpql = "SELECT fd.articulo, SUM(fd.cantidad) AS totalVendido " +
                "FROM FacturaDetalle fd " +
                "GROUP BY fd.articulo " +
                "ORDER BY totalVendido DESC";
        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        List<Object[]> articulos = query.getResultList();
        return articulos;
    }
    //Ejercicio 5
    public List<Factura> getFacturasUltimosTresMeses(int idCliente){
        // Calculamos la fecha de hace 3 meses
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -3);
        Date fechaLimite = cal.getTime();
        String jpql = "FROM Factura f " +
                "WHERE f.cliente.id = :idCliente " +
                "AND f.fechaAlta >= :fechaLimite";
        Query query = em.createQuery(jpql);
        query.setParameter("idCliente", Long.valueOf(idCliente));
        query.setParameter("fechaLimite", fechaLimite);
        return query.getResultList();
    }
    //Ejercicio 7
    public List<Articulo> getArticulosDeFactura(Long idFactura) {
        String jpql = "SELECT fd.articulo " +
                "FROM FacturaDetalle fd " +
                "WHERE fd.factura.id = :idFactura";

        TypedQuery<Articulo> query = em.createQuery(jpql, Articulo.class);
        query.setParameter("idFactura", idFactura);

        return query.getResultList();
    }
    //Ejercicio 8
    public Articulo getArticuloMasCaroDeFactura(Long idFactura) {
        String jpql = "SELECT fd.articulo " +
                "FROM FacturaDetalle fd " +
                "WHERE fd.factura.id = :idFactura " +
                "ORDER BY fd.articulo.precioVenta DESC";

        TypedQuery<Articulo> query = em.createQuery(jpql, Articulo.class);
        query.setParameter("idFactura", idFactura);
        query.setMaxResults(1); // solo el más caro

        return query.getSingleResult();
    }
    //Ejercicio 9
    public Long getCantidadTotalFacturas() {
        String jpql = "SELECT COUNT(f) FROM Factura f";
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        return query.getSingleResult();
    }








    public List<Factura> getFacturas(){
        String jpql = "SELECT f FROM Factura f";
        Query query = em.createQuery(jpql);

        List<Factura> facturas = query.getResultList();
        return facturas;
    }

    public List<Factura> getFacturasActivas(){
        //si quiero buscar distintos de NULL uso -> IS NOT NULL
        String jpql = "FROM Factura WHERE fechaBaja IS NULL ORDER BY fechaComprobante DESC";
        Query query = em.createQuery(jpql);

        List<Factura> facturas = query.getResultList();
        return facturas;
    }

    public List<Factura> getFacturasXNroComprobante(Long nroComprobante){
        String jpql = "FROM Factura WHERE nroComprobante = :nroComprobante";
        Query query = em.createQuery(jpql);
        query.setParameter("nroComprobante", nroComprobante);

        List<Factura> facturas = query.getResultList();
        return facturas;
    }

    public List<Factura> buscarFacturasXRangoFechas(LocalDate fechaInicio, LocalDate fechaFin){
        String jpql = "FROM Factura WHERE fechaComprobante >= :fechaInicio AND fechaComprobante <= :fechaFin";
        Query query = em.createQuery(jpql);
        query.setParameter("fechaInicio", fechaInicio);
        query.setParameter("fechaFin", fechaFin);

        List<Factura> facturas = query.getResultList();
        return facturas;
    }

    public Factura getFacturaXPtoVentaXNroComprobante(Integer puntoVenta, Long nroComprobante){
        String jpql = "FROM Factura WHERE puntoVenta = :puntoVenta AND nroComprobante = :nroComprobante";
        Query query = em.createQuery(jpql);
        query.setMaxResults(1);
        query.setParameter("puntoVenta", puntoVenta);
        query.setParameter("nroComprobante", nroComprobante);

        Factura factura = (Factura) query.getSingleResult();
        return factura;
    }

    public List<Factura> getFacturasXCliente(Long idCliente){
        String jpql = "FROM Factura WHERE cliente.id = :idCliente";
        Query query = em.createQuery(jpql);
        query.setParameter("idCliente", idCliente);

        List<Factura> facturas = query.getResultList();
        return facturas;
    }

    public List<Factura> getFacturasXCuitCliente(String cuitCliente){
        String jpql = "FROM Factura WHERE cliente.cuit = :cuitCliente";
        Query query = em.createQuery(jpql);
        query.setParameter("cuitCliente", cuitCliente);

        List<Factura> facturas = query.getResultList();
        return facturas;
    }

    public List<Factura> getFacturasXArticulo(Long idArticulo){ //INNER JOIN, LEFT JOIN, LEFT OUTER JOIN, etc
        StringBuilder jpql = new StringBuilder("SELECT fact FROM Factura AS fact LEFT OUTER JOIN fact.detallesFactura AS detalle");
        jpql.append(" WHERE detalle.id = :idArticulo");
        Query query = em.createQuery(jpql.toString());
        query.setParameter("idArticulo", idArticulo);

        List<Factura> facturas = query.getResultList();
        return facturas;
    }

    public Long getMaxNroComprobanteFactura(){ //MAX, MIN, COUNT, AVG, SUM
        StringBuilder jpql = new StringBuilder("SELECT MAX(nroComprobante) FROM Factura WHERE fechaBaja IS NULL");
        Query query = em.createQuery(jpql.toString());

        Long maxNroFactura = (Long) query.getSingleResult();
        return maxNroFactura;
    }

    public void cerrarEntityManager(){
        em.close();
        emf.close();
    }
}
