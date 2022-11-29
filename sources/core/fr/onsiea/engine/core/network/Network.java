package fr.onsiea.engine.core.network;

import fr.onsiea.engine.core.network.packet.PacketType;

public class Network
{
	/**
	 * Meta packet
	 */
	public final static PacketType	DISCONNECT		= NetworkUtils.createType("position", NetworkUtils.HIGH, false);
	public final static PacketType	CONNECT			= NetworkUtils.createType("position", NetworkUtils.HIGH, false);

	/**
	 * Game packet
	 */
	public final static PacketType	POSITION		= NetworkUtils.createType("position", NetworkUtils.HIGH, false);
	public final static PacketType	ROTATION		= NetworkUtils.createType("position", NetworkUtils.HIGH, false);
	public final static PacketType	SNEAK			= NetworkUtils.createType("position", NetworkUtils.MIDDLE, false);
	public final static PacketType	JUMP			= NetworkUtils.createType("position", NetworkUtils.MIDDLE, false);
	public final static PacketType	USE				= NetworkUtils.createType("position", NetworkUtils.MIDDLE, false);
	public final static PacketType	HIT				= NetworkUtils.createType("position", NetworkUtils.MIDDLE, false);
	public final static PacketType	PLACE			= NetworkUtils.createType("position", NetworkUtils.MIDDLE, false);
	public final static PacketType	CHAT_MESSAGE	= NetworkUtils.createType("position", NetworkUtils.LOW, true);
}