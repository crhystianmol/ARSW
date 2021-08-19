package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;

import java.util.LinkedList;

public class HostBlackListsValidatorThread extends Thread {

    protected int inicio;
    protected int fin;
    protected String ip;
    protected LinkedList<Integer> servers;
    protected int ocurrencesCount;

    /**
     * Constructor principal de la clase.
     * @param inicio inicio de la partición de datos asignada.
     * @param fin fin de la partición de datos asignada. Es inclusive.
     * @param ip dirección ip sobre la que se realizará la validación.
     */
    public HostBlackListsValidatorThread(int inicio, int fin, String ip) {
        inicializacion(inicio, fin, ip);
    }

    /**
     * Obtiene la cantidad de servidores maliciosos que se han encontrado durante la ejecucion del proceso
     * @return La cantidad de servidores maliciosos hasta el momento de la invocación
     */
    public int getOcurrencesCount() {
        return ocurrencesCount;
    }

    /**
     * Devuelve la cantidad de servidores en los quela dirección ip fue encontrada.
     * @return los servidores que se encontraron hasta el momento de la invocación.
     */
    public LinkedList<Integer> getServersFound(){
        return servers;
    }

    /**
     * Devuelve la cantidad de veces que la dirección ip fue encontrada en los servidores.
     * @return el numero de veces que la ip se encontró hasta el momento de la invocación.
     */
    public int getServersAmount() {
        return servers.size();
    }

    @Override
    public void run() {
        HostBlacklistsDataSourceFacade skds= HostBlacklistsDataSourceFacade.getInstance();
        for (int i=inicio; i<=fin; i++) {
            ocurrencesCount++;
            if (skds.isInBlackListServer(i, ip)) {
                servers.add(i);
            }
        }
    }

    public void inicializacion(int inicio, int fin, String ip){
        this.inicio = inicio;
        this.fin = fin;
        this.ip = ip;
        ocurrencesCount = 0;
        servers = new LinkedList<>();
    }
}
