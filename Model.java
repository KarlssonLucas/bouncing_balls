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
/*
We did not find a way to check if there are any energyleaks in the system, we checked this by keeping the program on for 10 minutes to see if the balls got slower.
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
		balls[1] = new Ball(width / 3, height * 20, 1, 2, 0.4);
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
					//Gravity needs to stay active during collision
					b.vy -= (g*deltaT)/2;
					b.y = b.radius;
					
				} else{
					//Gravity needs to stay active during collision
					b.vy -= (g*deltaT)/2;
					b.y = areaHeight-b.radius;		
				}
				b.vy *= -1;	
			}
			// compute new position according to the speed of the ball
		
			// v = (at)/2
			b.vy += (-g * deltaT)/2;
			//s = vt + (at^2)/2
			b.y += deltaT * b.vy;
			b.x += deltaT * b.vx;
			collide(balls);	
			// (s = vt + acceleration)
				
		}
	}
	double getAngle(Ball[] b){
		//Calculating angle, atan2 can find angles in all directions of the unit circle
		return Math.atan2(b[0].vy, b[0].vx);
	}

	void collide (Ball[] b){
		// Distance between balls
		double dist = Math.sqrt(Math.pow(b[0].x-b[1].x, 2) + Math.pow(b[0].y-b[1].y,2));
		if(Math.sqrt(Math.pow(b[0].radius + b[1].radius,2)) >= dist){ //Collided if distance is lower than the radius of the two balls.
			double angle = getAngle(b);

			//Temp variables for rotation
			double vx0temp = b[0].vx;
			double vx1temp = b[1].vx;
			
			//Rotation matrix
            b[0].vx = Math.cos(angle) * vx0temp - Math.sin(angle) * b[0].vy;
            b[0].vy = Math.cos(angle) * b[0].vy + Math.sin(angle) * vx0temp;
            b[1].vx = Math.cos(angle) * vx1temp - Math.sin(angle) * b[1].vy;
            b[1].vy = Math.cos(angle) * b[1].vy + Math.sin(angle) * vx1temp;

			//New velocity
			//Solved this by equationsystem with kinetic energy and momentum
			double newb0vx = ((b[0].weight*b[0].vx - b[1].weight*b[0].vx + 2*b[1].weight*b[1].vx)/(b[0].weight + b[1].weight));

			//v0y not affected after rotation
			double newb0vy = b[0].vy;
            b[1].vx = ((2*b[0].weight * b[0].vx + b[1].weight*b[1].vx - b[0].weight*b[1].vx)/(b[0].weight + b[1].weight));

			// Temp variables
			double newb1vx = b[1].vx;
			double newb1vy = b[1].vy;

			//Rotate back with -angle
            b[0].vx = Math.cos(-angle) * newb0vx - Math.sin(-angle) * newb0vy;
            b[0].vy = Math.cos(-angle) * newb0vy + Math.sin(-angle) * newb0vx;
            b[1].vx = Math.cos(-angle) * newb1vx - Math.sin(-angle) * newb1vy;
            b[1].vy = Math.cos(-angle) * newb1vy + Math.sin(-angle) * newb1vx;

			//remove overlaps so that the balls don't get stuck.
			double instersectD = dist - b[0].radius - b[1].radius; //overlapping distance
			b[0].x -= instersectD * (0.2 + b[0].x - b[1].x)/dist; //Find distance in x direction(similar triangle), 0.2 disturbs the uniform abit but it is good to have an offset in case it still is stuck
			b[0].y -= instersectD * (0.2  + b[0].y - b[1].y)/dist; //Find distance in x direction(similar triangle), 0.2 disturbs the uniform abit but it is good to have an offset in case it still is stuck
	
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
            this.weight = Math.pow(r,2); //Assuming that the balls are made from the same material
		}

		/**
		 * Position, speed, and radius of the ball. You may wish to add other attributes.
		 */
		double x, y, vx, vy, radius, weight;
	}
}
