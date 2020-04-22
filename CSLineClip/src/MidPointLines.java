/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author sajib
 */
public class MidPointLines  implements GLEventListener {
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

       // DrawMPL(gl,-90,0,90,0); 	//top horizontal line
 
      
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        //do nothing
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
        //do nothing
    }

    //y=mx+b
  /*  private int func(int x, float y){
        return (int)(dy*x - y*dx + b*dx);
    }*/
    
    public void DrawMPL(GL2 gl, int x1, int y1, int x2, int y2){
    	
    	gl.glPointSize(3.0f);
        gl.glColor3d(1, 0, 0);
                     
        gl.glBegin(GL2.GL_POINTS); 

    	int x,y,dx,dy,d,dE,dNE,zone;

    	//finding the zone of the input line
    	zone = findZone(x1,y1,x2,y2);
    	
       // System.out.println(x1 + " " + y1 + " " +x2+ " " + y2);
    
    	//converting x1 and y1 to corresponding Zone-0 value 
    	int conv_x1 = convertX(x1,y1,zone);
    	int conv_y1 = convertY(x1,y1,zone);
    	
    	//converting x2 and y2 to corresponding Zone-0 value 
    	int conv_x2 = convertX(x2,y2,zone);
    	int conv_y2 = convertY(x2,y2,zone);
    	
        System.out.println(conv_x1 + " " + conv_y1 + " " +conv_x2+ " " + conv_y2+" "+zone);
    	

    	x = conv_x1;
    	y = conv_y1;
    	
        dx = (conv_x2)-(conv_x1);
        dy = (conv_y2)-(conv_y1);
        d = (2*dy)-dx;
        dE = 2*dy;
        dNE = 2*(dy-dx);
        
        //plotting the initial coordinates by converting to original zone
     //  int xinit = convertX(conv_x1,conv_y1,zone); 
      // int yinit = convertY(conv_x1,conv_y1,zone);

        gl.glVertex2d(x1,y1); 
        
     	  while(x<conv_x2){
     		   if(d<0){
     			  x++;
     			  d += dE;
     		   }
     		   else{
     			  x++;
     			  y++;
     			  d += dNE;
     		   }
     	//method that converts coordinates to original zone and plots
           drawPixelOriginal(gl,x,y,zone); 
      
        }
        
        gl.glEnd();
    }
    
    void drawPixelOriginal(GL2 gl,int x,int y,int zone){
    	
    	gl.glPointSize(1.0f);
        gl.glColor3d(1, 0, 0);
                     
        gl.glBegin(GL2.GL_POINTS); 
        
        int orig_x = convertX(x,y,zone);
        int orig_y = convertY(x,y,zone);
       

   	  	gl.glVertex2d(orig_x,orig_y); 

    }
    
    
    //zone-finding method
    int findZone(int x1, int y1, int x2, int y2) {
        
    	int zone;    
    	int dx,dy;

        dx = (x2)-(x1);
        dy = (y2)-(y1);
        
    	
    	if((Math.abs(dx))>=(Math.abs(dy))){ // 0 3 4 7
    		if((dx >= 0) && (dy >= 0)){
    			zone = 0;
    		}
    		else if((dx < 0) && (dy >= 0)){
    			zone = 3;
    		}
    		else if((dx < 0) && (dy < 0)){
    			zone = 4;
    		}
    		else{
    			zone = 7;
    		}
    	}
    	else{ // 1 2 5 6
    		if((dx >= 0) && (dy >= 0)){
    			zone = 1;
    		}
    		else if((dx < 0) && (dy >= 0)){
    			zone = 2;
    		}
    		else if((dx < 0) && (dy < 0)){
    			zone = 5;
    		}
    		else{
    			zone = 6;
    		}
    	}
        return zone ;
    }
    
    //conversion to corresponding zone-0 x_value
    int convertX(int x, int y, int zone){
        int convertedX=0;
        switch(zone){
        case 0:
        	return x;
        case 1:
        	return  y;
        case 2:
        	return -y;
        case 3:
        	return -x;
        case 4:
        	return -x;
        case 5:
        	return -y;
        case 6:
        	return y;
        case 7:
        	return x;
        }
        	
        return convertedX;
    }
    
  //conversion to corresponding zone-0 y_value
    int convertY(int x, int y, int zone){
       int convertedY=0; 
       switch(zone){
    	case 0:
    		return y;
    	case 1:
    		return x;
    	case 2:
    		return x;
    	case 3:
    		return y;
    	case 4:
    		return -y;
    	case 5:
    		return -x;
    	case 6:
    		return -x;
    	case 7:
    		return -y;
       }
       return convertedY;
    }
    
    
    public void dispose(GLAutoDrawable arg0) {
        //do nothing
    }
}

