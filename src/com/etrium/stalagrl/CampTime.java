package com.etrium.stalagrl;

import java.util.ArrayList;

import com.etrium.stalagrl.region.MapRegionType;
import com.etrium.stalagrl.system.EtriumEvent;
import com.etrium.stalagrl.system.EventListener;
import com.etrium.stalagrl.system.EventManager;
import com.etrium.stalagrl.system.EventType;

public class CampTime implements EventListener
{
	private static final int TIME_STEP = 1;
	
	private static final int DARK_STARTS = 19;
	private static final int DARK_ENDS = 7;
	private static final float DAY_LIGHT = 1.0f;
	private static final float NIGHT_LIGHT = 0.2f;
	
	public int hour = 9;
	public int minute = 0;
	protected int unifiedTime = 0;
	protected EventManager evtMgr = new EventManager();
	
	protected ArrayList<Activity> activities;
	protected Activity freeTimeActivity;
	
	public CampTime()
	{
		evtMgr.RegisterListener(this, EventType.evtTimeIncrease);
		
		freeTimeActivity = new Activity();
		freeTimeActivity.name = "Free time";
		freeTimeActivity.regionType = MapRegionType.FREE_TIME;
		
		activities = new ArrayList<Activity>();
		
		Activity activity = new Activity();
		activity.startTime = 7000;
		activity.leadTime = 1000;
		activity.name = "Morning roll call";
		activity.regionType = MapRegionType.ROLLCALL;
		activities.add(activity);
		
		activity = new Activity();
		activity.startTime = 8500;
		activity.leadTime = 1000;
		activity.name = "Breakfast";
		activities.add(activity);
		
		EtriumEvent evt = new EtriumEvent();
		evt.type = EventType.evtActivityStart;
		evt.data = freeTimeActivity;
		evtMgr.SendEvent(evt, true);
	}
	
	int GetUnifiedTime()
	{
		unifiedTime = (hour * 1000) + (int)(minute * 16.6667f);
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
		
		CheckActivities();
	}
	
	void RunDayNightCycle()
	{
		boolean customLight = false;
		float customLightLevel = DAY_LIGHT;
		
		// Dusk
		if (hour == DARK_STARTS)
		{
			int part = (int)(minute * 16.6667f);
			float range = DAY_LIGHT - NIGHT_LIGHT;
			
			customLightLevel = DAY_LIGHT - ((part * range) / 1000.0f);
			customLight = true;
		}
		
		// Dawn
		if (hour == DARK_ENDS-1)
		{
			int part = (int)(minute * 16.6667f);
			float range = DAY_LIGHT - NIGHT_LIGHT;
			
			customLightLevel = ((part * range) / 1000.0f) + NIGHT_LIGHT;
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
	
	void CheckActivities()
	{
		int time = GetUnifiedTime(); 
		Activity currentActivity = null; 
		EventType evtType = EventType.evtNull;
		
		int size = activities.size();
		for (int i = 0; i < size; i++)
		{
			Activity activity = activities.get(i);
			if ((activity.leadTime > 0) &&
				(time == (activity.startTime - activity.leadTime)))
			{
				currentActivity = activity;
				evtType = EventType.evtActivityLeadStart;
			}
			else if (time == (activity.startTime))
			{
				currentActivity = activity;
				evtType = EventType.evtActivityStart;
			}
			else if (time == (activity.startTime + activity.length))
			{
				currentActivity = freeTimeActivity;
				evtType = EventType.evtActivityStart;
			}
		}
		
		if (evtType != EventType.evtNull)
		{
			EtriumEvent evt = new EtriumEvent();
			evt.type = evtType;
			evt.data = currentActivity;
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
