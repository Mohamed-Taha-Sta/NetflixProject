package Services;

import Controllers.ScoreEpisodeController;
import Controllers.ScoreSeasonController;
import Entities.Episode;
import Entities.Season;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

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


    public static Double GetSeriesScore(List<Season> seasonList) {
        return seasonList.stream()
                .map(season -> {
                    try {
                        return ScoreSeasonController.GetSeasonScore(season);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return 0D;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return 0D;
                    }
                }).collect(Collectors.averagingDouble(Double::doubleValue));
    }
}
