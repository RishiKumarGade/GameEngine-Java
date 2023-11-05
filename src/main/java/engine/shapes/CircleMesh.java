package engine.shapes;

import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.graphics.Vertex;
import engine.maths.Vector3f;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CircleMesh {

    public int lon,lat;
    public int r = 100;
    public Mesh mesh;
    ArrayList<Vertex> vertices;
    public Mesh generate(){
        for(int i =0 ;i<=100;i++){
            lon = map_value(i,0,100,3.14f,3.14f);
            for(int j =0 ;j<=100;j++){
                lat = map_value(j,0,100,3.14f,3.14f);
                float x = (float) (r*Math.sin(lon)*Math.cos(lat));
                float y = (float) (r*Math.sin(lon)*Math.sin(lat));
                float z = (float) (r*Math.cos(lon));
                vertices.add(new Vertex(new Vector3f(x,y,z), new Vector3f(x,y,z)) );
            }
        }

        return null;
    }
    public int map_value(int value, int start_range,int end_range , float new_start_range, float new_end_range){
        int percentage = (value - start_range) / (end_range - start_range);

        return (int) (new_start_range + percentage * (new_end_range - new_start_range));
    }
}
