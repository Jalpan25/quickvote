export const calculateParticipationRate = (surveyData, userResponses) => {
    if (!surveyData || !userResponses) return 0;
    const answeredQuestions = userResponses.length;
    const totalQuestions = surveyData.questions.length;
    return (answeredQuestions / totalQuestions) * 100;
  };
  
  export const identifyTrends = (surveyData) => {
    if (!surveyData) return [];
  
    const trends = [];
    
    surveyData.questions.forEach((question) => {
      const options = question.options;
      const totalVotes = options.reduce((sum, opt) => sum + opt.frequency, 0);
      const dominantOption = options.reduce((prev, current) =>
        current.frequency > prev.frequency ? current : prev
      );
  
      const dominanceRatio = dominantOption.frequency / totalVotes;
  
      if (dominanceRatio > 0.6) {
        trends.push({
          question: question.questionText,
          dominantOption: dominantOption.optionText,
          percentage: Math.round(dominanceRatio * 100),
          strength: dominanceRatio > 0.8 ? "Very Strong" : "Strong",
        });
      }
    });
  
    return trends;
  };
  
  export const generateInsights = (surveyData, userResponses) => {
    if (!surveyData || !userResponses) return [];
  
    const insights = [];
  
    // Participation insight
    const participationRate = calculateParticipationRate(surveyData, userResponses);
    if (participationRate < 50) {
      insights.push({
        type: "warning",
        message: "Low participation rate may affect the reliability of results",
      });
    }
  
    // Consensus insights
    surveyData.questions.forEach((question) => {
      const options = question.options;
      const totalVotes = options.reduce((sum, opt) => sum + opt.frequency, 0);
      const frequencies = options.map((opt) => opt.frequency / totalVotes);
  
      // Check for even distribution
      const isEvenlyDistributed = frequencies.every(
        (freq) => Math.abs(freq - 1 / options.length) < 0.1
      );
  
      if (isEvenlyDistributed) {
        insights.push({
          type: "info",
          message: `Mixed opinions on "${question.questionText}" - no clear consensus`,
        });
      }
    });
  
    return insights;
  };
  
  export const generateRecommendations = (surveyData, userResponses) => {
    if (!surveyData || !userResponses) return [];
  
    const recommendations = [];
  
    const participationRate = calculateParticipationRate(surveyData, userResponses);
    if (participationRate < 100) {
      const unansweredQuestions = surveyData.questions.filter(
        (question) =>
          !userResponses.find((response) => Number(response.questionId) === Number(question.questionId))
      );
  
      if (unansweredQuestions.length > 0) {
        recommendations.push({
          priority: "high",
          action: "Complete remaining questions",
          details: `${unansweredQuestions.length} questions remain unanswered`,
        });
      }
    }
  
    return recommendations;
  };
  
  export const generateAnalysis = (surveyData, userResponses) => {
    if (!surveyData || !userResponses) return null;
  
    return {
      overview: {
        totalQuestions: surveyData.questions.length,
        totalResponses: surveyData.totalResponses,
        participationRate: calculateParticipationRate(surveyData, userResponses),
      },
      trends: identifyTrends(surveyData),
      insights: generateInsights(surveyData, userResponses),
      recommendations: generateRecommendations(surveyData, userResponses),
      timestamp: new Date().toISOString(),
    };
  };
  