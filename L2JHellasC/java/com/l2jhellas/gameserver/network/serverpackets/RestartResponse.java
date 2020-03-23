package com.l2jhellas.gameserver.network.serverpackets;

public class RestartResponse extends L2GameServerPacket
{
	private static final String _S__74_RESTARTRESPONSE = "[S] 74 RestartResponse";
	private static final RestartResponse STATIC_PACKET_TRUE = new RestartResponse(true);
	private static final RestartResponse STATIC_PACKET_FALSE = new RestartResponse(false);
	
	public static final RestartResponse valueOf(boolean result)
	{
		return result ? STATIC_PACKET_TRUE : STATIC_PACKET_FALSE;
	}
	
	private final boolean _result;
	
	public RestartResponse(boolean result)
	{
		_result = result;
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0x5f);
		writeD(_result ? 1 : 0);
	}
	
	@Override
	public String getType()
	{
		return _S__74_RESTARTRESPONSE;
	}
	
}