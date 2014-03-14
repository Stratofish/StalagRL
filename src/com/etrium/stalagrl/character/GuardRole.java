package com.etrium.stalagrl.character;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class GuardRole
{
	public class PatrolPath
	{
		public Vector2 points[];
		public int curNode;
	}
	
	protected class PatrolRegion
	{
		public Vector2 bl;
		public Vector2 tr;
	}

	protected enum PatrolType
	{
		PATROL_PATH,
		PATROL_AREA
	}
	
	protected class Patrol
	{
		public PatrolType type;
	}
	
	public String name;
	
	public PatrolPath paths[];
	public int pathCount;
	public int nextPath = 0;
	public List<PatrolRegion> areas;
	public int areaCount;
	public int NextArea = 0;
	
	public Patrol patrols[];
	public int curPatrol = 0;
}
