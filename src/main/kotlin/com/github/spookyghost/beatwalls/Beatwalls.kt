package com.github.spookyghost.beatwalls

import assetFile.SongAsset
import assetFile.findSongAsset
import assetFile.readAssetFile
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.file
import mu.KotlinLogging

class BeatWalls: CliktCommand() {
    private val logger = KotlinLogging.logger {}
    val song by argument().file(exists = true)


    override fun run() {
        val assetFile = readAssetFile()
        val a = findSongAsset(song, assetFile)

//        val song = AssetController.currentSong()
//        val difficultyList =song.difficultyList
//        //with each difficulty, add all walls
//        for(difficulty in difficultyList) {
//            with(difficulty.component1()) {
//                //todo clears the obstacles
//                _obstacles.clear()
//
//                for (bookmark in _bookmarks) {
//                    //gets all the walls
//                    val walls = bookmark
//                        .toCommandList("bw")
//                        .flatMap { StructureManager.walls(it,this) }
//
//                    //converts the list to _obstacle and adds it
//                    _obstacles.addAll(walls.map { it.to_obstacle() })
//                }
//
//                /** TODO make this better */
//                for (bookmark in _bookmarks) {
//                    val walls = bookmark
//                        .toCommandList("bw-save")
//                    for(structureList in walls){
//                        val name = structureList.structureList.split(" ").filterNot { it.isEmpty() }[0]
//                        val duration = structureList.structureList.split(" ").filterNot { it.isEmpty() }.getOrNull(1)?.toDoubleOrNull()?:1.0
//                        val offset =  if(structureList.structureList.contains("-t")) AssetController.njsOffset() else 0.0
//                        val list = ArrayList(_obstacles
//                            .filter {
//                                it._time in (structureList.beatStartTime+offset)..(structureList.beatStartTime+duration+offset)
//                            }
//                            .map { it.toWall() }
//                        )
//                        val struct =CustomWallStructure(name,list)
//                        println(struct.wallList.size)
//                        if(struct.wallList.isNotEmpty())
//                        AssetController.customWallStructures().add(struct)
//                        AssetController.save()
//                    }
//                }
//            }
//            writeDifficulty(difficulty.toPair())
//        }
//    }
    }
}
