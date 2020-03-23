package com.l2jhellas.gameserver.network.serverpackets;

public class ExConfirmCancelItem extends L2GameServerPacket
{
	private static final String _S__FE_56_EXCONFIRMCANCELITEM = "[S] FE:56 ExConfirmCancelItem";
	
	private final int _itemObjId;
	private final int _price;
	
	public ExConfirmCancelItem(int itemObjId, int price)
	{
		_itemObjId = itemObjId;
		_price = price;
	}
	
	@Override
	protected void writeImpl()
	{
		writeC(0xfe);
		writeH(0x56);
		writeD(0x40A97712);
		writeD(_itemObjId);
		writeD(0x27);
		writeD(0x2006);
		writeQ(_price);
		writeD(0x01);
	}
	
	@Override
	public String getType()
	{
		return _S__FE_56_EXCONFIRMCANCELITEM;
	}
}