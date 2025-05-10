export const fetchSurvey = async (surveyId) => {
    const response = await fetch(`http://localhost:8080/api/surveys/${surveyId}`);
    if (!response.ok) {
      throw new Error("Failed to fetch survey data");
    }
    return await response.json();
  };
  
  export const updateSurveyAPI = async (surveyId, surveyData) => {
    const response = await fetch(`http://localhost:8080/api/surveys/${surveyId}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(surveyData),
    });
    if (!response.ok) {
      throw new Error("Failed to update survey");
    }
    return await response.json();
  };
  