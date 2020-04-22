/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package opengl;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author sbiswas.amit
 */
public class LineClip implements GLEventListener {
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
      
  
        clipLine(gl,130, 50, -170, -30);

    }
    
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        //do nothing
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
        //do nothing
    }

    private void clipLine(GL2 gl, int x1, int y1, int x2, int y2) {
    	/*gl.glPointSize(5.0f);
        gl.glColor3d(1, 0, 0);
        gl.glBegin(GL2.GL_POINTS);*/
        
    	int TOP = 8;
    	int BOTTOM = 4;
    	int RIGHT = 2;
    	int LEFT = 1;
    	
    	int xMax = 120;
    	int xMin = -120;
    	int yMax = 100;
    	int yMin = -100;
        
        int code, code_0, code_1;
        int x,y;
        int m = (y2-y1)/(x2-x1); //gradient of the line passing the given coordinates
        boolean accept = false;
        
        MidPointLines ml = new MidPointLines();
        
        code_0 = makeCode(x1,y1,TOP,BOTTOM,LEFT,RIGHT,xMax,xMin,yMax,yMin);
        code_1 = makeCode(x2,y2,TOP,BOTTOM,LEFT,RIGHT,xMax,xMin,yMax,yMin);
        
        ml.DrawMPL(gl, xMin, yMax, xMax, yMax); //top line
        ml.DrawMPL(gl, xMin, yMin, xMax, yMin); //bottom line
        
        
        ml.DrawMPL(gl, xMin, yMin, xMin, yMax); //left line
        ml.DrawMPL(gl, xMax, yMin, xMax, yMax); //right line
        
        ml.DrawMPL(gl,0,0,-30,40); 
        
        

   //     drawRectangle(gl, ml, xMax, xMin, yMax, yMin);
        
              
        while(true){
        	if((code_0 | code_1)== 0){ 		//completely accepted
        		gl.glVertex2d(x1,y1); 
        		accept = true;
        		if(accept){
        			ml.DrawMPL(gl, x1, y1, x2, y2);
        		}
       
        		break;
        	}
        	else if((code_0 & code_1)!= 0){	//completely rejected
        		accept = false;
        		break;
        	}
        	else{ 							//partially accepted 
        		if(code_0 != 0){
        			code = code_0;
        		}
        		else{
        			code = code_1;
        		}
        		if((code & TOP)!= 0){
        			y = yMax;
        			x = ((1/m)*(y-y1)) + x1;
        		}
        		else if((code & BOTTOM)!= 0){
        			y = yMin;
        			x = ((1/m)*(y-y1)) + x1;
        		}
        		else if((code & RIGHT)!= 0){
        			x = xMax;
        			y = (m*(x-x1)) + y1;
        		}
        		else {
        			x = xMin;
        			y = (m*(x-x1)) + y1;
        		}
        		if(code == code_0){
        			x1 = x;
        			y1 = y;
        			code_0 = makeCode(x1,y1,TOP,BOTTOM,LEFT,RIGHT,xMax,xMin,yMax,yMin);
        		}
        		else{
        			x2 = x;
        			y2 = y;
        			code_1 = makeCode(x2,y2,TOP,BOTTOM,LEFT,RIGHT,xMax,xMin,yMax,yMin);
        		}
        	}

    
        }
        gl.glEnd();
    }
    
    int makeCode(int x, int y, int TOP, int BOTTOM, int LEFT, int RIGHT, 
    		int xMax, int xMin, int yMax, int yMin){

    	int code = 0;
    	
    	if(y>yMax){
    		code = (code | TOP);
    	}
    	else if(y<yMin){
    		code = (code | BOTTOM);
    	}
    	if(x>xMax){
    		code = (code | RIGHT);
    	}
    	else if(x<xMin){
    		code = (code | LEFT);
    	}
    	
    	return code;
    }
    
    void drawRectangle(GL2 gl, MidPointLines ml, int xMax, int xMin, int yMax, int yMin){
    	gl.glPointSize(1.0f);
        gl.glColor3d(1, 0, 0);
                     
        gl.glBegin(GL2.GL_POINTS); 
        
     //   ml.DrawMPL(gl, xMin, yMax, xMax, yMax); //top line
     //   ml.DrawMPL(gl, xMin, yMin, xMax, yMin); //bottom line
        
        
    //    ml.DrawMPL(gl, xMin, yMin, xMin, yMax); //left line
    //    ml.DrawMPL(gl, xMax, yMin, xMax, yMax); //right line
        
 
    }
    
   
    
    public void dispose(GLAutoDrawable arg0) {
        //do nothing
    }
}
