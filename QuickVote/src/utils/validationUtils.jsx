// Validate email format
export const isValidEmail = (email) => {
    return /^\S+@\S+\.\S+$/.test(email);
  };
  
  // Validate OTP format (must be exactly 6 digits)
  export const isValidOtp = (otp) => {
    return /^\d{6}$/.test(otp);
  };
  