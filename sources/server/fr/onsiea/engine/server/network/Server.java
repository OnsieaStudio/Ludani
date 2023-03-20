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
import java.net.ServerSocket;
import java.net.SocketTimeoutException;

/**
 * @author Seynax
 *
 */
public class Server extends Thread
{
	private final ServerSocket serverSocket;

	private Server(final int portIn) throws IOException
	{
		this.serverSocket = new ServerSocket(portIn);
		this.serverSocket.setSoTimeout(10000);
	}

	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				System.out.println("			[SERVER]	Waiting for client on port : \""
						+ this.serverSocket.getLocalPort() + "\" ...");
				final var server = this.serverSocket.accept();

				System.out.println("			[SERVER]	Just connected to " + server.getRemoteSocketAddress());
				final var in = new DataInputStream(server.getInputStream());

				System.out.println("			[SERVER]	Client says : " + in.readUTF());
				final var out = new DataOutputStream(server.getOutputStream());
				out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress() + "\nGoodbye!");
				server.close();
			}
			catch (final SocketTimeoutException s)
			{
				System.out.println("			[SERVER]	Socket timed out!");
				break;
			}
			catch (final IOException e)
			{
				e.printStackTrace();
				break;
			}
		}
	}

	public final static Server start(final int portIn)
	{
		try
		{
			final var		server	= new Server(portIn);
			final Thread	t		= server;
			t.run();

			return server;
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
