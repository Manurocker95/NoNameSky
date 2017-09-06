
public class Ship_Neuronal_Net extends Neuronal_Net{
	
	protected static double[][] trainning = {

	// Amigos Vida PJLuchando Rango Perseguir MovimientoGrupo Huir

	{ 0, 1, 0, 0.2, 0.9, 0.1, 0.1},

	{ 0, 1, 1, 0.2, 0.9, 0.1, 0.1},

	{ 0, 1, 0, 0.8, 0.1, 0.1, 0.1},

	{ 0.1, 0.5, 0, 0.2, 0.9, 0.1, 0.1},

	{ 0, 0.25, 1, 0.5, 0.1, 0.9, 0.1},

	{ 0, 0.2, 1, 0.2, 0.1, 0.1, 0.9},

	{ 0.3, 0.2, 0, 0.2, 0.9, 0.1, 0.1},

	{ 0, 0.2, 0, 0.3, 0.1, 0.9, 0.1},

	{ 0, 1, 0, 0.2, 0.1, 0.9, 0.1},

	{ 0, 1, 1, 0.6, 0.1, 0.1, 0.1},

	{ 0, 1, 0, 0.8, 0.1, 0.9, 0.1},

	{ 0.1, 0.2, 0, 0.2, 0.1, 0.1, 0.9},

	{ 0, 0.25, 1, 0.5, 0.1, 0.1, 0.9},

	{ 0, 0.6, 0, 0.2, 0.1, 0.1, 0.9}

	};

	public Ship_Neuronal_Net ()
	{
		super (4 , 3, 3);
		trainNet ();
	}
	
	protected void trainNet () 
	{
		int i; double error = 1; int epoch = 0;
		System.out.println("RED ANTES DEL ENTRENAMIENTO");
		this.drawWeights();
		
		while ((error > 0.05) && (epoch < 50000))
		{
			error = 0;
			epoch++;
			
			for (i = 0; i < 14; i++)
			{
				this.setInput (0, trainning [i][0]);
				this.setInput (1, trainning [i][1]);
				this.setInput (2, trainning [i][2]);
				this.setInput (3, trainning [i][3]);
	
				this.setDesiredOutput(0, trainning [i][4]);
				this.setDesiredOutput(1, trainning [i][5]);
				this.setDesiredOutput(2, trainning [i][6]);
	
				this.feedForward();
		
				error += this.calculateError();
		
				this.backPropagation();
		
				if (Util_Const.PRINT_EPOCHS && epoch%1000 == 0) {
		
				System.out.println ("epoch: " + epoch + " input: " + i);
		
				this.dibujarValores ();
				}
			}

			error = error / 14.0;
		
			if (Util_Const.PRINT_EPOCHS && epoch%1000 == 0)
			{
				System.out.println ("epoch: " + epoch + " Error: " + error);
				System.out.println("----------------------------------------------");
				System.out.println("----------------------------------------------");
				System.out.println();
			}

		}

		System.out.println("RED DESPUÉS DEL ENTRENAMIENTO");

		this.drawWeights();

	}
	
	protected void retrainNet (double[] inputs, double output0, double output1, double output2)
	{

		int i;
		double error = 1;
		int epoch = 0;

		System.out.println("RED ANTES DEL REENTRENAMIENTO");

		this.drawWeights();

		while ((error > 0.1) && (epoch < 5000))
		{
			epoch++;
			this.setInput (0, inputs[0]);
			this.setInput (1, inputs[1]);
			this.setInput (2, inputs[2]);
			this.setInput (3, inputs[3]);
	
			this.setDesiredOutput(0, output0);
			this.setDesiredOutput(1, output1);
			this.setDesiredOutput(2, output2);
	
			this.feedForward();
	
			error = this.calculateError();
	
			this.backPropagation();
	
			if (Util_Const.PRINT_EPOCHS && epoch%1000 == 0 && i==13) {
	
			System.out.println ("epoch: " + epoch);
	
			this.dibujarValores ();
	
			}
		}
		
		if (Util_Const.PRINT_EPOCHS && epoch%1000 == 0)
		{
			System.out.println ("epoch: " + epoch + " Error: " + error);
			System.out.println("----------------------------------------------");
			System.out.println("----------------------------------------------");
			System.out.println();
		}

		System.out.println("RED DESPUÉS DEL REENTRENAMIENTO");

		this.drawWeights();
	}
}
