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
					b.vy -= (10*deltaT)/2;
					b.y = b.radius;
					
				} else{
					b.vy -= (10*deltaT)/2;
					b.y = areaHeight-b.radius;	
					
				}
				b.vy *= -1;	
			}
			
			// compute new position according to the speed of the ball
			collide(balls);	
			b.x += deltaT * b.vx;
			// (at^2)/2
			b.vy += (-10 * deltaT)/2;
			// (s = vt + acceleration)
			b.y += deltaT * b.vy;
			
			
		}

		
	}
	void collide (Ball[] b){
		if(b[0].radius + b[1].radius >= Math.abs(b[0].x - b[1].x) && b[0].radius + b[1].radius >= Math.abs(b[0].y - b[1].y)){
			double angle = 0;
			double[] vector0 = new double[2];
			double[] vector1 = new double[2];
		
			//räkna ut vinklar
			//Skapa vektorer
			for(int i = 0; i<vector0.length; i++){
				vector0[0] = (b[0].vx);
				vector0[1] = (b[0].vy);
				vector1[0] = (b[1].vx);
				vector1[1] = (b[1].vy);

			}
			
			//räkna ut resultanten för båda hastigheterna
			b[0].vx = (vector1[0] - vector0[0])/2; // 
			b[0].vy = (vector0[1] - vector1[1])/2;
			b[1].vx = (vector0[0] - vector1[0])/2; // 
			b[1].vy = (vector0[1] - vector1[1])/2;
			System.out.println(b[0].vx);
			System.out.println(b[1].vx);
			System.out.println("balls collide");

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
		}

		/**
		 * Position, speed, and radius of the ball. You may wish to add other attributes.
		 */
		double x, y, vx, vy, radius;
	}
}
