package com.example.QuickVote.util;

/**
 * Utility class for email templates used in the application
 */
public class EmailTemplates {

    /**
     * Generates an OTP verification email template
     *
     * @param otp The one-time password to include in the email
     * @return HTML content for the email as a string
     */
    public static String getOtpEmailTemplate(String otp) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <title>QuickVote OTP Verification</title>" +
                "    <style>" +
                "        body { font-family: 'Arial', sans-serif; line-height: 1.6; color: #333333; background-color: #f8f9fa; margin: 0; padding: 0; }" +
                "        .container { max-width: 600px; margin: 20px auto; background-color: #ffffff; border-radius: 16px; overflow: hidden; box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1); }" +
                "        .header { background: linear-gradient(to right, #4a90e2, #764ba2, #b24592); padding: 30px 20px; text-align: center; color: white; }" +
                "        .header h2 { margin: 0; font-size: 24px; font-weight: 700; letter-spacing: 0.5px; }" +
                "        .header p { margin: 8px 0 0; opacity: 0.9; font-size: 15px; }" +
                "        .content { padding: 30px 25px; background-color: #ffffff; }" +
                "        .otp-container { margin: 30px auto; text-align: center; }" +
                "        .otp-code { display: inline-block; font-size: 38px; font-weight: bold; letter-spacing: 8px; background: linear-gradient(to right, #4a90e2, #7f6de3, #b24592); -webkit-background-clip: text; background-clip: text; color: transparent; padding: 15px 25px; border-radius: 12px; box-shadow: 0 2px 15px rgba(0, 0, 0, 0.06); border: 1px solid #f0f0f0; }" +
                "        .message { margin-bottom: 25px; color: #4a5568; }" +
                "        .note { margin-top: 20px; padding: 15px; background-color: #f8f9fa; border-radius: 12px; font-size: 14px; color: #64748b; }" +
                "        .time-alert { background: linear-gradient(to right, #fff9e6, #ffe8e8); border-left: 4px solid #ff9800; padding: 12px 15px; border-radius: 8px; margin: 25px 0; color: #b45309; display: flex; align-items: center; }" +
                "        .time-alert svg { margin-right: 10px; flex-shrink: 0; }" +
                "        .divider { height: 1px; background: linear-gradient(to right, transparent, #e2e8f0, transparent); margin: 25px 0; }" +
                "        .footer { background-color: #f8f9fa; padding: 20px; text-align: center; font-size: 13px; color: #64748b; border-top: 1px solid #edf2f7; }" +
                "        .brand { font-weight: bold; color: #4a90e2; }" +
                "        .social-links { margin: 15px 0; }" +
                "        .social-links a { display: inline-block; margin: 0 8px; color: #64748b; text-decoration: none; }" +
                "        .copyright { margin-top: 15px; font-size: 12px; color: #94a3b8; }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class=\"container\">" +
                "        <div class=\"header\">" +
                "            <h2>QuickVote Verification</h2>" +
                "            <p>One-time password for account access</p>" +
                "        </div>" +
                "        <div class=\"content\">" +
                "            <div class=\"message\">" +
                "                <p>Hello,</p>" +
                "                <p>A verification request was initiated for your QuickVote account. Please use the following verification code to complete the process:</p>" +
                "            </div>" +
                "            <div class=\"otp-container\">" +
                "                <div class=\"otp-code\">" + otp + "</div>" +
                "            </div>" +
                "            <div class=\"time-alert\">" +
                "                <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"18\" height=\"18\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\">" +
                "                    <circle cx=\"12\" cy=\"12\" r=\"10\"></circle>" +
                "                    <polyline points=\"12 6 12 12 16 14\"></polyline>" +
                "                </svg>" +
                "                This code will expire in 60 second for security reasons." +
                "            </div>" +
                "            <div class=\"note\">" +
                "                <p><strong>Security Note:</strong> If you didn't request this verification code, please ignore this email or contact our support team if you have any concerns about your account security.</p>" +
                "            </div>" +
                "            <div class=\"divider\"></div>" +
                "            <p style=\"color: #64748b; font-size: 14px; text-align: center;\">Thank you for using QuickVote, your trusted voting platform.</p>" +
                "        </div>" +
                "        <div class=\"footer\">" +
                "            <div class=\"brand\">QuickVote</div>" +
                "            <div class=\"social-links\">" +
                "                <a href=\"#\">Help Center</a> | " +
                "                <a href=\"#\">Terms of Service</a> | " +
                "                <a href=\"#\">Privacy Policy</a>" +
                "            </div>" +
                "            <div class=\"copyright\">&copy; " + java.time.Year.now().getValue() + " QuickVote. All rights reserved.</div>" +
                "            <p style=\"font-size: 12px; color: #94a3b8; margin-top: 10px;\">This is an automated message, please do not reply to this email.</p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";
    }

    /**
     * You can add more email templates here as needed
     */
}