package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HostBlackListsValidator {

    protected static final int BLACK_LIST_ALARM_COUNT=5;
    
    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @return  Blacklists numbers where the given host's IP address was found.
     */
    public List<Integer> checkHost(String ipaddress){

        return checkHost(ipaddress, 1);
    }

    public List<Integer> checkHost(String ipaddress, int N) {
        LinkedList<Integer> blackListOcurrences = new LinkedList<>();

        int ocurrencesCount = 0;

        HostBlacklistsDataSourceFacade skds = HostBlacklistsDataSourceFacade.getInstance();
        int serverCount = skds.getRegisteredServersCount();
        int checkedListsCount = 0;

        if (N <= 0 || serverCount < N) N = 1;

        int nThreads = N;
        if (serverCount % N != 0) nThreads--;

        HostBlackListsValidatorThread[] threads = new HostBlackListsValidatorThread[N];
        int seccion = serverCount / nThreads;

        for (int i = 0; i < N; i++) {
            threads[i] = new HostBlackListsValidatorThread(i * seccion, Math.min((i + 1) * seccion, serverCount - 1), ipaddress);
            threads[i].start();
        }

        for (HostBlackListsValidatorThread t : threads) {
            try {
                t.join();
                checkedListsCount += t.getOcurrencesCount();
                ocurrencesCount += t.getServersAmount();
                blackListOcurrences.addAll(t.getServersFound());
            } catch (InterruptedException ie) {

            }
        }


        if (ocurrencesCount >= BLACK_LIST_ALARM_COUNT) {
            skds.reportAsNotTrustworthy(ipaddress);
        } else {
            skds.reportAsTrustworthy(ipaddress);
        }

        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, serverCount});

        return blackListOcurrences;
    }
    
    
    public static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
    
    

}
