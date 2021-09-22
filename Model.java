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
		balls[0] = new Ball(width / 3, height * 0.9, 1.5, 1.6, 0.2);
		balls[1] = new Ball(2 * width / 3, height * 0.7, -0.6, 0.6, 0.3);
	}


	void step(double deltaT) {
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
					b.vy -= (3*deltaT)/2;
					b.y = b.radius;
					
				} else{
					b.vy -= (3*deltaT)/2;
					b.y = areaHeight-b.radius;	
					
				}
				b.vy *= -1;	
			}
			
			// compute new position according to the speed of the ball
			collide(balls);	
			b.x += deltaT * b.vx;
			// (at^2)/2
			b.vy += (-3 * deltaT)/2;
			// (s = vt + acceleration)
			b.y += deltaT * b.vy;
			
			
		}

		
	}
	void collide (Ball[] b){
		if(b[0].radius + b[1].radius >= Math.abs(b[0].x - b[1].x) && b[0].radius + b[1].radius >= Math.abs(b[0].y - b[1].y)){
			double angle = Math.tan((b[0].y - b[1].y)/(b[0].x - b[1].x));

            double x = ((b[0].x - b[1].x) * Math.cos(angle) - (b[0].y - b[1].y) * Math.sin(angle));
            double y = ((b[0].y - b[1].y) * Math.cos(angle) + (b[0].x - b[1].x) * Math.sin(angle));

            ////////// bajs kod ovanför -.-

            double theta0 = Math.atan2(b[0].vy, b[0].vx);
            double theta1 = Math.atan2(b[1].vy, b[1].vx);
            double ang = Math.atan2(b[0].y - b[1].y, b[0].x - b[1].x);

            // New velocity
            double newb0vx = ((b[0].weight*b[0].vx - b[1].weight*b[0].vx + 2*b[1].weight*b[1].vx)/(b[0].weight + b[1].weight));
            double newb0vy = ((b[0].weight*b[0].vy - b[1].weight*b[0].vy + 2*b[1].weight*b[1].vy)/(b[0].weight + b[1].weight));

            b[1].vx = ((2*b[0].weight * b[0].vx + b[1].weight*b[1].vx - b[0].weight*b[1].vx)/(b[0].weight + b[1].weight)) ;
            b[1].vy = ((2*b[0].weight * b[0].vy + b[1].weight*b[1].vy - b[0].weight*b[1].vy)/(b[0].weight + b[1].weight)) ;

            b[0].vx = Math.cos(angle) * newb0vx - Math.sin(angle) * newb0vy;
            b[0].vy = Math.sin(angle) * newb0vx + Math.cos(angle) * newb0vy;

            b[1].vx = Math.cos(angle) * b[1].vx - Math.sin(angle) * b[1].vy;
            b[1].vy = Math.sin(angle) * b[1].vx + Math.cos(angle) * b[1].vy;

            b[0].x += b[0].vx * 0.001;
            b[0].y += b[0].vy * 0.001;
            b[1].x += b[1].vx * 0.001;
            b[1].y += b[1].vy * 0.001;

            
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
