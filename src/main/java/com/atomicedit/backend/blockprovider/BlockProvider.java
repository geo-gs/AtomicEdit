
package com.atomicedit.backend.blockprovider;

import com.atomicedit.volumes.Volume;

/**
 *
 * @author Justin Bonner
 */
public interface BlockProvider {
    
    public Volume getVolume();
    
    public short getBlockAt(int x, int y, int z);
    
    public void doForBlock(ActionForBlock action);
    
}
