package com.l2jhellas.gameserver.model.base;

import com.l2jhellas.gameserver.enums.player.ClassId;

public final class SubClass
{
	private ClassId _class;
	private final int _classIndex;
	private long _exp;
	private int _sp;
	private byte _level;
	
	public SubClass(int classId, int classIndex, long exp, int sp, byte level)
	{
		_class = ClassId.VALUES[classId];
		_classIndex = classIndex;
		_exp = exp;
		_sp = sp;
		_level = level;
	}
	
	public SubClass(int classId, int classIndex)
	{
		_class = ClassId.VALUES[classId];
		_classIndex = classIndex;
		_exp = Experience.LEVEL[40];
		_sp = 0;
		_level = 40;
	}
	
	public ClassId getClassDefinition()
	{
		return _class;
	}
	
	public int getClassId()
	{
		return _class.getId();
	}
	
	public void setClassId(int classId)
	{
		_class = ClassId.VALUES[classId];
	}
	
	public int getClassIndex()
	{
		return _classIndex;
	}
	
	public long getExp()
	{
		return _exp;
	}
	
	public void setExp(long exp)
	{
		if (exp > Experience.LEVEL[Experience.MAX_LEVEL])
			exp = Experience.LEVEL[Experience.MAX_LEVEL];
		
		_exp = exp;
	}
	
	public int getSp()
	{
		return _sp;
	}
	
	public void setSp(int sp)
	{
		_sp = sp;
	}
	
	public byte getLevel()
	{
		return _level;
	}
	
	public void setLevel(byte level)
	{
		if (level > (Experience.MAX_LEVEL - 1))
			level = (Experience.MAX_LEVEL - 1);
		else if (level < 40)
			level = 40;
		
		_level = level;
	}
}