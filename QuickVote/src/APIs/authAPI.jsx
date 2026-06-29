import axios from "axios";
import { API_BASE_URL } from "./config";
const baseUrl = API_BASE_URL.replace(/\/api$/, "");

export const sendOtp = async (email) => {
  try {
    await axios.post(`${baseUrl}/auth/send-otp`, { email }, {
      headers: { "Content-Type": "application/json" }
    });
    return { success: true };
  } catch (error) {
    return { success: false, message: "Failed to send OTP. Please try again." };
  }
};

export const verifyOtp = async (email, otp) => {
  try {
    const response = await axios.post(`${baseUrl}/auth/verify-otp`, { email, otp }, {
      headers: { "Content-Type": "application/json" }
    });

    const token = response.data.token;
    return { success: true, token };
  } catch (error) {
    return { success: false, message: "Invalid verification code. Please try again." };
  }
};
