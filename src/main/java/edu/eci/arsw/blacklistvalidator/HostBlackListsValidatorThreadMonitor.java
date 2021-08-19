package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;


public class HostBlackListsValidatorThreadMonitor extends HostBlackListsValidatorThread {

    private Monitor monitor;


    /**
     * Constructor de la clase HostBlackListsValidatorThreadEficiente. Recibe un monitor para asegurar sincronización con memoria compartida.
     *
     * @param inicio inicio de la partición de datos asignada.
     * @param fin fin de la partición de datos asignada. Es inclusive.
     * @param ip dirección ip sobre la que se realizará la validación.
     * @param monitor referencia al monitor que se utiliza como mecanismo de sincronización entre este y otros hilos.
     */
    public HostBlackListsValidatorThreadMonitor(int inicio, int fin, String ip, Monitor monitor) {
        this(inicio, fin, ip);
        this.monitor = monitor;
    }

    /**
     * Constructor principal de la clase.
     *
     * @param inicio inicio de la partición de datos asignada.
     * @param fin    fin de la partición de datos asignada. Es inclusive.
     * @param ip     dirección ip sobre la que se realizará la validación.
     */

    public HostBlackListsValidatorThreadMonitor(int inicio, int fin, String ip) {
        super(inicio, fin, ip);
    }

    @Override
    public void run() {
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        for (int i=inicio; i<=fin && monitor.esValido(); i++) {
            ocurrencesCount++;
            if (skds.isInBlackListServer(i, ip)) {
                monitor.aumentarContador();
                servers.add(i);
            }
        }
    }

}
