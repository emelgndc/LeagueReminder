package com.leagueReminder.maven.classes;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.summoner.Summoner;

public class test {
	
	public static void main( String[] args )
    {
	    Orianna.loadConfiguration("config.json");
	    Orianna.setRiotAPIKey("RGAPI-d45315e6-fac4-4834-9b2d-4ed353651a47");
	    
	    Summoner s = Summoner.named("emelg").withRegion(Region.OCEANIA).get();
	    System.out.println(s.isInGame());
    }
    
}
