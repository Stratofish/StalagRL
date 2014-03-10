package com.etrium.stalagrl.system;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

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
  protected int graph[][]; 
  
  public Dijkstra( int floorMap[][])
  {
    graph = floorMap;
  }
  
  public List shortestPath( int x1, int y1, int x2, int y2)
  {
    int dist[] = new int[10*10];
    int previous[] = new int[10*10];
    
    DijkstraNode q[] = new DijkstraNode[10*10]; 
    
    for (int y = 0; y < 10; y++)
    {      
      for (int x = 0; x < 10; x++)
      {
        DijkstraNode node =  new DijkstraNode();
        node.x = x;
        node.y = y;
        node.neighbours = new int[4];        
        
        node.neighbours[0] = -1;
        node.neighbours[1] = -1;
        node.neighbours[2] = -1;
        node.neighbours[3] = -1;
        
        if (y < 9) 
          node.neighbours[0] = ((y+1) * 10) + x;
                
        if (y > 0) 
          node.neighbours[1] = ((y-1) * 10) + x;
                
        if (x < 9) 
          node.neighbours[2] = (y * 10) + (x + 1);
        
        if (x > 0) 
          node.neighbours[3] = (y * 10) + (x - 1);
        
        dist[(y * 10) + x] = 0xfffffff;
        q[(y * 10) + x] = node;
        previous[(y * 10) + x] = -1; 
      }
    }
    
    dist[(y1 * 10) + x1] = 0;
    
    int nodesLeft = 10 * 10;
    while (nodesLeft > 0)
    {
      int u = -1;
      int udist = 0xfffffff;
      
      for (int i = 0; i < 10*10; i++)
      {
        if (dist[i] < udist)
          u = i;
      }
      
      if (u == -1){
        break;
      }
      
      q[u].done = true;
      nodesLeft--;
      
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
    }
    
    List s = new ArrayList<Vector2>();
    int u = (y2 * 10) + x2;
    while (previous[u] != -1)
    {
      Vector2 vec = new Vector2();
      vec.x = q[previous[u]].x;
      vec.y = q[previous[u]].y;
      s.add(0, vec);
      u = previous[u];
    }
    
    return s;       
  }
}
