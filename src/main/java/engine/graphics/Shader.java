package engine.graphics;

import engine.maths.Vector2f;
import engine.maths.Matrix4f;

import engine.maths.Vector3f;
import engine.utils.FileUtils;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private String vertexFile,fragmentFile;
    private int vertexId,fragmentId,programId;
    public Shader(String vertexpath,String fragmentpath){
        vertexFile = FileUtils.loadAsString(vertexpath);
        fragmentFile = FileUtils.loadAsString(fragmentpath);
    }
    public void create(){
        programId = glCreateProgram();
        vertexId = glCreateShader(GL_VERTEX_SHADER);
        fragmentId = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(vertexId,vertexFile);
        glCompileShader(vertexId);
        if(glGetShaderi(vertexId,GL_COMPILE_STATUS) == GL_FALSE){
            System.err.println("Vertex Shader : "+ glGetShaderInfoLog(vertexId));
            return;
        }

        glShaderSource(fragmentId,fragmentFile);
        glCompileShader(fragmentId);
        if(glGetShaderi(fragmentId,GL_COMPILE_STATUS) == GL_FALSE){
            System.err.println("Fragment Shader : "+ glGetShaderInfoLog(fragmentId));
            return;
        }

        glAttachShader(programId,vertexId);
        glAttachShader(programId,fragmentId);
        glLinkProgram(programId);
        if(glGetProgrami(programId,GL_LINK_STATUS)==GL_FALSE){
            System.err.println("Program linking error"+glGetProgramInfoLog(programId));
            return;
        }
        glValidateProgram(programId);
        if(glGetProgrami(programId,GL_VALIDATE_STATUS)==GL_FALSE){
            System.err.println("Program Validation error"+glGetProgramInfoLog(programId));
            return;
        }
    }

    public int getUniformLocation(String name){
        return glGetUniformLocation(programId,name);
    }
    public void setUniform(String name , boolean value){
        glUniform1i(getUniformLocation(name),value? 1 : 0);
    }
    public void setUniform(String name , float value){
        glUniform1f(getUniformLocation(name),value);
    }
    public void setUniform(String name , int value){
        glUniform1i(getUniformLocation(name),value);

    }
    public void setUniform(String name , Vector2f value){
    glUniform2f(getUniformLocation(name),value.getX(),value.getY());
    }
    public void setUniform(String name , Vector3f value){
        glUniform3f(getUniformLocation(name),value.getX(),value.getY(),value.getZ());
    }
    public void setUniform(String name , Matrix4f value){
        FloatBuffer matrix = MemoryUtil.memAllocFloat(Matrix4f.SIZE*Matrix4f.SIZE);
        matrix.put(value.getAll()).flip();
        glUniformMatrix4fv(getUniformLocation(name),true,matrix);
    }

    public void bind(){
        glUseProgram(programId);
    }
    public void unbind(){
        glUseProgram(0);
    }
    public void destroy(){
        glDetachShader(programId,vertexId);
        glDetachShader(programId,fragmentId);
        glDeleteShader(vertexId);
        glDeleteShader(fragmentId);
        glDeleteProgram(programId);
    }
}
