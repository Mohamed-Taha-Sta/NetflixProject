package Controllers;

import Entities.Season;
import Services.ScoreSeasonService;
import Services.ScoreSeriesService;

import java.util.List;

public class ScoreSeriesController {

    public static int GetNumberOfVotersSeason(List<Season> seasonList) {
        return ScoreSeriesService.GetNumberOfVotersSeason(seasonList);
    }

    public static Double GetSeriesScore(List<Season> seasonList){
        return ScoreSeriesService.GetSeriesScore(seasonList);
    }

}
