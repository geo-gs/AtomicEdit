
package com.atomicedit.volumes;

import com.atomicedit.backend.BlockCoord;
import com.atomicedit.backend.ChunkSectionCoord;
import com.atomicedit.backend.chunk.ChunkCoord;
import com.atomicedit.backend.utils.GeneralUtils;
import com.atomicedit.logging.Logger;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;

/**
 * Classes implementing this interface describe a selection of block coordinates.
 * @author Justin Bonner
 */
public class Volume {
    
    private BitSet includedSet;
    private Box enclosingBox;
    private Collection<ChunkCoord> containedChunkCoords;
    private Collection<ChunkSectionCoord> containedChunkSectionCoords;
    
    public Volume(Box enclosingBox, BitSet includedSet){
        this.enclosingBox = enclosingBox;
        this.includedSet = includedSet;
        if(includedSet.size() != enclosingBox.getNumBlocksContained()){
            String message = "Tried to create Volume of inconsistent size";
            Logger.error(message);
            throw new IllegalArgumentException(message);
        }
    }
    
    public int getNumBlocksContained(){
        return includedSet.cardinality();
    }
    
    /**
     * The included set is a mapping from a block's index (ordered YZX) in the enclosing Box, to a boolean
     * indicating if this block is inside this Volume.
     * @return 
     */
    public BitSet getIncludedSet(){
        return this.includedSet;
    }
    
    public Box getEnclosingBox(){
        return this.enclosingBox;
    }
    
    
    public boolean containsXYZ(int x, int y, int z){
        int index = GeneralUtils.getIndexYZX(x, y, z, enclosingBox.getXLength(), enclosingBox.getZLength());
        if(!enclosingBox.isInsideBox(x, y, z)){
            return false;
        }
        return includedSet.get(index);
    }
    
    /**
     * Get a collection of the coordinates of the chunks that may be in this volume based off the enclosing box.
     * This volume will not contain a chunk whose coordinate is not returned by this method. However a chunk returned by this method
     * may not be in the volume (but probably is).
     * This method does not check if all the blocks that this volume's enclosing box and a chunk share are excluded from the volume by the included set.
     * This method requires a position in world space to be passed in as a Volume has no location.
     * @param smallestCoord the smallest point in the volume
     * @return 
     */
    public Collection<ChunkCoord> getContainedChunkCoords(BlockCoord smallestCoord){
        BlockCoord largestCoord = new BlockCoord(smallestCoord.x + enclosingBox.getXLength(), smallestCoord.y + enclosingBox.getYLength(), smallestCoord.z + enclosingBox.getZLength());
        this.containedChunkCoords = new ArrayList<>();
        for(int x = smallestCoord.getChunkCoord().x; x <= largestCoord.getChunkCoord().x; x++){
            for(int z = smallestCoord.getChunkCoord().z; z <= largestCoord.getChunkCoord().z; z++){
                this.containedChunkCoords.add(new ChunkCoord(x, z));
            }
        }
        return new ArrayList<>(this.containedChunkCoords); //dont let the internal array leak, dont want it modified accidentally
    }
    
    /**
     * Get the chunk section coords contained by this volume if this volume is located so that smallestCoord
     * is the smallest point in the volume.
     * @param smallestCoord
     * @return chunk section coords in 
     */
    public Collection<ChunkSectionCoord> getContainedChunkSectionCoords(BlockCoord smallestCoord){
        BlockCoord largestCoord = new BlockCoord(smallestCoord.x + enclosingBox.getXLength(), smallestCoord.y + enclosingBox.getYLength(), smallestCoord.z + enclosingBox.getZLength());
        this.containedChunkSectionCoords = new ArrayList<>();
        for(int x = smallestCoord.getChunkCoord().x; x <= largestCoord.getChunkCoord().x; x++){
            for(int z = smallestCoord.getChunkCoord().z; z <= largestCoord.getChunkCoord().z; z++){
                for(int y = smallestCoord.getSubChunkIndex(); y < largestCoord.getSubChunkIndex(); y++){
                    this.containedChunkSectionCoords.add(new ChunkSectionCoord(x, y, z));
                }
            }
        }
        return new ArrayList<>(this.containedChunkSectionCoords); //dont let the internal array leak, dont want it modified accidentally
    }
    
    
    /**
     * Do some action for each x, y, z in this volume.
     * The x, y, and z are relative and can be anything from 0, 0, 0 to xLength - 1, yLength - 1, zLength - 1.
     * @param action 
     */
    public void doForXyz(ActionForXYZ action){
        int index = 0; //start at first block in volume
        int x;
        int y;
        int z;
        int xLen = this.enclosingBox.getXLength();
        int zLen = this.enclosingBox.getZLength();
        for(int i = 0; i < this.getNumBlocksContained(); i++){
            index = includedSet.nextSetBit(index);
            x = GeneralUtils.getXFromIndexYZX(index, xLen);
            y = GeneralUtils.getYFromIndexYZX(index, xLen, zLen);
            z = GeneralUtils.getZFromIndexYZX(index, xLen, zLen);
            action.action(x, y, z);
        }
    }
    
    
}
