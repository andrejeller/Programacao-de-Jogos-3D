package br.pucpr.mage;

import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Collection;

import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

public class MeshBuilder {
    private Mesh mesh;

    public MeshBuilder() {
        mesh = new Mesh();
        glBindVertexArray(mesh.getId());
    }

    // Raw buffer methods
    public MeshBuilder addBufferAttribute(String name, ArrayBuffer data) {
        mesh.addAttribute(name, data);
        return this;
    }

    public MeshBuilder addBufferAttribute(String name, int elementSize, FloatBuffer values) {
        return addBufferAttribute(name, new ArrayBuffer(elementSize, values));
    }

    // Vector3 Buffer Methods
    public MeshBuilder addVector3fAttribute(String name, Collection<Vector3f> values) {
        FloatBuffer valueBuffer = BufferUtils.createFloatBuffer(values.size() * 3);
        for (Vector3f value : values) {
            valueBuffer.put(value.x).put(value.y).put(value.z);
        }
        valueBuffer.flip();
        return addBufferAttribute(name, 3, valueBuffer);
    }

    // Vector4 buffer methods
    public MeshBuilder addVector4fAttribute(String name, Collection<Vector4f> values) {
        FloatBuffer valueBuffer = BufferUtils.createFloatBuffer(values.size() * 4);
        for (Vector4f value : values) {
            valueBuffer.put(value.x).put(value.y).put(value.z).put(value.w);
        }
        valueBuffer.flip();
        return addBufferAttribute(name, 4, valueBuffer);
    }

    // Index buffer methods
    public MeshBuilder setIndexBuffer(IndexBuffer indexBuffer) {
        mesh.setIndexBuffer(indexBuffer);
        return this;
    }

    public MeshBuilder setIndexBuffer(IntBuffer data) {
        return setIndexBuffer(new IndexBuffer(data));
    }

    public MeshBuilder setIndexBuffer(Collection<Integer> data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.size());
        for (int value : data) {
            buffer.put(value);
        }
        buffer.flip();
        return setIndexBuffer(buffer);
    }

    public MeshBuilder loadShader(String... shaders) {
        mesh.setShader(Shader.loadProgram(shaders));
        return this;
    }

    // Final mesh creation
    public Mesh create() {
        glBindVertexArray(0);
        return mesh;
    }
}
