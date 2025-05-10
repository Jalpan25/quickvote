import axios from "axios";

const API_BASE_URL = "http://localhost:8080/auth"; // Backend API base URL

// Send OTP
export const sendOtp = async (email) => {
  try {
    await axios.post(`${API_BASE_URL}/send-otp`, { email }, {
      headers: { "Content-Type": "application/json" }
    });
    return { success: true };
  } catch (error) {
    return { success: false, message: "Failed to send OTP. Please try again." };
  }
};

// Verify OTP
export const verifyOtp = async (email, otp) => {
  try {
    await axios.post(`${API_BASE_URL}/verify-otp`, { email, otp });
    return { success: true };
  } catch (error) {
    return { success: false, message: "Invalid verification code. Please try again." };
  }
};
