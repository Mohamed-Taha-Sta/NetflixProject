package Services;

import Controllers.ScoreEpisodeController;
import Entities.Episode;

import java.util.List;

public class ScoreSeasonService {

    public static int GetNumberOfVotersSeason(List<Episode> episodeList)
    {
        return episodeList.stream()
                .map(episode -> ScoreEpisodeController.GetNumberVotesEpisode(episode))
                .reduce((integer, integer2) -> integer+integer2)
                .get();
    }

}
