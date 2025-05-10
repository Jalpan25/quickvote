export const submitResponse = async (surveyId, responses, navigate) => {
    const userEmail = localStorage.getItem("userEmail");
    if (!userEmail) {
      alert("User email not found. Please log in again.");
      return;
    }
  
    const surveySubmissionData = {
      email: userEmail,
      responses: Object.entries(responses).map(([questionId, optionId]) => ({
        questionId: parseInt(questionId, 10),
        optionId: parseInt(optionId, 10),
      })),
    };
  
    try {
      const response = await fetch(`http://localhost:8080/api/surveys/${surveyId}/responses`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(surveySubmissionData),
      });
  
      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`Failed to submit responses: ${errorText}`);
      }
  
      navigate("/dashboard");
    } catch (error) {
      console.error("Error submitting survey:", error);
      alert(`Failed to submit survey. Please try again. Error: ${error.message}`);
    }
  };