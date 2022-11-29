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
package fr.onsiea.engine.client.graphics.opengl.shader.uniform;

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
import org.lwjgl.opengl.GL20;

import fr.onsiea.engine.client.graphics.opengl.shader.GLShaderProgram;
import fr.onsiea.engine.client.graphics.shader.IShaderProgram;
import fr.onsiea.engine.client.graphics.shader.uniform.IShaderUniform;
import fr.onsiea.engine.utils.BufferHelper;
import fr.onsiea.engine.utils.Primitive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seynax
 *
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class GLUniform implements IShaderUniform
{
	public final static void load(int locationIn, boolean valueIn)
	{
		GL20.glUniform1i(locationIn, valueIn ? 1 : 0);
	}

	public final static void load(int locationIn, boolean... valuesIn)
	{
		GL20.glUniform1iv(locationIn, Primitive.toIntArray(valuesIn));
	}

	public final static void load2B(int locationIn, boolean... valuesIn)
	{
		GL20.glUniform2iv(locationIn, Primitive.toIntArray(valuesIn));
	}

	public final static void load3B(int locationIn, boolean... valuesIn)
	{
		GL20.glUniform3iv(locationIn, Primitive.toIntArray(valuesIn));
	}

	public final static void load4B(int locationIn, boolean... valuesIn)
	{
		GL20.glUniform4iv(locationIn, Primitive.toIntArray(valuesIn));
	}

	public final static void load(int locationIn, byte valueIn)
	{
		GL20.glUniform1i(locationIn, valueIn);
	}

	public final static void load(int locationIn, byte... valuesIn)
	{
		GL20.glUniform1iv(locationIn, Primitive.toIntArray(valuesIn));
	}

	public final static void load2B(int locationIn, byte... valuesIn)
	{
		GL20.glUniform2iv(locationIn, Primitive.toIntArray(valuesIn));
	}

	public final static void load3B(int locationIn, byte... valuesIn)
	{
		GL20.glUniform3iv(locationIn, Primitive.toIntArray(valuesIn));
	}

	public final static void load4B(int locationIn, byte... valuesIn)
	{
		GL20.glUniform4iv(locationIn, Primitive.toIntArray(valuesIn));
	}

	public final static void load(int locationIn, short valueIn)
	{
		GL20.glUniform1i(locationIn, valueIn);
	}

	public final static void load(int locationIn, short... valuesIn)
	{
		GL20.glUniform1iv(locationIn, Primitive.toIntArray(valuesIn));
	}

	public final static void load2S(int locationIn, short... valuesIn)
	{
		GL20.glUniform2iv(locationIn, Primitive.toIntArray(valuesIn));
	}

	public final static void load3S(int locationIn, short... valuesIn)
	{
		GL20.glUniform3iv(locationIn, Primitive.toIntArray(valuesIn));
	}

	public final static void load4S(int locationIn, short... valuesIn)
	{
		GL20.glUniform4iv(locationIn, Primitive.toIntArray(valuesIn));
	}

	public final static void load(int locationIn, int valueIn)
	{
		GL20.glUniform1i(locationIn, valueIn);
	}

	public final static void load(int locationIn, int... valuesIn)
	{
		GL20.glUniform1iv(locationIn, valuesIn);
	}

	public final static void load(int locationIn, float valueIn)
	{
		GL20.glUniform1f(locationIn, valueIn);
	}

	public final static void load(int locationIn, float... valuesIn)
	{
		GL20.glUniform1fv(locationIn, valuesIn);
	}

	public final static void load(int locationIn, float xIn, float yIn)
	{
		GL20.glUniform2f(locationIn, xIn, yIn);
	}

	public final static void load(int locationIn, Vector2i valueIn)
	{
		GL20.glUniform2i(locationIn, valueIn.x(), valueIn.y());
	}

	public final static void load(int locationIn, Vector2f valueIn)
	{
		GL20.glUniform2f(locationIn, valueIn.x(), valueIn.y());
	}

	public final static void load(int locationIn, Vector2d valueIn)
	{
		GL20.glUniform2f(locationIn, (float) valueIn.x(), (float) valueIn.y());
	}

	public final static void load2I(int locationIn, int... valuesIn)
	{
		GL20.glUniform2iv(locationIn, valuesIn);
	}

	public final static void load2F(int locationIn, float... valuesIn)
	{
		GL20.glUniform2fv(locationIn, valuesIn);
	}

	public final static void load(int locationIn, float xIn, float yIn, float zIn)
	{
		GL20.glUniform3f(locationIn, xIn, yIn, zIn);
	}

	public final static void load(int locationIn, Vector3i valueIn)
	{
		GL20.glUniform3i(locationIn, valueIn.x(), valueIn.y(), valueIn.z());
	}

	public final static void load(int locationIn, Vector3f valueIn)
	{
		GL20.glUniform3f(locationIn, valueIn.x(), valueIn.y(), valueIn.z());
	}

	public final static void load(int locationIn, Vector3d valueIn)
	{
		GL20.glUniform3f(locationIn, (float) valueIn.x(), (float) valueIn.y(), (float) valueIn.z());
	}

	public final static void load3I(int locationIn, int... valuesIn)
	{
		GL20.glUniform3iv(locationIn, valuesIn);
	}

	public final static void load3F(int locationIn, float... valuesIn)
	{
		GL20.glUniform3fv(locationIn, valuesIn);
	}

	public final static void load(int locationIn, float xIn, float yIn, float zIn, float wIn)
	{
		GL20.glUniform4f(locationIn, xIn, yIn, zIn, wIn);
	}

	public final static void load(int locationIn, Vector4i valueIn)
	{
		GL20.glUniform4f(locationIn, valueIn.x(), valueIn.y(), valueIn.z(), valueIn.w());
	}

	public final static void load(int locationIn, Vector4f valueIn)
	{
		GL20.glUniform4f(locationIn, valueIn.x(), valueIn.y(), valueIn.z(), valueIn.w());
	}

	public final static void load(int locationIn, Vector4d valueIn)
	{
		GL20.glUniform4f(locationIn, (float) valueIn.x(), (float) valueIn.y(), (float) valueIn.z(),
				(float) valueIn.w());
	}

	public final static void load4I(int locationIn, int... valuesIn)
	{
		GL20.glUniform4iv(locationIn, valuesIn);
	}

	public final static void load4F(int locationIn, float... valuesIn)
	{
		GL20.glUniform4fv(locationIn, valuesIn);
	}

	// Matrix2f

	public final static void load(int locationIn, Matrix2f valueIn)
	{
		GL20.glUniformMatrix2fv(locationIn, false, BufferHelper.convertToBuffer(valueIn));
	}

	public final static void loadTranspose(int locationIn, Matrix2f valueIn)
	{
		GL20.glUniformMatrix2fv(locationIn, true, BufferHelper.convertToBuffer(valueIn));
	}

	public final static void loadMat2(int locationIn, FloatBuffer bufferIn)
	{
		GL20.glUniformMatrix2fv(locationIn, false, bufferIn);
	}

	public final static void loadMat2Transpose(int locationIn, FloatBuffer bufferIn)
	{
		GL20.glUniformMatrix2fv(locationIn, true, bufferIn);
	}

	public final static void loadMat2(int locationIn, float[] arrayIn)
	{
		GL20.glUniformMatrix2fv(locationIn, false, arrayIn);
	}

	public final static void loadMat2Transpose(int locationIn, float[] arrayIn)
	{
		GL20.glUniformMatrix2fv(locationIn, true, arrayIn);
	}

	// Matrix3f

	public final static void load(int locationIn, Matrix3f valueIn)
	{
		GL20.glUniformMatrix3fv(locationIn, false, BufferHelper.convertToBuffer(valueIn));
	}

	public final static void loadTranspose(int locationIn, Matrix3f valueIn)
	{
		GL20.glUniformMatrix3fv(locationIn, true, BufferHelper.convertToBuffer(valueIn));
	}

	public final static void loadMat3(int locationIn, FloatBuffer bufferIn)
	{
		GL20.glUniformMatrix3fv(locationIn, false, bufferIn);
	}

	public final static void loadMat3Transpose(int locationIn, FloatBuffer bufferIn)
	{
		GL20.glUniformMatrix3fv(locationIn, true, bufferIn);
	}

	public final static void loadMat3(int locationIn, float[] arrayIn)
	{
		GL20.glUniformMatrix3fv(locationIn, false, arrayIn);
	}

	public final static void loadMat3Transpose(int locationIn, float[] arrayIn)
	{
		GL20.glUniformMatrix3fv(locationIn, true, arrayIn);
	}

	// Matrix4f

	public final static void load(int locationIn, Matrix4f valueIn)
	{
		GL20.glUniformMatrix4fv(locationIn, false, BufferHelper.convertToBuffer(valueIn));
	}

	public final static void loadTranspose(int locationIn, Matrix4f valueIn)
	{
		GL20.glUniformMatrix4fv(locationIn, true, BufferHelper.convertToBuffer(valueIn));
	}

	public final static void loadMat4(int locationIn, FloatBuffer bufferIn)
	{
		GL20.glUniformMatrix4fv(locationIn, false, bufferIn);
	}

	public final static void loadMat4Transpose(int locationIn, FloatBuffer bufferIn)
	{
		GL20.glUniformMatrix4fv(locationIn, true, bufferIn);
	}

	public final static void loadMat4(int locationIn, float[] arrayIn)
	{
		GL20.glUniformMatrix4fv(locationIn, false, arrayIn);
	}

	public final static void loadMat4Transpose(int locationIn, float[] arrayIn)
	{
		GL20.glUniformMatrix4fv(locationIn, true, arrayIn);
	}

	private IShaderProgram	parent;
	private int				location;

	public GLUniform(GLShaderProgram parentIn, String nameIn)
	{
		this.parent(parentIn);

		this.location(parentIn.uniformLocation(nameIn));
	}

	public IShaderProgram load(boolean valueIn)
	{
		GL20.glUniform1i(this.location(), valueIn ? 1 : 0);

		return this.parent();
	}

	public IShaderProgram load(boolean... valuesIn)
	{
		GL20.glUniform1iv(this.location(), Primitive.toIntArray(valuesIn));

		return this.parent();
	}

	public IShaderProgram load2B(boolean... valuesIn)
	{
		GL20.glUniform2iv(this.location(), Primitive.toIntArray(valuesIn));

		return this.parent();
	}

	public IShaderProgram load3B(boolean... valuesIn)
	{
		GL20.glUniform3iv(this.location(), Primitive.toIntArray(valuesIn));

		return this.parent();
	}

	public IShaderProgram load4B(boolean... valuesIn)
	{
		GL20.glUniform4iv(this.location(), Primitive.toIntArray(valuesIn));

		return this.parent();
	}

	public IShaderProgram load(byte valueIn)
	{
		GL20.glUniform1i(this.location(), valueIn);

		return this.parent();
	}

	public IShaderProgram load(byte... valuesIn)
	{
		GL20.glUniform1iv(this.location(), Primitive.toIntArray(valuesIn));

		return this.parent();
	}

	public IShaderProgram load2B(byte... valuesIn)
	{
		GL20.glUniform2iv(this.location(), Primitive.toIntArray(valuesIn));

		return this.parent();
	}

	public IShaderProgram load3B(byte... valuesIn)
	{
		GL20.glUniform3iv(this.location(), Primitive.toIntArray(valuesIn));

		return this.parent();
	}

	public IShaderProgram load4B(byte... valuesIn)
	{
		GL20.glUniform4iv(this.location(), Primitive.toIntArray(valuesIn));

		return this.parent();
	}

	public IShaderProgram load(short valueIn)
	{
		GL20.glUniform1i(this.location(), valueIn);

		return this.parent();
	}

	public IShaderProgram load(short... valuesIn)
	{
		GL20.glUniform1iv(this.location(), Primitive.toIntArray(valuesIn));

		return this.parent();
	}

	public IShaderProgram load2S(short... valuesIn)
	{
		GL20.glUniform2iv(this.location(), Primitive.toIntArray(valuesIn));

		return this.parent();
	}

	public IShaderProgram load3S(short... valuesIn)
	{
		GL20.glUniform3iv(this.location(), Primitive.toIntArray(valuesIn));

		return this.parent();
	}

	public IShaderProgram load4S(short... valuesIn)
	{
		GL20.glUniform4iv(this.location(), Primitive.toIntArray(valuesIn));

		return this.parent();
	}

	public IShaderProgram load(int valueIn)
	{
		GL20.glUniform1i(this.location(), valueIn);

		return this.parent();
	}

	public IShaderProgram load(int... valuesIn)
	{
		GL20.glUniform1iv(this.location(), valuesIn);

		return this.parent();
	}

	public IShaderProgram load(float valueIn)
	{
		GL20.glUniform1f(this.location(), valueIn);

		return this.parent();
	}

	public IShaderProgram load(float... valuesIn)
	{
		GL20.glUniform1fv(this.location(), valuesIn);

		return this.parent();
	}

	public IShaderProgram load(float xIn, float yIn)
	{
		GL20.glUniform2f(this.location(), xIn, yIn);

		return this.parent();
	}

	public IShaderProgram load(Vector2i valueIn)
	{
		GL20.glUniform2i(this.location(), valueIn.x(), valueIn.y());

		return this.parent();
	}

	public IShaderProgram load(Vector2f valueIn)
	{
		GL20.glUniform2f(this.location(), valueIn.x(), valueIn.y());

		return this.parent();
	}

	public IShaderProgram load(Vector2d valueIn)
	{
		GL20.glUniform2f(this.location(), (float) valueIn.x(), (float) valueIn.y());

		return this.parent();
	}

	public IShaderProgram load2I(int... valuesIn)
	{
		GL20.glUniform2iv(this.location(), valuesIn);

		return this.parent();
	}

	public IShaderProgram load2F(float... valuesIn)
	{
		GL20.glUniform2fv(this.location(), valuesIn);

		return this.parent();
	}

	public IShaderProgram load(float xIn, float yIn, float zIn)
	{
		GL20.glUniform3f(this.location(), xIn, yIn, zIn);

		return this.parent();
	}

	public IShaderProgram load(Vector3i valueIn)
	{
		GL20.glUniform3i(this.location(), valueIn.x(), valueIn.y(), valueIn.z());

		return this.parent();
	}

	public IShaderProgram load(Vector3f valueIn)
	{
		GL20.glUniform3f(this.location(), valueIn.x(), valueIn.y(), valueIn.z());

		return this.parent();
	}

	public IShaderProgram load(Vector3d valueIn)
	{
		GL20.glUniform3f(this.location(), (float) valueIn.x(), (float) valueIn.y(), (float) valueIn.z());

		return this.parent();
	}

	public IShaderProgram load3I(int... valuesIn)
	{
		GL20.glUniform3iv(this.location(), valuesIn);

		return this.parent();
	}

	public IShaderProgram load3F(float... valuesIn)
	{
		GL20.glUniform3fv(this.location(), valuesIn);

		return this.parent();
	}

	public IShaderProgram load(float xIn, float yIn, float zIn, float wIn)
	{
		GL20.glUniform4f(this.location(), xIn, yIn, zIn, wIn);

		return this.parent();
	}

	public IShaderProgram load(Vector4i valueIn)
	{
		GL20.glUniform4f(this.location(), valueIn.x(), valueIn.y(), valueIn.z(), valueIn.w());

		return this.parent();
	}

	public IShaderProgram load(Vector4f valueIn)
	{
		GL20.glUniform4f(this.location(), valueIn.x(), valueIn.y(), valueIn.z(), valueIn.w());

		return this.parent();
	}

	public IShaderProgram load(Vector4d valueIn)
	{
		GL20.glUniform4f(this.location(), (float) valueIn.x(), (float) valueIn.y(), (float) valueIn.z(),
				(float) valueIn.w());

		return this.parent();
	}

	public IShaderProgram load4I(int... valuesIn)
	{
		GL20.glUniform4iv(this.location(), valuesIn);

		return this.parent();
	}

	public IShaderProgram load4F(float... valuesIn)
	{
		GL20.glUniform4fv(this.location(), valuesIn);

		return this.parent();
	}
	// Matrix2f

	public IShaderProgram load(Matrix2f valueIn)
	{
		GL20.glUniformMatrix2fv(this.location(), false, BufferHelper.convertToBuffer(valueIn));

		return this.parent();
	}

	public IShaderProgram loadTranspose(Matrix2f valueIn)
	{
		GL20.glUniformMatrix2fv(this.location(), true, BufferHelper.convertToBuffer(valueIn));

		return this.parent();
	}

	public IShaderProgram loadMat2(FloatBuffer bufferIn)
	{
		GL20.glUniformMatrix2fv(this.location(), false, bufferIn);

		return this.parent();
	}

	public IShaderProgram loadMat2Transpose(FloatBuffer bufferIn)
	{
		GL20.glUniformMatrix2fv(this.location(), true, bufferIn);

		return this.parent();
	}

	public IShaderProgram loadMat2(float[] arrayIn)
	{
		GL20.glUniformMatrix2fv(this.location(), false, arrayIn);

		return this.parent();
	}

	public IShaderProgram loadMat2Transpose(float[] arrayIn)
	{
		GL20.glUniformMatrix2fv(this.location(), true, arrayIn);

		return this.parent();
	}

	// Matrix3f

	public IShaderProgram load(Matrix3f valueIn)
	{
		GL20.glUniformMatrix3fv(this.location(), false, BufferHelper.convertToBuffer(valueIn));

		return this.parent();
	}

	public IShaderProgram loadTranspose(Matrix3f valueIn)
	{
		GL20.glUniformMatrix3fv(this.location(), true, BufferHelper.convertToBuffer(valueIn));

		return this.parent();
	}

	public IShaderProgram loadMat3(FloatBuffer bufferIn)
	{
		GL20.glUniformMatrix3fv(this.location(), false, bufferIn);

		return this.parent();
	}

	public IShaderProgram loadMat3Transpose(FloatBuffer bufferIn)
	{
		GL20.glUniformMatrix3fv(this.location(), true, bufferIn);

		return this.parent();
	}

	public IShaderProgram loadMat3(float[] arrayIn)
	{
		GL20.glUniformMatrix3fv(this.location(), false, arrayIn);

		return this.parent();
	}

	public IShaderProgram loadMat3Transpose(float[] arrayIn)
	{
		GL20.glUniformMatrix3fv(this.location(), true, arrayIn);

		return this.parent();
	}

	// Matrix4f

	public IShaderProgram load(Matrix4f valueIn)
	{
		GL20.glUniformMatrix4fv(this.location(), false, BufferHelper.convertToBuffer(valueIn));

		return this.parent();
	}

	public IShaderProgram loadTranspose(Matrix4f valueIn)
	{
		GL20.glUniformMatrix4fv(this.location(), true, BufferHelper.convertToBuffer(valueIn));

		return this.parent();
	}

	public IShaderProgram loadMat4(FloatBuffer bufferIn)
	{
		GL20.glUniformMatrix4fv(this.location(), false, bufferIn);

		return this.parent();
	}

	public IShaderProgram loadMat4Transpose(FloatBuffer bufferIn)
	{
		GL20.glUniformMatrix4fv(this.location(), true, bufferIn);

		return this.parent();
	}

	public IShaderProgram loadMat4(float[] arrayIn)
	{
		GL20.glUniformMatrix4fv(this.location(), false, arrayIn);

		return this.parent();
	}

	public IShaderProgram loadMat4Transpose(float[] arrayIn)
	{
		GL20.glUniformMatrix4fv(this.location(), true, arrayIn);

		return this.parent();
	}
}