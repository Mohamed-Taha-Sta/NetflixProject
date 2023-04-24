package Controllers;

import Entities.Episode;
import Entities.Season;
import Services.ScoreEpisodeService;
import Services.ScoreSeasonService;
import Services.SeasonService;
import Utils.DataHolderSeason;
import Utils.DataHolderSeries;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ScoreSeasonController {

    public static int GetNumberOfVotersSeason(Season season) throws SQLException, IOException {
        List<Episode> episodeList = SeasonController.FindEpisodeSeasonID(season);

        return ScoreSeasonService.GetNumberOfVotersSeason(episodeList);
    }

    public static Double GetSeasonScore(Season season) throws SQLException, IOException {
        List<Episode> episodeList = SeasonController.FindEpisodeSeasonID(season);
        return ScoreSeasonService.GetSeasonScore(episodeList);
    }



}
