#version 400 core

in vec3 position;
in vec2 textureCoord;

out vec2 fragTextureCoord;
// out vec3 color;

uniform mat4 transformationMatrix;

void main(){
    gl_Position = transformationMatrix * vec4(position, 1.0);
    // color = vec3(position.x + 0.25, 0.17, position.y + 0.25);
    fragTextureCoord = textureCoord;
}