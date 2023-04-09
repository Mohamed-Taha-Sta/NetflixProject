package Services;

import DAO.EpisodeDAO2;
import Entities.Episode;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EpisodeService {




    public static boolean AddEpisode(Episode episode) throws SQLException, IOException {
        return EpisodeDAO2.AddEpisode(episode);
    }

    public static List<Episode> FindEpisodeSeasonID(long id) throws SQLException, IOException {
        return EpisodeDAO2.FindEpisodeSeasonID(id);
    }

    public static List<Episode> FindEpisodeName(String EpisodeName) throws SQLException, IOException {
        return EpisodeDAO2.FindEpisodeName(EpisodeName);
    }

    public static List<Episode> FindEpisodeID(long id) throws SQLException, IOException {
        return EpisodeDAO2.FindEpisodeID(id);
    }

    public static long getVotesEpisode(Episode ep) throws SQLException {
        return EpisodeDAO2.getVotesEpisode(ep);
    }

    public static long getScoreEpisode(Episode ep) throws SQLException {
        return EpisodeDAO2.getScoreEpisode(ep);
    }

    public static long getViewNbrEpisode(Episode ep){
        return EpisodeDAO2.getViewNbrEpisode(ep);
    }

    public static boolean UpdatePositiveScoreEpisode(Episode ep) throws SQLException {
        return EpisodeDAO2.UpdatePositiveScoreEpisode(ep);
    }

    public static boolean UpdateNegativeScoreEpisode(Episode ep) throws SQLException {
        return EpisodeDAO2.UpdateNegativeScoreEpisode(ep);
    }

    public static boolean UpdateViewNbrEpisode(Episode ep) throws SQLException {
        return EpisodeDAO2.UpdateViewNbrEpisode(ep);
    }







}
