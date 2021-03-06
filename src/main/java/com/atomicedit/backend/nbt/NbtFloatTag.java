
package com.atomicedit.backend.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 *
 * @author Justin Bonner
 */
public class NbtFloatTag extends NbtTag{
    
    private float data;
    
    public NbtFloatTag(DataInputStream input) throws IOException{
        super(NbtTypes.TAG_FLOAT, NbtTag.readUtfString(input));
        this.data = input.readFloat();
    }
    
    public NbtFloatTag(String name, float data){
        super(NbtTypes.TAG_FLOAT, name);
        this.data = data;
    }
    
    @Override
    public void write(DataOutputStream output) throws IOException{
        output.writeFloat(data);
    }
    
    public float getPayload(){
        return this.data;
    }
    
}
