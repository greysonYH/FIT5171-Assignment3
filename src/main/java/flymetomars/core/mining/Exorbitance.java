package flymetomars.core.mining;

import flymetomars.data.MissionDAO;
import flymetomars.model.Mission;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by greyson on 24/5/17.
 */
public class Exorbitance {
    MissionDAO missionDAO;
    public Exorbitance(MissionDAO missionDAO){
        this.missionDAO = missionDAO;
    }

    public List<Mission> getTopKMostExpensiveMissions(int k) throws SQLException {
        List<Mission> missionList= missionDAO.getAll();
        Map<Mission, Integer> sortedMissionWithCost = new HashMap<>();
        List<Mission> topKMission = new ArrayList<>();

        // Get all the mission and its total equipment cost, store in a HashMap
        Map<Mission, Integer> missionCostMap = new HashMap<>();
        for (Mission m : missionList) {
            missionCostMap.put(m, m.getEquipmentTotalCost());
        }

        // Sort the Missions by the cost
        sortedMissionWithCost = EntityMiner.sortByValues(missionCostMap);

        // Get the sorted mission list
        Iterator<Mission> it = sortedMissionWithCost.keySet().iterator();
        while (it.hasNext()) {
            topKMission.add(it.next());
        }
        if (k > topKMission.size())
            k = topKMission.size();

        // Return top-k missions
        return topKMission.subList(0, k);
    }
}

