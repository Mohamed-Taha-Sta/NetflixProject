package Controllers.FXMLControllers;

import Entities.Season;
import Services.ScoreSeriesService;

import java.util.List;

public class ScoreSeriesController {

    public static int GetNumberOfVotersSeason(List<Season> seasonList) {
        return ScoreSeriesService.GetNumberOfVotersSeason(seasonList);
    }

}
