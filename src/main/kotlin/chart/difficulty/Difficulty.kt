package chart.difficulty

import beatwalls.AssetReader
import beatwalls.GlobalConfig
import com.google.gson.annotations.SerializedName
import structure.Define
import structure.WallStructure

data class Difficulty(

    @SerializedName("_version") val _version : String,
    @SerializedName("_events") val _events : ArrayList<_events>,
    @SerializedName("_notes") val _notes : ArrayList<_notes>,
    @SerializedName("_obstacles") val _obstacles : ArrayList<_obstacles>,
    @SerializedName("_customEvents") val _customEvents : ArrayList<_customEvents>?,
    @SerializedName("_customData") val _customData : _customData?
) {
    fun createWalls(list: ArrayList<WallStructure>) {

        //removes the old Obstacles
        removeOldWalls()

        // saves the old obstacles in a list
        val oldObstacles = mutableListOf<_obstacles>()
        for (w in list) {
            //skips lower level define structures
            if (w is Define && !w.isTopLevel) { continue }

            // generates the obstacles
            val obstacles = BpmAdjuster(this).generate(w)

            // adds the obstacle to the diff
            if(!GlobalConfig.clearAll)
                this._obstacles.addAll(obstacles)

            // adds the obstacles to the OldObstacles
            oldObstacles.addAll(obstacles)
        }

        // writes the old obstacles
        AssetReader.writeOldObstacle(oldObstacles.toTypedArray())
    }

    private fun removeOldWalls(){
        if(GlobalConfig.deleteAllPrevious)
            this._obstacles.clear()
        else{
            this._obstacles.removeAll(AssetReader.getOldObstacles())
        }
    }
}



