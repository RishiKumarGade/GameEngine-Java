#version 460
out vec4 FragColor;
in vec3 passColor;
in vec2 passTextCoord;
uniform sampler2D tex;
void main(){
    FragColor = texture(tex,passTextCoord);
}