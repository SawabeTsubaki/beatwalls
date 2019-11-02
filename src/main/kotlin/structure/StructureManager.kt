package structure

import assetFile.AssetController
import com.github.ajalt.clikt.core.subcommands
import mu.KotlinLogging
import parameter.Command
import parameter.WallParameter
import song.Difficulty

@Suppress("RedundantEmptyInitializerBlock")
object StructureManager {
    private val logger = KotlinLogging.logger {}
    private val allWallStructure = allDefaultWalls()
    private val bpm = AssetController.getBPM()

    init {
        //allWallStructure.addAll(AssetController.mixedWallStructures().map { it.toCustomWallStructure() })
    }
    private fun allDefaultWalls(): ArrayList<WallStructure> {
        val specialWalls = SpecialWallStructure::class.sealedSubclasses
            .mapNotNull { it.objectInstance }

        val customWalls =
            AssetController.customWallStructures()

        val list = specialWalls + customWalls // + mWallCommandStruct
        return  ArrayList(list)
    }

    private fun findStructure(c: List<String>): List<WallStructure> {
        val names = c.map { it.toLowerCase() }
        val list = allWallStructure
        return list.filter {
            names.contains(it.name.toLowerCase())
        }
    }

    fun walls(c:Command, d:Difficulty?): List<Wall> {
        val arr = ArrayList(c.command.split(" ").filter { it.isNotEmpty() })
        val struct = findStructure(arr)
        var p = WallParameter()

        /** ads the subcommands to the special wallstructures */
        struct
            .filterIsInstance<SpecialWallStructure>()
            .forEach {  p = p.subcommands(it)   }

        /** removes the normal wallStructures from the args */
        struct
            .filterIsInstance<CustomWallStructure>()
            .forEach { arr.remove(it.name.toLowerCase()) }

        p.parse(arr)
        var tempWalls = struct.flatMap { it.walls() }

        /** bpm change */
        val tempBpm =
            d?._BPMChanges?.findLast { bpmChanges -> bpmChanges._time <= c.beatStartTime }?._BPM ?: bpm

        /** before bpm change*/
        with (p){
            tempWalls =tempWalls
                .mapIf(fast) {it.fast()}
                .mapIf(hyper){ it.hyper() }
                .flatMapIf(split!=null) {it.split(split!!)}
                .flatMapIf(mirror) {it.mirror(noDuplicate)}
                .flatMapIf(verticalMirror) {it.verticalMirror(d=noDuplicate)}
                .flatMapIf(pointMirror) {it.pointMirror(d=noDuplicate)}
                    // repeat and scale
                .flatMapIf(repeat!= null) {it.repeat(repeat!!,repeatGap)}
                .map{ it.scale(scale)}
                .map{it.verticalScale(verticalScale)}
                    // extend
                .mapIf(extendX != null){it.extendX(extendX!!)}
                .mapIf(extendY != null){it.extendY(extendY!!)}
                .mapIf(extendZ != null){it.extendZ(extendZ!!)}
                    //fuckUp
                .mapIf(fuckUp!=null){ it.fuckUp() }
                    //bpm change
                .map { it.adjustToBPM(bpm, tempBpm, c.beatStartTime)    }
                .mapIf(time) { it.time(AssetController.njsOffset())}
        }

        logger.info { p }
        logger.info { "added $struct to Difficulty"}


        /** after bpm change */
        return tempWalls
    }

    private fun List<Wall>.mapIf(b:Boolean, refactor: (Wall) -> Wall): List<Wall>{
        return if(b)
            this.map { refactor(it) }
        else
            this
    }
    private fun List<Wall>.flatMapIf(b:Boolean, refactor: (Wall) -> List<Wall>): List<Wall>{
        return if(b)
            this.flatMap { refactor(it) }
        else
            this
    }
}