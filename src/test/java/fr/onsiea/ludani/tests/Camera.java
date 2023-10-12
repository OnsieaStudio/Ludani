/*
 * Copyright 2021-2023 Onsiea Studio some rights reserved.
 *
 * This file is part of Ludart Game Framework project developed by Onsiea Studio.
 * (https://github.com/OnsieaStudio/Ludart)
 *
 * Ludart is [licensed]
 * (https://github.com/OnsieaStudio/Ludart/blob/main/LICENSE) under the terms of
 * the "GNU General Public License v3.0" (GPL-3.0).
 * https://github.com/OnsieaStudio/Ludart/wiki/License#license-and-copyright
 *
 * Ludart is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3.0 of the License, or
 * (at your option) any later version.
 *
 * Ludart is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Ludart. If not, see <https://www.gnu.org/licenses/>.
 *
 * Any reproduction or alteration of this project may reference it and utilize its name and derivatives, provided it clearly states its modification status and includes a link to the original repository. Usage of all names belonging to authors, developers, and contributors remains subject to copyright.
 * in other cases prior written authorization is required for using names such as "Onsiea," "Ludart," or any names derived from authors, developers, or contributors for product endorsements or promotional purposes.
 *
 *
 * @Author : Seynax (https://github.com/seynax)
 * @Organization : Onsiea Studio (https://github.com/OnsieaStudio)
 */

package fr.onsiea.ludani.tests;

import fr.onsiea.ludart.client.window.settings.IWindowSettings;
import lombok.Getter;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

@Getter
public class Camera
{
	private final static double   PI_180                 = Math.PI / 180.0D;
	private final static double[] X_MOUSE_POSITION_ARRAY = new double[1];
	private final static double[] Y_MOUSE_POSITION_ARRAY = new double[1];
	private final        Vector3f X_VECTOR               = new Vector3f(1, 0, 0);
	private final        Vector3f Y_VECTOR               = new Vector3f(0, 1, 0);
	private final        Vector3f cameraPosition;
	private final        Vector2f cameraOrientation;
	private final        Matrix4f viewMatrix;
	private final        double   xScreenCenter;
	private final        double   yScreenCenter;
	private final        double   speed;
	private final        double   rotateSpeed;
	private final        int      xRotationMax;

	public Camera(double speedIn, double rotateSpeedIn, IWindowSettings windowSettingsIn)
	{
		speed       = speedIn;
		rotateSpeed = rotateSpeedIn;

		cameraPosition    = new Vector3f();
		cameraOrientation = new Vector2f();
		viewMatrix        = new Matrix4f();

		xRotationMax  = 90;
		xScreenCenter = windowSettingsIn.width() / 2;
		yScreenCenter = windowSettingsIn.height() / 2;
	}

	public void input(long windowHandleIn)
	{
		GLFW.glfwGetCursorPos(windowHandleIn, X_MOUSE_POSITION_ARRAY, Y_MOUSE_POSITION_ARRAY);
		GLFW.glfwSetCursorPos(windowHandleIn, xScreenCenter, yScreenCenter);
		var xMouseTranslation = xScreenCenter - X_MOUSE_POSITION_ARRAY[0];
		var yMouseTranslation = yScreenCenter - Y_MOUSE_POSITION_ARRAY[0];
		cameraOrientation.y += -xMouseTranslation * rotateSpeed;
		cameraOrientation.x += -yMouseTranslation * rotateSpeed;
		cameraOrientation.x %= 360;
		if (cameraOrientation.x() > xRotationMax)
		{
			cameraOrientation.x = xRotationMax;
		}
		else if (cameraOrientation.x() < -xRotationMax)
		{
			cameraOrientation.x = -xRotationMax;
		}
		cameraOrientation.y %= 360;

		if (GLFW.glfwGetKey(windowHandleIn, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS)
		{
			cameraPosition.x += -Math.sin(cameraOrientation.y() * (PI_180)) * speed;
			cameraPosition.z += Math.cos(cameraOrientation.y() * (PI_180)) * speed;
		}
		if (GLFW.glfwGetKey(windowHandleIn, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS)
		{
			cameraPosition.x += Math.sin(cameraOrientation.y() * (PI_180)) * speed;
			cameraPosition.z += -Math.cos(cameraOrientation.y() * (PI_180)) * speed;
		}
		if (GLFW.glfwGetKey(windowHandleIn, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS)
		{
			cameraPosition.x += Math.sin((cameraOrientation.y() + 90) * (PI_180)) * speed;
			cameraPosition.z += -Math.cos((cameraOrientation.y() + 90) * (PI_180)) * speed;
		}
		if (GLFW.glfwGetKey(windowHandleIn, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS)
		{
			cameraPosition.x += Math.sin((cameraOrientation.y() - 90) * (PI_180)) * speed;
			cameraPosition.z += -Math.cos((cameraOrientation.y() - 90) * (PI_180)) * speed;
		}
		if (GLFW.glfwGetKey(windowHandleIn, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS)
		{
			cameraPosition.y += speed;
		}
		if (GLFW.glfwGetKey(windowHandleIn, GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS)
		{
			cameraPosition.y -= speed;
		}
	}

	public Matrix4f view()
	{
		viewMatrix.identity();

		viewMatrix.rotate((float) Math.toRadians(cameraOrientation.x), X_VECTOR)
				.rotate((float) Math.toRadians(cameraOrientation.y), Y_VECTOR);

		viewMatrix.translate(-cameraPosition.x, -cameraPosition.y, -cameraPosition.z);

		return viewMatrix;
	}
}
