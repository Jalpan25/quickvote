export const createSurveyAPI = async (surveyData) => {
  try {
    const response = await fetch("http://localhost:8080/api/surveys", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(surveyData),
    });

    if (!response.ok) {
      const errorResponse = await response.text();
      throw new Error(errorResponse || "Failed to create survey");
    }

    const responseData = await response.json();
    return responseData; // Return the parsed JSON data
  } catch (error) {
    console.error("Network error:", error);
    throw error; // Re-throw the error to be handled by the caller
  }
};