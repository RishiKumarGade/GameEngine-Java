#version 460
in vec3 position;
in vec3 color;
in vec2 textCoord;

out vec3 passColor;
out vec2 passTextCoord;

uniform mat4 model;
uniform mat4 projection;
uniform mat4 view;
void main(){
    gl_Position = projection * view * model * vec4(position,1.0f)  ;
    passColor=color;
    passTextCoord=textCoord;
}