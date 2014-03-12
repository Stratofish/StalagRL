package com.etrium.stalagrl.system;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.etrium.stalagrl.MapCell;

 //From wikipedia - http://en.wikipedia.org/wiki/Dijkstra's_algorithm
 //
 //function Dijkstra(Graph, source):
 //2      for each vertex v in Graph:                                // Initializations
 //3          dist[v]  := infinity ;                                  // Unknown distance function from 
 //4                                                                 // source to v
 //5          previous[v]  := undefined ;                             // Previous node in optimal path
 //6      end for                                                    // from source
 //7      
 //8      dist[source]  := 0 ;                                        // Distance from source to source
 //9      Q := the set of all nodes in Graph ;                       // All nodes in the graph are
 //10                                                                 // unoptimized – thus are in Q
 //11      while Q is not empty:                                      // The main loop
 //12          u := vertex in Q with smallest distance in dist[] ;    // Source node in first case
 //13          remove u from Q ;
 //14          if dist[u] = infinity:
 //15              break ;                                            // all remaining vertices are
 //16          end if                                                 // inaccessible from source
 //17          
 //18          for each neighbor v of u:                              // where v has not yet been 
 //19                                                                 // removed from Q.
 //20              alt := dist[u] + dist_between(u, v) ;
 //21              if alt < dist[v]:                                  // Relax (u,v,a)
 //22                  dist[v]  := alt ;
 //23                  previous[v]  := u ;
 //24                  decrease-key v in Q;                           // Reorder v in the Queue
 //25              end if
 //26          end for
 //27      end while
 //28      return dist;
 //29  end function

public class Dijkstra
{
  protected MapCell map[][];
  protected int width;
  protected int height;
  protected int nodeCount;
  
  public Dijkstra( MapCell floorMap[][], int p_width, int p_height)
  {
    map = floorMap;
    width = p_width;
    height = p_height;
    nodeCount = width * height;
  }
  
  public List<Vector2> shortestPath( int x1, int y1, int x2, int y2)
  {
    int dist[] = new int[nodeCount];
    int previous[] = new int[nodeCount];
    
    DijkstraNode q[] = new DijkstraNode[nodeCount]; 
    
    for (int y = 0; y < height; y++)
    {      
      for (int x = 0; x < width; x++)
      {
        DijkstraNode node =  new DijkstraNode();
        node.x = x;
        node.y = y;
        node.neighbours = new int[4];        
        
        node.neighbours[0] = -1;
        node.neighbours[1] = -1;
        node.neighbours[2] = -1;
        node.neighbours[3] = -1;

        if ((y < (height-1)) &&
        	((map[x][y].collision & MapCell.NORTH) == 0) &&
        	((map[x][y+1].collision & MapCell.SOUTH) == 0))
          node.neighbours[0] = ((y+1) * width) + x;
                
        if ((y > 0) &&
        	((map[x][y].collision & MapCell.SOUTH) == 0) &&
            ((map[x][y-1].collision & MapCell.NORTH) == 0))
          node.neighbours[1] = ((y-1) * width) + x;
                
        if ((x < (width-1)) &&
        	((map[x][y].collision & MapCell.EAST) == 0) &&
            ((map[x+1][y].collision & MapCell.WEST) == 0))
          node.neighbours[2] = (y * width) + (x + 1);
        
        if ((x > 0) &&
        	((map[x][y].collision & MapCell.WEST) == 0) &&
            ((map[x-1][y].collision & MapCell.EAST) == 0))
          node.neighbours[3] = (y * width) + (x - 1);
        
        dist[(y * width) + x] = 0xfffffff;
        q[(y * width) + x] = node;
        previous[(y * width) + x] = -1; 
      }
    }
    
    dist[(y1 * width) + x1] = 0;
    
    int nodesLeft = nodeCount;
    while (nodesLeft > 0)
    {
      int u = -1;
      int udist = 0xfffffff;
      
      for (int i = 0; i < nodeCount; i++)
      {
    	  if ((dist[i] < udist) && (!q[i].done))
    	  {
    		  u = i;
    		  udist = dist[i];
    	  }
      }

      if (u == -1){      
        break;
      }
      
      if ((q[u].x == x2) &&
    	  (q[u].y == y2))
      {
    	  break;
      }
      
      if (dist[u] == 0xfffffff)
      {       
        break;
      }
      
      for (int v = 0; v < 4; v++)
      {
        int neighbour = q[u].neighbours[v];
       
        if (neighbour != -1)
        {          
          int alt = dist[u] + 1;    
          if (alt < dist[neighbour])
          {
            dist[neighbour] = alt;
            previous[neighbour] = u;
          }
        }               
      }
      
      q[u].done = true;
      nodesLeft--;
    }      
    
    
    
    ArrayList<Vector2> s = new ArrayList<Vector2>();
    
    int u = (y2 * width) + x2;
    
    Vector2 vec;
        
    while (previous[u] != -1)
    {
      vec = new Vector2();
      vec.x = q[previous[u]].x;
      vec.y = q[previous[u]].y;
      s.add(0, vec);
      u = previous[u];
    }
    
    if (s.size() > 0)
    {
    	vec = new Vector2();
        vec.x = q[u].x;
        vec.y = q[u].y;
        s.add(0, vec);
    }
    
    return s;       
  }
}
