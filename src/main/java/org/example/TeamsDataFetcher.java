package org.example;

import com.netflix.graphql.dgs.*;
import graphql.schema.DataFetchingEnvironment;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@DgsComponent
public class TeamsDataFetcher {

    private final Logger logger = LoggerFactory.getLogger(TeamsDataFetcher.class);

    private final List<Team> teams = List.of(new Team(1, "Tranmere Rovers FC"),
                                                new Team(2, "Everton Fc"));

    @DgsEntityFetcher(name = "MarketTeamMapping")
    public MarketTeamMapping getMarketTeam(Map<String, Object> values) {
        return new MarketTeamMapping(Integer.valueOf((String) values.get("id")), 2,1);
    }

    @DgsData(parentType = "MarketTeamMapping", field = "team")
    public List<Team> getTeams(DataFetchingEnvironment dfe) {
        MarketTeamMapping marketTeamMapping = dfe.getSource();
        logger.debug(String.format("We have team id {}", marketTeamMapping.getTeamId()));
        return teams.stream().filter(x -> x.getId()== marketTeamMapping.getTeamId()).toList();

    }

}
