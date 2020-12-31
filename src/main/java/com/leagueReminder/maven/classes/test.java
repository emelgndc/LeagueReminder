package com.leagueReminder.maven.classes;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

public class test {
	
	public static void main( String[] args )
    {
	    Orianna.loadConfiguration("config.json");
	    Orianna.setRiotAPIKey("RGAPI-c12552ef-9307-4026-919c-96c91c71b5da");
	    
	    Summoner s = Summoner.named("emelg").withRegion(Region.OCEANIA).get();
	    System.out.println(s.isInGame());
    }
    
}
