package fr.onsiea.engine.core.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class NetworkReceiver implements Runnable
{
	private Map<String, Value>	values;
	private boolean				isRunning;
	public boolean				isConnected;
	private final int			port;
	private Socket				client;
	private ServerSocket		serverSocket;
	BufferedReader				br;

	public NetworkReceiver(int port)
	{
		this.port = port;
		this.initialization();
	}

	@Override
	public void run()
	{
		while (this.isRunning && !Thread.currentThread().isInterrupted())
		{
			while (this.client == null || !this.client.isConnected() || this.client.isClosed())
			{
				System.out.println("Receiver : clientConnexion !");
				this.clientConnexion();
				System.out.println("Receiver : client is connected !");
			}
			if ((this.client == null) || (this.client != null && (!this.client.isConnected() || this.client.isClosed())))
			{
				this.isConnected = false;
			}
			else
			{
				this.isConnected = true;
			}

			try
			{
				final var content = this.br.readLine();
				if (content != null)
				{
					System.out.println("Has receive ! " + content);
					var		i		= 1;
					String	nameIn	= null;
					String	valueIn	= null;
					String	c;
					while ((c = content.substring(i - 1, i)) != null && i < content.length())
					{
						if (":".equals(c))
						{
							nameIn	= content.substring(0, i - 1);
							valueIn	= content.substring(i);
						}
						i++;
					}
					if (nameIn != null && valueIn != null)
					{
						this.values.put(nameIn, new Value(valueIn));
					}
				}
			}
			catch (final IOException e)
			{
				e.printStackTrace();
				this.stop();
				this.close();
			}
		}
	}

	private void close()
	{
		try
		{
			this.client.close();
			this.serverSocket.close();
			this.br.close();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}

	public void initialization()
	{
		this.values		= new HashMap<>();
		this.isRunning	= true;
		try
		{
			this.serverSocket = new ServerSocket(this.port, 100, InetAddress.getLocalHost());
		}
		catch (final IOException e)
		{
			e.printStackTrace();
			this.stop();
			this.close();
		}
	}

	public void clientConnexion()
	{
		try
		{
			this.client	= this.serverSocket.accept();
			this.br		= new BufferedReader(new InputStreamReader(this.client.getInputStream()));
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}

	public synchronized Value getValue(String nameIn)
	{
		if (this.values.get(nameIn) == null)
		{
			return null;
		}
		return this.values.get(nameIn).copy();
	}

	public synchronized void remove(String nameId)
	{
		this.values.remove(nameId);
	}

	public synchronized void stop()
	{
		this.isRunning = false;
	}

	public static class Value
	{
		private String value;

		public Value(String valueIn)
		{
			this.setValue(valueIn);
		}

		private void setValue(String valueIn)
		{
			this.value = valueIn;
		}

		public String getValue()
		{
			return this.value;
		}

		public Value copy()
		{
			return new Value(this.getValue());
		}
	}
}