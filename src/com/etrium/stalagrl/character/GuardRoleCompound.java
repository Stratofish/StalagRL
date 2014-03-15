package com.etrium.stalagrl.character;

import com.badlogic.gdx.math.Vector2;
import com.etrium.stalagrl.Map;

public class GuardRoleCompound extends GuardRole
{
	public GuardRoleCompound(Map p_map, Character p_character)
	{
		SetMap(p_map);
		SetCharacter(p_character);
		
		pathCount = 1;
		
		paths = new PatrolPath[pathCount];
		patrols = new Patrol[1];
		
		PatrolPath path = new PatrolPath();
		path.nodeCount = 4;
		path.points = new Vector2[path.nodeCount];
		path.points[0] = new Vector2();
		path.points[0].x = 37;
		path.points[0].y = 17;
		
		path.points[1] = new Vector2();
		path.points[1].x = 82;
		path.points[1].y = 17;
		
		path.points[2] = new Vector2();
		path.points[2].x = 82;
		path.points[2].y = 64;
		
		path.points[3] = new Vector2();
		path.points[3].x = 37;
		path.points[3].y = 64;
		
		paths[0] = path;
		
		patrols[0] = new Patrol();
		patrols[0].type = PatrolType.PATROL_PATH;
		
		followActivities = false;
		
		SetStartLocation();
	}
}
