package bouncing_balls;

import jdk.javadoc.internal.doclets.formats.html.SourceToHTMLConverter;

/**
 * The physics model.
 * 
 * This class is where you should implement your bouncing balls model.
 * 
 * The code has intentionally been kept as simple as possible, but if you wish, you can improve the design.
 * 
 * @author Simon Robillard
 *
 */
class Model {

	double areaWidth, areaHeight;
	
	Ball [] balls;

	Model(double width, double height) {
		areaWidth = width;
		areaHeight = height;
		
		// Initialize the model with a few balls
		balls = new Ball[2];
		balls[0] = new Ball(width / 3, height * 0.7, 1, 5, 0.2);
		balls[1] = new Ball(width / 3, height * 20, 0, 5, 0.2);
	}


	void step(double deltaT) {
		double g = 0;
		// TODO this method implements one step of simulation with a step deltaT
		for (Ball b : balls) {
			
			// detect collision with the border
			if (b.x < b.radius || b.x > areaWidth - b.radius) {
				if (b.vx < 0){
					b.x = b.radius;
					
				} else{
					b.x = areaWidth-b.radius;
				}
				
				b.vx *= -1; // change direction of ball
			}
			if (b.y < b.radius || b.y > areaHeight - b.radius) {
				if (b.vy <0){
					b.vy -= (g*deltaT)/2;
					b.y = b.radius;
					System.out.println(b.vy + b.vy);
					
					
				} else{
					b.vy -= (g*deltaT)/2;
					b.y = areaHeight-b.radius;	
					
				}
				b.vy *= -1;	
			}
			
			// compute new position according to the speed of the ball
			collide(balls);	
			b.x += deltaT * b.vx;
			// (at^2)/2
			b.vy += (-g * deltaT)/2;
			// (s = vt + acceleration)
			b.y += deltaT * b.vy;
			
			
		}

		
	}
	void collide (Ball[] b){
		if(b[0].radius + b[1].radius >= Math.abs(b[0].x - b[1].x) && b[0].radius + b[1].radius >= Math.abs(b[0].y - b[1].y)){
			double angle = 0;
			// Calculating angle
			if(b[0].x > b[1].x  && b[0].y > b[1].y){
				angle = Math.acos(b[0].x/((b[0].y*b[0].y* + b[0].x*b[0].x)));
				
				
			}
			if(b[0].x < b[1].x  && b[0].y > b[1].y){
				angle = (Math.PI/2) + Math.acos(b[0].x/((b[0].y*b[0].y + b[0].x*b[0].x)));
				

			}
			if(b[0].x > b[1].x  && b[0].y < b[1].y){
				angle = Math.PI + Math.acos(b[0].x/((b[0].y*b[0].y + b[0].x*b[0].x)));
			
			}
			if(b[0].x < b[1].x  && b[0].y < b[1].y){
				angle = 1.5 * Math.PI + Math.acos(  + b[0].x/(b[0].y*b[0].y + (b[0].x*(b[0].x))));
				
			}
			System.out.println(angle);
			System.out.println(b[0].vx);
			System.out.println(b[0].vy);
			//find the angle
			//angle = Math.atan2((b[0].y - b[1].y),(b[0].x - b[1].x));
			

			//Rotate it
            b[0].vx = Math.cos(angle) * b[0].vx - Math.sin(angle) * b[0].vy;
            b[0].vy = Math.sin(angle) * b[0].vx + Math.cos(angle) * b[0].vy;
            b[1].vx = Math.cos(angle) * b[1].vx - Math.sin(angle) * b[1].vy;
            b[1].vy = Math.sin(angle) * b[1].vx + Math.cos(angle) * b[1].vy;

			System.out.println(b[0].vx);
			System.out.println(b[0].vy);
			// New velocity
			double newb0vx = ((b[0].weight*b[0].vx - b[1].weight*b[0].vx + 2*b[1].weight*b[1].vx)/(b[0].weight + b[1].weight));
            //double newb0vy = ((b[0].weight*b[0].vy - b[1].weight*b[0].vy + 2*b[1].weight*b[1].vy)/(b[0].weight + b[1].weight));
			double newb0vy = b[0].vy;

			System.out.println(b[0].vx);
			System.out.println(b[0].vy);


            b[1].vx = ((2*b[0].weight * b[0].vx + b[1].weight*b[1].vx - b[0].weight*b[1].vx)/(b[0].weight + b[1].weight));
           // b[1].vy = ((2*b[0].weight * b[0].vy + b[1].weight*b[1].vy - b[0].weight*b[1].vy)/(b[0].weight + b[1].weight));

			System.out.println(b[0].vx);
			System.out.println(b[0].vy);

            b[0].vx = Math.cos(angle) * newb0vx + Math.sin(angle) * newb0vy;
            b[0].vy = Math.cos(angle) * newb0vy - Math.sin(angle) * newb0vx;
            b[1].vx = Math.cos(angle) * b[1].vx + Math.sin(angle) * b[1].vy;
            b[1].vy = Math.cos(angle) * b[1].vy - Math.sin(angle) * b[1].vx;

			System.out.println(b[0].vx);
			System.out.println(b[0].vy);

            

		}
	}

	
	/**
	 * Simple inner class describing balls.
	 */
	class Ball {
		
		Ball(double x, double y, double vx, double vy, double r) {
			this.x = x;
			this.y = y;
			this.vx = vx;
			this.vy = vy;
			this.radius = r;
            this.weight = Math.pow(r,2);
		}

		/**
		 * Position, speed, and radius of the ball. You may wish to add other attributes.
		 */
		double x, y, vx, vy, radius, weight;
	}
}
