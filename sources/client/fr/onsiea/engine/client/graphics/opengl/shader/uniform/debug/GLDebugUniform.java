/**
* Copyright 2021 Onsiea All rights reserved.<br><br>
*
* This file is part of Onsiea Engine project. (https://github.com/Onsiea/OnsieaEngine)<br><br>
*
* Onsiea Engine is [licensed] (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE) under the terms of the "GNU General Public Lesser License v3.0" (GPL-3.0).
* https://github.com/Onsiea/OnsieaEngine/wiki/License#license-and-copyright<br><br>
*
* Onsiea Engine is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3.0 of the License, or
* (at your option) any later version.<br><br>
*
* Onsiea Engine is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Lesser General Public License for more details.<br><br>
*
* You should have received a copy of the GNU Lesser General Public License
* along with Onsiea Engine.  If not, see <https://www.gnu.org/licenses/>.<br><br>
*
* Neither the name "Onsiea", "Onsiea Engine", or any derivative name or the names of its authors / contributors may be used to endorse or promote products derived from this software and even less to name another project or other work without clear and precise permissions written in advance.<br><br>
*
* @Author : Seynax (https://github.com/seynax)<br>
* @Organization : Onsiea Studio (https://github.com/Onsiea)
*/
package fr.onsiea.engine.client.graphics.opengl.shader.uniform.debug;

import java.nio.FloatBuffer;

import org.joml.Matrix2f;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.joml.Vector4d;
import org.joml.Vector4f;
import org.joml.Vector4i;

import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.opengl.shader.uniform.GLUniform;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderUniform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 *
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLDebugUniform extends GLUniform implements IShaderUniform
{
	private IShaderProgram	parent;
	private String			name;

	public GLDebugUniform(GLShaderProgram parentIn, String nameIn)
	{
		super(parentIn, nameIn);

		this.parent	= parentIn;
		this.name	= nameIn;
	}

	@Override
	public IShaderProgram load(boolean valueIn)
	{
		super.load(valueIn);

		System.err.println("[DEBUG-Shader] Loading boolean \"" + valueIn + "\" into \"" + this.name
				+ "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(boolean... valuesIn)
	{
		super.load(valuesIn);

		System.err.println("[DEBUG-Shader] Loading boolean... of size \"" + valuesIn.length + "\" into \"" + this.name
				+ "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load2B(boolean... valuesIn)
	{
		super.load2B(valuesIn);

		System.err.println("[DEBUG-Shader] Loading 2B boolean... of size \"" + valuesIn.length + "\" into \""
				+ this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load3B(boolean... valuesIn)
	{
		super.load3B(valuesIn);

		System.err.println("[DEBUG-Shader] Loading 3B boolean... of size \"" + valuesIn.length + "\" into \""
				+ this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load4B(boolean... valuesIn)
	{
		super.load4B(valuesIn);

		System.err.println("[DEBUG-Shader] Loading 4B boolean... of size \"" + valuesIn.length + "\" into \""
				+ this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(byte valueIn)
	{
		super.load(valueIn);

		System.err.println("[DEBUG-Shader] Loading byte \"" + valueIn + "\" into \"" + this.name
				+ "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(byte... valuesIn)
	{
		super.load(valuesIn);

		System.err.println("[DEBUG-Shader] Loading byte... into float array of size \"" + valuesIn.length + "\" into \""
				+ this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load2B(byte... valuesIn)
	{
		super.load2B(valuesIn);

		System.err.println("[DEBUG-Shader] Loading 2b byte... into float array of size \"" + valuesIn.length
				+ "\" into \"" + this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load3B(byte... valuesIn)
	{
		super.load3B(valuesIn);

		System.err.println("[DEBUG-Shader] Loading 3b byte... into float array of size \"" + valuesIn.length
				+ "\" into \"" + this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load4B(byte... valuesIn)
	{
		super.load4B(valuesIn);

		System.err.println("[DEBUG-Shader] Loading 4b byte... into float array of size \"" + valuesIn.length
				+ "\" into \"" + this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(short valueIn)
	{
		super.load(valueIn);

		System.err.println("[DEBUG-Shader] Loading short \"" + valueIn + "\" into \"" + this.name
				+ "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(short... valuesIn)
	{
		super.load(valuesIn);

		System.err.println("[DEBUG-Shader] Loading short... into float array of size \"" + valuesIn.length
				+ "\" into \"" + this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load2S(short... valuesIn)
	{
		super.load2S(valuesIn);

		System.err.println("[DEBUG-Shader] Loading 2s short... into float array of size \"" + valuesIn.length
				+ "\" into \"" + this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load3S(short... valuesIn)
	{
		super.load3S(valuesIn);

		System.err.println("[DEBUG-Shader] Loading 3s short... into float array of size \"" + valuesIn.length
				+ "\" into \"" + this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load4S(short... valuesIn)
	{
		super.load4S(valuesIn);

		System.err.println("[DEBUG-Shader] Loading 4s short... into float array of size \"" + valuesIn.length
				+ "\" into \"" + this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(int valueIn)
	{
		super.load(valueIn);

		System.err.println("[DEBUG-Shader] Loading int \"" + valueIn + "\" into \"" + this.name
				+ "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(int... valuesIn)
	{
		super.load(valuesIn);

		System.err.println("[DEBUG-Shader] Loading int... into float array of size \"" + valuesIn.length + "\" into \""
				+ this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(float valueIn)
	{
		super.load(valueIn);

		System.err.println("[DEBUG-Shader] Loading float \"" + valueIn + "\" into \"" + this.name
				+ "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(float... valuesIn)
	{
		super.load(valuesIn);

		System.err.println("[DEBUG-Shader] Loading float... into float array of size \"" + valuesIn.length
				+ "\" into \"" + this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(float xIn, float yIn)
	{
		super.load(xIn, yIn);

		System.err.println("[DEBUG-Shader] Loading vector2f (float xIn, float yIn) \"" + xIn + ", " + yIn + "\" into \""
				+ this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(Vector2i valueIn)
	{
		super.load(valueIn);

		System.err.println("[DEBUG-Shader] Loading Vector2i \"" + valueIn + "\" into \"" + this.name
				+ "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(Vector2f valueIn)
	{
		super.load(valueIn);

		System.err.println("[DEBUG-Shader] Loading vector2f \"" + valueIn + "\" into \"" + this.name
				+ "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(Vector2d valueIn)
	{
		super.load(valueIn);

		System.err.println("[DEBUG-Shader] Loading Vector2d \"" + valueIn + "\" into \"" + this.name
				+ "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load2I(int... valuesIn)
	{
		super.load2I(valuesIn);

		System.err.println("[DEBUG-Shader] Loading 2i int... into float array of size \"" + valuesIn.length
				+ "\" into \"" + this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load2F(float... valuesIn)
	{
		super.load2F(valuesIn);

		System.err.println("[DEBUG-Shader] Loading 2f float... into float array of size \"" + valuesIn.length
				+ "\" into \"" + this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(float xIn, float yIn, float zIn)
	{
		super.load(xIn, yIn, zIn);

		System.err.println(
				"[DEBUG-Shader] Loading  vector3f (float xIn, float yIn, float zIn) \"" + xIn + ", " + yIn + ", " + zIn
						+ "\" into \"" + this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(Vector3i valueIn)
	{
		super.load(valueIn);

		System.err.println("[DEBUG-Shader] Loading Vector3i \"" + valueIn + "\" into \"" + this.name
				+ "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(Vector3f valueIn)
	{
		super.load(valueIn);

		System.err.println("[DEBUG-Shader] Loading vector3f \"" + valueIn + "\" into \"" + this.name
				+ "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(Vector3d valueIn)
	{
		super.load(valueIn);

		System.err.println("[DEBUG-Shader] Loading Vector3d \"" + valueIn + "\" into \"" + this.name
				+ "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load3I(int... valuesIn)
	{
		super.load3I(valuesIn);

		System.err.println("[DEBUG-Shader] Loading 3i int... into float array of size \"" + valuesIn.length
				+ "\" into \"" + this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load3F(float... valuesIn)
	{
		super.load3F(valuesIn);

		System.err.println("[DEBUG-Shader] Loading 3f float... into float array of size \"" + valuesIn.length
				+ "\" into \"" + this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(float xIn, float yIn, float zIn, float wIn)
	{
		super.load(xIn, yIn, zIn, wIn);

		System.err.println("[DEBUG-Shader] Loading  vector4f (float xIn, float yIn, float zIn, float wIn) \"" + xIn
				+ ", " + yIn + ", " + zIn + "\" into \"" + this.name + "\" uniform (general) of \"" + this.parent.name()
				+ "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(Vector4i valueIn)
	{
		super.load(valueIn);

		System.err.println("[DEBUG-Shader] Loading Vector4i \"" + valueIn + "\" into \"" + this.name
				+ "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(Vector4f valueIn)
	{
		super.load(valueIn);

		System.err.println("[DEBUG-Shader] Loading vector4f \"" + valueIn + "\" into \"" + this.name
				+ "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load(Vector4d valueIn)
	{
		super.load(valueIn);

		System.err.println("[DEBUG-Shader] Loading Vector4d \"" + valueIn + "\" into \"" + this.name
				+ "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load4I(int... valuesIn)
	{
		super.load4I(valuesIn);

		System.err.println("[DEBUG-Shader] Loading 4i int... into float array of size \"" + valuesIn.length
				+ "\" into \"" + this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram load4F(float... valuesIn)
	{
		super.load4F(valuesIn);

		System.err.println("[DEBUG-Shader] Loading 4f float... into float array of size \"" + valuesIn.length
				+ "\" into \"" + this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}
	// Matrix2f

	@Override
	public IShaderProgram load(Matrix2f valueIn)
	{
		super.load(valueIn);

		System.err.println("[DEBUG-Shader] Loading matrix2f \"" + valueIn + "\" into \"" + this.name
				+ "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram loadTranspose(Matrix2f valueIn)
	{
		super.loadTranspose(valueIn);

		System.err.println("[DEBUG-Shader] Loading matrix2f \"" + valueIn + "\" into \"" + this.name
				+ "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram loadMat2(FloatBuffer bufferIn)
	{
		super.loadMat2(bufferIn);

		System.err.println("[DEBUG-Shader] Loading matrix2f into floatBuffer of size \"" + bufferIn.capacity()
				+ "\" into \"" + this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram loadMat2Transpose(FloatBuffer bufferIn)
	{
		super.loadMat2Transpose(bufferIn);

		System.err
				.println("[DEBUG-Shader] Loading transposed matrix2f into floatBuffer of size \"" + bufferIn.capacity()
						+ "\" into \"" + this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram loadMat2(float[] arrayIn)
	{
		super.loadMat2(arrayIn);

		System.err.println("[DEBUG-Shader] Loading matrix2f into float array of size \"" + arrayIn.length + "\" into \""
				+ this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram loadMat2Transpose(float[] arrayIn)
	{
		super.loadMat2Transpose(arrayIn);

		System.err.println("[DEBUG-Shader] Loading transposed matrix2f into float array of size \"" + arrayIn.length
				+ "\" into \"" + this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	// Matrix3f

	@Override
	public IShaderProgram load(Matrix3f valueIn)
	{
		super.load(valueIn);

		System.err.println("[DEBUG-Shader] Loading matrix3f \"" + valueIn + "\" into \"" + this.name
				+ "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram loadTranspose(Matrix3f valueIn)
	{
		super.loadTranspose(valueIn);

		System.err.println("[DEBUG-Shader] Loading transposed matrix3f \"" + valueIn + "\" into \"" + this.name
				+ "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram loadMat3(FloatBuffer bufferIn)
	{
		super.loadMat3(bufferIn);

		System.err.println("[DEBUG-Shader] Loading matrix3f into floatBuffer of size \"" + bufferIn.capacity()
				+ "\" into \"" + this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram loadMat3Transpose(FloatBuffer bufferIn)
	{
		super.loadMat3Transpose(bufferIn);

		System.err
				.println("[DEBUG-Shader] Loading transposed matrix3f into floatBuffer of size \"" + bufferIn.capacity()
						+ "\" into \"" + this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram loadMat3(float[] arrayIn)
	{
		super.loadMat3(arrayIn);

		System.err.println("[DEBUG-Shader] Loading matrix3f into float array of size \"" + arrayIn.length + "\" into \""
				+ this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram loadMat3Transpose(float[] arrayIn)
	{
		super.loadMat3Transpose(arrayIn);

		System.err.println("[DEBUG-Shader] Loading transposed matrix3f into float array of size \"" + arrayIn.length
				+ "\" into \"" + this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	// Matrix4f

	@Override
	public IShaderProgram load(Matrix4f valueIn)
	{
		super.load(valueIn);

		System.err.println("[DEBUG-Shader] Loading matrix4f \"" + valueIn + "\" into \"" + this.name
				+ "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram loadTranspose(Matrix4f valueIn)
	{
		super.loadTranspose(valueIn);

		System.err.println("[DEBUG-Shader] Loading transposed matrix4f \"" + valueIn + "\" into \"" + this.name
				+ "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram loadMat4(FloatBuffer bufferIn)
	{
		super.loadMat4(bufferIn);

		System.err.println("[DEBUG-Shader] Loading matrix4f into floatBuffer of size \"" + bufferIn.capacity()
				+ "\" into \"" + this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram loadMat4Transpose(FloatBuffer bufferIn)
	{
		super.loadMat4Transpose(bufferIn);

		System.err
				.println("[DEBUG-Shader] Loading transposed matrix4f into floatBuffer of size \"" + bufferIn.capacity()
						+ "\" into \"" + this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram loadMat4(float[] arrayIn)
	{
		super.loadMat4(arrayIn);

		System.err.println("[DEBUG-Shader] Loading matrix4f into float array of size \"" + arrayIn.length + "\" into \""
				+ this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}

	@Override
	public IShaderProgram loadMat4Transpose(float[] arrayIn)
	{
		super.loadMat4Transpose(arrayIn);

		System.err.println("[DEBUG-Shader] Loading transposed matrix4f into float array of size \"" + arrayIn.length
				+ "\" into \"" + this.name + "\" uniform (general) of \"" + this.parent.name() + "\" shader !");

		return this.parent();
	}
}