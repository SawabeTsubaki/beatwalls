package old_structures


import mu.KotlinLogging
import song.Difficulty
import structure.Wall

private val logger = KotlinLogging.logger {}

object OldWallStructureManager
{
    private val wallStructuresList = arrayListOf<OldWallStructure>()

    lateinit var difficulty:Difficulty

    fun loadManager(list:ArrayList<CustomOldWallStructure>) {
        with(wallStructuresList){
            val sealedClassList = OldWallStructure::class.sealedSubclasses
            addAll( sealedClassList.mapNotNull { it.objectInstance }) //adds all objects to the
            addAll(list)
        }
    }


    fun getWallList(oldParameters: OldParameters): ArrayList<Wall> {

        //all the variables
        val list = arrayListOf<Wall>()
        val count = oldParameters.repeatCount
        val gap = oldParameters.repeatGap

        //gets repeat right
        for (i in 0..count) {

            //gets the structure with the given name, or just an empty arrayListOf<_obstacles>
            val structure = wallStructuresList.findLast {
                it.name.toLowerCase() == oldParameters.name.toLowerCase()
            }   ?: return list
            val wallList= structure.getWallList(oldParameters)


            //adjusts the parameter
            wallList.forEach { it.adjustParameters(oldParameters) }

            //adds the mirrored list
            if (structure.mirror)
                //wallList.addAll(wallList.map { it.mirror() })

            //adds the temp list to our List
            list.addAll(wallList)
            oldParameters.startTime += gap
        }
        logger.info { "added ${oldParameters.name}" }
        return list
    }
}