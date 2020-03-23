package com.l2jhellas.gameserver.scrips.quests.ai.group;

import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.network.serverpackets.CreatureSay;
import com.l2jhellas.gameserver.scrips.quests.ai.AbstractNpcAI;
import com.l2jhellas.util.Rnd;

public class OlMahumGeneral extends AbstractNpcAI
{
	private static final int MAHUM = 20438;
	
	private static boolean _FirstAttacked;
	
	public OlMahumGeneral()
	{
		super("OlMahumGeneral", "ai");
		int[] mobs =
		{
			MAHUM
		};
		registerMobs(mobs);
		_FirstAttacked = false;
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance attacker, int damage, boolean isPet)
	{
		if (npc.getNpcId() == MAHUM)
		{
			if (_FirstAttacked)
			{
				if (Rnd.get(100) == 100)
					attacker.sendPacket(new CreatureSay(npc.getObjectId(), 0, npc.getName(), "We shall see about that!"));
			}
			else
			{
				_FirstAttacked = true;
				attacker.sendPacket(new CreatureSay(npc.getObjectId(), 0, npc.getName(), "I will definitely repay this humiliation!"));
			}
		}
		return super.onAttack(npc, attacker, damage, isPet);
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
	{
		int npcId = npc.getNpcId();
		if (npcId == MAHUM)
		{
			_FirstAttacked = false;
		}
		return super.onKill(npc, killer, isPet);
	}
	
	public static void main(String[] args)
	{
		new OlMahumGeneral();
	}
}