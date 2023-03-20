/**
*	Copyright 2021 Onsiea All rights reserved.
*
*	This file is part of Onsiea Engine. (https://github.com/Onsiea/OnsieaEngine)
*
*	Unless noted in license (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE.md) notice file (https://github.com/Onsiea/OnsieaEngine/blob/main/LICENSE_NOTICE.md), Onsiea engine and all parts herein is licensed under the terms of the LGPL-3.0 (https://www.gnu.org/licenses/lgpl-3.0.html)  found here https://www.gnu.org/licenses/lgpl-3.0.html and copied below the license file.
*
*	Onsiea Engine is libre software: you can redistribute it and/or modify
*	it under the terms of the GNU Lesser General Public License as published by
*	the Free Software Foundation, either version 3.0 of the License, or
*	(at your option) any later version.
*
*	Onsiea Engine is distributed in the hope that it will be useful,
*	but WITHOUT ANY WARRANTY; without even the implied warranty of
*	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*	GNU Lesser General Public License for more details.
*
*	You should have received a copy of the GNU Lesser General Public License
*	along with Onsiea Engine.  If not, see <https://www.gnu.org/licenses/> <https://www.gnu.org/licenses/lgpl-3.0.html>.
*
*	Neither the name "Onsiea", "Onsiea Engine", or any derivative name or the names of its authors / contributors may be used to endorse or promote products derived from this software and even less to name another project or other work without clear and precise permissions written in advance.
*
*	(more details on https://github.com/Onsiea/OnsieaEngine/wiki/License)
*
*	@author Seynax
*/
package fr.onsiea.engine.server.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Seynax
 *
 */
public class Client
{
	private final String	serverName;
	private final int		serverPort;

	private Client(final String serverNameIn, final int serverPortIn)
	{
		this.serverName	= serverNameIn;
		this.serverPort	= serverPortIn;
	}

	private final Client runtime()
	{
		try
		{
			System.out.println("[CLIENT]	Connecting to " + this.serverName + " on port " + this.serverPort);
			final var client = new Socket(this.serverName, this.serverPort);

			System.out.println("[CLIENT]	Just connected to " + client.getRemoteSocketAddress());
			final var	outToServer	= client.getOutputStream();
			final var	out			= new DataOutputStream(outToServer);

			out.writeUTF("[CLIENT]	Hello from " + client.getLocalSocketAddress());
			final var	inFromServer	= client.getInputStream();
			final var	in				= new DataInputStream(inFromServer);

			System.out.println("[CLIENT]	Server says : " + in.readUTF());
			client.close();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}

		return this;
	}

	public final static Client start(final String serverNameIn, final int serverPortIn)
	{
		return new Client(serverNameIn, serverPortIn).runtime();
	}
}