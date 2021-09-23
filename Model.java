package bouncing_balls;

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
		balls[0] = new Ball(width / 3, height * 0.7, 2, 2, 0.2);
		balls[1] = new Ball(width / 3, height * 20, 1, 2, 0.22);
	}


	void step(double deltaT) {
		double g = 2;
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
	double getAngle(Ball[] b){
		double angle = 0;
        double deltax = Math.pow(b[0].x-b[1].x,2);
        double deltay = Math.pow(b[0].y-b[1].y,2);

			// Calculating angle
			
		angle = Math.acos(deltax/Math.sqrt((Math.pow(deltay,2) + Math.pow(deltax,2))));
			
		if(b[0].x > b[1].x  && b[0].y > b[1].y){
			angle = angle;
			System.out.println("First if");
			System.out.println("_______");
			//Do nothing, base case
				
				//Do nothing, base case
				
		}

		if(b[0].x < b[1].x  && b[0].y > b[1].y){
			System.out.println("Second if");
			System.out.println(angle);
			angle = -angle;
			
			System.out.println(angle);
			System.out.println("_______");
				
		}
		if(b[0].x < b[1].x  && b[0].y < b[1].y){
			System.out.println("Third if");
			System.out.println(angle);
			angle = -(Math.PI - angle);
			System.out.println(angle);
			System.out.println("_______");
		
		}
	
	

		if(b[0].x > b[1].x  && b[0].y < b[1].y){
			System.out.println("Fourth if");
			System.out.println(angle);
			angle = -(Math.PI-angle);
			System.out.println(angle);
			System.out.println("_______");	
						
		}
		return angle;
	}

		
	void rotate (Ball b, double angle){
		double temp1 = b.vx;
		double temp2 = b.vy;
		b.vx = Math.cos(angle) * temp1 - Math.sin(angle) * temp2;
		b.vy = Math.sin(angle) * temp1 + Math.cos(angle) * temp2;
	}

	void collide (Ball[] b){
		if(b[0].radius + b[1].radius >= Math.abs(b[0].x - b[1].x) && b[0].radius + b[1].radius >= Math.abs(b[0].y - b[1].y)){
			double angle = getAngle(b);
			
			//Rotate it
			
			rotate(b[0], angle);
			rotate(b[1], angle);

			// New velocity
			double newb0vx = ((b[0].weight*b[0].vx - b[1].weight*b[0].vx + 2*b[1].weight*b[1].vx)/(b[0].weight + b[1].weight));
			double newb0vy = b[0].vy;
           // double newb0vy = ((b[0].weight*b[0].vy - b[1].weight*b[0].vy + 2*b[1].weight*b[1].vy)/(b[0].weight + b[1].weight));
            b[1].vx = ((2*b[0].weight * b[0].vx + b[1].weight*b[1].vx - b[0].weight*b[1].vx)/(b[0].weight + b[1].weight));
            //b[1].vy = ((2*b[0].weight * b[0].vy + b[1].weight*b[1].vy - b[0].weight*b[1].vy)/(b[0].weight + b[1].weight));

            b[0].vx = Math.cos(-angle) * newb0vx - Math.sin(-angle) * newb0vy;
            b[0].vy = Math.cos(-angle) * newb0vy + Math.sin(-angle) * newb0vx;
            b[1].vx = Math.cos(-angle) * b[1].vx - Math.sin(-angle) * b[1].vy;
            b[1].vy = Math.cos(-angle) * b[1].vy + Math.sin(-angle) * b[1].vx;
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
