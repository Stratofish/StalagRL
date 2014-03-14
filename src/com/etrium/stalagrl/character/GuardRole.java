package com.etrium.stalagrl.character;

import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.etrium.stalagrl.Map;
import com.etrium.stalagrl.system.Dijkstra;

public class GuardRole
{
	public class PatrolPath
	{
		public Vector2 points[];
		public int nodeCount;
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
		public String activity = "";
	}
	
	public String name;

	public PatrolPath paths[];
	public int pathCount;
	public int nextPath = 0;
	public int nextPathNode = 0;
	public List<PatrolRegion> areas;
	public int areaCount;
	public int NextArea = 0;
	
	public Patrol patrols[];
	public int curPatrol = 0;
	public Vector2 currentTarget = null;
	
	public boolean followActivities = true;
	public Map map;
	public Character character;
	public List<Vector2> route = null;
	
	public int x = 0;
	public int y = 0;
	
	public GuardRole()
	{
	}
	
	public GuardRole(Map p_map, Character p_character)
	{
		SetMap(p_map);
		SetCharacter(p_character);
		
		SetStartLocation();
	}
	
	public void SetMap(Map p_map)
	{
		map = p_map;
	}
	
	public void SetCharacter(Character p_character)
	{
		character = p_character;
	}
	
	public void SetStartLocation()
	{
		if (patrols[curPatrol].type == PatrolType.PATROL_PATH)
		{
			// TODO: check node isn't already being used as a start point
			int start = (int) (Math.random() * paths[nextPath].nodeCount);
			nextPathNode = start;
			x = (int) paths[nextPath].points[start].x;
			y = (int) paths[nextPath].points[start].y;
			character.SetPosition(x, y, map.floorMap[x][y].floorLevel);
			
			System.out.println("Starting at: " + start);
			
			nextPathNode++;

			if (nextPathNode >= paths[nextPath].nodeCount)
				nextPathNode = 0;
		}
	}
	
	public void DoControl()
	{
		x = character.x;
		y = character.y;
		
		if (route == null)
		{
			if (currentTarget == null)
			{ 
				if (patrols[curPatrol].type == PatrolType.PATROL_PATH)
				{
					PatrolPath path = paths[nextPath];
					currentTarget = path.points[nextPathNode];
					nextPathNode++;

					if (nextPathNode >= path.nodeCount)
					{
						nextPathNode = 0;
					}
				}
				
				Dijkstra dj = new Dijkstra(map.floorMap, map.width, map.height);
				int x2 = (int)currentTarget.x;
				int y2 = (int)currentTarget.y;
				
				route = dj.shortestPath(character.x, character.y, x2, y2);
				if (route.size() != 0)
					route.remove(0);
				else
				{
					System.out.println(x + ", " + y + " to " + x2 + ", " + y2);
				}
				
				currentTarget = null;
			}
		}
		
		if (route.size() == 0)
		{
			
		}
		
		int newX = (int)route.get(0).x;
		int newY = (int)route.get(0).y;

		float newRot = 0.0f;
		if (newY > y) newRot = 180.0f;
		if (newY < y) newRot = 0.0f;
		if (newX > x) newRot = 90.0f;
		if (newX < x) newRot = 270.0f;
		
		character.SetPosition(newX, newY, map.floorMap[newX][newY].floorLevel);
		character.instance.transform.rotate(0.0f,  0.0f,  1.0f, newRot);
		
		route.remove(0);
		if (route.size() == 0)
			route = null;
	}
}
