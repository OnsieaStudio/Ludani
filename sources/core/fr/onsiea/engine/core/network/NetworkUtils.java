package fr.onsiea.engine.core.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import fr.onsiea.engine.core.network.packet.PacketType;

public class NetworkUtils
{
	public final static Priority	LOW		= new Priority((byte) 0);
	public final static Priority	MIDDLE	= new Priority((byte) 127);
	public final static Priority	HIGH	= new Priority((byte) 255);

	/**
	 * Return true if levelIn (Integer) is LOW level
	 *
	 * @param levelIn
	 * @return
	 */
	public boolean isLowPriority(final int levelIn)
	{
		return levelIn == NetworkUtils.LOW.getLevel();
	}

	/**
	 * Return true if levelIn (Integer) is MIDDLE level
	 *
	 * @param levelIn
	 * @return
	 */
	public boolean isMiddlePriority(final int levelIn)
	{
		return levelIn == NetworkUtils.MIDDLE.getLevel();
	}

	/**
	 * Return true if levelIn (Integer) is HIGH level
	 *
	 * @param levelIn
	 * @return
	 */
	public boolean isHighPriority(final int levelIn)
	{
		return levelIn == NetworkUtils.HIGH.getLevel();
	}

	/**
	 * Return new PacketType with parameter nameIn (String) priorityIn (Priority)
	 * byGroupIn (boolean)
	 *
	 * @param nameIn
	 * @param priorityIn
	 * @param byGroupIn
	 * @return
	 */
	public static PacketType createType(final String nameIn, final Priority priorityIn, final boolean byGroupIn)
	{
		return new PacketType(nameIn, priorityIn, byGroupIn);
	}

	public final static void test()
	{
		try
		{
			new NetIdentity();
			System.out.println("localhost : ");
			final var localhost = InetAddress.getLocalHost();
			NetworkUtils.inform(localhost);
			System.out.println("");
			System.out.println("");
			NetworkUtils.inform(InetAddress.getAllByName(localhost.getHostName()));

			final var	networkInterfaces	= NetworkInterface.getNetworkInterfaces();

			final var	iterator			= networkInterfaces.asIterator();
			while (iterator.hasNext())
			{
				final var networkInterface = iterator.next();
				if (networkInterface != null)
				{
					NetworkUtils.informNetworkInterface(networkInterface);
				}
			}

		}
		catch (final UnknownHostException | SocketException e)
		{
			e.printStackTrace();
		}
	}

	public final static void inform(final InetAddress... iNetAddress_sIn)
	{
		for (final InetAddress iNetAddress : iNetAddress_sIn)
		{
			if (iNetAddress == null)
			{
				continue;
			}
			try
			{
				System.out.println("Canonical : " + iNetAddress.getCanonicalHostName());
				System.out.println("HostAddress : " + iNetAddress.getHostAddress());
				System.out.println("HostName : " + iNetAddress.getHostName());

				if (iNetAddress.getAddress() != null)
				{
					System.out.print("Address : " + iNetAddress.getAddress() + " : ");
					for (final byte byt : iNetAddress.getAddress())
					{
						System.out.print(byt + ".");
					}
					System.out.println();
				}

				System.out.println("LocalAddress : " + iNetAddress.isAnyLocalAddress());
				System.out.println("LinkLocalAddress : " + iNetAddress.isLinkLocalAddress());
				System.out.println("LoopbackAddress : " + iNetAddress.isLoopbackAddress());
				System.out.println("MCGlobal : " + iNetAddress.isMCGlobal());
				System.out.println("MCLinkGlobal : " + iNetAddress.isMCLinkLocal());
				System.out.println("MCNodeGlobal : " + iNetAddress.isMCNodeLocal());
				System.out.println("MCOrgGlobal : " + iNetAddress.isMCOrgLocal());
				System.out.println("MCSiteGlobal : " + iNetAddress.isMCSiteLocal());
				System.out.println("MulticastAddress : " + iNetAddress.isMulticastAddress());
				System.out.println("Reachable : " + iNetAddress.isReachable(10));
				System.out.println("SiteLocalAddress : " + iNetAddress.isSiteLocalAddress());
			}
			catch (final IOException e)
			{
				e.printStackTrace();
			}
			System.out.println("");
			System.out.println("");
		}
	}

	public final static void informNetworkInterface(final NetworkInterface... networkInterfacesIn)
	{
		for (final NetworkInterface networkInterface : networkInterfacesIn)
		{
			try
			{
				System.out.println("DisplayName : " + networkInterface.getDisplayName());
				System.out.println("Index : " + networkInterface.getIndex());
				System.out.println("MTU : " + networkInterface.getMTU());
				System.out.println("Name : " + networkInterface.getName());

				if (networkInterface.getHardwareAddress() != null)
				{
					System.out.println("HardwareAddress : " + networkInterface.getHardwareAddress());
					for (final byte byt : networkInterface.getHardwareAddress())
					{
						System.out.print(byt + ".");
					}
					System.out.println();
				}

				if (networkInterface.getInetAddresses() != null)
				{
					System.out.println("Inetaddress of hardware : ");
					final var iteratorAddress = networkInterface.getInetAddresses().asIterator();
					while (iteratorAddress.hasNext())
					{
						final var iNetAddress = iteratorAddress.next();
						if (iNetAddress != null)
						{
							NetworkUtils.inform(iNetAddress);
						}
						else
						{
							System.out.println("null");
						}
					}
				}

				if (networkInterface.getInterfaceAddresses() != null)
				{
					System.out.println("InterfaceAddresses size : " + networkInterface.getInterfaceAddresses().size());
					for (final InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses())
					{
						System.out.println("Address : ");
						NetworkUtils.inform(interfaceAddress.getAddress());
						System.out.println("Broadcast : ");
						NetworkUtils.inform(interfaceAddress.getBroadcast());
						System.out.println("NetworkPrefixLength : " + interfaceAddress.getNetworkPrefixLength());
					}
				}
				System.out.println("LoopBack : " + networkInterface.isLoopback());
				System.out.println("PointToPoint : " + networkInterface.isPointToPoint());
				System.out.println("Up : " + networkInterface.isUp());
				System.out.println("Virtual : " + networkInterface.isVirtual());
				System.out.println("multicasts : " + networkInterface.supportsMulticast());

				/**
				 * if (networkInterface.getParent() != null) {
				 * System.out.println("ParentInterface : ");
				 * NetworkUtils.informNetworkInterface(networkInterface.getParent()); }
				 *
				 * if (networkInterface.getSubInterfaces() != null) {
				 * System.out.println("SubInterfaces : "); final var iterator =
				 * networkInterface.getSubInterfaces().asIterator(); while (iterator.hasNext())
				 * { final var subNetworkInterface = iterator.next(); if (subNetworkInterface !=
				 * null) { NetworkUtils.informNetworkInterface(subNetworkInterface); } } }
				 **/

			}
			catch (final SocketException e)
			{
				e.printStackTrace();
			}

		}
	}

	public final static int getPort()
	{
		return -1;
	}

	public static class Priority
	{
		private byte level;

		public Priority(final byte levelIn)
		{
			this.setLevel(levelIn);
		}

		public byte getLevel()
		{
			return this.level;
		}

		public void setLevel(final byte levelIn)
		{
			this.level = levelIn;
		}
	}
}