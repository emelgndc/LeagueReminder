package com.leagueReminder.maven.classes;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Orianna.loadConfiguration("config.json");
        Orianna.setRiotAPIKey("API KEY HERE");
        
        Window window = new Window();
        //Summoner emelg = Summoner.named("emelg").get();
        //System.out.println(emelg.getLevel());
        
    }
}
