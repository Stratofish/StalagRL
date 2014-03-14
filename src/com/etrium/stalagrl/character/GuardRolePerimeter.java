package com.etrium.stalagrl.character;

import com.badlogic.gdx.math.Vector2;

public class GuardRolePerimeter extends GuardRole
{
	public GuardRolePerimeter()
	{
		pathCount = 1;
		
		paths = new PatrolPath[pathCount];
		patrols = new Patrol[1];
		
		PatrolPath path = new PatrolPath();
		path.points = new Vector2[6];
		path.points[0] = new Vector2();
		path.points[0].x = 3;
		path.points[0].y = 58;
		
		path.points[1] = new Vector2();
		path.points[1].x = 3;
		path.points[1].y = 3;
		
		path.points[2] = new Vector2();
		path.points[2].x = 58;
		path.points[2].y = 3;
		
		path.points[3] = new Vector2();
		path.points[3].x = 58;
		path.points[3].y = 58;
		
		path.points[4] = new Vector2();
		path.points[4].x = 3;
		path.points[4].y = 3;
		
		path.points[5] = new Vector2();
		path.points[5].x = 58;
		path.points[5].y = 3;
		
		path.curNode = 0;
		
		paths[0] = path;
		
		patrols[0] = new Patrol();
		patrols[0].type = PatrolType.PATROL_PATH;
	}
}
