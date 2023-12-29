#version 400 core

in vec3 color;
// in vec2 fragTextureCoord;

out vec4 fragColor;

uniform sampler2D textureSampler;

void main(){
    fragColor = vec4(color, 1.0);
    //fragColor = texture(textureSampler, fragTextureCoord);
}