import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import {
  BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer, CartesianGrid, Legend, PieChart, Pie, Cell,
} from "recharts";
import { FileDown, Brain, TrendingUp, AlertTriangle } from 'lucide-react';
import apiService, { getUserFromToken, isTokenExpired } from "../APIs/AdminResultPageAPI";

const SurveyResult = () => {
  const [surveyData, setSurveyData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [chartType, setChartType] = useState("bar");
  const [analysis, setAnalysis] = useState(null);

  const location = useLocation();
  const navigate = useNavigate();
  const surveyId = location.state?.surveyId;
  const COLORS = ["#FF6384", "#36A2EB", "#FFCE56", "#4CAF50", "#FF9800", "#9C27B0"];

  // Get user data from JWT token
  const userData = getUserFromToken();

  useEffect(() => {
    // Check authentication first
    if (!userData || isTokenExpired()) {
      setError("Authentication expired. Please login again.");
      setTimeout(() => navigate("/login"), 2000);
      return;
    }

    if (!surveyId) {
      setError("Survey ID is missing. Please go back and try again.");
      setLoading(false);
      return;
    }

    // Fetch required data
    const fetchData = async () => {
      try {
        // Fetch survey data
        const surveyData = await apiService.getSurveyData(surveyId);
        setSurveyData(surveyData);
        setLoading(false);
      } catch (error) {
        console.error("Error loading data:", error);
        setError("Failed to load survey data. Please try again.");
        setLoading(false);
      }
    };

    fetchData();
  }, [surveyId, navigate]);

  useEffect(() => {
    if (surveyData) {
      generateAnalysis();
    }
  }, [surveyData]);

  const generateAnalysis = () => {
    if (!surveyData) return;

    const analysis = {
      overview: {
        totalQuestions: surveyData.questions.length,
        totalResponses: surveyData.totalResponses,
        participationRate: calculateParticipationRate(),
      },
      trends: identifyTrends(),
      insights: generateInsights(),
      recommendations: generateRecommendations(),
      timestamp: new Date().toISOString(),
    };

    setAnalysis(analysis);
  };

  const calculateParticipationRate = () => {
    // For admin view, we can calculate participation rate based on total responses
    // compared to number of questions. This is just an example - adjust as needed.
    return surveyData.totalResponses > 0 ? 100 : 0;
  };

  const identifyTrends = () => {
    const trends = [];

    surveyData.questions.forEach(question => {
      const options = question.options;
      const totalVotes = options.reduce((sum, opt) => sum + opt.frequency, 0);
      
      if (totalVotes === 0) return;
      
      const dominantOption = options.reduce((prev, current) =>
        (current.frequency > prev.frequency) ? current : prev
      );

      const dominanceRatio = dominantOption.frequency / totalVotes;

      if (dominanceRatio > 0.6) {
        trends.push({
          question: question.questionText,
          dominantOption: dominantOption.optionText,
          percentage: Math.round(dominanceRatio * 100),
          strength: dominanceRatio > 0.8 ? 'Very Strong' : 'Strong'
        });
      }
    });

    return trends;
  };

  const generateInsights = () => {
    const insights = [];

    if (surveyData.totalResponses < 5) {
      insights.push({
        type: 'warning',
        message: 'Low response count may affect the reliability of results'
      });
    }

    surveyData.questions.forEach(question => {
      const options = question.options;
      const totalVotes = options.reduce((sum, opt) => sum + opt.frequency, 0);
      
      if (totalVotes === 0) {
        insights.push({
          type: 'warning',
          message: `No responses for question: "${question.questionText}"`
        });
        return;
      }
      
      const frequencies = options.map(opt => opt.frequency / totalVotes);

      const isEvenlyDistributed = frequencies.every(freq =>
        Math.abs(freq - 1 / options.length) < 0.1
      );

      if (isEvenlyDistributed && totalVotes >= 5) {
        insights.push({
          type: 'info',
          message: `Mixed opinions on "${question.questionText}" - no clear consensus`
        });
      }
    });

    return insights;
  };

  const generateRecommendations = () => {
    const recommendations = [];

    if (surveyData.totalResponses < 10) {
      recommendations.push({
        priority: 'medium',
        action: 'Collect more responses',
        details: 'More responses would increase the reliability of results'
      });
    }

    // Look for questions with no clear consensus
    surveyData.questions.forEach(question => {
      const options = question.options;
      const totalVotes = options.reduce((sum, opt) => sum + opt.frequency, 0);
      
      if (totalVotes === 0) return;
      
      const frequencies = options.map(opt => opt.frequency / totalVotes);
      const highestFrequency = Math.max(...frequencies);
      
      if (highestFrequency < 0.4 && totalVotes >= 5) {
        recommendations.push({
          priority: 'low',
          action: 'Follow up on mixed opinions',
          details: `Question "${question.questionText}" has diverse answers and may need further investigation`
        });
      }
    });

    return recommendations;
  };

  const downloadReport = () => {
    if (!analysis) return;

    const report = `
Survey Analysis Report
Generated on: ${new Date(analysis.timestamp).toLocaleString()}

OVERVIEW
--------
Total Questions: ${analysis.overview.totalQuestions}
Total Responses: ${analysis.overview.totalResponses}
Participation Rate: ${analysis.overview.participationRate.toFixed(1)}%

KEY TRENDS
----------
${analysis.trends.map(trend => `
• ${trend.question}
  - Dominant Choice: ${trend.dominantOption}
  - Support: ${trend.percentage}%
  - Trend Strength: ${trend.strength}
`).join('\n')}

INSIGHTS
--------
${analysis.insights.map(insight => `• ${insight.message}`).join('\n')}

RECOMMENDATIONS
--------------
${analysis.recommendations.map(rec => `
[${rec.priority.toUpperCase()}] ${rec.action}
${rec.details}
`).join('\n')}
    `;

    const blob = new Blob([report], { type: 'text/plain' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `survey-analysis-${surveyId}.txt`;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    window.URL.revokeObjectURL(url);
  };

  if (loading) return <div className="loading">Loading survey results...</div>;
  if (error) return <div className="error">{error}</div>;

  return (
    <div className="bg-gradient-to-r from-blue-100 to-purple-100 p-8 rounded-lg shadow-lg">
      <header className="flex justify-between items-center mb-8 bg-white bg-opacity-70 p-4 rounded-lg">
        <div>
          <h1 className="text-2xl font-bold text-blue-800">{surveyData.surveyTitle}</h1>
          <p className="text-purple-700">Total Responses: {surveyData.totalResponses}</p>
          {userData && <p className="text-blue-600">User: {userData.email} ({userData.role})</p>}
        </div>
        <button onClick={downloadReport} className="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-lg flex items-center gap-2">
          <FileDown className="text-white" />
          Download Analysis Report
        </button>
      </header>
  
      {analysis && (
        <div className="space-y-6">
          <div className="bg-white bg-opacity-80 p-6 rounded-lg shadow">
            <div className="flex items-center gap-2 mb-4">
              <Brain className="text-purple-600" />
              <h2 className="text-xl font-semibold text-blue-800">AI Analysis Overview</h2>
            </div>
            <div className="mb-2">
              <label className="text-purple-700 font-medium">Participation Rate</label>
              <div className="h-4 bg-blue-100 rounded-full w-full mt-1">
                <div 
                  className="h-4 bg-gradient-to-r from-blue-500 to-purple-500 rounded-full" 
                  style={{ width: `${analysis.overview.participationRate}%` }}
                />
              </div>
              <span className="text-blue-700 font-medium">{analysis.overview.participationRate.toFixed(1)}%</span>
            </div>
          </div>
  
          {analysis.trends.length > 0 && (
            <div className="bg-white bg-opacity-80 p-6 rounded-lg shadow">
              <div className="flex items-center gap-2 mb-4">
                <TrendingUp className="text-blue-600" />
                <h2 className="text-xl font-semibold text-purple-800">Key Trends</h2>
              </div>
              <ul className="space-y-3">
                {analysis.trends.map((trend, index) => (
                  <li key={index} className="border-l-4 border-blue-400 pl-3 py-1">
                    <p className="font-medium text-blue-800">{trend.question}</p>
                    <p className="text-purple-700">
                      {trend.dominantOption} ({trend.percentage}% - {trend.strength})
                    </p>
                  </li>
                ))}
              </ul>
            </div>
          )}
  
          {analysis.insights.length > 0 && (
            <div className="bg-white bg-opacity-80 p-6 rounded-lg shadow">
              <div className="flex items-center gap-2 mb-4">
                <AlertTriangle className="text-purple-600" />
                <h2 className="text-xl font-semibold text-blue-800">Important Insights</h2>
              </div>
              <ul className="space-y-3">
                {analysis.insights.map((insight, index) => (
                  <li key={index} className={`p-3 rounded-lg ${
                    insight.type === 'positive' ? 'bg-blue-100 text-blue-800' : 
                    insight.type === 'warning' ? 'bg-orange-100 text-orange-800' : 
                    'bg-gray-100 text-gray-800'
                  }`}>
                    {insight.message}
                  </li>
                ))}
              </ul>
            </div>
          )}
        </div>
      )}
  
      <div className="mt-6 flex justify-end">
        <button
          onClick={() => setChartType(chartType === "bar" ? "pie" : "bar")}
          className="bg-purple-500 hover:bg-purple-600 text-white px-4 py-2 rounded-lg"
        >
          Switch to {chartType === "bar" ? "Pie Chart" : "Bar Chart"}
        </button>
      </div>
  
      <div className="grid grid-cols-1 md:grid-cols-2 gap-8 mt-6">
        {surveyData.questions.map((question) => {
          const chartData = question.options.map((option, index) => ({
            name: option.optionText,
            votes: option.frequency,
            color: COLORS[index % COLORS.length],
          }));

          return (
            <div key={question.questionId} className="bg-white bg-opacity-90 p-6 rounded-lg shadow-md">
              <h2 className="text-xl font-semibold text-blue-800 mb-4">{question.questionText}</h2>
  
              <div className="h-80 flex items-center justify-center">
                <ResponsiveContainer width="100%" height={300}>
                  {chartType === "bar" ? (
                    <BarChart data={chartData} margin={{ top: 20, right: 30, left: 40, bottom: 10 }}>
                      <CartesianGrid strokeDasharray="3 3" stroke="#e0e0ff" />
                      <XAxis dataKey="name" stroke="#6366f1" />
                      <YAxis stroke="#6366f1" />
                      <Tooltip cursor={{ fill: "rgba(219, 234, 254, 0.4)" }} />
                      <Legend />
                      <Bar dataKey="votes" fill="#8b5cf6" barSize={30} radius={[8, 8, 0, 0]} />
                    </BarChart>
                  ) : (
                    <PieChart>
                      <Pie 
                        data={chartData} 
                        dataKey="votes" 
                        nameKey="name" 
                        cx="50%" 
                        cy="50%" 
                        outerRadius={120} 
                        label
                      >
                        {chartData.map((entry, index) => (
                          <Cell key={`cell-${index}`} fill={entry.color} stroke="#fff" strokeWidth={2} />
                        ))}
                      </Pie>
                      <Tooltip />
                    </PieChart>
                  )}
                </ResponsiveContainer>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default SurveyResult;