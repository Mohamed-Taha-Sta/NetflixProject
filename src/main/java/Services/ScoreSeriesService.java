package Services;

import Controllers.ScoreEpisodeController;
import Controllers.ScoreSeasonController;
import Entities.Episode;
import Entities.Season;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ScoreSeriesService {


    public static int GetNumberOfVotersSeason(List<Season> seasonList)
    {
        return seasonList.stream()
                .map(season -> {
                    try {
                        return ScoreSeasonController.GetNumberOfVotersSeason(season);
                    } catch (SQLException | IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .reduce(Integer::sum)
                .orElse(0);

    }


}
