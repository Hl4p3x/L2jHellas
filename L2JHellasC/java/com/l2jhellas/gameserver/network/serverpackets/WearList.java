package com.l2jhellas.gameserver.network.serverpackets;

import java.util.List;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.model.L2TradeList;
import com.l2jhellas.gameserver.model.actor.item.L2Item;
import com.l2jhellas.gameserver.model.actor.item.L2ItemInstance;

public class WearList extends L2GameServerPacket
{
	private static final String _S__EF_WEARLIST = "[S] EF WearList";
	private final int _listId;
	private final L2ItemInstance[] _list;
	private final int _money;
	private int _expertise;
	
	public WearList(L2TradeList list, int currentMoney, int expertiseIndex)
	{
		_listId = list.getListId();
		List<L2ItemInstance> lst = list.getItems();
		_list = lst.toArray(new L2ItemInstance[lst.size()]);
		_money = currentMoney;
		_expertise = expertiseIndex;
	}
	
	public WearList(List<L2ItemInstance> lst, int listId, int currentMoney)
	{
		_listId = listId;
		_list = lst.toArray(new L2ItemInstance[lst.size()]);
		_money = currentMoney;
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0xef);
		writeC(0xc0); // ?
		writeC(0x13); // ?
		writeC(0x00); // ?
		writeC(0x00); // ?
		writeD(_money); // current money
		writeD(_listId);
		
		int newlength = 0;
		for (L2ItemInstance item : _list)
		{
			if (item.getItem().getCrystalType().getId() <= _expertise && item.isEquipable())
				newlength++;
		}
		writeH(newlength);
		
		for (L2ItemInstance item : _list)
		{
			if (item.getItem().getCrystalType().getId() <= _expertise && item.isEquipable())
			{
				writeD(item.getItemId());
				writeH(item.getItem().getType2());// item type2
				
				if (item.getItem().getType1() != L2Item.TYPE1_ITEM_QUESTITEM_ADENA)
				{
					writeH(item.getItem().getBodyPart());// slot 0006-lr.ear 0008-neck 0030-lr.finger 0040-head 0080-?? 0100-l.hand 0200-gloves 0400-chest 0800-pants 1000-feet 2000-?? 4000-r.hand 8000-r.hand
				}
				else
				{
					writeH(0x00);// slot 0006-lr.ear 0008-neck 0030-lr.finger 0040-head 0080-?? 0100-l.hand 0200-gloves 0400-chest 0800-pants 1000-feet 2000-?? 4000-r.hand
					// 8000-r.hand
				}
				writeD(Config.WEAR_PRICE);
			}
		}
	}
	
	@Override
	public String getType()
	{
		return _S__EF_WEARLIST;
	}
}