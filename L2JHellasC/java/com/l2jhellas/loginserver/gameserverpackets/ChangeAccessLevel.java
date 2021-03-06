package com.l2jhellas.loginserver.gameserverpackets;

import com.l2jhellas.loginserver.clientpackets.ClientBasePacket;

public class ChangeAccessLevel extends ClientBasePacket
{
	private final int _level;
	private final String _account;
	
	public ChangeAccessLevel(byte[] decrypt)
	{
		super(decrypt);
		_level = readD();
		_account = readS();
	}
	
	public String getAccount()
	{
		return _account;
	}
	
	public int getLevel()
	{
		return _level;
	}
}