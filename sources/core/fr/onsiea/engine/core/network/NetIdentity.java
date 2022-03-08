package fr.onsiea.engine.core.network;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Class that allows a device to identify itself on the INTRANET.
 *
 * @author Decoded4620 2016
 */
public class NetIdentity
{
	private String	loopbackHost	= "";
	private String	host			= "";

	private String	loopbackIp		= "";
	private String	ip				= "";

	public NetIdentity()
	{
		try
		{
			final var interfaces = NetworkInterface.getNetworkInterfaces();

			while (interfaces.hasMoreElements())
			{
				final var i = interfaces.nextElement();
				if (i != null)
				{
					final var addresses = i.getInetAddresses();
					while (addresses.hasMoreElements())
					{
						final var	address		= addresses.nextElement();
						final var	hostAddr	= address.getHostAddress();

						// local loopback
						if (hostAddr.indexOf("127.") == 0)
						{
							this.loopbackIp		= address.getHostAddress();
							this.loopbackHost	= address.getHostName();
						}

						final var	networkInterface	= NetworkInterface.getByInetAddress(address);
						var			success				= false;
						// internal ip addresses (behind this router)
						if ((networkInterface != null) && (address.isSiteLocalAddress() && !networkInterface.isVirtual()
								&& !networkInterface.isLoopback()))
						// if (hostAddr.indexOf("192.168") == 0 || hostAddr.indexOf("10.") == 0
						// || hostAddr.indexOf("172.16") == 0)
						{
							this.host	= address.getHostName();
							this.ip		= address.getHostAddress();
							success		= true;
						}
						if (!success)
						{
							continue;
						}

						System.out.println(i.getDisplayName());
						System.out.println("\t\t-" + address.getHostName() + ":" + address.getHostAddress() + " - "
								+ address.getAddress());
					}
				}
			}
		}
		catch (final SocketException e)
		{

		}
		try
		{
			final var loopbackIpAddress = InetAddress.getLocalHost();
			this.loopbackIp = loopbackIpAddress.getHostName();
			System.out.println("LOCALHOST: " + this.loopbackIp);
		}
		catch (final UnknownHostException e)
		{
			System.err.println("ERR: " + e.toString());
		}
	}

	public String getLoopbackHost()
	{
		return this.loopbackHost;
	}

	public String getHost()
	{
		return this.host;
	}

	public String getIp()
	{
		return this.ip;
	}

	public String getLoopbackIp()
	{
		return this.loopbackIp;
	}
}