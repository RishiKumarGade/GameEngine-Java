#version 460
out vec4 FragColor;
in vec3 passColor;

void main(){
    FragColor = vec4(passColor,1.0);
}