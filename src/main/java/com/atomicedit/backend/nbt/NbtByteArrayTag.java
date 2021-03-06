
package com.atomicedit.backend.nbt;

import com.atomicedit.logging.Logger;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author Justin Bonner
 */
public class NbtByteArrayTag extends NbtTag{
    
    private byte[] data;
    private int dataSize;
    
    public NbtByteArrayTag(DataInputStream input) throws IOException{
        super(NbtTypes.TAG_BYTE_ARRAY, NbtTag.readUtfString(input));
        this.dataSize = input.readInt();
        this.data = new byte[dataSize];
        int result = input.read(data);
        if(result != dataSize){
            Logger.warning("Could not read full length of NBT byte array");
            throw new IOException();
        }
    }
    
    public NbtByteArrayTag(String name, byte[] data){
        super(NbtTypes.TAG_BYTE_ARRAY, name);
        this.data = data;
        this.dataSize = data.length;
    }
    
    @Override
    public void write(DataOutputStream output) throws IOException{
        output.write(dataSize);
        output.write(data);
    }
    
    public byte[] getPayload(){
        return this.data;
    }
    
    public int getPayloadSize(){
        return this.dataSize;
    }
    
}
