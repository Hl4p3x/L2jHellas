package Extensions.RankSystem;

import java.util.HashMap;
import java.util.Map;

import Extensions.RankSystem.Util.RPSUtil;

import com.l2jhellas.Config;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.actor.item.Inventory;
import com.l2jhellas.gameserver.model.actor.item.L2ItemInstance;
import com.l2jhellas.gameserver.network.serverpackets.NpcHtmlMessage;

public class RPSHtmlDeathStatus
{
	// store killer data, (anti-change target protection).
	// data is updating on every kill.
	
	private int _killerObjectId = 0;
	private int _killerLevel = 0;
	private int _killerClassId = 0;
	private int _killerCurrentCP = 0;
	private int _killerCurrentHP = 0;
	private int _killerCurrentMP = 0;
	private int _killerMaxCP = 0;
	private int _killerMaxHP = 0;
	private int _killerMaxMP = 0;
	private String _killerName = null;
	
	private Map<Integer, KillerItem> _killerItems = new HashMap<>();
	
	private boolean _valid = false;
	
	public RPSHtmlDeathStatus(L2PcInstance killer)
	{
		if (killer == null)
			return;
		
		_killerObjectId = killer.getObjectId();
		_killerLevel = killer.getLevel();
		_killerClassId = killer.getClassId().getId();
		_killerCurrentCP = (int) killer.getCurrentCp();
		_killerCurrentHP = (int) killer.getCurrentHp();
		_killerCurrentMP = (int) killer.getCurrentMp();
		
		_killerMaxCP = killer.getMaxCp();
		_killerMaxHP = killer.getMaxHp();
		_killerMaxMP = killer.getMaxMp();
		
		_killerName = killer.getName();
		
		// load item killer list:
		if (Config.DEATH_MANAGER_SHOW_ITEMS_ENABLED)
		{
			_killerItems = getKillerItems(killer);
		}
		
		setValid(true);
	}
	
	public void sendPage(L2PcInstance victim)
	{
		NpcHtmlMessage n = new NpcHtmlMessage(0);
		
		n.setHtml(preparePage());
		
		victim.sendPacket(n);
	}
	
	private String preparePage()
	{
		String tb = "";
		String tb_weapon = "";
		String tb_armor = "";
		String tb_jewel = "";
		String tb_other = "";
		
		tb += "<html><title>" + _killerName + " Equipment informations</title><body><center>";
		
		tb += "<table width=270 border=0 cellspacing=0 cellpadding=2 bgcolor=000000>";
		tb += "<tr><td width=270 height=18 align=center><font color=ae9977>Killer Name (lvl):</font> " + _killerName + " (" + _killerLevel + ")</td></tr>";
		tb += "<tr><td width=270 height=18 align=center><font color=ae9977>Killer Class:</font> " + RPSUtil.getClassName(_killerClassId) + "</td></tr>";
		tb += "<tr><td FIXWIDTH=270 HEIGHT=4><img src=\"L2UI.Squaregray\" width=\"270\" height=\"1\"></img></td></tr>";
		tb += "</table>";
		
		tb += "<table width=270 border=0 cellspacing=0 cellpadding=2 bgcolor=000000>";
		tb += "<tr><td width=270 height=18 align=center><font color=ae9977>CP:</font> <font color=FFF000>" + _killerCurrentCP + " / " + _killerMaxCP + "</font></td></tr>";
		tb += "<tr><td width=270 height=18 align=center><font color=ae9977>HP:</font> <font color=FF0000>" + _killerCurrentHP + " / " + _killerMaxHP + "</font><font color=ae9977>, MP:</font> <font color=2080D0>" + _killerCurrentMP + "/" + _killerMaxMP + "</font></td></tr>";
		tb += "<tr><td FIXWIDTH=270 HEIGHT=4><img src=\"L2UI.Squaregray\" width=\"270\" height=\"1\"></img></td></tr>";
		tb += "<tr><td width=270 height=12></td></tr>";
		tb += "</table>";
		
		// show item list:
		if (Config.DEATH_MANAGER_SHOW_ITEMS_ENABLED)
		{
			tb += "<table width=270 border=0 cellspacing=0 cellpadding=2 bgcolor=000000>";
			
			// create groups headers:
			tb_weapon += "<tr><td width=270 height=18 align=center><font color=2080D0>Weapon / Shield</font></td></tr>";
			tb_armor += "<tr><td width=270 height=18 align=center><font color=2080D0>Armor</font></td></tr>";
			tb_jewel += "<tr><td width=270 height=18 align=center><font color=2080D0>Jewellery</font></td></tr>";
			tb_other += "<tr><td width=270 height=18 align=center><font color=2080D0>Other</font></td></tr>";
			
			// create group separator:
			tb_weapon += "<tr><td FIXWIDTH=270 HEIGHT=3><img src=\"L2UI.Squaregray\" width=\"270\" height=\"1\"></img></td></tr>";
			tb_armor += "<tr><td FIXWIDTH=270 HEIGHT=3><img src=\"L2UI.Squaregray\" width=\"270\" height=\"1\"></img></td></tr>";
			tb_jewel += "<tr><td FIXWIDTH=270 HEIGHT=3><img src=\"L2UI.Squaregray\" width=\"270\" height=\"1\"></img></td></tr>";
			tb_other += "<tr><td FIXWIDTH=270 HEIGHT=3><img src=\"L2UI.Squaregray\" width=\"270\" height=\"1\"></img></td></tr>";
			
			// add items to groups:
			for (Map.Entry<Integer, KillerItem> e : _killerItems.entrySet())
			{
				if (e.getValue()._group == KillerItem.GROUP_WEAPON)
				{
					tb_weapon += "<tr><td width=270 height=16 align=center><font color=808080>" + e.getValue()._itemName + " (</font><font color=FF8000>+" + e.getValue()._itemEnchantLevel + "</font><font color=808080>)</font></td></tr>";
					tb_weapon += "<tr><td width=270 HEIGHT=3><img src=\"L2UI.Squaregray\" width=\"270\" height=\"1\"></img></td></tr>";
				}
				else if (e.getValue()._group == KillerItem.GROUP_ARMOR)
				{
					tb_armor += "<tr><td width=270 height=16 align=center><font color=808080>" + e.getValue()._itemName + " (</font><font color=FF8000>+" + e.getValue()._itemEnchantLevel + "</font><font color=808080>)</font></td></tr>";
					tb_armor += "<tr><td width=270 HEIGHT=3><img src=\"L2UI.Squaregray\" width=\"270\" height=\"1\"></img></td></tr>";
				}
				else if (e.getValue()._group == KillerItem.GROUP_JEWEL)
				{
					tb_jewel += "<tr><td width=270 height=16 align=center><font color=808080>" + e.getValue()._itemName + " (</font><font color=FF8000>+" + e.getValue()._itemEnchantLevel + "</font><font color=808080>)</font></td></tr>";
					tb_jewel += "<tr><td width=270 HEIGHT=3><img src=\"L2UI.Squaregray\" width=\"270\" height=\"1\"></img></td></tr>";
				}
				else
				// KillerItem.GROUP_OTHER
				{
					tb_other += "<tr><td width=270 height=16 align=center><font color=808080>" + e.getValue()._itemName + " (</font><font color=FF8000>+" + e.getValue()._itemEnchantLevel + "</font><font color=808080>)</font></td></tr>";
					tb_other += "<tr><td width=270 HEIGHT=3><img src=\"L2UI.Squaregray\" width=\"270\" height=\"1\"></img></td></tr>";
				}
			}
			
			// add to head TB generated TB's:
			tb += tb_weapon;
			tb += tb_armor;
			tb += tb_jewel;
			tb += tb_other;
			
			tb += "</table>";
		}
		
		// footer and back button:
		tb += "<table border=0 cellspacing=0 cellpadding=0>";
		tb += "<tr><td width=270 height=12 align=center><font color=808080>- killer's state in kill moment -</font></td></tr>";
		tb += "<tr><td width=270 height=12></td></tr>";
		tb += "<tr><td width=270 align=center><button value=\"Back\" action=\"bypass -h RPS.PS\"  width=" + Config.BUTTON_W + " height=" + Config.BUTTON_H + " back=\"" + Config.BUTTON_DOWN + "\" fore=\"" + Config.BUTTON_UP + "\"></td></tr>";
		tb += "</table>";
		
		tb += "</center></body></html>";
		
		return tb;
	}
	
	private Map<Integer, KillerItem> getKillerItems(L2PcInstance killer)
	{
		Map<Integer, KillerItem> items = new HashMap<>();
		
		for (int j = 0; j < Inventory.PAPERDOLL_TOTALSLOTS; j++)
		{
			L2ItemInstance item = killer.getInventory().getPaperdollItem(j);
			
			if (item != null && (j == Inventory.PAPERDOLL_LHAND || j == Inventory.PAPERDOLL_RHAND))
			{
				items.put(j, new KillerItem(item, KillerItem.GROUP_WEAPON));
			}
			else if (item != null && (j == Inventory.PAPERDOLL_HEAD || j == Inventory.PAPERDOLL_CHEST || j == Inventory.PAPERDOLL_GLOVES || j == Inventory.PAPERDOLL_LEGS || j == Inventory.PAPERDOLL_FEET))
			{
				items.put(j, new KillerItem(item, KillerItem.GROUP_ARMOR));
			}
			else if (item != null && (j == Inventory.PAPERDOLL_NECK || j == Inventory.PAPERDOLL_REAR || j == Inventory.PAPERDOLL_LEAR || j == Inventory.PAPERDOLL_RFINGER || j == Inventory.PAPERDOLL_LFINGER))
			{
				items.put(j, new KillerItem(item, KillerItem.GROUP_JEWEL));
			}
			else if (item != null)
			{
				items.put(j, new KillerItem(item, KillerItem.GROUP_OTHER));
			}
		}
		
		return items;
	}
	
	public boolean isValid()
	{
		return _valid;
	}
	
	public void setValid(boolean valid)
	{
		_valid = valid;
	}
	
	public int getKillerObjectId()
	{
		return _killerObjectId;
	}
	
	public void setKillerObjectId(int killerObjectId)
	{
		_killerObjectId = killerObjectId;
	}
	
	class KillerItem
	{
		int _itemObjId = 0;
		String _itemName = null;
		int _itemEnchantLevel = 0;
		byte _group = GROUP_OTHER;
		
		public static final byte GROUP_WEAPON = 1;
		public static final byte GROUP_ARMOR = 2;
		public static final byte GROUP_JEWEL = 3;
		public static final byte GROUP_OTHER = 4;
		
		public KillerItem(L2ItemInstance itemInstance, byte group)
		{
			_itemObjId = itemInstance.getObjectId();
			_itemName = itemInstance.getItemName();
			_itemEnchantLevel = itemInstance.getEnchantLevel();
			_group = group;
		}
	}
}