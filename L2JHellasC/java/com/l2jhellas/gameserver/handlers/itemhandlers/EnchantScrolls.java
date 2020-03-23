package com.l2jhellas.gameserver.handlers.itemhandlers;

import com.l2jhellas.gameserver.handler.IItemHandler;
import com.l2jhellas.gameserver.model.actor.L2Playable;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.actor.item.L2ItemInstance;
import com.l2jhellas.gameserver.network.SystemMessageId;
import com.l2jhellas.gameserver.network.serverpackets.ChooseInventoryItem;

public class EnchantScrolls implements IItemHandler
{
	
	private static final int[] ITEM_IDS =
	{
		729,
		730,
		731,
		732,
		6569,
		6570, // a grade
		947,
		948,
		949,
		950,
		6571,
		6572, // b grade
		951,
		952,
		953,
		954,
		6573,
		6574, // c grade
		955,
		956,
		957,
		958,
		6575,
		6576, // d grade
		959,
		960,
		961,
		962,
		6577,
		6578
	// s grade
	};
	
	@Override
	public void useItem(L2Playable playable, L2ItemInstance item)
	{
		if (!(playable instanceof L2PcInstance))
			return;
		final L2PcInstance activeChar = (L2PcInstance) playable;
		
		if (!activeChar.canEnchant() || activeChar.isCastingNow())
			return;
		
		if (activeChar.getActiveEnchantItem() == null)
			activeChar.sendPacket(SystemMessageId.SELECT_ITEM_TO_ENCHANT);
		
		activeChar.setActiveEnchantItem(item);
		activeChar.sendPacket(new ChooseInventoryItem(item.getItemId()));
	}
	
	@Override
	public int[] getItemIds()
	{
		return ITEM_IDS;
	}
}