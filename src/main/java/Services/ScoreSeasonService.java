package Services;

import Controllers.ScoreEpisodeController;
import Entities.Episode;

import java.util.List;
import java.util.stream.Collectors;

public class ScoreSeasonService {

    public static int GetNumberOfVotersSeason(List<Episode> episodeList)
    {
        return episodeList.stream()
                .map(episode -> ScoreEpisodeController.GetNumberVotesEpisode(episode))
                .reduce((integer, integer2) -> integer+integer2)
                .orElse(0);
    }

    public static Double GetSeasonScore(List<Episode> episodeList) {
        return episodeList.stream()
                .map(episode -> ScoreEpisodeController.GetEpisodeScore(episode))
                .collect(Collectors.averagingDouble(Double::doubleValue));
    }
}
