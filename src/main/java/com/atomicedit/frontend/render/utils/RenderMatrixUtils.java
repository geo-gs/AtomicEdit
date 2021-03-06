
package com.atomicedit.frontend.render.utils;

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 *
 * @author Justin Bonner
 */
public class RenderMatrixUtils {
    
    
    public static Matrix4f createModelMatrix(Vector3f pos, Vector3f rot){
        Matrix4f model = new Matrix4f(); //identity matrix
        model.rotate(-(float)Math.toRadians(rot.x), 1, 0, 0);
        model.rotate(-(float)Math.toRadians(rot.y), 0, 1, 0);
        model.rotate(-(float)Math.toRadians(rot.z), 0, 0, 1);
        model.translate(pos);
        return model;
    }
    
    
    public static Matrix4f createViewMatrix(Vector3f pos, Vector3f rot){
        Vector3f negPos = new Vector3f();
        Vector3f negRot = new Vector3f();
        pos.negate(negPos);
        rot.negate(negRot);
        return createModelMatrix(negPos, negRot);
    }
    
    
    public static Matrix4f createProjectionMatrix(float fov, float aspectRatio, float nearClip, float farClip){
        Matrix4f proj = new Matrix4f();
        return proj.perspective(fov, aspectRatio, nearClip, farClip);
    }
    
    
    
    
}
