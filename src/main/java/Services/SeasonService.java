package Services;

import java.util.List;
import java.util.stream.Collectors;

public class SeasonService {

    public static long StreamAverageScore(List<Long> listScore)
    {
         Double res = listScore.stream()
                .collect(Collectors.averagingLong(Long::longValue));

         return Math.round(res);
    }

}
