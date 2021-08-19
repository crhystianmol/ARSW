package edu.eci.arsw.blacklistvalidator;

/**
 * Clase que realiza la sincronización entre los hilos que evalúan las IPs. Su lógica involucra una zona crítica pequeña que mejora la eficiencia en
 * conjunto de los hilos utilizados.
 *
 */
public class Monitor {

    private int cuenta;

    /**
     * Constructor principal de la clase. Inicializa el conteo de servidores.
     */
    public Monitor() {
        restart();
    }

    /**
     * Aumenta la cuenta que se lleva de los servidores en los que se encontró la IP maliciosa.
     */
    public synchronized void aumentarContador() {
        cuenta++;
    }

    /**
     * Verifica si la cuenta de los servidores ya a alcanzado el límite contenido en la clase HostBlackListsValidator.
     * @return true si ya se pasó el límite, false en caso contrario.
     */
    public boolean esValido() {
        return cuenta < HostBlackListsValidator.BLACK_LIST_ALARM_COUNT;
    }

    /**
     * Reinicia la cuenta de los servidores.
     */
    public void restart() {
        cuenta = 0;
    }


}
