package fr.onsiea.engine.client.graphics.opengl.utils.old;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import fr.onsiea.engine.maths.MathUtils;

public class GLDirect
{
	private static final GLDisplayList glDisplayList = new GLDisplayList();

	public static void gluPerspective(float fovy, float aspect, float near, float far)
	{
		final var	bottom	= -near * MathUtils.coTangent((float) Math.toRadians(fovy / 2f));
		final var	top		= -bottom;
		final var	left	= aspect * bottom;
		final var	right	= -left;
		GL11.glFrustum(bottom, top, left, right, near, far);
	}

	public static void prepareDraw()
	{

		//GL11.glLoadIdentity();

		GL11.glTranslatef(0.0f, 0.0f, 0.0f);
		GL11.glRotatef(0.0f, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(0.0f, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(0.0f, 0.0f, 0.0f, 1.0f);
		GL11.glColor3f(1.0f, 1.0f, 1.0f);

	}

	public static void prepareDraw(Vector3f translationIn)
	{

		//GL11.glLoadIdentity();

		GL11.glTranslatef(translationIn.x, translationIn.y, translationIn.z);
		GL11.glRotatef(0.0f, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(0.0f, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(0.0f, 0.0f, 0.0f, 1.0f);
		GL11.glColor3f(1.0f, 1.0f, 1.0f);

	}

	public static void prepareDraw(Vector3f translationIn, Vector3f rotationIn)
	{

		//GL11.glLoadIdentity();

		GL11.glTranslatef(translationIn.x, translationIn.y, translationIn.z);
		GL11.glRotatef(rotationIn.x, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(rotationIn.y, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(rotationIn.z, 0.0f, 0.0f, 1.0f);
		GL11.glColor3f(1.0f, 1.0f, 1.0f);
	}

	public static void prepareDraw(Vector3f translationIn, Vector3f rotationIn, Vector3f colorIn)
	{

		//GL11.glLoadIdentity();

		GL11.glTranslatef(translationIn.x, translationIn.y, translationIn.z);
		GL11.glRotatef(rotationIn.x, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(rotationIn.y, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(rotationIn.z, 0.0f, 0.0f, 1.0f);
		GL11.glColor3f(colorIn.x, colorIn.y, colorIn.z);

	}

	public static void prepareDraw(Vector3f translationIn, Vector3f rotationIn, Vector3f colorIn, float alphaIn)
	{

		//GL11.glLoadIdentity();

		GL11.glTranslatef(translationIn.x, translationIn.y, translationIn.z);
		GL11.glRotatef(rotationIn.x, 1.0f, 0.0f, 0.0f);
		GL11.glRotatef(rotationIn.y, 0.0f, 1.0f, 0.0f);
		GL11.glRotatef(rotationIn.z, 0.0f, 0.0f, 1.0f);
		GL11.glColor4f(colorIn.x, colorIn.y, colorIn.z, alphaIn);

	}

	public static void beginQuads()
	{
		GL11.glBegin(GL11.GL_QUADS);
	}

	public static void beginTriangles()
	{
		GL11.glBegin(GL11.GL_TRIANGLES);
	}

	public static void beginLines()
	{
		GL11.glBegin(GL11.GL_LINES);
	}

	public static void drawVertex(Vector3f vertexIn)
	{
		GL11.glVertex3f(vertexIn.x, vertexIn.y, vertexIn.z);
	}

	public static void drawVertex(float xIn, float yIn, float zIn)
	{
		GL11.glVertex3f(xIn, yIn, zIn);
	}

	public static void drawColor(Vector3f colorIn)
	{
		GL11.glColor3f(colorIn.x, colorIn.y, colorIn.z);
	}

	public static void drawColor(float redIn, float greenIn, float blueIn)
	{
		GL11.glColor3f(redIn, greenIn, blueIn);
	}

	public static void drawColor(float redIn, float greenIn, float blueIn, float alphaIn)
	{
		GL11.glColor4f(redIn, greenIn, blueIn, alphaIn);
	}

	public static void drawTex(Vector2f texIn)
	{
		GL11.glTexCoord2f(texIn.x, texIn.y);
	}

	public static void drawTex(float xIn, float yIn)
	{
		GL11.glTexCoord2f(xIn, yIn);
	}

	public static void drawTex(Vector3f texIn)
	{
		GL11.glTexCoord3f(texIn.x, texIn.y, texIn.z);
	}

	public static void drawTex(float xIn, float yIn, float zIn)
	{
		GL11.glTexCoord3f(xIn, yIn, zIn);
	}

	public static void callList(String nameIn)
	{
		GLDirect.glDisplayList.call(nameIn);
	}

	public static void callList(int idIn)
	{
		GLDirect.glDisplayList.call(idIn);
	}

	public void drawQuadsCube()
	{
		GLDirect.drawColor(new Vector3f(1.0f, 1.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 1.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 1.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 1.0f, 1.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 1.0f, 1.0f));
		GLDirect.drawColor(new Vector3f(1.0f, 0.5f, 0.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 0.0f, 1.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 0.0f, 1.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 0.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 0.0f, 0.0f));
		GLDirect.drawColor(new Vector3f(1.0f, 0.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 1.0f, 1.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 1.0f, 1.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 0.0f, 1.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 0.0f, 1.0f));
		GLDirect.drawColor(new Vector3f(1.0f, 1.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 0.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 0.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 1.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 1.0f, 0.0f));
		GLDirect.drawColor(new Vector3f(0.0f, 0.0f, 1.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 1.0f, 1.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 1.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 0.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 0.0f, 1.0f));
		GLDirect.drawColor(new Vector3f(1.0f, 0.0f, 1.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 1.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 1.0f, 1.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 0.0f, 1.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 0.0f, 0.0f));
		this.endDraw();
	}

	public void drawQuadsCube(int textureIdIn)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureIdIn);
		//drawColor(1.0f,1.0f,0.0f);
		GLDirect.drawTex(1, 1);
		GLDirect.drawVertex(1.0f, 1.0f, 0.0f);
		GLDirect.drawTex(0, 1);
		GLDirect.drawVertex(0.0f, 1.0f, 0.0f);
		GLDirect.drawTex(0, 0);
		GLDirect.drawVertex(0.0f, 1.0f, 1.0f);
		GLDirect.drawTex(1, 0);
		GLDirect.drawVertex(1.0f, 1.0f, 1.0f);

		GLDirect.drawTex(1, 0);
		GLDirect.drawVertex(1.0f, 0.0f, 1.0f);
		GLDirect.drawTex(0, 0);
		GLDirect.drawVertex(0.0f, 0.0f, 1.0f);
		GLDirect.drawTex(0, 1);
		GLDirect.drawVertex(0.0f, 0.0f, 0.0f);
		GLDirect.drawTex(1, 1);
		GLDirect.drawVertex(1.0f, 0.0f, 0.0f);

		GLDirect.drawTex(0, 0);
		GLDirect.drawVertex(1.0f, 1.0f, 1.0f);
		GLDirect.drawTex(1, 0);
		GLDirect.drawVertex(0.0f, 1.0f, 1.0f);
		GLDirect.drawTex(1, 1);
		GLDirect.drawVertex(0.0f, 0.0f, 1.0f);
		GLDirect.drawTex(0, 1);
		GLDirect.drawVertex(1.0f, 0.0f, 1.0f);

		GLDirect.drawTex(1, 1);
		GLDirect.drawVertex(1.0f, 0.0f, 0.0f);
		GLDirect.drawTex(0, 1);
		GLDirect.drawVertex(0.0f, 0.0f, 0.0f);
		GLDirect.drawTex(0, 0);
		GLDirect.drawVertex(0.0f, 1.0f, 0.0f);
		GLDirect.drawTex(1, 0);
		GLDirect.drawVertex(1.0f, 1.0f, 0.0f);

		GLDirect.drawTex(0, 0);
		GLDirect.drawVertex(0.0f, 1.0f, 1.0f);
		GLDirect.drawTex(1, 0);
		GLDirect.drawVertex(0.0f, 1.0f, 0.0f);
		GLDirect.drawTex(1, 1);
		GLDirect.drawVertex(0.0f, 0.0f, 0.0f);
		GLDirect.drawTex(0, 1);
		GLDirect.drawVertex(0.0f, 0.0f, 1.0f);

		GLDirect.drawTex(0, 0);
		GLDirect.drawVertex(1.0f, 1.0f, 0.0f);
		GLDirect.drawTex(1, 0);
		GLDirect.drawVertex(1.0f, 1.0f, 1.0f);
		GLDirect.drawTex(1, 1);
		GLDirect.drawVertex(1.0f, 0.0f, 1.0f);
		GLDirect.drawTex(0, 1);
		GLDirect.drawVertex(1.0f, 0.0f, 0.0f);
		this.endDraw();
	}

	public void drawCube()
	{
		GLDirect.drawColor(new Vector3f(1.0f, 1.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 1.0f, 1.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 0.0f, 1.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 0.0f, 1.0f));

		GLDirect.drawColor(new Vector3f(1.0f, 0.8f, 0.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 0.0f, 1.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 1.0f, 1.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 1.0f, 1.0f));

		GLDirect.drawColor(new Vector3f(1.0f, 1.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 1.0f, 1.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 0.0f, 1.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 0.0f, 0.0f));

		GLDirect.drawColor(new Vector3f(1.0f, 1.0f, 0.5f));
		GLDirect.drawVertex(new Vector3f(1.0f, 0.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 1.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 1.0f, 1.0f));

		GLDirect.drawColor(new Vector3f(1.0f, 1.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 1.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 0.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 0.0f, 0.0f));

		GLDirect.drawColor(new Vector3f(0.3f, 1.0f, 0.4f));
		GLDirect.drawVertex(new Vector3f(0.0f, 0.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 1.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 1.0f, 0.0f));

		GLDirect.drawColor(new Vector3f(1.0f, 1.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 1.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 0.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 0.0f, 1.0f));

		GLDirect.drawColor(new Vector3f(1.0f, 1.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 0.0f, 1.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 1.0f, 1.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 1.0f, 0.0f));

		GLDirect.drawColor(new Vector3f(1.0f, 1.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 1.0f, 1.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 1.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 1.0f, 0.0f));

		GLDirect.drawColor(new Vector3f(1.0f, 1.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 1.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 1.0f, 1.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 1.0f, 1.0f));

		GLDirect.drawColor(new Vector3f(1.0f, 1.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 0.0f, 1.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 0.0f, 1.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 0.0f, 0.0f));

		GLDirect.drawColor(new Vector3f(1.0f, 1.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(0.0f, 0.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 0.0f, 0.0f));
		GLDirect.drawVertex(new Vector3f(1.0f, 0.0f, 1.0f));
		this.endDraw();
	}

	public void drawCube(int textureIdIn)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureIdIn);
		GLDirect.drawTex(1, 0);
		GLDirect.drawVertex(new Vector3f(0.0f, 1.0f, 1.0f));
		GLDirect.drawTex(1, 1);
		GLDirect.drawVertex(new Vector3f(0.0f, 0.0f, 1.0f));
		GLDirect.drawTex(0, 1);
		GLDirect.drawVertex(new Vector3f(1.0f, 0.0f, 1.0f));

		GLDirect.drawTex(0, 1);
		GLDirect.drawVertex(new Vector3f(1.0f, 0.0f, 1.0f));
		GLDirect.drawTex(0, 0);
		GLDirect.drawVertex(new Vector3f(1.0f, 1.0f, 1.0f));
		GLDirect.drawTex(1, 0);
		GLDirect.drawVertex(new Vector3f(0.0f, 1.0f, 1.0f));

		GLDirect.drawTex(1, 0);
		GLDirect.drawVertex(new Vector3f(1.0f, 1.0f, 1.0f));
		GLDirect.drawTex(1, 1);
		GLDirect.drawVertex(new Vector3f(1.0f, 0.0f, 1.0f));
		GLDirect.drawTex(0, 1);
		GLDirect.drawVertex(new Vector3f(1.0f, 0.0f, 0.0f));

		GLDirect.drawTex(0, 1);
		GLDirect.drawVertex(new Vector3f(1.0f, 0.0f, 0.0f));
		GLDirect.drawTex(0, 0);
		GLDirect.drawVertex(new Vector3f(1.0f, 1.0f, 0.0f));
		GLDirect.drawTex(1, 0);
		GLDirect.drawVertex(new Vector3f(1.0f, 1.0f, 1.0f));

		GLDirect.drawTex(1, 0);
		GLDirect.drawVertex(new Vector3f(1.0f, 1.0f, 0.0f));
		GLDirect.drawTex(1, 1);
		GLDirect.drawVertex(new Vector3f(1.0f, 0.0f, 0.0f));
		GLDirect.drawTex(0, 1);
		GLDirect.drawVertex(new Vector3f(0.0f, 0.0f, 0.0f));

		GLDirect.drawTex(0, 1);
		GLDirect.drawVertex(new Vector3f(0.0f, 0.0f, 0.0f));
		GLDirect.drawTex(0, 0);
		GLDirect.drawVertex(new Vector3f(0.0f, 1.0f, 0.0f));
		GLDirect.drawTex(1, 0);
		GLDirect.drawVertex(new Vector3f(1.0f, 1.0f, 0.0f));

		GLDirect.drawTex(1, 0);
		GLDirect.drawVertex(new Vector3f(0.0f, 1.0f, 0.0f));
		GLDirect.drawTex(1, 1);
		GLDirect.drawVertex(new Vector3f(0.0f, 0.0f, 0.0f));
		GLDirect.drawTex(0, 1);
		GLDirect.drawVertex(new Vector3f(0.0f, 0.0f, 1.0f));

		GLDirect.drawTex(0, 1);
		GLDirect.drawVertex(new Vector3f(0.0f, 0.0f, 1.0f));
		GLDirect.drawTex(0, 0);
		GLDirect.drawVertex(new Vector3f(0.0f, 1.0f, 1.0f));
		GLDirect.drawTex(1, 0);
		GLDirect.drawVertex(new Vector3f(0.0f, 1.0f, 0.0f));

		GLDirect.drawTex(0, 0);
		GLDirect.drawVertex(new Vector3f(0.0f, 1.0f, 1.0f));
		GLDirect.drawTex(0, 1);
		GLDirect.drawVertex(new Vector3f(0.0f, 1.0f, 0.0f));
		GLDirect.drawTex(1, 1);
		GLDirect.drawVertex(new Vector3f(1.0f, 1.0f, 0.0f));

		GLDirect.drawTex(1, 1);
		GLDirect.drawVertex(new Vector3f(1.0f, 1.0f, 0.0f));
		GLDirect.drawTex(1, 0);
		GLDirect.drawVertex(new Vector3f(1.0f, 1.0f, 1.0f));
		GLDirect.drawTex(0, 0);
		GLDirect.drawVertex(new Vector3f(0.0f, 1.0f, 1.0f));

		GLDirect.drawTex(0, 0);
		GLDirect.drawVertex(new Vector3f(0.0f, 0.0f, 1.0f));
		GLDirect.drawTex(1, 0);
		GLDirect.drawVertex(new Vector3f(1.0f, 0.0f, 1.0f));
		GLDirect.drawTex(0, 1);
		GLDirect.drawVertex(new Vector3f(0.0f, 0.0f, 0.0f));

		GLDirect.drawTex(0, 1);
		GLDirect.drawVertex(new Vector3f(0.0f, 0.0f, 0.0f));
		GLDirect.drawTex(1, 1);
		GLDirect.drawVertex(new Vector3f(1.0f, 0.0f, 0.0f));
		GLDirect.drawTex(1, 0);
		GLDirect.drawVertex(new Vector3f(1.0f, 0.0f, 1.0f));
		this.endDraw();
	}

	public void endDraw()
	{
		GL11.glEnd();
	}
}
