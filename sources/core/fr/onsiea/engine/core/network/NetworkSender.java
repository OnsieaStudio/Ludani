package fr.onsiea.engine.core.network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class NetworkSender implements Runnable
{
	private List<String>	toSends;
	private boolean			isRunning;
	public boolean			isConnected;
	private final String	clientIp;
	private final int		port;
	private Socket			client;
	private BufferedWriter	os;

	public NetworkSender(String clientIp, int port)
	{
		this.clientIp	= clientIp;
		this.port		= port;
		this.initialization();
	}

	@Override
	public void run()
	{
		while (this.isRunning || Thread.currentThread().isInterrupted())
		{
			if ((this.client == null) || (this.client != null && (!this.client.isConnected() || this.client.isClosed())))
			{
				this.isConnected = false;
			}
			else
			{
				this.isConnected = true;
			}
			while (this.client == null || !this.client.isConnected() || this.client.isClosed())
			{
				System.out.println("Sender : clientConnexion !");
				this.clientConnexion();
			}

			try
			{
				for (var i = this.toSends.size() - 1; i >= 0; i--)
				{
					final var toSend = this.toSends.get(i);
					if (toSend == null)
					{
						continue;
					}
					this.os.write(toSend);
					this.os.newLine();
					this.os.flush();

					System.out.println("Sender : hasSend ! " + toSend);
				}
				this.toSends.clear();
			}
			catch (final IOException e)
			{
				e.printStackTrace();
				this.stop();
				this.close();
			}
		}

		this.close();
	}

	private void close()
	{
		try
		{
			this.client.close();
			this.os.close();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}

	public void initialization()
	{
		this.toSends	= new ArrayList<>();
		this.isRunning	= true;
	}

	public void clientConnexion()
	{
		try
		{
			this.client	= new Socket(this.clientIp, this.port);
			this.os		= new BufferedWriter(new OutputStreamWriter(this.client.getOutputStream()));
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}

	public synchronized void send(String name, String value)
	{
		this.toSends.add(name + ":" + value);
	}

	public synchronized void send(String message)
	{
		this.toSends.add(message);
	}

	public synchronized void stop()
	{
		this.isRunning = false;
	}
}