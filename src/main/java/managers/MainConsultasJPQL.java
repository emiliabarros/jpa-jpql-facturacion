package managers;

import funciones.FuncionApp;
import org.example.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainConsultasJPQL {

    public static void main(String[] args) {
        //REPOSITORIO-> https://github.com/gerardomagni/jpqlquerys.git

        //buscarFacturas();
        //buscarFacturasActivas();
        //buscarFacturasXNroComprobante();
        //buscarFacturasXRangoFechas();
        //buscarFacturaXPtoVentaXNroComprobante();
        //buscarFacturasXCliente();
        //buscarFacturasXCuitCliente();
        //buscarFacturasXArticulo();
        //mostrarMaximoNroFactura();
        //buscarClientesXIds();
        //buscarClientesXRazonSocialParcial();

//        imprimirClientes();
//        facturasUltimoMes();
//        clieteConMasF();
//        ArticulosMasVendidos();
//        facturasUltimos3Meses();
//        totalPorCliente();
//        articulosPorFactura();
//        articuloMasCaro();
        totalFacturas();

    }
//1
    public static void imprimirClientes() {
        ClienteManager mCliente= new ClienteManager(true);
        try {
            List<Cliente> clientes = mCliente.getClientes();
            mostrarClientes(clientes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mCliente.cerrarEntityManager();
        }
    }
//2
    public static void facturasUltimoMes(){
        FacturaManager mFactura= new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasUltimoMes();
            mostrarFacturas(facturas);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mFactura.cerrarEntityManager();
        }
    }
//3
    public static void clieteConMasF(){
        ClienteManager mCliente= new ClienteManager(true);
        try {
            Cliente cliente = mCliente.getClienteConMasFacturas();
            mostrarClientes(cliente);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            mCliente.cerrarEntityManager();
        }
    }
//4
    public static void ArticulosMasVendidos(){
        FacturaManager mFactura= new FacturaManager(true);
        try {
            List<Object[]> articulos = mFactura.getArticulosMasVendidos();
            System.out.println("Filas obtenidas: " + articulos.size());
            for (Object[] fila : articulos) {
                Articulo articulo = (Articulo) fila[0];    // El artículo
                Number totalVendidoNum = (Number) fila[1];        // La cantidad total vendida
                System.out.println("Artículo: " + articulo.getDenominacion());
                System.out.println("Total vendido: " + totalVendidoNum);
                System.out.println("----------------------------");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }
//5
    public static void facturasUltimos3Meses(){
        FacturaManager mFactura= new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasUltimosTresMeses(1);
            mostrarFacturas(facturas);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

//6
    public static void totalPorCliente(){
        System.out.println("Ejecutando consulta...");
        ClienteManager mCliente= new ClienteManager(true);
        try {
            Long idCliente = 1L;
            Double totalFacturado = mCliente.getMontoTotalFacturadoPorCliente(idCliente);
            System.out.println("Monto total facturado por el cliente " + idCliente + ": " + totalFacturado);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            mCliente.cerrarEntityManager();
        }
    }
//7
    public static void articulosPorFactura(){
        FacturaManager mFactura= new FacturaManager(true);
        try {
            Long idFactura = 1L;
            List<Articulo> articulos = mFactura.getArticulosDeFactura(idFactura);
            System.out.println("Articulos de la factura " + idFactura + ":");
            for (Articulo a : articulos) {
                System.out.println("- " + a.getDenominacion());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }
//8
    public static void articuloMasCaro(){
        FacturaManager mFactura= new FacturaManager(true);
        try {
            Long idFactura = 1L;
            Articulo articulo = mFactura.getArticuloMasCaroDeFactura(idFactura);
            System.out.println("Articulo mas caro de la factura " + idFactura + ":");
            System.out.println(articulo.getDenominacion() + " - Precio: $" + articulo.getPrecioVenta());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }
//9
    public static void totalFacturas(){
        FacturaManager mFactura= new FacturaManager(true);
        try {
            Long total = mFactura.getCantidadTotalFacturas();
            System.out.println("Cantidad total de facturas generadas: " + total);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }









//////////////////////////////////////////////////////////
    public static void buscarFacturas(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturas();
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturasActivas(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasActivas();
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturasXNroComprobante(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasXNroComprobante(796910l);
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturasXRangoFechas(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            LocalDate fechaActual = LocalDate.now();
            LocalDate fechaInicio = FuncionApp.restarSeisMeses(fechaActual);
            List<Factura> facturas = mFactura.buscarFacturasXRangoFechas(fechaInicio, fechaActual);
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturaXPtoVentaXNroComprobante(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            Factura factura = mFactura.getFacturaXPtoVentaXNroComprobante(2024, 796910l);
            mostrarFactura(factura);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturasXCliente(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasXCliente(7l);
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturasXCuitCliente(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasXCuitCliente("27236068981");
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarFacturasXArticulo(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            List<Factura> facturas = mFactura.getFacturasXArticulo(6l);
            mostrarFacturas(facturas);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void mostrarMaximoNroFactura(){
        FacturaManager mFactura = new FacturaManager(true);
        try {
            Long nroCompMax = mFactura.getMaxNroComprobanteFactura();
            System.out.println("N° " + nroCompMax);
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mFactura.cerrarEntityManager();
        }
    }

    public static void buscarClientesXIds(){
        ClienteManager mCliente = new ClienteManager(true);
        try {
            List<Long> idsClientes = new ArrayList<>();
            idsClientes.add(1l);
            idsClientes.add(2l);
            List<Cliente> clientes = mCliente.getClientesXIds(idsClientes);
            for(Cliente cli : clientes){
                System.out.println("Id: " + cli.getId());
                System.out.println("CUIT: " + cli.getCuit());
                System.out.println("Razon Social: " + cli.getRazonSocial());
                System.out.println("-----------------");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mCliente.cerrarEntityManager();
        }
    }

    public static void buscarClientesXRazonSocialParcial(){
        ClienteManager mCliente = new ClienteManager(true);
        try {
            List<Long> idsClientes = new ArrayList<>();
            idsClientes.add(1l);
            idsClientes.add(2l);
            List<Cliente> clientes = mCliente.getClientesXRazonSocialParcialmente("Lui");
            for(Cliente cli : clientes){
                System.out.println("Id: " + cli.getId());
                System.out.println("CUIT: " + cli.getCuit());
                System.out.println("Razon Social: " + cli.getRazonSocial());
                System.out.println("-----------------");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            mCliente.cerrarEntityManager();
        }
    }



    public static void mostrarFactura(Factura factura){
        List<Factura> facturas = new ArrayList<>();
        facturas.add(factura);
        mostrarFacturas(facturas);
    }

    public static void mostrarFacturas(List<Factura> facturas){
        for(Factura fact : facturas){
            System.out.println("N° Comp: " + fact.getStrProVentaNroComprobante());
            System.out.println("Fecha: " + FuncionApp.formatLocalDateToString(fact.getFechaComprobante()));
            System.out.println("CUIT Cliente: " + FuncionApp.formatCuitConGuiones(fact.getCliente().getCuit()));
            System.out.println("Cliente: " + fact.getCliente().getRazonSocial() + " ("+fact.getCliente().getId() + ")");
            System.out.println("------Articulos------");
            for(FacturaDetalle detalle : fact.getDetallesFactura()){
                System.out.println(detalle.getArticulo().getDenominacion() + ", " + detalle.getCantidad() + " unidades, $" + FuncionApp.getFormatMilDecimal(detalle.getSubTotal(), 2));
            }
            System.out.println("Total: $" + FuncionApp.getFormatMilDecimal(fact.getTotal(),2));
            System.out.println("*************************");
        }
    }

    public static void mostrarClientes(Cliente cliente){
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(cliente);
        mostrarClientes(clientes);
    }


    public static void mostrarClientes(List<Cliente> clientes){
        for(Cliente cli : clientes){
            System.out.println("Id: " + cli.getId());
            System.out.println("CUIT: " + cli.getCuit());
            System.out.println("Razon Social: " + cli.getRazonSocial());

        }
    }



}
