package managers;

import funciones.FuncionApp;
import org.example.Cliente;
import org.example.Factura;

import javax.persistence.*;
import java.util.*;

public class ClienteManager {
    EntityManagerFactory emf = null;
    EntityManager em = null;

    public ClienteManager(boolean anularShowSQL) {
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

    //Ejercicio 1
    public List<Cliente> getClientes() {
        Query query = em.createQuery(" FROM " + Cliente.class.getSimpleName());
        List<Cliente> clientes = query.getResultList();
        return clientes;
    }

    //Ejercicio 3
    public Cliente getClienteConMasFacturas() {
        String jpql = "SELECT f.cliente " +
                "FROM Factura f " +
                "GROUP BY f.cliente " +
                "ORDER BY COUNT(f) DESC";
        Cliente cliente = em.createQuery(jpql, Cliente.class)
                .setMaxResults(1)
                .getSingleResult();
        return cliente;
    }

    //Ejercicio 6
    public Double getMontoTotalFacturadoPorCliente(Long idCliente) {
        String jpql = "SELECT SUM(f.total) " +
                "FROM Factura f " +
                "WHERE f.cliente.id = :idCliente";

        TypedQuery<Double> query = em.createQuery(jpql, Double.class);
        query.setParameter("idCliente", idCliente);
        Double resultado = query.getSingleResult();
        return resultado != null ? resultado : 0.0;
    }

    public List<Cliente> getClientesXIds(List<Long> idsClientes){
        String jpql = "FROM Cliente WHERE id IN (:idsClientes) ORDER BY razonSocial ASC";
        Query query = em.createQuery(jpql);
        query.setParameter("idsClientes", idsClientes);
        List<Cliente> clientes = query.getResultList();
        return clientes;
    }

    public List<Cliente> getClientesXRazonSocialParcialmente(String razonSocial){
        StringBuilder jpql = new StringBuilder("SELECT DISTINCT(cliente) FROM Cliente cliente WHERE 1=1 ");
        if(!FuncionApp.isEmpty(razonSocial))
            jpql.append(this.parseSearchField("cliente.razonSocial", razonSocial));
        jpql.append(" ORDER BY cliente.razonSocial ASC");
        Query query = em.createQuery(jpql.toString());
        List<Cliente> clientes = query.getResultList();
        return clientes;
    }

    public void cerrarEntityManager(){
        em.close();
        emf.close();
    }

    public String parseSearchField(String field, String value) {

        String[] fields = new String[1];
        fields[0] = field;

        return this.parseSearchField(fields, value);
    }

    public String parseSearchField(String field[], String value) {
        if(value != null) {
            String[] values = value.split(" ");
            StringBuffer result = new StringBuffer();

            for(int i = 0; i < values.length; i++) {

                StringBuffer resultFields = new StringBuffer();

                if(!values[i].trim().equals("")){
                    result.append(" AND ");

                    for (int j = 0; j < field.length ; j++){

                        if (j!=0)
                            resultFields.append(" OR ");

                        resultFields.append(" (LOWER(" + field[j].trim() + ") LIKE '" + values[i].trim().toLowerCase() + "%' OR LOWER(" + field[j].trim() + ") LIKE '%" + values[i].trim().toLowerCase() + "%')");
                    }
                    result.append("(" + resultFields.toString() + ")");
                }
            }

            return result.toString();
        }

        return "";
    }
}
