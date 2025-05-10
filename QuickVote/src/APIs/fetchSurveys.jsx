export const fetchSurveysByAdmin = async (email, setAdmin, setCreatedSurveys) => {
    try {
        const response = await fetch("http://localhost:8080/api/surveys/fetch-by-admin", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ adminEmail: email }),
        });

        if (!response.ok) {
            throw new Error("Failed to fetch surveys");
        }

        const data = await response.json();
        setAdmin(data.admin);
        setCreatedSurveys(data.createdSurveys);
        console.log("API Returned Admin Role:", fetchedAdmin.role);

    } catch (error) {
        console.error("Error fetching surveys:", error);
    }
};

export const deleteSurvey = async (surveyId) => {
    try {
        const response = await fetch(`http://localhost:8080/api/surveys/${surveyId}`, {
            method: "DELETE",
        });

        if (!response.ok) {
            throw new Error("Failed to delete survey");
        }

        return true;
    } catch (error) {
        console.error("Error deleting survey:", error);
        return false;
    }
};
