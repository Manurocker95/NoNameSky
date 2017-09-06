import java.util.Random;

public class Neuronal_Layer {

	public int numberOfNodes;
	public int numberOfChildrenNodes;
	public int numberOfParentNodes;
	public double neuronalWeights [][];
	public double neuronalWeightsChanges [][];
	public double neuronalValues [];
	public double desiredValues [];
	public double errors [];
	public double biasValues [];
	public double biasWeights [];
	protected Neuronal_Layer parentLayer, childLayer;

	Random rnd;
	
	public Neuronal_Layer (int nParentNodes, int nNodes, int nChildNodes) 
	{
		numberOfParentNodes = nParentNodes;
		numberOfNodes = nNodes;
		numberOfChildrenNodes = nChildNodes;
		parentLayer = null;
		childLayer = null;
		rnd = new Random();
	}
	
	public void initialize (int nNodes, Neuronal_Layer parent, Neuronal_Layer child) 
	{
		int i,j;

		numberOfNodes = nNodes;
		neuronalValues = new double [nNodes];
		desiredValues = new double [nNodes];
		errors = new double [nNodes];

		if (parent != null)
		{
			parentLayer = parent;
			numberOfParentNodes = parent.numberOfNodes;
		}
		else {
			parentLayer = null;
			numberOfParentNodes = 0;
		}

		if (child != null)
		{
			childLayer = child;
			numberOfChildrenNodes = child.numberOfNodes;
			neuronalWeights = new double [numberOfNodes][numberOfChildrenNodes];
			neuronalWeightsChanges = new double [numberOfNodes][numberOfChildrenNodes];
			biasValues = new double [numberOfChildrenNodes];
			biasWeights = new double [numberOfChildrenNodes];
		}
		else {
			childLayer = null;
			numberOfChildrenNodes = 0;
			neuronalWeights = null;
			neuronalWeightsChanges = null;
			biasValues = null;
			biasWeights = null;
		}

		for (i = 0; i < numberOfNodes; i++)
		{
			neuronalValues[i] = 0;
			desiredValues[i] = 0;
			errors[i] = 0;
			
			if (child != null)
			{
				for (j = 0; j < numberOfChildrenNodes; j++)
				{
					neuronalWeights[i][j] = 0;
					neuronalWeightsChanges[i][j] = 0;
				}
			}

		}

		if (child != null)
		{
			for (j = 0; j < numberOfChildrenNodes; j++)
			{
				biasValues[j] = -1;
				biasWeights[j] = 0;
			}

		}

	}
	
	public void setRandomWeights ()
	{
		int i,j;
		
		for (i = 0; i < numberOfNodes; i++)
		{
			for (j = 0; j < numberOfChildrenNodes; j++)
			{
				neuronalWeights [i][j] = createRandomWeight();
			}
		}

		for (j = 0; j < numberOfChildrenNodes; j++)
		{
			biasWeights [j] = createRandomWeight();
		}
	}

	protected double createRandomWeight ()
	{
		double randomWeight;
		int randomSign;
		randomWeight = rnd.nextDouble();

		if (rnd.nextBoolean())
		{
			randomSign = 1;
		}
		else {
			randomSign = -1;
		}

		return randomWeight * randomSign;
	}
	
	public void calculateNeuronalValues ()
	{
		int i,j;
		double x;

		if (parentLayer != null)
		{
			for (j = 0; j < numberOfNodes; j++)
			{
				x = 0;
		
				for (i = 0; i < numberOfParentNodes; i++)
				{
					x += parentLayer.neuronalValues[i] * parentLayer.neuronalWeights [i][j];
				}
		
				x += parentLayer.biasValues[j] * parentLayer.biasWeights[j];
		
				if ((childLayer == null) && Util_Const.OUTPUT_LINEAL)
				{
					neuronalValues[j] = x;
				}
				else {
					// Aplicamos la función de activación. Función logística.
					neuronalValues[j] = 1.0f / (1 + Math.exp(-x));
				}
			}
		}
	}
	
	public void calculateErrors ()
	{
		int i,j;
		double sum;

		if (childLayer == null)
		{
			for (i = 0; i < numberOfNodes; i++)
			{
				// Ecuación del error.
				errors[i] = (desiredValues[i] - neuronalValues[i]) * neuronalValues[i] * (1.0f - neuronalValues[i]);
			}
		}
		else if (parentLayer == null)
		{
			for (i = 0; i < numberOfNodes; i++)
			{
				errors[i] = 0.0f;
			}
		}
		else {
			for (i = 0; i < numberOfNodes; i++)
			{
				sum = 0;
				
				for (j = 0; j < numberOfChildrenNodes; j++)
				{
					sum += childLayer.errors[j] * neuronalWeights[i][j];
				}

				errors[i] = sum * neuronalValues[i] * (1.0f - neuronalValues[i]);
			}
		}
	}
	
	public void setWeights ()
	{
		int i, j;
		double dw; // Incremento de w.

		if (childLayer != null)
		{
			for (i = 0; i < numberOfNodes; i++)
			{
				for (j = 0; j < numberOfChildrenNodes; j++)
				{
					dw = Util_Const.LEARNING_RATIO * childLayer.errors[j] * neuronalValues[i];

					if (Util_Const.USED_INERTIA)
					{
						neuronalWeights[i][j] += dw + Util_Const.INERTIA_FACTOR * neuronalWeightsChanges[i][j];
						neuronalWeightsChanges[i][j] = dw;
					}
					else {
						neuronalWeights[i][j] += dw;
					}
				}
			}

			for (j = 0; j < numberOfChildrenNodes; j++)
			{
				dw = Util_Const.LEARNING_RATIO * childLayer.errors[j] * biasValues[j];
				biasWeights[j] += dw;
			}
		}
	}
	
}


