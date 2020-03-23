package com.l2jhellas.gameserver.scrips.quests;

import com.l2jhellas.gameserver.enums.player.ClassRace;
import com.l2jhellas.gameserver.model.actor.L2Npc;
import com.l2jhellas.gameserver.model.actor.instance.L2PcInstance;
import com.l2jhellas.gameserver.model.quest.Quest;
import com.l2jhellas.gameserver.model.quest.QuestState;

public class Q265_ChainsOfSlavery extends Quest
{
	private static final String qn = "Q265_ChainsOfSlavery";
	
	// Item
	private static final int SHACKLE = 1368;
	
	// Newbie Items
	private static final int SPIRITSHOT_FOR_BEGINNERS = 5790;
	private static final int SOULSHOT_FOR_BEGINNERS = 5789;
	
	public Q265_ChainsOfSlavery()
	{
		super(265, qn, "Chains of Slavery");
		
		setItemsIds(SHACKLE);
		
		addStartNpc(30357); // Kristin
		addTalkId(30357);
		
		addKillId(20004, 20005);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		QuestState st = player.getQuestState(qn);
		if (st == null)
			return htmltext;
		
		if (event.equalsIgnoreCase("30357-03.htm"))
		{
			st.setState(STATE_STARTED);
			st.set("cond", "1");
			st.playSound(QuestState.SOUND_ACCEPT);
		}
		else if (event.equalsIgnoreCase("30357-06.htm"))
		{
			st.playSound(QuestState.SOUND_FINISH);
			st.exitQuest(true);
		}
		
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(qn);
		String htmltext = getNoQuestMsg();
		if (st == null)
			return htmltext;
		
		switch (st.getState())
		{
			case STATE_CREATED:
				if (player.getRace() != ClassRace.DARK_ELF)
					htmltext = "30357-00.htm";
				else if (player.getLevel() < 6)
					htmltext = "30357-01.htm";
				else
					htmltext = "30357-02.htm";
				break;
			
			case STATE_STARTED:
				final int shackles = st.getQuestItemsCount(SHACKLE);
				if (shackles == 0)
					htmltext = "30357-04.htm";
				else
				{
					int reward = 12 * shackles;
					if (shackles > 10)
						reward += 500;
					
					htmltext = "30357-05.htm";
					st.takeItems(SHACKLE, -1);
					st.rewardItems(57, reward);
					
					if (player.isNewbie() && st.getInt("Reward") == 0)
					{
						st.showQuestionMark(26);
						st.set("Reward", "1");
						
						if (player.isMageClass())
						{
							st.playTutorialVoice("tutorial_voice_027");
							st.giveItems(SPIRITSHOT_FOR_BEGINNERS, 3000);
						}
						else
						{
							st.playTutorialVoice("tutorial_voice_026");
							st.giveItems(SOULSHOT_FOR_BEGINNERS, 6000);
						}
					}
				}
				break;
		}
		
		return htmltext;
	}
	
	@Override
	public String onKill(L2Npc npc, L2PcInstance player, boolean isPet)
	{
		QuestState st = checkPlayerState(player, npc, STATE_STARTED);
		if (st == null)
			return null;
		
		st.dropItems(SHACKLE, 1, 0, 330000);
		
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q265_ChainsOfSlavery();
	}
}