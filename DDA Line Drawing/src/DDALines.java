import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

public class DDALines implements GLEventListener {
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
      
  
        DDA(gl,100, 100, 50, 50);
        DDA(gl,100, 100, 150, 50);
        DDA(gl,50, 50, 150, 50);
        
        DDA(gl,70, 50, 70, 10);
        DDA(gl,130, 50, 130, 10);
        
        DDA(gl,70, 10, 130, 10); 
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        //do nothing
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
        //do nothing
    }

    private void DDA(GL2 gl, int x1, int y1, int x2, int y2) {
    	gl.glPointSize(5.0f);
        gl.glColor3d(1, 0, 0);
        gl.glBegin(GL2.GL_POINTS);
        

    	double x = x1;
    	double y = y1;
    	int dx = Math.abs(x2 - x1);
    	int dy = Math.abs(y2 - y1);
    	int length;
    	
    	
    	if(dx > dy){
    		length = dx;
    	}
    	else{
    		length = dy;
    	}
    
    	dx = (x2-x1)/length;
    	dy = (y2-y1)/length;
    	
    	x = x1 + 0.5*(sign(dx));
    	y = y1 + 0.5*(sign(dy));
   
    	int i = 1;
    	while(i<=length){
    		gl.glVertex2d(x, y);
    		x = x + dx;
    		y = y + dy;
    		i++;
    	}
        gl.glEnd();
    }
    
    public int sign(int arg){
    	if(arg == 0){
    		return 0;
    	}
    	else if(arg >0){
    		return 1;
    	}
    	else{
    		return -1;
    	}
    }
    
    public void dispose(GLAutoDrawable arg0) {
        //do nothing
    }
}
