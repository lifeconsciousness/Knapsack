#version 400 core

in vec2 fragTextureCoord;
in vec3 fragPosition;

out vec4 fragColor;

uniform sampler2D textureSampler;

void main() {
    // Set the outline width (you can adjust this value)
    float outlineWidth = 0.015;

    // Calculate the distance to the nearest edge using normalized device coordinates
    float distToEdge = min(fragTextureCoord.x, min(1.0 - fragTextureCoord.x, min(fragTextureCoord.y, 1.0 - fragTextureCoord.y)));

    // Use the distance to the nearest edge to determine if the fragment is inside or outside
    if (distToEdge > outlineWidth) {
        // Fragments outside the element will have a different color (outline color)
        fragColor = vec4(1.0, 1.0, 1.0, 1.0); // Set outline color to white
    } else {
        // Fragments inside the element will use the texture color
        fragColor = texture(textureSampler, fragTextureCoord);
    }
}
