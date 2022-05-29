package lt.testing.drools.tool.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Fare {

    private Long nightSurcharge;
    private Long rideFare;

    public Fare() {
        this.nightSurcharge = 0L;
        this.rideFare = 0L;
    }

    public Long getTotalFare() {
        return nightSurcharge + rideFare;
    }

}
