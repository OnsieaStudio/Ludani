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
package fr.onsiea.engine.client.graphics.texture;

import java.nio.ByteBuffer;

/**
 * @author Seynax
 *
 */
public interface ITexture
{
	/**
	 * @return id of texture
	 */
	int id();

	/**
	 * Reset bind texture index on 0
	 * @return
	 */
	ITexture resetIndex();

	/**
	 * Attach (bind) texture to futur draw call
	 * @return
	 */
	ITexture attach();

	/**
	 * Attach (bind) texture at 0 to futur draw call
	 * @return
	 */
	ITexture attachAt0();

	/**
	 * Attach (bind) texture at indexIn to futur draw call
	 * @return
	 */
	ITexture attachAt(int indexIn);

	/**
	 * Attach (bind) texture of type to futur draw call
	 * @return
	 */
	ITexture attach(int typeIn);

	/**
	 * Attach (bind) texture of type at 0 to futur draw call
	 * @return
	 */
	ITexture attachAt0(int typeIn);

	/**
	 * Attach (bind) texture of type at indexIn to futur draw call
	 * @return
	 */
	ITexture attachAt(int typeIn, int indexIn);

	/**
	 * Send bufferIn byte data into texture (need to bind texture)
	 * @param bufferIn
	 * @return
	 */
	ITexture send(final ByteBuffer bufferIn);

	/**
	 * detach (bind) texture of type at indexIn
	 * @return
	 */
	ITexture detachAt(int typeIn, int indexIn);

	/**
	 * detach (bind) texture of type at 0
	 * @return
	 */
	ITexture detachAt0(int typeIn);

	/**
	 * detach (bind) texture of type
	 * @return
	 */
	ITexture detach(int typeIn);

	/**
	 * detach (bind) texture at indexIn
	 * @return
	 */
	ITexture detachAt(int indexIn);

	/**
	 * detach (bind) texture at 0
	 * @return
	 */
	ITexture detachAt0();

	/**
	 * detach (bind) texture
	 * @return
	 */

	ITexture detach();

	int width();

	int height();

	/**
	 *
	 */
	ITexture delete();
}