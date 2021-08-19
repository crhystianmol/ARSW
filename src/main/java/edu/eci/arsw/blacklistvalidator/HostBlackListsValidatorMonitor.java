package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



public class HostBlackListsValidatorMonitor extends HostBlackListsValidator {

    @Override
    public List<Integer> checkHost(String ipaddress, int N){
        LinkedList<Integer> blackListOcurrences=new LinkedList<>();

        int ocurrencesCount=0;

        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        int serverCount = skds.getRegisteredServersCount();
        int checkedListsCount=0;

        if (N <= 0 || serverCount < N) N = 1;

        int nThreads = N;
        if (serverCount % N != 0) nThreads--;

        HostBlackListsValidatorThread[] threads = new HostBlackListsValidatorThreadMonitor[N];
        int seccion = serverCount / nThreads;

        Monitor monitor = new Monitor();

        for (int i=0; i<N; i++) {
            threads[i] = new HostBlackListsValidatorThreadMonitor(i*seccion, Math.min((i+1)*seccion, serverCount-1), ipaddress, monitor);
            threads[i].start();
        }

        for (HostBlackListsValidatorThread t: threads) {
            try {
                t.join();
                checkedListsCount += t.getOcurrencesCount();
                ocurrencesCount += t.getServersAmount();
                blackListOcurrences.addAll(t.getServersFound());
            } catch(InterruptedException ie) {

            }
        }


        if (ocurrencesCount>=BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{
            skds.reportAsTrustworthy(ipaddress);
        }

        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, serverCount});

        return blackListOcurrences;
    }



}
