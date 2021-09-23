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
		balls[0] = new Ball(width / 3, height * 0.9, 1.2, 1.6, 0.2);
		balls[1] = new Ball(width / 3, height * 0.7, -0.0, 0.6, 0.3);
	}


	void step(double deltaT) {
		double g = 5;
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

    void rotate (Ball b, double a) {
        b.vx = Math.cos(a) * b.vx - Math.sin(a) * b.vy;
        b.vy = Math.sin(a) * b.vx + Math.cos(a) * b.vy;
    }

	void collide (Ball[] b){
        double deltax = Math.pow(b[0].x-b[1].x,2);
        double deltay = Math.pow(b[0].y-b[1].y,2);
        double dist = Math.sqrt(Math.pow(deltax,2) + Math.pow(deltay,2));

		if(b[0].radius + b[1].radius >= dist){

			double angle = Math.acos(deltax/dist);
            
            if(b[0].x < b[1].x && b[0].y > b[1].y) {
                angle = Math.PI -angle;
            } else if (b[0].x < b[1].x && b[0].y < b[1].y) {
                angle = -Math.PI -angle;
            }


            rotate(b[0], angle);
            rotate(b[1], angle);

            double r = b[1].vx - b[0].vx;
            double i = b[0].weight * b[0].vx + b[1].weight*b[1].vx;

            b[1].vx = (i - b[0].weight*r)/(b[0].weight+b[1].weight);
            b[0].vx = (i-b[1].weight*b[1].vx)/(b[0].weight);

            rotate(b[0], -angle);
            rotate(b[1], -angle);

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
