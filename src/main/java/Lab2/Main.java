package Lab2;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {
    public static String[] getHomeTeam(Element teamsElement)
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

    public static void main(String[] args) throws IOException
    {
        Document doc;
        doc = SSLHelper.getConnection("https://plk.pl/terminarz-i-wyniki.html").get();

        String title = doc.title();
        System.out.println("Title: " + title);

        Elements games = doc.select("tr[itemscope]");
        for (Element game : games)
        {
            Elements tdChildren = game.children();
            String[] teams = getHomeTeam(tdChildren.get(0));
            String startDate = getStartDate(tdChildren.get(2));
            String score = getScore(tdChildren.get(3));

            System.out.println("Mecz " + teams[0] + " vs " + teams[1]);
            System.out.println("Date: " + startDate);
            System.out.println("Score: " + score + "\n\n");
        }

    }
}
