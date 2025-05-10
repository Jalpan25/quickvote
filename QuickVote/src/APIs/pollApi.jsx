// src/api/pollApi.js
export const createPollAPI = async (pollData) => {
    const response = await fetch("http://localhost:8080/api/surveys", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(pollData),
    });
  
    if (!response.ok) {
      throw new Error(`Error: ${response.statusText}`);
    }
  
    return response.json();
  };
  