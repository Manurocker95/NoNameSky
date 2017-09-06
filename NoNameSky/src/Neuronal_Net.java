import java.text.DecimalFormat;

public class Neuronal_Net {

	protected Neuronal_Layer inputLayer;
	protected Neuronal_Layer hiddenLayer;
	protected Neuronal_Layer outputLayer;

	public Neuronal_Net (int nInputNodes, int nHiddenNodes, int nOutputNodes)
	{
		inputLayer = new Neuronal_Layer (0, nInputNodes, nHiddenNodes);
		hiddenLayer = new Neuronal_Layer (nInputNodes, nHiddenNodes, nOutputNodes);
		outputLayer = new Neuronal_Layer (nHiddenNodes, nOutputNodes, 0);
		inputLayer.initialize(nInputNodes, null, hiddenLayer);
		hiddenLayer.initialize(nHiddenNodes, inputLayer, outputLayer);
		outputLayer.initialize(nOutputNodes, hiddenLayer, null);
		inputLayer.setRandomWeights();
		hiddenLayer.setRandomWeights();
	}
	
	public void setInput (int i, double valor)
	{
		if ((i >= 0) && (i < inputLayer.numberOfNodes))
			inputLayer.neuronalValues[i] = valor;
	}

	public double getOutput (int i)
	{
		if ((i >= 0) && (i < outputLayer.numberOfNodes))
			return outputLayer.neuronalValues[i];
		else
			return Util_Const.OUTPUT_ERROR;
	}

	public void setDesiredOutput (int i, double valor)
	{
		if ((i >= 0) && (i < outputLayer.numberOfNodes))
			outputLayer.desiredValues[i] = valor;
	}
	
	public void feedForward ()
	{
		inputLayer.calculateNeuronalValues();
		hiddenLayer.calculateNeuronalValues();
		outputLayer.calculateNeuronalValues();
	}

	public void backPropagation ()
	{
		outputLayer.calculateErrors();
		hiddenLayer.calculateErrors();
		hiddenLayer.setWeights();
		inputLayer.setWeights();
	}
	
	public int getMaxOutputId ()
	{
		int i, id;
		double max;
		id = 0;
		max = outputLayer.neuronalValues[0];

		for (i = 1; i < outputLayer.numberOfNodes; i++)
		{
			if (outputLayer.neuronalValues[i] > max)
			{
				id = i;
				max = outputLayer.neuronalValues[i];
			}
		}

		return id;
	}
	
	public double calculateError ()
	{
		int i;
		double error = 0;

		for (i = 0; i < outputLayer.numberOfNodes; i++)
		{
			error += Math.pow(outputLayer.neuronalValues[i] - outputLayer.desiredValues[i], 2);
		}

		error = error / outputLayer.numberOfNodes;

		return error;

	}
	
	// Mostrar el contenido de la red neuronal: valores de las neuronas y pesos.
	
	public void drawWeights () 
	{
		int i, j, k;
		String [] col0, col1, col2, col3;
		int c0 = 0;
		int c1 = 0;
		int c2 = 0;
		int c3 = 0;
		int maxc0 = inputLayer.numberOfNodes * hiddenLayer.numberOfNodes;
		int maxc1 = inputLayer.numberOfChildrenNodes;
		int maxc2 = hiddenLayer.numberOfNodes * outputLayer.numberOfNodes;
		int maxc3 = hiddenLayer.numberOfChildrenNodes;

		col0 = new String[maxc0];
		col1 = new String[maxc1];
		col2 = new String[maxc2];
		col3 = new String[maxc3];

		DecimalFormat df = new DecimalFormat("+##,##00.00;-#");

		System.out.println("----------------------------------------------");

		System.out.println("Pesos");
		
		for (i = 0; i < inputLayer.numberOfNodes; i++)
		{
			for (j = 0; j < hiddenLayer.numberOfNodes; j++)
			{
				col0[c0] = "Ni" + i + " " + df.format (inputLayer.neuronalWeights[i][j]) + " Nh" + j;
				c0++;
			}
		}

		for (j = 0; j < hiddenLayer.numberOfNodes; j++)
		{
			col1[c1] = "Bh" + j + " " + df.format (inputLayer.biasWeights[j]);
			c1++;
		}

		for (j = 0; j < hiddenLayer.numberOfNodes; j++)
		{
			for (k = 0; k < outputLayer.numberOfNodes; k++)
			{
				col2[c2] = "Nh" + j + " " + df.format ( hiddenLayer.neuronalWeights[j][k]) + " No" + k;
				c2++;
			}
		}

		for (k = 0; k < outputLayer.numberOfNodes; k++)
		{
			col3[c3] = "Bo" + k + " " + df.format (hiddenLayer.biasWeights[k]);
			c3++;
		}
		
		int max = Math.max (maxc0, maxc2);

		for (i = 0; i < max; i++)
		{
			if (i < maxc0) System.out.print(col0[i]);
			else System.out.print(" ");
			
			System.out.print(" ");

			if (i < maxc1) System.out.print(col1[i]);
			else System.out.print(" ");
			
			System.out.print(" ");

			if (i < maxc2) System.out.print(col2[i]);
			else System.out.print(" ");

			System.out.print(" ");

			if (i < maxc3) System.out.print(col3[i]);

			System.out.println ();
		}

		System.out.println("----------------------------------------------");

	}
	
	public void dibujarValores ()
	{
		int i, j, k;
		String [] col0, col1, col2, col3;
		int c0 = 0;
		int c1 = 0;
		int c2 = 0;
		int c3 = 0;
		int maxc0 = inputLayer.numberOfNodes;
		int maxc1 = hiddenLayer.numberOfNodes;
		int maxc2 = outputLayer.numberOfNodes;
		int maxc3 = outputLayer.numberOfNodes;

		col0 = new String[maxc0];
		col1 = new String[maxc1];
		col2 = new String[maxc2];
		col3 = new String[maxc3];

		DecimalFormat df = new DecimalFormat("+##,##00.00;-#");

		System.out.println("----------------------------------------------");

		System.out.println("Valores");
		
		for (i = 0; i < inputLayer.numberOfNodes; i++) 
		{
			col0[c0] = "Ni" + i + " " + df.format (inputLayer.neuronalValues[i]);
			c0++;
		}

		for (i = 0; i < hiddenLayer.numberOfNodes; i++)
		{
			col1[c1] = "Nh" + i + " " + df.format (hiddenLayer.neuronalValues[i]);
			c1++;
		}

		for (i = 0; i < outputLayer.numberOfNodes; i++)
		{
			col2[c2] = "Noc" + i + " " + df.format (outputLayer.neuronalValues[i]);
			c2++;
		}

		for (i = 0; i < outputLayer.numberOfNodes; i++)
		{
			col3[c3] = "Nod" + i + " " + df.format (outputLayer.desiredValues[i]);
			c3++;
		}
		
		int max = Math.max(maxc0, maxc1);

		max = Math.max(max, maxc2);

		for (i = 0; i < max; i++)
		{
			if (i < maxc0) System.out.print(col0[i]);
			else System.out.print(" ");

			System.out.print(" ");

			if (i < maxc1) System.out.print(col1[i]);
			else System.out.print(" ");

			System.out.print(" ");

			if (i < maxc2) System.out.print(col2[i]);
			else System.out.print(" ");

			System.out.print(" ");

			if (i < maxc3) System.out.print(col3[i]);

			System.out.println ();
		}

		System.out.println("----------------------------------------------");

	}
}
