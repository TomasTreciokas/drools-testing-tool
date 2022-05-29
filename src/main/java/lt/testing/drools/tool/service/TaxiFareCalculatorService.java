package lt.testing.drools.tool.service;

import lt.testing.drools.tool.model.Fare;
import lt.testing.drools.tool.model.DroolsData;
import lt.testing.drools.tool.model.TaxiRide;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TaxiFareCalculatorService {

    private final KieContainer kieContainer;
    private final TrackingAgendaEventListener trackingAgendaEventListener;
    private final KieContainer kieMapContainer;

    @Autowired
    public TaxiFareCalculatorService(
            @Qualifier("kieContainerTaxiFare") KieContainer kieContainer,
            TrackingAgendaEventListener trackingAgendaEventListener,
            @Qualifier("kieContainerTaxiFareMap") KieContainer kieMapContainer
    ){
        this.kieContainer = kieContainer;
        this.trackingAgendaEventListener = trackingAgendaEventListener;
        this.kieMapContainer = kieMapContainer;
    }

    public Long calculateFare(TaxiRide taxiRide){
        Fare rideFare = new Fare();
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.addEventListener(trackingAgendaEventListener);
        kieSession.setGlobal("rideFare", rideFare);
        kieSession.insert(taxiRide);
        kieSession.fireAllRules();
        kieSession.dispose();

        System.out.println();
        return rideFare.getTotalFare();
    }

    public Long calculateFareMap(DroolsData taxiRide){
        Fare rideFare = new Fare();
        KieSession kieSession = kieMapContainer.newKieSession();
        kieSession.addEventListener(trackingAgendaEventListener);
        kieSession.setGlobal("rideFare", rideFare);
        kieSession.insert(taxiRide);
        kieSession.fireAllRules();
        kieSession.dispose();

        System.out.println();
        return rideFare.getTotalFare();
    }
}
