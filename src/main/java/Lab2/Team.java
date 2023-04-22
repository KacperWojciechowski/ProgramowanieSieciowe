package Lab2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class Team {
    @Getter @Setter
    String name;
    @Getter @Setter
    List<Match> awayMatches = new ArrayList<>();
    @Getter @Setter
    List<Match> homeMatches = new ArrayList<>();

    public void AppendAwayMatch(Match match)
    {
        awayMatches.add(match);
    }

    public void AppendHomeMatch(Match match)
    {
        homeMatches.add(match);
    }
}
