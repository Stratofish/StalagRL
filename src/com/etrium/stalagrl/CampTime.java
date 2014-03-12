package com.etrium.stalagrl;

import com.etrium.stalagrl.system.EtriumEvent;
import com.etrium.stalagrl.system.EventListener;
import com.etrium.stalagrl.system.EventManager;
import com.etrium.stalagrl.system.EventType;

public class CampTime implements EventListener
{
	private static final int TIME_STEP = 10;
	
	private static final int DARK_STARTS = 19;
	private static final int DARK_ENDS = 7;
	private static final float DAY_LIGHT = 1.0f;
	private static final float NIGHT_LIGHT = 0.4f;
	
	public int hour = 9;
	public int minute = 0;
	protected float unifiedTime = 0.0f;
	protected float minuteFraction = 1.0f / 60.0f; 
	protected EventManager evtMgr = new EventManager();
	
	public CampTime()
	{
		evtMgr.RegisterListener(this, EventType.evtTimeIncrease);
	}
	
	float GetUnifiedTime()
	{
		unifiedTime = hour + (minuteFraction * (float)minute);
		
		return unifiedTime;
	}
	
	void AddMinutes(int minutes)
	{
		minute += minutes;
		while (minute > 59)
		{
			minute -= 60;
			hour++;
		}
		
		while (hour > 23)
		{
			hour -= 24;
		}
		
		System.out.println(hour + ":" + minute);
		
		RunSchedule();
	}
	
	protected void RunSchedule()
	{
		RunDayNightCycle();
	}
	
	void RunDayNightCycle()
	{
		boolean customLight = false;
		float customLightLevel = DAY_LIGHT;
		
		// Dusk
		if (hour == DARK_STARTS)
		{
			float part = minuteFraction * (float)minute;
			float range = DAY_LIGHT - NIGHT_LIGHT;
			
			customLightLevel = DAY_LIGHT - (part * range);
			customLight = true;
		}
		
		// Dawn
		if (hour == DARK_ENDS-1)
		{
			float part = minuteFraction * (float)minute;
			float range = DAY_LIGHT - NIGHT_LIGHT;
			
			customLightLevel = (part * range) + NIGHT_LIGHT;
			customLight = true;
		}
		
		// Night
		if ((hour > DARK_STARTS) ||
			(hour < DARK_ENDS-1))
		{
			customLightLevel = NIGHT_LIGHT;
			customLight = true;
		}
		
		// Day
		if (hour == DARK_ENDS)
		{
			customLightLevel = DAY_LIGHT;
			customLight = true;
		}

		if (customLight)
		{
			EtriumEvent evt = new EtriumEvent();
			evt.type = EventType.evtGlobalLightLevel;
			evt.data = customLightLevel;
			evtMgr.SendEvent(evt, true);
		}
	}
	
	void SetTime(int hours, int minutes)
	{
		minute = minutes;
		hour = hours;
		
		RunSchedule();
	}

	@Override
	public boolean ReceiveEvent(EtriumEvent p_event)
	{
		if (p_event.type == EventType.evtTimeIncrease)
		{
			AddMinutes(TIME_STEP);
			return true;
		}
		
		return false;
	}

	@Override
	public void StartListening()
	{
	}

	@Override
	public void StopListening()
	{
	}
}
