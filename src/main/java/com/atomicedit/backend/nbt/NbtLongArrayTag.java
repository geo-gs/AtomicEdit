
package com.atomicedit.backend.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author Justin Bonner
 */
public class NbtLongArrayTag extends NbtTag{
    
    private long[] data;
    private int dataSize;
    
    public NbtLongArrayTag(DataInputStream input) throws IOException{
        super(NbtTypes.TAG_LONG_ARRAY, NbtTag.readUtfString(input));
        this.dataSize = input.readInt();
        this.data = new long[dataSize];
        for(int i = 0; i < dataSize; i++){
            data[i] = input.readInt();
        }
    }
    
    public NbtLongArrayTag(String name, long[] data){
        super(NbtTypes.TAG_LONG_ARRAY, name);
        this.data = data;
        this.dataSize = data.length;
    }
    
    @Override
    public void write(DataOutputStream output) throws IOException{
        output.writeInt(dataSize);
        for(int i = 0; i < dataSize; i++){
            output.writeLong(data[i]);
        }
    }
    
    public long[] getPayload(){
        return this.data;
    }
    
    public int getPayloadSize(){
        return this.dataSize;
    }
    
}
