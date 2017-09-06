
public class Score_Manager 
{
	
	//===================================================================================================================//
	// 											**Variables**															 //
	//===================================================================================================================//
	//Puntuación acual
	int score = 0;
	int [] best_scores = new int [Util_Const.MAX_SCORES];
	
	//Instancia singleton
	private static Score_Manager instance; 
	
	//===================================================================================================================//
	// 										**Constructor Singleton**													 //
	//===================================================================================================================//
	
	public static Score_Manager Instance()
	{
		return instance;
	}	
	
	public Score_Manager()
	{
		if (instance == null)
		{
			instance = this;	
		}
		else
		{
			System.out.println("Ya hay una instancia del score manager");
			return;
		}
	}
	
	public void Initialize()
	{
		score = 0;
		
		for (int i = 0; i < best_scores.length; i++)
		{
			best_scores[i] = 0;
		}
	}
	
	public void Add_Score(int value)
	{
		score += value;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public int [] getBestsScores()
	{
		return best_scores;
	}
	
	public void CheckScore(int newScore)
	{
		int count = 0;
		boolean gotNewBestScore = false;
		for (int i = 0; i < best_scores.length; i++)
		{
			if (newScore >= best_scores[i])
			{
				count = i;
				gotNewBestScore = true;
				break;
			}
		}
		
		if (gotNewBestScore)
		{
			for (int i = best_scores.length; i > count; i--)
			{
				best_scores[i] = best_scores[i-1];
			}
			
			best_scores[count] = newScore;
		}
	}
}
