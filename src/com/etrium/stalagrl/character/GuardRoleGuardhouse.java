package com.etrium.stalagrl.character;

import com.badlogic.gdx.math.Vector2;
import com.etrium.stalagrl.Map;

public class GuardRoleGuardhouse extends GuardRole
{
	public GuardRoleGuardhouse(Map p_map, Character p_character)
	{
		SetMap(p_map);
		SetCharacter(p_character);
		
		pathCount = 1;
		
		paths = new PatrolPath[pathCount];
		patrols = new Patrol[1];
		
		PatrolPath path = new PatrolPath();
		path.nodeCount = 11;
		path.points = new Vector2[path.nodeCount];
		path.points[0] = new Vector2();
		path.points[0].x = 18+35;
		path.points[0].y = 13+65;
		
		path.points[1] = new Vector2();
		path.points[1].x = 18+35;
		path.points[1].y = 4+65;
		
		path.points[2] = new Vector2();
		path.points[2].x = 26+35;
		path.points[2].y = 6+65;
		
		path.points[3] = new Vector2();
		path.points[3].x = 26+35;
		path.points[3].y = 14+65;
		
		path.points[4] = new Vector2();
		path.points[4].x = 32+35;
		path.points[4].y = 14+65;
		
		path.points[5] = new Vector2();
		path.points[5].x = 31+35;
		path.points[5].y = 12+65;
		
		path.points[6] = new Vector2();
		path.points[6].x = 26+35;
		path.points[6].y = 14+65;
		
		path.points[7] = new Vector2();
		path.points[7].x = 26+35;
		path.points[7].y = 6+65;
		
		path.points[8] = new Vector2();
		path.points[8].x = 37+35;
		path.points[8].y = 9+65;
		
		path.points[9] = new Vector2();
		path.points[9].x = 42+35;
		path.points[9].y = 1+65;
		
		path.points[10] = new Vector2();
		path.points[10].x = 31+35;
		path.points[10].y = 1+65;
		
		
		
		paths[0] = path;
		
		patrols[0] = new Patrol();
		patrols[0].type = PatrolType.PATROL_PATH;
		
		followActivities = false;
		
		SetStartLocation();
	}
}
