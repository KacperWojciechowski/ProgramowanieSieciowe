package Lab2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
public class Match {

    @Getter @Setter
    Team guest;

    @Getter @Setter
    Team host;

    @Getter @Setter
    int guestPoints;

    @Getter @Setter
    int hostPoints;

    @Getter @Setter
    LocalDateTime date;

    public final static int NO_SCORE = -1;
}
