package Lab2;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class Main {
    public static String[] getTeams(Element teamsElement)
    {
        String[] teams = new String[2];

        for(Element element : teamsElement.children())
        {
            if (element.is("a") && element.attr("href").contains("druzyny"))
            {
                Element teamElement = element.child(0);
                if (teamElement.attr("itemprop").equals("homeTeam")) {
                    teams[0] = teamElement.text();
                }
                else if (teamElement.attr("itemprop").equals("awayTeam")) {
                    teams[1] = teamElement.text();
                }
            }
        }

        return teams;
    }

    public static String getStartDate(Element dateElement)
    {
        return dateElement.text();
    }

    public static String getScore(Element scoreElement)
    {
        String ret = "";
        for (Element element : scoreElement.children())
        {
            if (element.is("a") && element.hasAttr("href"))
            {
                ret = element.text();
                break;
            }
        }
        return ret;
    }

    public static final int INDEX_NOT_FOUND = -1;
    public static int checkForTeam(List<Team> teams, String teamName)
    {
        int index = 0;
        for (Iterator<Team> team = teams.iterator(); team.hasNext(); index++)
        {
            if (team.next().name.equalsIgnoreCase(teamName))
            {
                return index;
            }
        }
        return INDEX_NOT_FOUND;
    }

    public static Team getTeamReferenceFromList(List<Team> teams, String searchedTeam)
    {
        int teamIndex = checkForTeam(teams, searchedTeam);
        if (teamIndex != INDEX_NOT_FOUND)
        {
            return teams.get(teamIndex);
        }
        else
        {
            Team team = new Team();
            team.setName(searchedTeam);
            teams.add(team);
            return team;
        }
    }

    public static void main(String[] args) throws IOException
    {
        Document doc;
        doc = SSLHelper.getConnection("https://plk.pl/terminarz-i-wyniki.html").get();

        String title = doc.title();
        System.out.println("Title: " + title);

        List<Team> teamList = new ArrayList<>();

        Elements games = doc.select("tr[itemscope]");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        for (Element game : games)
        {
            Elements tdChildren = game.children();
            String[] teams = getTeams(tdChildren.get(0));
            String startDate = getStartDate(tdChildren.get(2));
            String score = getScore(tdChildren.get(3));

            System.out.println("Mecz " + teams[0] + " vs " + teams[1]);
            System.out.println("Date: " + startDate);
            System.out.println("Score: " + score + "\n\n");

            Team hostTeam = getTeamReferenceFromList(teamList, teams[0]);
            Team guestTeam = getTeamReferenceFromList(teamList, teams[1]);

            Match match = new Match();
            match.setHost(hostTeam);
            match.setGuest(guestTeam);
            match.setDate(LocalDateTime.parse(startDate, formatter));
            if (score.contains("--"))
            {
                match.setHostPoints(Match.NO_SCORE);
                match.setGuestPoints(Match.NO_SCORE);
            }
            else
            {
                int separatorPos = score.indexOf(':');
                match.setHostPoints(Integer.parseInt(score.substring(0, separatorPos)));
                match.setGuestPoints(Integer.parseInt(score.substring(separatorPos + 1)));
            }
            hostTeam.AppendHomeMatch(match);
            guestTeam.AppendAwayMatch(match);
        }

    }
}
