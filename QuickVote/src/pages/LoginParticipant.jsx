// File: LoginPage.js
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import EmailForm from "../Component/LoginPage/EmailForm";
import OtpPopup from "../Component/LoginPage/OtpPopup";
import BackgroundAnimation from "../Component/LoginPage/BackgroundAnimation";
import LoginBanner from "../Component/LoginPage/LoginBanner";
import useCountdown from "../Component/LoginPage/useCountdown";
// import jwtDecode from "jwt-decode";
import { jwtDecode } from "jwt-decode";
import { sendOtp, verifyOtp } from "../APIs/authAPI";

const LoginPage = () => {
  const [email, setEmail] = useState("");
  const [isPopupVisible, setIsPopupVisible] = useState(false);
  const [otp, setOtp] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [isOtpSending, setIsOtpSending] = useState(false);
  const [error, setError] = useState("");
  const [otpRequested, setOtpRequested] = useState(false);
  const navigate = useNavigate();
  
  const [countdown, startCountdown] = useCountdown(0);

  const handleGenerateOtp = async () => {
    if (!email.match(/^\S+@\S+\.\S+$/)) {
      setError("Please enter a valid email address");
      return;
    }

    const lastOtpTime = localStorage.getItem("lastOtpTime");
    const currentTime = Date.now();

    if (lastOtpTime && currentTime - lastOtpTime < 60000) {
      setError(`Please wait ${Math.ceil((60000 - (currentTime - lastOtpTime)) / 1000)}s before resending OTP`);
      return;
    }

    setError("");
    setIsOtpSending(true);

    const response = await sendOtp(email);
    if (response.success) {
      localStorage.setItem("lastOtpTime", Date.now());
      setIsPopupVisible(true);
      startCountdown(60);
      setOtpRequested(true);
    } else {
      setError(response.message);
    }
    setIsOtpSending(false);
  };

  const handleLogin = async (e) => {
  e.preventDefault();
  if (otp.length !== 6) {
    setError("Please enter a 6-digit code");
    return;
  }

  setIsLoading(true);
  const response = await verifyOtp(email, otp);
  if (response.success) {
    const token = response.token;
    localStorage.setItem("token", token); // ✅ Store token only

    const decoded = jwtDecode(token);
    console.log("Logged in as:", decoded.sub); // You can use decoded.sub when needed

    navigate("/dashboard");
  } else {
    setError(response.message);
    setOtp("");
  }
  setIsLoading(false);
};


  const handleClosePopup = () => {
    setIsPopupVisible(false);
    setOtp("");
    setError("");
  };

  const reopenOtpPopup = () => {
    setIsPopupVisible(true);
  };

  // Show reopen button only if OTP was requested, popup is closed, and countdown is still active
  const showReopenButton = otpRequested && !isPopupVisible && countdown > 0;

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-r from-blue-50 to-purple-50 p-4">
      <BackgroundAnimation />

      <div className="w-full max-w-md relative z-10">
        <div className="bg-white bg-opacity-80 backdrop-blur-lg rounded-3xl shadow-xl overflow-hidden">
          <LoginBanner />
          
          <div className="p-8">
            <h1 className="text-3xl font-bold text-blue-700 mb-6 text-center">Welcome Back</h1>
            <EmailForm 
              email={email}
              setEmail={setEmail}
              error={error}
              handleGenerateOtp={handleGenerateOtp}
              isOtpSending={isOtpSending}
              showReopenButton={showReopenButton}
              reopenOtpPopup={reopenOtpPopup}
            />
          </div>
        </div>
      </div>

      {isPopupVisible && (
        <OtpPopup
          email={email}
          otp={otp}
          setOtp={setOtp}
          error={error}
          isLoading={isLoading}
          countdown={countdown}
          handleClosePopup={handleClosePopup}
          handleLogin={handleLogin}
          handleGenerateOtp={handleGenerateOtp}
        />
      )}
    </div>
  );
};

export default LoginPage;