package com.l2jhellas.gameserver.skills.funcsCalc;

import com.l2jhellas.gameserver.model.actor.L2Character;
import com.l2jhellas.gameserver.skills.Env;
import com.l2jhellas.gameserver.skills.Formulas;
import com.l2jhellas.gameserver.skills.Stats;
import com.l2jhellas.gameserver.skills.funcs.Func;

public class FuncMaxHpMul extends Func
{
	static final FuncMaxHpMul _fmhm_instance = new FuncMaxHpMul();
	
	public static Func getInstance()
	{
		return _fmhm_instance;
	}
	
	private FuncMaxHpMul()
	{
		super(Stats.MAX_HP, 0x20, null);
	}
	
	@Override
	public void calc(Env env)
	{
		L2Character p = env.player;
		env.value *= Formulas.CONbonus[p.getCON()];
	}
}