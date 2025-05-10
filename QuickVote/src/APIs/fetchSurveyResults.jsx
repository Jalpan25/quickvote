import axios from "axios";

export const fetchSurveyResults = async (surveyId, setSurveyData, setLoading, setError) => {
  try {
    const response = await axios.post("http://localhost:8080/api/survey-results/results", { surveyId });
    setSurveyData(response.data);
  } catch (error) {
    setError("Failed to load survey data. Please try again.");
  } finally {
    setLoading(false);
  }
};

export const fetchUserResponses = async (surveyId, email, setUserResponses) => {
  try {
    const response = await axios.post("http://localhost:8080/api/responses/fetch", {
      surveyId,
      email,
    });
    setUserResponses(response.data.responses || []);
  } catch (error) {
    console.error("Error fetching user responses:", error);
  }
};
