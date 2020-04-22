/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opengl;

import javax.media.opengl.GL2;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author sajib
 */
public class CircleDraw  implements GLEventListener {
    /**
     * Interface to the GLU library.
     */
    private GLU glu;

    /**
     * Take care of initialization here.
     * @param gld
     */
    @Override
    public void init(GLAutoDrawable gld) {
        GL2 gl = gld.getGL().getGL2();
        glu = new GLU();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glViewport(-250, -150, 250, 150);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(-250.0, 250.0, -150.0, 150.0);
    }

    /**
     * Take care of drawing here.
     * @param drawable
     */
    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        
        LineDraw ld = new LineDraw();     
        Scanner sc = new Scanner(System.in);
        
        
        int inp = sc.nextInt();
        ArrayList<Integer> arr = new ArrayList<Integer>();
        arr.add(0);
        for(int i=1; i<=inp; i++) {
        	arr.add(i*10);
        }
      
        System.out.println(arr);

                       
        int depth=inp/2;
               
        if(depth == 0) {
        	int i = (arr.size() -1) - depth;
    		i = arr.get(i);
    		
    		ld.DrawMPL(gl, i, i, i, -i);   	//right 
            ld.DrawMPL(gl, -i, i, -i,-i);   //left
            
            ld.DrawMPL(gl,-i,i,i,i);  		//top 
            ld.DrawMPL(gl,-i,-i,i,-i); 	 	//bottom
        }
        
        else if((inp%2) == 1) {
    		int i = (arr.size() -1) - depth;
    		i = arr.get(i);
    		
    		ld.DrawMPL(gl, i, i, i, -i);   	//right 
            ld.DrawMPL(gl, -i, i, -i,-i);   //left
            
            ld.DrawMPL(gl,-i,i,i,i);  		//top 
            ld.DrawMPL(gl,-i,-i,i,-i); 	 	//bottom
            
            int j = arr.size()-1;
            
            for (int d = 1; d <= depth;) {
            	
                	i = arr.get(j);
                	
                	ld.DrawMPL(gl, i, i, i, -i);   	//right 
                    ld.DrawMPL(gl, -i, i, -i,-i);   //left
                    
                    ld.DrawMPL(gl,-i,i,i,i);  		//top 
                    ld.DrawMPL(gl,-i,-i,i,-i); 	 	//bottom
                    
            		drawMPC(gl,i);  				//circle  
            	
            	j--;
            	d++;
           
            }
        }
        else if(inp%2 == 0) {
    	int j = arr.size()-1;
    
        for (int d = 1; d <= depth;) {
        	
            	int i = arr.get(j);
            	
            	ld.DrawMPL(gl, i, i, i, -i);   	//right 
                ld.DrawMPL(gl, -i, i, -i,-i);   //left
                
                ld.DrawMPL(gl,-i,i,i,i);  		//top 
                ld.DrawMPL(gl,-i,-i,i,-i); 	 	//bottom
                
        		drawMPC(gl,i);  				//circle  
        	
        	j--;
        	d++;
       
        }
        
    	}
    	

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        //do nothing
    
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
        //do nothing
    	
    }
    
    public void drawMPC(GL2 gl, int r){
    	
    	gl.glPointSize(3.0f);
        gl.glColor3d(1, 0, 0);              
        gl.glBegin(GL2.GL_POINTS); 
        

        
        int x = r;
        int y = 0;
        int d = 5-4*r;
        
        drawPixel8Way(gl,x,y);
        while(y<x) {
        	if(d<0) { //dN
        		d += 8*y+12;
        		y++;
        	}
        	else { //dNW
        		d += 8*y-8*x+20;
        		y++;
        		x--;
        	}
        	drawPixel8Way(gl,x,y);
        }
    
        gl.glEnd();
   }
    
	
 void drawPixel8Way(GL2 gl, int x, int y) {
	// TODO Auto-generated method stub
	 
	 	gl.glPointSize(5.0f);
        gl.glColor3d(1, 0, 0);
                     
        gl.glBegin(GL2.GL_POINTS); 
        
   	  	gl.glVertex2d(x, y); 
   	  	gl.glVertex2d(-x, y);
   	  	gl.glVertex2d(x, -y); 
   	  	gl.glVertex2d(-x, -y); 
   	 
   	  	gl.glVertex2d(y, x); 
   	  	gl.glVertex2d(-y, x); 
   	  	gl.glVertex2d(y, -x); 
   	  	gl.glVertex2d(-y, -x); 
	
 	}	
    
    public void dispose(GLAutoDrawable arg0) {
        //do nothing
    }
}
